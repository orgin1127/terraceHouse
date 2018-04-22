package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.PersonalFile.PersonalFile;

import lombok.Getter;

@Getter
public class PersonalFileMainResponseDto {

	private Long file_number;
	private String saved_personal_file_path;
	private JoinRoomMember terrace_room_number;
	private Member member_number;
	
	public PersonalFileMainResponseDto(PersonalFile pf){
		this.file_number = pf.getFile_number();
		this.saved_personal_file_path = pf.getSaved_personal_file_path();
		this.terrace_room_number = pf.getTerrace_room_number();
		this.member_number = pf.getMember_number();
	}

	@Override
	public String toString() {
		return "PersonalFileMainResponseDto [file_number=" + file_number + ", saved_personal_file_path="
				+ saved_personal_file_path + ", terrace_room_number=" + terrace_room_number + ", member_number="
				+ member_number + "]";
	}
	
	
}
