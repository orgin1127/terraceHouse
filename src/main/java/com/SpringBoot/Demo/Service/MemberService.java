package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.Member.MemberRepository;
import com.SpringBoot.Demo.dto.MemberMainResponseDto;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	@Transactional
	public Long save(MemberSaveRequestDto dto){
		return memberRepository.save(dto.toEntity()).getMember_number();
	}
	
	
	@Transactional(readOnly = true)
	public List<MemberMainResponseDto> findAllDesc() { 
		return memberRepository.findAllDesc()
					.map(MemberMainResponseDto::new)
					.collect(Collectors.toList()); 
	}
	
	@Transactional(readOnly = true)
	public Member findById(String memberid) {
		return memberRepository.findById(memberid);
	}
}
