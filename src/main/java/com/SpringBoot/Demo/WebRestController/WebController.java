package com.SpringBoot.Demo.WebRestController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Demo.Service.PostService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {
	
	
	
	@GetMapping("/")
	public String main(Model model) {
		//model.addAttribute("posts", postService.findAllDesc());
		return "main";
	}
	
	@GetMapping("/chat")
	public String chat() {
		//model.addAttribute("posts", postService.findAllDesc());
		return "chat";
	}
	
}
