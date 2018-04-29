package com.SpringBoot.Demo.Domain.RegularTerrace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SpringBoot.Demo.Domain.Member.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RegularTerrace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long regular_terrace_number;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "create_member_number")
	private Member member;
	
	@Column(columnDefinition = "datetime default now()")
	private String create_date; 
	
	@Column(nullable = false)
	private String terrace_name;
	
	@Column(nullable = false)
	private int terrace_mop;
	
	@Column(nullable = false)
	private int terrace_freq;
	
	@Column(nullable = false)
	private String terrace_date;

	public RegularTerrace() {
	}
	
	@Builder
	public RegularTerrace(Member member, String terrace_name, int terrace_mop, int terrace_freq, String terrace_date){
		this.member = member;
		this.terrace_name = terrace_name;
		this.terrace_mop = terrace_mop;
		this.terrace_freq = terrace_freq;
		this.terrace_date = terrace_date;
	}

	@Override
	public String toString() {
		return "RegularTerrace [regular_terrace_number=" + regular_terrace_number + ", member=" + member
				+ ", create_date=" + create_date + ", terrace_name=" + terrace_name + ", terrace_mop=" + terrace_mop
				+ ", terrace_freq=" + terrace_freq + ", terrace_date=" + terrace_date + "]";
	}

	
}
