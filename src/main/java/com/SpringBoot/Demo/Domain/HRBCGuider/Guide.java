package com.SpringBoot.Demo.Domain.HRBCGuider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter 
public class Guide {

	int dataNumber;
	String exelFieldName;
	String exelFieldData;
	boolean isNecessary;
	String matchingGuide;
	int matchingGuide_simple; // 1: matching with HRBC Field , 2: new write , 3: making includable in HRBC's multi text type;
	String matchingGuide_detail; 
	String additionalGuide;
	String additionalGuide_detail;
	String FieldNameonHRBC;
	
	public Guide(int dataNumber, String exelFieldName, String exelFieldData, boolean isNecessary, String matchingGuide,
			int matchingGuide_simple, String matchingGuide_detail, String additionalGuide,
			String additionalGuide_detail, String fieldNameonHRBC) {
		super();
		this.dataNumber = dataNumber;
		this.exelFieldName = exelFieldName;
		this.exelFieldData = exelFieldData;
		this.isNecessary = isNecessary;
		this.matchingGuide = matchingGuide;
		this.matchingGuide_simple = matchingGuide_simple;
		this.matchingGuide_detail = matchingGuide_detail;
		this.additionalGuide = additionalGuide;
		this.additionalGuide_detail = additionalGuide_detail;
		FieldNameonHRBC = fieldNameonHRBC;
	}
	
	public Guide() {
		super();
	}

	@Override
	public String toString() {
		return "Guide [dataNumber=" + dataNumber + ", exelFieldName=" + exelFieldName + ", exelFieldData="
				+ exelFieldData + ", isNecessary=" + isNecessary + ", matchingGuide=" + matchingGuide
				+ ", matchingGuide_simple=" + matchingGuide_simple + ", matchingGuide_detail=" + matchingGuide_detail
				+ ", additionalGuide=" + additionalGuide + ", additionalGuide_detail=" + additionalGuide_detail
				+ ", FieldNameonHRBC=" + FieldNameonHRBC + "]";
	}

}
