package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.Getter;

@Getter
public class TerraceRoomMainResponseDto {
	
	private Long terrace_room_number;
	private String terrace_room_name;
	private int terrace_room_mop;
	private String create_date;
	private Member member;
	
	public TerraceRoomMainResponseDto(TerraceRoom tr){
		terrace_room_number = tr.getTerrace_room_number();
		terrace_room_name = tr.getTerrace_room_name();
		terrace_room_mop = tr.getTerrace_room_mop();
		create_date = tr.getCreate_date();
		member = tr.getMember();
	}

	@Override
	public String toString() {
		return "TerraceRoomMainResponseDto [terrace_room_number=" + terrace_room_number + ", terrace_room_name="
				+ terrace_room_name + ", terrace_room_mop=" + terrace_room_mop + ", create_date=" + create_date
				+ ", member=" + member + "]";
	}
	

}
