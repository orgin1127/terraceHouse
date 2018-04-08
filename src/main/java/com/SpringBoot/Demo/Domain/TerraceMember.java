package com.SpringBoot.Demo.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="TerraceMember")
public class TerraceMember {
	
	@Id
	@GeneratedValue
	private long memberNumber;
	
	@Column(nullable = false)
	private String memberID;
	
	@Column(nullable = false)
	private String memberPW;
	
	@Column(nullable = false)
	private String memberEmail;

	@Column(nullable = false)
	private String memberName;
	
	private String mailConfirmed;

	public TerraceMember() {
	}
	
	public TerraceMember(String memberID, String memberPW, String memberEmail, String memberName) {
		this.memberID = memberID;
		this.memberPW = memberPW;
		this.memberEmail = memberEmail;
		this.memberName = memberName;
	}
	
	@Override
	public String toString() {
		return "TerraceMember [memberNumber=" + memberNumber + ", memberID=" + memberID + ", memberPW=" + memberPW
				+ ", memberEmail=" + memberEmail + ", memberName=" + memberName + ", mailConfirmed=" + mailConfirmed
				+ "]";
	}


}
