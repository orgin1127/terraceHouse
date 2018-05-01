package com.SpringBoot.Demo.Service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.RegularTerraceMember.RegularTerraceMember;
import com.SpringBoot.Demo.Domain.RegularTerraceMember.RegularTerraceMemberRepository;
import com.SpringBoot.Demo.dto.RegularTerraceMemberSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegularTerraceMemberService {
	
	private RegularTerraceMemberRepository regularTerraceMemberRepository;
	
	@Transactional
	public Long regularTerrareMemberInsert(RegularTerraceMemberSaveRequestDto dto){
		return regularTerraceMemberRepository.save(dto.toEntity()).getRegular_terrace_member_number();
	}
	
	@Transactional
	public ArrayList<RegularTerraceMember> getAllMyRegularTerrace(Long member_number){
		ArrayList<RegularTerraceMember> list = new ArrayList<>();
		list = regularTerraceMemberRepository.findAllRegularTerraceByMemberNumber(member_number);
		return list;
	}
}
