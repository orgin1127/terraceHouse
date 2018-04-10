package com.SpringBoot.Demo.webservice.Domain;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.Member.MemberRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@After
	public void cleanup() {
		memberRepository.deleteAll();
	}
	
	@Test
	public void memberRegiTest() {
		//given
		memberRepository.save(Member.builder()
								.memberid("testacc1")
								.memberpw("123123")
								.member_email("ggg@ggg.com")
								.member_name("홍길동")
								.build());
		//when
		List<Member> memberList = memberRepository.findAll();
		
		//then
		Member member = memberList.get(0);
		assertThat(member.getMemberid(), is("testacc1"));
		/*assertThat(member.getMemberid(), is("testacc1"));
		assertThat(member.getMember_name(), is("홍길동"));*/
	}

}
