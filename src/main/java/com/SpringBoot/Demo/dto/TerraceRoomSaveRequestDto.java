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
	private String original_file_name;
	private String saved_file_path;
	private String saved_file_name;
	private String shared_file_path;
	private String shared_file_name;
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


	public TerraceRoomSaveRequestDto(Long terrace_room_number, String original_file_name,
			String saved_file_path, String saved_file_name,
			String shared_file_path, String shared_file_name) {
		this.terrace_room_number = terrace_room_number;
		this.original_file_name = original_file_name;
		this.saved_file_path = saved_file_path;
		this.saved_file_name = saved_file_name;
		this.shared_file_path = shared_file_path;
		this.shared_file_name = shared_file_name;
	}

	@Override
	public String toString() {
		return "TerraceRoomSaveRequestDto [terrace_room_number=" + terrace_room_number + ", terrace_room_name="
				+ terrace_room_name + ", terrace_room_mop=" + terrace_room_mop + ", original_file_name="
				+ original_file_name + ", saved_file_path=" + saved_file_path + ", saved_file_name=" + saved_file_name
				+ ", shared_file_path=" + shared_file_path + ", shared_file_name=" + shared_file_name + ", create_date="
				+ create_date + ", member=" + member + "]";
	}
	
}
