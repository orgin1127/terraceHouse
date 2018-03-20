package com.SpringBoot.Demo.WebRestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Demo.DTO.Posts.PostsSaveRequestDTO;
import com.SpringBoot.Demo.Domain.PostsRepository;

import lombok.AllArgsConstructor;

@RestController
//모든 필드의 생성자를 Lombok을 이용해 자동생성하는 annotation
@AllArgsConstructor
public class WebRestController {
	
	private PostsRepository postsRepository;


	@GetMapping("/hello")
	public String helloController(){
		return "Hello World! 그리고 안녕";
	}
	
	@PostMapping("/posts")
	public void savePosts(@RequestBody PostsSaveRequestDTO dto){
		postsRepository.save(dto.toEntity());
	}
}
