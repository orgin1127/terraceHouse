package com.SpringBoot.Demo.Service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotification;
import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotificationRepository;
import com.SpringBoot.Demo.dto.MemberNotificationSaveRequestDto;

@Service
public class MemberNotificationService {
	
	private MemberNotificationRepository memberNotificationRepository;
	
	public void insertNotification(MemberNotificationSaveRequestDto dto){
		memberNotificationRepository.save(dto.toEntity());
	}
	
	public ArrayList<MemberNotification> getNotificationList(String receiverID){
		ArrayList<MemberNotification> list = new ArrayList<>();
		list = memberNotificationRepository.getNotificationList(receiverID);
		return list;
	}

}
