package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.PersonalFile.PersonalFile;
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
public class PersonalFileSaveRequestDto {
	
	private Long file_number;
	private String saved_personal_file_path;
	private String saved_personal_file_name;
	private TerraceRoom terrace_room_number;
	private Member member_number;
	
	@Builder
	public PersonalFileSaveRequestDto(TerraceRoom terrace_room_number, Member member_number){
		this.terrace_room_number = terrace_room_number;
		this.member_number = terrace_room_number.getMember();
	}
	
	public PersonalFile toEntity(){
		return PersonalFile.builder().tr(terrace_room_number)
									.m(member_number)
									.build();
	}

	
	
	
}
