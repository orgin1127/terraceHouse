package com.SpringBoot.Demo.WebRestController;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.SpringBoot.Demo.Service.JoinRoomMemberService;
import com.SpringBoot.Demo.Service.MemberNotificationService;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.Service.PersonalFileService;
import com.SpringBoot.Demo.Service.TerraceRoomService;
import com.SpringBoot.Demo.dto.JoinRoomMemberMainResponseDto;
import com.SpringBoot.Demo.dto.JoinRoomMemberSaveRequestDto;
import com.SpringBoot.Demo.dto.MemberNotificationSaveRequestDto;
import com.SpringBoot.Demo.dto.PersonalFileMainResponseDto;
import com.SpringBoot.Demo.dto.PersonalFileSaveRequestDto;
import com.SpringBoot.Demo.s3.S3FileUploadAndDownload;
import com.SpringBoot.Demo.s3.S3Util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WebController {
	
	
	private MemberService memberService;
	private TerraceRoomService terraceRoomService;
	private JoinRoomMemberService joinRoomMemberService;
	private PersonalFileService personalFileService;
	private MemberNotificationService memberNotificationService;
	
	@Autowired
	S3Util s3;
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("members", memberService.findAllDesc());
		
		return "TR_Main";
	}
	
	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
	
	@GetMapping("/tr")
	public String terraceRoom(Long terrace_room_number,Model model, HttpSession session) {
		model.addAttribute("loginid", session.getAttribute("loginID"));
		System.out.println(terrace_room_number);
		
		//생성된 방의 정보를 db에 등록 후 model에 담아 감
		TerraceRoom tr = terraceRoomService.findOneByTerraceRoomNumber(terrace_room_number);
		model.addAttribute("terraceName", tr.getTerrace_room_name());
		model.addAttribute("terraceInfo",tr);
		
		
		//방 생성자를 즉시 해당 방의 참여자로 db에 등록
		JoinRoomMemberSaveRequestDto dto = new JoinRoomMemberSaveRequestDto();
		dto.setMember((Member)session.getAttribute("loginedMember"));
		dto.setTerraceRoom(tr);
		
		Long result = joinRoomMemberService.save(dto);
		
		System.out.println("tr result : " + result);
		
		return "terraceRoom";
	}
	
	@GetMapping("/tr2")
	public String terraceRoom2(Long terrace_room_number,String creator,Model model, HttpSession session) {
		model.addAttribute("loginid", session.getAttribute("loginID"));
		model.addAttribute("creator",creator);
		System.out.println(terrace_room_number);
		
		//생성된 방의 정보를 db에 등록 후 model에 담아 감
		TerraceRoom tr = terraceRoomService.findOneByTerraceRoomNumber(terrace_room_number);
		model.addAttribute("terraceName", tr.getTerrace_room_name());
		model.addAttribute("terraceInfo",tr);
		
		
		//방 생성자를 즉시 해당 방의 참여자로 db에 등록
		JoinRoomMemberSaveRequestDto dto = new JoinRoomMemberSaveRequestDto();
		dto.setMember((Member)session.getAttribute("loginedMember"));
		dto.setTerraceRoom(tr);
		
		Long result = joinRoomMemberService.save(dto);
		
		System.out.println("tr result : " + result);
		
		return "terraceRoom";
	}
	
	@GetMapping("/myPage")
	public String myPage(HttpSession session, Model model){
		Member member = memberService.findById((String)session.getAttribute("loginID"));
		model.addAttribute("id",member.getMemberid());
		
		model.addAttribute("email",member.getMember_email());
		model.addAttribute("name", member.getMember_name());
		return "MyPage";
	}
	
	@GetMapping("/inviteUser")
	public String inviteUser(String terrace_num,String loginId,Model model){
		
		model.addAttribute("terrace_num", terrace_num);
		
		return "inviteUser";
	}
	
	@GetMapping("/inviting")
	public String inviting(String terrace_number, String receiver,HttpSession session){
		System.out.println("유저 ID : "+receiver);
		int num = Integer.parseInt(terrace_number);
		String loginId = (String)session.getAttribute("loginID");
		MemberNotificationSaveRequestDto dto = new MemberNotificationSaveRequestDto();
		
		dto.setSender(loginId);
		dto.setReceiver(receiver);
		dto.setNotification_content("/tr2?terrace_room_number="+num+"&creator="+loginId);
		dto.setNotification_type("invite");
		System.out.println(dto.toString());
		memberNotificationService.insertNotification(dto);
		
		return "inviteUser";
	}
	
	@GetMapping("/afterLogin")
	public String afterLogin(Model model, HttpSession session){
		model.addAttribute("loginid", session.getAttribute("loginID"));
		model.addAttribute("loginname",session.getAttribute("loginName"));
		return "TR_AfterLogin";
	}
	@GetMapping("/emailConfirm")
	public String emailConfirm(String key, String memberid){
		try{
			memberService.updateMemberEmailConfirmed(memberid);
			S3FileUploadAndDownload s3File = new S3FileUploadAndDownload(s3.getAccess_key(), s3.getSecret_key());
			s3File.createFolder(s3.getBucket(), "tr-user-files/"+memberid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "TR_AfterEmailConfirm";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "/";
	}
	@GetMapping("myBlackBoard")
	public String blackBoard(String creator, String pages
							, Model model, Long terrace_room_number,HttpSession session){
		
		model.addAttribute("creator",creator);
		model.addAttribute("pages", pages);
		
		Member m = (Member)session.getAttribute("loginedMember");
		System.out.println("m number : "+m.getMember_number());
		System.out.println("terrace room : " + terrace_room_number);
		JoinRoomMember jrm = joinRoomMemberService.findOneByJoinMemberNumberAndJoinTerrarcNumber(m.getMember_number(), terrace_room_number);
		System.out.println("jrm : "+ jrm.toString());
		PersonalFileSaveRequestDto dto = new PersonalFileSaveRequestDto();
		model.addAttribute("terrace_name", jrm.getTerrace_room().getTerrace_room_name());
		model.addAttribute("terrace_room_number", terrace_room_number);
		dto.setTerrace_room_number(jrm.getTerrace_room());
		dto.setMember_number(m);
		System.out.println("pfs : " + dto.toString());
		personalFileService.save(dto);
		return "myBlackBoard";
	}
	
	
	
	@GetMapping("/myFilesDownload")
	public ResponseEntity<byte[]> myFilesDownload(String filePath, String fileName){
		try{
			System.out.println("filePath = " + filePath);
			System.out.println("fileName = " + fileName);
			S3FileUploadAndDownload s3File = new S3FileUploadAndDownload(s3.getAccess_key(), s3.getSecret_key());
			return s3File.download(filePath, fileName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/terraceRoomsList")
	public String terraceRoomsList(Model model){
		model.addAttribute("terraceRoomList", terraceRoomService.findAllByTerraceActive());
		return "TR_TerraceRooms";
	}
}
