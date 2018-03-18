package com.SpringBoot.Demo.WebRestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebRestController {
	
	@GetMapping("/hello")
	public String helloController(){
		return "Hello World! 그리고 안녕";
	}
}
