package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.DTO.Posts.PostsMainResponseDTO;
import com.SpringBoot.Demo.DTO.Posts.PostsSaveRequestDTO;
import com.SpringBoot.Demo.Domain.PostsRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostService {
	private PostsRepository postsRepository;
	
	/*//DB data를 등록/수정/삭제하는 Service메소드는 @Transactional 어노테이션을 사용한다
	//메소드 작업 중 Exception 발생 시 해당 메소드에서 이루어진 DB작업을 초기화
	@Transactional
	public Long Save(PostsSaveRequestDTO dto){
		return postsRepository.save(dto.toEntity()).getId();
	}
	//test
	@Transactional(readOnly = true)
	public List<PostsMainResponseDTO> findAllDesc() {
		return postsRepository.findAllDesc().map(PostsMainResponseDTO::new).collect(Collectors.toList());
	}*/
}
