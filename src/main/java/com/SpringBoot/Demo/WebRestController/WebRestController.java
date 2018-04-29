package com.SpringBoot.Demo.WebRestController;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.SpringBoot.Demo.Service.JoinRoomMemberService;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.Service.PersonalFileService;
import com.SpringBoot.Demo.Service.RegularTerraceRoomService;
import com.SpringBoot.Demo.Service.TerraceRoomService;
import com.SpringBoot.Demo.dto.JoinRoomMemberMainResponseDto;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;
import com.SpringBoot.Demo.dto.PersonalFileMainResponseDto;
import com.SpringBoot.Demo.dto.RegularTerraceSaveRequestDto;
import com.SpringBoot.Demo.dto.TerraceRoomMainResponseDto;
import com.SpringBoot.Demo.dto.TerraceRoomSaveRequestDto;
import com.SpringBoot.Demo.s3.S3FileUploadAndDownload;
import com.SpringBoot.Demo.s3.S3Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;

//생성되는 모든 컨트롤러를 ResponseBody 대상으로 자동 정의해주는 annotation

//모든 필드의 생성자를 Lombok을 이용해 자동생성하는 annotation
@AllArgsConstructor
@RestController
public class WebRestController {
	
	private Environment environment;
	private TerraceRoomService terraceRoomService;
	private MemberService memberService;
	private PersonalFileService personalFileService;
	private JoinRoomMemberService joinRoomMemberService;
	private RegularTerraceRoomService regularTerraceRoomService;
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	S3Util s3;
	
	@GetMapping("/hello")
	public String helloController(){
		return "Hello World! 그리고 안녕";
	}
	
	@PostMapping("/memberRegi")
	public Long saveMember(@RequestBody MemberSaveRequestDto dto){
		String key = new TempKey().getKey(10, false);
		try{
			MailHandler sendMail = new MailHandler(mailSender);
			sendMail.setSubject("TerraceHouse 회원 가입 메일 인증");
			sendMail.setText(new StringBuffer().append("<h1>메일인증</h1>")
					.append("<a href='http://terraceshouses.com/emailConfirm?key=")
					.append(key)
					.append("&memberid="+dto.getMemberid())
					.append("' target='_blenk'>이메일 인증 확인</a>")
					.toString());
			sendMail.setTo(dto.getMember_email());
			sendMail.send();
		}
		catch (Exception e) {
			
		}
		return memberService.save(dto);
	}
	
	@PostMapping("/registTerraceRoom")
	public Long registTerraceRoom(@RequestBody TerraceRoomSaveRequestDto dto, HttpSession session){
		System.out.println("regi tr room : " + dto.toString());
		
		Member m = (Member) session.getAttribute("loginedMember");
		System.out.println(m.toString());
		
		Long result = terraceRoomService.save(dto, m);
		
		return result;
	}
	
	@PostMapping("/regularTerraceRegi")
	public Long regularTerraceRegi(@RequestBody RegularTerraceSaveRequestDto dto, HttpSession session){
		System.out.println("regi regular tr : "+ dto.toString());
		Long result;
		Member m = (Member) session.getAttribute("loginedMember");
		dto.setMember(m);
		result = regularTerraceRoomService.saveRegularTerrace(dto);
		return result;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody MemberSaveRequestDto dto, HttpSession session){
		System.out.println("로그인 작동");
		System.out.println(dto.toString());
		Member loginedM = memberService.findByIdAndPw(dto);
		String result = "n";
		if (loginedM != null){
			if(loginedM.getMail_confirmed().equals("y")){
				result = "y";
				session.setAttribute("loginedMember", loginedM);
				session.setAttribute("loginID", loginedM.getMemberid());
				session.setAttribute("member_number", loginedM.getMember_number());
				session.setAttribute("loginName", loginedM.getMember_name());
			}
			else if(loginedM.getMail_confirmed().equals("n")){
				result = "n";
			}
		}
		System.out.println(session.getAttribute("member_number"));
		return result;
	}
	
	@GetMapping("/profile")
	public String getProfile() {
		return Arrays.stream(environment.getActiveProfiles())
				.findFirst()
				.orElse("");
	}

	@PostMapping("/uploadPDF")
	public String uploadPDF(@RequestParam("file") MultipartFile file
							, @RequestParam("loginId") String memberid
							, @RequestParam("terraceName") String terraceName
							, @RequestParam("terrace_room_number") Long terrace_room_number
							,HttpSession session) {
		String result = "";
		System.out.println(file.getOriginalFilename()+", "+memberid+", " +terraceName);
		try{
			S3FileUploadAndDownload s3File = new S3FileUploadAndDownload(s3.getAccess_key(), s3.getSecret_key());
			HashMap<String, Object> map = s3File.fileUpload(file, memberid, s3.getBucket(), terraceName,terrace_room_number);
			result = map.get("page")+"";
			String original_file_name = (String)map.get("original_file_name");
			String saved_file_path = (String)map.get("saved_file_path");
			String shared_file_path = (String)map.get("shared_file_path");
			String saved_file_name = (String)map.get("saved_file_name");
			String shared_file_name = (String)map.get("shared_file_name");
			
			terraceRoomService.updateTerraceRoomInPDF(terrace_room_number, original_file_name
													, saved_file_path, saved_file_name 
													,shared_file_path,shared_file_name);
		}
		catch (Exception e) {
		}
		return result;
	}
	
	@PostMapping("/makePDF")
	public String loadPDF(@RequestParam("imageArray") String[] imageArray,
							@RequestParam("terrace_room_number")Long terrace_room_number
							,HttpSession session) {
		//받은 배열을 저장
		String[] imgArray = imageArray;
		//그 배열로 이미지를 생성할 변수
		BufferedImage image = null;
		byte[] bytimage;
		//새 PDF 생성
		PDDocument doc = new PDDocument();
		//페이지 갯수 만큼
		for (int i = 0; i < imgArray.length ; i++){
			bytimage = Base64.getDecoder().decode(imgArray[i].substring("data:image/png;base64,".length()));
			ByteArrayInputStream bis = new ByteArrayInputStream(bytimage);
			
			try {
				image = ImageIO.read(bis);
				bis.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
			//새 페이지 생성
			PDPage page = new PDPage(new PDRectangle(595, 842));
						
			try {
				doc.addPage(page);
				PDPageContentStream cs = new PDPageContentStream(doc, page);
				PDImageXObject pio = JPEGFactory.createFromImage(doc, image);
				//새 페이지에 그림을 붙여넣음
				cs.drawImage(pio, 0, 0);
				cs.close();
				//이름을 myPDF로
				//doc.save("myPDF.pdf");
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			S3FileUploadAndDownload s3File = new S3FileUploadAndDownload(s3.getAccess_key(), s3.getSecret_key());
			TerraceRoom tr = terraceRoomService.findOneByTerraceRoomNumber(terrace_room_number);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.save(out);
			
			InputStream is = null;
		    byte[] data = out.toByteArray();
	        is = new ByteArrayInputStream(data);
			
			
			s3File.uploadSharedPDF(is, tr);
			is.close();
			//페이지를 다 집어넣고 나서 문서를 닫음
			doc.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	@PostMapping("/makePersonalPDF")
	public String makePersonalPDF(@RequestParam("imageArray") String[] imageArray
								, @RequestParam("terrace_room_number")Long terrace_room_number
								, HttpSession session){
		//받은 배열을 저장
		String[] imgArray = imageArray;
		//그 배열로 이미지를 생성할 변수
		BufferedImage image = null;
		byte[] bytimage;
		//새 PDF 생성
		PDDocument doc = new PDDocument();
		//페이지 갯수 만큼
		for (int i = 0; i < imgArray.length ; i++){
			bytimage = Base64.getDecoder().decode(imgArray[i].substring("data:image/png;base64,".length()));
			ByteArrayInputStream bis = new ByteArrayInputStream(bytimage);
			
			try{
				image = ImageIO.read(bis);
				bis.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}			
			//새 페이지 생성
			PDPage page = new PDPage(new PDRectangle(595, 842));
						
			try{
				doc.addPage(page);
				PDPageContentStream cs = new PDPageContentStream(doc, page);
				PDImageXObject pio = JPEGFactory.createFromImage(doc, image);
				//새 페이지에 그림을 붙여넣음
				cs.drawImage(pio, 0, 0);
				cs.close();
				//이름을 myPDF로
				//doc.save("personalPDF.pdf");
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		try{
			S3FileUploadAndDownload s3File = new S3FileUploadAndDownload(s3.getAccess_key(), s3.getSecret_key());
			TerraceRoom tr = terraceRoomService.findOneByTerraceRoomNumber(terrace_room_number);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.save(out);
			InputStream is = null;
		    byte[] data = out.toByteArray();
		    is = new ByteArrayInputStream(data);
		    
		    String fileName = s3File.uploadPersonalPDF(is, tr);
			//페이지를 다 집어넣고 나서 문서를 닫음
			doc.close();
			Member m = (Member) session.getAttribute("loginedMember");
			personalFileService.updatePersonalFile(tr.getSaved_file_path(), fileName+ "(personal).pdf"
													,tr, m);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	@GetMapping("/myPage")
	public String myPage(HttpSession session, Model model){
		Member member = memberService.findById((String)session.getAttribute("loginID"));
		model.addAttribute("id",member.getMemberid());
		
		model.addAttribute("email",member.getMember_email());
		model.addAttribute("name", member.getMember_name());
		return "MyPage";
	}
	
	@GetMapping("/endOfTerraceRoom")
	public void endOfTerraceRoom(@RequestParam("terrace_room_number")Long terrace_room_number){
		System.out.println("end 작동");
		terraceRoomService.endOfTerraceRoom(terrace_room_number);
	}
	
	@PostMapping("/searchTerraceRoom")
	public List<TerraceRoom> searchTerraceRoom(@RequestParam("inputTitle") String inputTitle){
		System.out.println(inputTitle);
		ArrayList<TerraceRoom>list2 = new ArrayList<>();
		list2 = terraceRoomService.findAllByInputTitle(inputTitle);
		System.out.println(list2);
		return list2;
	}
	
	@GetMapping("/myFiles")
	public List<PersonalFileMainResponseDto> myFiles(HttpSession session){
		
		Member m = (Member) session.getAttribute("loginedMember");
		
		List<PersonalFileMainResponseDto> pList = personalFileService.findAllByMemberNumber(m);
		System.out.println(pList.toString());		
		
		
		return pList;
	}
	
}
