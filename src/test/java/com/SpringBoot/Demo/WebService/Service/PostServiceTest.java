package com.SpringBoot.Demo.WebService.Service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.SpringBoot.Demo.DTO.Posts.PostsSaveRequestDTO;
import com.SpringBoot.Demo.Domain.Posts;
import com.SpringBoot.Demo.Domain.PostsRepository;
import com.SpringBoot.Demo.Service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostsRepository postsRepository;
	
	@After
	public void cleanUP() {
		postsRepository.deleteAll();
	}
	
	@Test
	public void dtoDataSavedtoPostTable() {
		//given
		PostsSaveRequestDTO dto = PostsSaveRequestDTO.builder()
				.author("테스트 글쓴이")
				.content("글 내용")
				.title("글 제목")
				.build();
		
		//when
		postService.Save(dto);
		
		//then
		Posts posts = postsRepository.findAll().get(0);
		assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
		assertThat(posts.getContent()).isEqualTo(dto.getContent()); 
		assertThat(posts.getTitle()).isEqualTo(dto.getTitle());
	}
}
