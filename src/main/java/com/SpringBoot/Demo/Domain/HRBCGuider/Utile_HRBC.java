package com.SpringBoot.Demo.Domain.HRBCGuider;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;


@Repository
public class Utile_HRBC {

	// Hrbc 전체 필드 가져오기 
	public ArrayList<HRBCField> getField(String classification, String token){ // classification is job or resume etc
		
		ArrayList<HRBCField> fieldList = new ArrayList<HRBCField>();
		
		String url = "http://hrbc1-api.localvm/v1/field?partition=1421&resource=17&count=200&active=-1&start=0";
		
		try {
			Document document =Jsoup.connect(url)
												.header("Content-Type","application/xml; charset=UTF-8")
												.header("X-porters-hrbc-oauth-token", token)
												.parser(Parser.xmlParser()).timeout(3000).get();
					
			Elements rawData = document.getElementsByTag("item");
		
			for(Element e:rawData){
				
				String name = e.getElementsByTag("field.p_name").text();
				//int dataType = Integer.parseInt(e.getElementsByTag("field.p_type").text());
				String dataType = e.getElementsByTag("field.p_type").text();
				
				HRBCField field = new HRBCField();
				
				field.setDataType(dataType);
				field.setFieldName(name);
				
				fieldList.add(field);	
			}
		
		
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fieldList;
	}
	
	// Hrbc 전체 옵션 및 옵션벨류 가져오기 
	public ArrayList<HRBCOption> getOption(String token){
		
		ArrayList<HRBCOption> optionList = new ArrayList<HRBCOption>();
		String url = "http://hrbc1-api.localvm/v1/option?partition=1421&level=0&enabled=-1";
	
		try {
			Document document =Jsoup.connect(url)
												.header("Content-Type","application/xml; charset=UTF-8")
												.header("X-porters-hrbc-oauth-token", token)
												.parser(Parser.xmlParser()).timeout(3000).get();
		
			
			Elements rawData = document.getElementsByTag("item");
			Elements rawData1 = rawData.parents();
			Elements rawData2 = document.children();// 바로 밑의 칠드런 
		
			for(Element e:rawData){
			
				String name = e.getElementsByTag("option.p_name").text();
				
				//Element e_under = e.getElementById("item");
				HRBCOption option = new HRBCOption();
				
				option.setOptionName(name);
				
				optionList.add(option);	
			}
		
		
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return optionList;
	}
	
	
	// Hrbc 엘리어스에 해당되는 옵션    을  사용하는 필드 값 찾기 
	public String findField(String optionAias, String token){
		
		return "";
	}
	
	
	// 필드에서 사용하고 있는 옵션 선택지 가져오기
	public ArrayList<String> getOptionList(String fieldName, String token){
		
		ArrayList<String> optionList = new ArrayList<String>();
		
		
		return optionList; 
	}
	
}
