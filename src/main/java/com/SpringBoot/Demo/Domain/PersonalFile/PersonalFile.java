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
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "terrace_room_number")
	private JoinRoomMember terrace_room_number;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number")
	private Member member_number;

	public PersonalFile() {
	}
	
	@Builder
	public PersonalFile(String saved_personal_file_path, JoinRoomMember jrm, Member m){
		this.saved_personal_file_path = saved_personal_file_path;
		this.terrace_room_number = jrm;
		this.member_number = m;
	}

	@Override
	public String toString() {
		return "PersonalFile [file_number=" + file_number + ", saved_personal_file_path=" + saved_personal_file_path
				+ ", terrace_room_number=" + terrace_room_number + ", member_number=" + member_number + "]";
	}
	
}
