package com.SpringBoot.Demo.WebRestController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.Member.MemberRepository;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;

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
					.append("' target='_blenk'>이메일 인증 확인</a>")
					.toString());
			sendMail.setTo(dto.getMember_email());
			sendMail.send();
		}
		catch (Exception e) {
			
		}
		return memberService.save(dto);
	}
	
	@GetMapping("/emailConfirm")
	public String emailConfirm(String key){
		System.out.println(key);
		
		return "";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody MemberSaveRequestDto dto){
		MemberSaveRequestDto loginedM = memberService.findByIdAndPw(dto);
		if (loginedM != null){
			if(loginedM.getMail_confirmed().equals("y")){
				
			}
			else if(loginedM.getMail_confirmed().equals("n")){
				
			}
		}
		return "";
	}
	
	@GetMapping("/profile")
	public String getProfile() {
		return Arrays.stream(environment.getActiveProfiles())
				.findFirst()
				.orElse("");
	}
	
}
