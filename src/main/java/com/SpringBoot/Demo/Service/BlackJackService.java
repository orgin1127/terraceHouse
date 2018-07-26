package com.SpringBoot.Demo.Service;

import java.util.ArrayList;
import java.util.Collections;

import org.mozilla.javascript.ast.ArrayLiteral;

import com.SpringBoot.Demo.BlackJack.Card.Card;

public class BlackJackService {
	
	public ArrayList<String> creatCardList(){
		
		ArrayList<String> cardList = new ArrayList<>();
	
		String[] number = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		String[] shape = {"♠", "♥", "♦", "♣"};
		
		for(int index = 0; index<shape.length; index++){
			for(int index2 = 0; index2<number.length; index2++){
				cardList.add(shape[index]+" "+number[index2]);
			}
		}
		
		Collections.shuffle(cardList);
		
		return cardList;
	}

}
