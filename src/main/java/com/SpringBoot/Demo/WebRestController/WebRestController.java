package com.SpringBoot.Demo.WebRestController;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Demo.DTO.Posts.PostsSaveRequestDTO;
import com.SpringBoot.Demo.Domain.PostsRepository;
import com.SpringBoot.Demo.Service.PostService;

import lombok.AllArgsConstructor;

@RestController
//모든 필드의 생성자를 Lombok을 이용해 자동생성하는 annotation
@AllArgsConstructor
public class WebRestController {
	
	private PostService postService;
	private Environment environment;

	@GetMapping("/hello")
	public String helloController(){
		return "Hello World! 그리고 안녕";
	}
	
	/*@PostMapping("/posts")
	public Long savePosts(@RequestBody PostsSaveRequestDTO dto){
		return postService.Save(dto);
	}*/
	
	/*@GetMapping("/profile")
	public String getProfile() {
		return Arrays.stream(environment.getActiveProfiles())
				.findFirst()
				.orElse("");
	}*/
}
