package com.SpringBoot.Demo.DTO.Posts;

import com.SpringBoot.Demo.Domain.Posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDTO {
	
	private String title; 
	private String content; 
	private String author;

	public Posts toEntity() {
		return Posts.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
	}
}
