package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMember;
import com.SpringBoot.Demo.Domain.JoinRoomMember.JoinRoomMemberRepository;
import com.SpringBoot.Demo.dto.JoinRoomMemberMainResponseDto;
import com.SpringBoot.Demo.dto.JoinRoomMemberSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JoinRoomMemberService {
	
	private JoinRoomMemberRepository joinRoomMemberRepository;
	
	@Transactional
	public Long save (JoinRoomMemberSaveRequestDto dto) {
		return joinRoomMemberRepository.save(dto.toEntity()).getJoin_number();
	}
	
	@Transactional(readOnly = true)
	public List<JoinRoomMemberMainResponseDto> findOneByMemberNumber(Long member_number){
		return joinRoomMemberRepository.findOneByMemberNumber(member_number)
										.map(JoinRoomMemberMainResponseDto::new)
										.collect(Collectors.toList()); 
	}
}