package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerrace;
import com.SpringBoot.Demo.Domain.RegularTerraceMember.RegularTerraceMember;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.Getter;

@Getter
public class RegularTerraceMemberMainResponseDto {
	
	private Long regular_terrace_member_number;
	private RegularTerrace regularTerrace;
	private Member member;
	
	public RegularTerraceMemberMainResponseDto (RegularTerraceMember rtm){
		this.regular_terrace_member_number = rtm.getRegular_terrace_member_number();
		this.regularTerrace = rtm.getRegularTerrace();
		this.member = rtm.getMember();
	}

	@Override
	public String toString() {
		return "RegularTerraceMemberMainResponseDto [regular_terrace_member_number=" + regular_terrace_member_number
				+ ", regularTerrace=" + regularTerrace + ", member=" + member + "]";
	}
	
	
	
}
