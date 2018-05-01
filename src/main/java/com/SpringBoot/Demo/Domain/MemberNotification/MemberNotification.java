package com.SpringBoot.Demo.Domain.MemberNotification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberNotification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long notification_number;
	
	@Column(nullable = false)
	private String sender;
	
	@Column(nullable = false)
	private String receiver;
	
	@Column(nullable = false)
	private String notification_type;
	
	@Column(nullable = false)
	private String notification_content;
	
	@Column(columnDefinition = "varchar(10) default 'N'")
	private String confirmed = "n";
	
	public MemberNotification() {
		
	}
	
	@Builder
	public MemberNotification(String sender, String receiver, String notification_type, String notification_content){
		this.sender = sender;
		this.receiver = receiver;
		this.notification_type = notification_type;
		this.notification_content = notification_content;
	}

	@Override
	public String toString() {
		return "MemberNotification [notification_number=" + notification_number + ", sender=" + sender + ", receiver="
				+ receiver + ", notification_type=" + notification_type + ", notification_content="
				+ notification_content + ", confirmed=" + confirmed + "]";
	}

	
	
}
