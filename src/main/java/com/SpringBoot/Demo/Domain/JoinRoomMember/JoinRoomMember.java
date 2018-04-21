package com.SpringBoot.Demo.Domain.JoinRoomMember;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JoinRoomMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long join_number;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "join_member_number")
	private Member join_member_number;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "join_terrace_room_number")
	private TerraceRoom join_terrace_room_number;

	public JoinRoomMember() {
	}
	
	@Builder
	public JoinRoomMember(Member member, TerraceRoom terraceRoom) {
		this.join_member_number = member;
		this.join_terrace_room_number = terraceRoom;
	}
		
}
