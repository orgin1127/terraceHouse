package com.SpringBoot.Demo.Domain.HRBCGuider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HRBCCustom {
	
	private String itemName;
	private String itemData;
	private String matchingResult;
	
	@Override
	public String toString() {
		return "HRBCCustom [itemName=" + itemName + ", itemData=" + itemData + ", matchingResult=" + matchingResult
				+ "]";
	}
	
}
