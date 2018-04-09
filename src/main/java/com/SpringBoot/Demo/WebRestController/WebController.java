package com.SpringBoot.Demo.WebRestController;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.SpringBoot.Demo.Service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {
	
	private MemberService memberService;
	@GetMapping("/")
	public String main(Model model) {
		File testFile = new File("app/config/terraceHouse/real-application.yml");
		if (testFile.exists()) {
			System.out.println(testFile.getAbsolutePath());
			System.out.println("존재");
		}
		else {
			System.out.println("존재안함");
		}
		model.addAttribute("members", memberService.findAllDesc());
		return "TR_Main";
	}
	
	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
	
	@GetMapping("/tr")
	public String JSP() {
		return "terraceRoom";
	}
}
