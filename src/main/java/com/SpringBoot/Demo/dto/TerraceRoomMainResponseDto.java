package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TerraceRoomMainResponseDto {
	
	private Long terrace_room_number;
	private String terrace_room_name;
	private int terrace_room_mop;
	private String original_file_name;
	private String saved_file_path;
	private String saved_file_name;
	private String shared_file_path;
	private String shared_file_name;
	private String create_date;
	private String terrace_room_active;
	private Member member;
	
	public TerraceRoomMainResponseDto(TerraceRoom tr){
		terrace_room_number = tr.getTerrace_room_number();
		terrace_room_name = tr.getTerrace_room_name();
		terrace_room_mop = tr.getTerrace_room_mop();
		original_file_name = tr.getOriginal_file_name();
		saved_file_path = tr.getSaved_file_path();
		saved_file_name = tr.getSaved_file_name();
		shared_file_path = tr.getShared_file_path();
		shared_file_name = tr.getShared_file_name();
		create_date = tr.getCreate_date();
		terrace_room_active = tr.getTerrace_room_active();
		member = tr.getMember();
	}

	@Override
	public String toString() {
		return "TerraceRoomMainResponseDto [terrace_room_number=" + terrace_room_number + ", terrace_room_name="
				+ terrace_room_name + ", terrace_room_mop=" + terrace_room_mop + ", original_file_name="
				+ original_file_name + ", saved_file_path=" + saved_file_path + ", saved_file_name=" + saved_file_name
				+ ", shared_file_path=" + shared_file_path + ", shared_file_name=" + shared_file_name + ", create_date="
				+ create_date + ", terrace_room_active=" + terrace_room_active + ", member=" + member + "]";
	}

}
