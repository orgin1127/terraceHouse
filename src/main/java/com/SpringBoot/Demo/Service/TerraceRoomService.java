package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.Member.MemberRepository;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoomRepository;
import com.SpringBoot.Demo.dto.TerraceRoomMainResponseDto;
import com.SpringBoot.Demo.dto.TerraceRoomSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TerraceRoomService {
	
	private TerraceRoomRepository terraceRoomRepository;
	private MemberRepository memberRepository;
	
	@Transactional
	public Long save(TerraceRoomSaveRequestDto dto, Member m){
		dto.setMember(memberRepository.findOne(m.getMember_number()));
		return terraceRoomRepository.save(dto.toEntity()).getTerrace_room_number();
	}
	
	@Transactional(readOnly = true)
	public List<TerraceRoomMainResponseDto> findAllAsc(){
		return terraceRoomRepository.findAllAsc()
				.map(TerraceRoomMainResponseDto::new)
				.collect(Collectors.toList()); 
	}
	
	@Transactional(readOnly = true)
	public TerraceRoom findOneByTerraceRoomNumber(Long terrace_room_number){
		return terraceRoomRepository.findOne(terrace_room_number);
	}

}
