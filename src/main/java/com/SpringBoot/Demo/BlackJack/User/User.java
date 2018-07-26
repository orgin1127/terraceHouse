package com.SpringBoot.Demo.BlackJack.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private String hand;

	@Override
	public String toString() {
		return "User [hand=" + hand + "]";
	}

}
