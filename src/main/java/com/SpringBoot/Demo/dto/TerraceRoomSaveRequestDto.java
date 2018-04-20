package com.SpringBoot.Demo.dto;

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
public class TerraceRoomSaveRequestDto {
	
	private Long terrace_room_number;
	private String terrace_room_name;
	private int terrace_room_mop;
	private String create_date;
	private Member member;
	
	@Builder
	public TerraceRoomSaveRequestDto(String terrace_room_name, int terrace_room_mop, Member member) {
		this.terrace_room_name = terrace_room_name;
		this.terrace_room_mop = terrace_room_mop;
		this.member = member;
	}
	
	public TerraceRoom toEntity(){
		return TerraceRoom.builder()
							.terrace_room_name(terrace_room_name)
							.terrace_room_mop(terrace_room_mop)
							.member(member)
							.build();
	}
}
