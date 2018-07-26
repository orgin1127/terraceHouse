package com.SpringBoot.Demo.BlackJack.Card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	
	private String Number;
	private String Shape;
	
	@Override
	public String toString() {
		return "Card [Number=" + Number + ", Shape=" + Shape + "]";
	}

}
