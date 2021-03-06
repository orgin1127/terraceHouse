package com.SpringBoot.Demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.Member.MemberRepository;
import com.SpringBoot.Demo.dto.MemberMainResponseDto;
import com.SpringBoot.Demo.dto.MemberSaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
	public Member findByIdAndPw(MemberSaveRequestDto m) {
		System.out.println(m.toString());
		Member mm = memberRepository.findByIdAndPw(m.getMemberid(), m.getMemberpw());
		
		if (mm != null) {
		
		}
		else {
			System.out.println("mm null");
		}
		
		return mm;
	}
	
	@Transactional
	public void updateMemberEmailConfirmed(String memberid){
		int result = memberRepository.updateMemberEmailConfirmed(memberid);
		if (result == 1){
			System.out.println("업데이트 성공");
		}
		else {
			System.out.println("업데이트 실패");
		}
	}
	
}
