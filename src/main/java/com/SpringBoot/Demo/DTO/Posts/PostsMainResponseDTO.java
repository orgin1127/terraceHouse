package com.SpringBoot.Demo.DTO.Posts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.SpringBoot.Demo.Domain.Posts;

import lombok.Getter;

@Getter
public class PostsMainResponseDTO {
	
	private Long id;
	private String title;
	private String author; 
	private String modifiedDate;

	public PostsMainResponseDTO(Posts entity) { 
		id = entity.getId(); 
		title = entity.getTitle(); 
		author = entity.getAuthor(); 
		modifiedDate = toStringDateTime(entity.getModifiedDate()); 
	}
	private String toStringDateTime(LocalDateTime localDateTime){ 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		return Optional.ofNullable(localDateTime).map(formatter::format) .orElse(""); 
	}

}
