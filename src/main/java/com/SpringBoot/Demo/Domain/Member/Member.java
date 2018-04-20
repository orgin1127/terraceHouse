package com.SpringBoot.Demo.Domain.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long member_number;
	
	@Column(length = 100, nullable = false)
	private String memberid;
	
	@Column(nullable = false)
	private String memberpw;
	
	@Column(nullable = false)
	private String member_email;
	
	@Column(nullable = false)
	private String member_name;
	
	@Column(columnDefinition = "varchar(10) default 'N'")
	private String mail_confirmed = "n";
	
	public Member() {
	}
	
	@Builder
	public Member(String memberid, String memberpw, String member_email, String member_name){
		this.memberid = memberid;
		this.memberpw = memberpw;
		this.member_email = member_email;
		this.member_name = member_name;
	}
}
