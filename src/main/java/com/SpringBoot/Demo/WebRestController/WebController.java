package com.SpringBoot.Demo.WebRestController;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.s3.S3Util;
import com.amazonaws.services.s3.AmazonS3;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {
	String bucketName = "terracehouse-user-bucket";
	private MemberService memberService;
	@GetMapping("/")
	public String main(Model model) {
		/*File testFile = new File("/app/config/terraceHouse/real-application.yml");
		if (testFile.exists()) {
			System.out.println(testFile.getAbsolutePath());
			System.out.println("존재");
		}
		else {
			System.out.println("존재안함");
			try {
				testFile.createNewFile();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
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
		String result = "";
		System.out.println(memberid);
		S3Util s3 = new S3Util();
		s3.createFolder(bucketName, memberid);
		return "TR_AfterEmailConfirm";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "/";
	}
	
	
}
