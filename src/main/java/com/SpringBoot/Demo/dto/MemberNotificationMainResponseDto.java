package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotification;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

import lombok.Getter;

@Getter
public class MemberNotificationMainResponseDto {
	
	private Long notification_number;
	private String sender;
	private String receiver;
	private String notification_type;
	private String notification_content;
	private String terrace_name;
	private Long terrace_number;
	private String confirmed;
	
	public MemberNotificationMainResponseDto(MemberNotification mn){
		this.notification_number = mn.getNotification_number();
		this.sender = mn.getSender();
		this.receiver = mn.getReceiver();
		this.notification_type = mn.getNotification_type();
		this.notification_content = mn.getNotification_content();
		this.terrace_name = mn.getTerrace_name();
		this.terrace_number = mn.getTerrace_number();
		this.confirmed = mn.getConfirmed();
	}

	@Override
	public String toString() {
		return "MemberNotificationMainResponseDto [notification_number=" + notification_number + ", sender=" + sender
				+ ", receiver=" + receiver + ", notification_type=" + notification_type + ", notification_content="
				+ notification_content + ", terrace_name=" + terrace_name + ", terrace_number=" + terrace_number
				+ ", confirmed=" + confirmed + "]";
	}

	
	
}
