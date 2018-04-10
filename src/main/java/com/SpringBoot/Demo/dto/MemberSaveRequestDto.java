package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDto {
	
	private Long member_number;
	private String memberid;
	private String memberpw;
	private String member_email;
	private String member_name;
	private String mail_confirmed;
	
	@Builder
	public MemberSaveRequestDto(String memberid, String memberpw, String member_email, String member_name){
		this.memberid = memberid;
		this.memberpw = memberpw;
		this.member_email = member_email;
		this.member_name = member_name;
	}
	
	public Member toEntity() {
		return Member.builder()
						.memberid(memberid)
						.memberpw(memberpw)
						.member_email(member_email)
						.member_name(member_name)
						.build();
	}
		
}
