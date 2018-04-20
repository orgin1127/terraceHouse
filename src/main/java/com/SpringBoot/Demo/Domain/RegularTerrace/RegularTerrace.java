package com.SpringBoot.Demo.Domain.RegularTerrace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
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

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class RegularTerrace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long regular_terrace_number;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_member_number", foreignKey = @ForeignKey(name = "regular_terrace_fk"))
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
	public RegularTerrace(Member member, String create_date, String terrace_name, int terrace_mop, int terrace_freq, String terrace_date){
		this.member = member;
		this.create_date = create_date;
		this.terrace_name = terrace_name;
		this.terrace_mop = terrace_mop;
		this.terrace_freq = terrace_freq;
		this.terrace_date = terrace_date;
	}
}
