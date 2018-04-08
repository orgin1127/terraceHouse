package com.SpringBoot.Demo.WebRestController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {
	
	
	
	@GetMapping("/")
	public String main(Model model) {
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
