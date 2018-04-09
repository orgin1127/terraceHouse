package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;

import lombok.Getter;

@Getter
public class MemberMainResponseDto {
	
	private Long member_number;
	private String memberid;
	private String memberpw;
	private String member_email;
	private String member_name;
	private String mail_confirmed;
	
	public MemberMainResponseDto(Member member){
		member_number = member.getMember_number();
		memberid = member.getMemberid();
		memberpw = member.getMemberpw();
		member_email = member.getMember_email();
		member_name = member.getMember_name();
		mail_confirmed = member.getMail_confirmed();
	}
}
