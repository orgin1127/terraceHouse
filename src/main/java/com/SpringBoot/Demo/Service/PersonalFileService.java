package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.PersonalFile.PersonalFileRepository;
import com.SpringBoot.Demo.dto.PersonalFileMainResponseDto;
import com.SpringBoot.Demo.dto.PersonalFileSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PersonalFileService {
	
	private PersonalFileRepository personalFileRepository;
	
	@Transactional
	public Long save(PersonalFileSaveRequestDto dto){
		return personalFileRepository.save(dto.toEntity()).getFile_number();
	}
	
	
	@Transactional(readOnly = true)
	public List<PersonalFileMainResponseDto> findAllByMemberNumber(Long member_number){
		return personalFileRepository.findAllByMemberNumber(member_number)
										.map(PersonalFileMainResponseDto::new)
										.collect(Collectors.toList());
	}
}
