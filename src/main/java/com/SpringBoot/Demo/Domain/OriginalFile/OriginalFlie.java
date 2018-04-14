package com.SpringBoot.Demo.Domain.OriginalFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OriginalFlie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long file_number;
	
	@Column(columnDefinition = "varchar(500)", nullable = false)
	private String original_file_name;
	
	@Column(columnDefinition = "varchar(1000)", nullable = false)
	private String saved_file_name;

}
