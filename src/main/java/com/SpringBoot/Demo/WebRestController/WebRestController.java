package com.SpringBoot.Demo.WebRestController;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/hello")
	public String helloController(){
		return "Hello World! 그리고 안녕";
	}
	
	@PostMapping("/memberRegi")
	public Long saveMember(@RequestBody MemberSaveRequestDto dto){
		return memberService.save(dto);
	}
	
	@GetMapping("/profile")
	public String getProfile() {
		return Arrays.stream(environment.getActiveProfiles())
				.findFirst()
				.orElse("");
	}
	
}
