package com.SpringBoot.Demo.WebRestController;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.SpringBoot.Demo.Service.JoinRoomMemberService;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.Service.TerraceRoomService;
import com.SpringBoot.Demo.dto.JoinRoomMemberMainResponseDto;
import com.SpringBoot.Demo.dto.JoinRoomMemberSaveRequestDto;
import com.SpringBoot.Demo.s3.S3FileUploadAndDownload;
import com.SpringBoot.Demo.s3.S3Util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WebController {
	
	
	private MemberService memberService;
	private TerraceRoomService terraceRoomService;
	private JoinRoomMemberService joinRoomMemberService;
	
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
		TerraceRoom tr = terraceRoomService.findOneByTerraceRoomNumber(terrace_room_number);
		model.addAttribute("terraceName", tr.getTerrace_room_name());
		model.addAttribute("terraceInfo",tr);
		JoinRoomMemberSaveRequestDto dto = new JoinRoomMemberSaveRequestDto();
		dto.setMember((Member)session.getAttribute("loginedMember"));
		dto.setTerraceRoom(tr);
		Long result = joinRoomMemberService.save(dto);
		System.out.println(result);
		
		return "terraceRoom";
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
	public String blackBoard(String creator,String pages,Model model){
		
		model.addAttribute("creator",creator);
		model.addAttribute("pages", pages);
		return "myBlackBoard";
	}
	
	@GetMapping("/myFiles")
	public String myFiles(HttpSession session, Model model){
		
		Long l = (Long)session.getAttribute("member_number");
		List<JoinRoomMemberMainResponseDto> list = joinRoomMemberService.findOneByMemberNumber(l);
		model.addAttribute("jrm", list);
		return "willDelete";
	}
	
}
