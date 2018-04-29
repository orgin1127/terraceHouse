package com.SpringBoot.Demo.dto;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.RegularTerrace.RegularTerrace;

import lombok.Getter;

@Getter
public class RegularTerraceMainResponseDto {
	private Long regular_terrace_number;
	private Member member;
	private String create_date; 
	private String terrace_name;
	private int terrace_mop;
	private int terrace_freq;
	private String terrace_date;
	
	public RegularTerraceMainResponseDto(RegularTerrace rt){
		this.regular_terrace_number = rt.getRegular_terrace_number();
		this.member = rt.getMember();
		this.create_date = rt.getCreate_date();
		this.terrace_name = rt.getTerrace_name();
		this.terrace_mop = rt.getTerrace_mop();
		this.terrace_freq = rt.getTerrace_freq();
		this.terrace_date = rt.getCreate_date();
	}

	@Override
	public String toString() {
		return "RegularTerraceMainResponseDto [regular_terrace_number=" + regular_terrace_number + ", member=" + member
				+ ", create_date=" + create_date + ", terrace_name=" + terrace_name + ", terrace_mop=" + terrace_mop
				+ ", terrace_freq=" + terrace_freq + ", terrace_date=" + terrace_date + "]";
	}
	
}
