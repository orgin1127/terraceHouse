package com.SpringBoot.Demo.Domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
	
	@Autowired
	PostsRepository postRepository;
	
	@After
	public void cleanUp() {
/*		postRepository.deleteAll	();*/
	}
	
	@Test
	public void getTestContentList() {
/*		//given
		postRepository.save(Posts.builder()
				.title("테스트 게시글 제목")
				.content("테스트 글 내용입니다")
				.author("테스트 글 작성자")
				.build());
		
		//when
		List<Posts> postsList = postRepository.findAll();
		
		//then
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle());
		assertThat(posts.getContent());*/
	}
	
	@Test
	public void BaseTimeEntityAdd(){
/*		//given
		LocalDateTime now = LocalDateTime.now();
		postRepository.save(Posts.builder()
				.title("테스트 게시글 제목")
				.content("테스트 글 내용")
				.author("작성자")
				.build());
		//when
		List<Posts> postsList = postRepository.findAll();
		//then
		Posts posts = postsList.get(0);
		assertTrue(posts.getCreatedDate().isAfter(now)); 
		assertTrue(posts.getModifiedDate().isAfter(now));
*/
	}
}
