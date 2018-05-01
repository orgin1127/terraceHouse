package com.SpringBoot.Demo.Service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.Member.MemberRepository;
import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotification;
import com.SpringBoot.Demo.Domain.MemberNotification.MemberNotificationRepository;
import com.SpringBoot.Demo.dto.MemberNotificationSaveRequestDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberNotificationService {
	
	private MemberNotificationRepository memberNotificationRepository;
	
	@Transactional
	public void insertNotification(MemberNotificationSaveRequestDto dto){
		memberNotificationRepository.save(dto.toEntity());
	}
	
	@Transactional(readOnly = true)
	public ArrayList<MemberNotification> getNotificationList(String receiverID){
		ArrayList<MemberNotification> list = new ArrayList<>();
		list = memberNotificationRepository.getNotificationList(receiverID);
		return list;
	}
	
	@Transactional
	public void updateConfirmed(Long terrace_number, String receiver){
		memberNotificationRepository.updateNotificationConfirmed(terrace_number, receiver);
	}

}
