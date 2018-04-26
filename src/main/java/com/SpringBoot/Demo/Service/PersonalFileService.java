package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.PersonalFile.PersonalFileRepository;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;
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
	public List<PersonalFileMainResponseDto> findAllByMemberNumber(Member member_number){
		return personalFileRepository.findAllByMemberNumber(member_number)
										.map(PersonalFileMainResponseDto::new)
										.collect(Collectors.toList());
	}
	
	@Transactional
	public int updatePersonalFile(String saved_personal_file_path, String saved_personal_file_name,TerraceRoom terrace_room_number, Member member_number) {
		return personalFileRepository
				.updateByPersonalFileNumber(saved_personal_file_path
											, saved_personal_file_name
											, terrace_room_number
											, member_number);
	}
}
