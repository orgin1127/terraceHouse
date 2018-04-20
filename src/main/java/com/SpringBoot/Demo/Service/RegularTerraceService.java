package com.SpringBoot.Demo.Service;

import org.springframework.stereotype.Service;

import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerraceRepository;
import com.SpringBoot.Demo.dto.RegularTerraceSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegularTerraceService {
	
	private RegularTerraceRepository regularTerraceRepository;
	
	public Long save(RegularTerraceSaveRequestDto dto){
		return regularTerraceRepository.save(dto.toEntity()).getMember().getMember_number();
	}
}
