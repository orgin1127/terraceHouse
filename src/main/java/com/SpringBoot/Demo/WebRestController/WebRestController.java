package com.SpringBoot.Demo.WebRestController;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;
import com.SpringBoot.Demo.s3.S3FileUploadAndDownload;
import com.SpringBoot.Demo.s3.S3Util;

import lombok.AllArgsConstructor;

//생성되는 모든 컨트롤러를 ResponseBody 대상으로 자동 정의해주는 annotation

//모든 필드의 생성자를 Lombok을 이용해 자동생성하는 annotation
@AllArgsConstructor
@RestController
public class WebRestController {
	
	private Environment environment;
	
	private MemberService memberService;
	
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
	
	@PostMapping("/login")
	public String login(@RequestBody MemberSaveRequestDto dto, HttpSession session){
		MemberSaveRequestDto loginedM = memberService.findByIdAndPw(dto);
		String result = "";
		if (loginedM != null){
			if(loginedM.getMail_confirmed().equals("y")){
				result = "y";
				session.setAttribute("loginedMember", loginedM);
				session.setAttribute("loginID", loginedM.getMemberid());
				session.setAttribute("loginName", loginedM.getMember_name());
			}
			else if(loginedM.getMail_confirmed().equals("n")){
				result = "n";
			}
		}
		return result;
	}
	
	@GetMapping("/profile")
	public String getProfile() {
		return Arrays.stream(environment.getActiveProfiles())
				.findFirst()
				.orElse("");
	}

	@GetMapping("/uploadPDF")
	public String uploadPDF(@RequestParam("file") MultipartFile file, @RequestParam("loginid") String memberid) {
		return "";
	}
}
