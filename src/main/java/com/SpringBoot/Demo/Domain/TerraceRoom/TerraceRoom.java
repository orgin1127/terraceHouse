package com.SpringBoot.Demo.Domain.TerraceRoom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SpringBoot.Demo.Domain.Member.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TerraceRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long terrace_room_number;
	
	@Column(columnDefinition = "varchar(200)", nullable = false)
	private String terrace_room_name;
	
	@Column(nullable = false)
	private int terrace_room_mop;
	
	@Column(columnDefinition = "default non")
	private String original_file_name = "non";
	
	@Column(columnDefinition = "default non")
	private String saved_file_path = "non";
	
	@Column(columnDefinition = "defualt 'non'")
	private String saved_file_name = "non";
	
	@Column(columnDefinition = "default non")
	private String shared_file_path = "non";
	
	@Column(columnDefinition = "default 'non'")
	private String shared_file_name = "non";
	
	@Column(columnDefinition = "datetime default now()")
	private String create_date;
	
	@Column(columnDefinition = "default 'active'")
	private String terrace_room_active = "active";
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member_number")
	private Member member;

	public TerraceRoom() {
	}
	
	@Builder
	public TerraceRoom(String terrace_room_name, int terrace_room_mop, Member member) {
		this.terrace_room_name = terrace_room_name;
		this.terrace_room_mop = terrace_room_mop;
		this.member = member;
	}

	@Override
	public String toString() {
		return "TerraceRoom [terrace_room_number=" + terrace_room_number + ", terrace_room_name=" + terrace_room_name
				+ ", terrace_room_mop=" + terrace_room_mop + ", original_file_name=" + original_file_name
				+ ", saved_file_path=" + saved_file_path + ", saved_file_name=" + saved_file_name
				+ ", shared_file_path=" + shared_file_path + ", shared_file_name=" + shared_file_name + ", create_date="
				+ create_date + ", terrace_room_active=" + terrace_room_active + ", member=" + member + "]";
	}
}
