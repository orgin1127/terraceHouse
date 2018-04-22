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
	private JoinRoomMember terrace_room_number;
	private Member member_number;
	
	@Builder
	public PersonalFileSaveRequestDto(String saved_personal_file_path, JoinRoomMember terrace_room_number, Member member_number){
		this.saved_personal_file_path = saved_personal_file_path;
		this.terrace_room_number = terrace_room_number;
		this.member_number = member_number;
	}
	
	public PersonalFile toEntity(){
		return PersonalFile.builder()
							.saved_personal_file_path(saved_personal_file_path)
							.jrm(terrace_room_number)
							.m(member_number)
							.build();
	}

	@Override
	public String toString() {
		return "PersonalFileSaveRequestDto [file_number=" + file_number + ", saved_personal_file_path="
				+ saved_personal_file_path + ", terrace_room_number=" + terrace_room_number + ", member_number="
				+ member_number + "]";
	}
	
	
}
