package com.SpringBoot.Demo.WebRestController;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.s3.S3FileUploadAndDownload;
import com.SpringBoot.Demo.s3.S3Util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WebController {
	
	
	private MemberService memberService;
	
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
	public String terraceRoom(Model model) {
		model.addAttribute("testacc1", "testacc1");
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
	
	
}
