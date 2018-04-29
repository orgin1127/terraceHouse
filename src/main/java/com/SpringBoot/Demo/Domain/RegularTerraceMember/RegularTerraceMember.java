package com.SpringBoot.Demo.Domain.RegularTerraceMember;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerrace;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegularTerraceMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long regular_terrace_member_number;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "terrace_number")
	private RegularTerrace regularTerrace;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member_number")
	private Member member;

	public RegularTerraceMember() {
	}
	
	@Builder
	public RegularTerraceMember(RegularTerrace regularTerrace, Member member) {
		this.regularTerrace = regularTerrace;
		this.member = member;
	}

	@Override
	public String toString() {
		return "RegularTerraceMember [regular_terrace_member_number=" + regular_terrace_member_number
				+ ", regularTerrace=" + regularTerrace + ", member=" + member + "]";
	}
	

}
