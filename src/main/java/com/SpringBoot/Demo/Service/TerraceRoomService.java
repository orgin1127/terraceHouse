package com.SpringBoot.Demo.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoomRepository;
import com.SpringBoot.Demo.dto.TerraceRoomSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TerraceRoomService {
	
	private TerraceRoomRepository terraceRoomRepository;
	
	@Transactional
	public void save(TerraceRoomSaveRequestDto dto){
		terraceRoomRepository.save(dto.toEntity());
	}
}
