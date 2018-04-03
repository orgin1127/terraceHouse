package com.SpringBoot.Demo.WebRestController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Demo.Service.PostService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WebController {
	
	private PostService postService;
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("posts", postService.findAllDesc());
		return "demoTestJSP";
	}
}
