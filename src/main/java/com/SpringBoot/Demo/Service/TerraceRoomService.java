package com.SpringBoot.Demo.Service;

import java.util.ArrayList;
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
	
	@Transactional
	public int updateTerraceRoomInPDF(Long terrace_room_number, String original_file_name
									,String saved_file_path,String saved_file_name 
									,String shared_file_path,String shared_file_name){
		System.out.println("update작동");
		int result = terraceRoomRepository.updateTerraceRoomInPDF(terrace_room_number, original_file_name
																, saved_file_path, saved_file_name
																, shared_file_path, shared_file_name);
		return result;
	}
	
	@Transactional
	public void save(TerraceRoomSaveRequestDto dto){
		terraceRoomRepository.save(dto.toEntity());
	}
	
	@Transactional
	public void endOfTerraceRoom(Long terrace_room_number){
		terraceRoomRepository.updateTerraceRoomInActive(terrace_room_number);
	}
	
	@Transactional(readOnly = true)
	public List<TerraceRoomMainResponseDto> findAllAsc(){
		return terraceRoomRepository.findAllAsc()
				.map(TerraceRoomMainResponseDto::new)
				.collect(Collectors.toList()); 
	}
	
	@Transactional(readOnly = true)
	public List<TerraceRoomMainResponseDto> findAllByTerraceActive(){
		return terraceRoomRepository.findAllByTerraceActive()
				.map(TerraceRoomMainResponseDto::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ArrayList<TerraceRoom> findAllByInputTitle(String inputTitle){
		return  terraceRoomRepository.findAllByTitle(inputTitle);
	}
	
	@Transactional(readOnly = true)
	public TerraceRoom findOneByTerraceRoomNumber(Long terrace_room_number){
		return terraceRoomRepository.findOneByTerraceRoomNumber(terrace_room_number);
	}

}
