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
	
	@Transactional(readOnly = true)
	public MemberSaveRequestDto findByIdAndPw(MemberSaveRequestDto m) {
		System.out.println(m.toString());
		Member mm = memberRepository.findByIdAndPw(m.getMemberid(), m.getMemberpw());
		MemberSaveRequestDto dto;
		if (mm != null) {
			dto = new MemberSaveRequestDto(
					mm.getMember_number(), mm.getMemberid(), mm.getMemberpw(), 
					mm.getMember_email(), mm.getMember_name(), mm.getMail_confirmed());	
		}
		else {
			System.out.println("mm null");
			dto = null;
		}
		
		return dto;
	}
	
	@Transactional
	public void updateMemberEmailConfirmed(String memberid){
		memberRepository.updateMemberEmailConfirmed(memberid);
	}

}
