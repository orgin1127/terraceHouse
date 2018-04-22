package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.Getter;

@Getter
public class JoinRoomMemberMainResponseDto {
	
	private Long join_number;
	private Member member;
	private TerraceRoom terraceRoom;
	
	
	public JoinRoomMemberMainResponseDto(JoinRoomMember jrm) {
		this.join_number = jrm.getJoin_number();
		this.member = jrm.getMember();
		this.terraceRoom = jrm.getTerrace_room();
	}


	@Override
	public String toString() {
		return "JoinRoomMemberMainResponseDto [join_number=" + join_number + ", member=" + member + ", terraceRoom="
				+ terraceRoom + "]";
	}

	
}
