package com.SpringBoot.Demo.Domain.PersonalFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PersonalFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long file_number;
	
	@Column(columnDefinition = "default non")
	private String saved_personal_file_path = "non";
	
	@Column(columnDefinition = "default non")
	private String saved_personal_file_name = "non";
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "terrace_room_number")
	private TerraceRoom terrace_room_number;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number")
	private Member member_number;

	public PersonalFile() {
	}
	
	@Builder
	public PersonalFile( TerraceRoom tr, Member m){
		this.terrace_room_number = tr;
		this.member_number = m;
	}

	@Override
	public String toString() {
		return "PersonalFile [file_number=" + file_number + ", saved_personal_file_path=" + saved_personal_file_path
				+ ", terrace_room_number=" + terrace_room_number + ", member_number=" + member_number + "]";
	}
	
}
