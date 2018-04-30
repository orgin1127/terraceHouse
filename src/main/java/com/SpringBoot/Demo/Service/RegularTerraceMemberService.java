package com.SpringBoot.Demo.Service;

import org.springframework.stereotype.Service;

import com.SpringBoot.Demo.Domain.RegularTerraceMember.RegularTerraceMemberRepository;
import com.SpringBoot.Demo.dto.RegularTerraceMemberSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegularTerraceMemberService {
	
	private RegularTerraceMemberRepository regularTerraceMemberRepository;
	
	public Long regularTerrareMemberInsert(RegularTerraceMemberSaveRequestDto dto){
		return regularTerraceMemberRepository.save(dto.toEntity()).getRegular_terrace_member_number();
	}
	
	
}
