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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.SpringBoot.Demo.Domain.HRBCGuider.Utile_Excel;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotification;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerrace;
import com.SpringBoot.Demo.Domain.RegularTerraceMember.RegularTerraceMember;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.SpringBoot.Demo.Service.JoinRoomMemberService;
import com.SpringBoot.Demo.Service.MemberNotificationService;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.Service.PersonalFileService;
import com.SpringBoot.Demo.Service.RegularTerraceMemberService;
import com.SpringBoot.Demo.Service.RegularTerraceRoomService;
import com.SpringBoot.Demo.Service.TerraceRoomService;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;
import com.SpringBoot.Demo.dto.PersonalFileMainResponseDto;
import com.SpringBoot.Demo.dto.RegularTerraceMemberSaveRequestDto;
import com.SpringBoot.Demo.dto.RegularTerraceSaveRequestDto;
import com.SpringBoot.Demo.dto.TerraceRoomMainResponseDto;
import com.SpringBoot.Demo.dto.TerraceRoomSaveRequestDto;
import com.SpringBoot.Demo.s3.S3FileUploadAndDownload;
import com.SpringBoot.Demo.s3.S3Util;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;

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
	private RegularTerraceMemberService regularTerraceMemberService;
	private MemberNotificationService memberNotificationService;
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
	
	//직접 정기 테라스를 만들었을 경우
	@PostMapping("/regularTerraceRegi")
	public Long regularTerraceRegi(@RequestBody RegularTerraceSaveRequestDto dto, HttpSession session){
		System.out.println("regi regular tr : "+ dto.toString());
		Long result;
		Member m = (Member) session.getAttribute("loginedMember");
		dto.setMember(m);
		result = regularTerraceRoomService.saveRegularTerrace(dto);
		//개설자를 곧바로 레귤러 테라스의 멤버로 추가
		if (result != 0) {
			RegularTerraceMemberSaveRequestDto saveDto = new RegularTerraceMemberSaveRequestDto();
			saveDto.setMember(m);
			RegularTerrace rt = regularTerraceRoomService.findOneByTerraceNumber(result);
			saveDto.setRegularTerrace(rt);
			regularTerraceMemberService.regularTerrareMemberInsert(saveDto);
		}
		return result;
	}
	
	@GetMapping("/getMyRegularTerrace")
	public ArrayList<RegularTerraceMember> getMyRegularTerrace(HttpSession session){
		Member m = (Member) session.getAttribute("loginedMember");
		ArrayList<RegularTerraceMember> list = regularTerraceMemberService.getAllMyRegularTerrace(m.getMember_number());
		return list;
	}
	
	//유저가 보낸 정기 테라스 초대를 받았을 경우
	@PostMapping("/acceptRTInvite")
	public Long regularTerraceMemberRegi(Long regular_terrace_number,HttpSession session){
		
		System.out.println("들어온 번호 : "+regular_terrace_number);
		RegularTerraceMemberSaveRequestDto saveDto = new RegularTerraceMemberSaveRequestDto();
		Member m = (Member) session.getAttribute("loginedMember");
		saveDto.setMember(m);
		RegularTerrace rt = regularTerraceRoomService.findOneByTerraceNumber(regular_terrace_number);
		saveDto.setRegularTerrace(rt);
		Long result = regularTerraceMemberService.regularTerrareMemberInsert(saveDto);		
		memberNotificationService.updateConfirmed(regular_terrace_number, m.getMemberid());
		return result;
	}
	
	@PostMapping("/getAllInvitation")
	public ArrayList<MemberNotification> getAllInvitation(HttpSession session){
		ArrayList<MemberNotification> result = null;
		String loginId = (String) session.getAttribute("loginID");
		
		
		System.out.println("검색할 ID : " + loginId);
		result = memberNotificationService.getNotificationList(loginId);
		
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
				.skip(1)
				.findFirst()
				.orElse("");
	}
	
	@GetMapping("/searchUser")
	public Member searchUser(@RequestParam("searchId") String searchId){
		
		Member m =memberService.findById(searchId);
		
		return m;
	}
	
	@PostMapping("/imgForScan")
	public String uploadImage(@RequestParam("file") MultipartFile file){
		String result = "";
		StringBuffer sb = new StringBuffer();
		String testString = "";
		try {
			S3FileUploadAndDownload s3File = new S3FileUploadAndDownload(s3.getAccess_key(), s3.getSecret_key());
			String fileName = "";
			if(file.getOriginalFilename() != null){
				int cut = file.getOriginalFilename().lastIndexOf('/');
				fileName = file.getOriginalFilename().substring(cut+1);
			}
			System.out.println(""+file.getOriginalFilename());
			System.out.println(fileName);
			
			byte[] data = file.getBytes();
			InputStream is = null;
			is = new ByteArrayInputStream(data);
			result = s3File.uploadImageToS3(is, s3.getBucket(), fileName);
			is.close();
			
			if (!result.equals("")){
				String url = "https://s3.ap-northeast-2.amazonaws.com/terracehouse-user-bucket/" + result;
				
				List<AnnotateImageRequest> requests = new ArrayList<>();
				ImageSource imgSource = ImageSource.newBuilder().setImageUri(url).build();
				Image img = Image.newBuilder().setSource(imgSource).build();
				Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
				AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
				requests.add(request);
				
				try (ImageAnnotatorClient client = ImageAnnotatorClient.create()){
					BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				    List<AnnotateImageResponse> responses = response.getResponsesList();
				    for (AnnotateImageResponse res : responses) {
				    	if (res.hasError()) {
				            System.out.printf("Error: %s\n", res.getError().getMessage());
				        }
				    	
				    	List<EntityAnnotation> annotation = res.getTextAnnotationsList();
				    	sb.append(annotation.get(0).getDescription());
				    }
				    
				    System.out.println("추출된 문자열 : "+sb.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
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
	@GetMapping("/roomsList")
	public List<TerraceRoomMainResponseDto> terraceRoomsList(Model model){
		System.out.println(terraceRoomService.findAllByTerraceActive());
		return terraceRoomService.findAllByTerraceActive();
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
	
	/**
	 * 이하 신입 개발 연수 사용 컨트롤러
	 * **/
	
	//엑셀 업로드시 사용할 method
	@PostMapping(path = "uploadExcel", produces = "application/json")
	public HashMap<String, List<String>> uploadExcel(@RequestParam("ex_file") MultipartFile excel, HttpSession session){
		
		System.out.println("upload method worked");
		
		//ArrayList for data save 1 is koumoku, 2 is data of first row
		List<String> itemList = new ArrayList<>();
		List<String> dataList = new ArrayList<>();
		
		ArrayList<List<String>> valueList = new ArrayList<>();
		
		if (!excel.isEmpty()) {
			try {
				
				int pos = excel.getOriginalFilename().lastIndexOf(".");
				String filename = excel.getOriginalFilename().substring(pos);
				
				//convert MultipartFile to File
				InputStream is = null;
				is = excel.getInputStream();
				
				
				
				if (filename.equals(".xls")) {
					
					HSSFWorkbook workBook = new HSSFWorkbook(is);
					HSSFSheet sheet;
					HSSFRow row;
					HSSFCell cell;
					
					sheet = workBook.getSheetAt(0);
					
					//항목 추출
					row = sheet.getRow(0);
					int noc = row.getPhysicalNumberOfCells();
					
					for (int count = 0; count < noc; count++){
						cell = row.getCell(count);
						System.out.println(cell.toString());
						itemList.add(cell.toString());
					}
					
					//첫번째 데이터 추출
					row = sheet.getRow(1);
					System.out.println("!!!"+noc+"!!!");
					
					for (int count = 0; count < noc; count++) {
						cell = row.getCell(count);
						if (cell == null) {
							dataList.add("");
						}
						else {
							dataList.add(cell.toString());
							System.out.println(cell.toString());							
						}
					}
					
					//항목의 데이터를 배열로 저장 후 리스트에 저장
					System.out.println("lor: "+sheet.getLastRowNum());
					int lor = sheet.getLastRowNum();
					
					int cellChangeNum = 0;
					for(int count = 0; count<noc; count++) {
						
						List<String> values = new ArrayList<>();
						
						for(int index=1; index<lor; index++){
							row = sheet.getRow(index);
							
							cell = row.getCell(cellChangeNum);
							
							if (cell == null){
								values.add("");
							}
							else {
								values.add(cell.toString());
							}
						}
						cellChangeNum++;
						valueList.add(values);
					}
				}
				
				else if (filename.equals(".xlsx")) {
					
					XSSFWorkbook workBook = new XSSFWorkbook(is);
					XSSFSheet sheet;
					XSSFRow row;
					XSSFCell cell;
					
					sheet = workBook.getSheetAt(0);
					
					//항목 추출
					row = sheet.getRow(0);
					int noc = row.getPhysicalNumberOfCells();
					
					for (int count = 0; count < noc; count++){
						cell = row.getCell(count);
						System.out.println(cell.toString());
						itemList.add(cell.toString());
					}
					
					//첫번째 데이터 추출
					row = sheet.getRow(1);
					System.out.println("!!!"+noc+"!!!");
					
					for (int count = 0; count < noc; count++) {
						cell = row.getCell(count);
						if (cell == null) {
							dataList.add("");
						}
						else {
							dataList.add(cell.toString());
							System.out.println(cell.toString());							
						}
					}
					
					//항목의 데이터를 배열로 저장 후 리스트에 저장
					System.out.println("lor: "+sheet.getLastRowNum());
					int lor = sheet.getLastRowNum();
					
					int cellChangeNum = 0;
					for(int count = 0; count<noc; count++) {
						
						List<String> values = new ArrayList<>();
						
						for(int index=1; index<lor; index++){
							row = sheet.getRow(index);
							
							cell = row.getCell(cellChangeNum);
							
							if (cell == null){
								values.add("");
							}
							else {
								values.add(cell.toString());
							}
						}
						cellChangeNum++;
						valueList.add(values);
					}
					
				}
				
				is.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				
			}
			
		}
		
		Utile_Excel ue = new Utile_Excel(valueList);
		session.setAttribute("itemList", ue);
		
		HashMap<String, List<String>> result = new HashMap<>();
		
		result.put("itemList", itemList);
		result.put("dataList", dataList);
		
		return result;
	}
	
	//항목 체크 후 확인 버튼 누른 뒤 작동할 method
	@PostMapping(path = "confirmCheck")
	public int confirmCheck(@RequestParam("checkedItemList") String[] checkedItemList) {
		for (int i = 0; i < checkedItemList.length; i++) {
			System.out.println(checkedItemList[i]);
		}
		
		return 1;
	}
	
}
