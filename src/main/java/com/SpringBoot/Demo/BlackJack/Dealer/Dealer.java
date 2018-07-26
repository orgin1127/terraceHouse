package com.SpringBoot.Demo.BlackJack.Dealer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dealer {
	
	private String hand;

	@Override
	public String toString() {
		return "Dealer [hand=" + hand + "]";
	}

	
}
