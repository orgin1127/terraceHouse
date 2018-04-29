package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerrace;
import com.SpringBoot.Demo.Domain.RegularTerraceMember.RegularTerraceMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegularTerraceMemberSaveRequestDto {
	
	private Long regular_terrace_member_number;
	private RegularTerrace regularTerrace;
	private Member member;
	
	@Builder
	public RegularTerraceMemberSaveRequestDto(RegularTerrace regularTerrace, Member member){
		this.regularTerrace = regularTerrace;
		this.member = member;
	}
	
	public RegularTerraceMember toEntity(){
		return RegularTerraceMember.builder()
									.regularTerrace(regularTerrace)
									.member(member)
									.build();
	}

	@Override
	public String toString() {
		return "RegularTerraceMemberSaveRequestDto [regular_terrace_member_number=" + regular_terrace_member_number
				+ ", regularTerrace=" + regularTerrace + ", member=" + member + "]";
	}
	
}
