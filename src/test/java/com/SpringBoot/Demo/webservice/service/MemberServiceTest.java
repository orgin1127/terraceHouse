package com.SpringBoot.Demo.webservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.Member.MemberRepository;
import com.SpringBoot.Demo.Service.MemberService;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@After
	public void cleanup() {
		memberRepository.deleteAll();
	}
	
	@Test
	public void saveDtoDataToMemberTable() {
		//given
		MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
										.memberid("testacc1")
										.memberpw("123123")
										.member_email("ggg@gmail.com")
										.member_name("홍길동")
										.build();
		//when
		memberService.save(dto);
		//then
		Member member = memberRepository.findAll().get(2);
		assertThat(member.getMemberid()).isEqualTo(dto.getMemberid());
		assertThat(member.getMember_email()).isEqualTo(dto.getMember_email());
		assertThat(member.getMemberpw()).isEqualTo(dto.getMemberpw());
		assertThat(member.getMember_name()).isEqualTo(dto.getMember_name());
	}

}
