package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberNotificationSaveRequestDto {
	
	private Long notification_number;
	private String sender;
	private String receiver;
	private String notification_type;
	private String notification_content;
	private String confirmed;
	
	@Builder
	public MemberNotificationSaveRequestDto(String sender, String receiver, String notification_type, String notification_content){
		this.sender = sender;
		this.receiver = receiver;
		this.notification_type = notification_type;
		this.notification_content = notification_content;
	}
	
	public MemberNotification toEntity(){
		return MemberNotification.builder()
								.sender(sender)
								.receiver(receiver)
								.notification_type(notification_type)
								.notification_content(notification_content)
								.build();
	}

}
