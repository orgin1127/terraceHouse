package com.SpringBoot.Demo.dto;



import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerrace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegularTerraceSaveRequestDto {

	private Long regular_terrace_number;
	private Member member;
	private String create_date;
	private String terrace_name;
	private int terrace_mop;
	private int terrace_freq;
	private String terrace_date;
	
	@Builder
	public RegularTerraceSaveRequestDto(Member member,String terrace_name,int terrace_mop,int terrace_freq,String terrace_date) {
		this.member = member;
		this.terrace_name = terrace_name;
		this.terrace_mop = terrace_mop;
		this.terrace_freq = terrace_freq;
		this.terrace_date = terrace_date;
	}
	
	public RegularTerrace toEntity(){
		return RegularTerrace.builder()
								.member(member)
								.terrace_name(terrace_name)
								.terrace_mop(terrace_mop)
								.terrace_freq(terrace_freq)
								.terrace_date(terrace_date)
								.build();
	}

	@Override
	public String toString() {
		return "RegularTerraceSaveRequestDto [regular_terrace_number=" + regular_terrace_number + ", member=" + member
				+ ", create_date=" + create_date + ", terrace_name=" + terrace_name + ", terrace_mop=" + terrace_mop
				+ ", terrace_freq=" + terrace_freq + ", terrace_date=" + terrace_date + "]";
	}

}
