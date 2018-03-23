package com.SpringBoot.Demo.WebRestController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.SpringBoot.Demo.Service.PostService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {
	
	private PostService postService;
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("posts", postService.findAllDesc());
		return "main";
	}
}
