package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.Getter;

@Getter
public class TerraceRoomMainResponseDto {
	
	private Long terrace_room_number;
	private String terrace_room_name;
	private int terrace_room_mop;
	private String original_file_name;
	private String saved_file_path;
	private String shared_file_path;
	private String create_date;
	private Member member;
	
	public TerraceRoomMainResponseDto(TerraceRoom tr){
		terrace_room_number = tr.getTerrace_room_number();
		terrace_room_name = tr.getTerrace_room_name();
		terrace_room_mop = tr.getTerrace_room_mop();
		original_file_name = tr.getOriginal_file_name();
		saved_file_path = tr.getSaved_file_path();
		shared_file_path = tr.getShared_file_path();
		create_date = tr.getCreate_date();
		member = tr.getMember();
	}

	@Override
	public String toString() {
		return "TerraceRoomMainResponseDto [terrace_room_number=" + terrace_room_number + ", terrace_room_name="
				+ terrace_room_name + ", terrace_room_mop=" + terrace_room_mop + ", original_file_name="
				+ original_file_name + ", saved_file_path=" + saved_file_path + ", shared_file_path=" + shared_file_path
				+ ", create_date=" + create_date + ", member=" + member + "]";
	}

}
