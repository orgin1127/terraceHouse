package com.SpringBoot.Demo.Service;

import org.springframework.stereotype.Service;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerraceRepository;
import com.SpringBoot.Demo.dto.RegularTerraceSaveRequestDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegularTerraceRoomService {
	
	private RegularTerraceRepository regularTerraceRepository;
	
	public Long saveRegularTerrace(RegularTerraceSaveRequestDto dto){
		return regularTerraceRepository.save(dto.toEntity()).getRegular_terrace_number();
	}
}
