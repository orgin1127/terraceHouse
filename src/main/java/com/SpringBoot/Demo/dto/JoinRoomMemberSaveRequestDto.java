package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRoomMemberSaveRequestDto {
	
	private Long join_number;
	private Member member;
	private TerraceRoom terraceRoom;
	
	@Builder
	public JoinRoomMemberSaveRequestDto(Member m, TerraceRoom tr){
		this.member = m;
		this.terraceRoom = tr;
	}
	
	public JoinRoomMember toEntity(){
		return JoinRoomMember.builder()
								.member(member)
								.terraceRoom(terraceRoom)
								.build();
	}

}
