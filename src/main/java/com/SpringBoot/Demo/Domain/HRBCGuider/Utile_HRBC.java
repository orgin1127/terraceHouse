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
	public ArrayList<HRBCField> getField(String classification){ // classification is job or resume etc
		
		ArrayList<HRBCField> fieldList = new ArrayList<HRBCField>();
		
		
		
		return fieldList;
	}
	
	// Hrbc 전체 옵션 및 옵션벨류 가져오기 
	public ArrayList<HRBCOption> getOption(){
		
		ArrayList<HRBCOption> optionList = new ArrayList<HRBCOption>();
		
		
		//
		return optionList;
	}
	
	
	// Hrbc 엘리어스에 해당되는 옵션    을  사용하는 필드 값 찾기 
	public String findField(String optionAias){
		
		
		return "";
	}
	
	
	// 필드에서 사용하고 있는 옵션 선택지 가져오기
	public ArrayList<String> getOptionList(String fieldName){
		
		ArrayList<String> optionList = new ArrayList<String>();
		
		
		return optionList; 
	}
	
public String connectOptionData(String url, String token){
		
		Connection.Response response;
		String btnk= "not entered";
		
		try {

			/*			response = ((Connection) Jsoup.connect(url)
			        .method(Connection.Method.GET)
			        .header("X-porters-hrbc-oauth-token", token)
			        .parser(Parser.xmlParser()).timeout(3000).get())


			        //.header("Content-Type","application/xml; charset=UTF-8")
			        .header("Access-Control-Allow-Origin" , "*")
			        .header("Access-Control-Allow-Methods" , "GET, POST, OPTIONS")
			        .header("Access-Control-Max-Age" , "3600")
			        .header("Access-Control-Allow-Headers" , "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
			        
			        .execute();
		
		Document document = response.parse();
		
*/		
		
			Document document =Jsoup.connect(url)
					.header("Content-Type","application/xml; charset=UTF-8")
			        .header("X-porters-hrbc-oauth-token", token)
			        .parser(Parser.xmlParser()).timeout(3000).get() 
					;
		
		
		/*String str  = document.html();
		
		System.out.println(str);*/
		
		Elements el  = document.getElementsByTag("option.p_name");
		
		ArrayList<String> optionList = new ArrayList<String>();
		
		for(Element e: el){
			
		String a = e.text();
			
		optionList.add(a);	
			
		}
		
		System.out.println("---------------------------------Optiondata check ----------------------------------");
		
		for(String a: optionList){
		System.out.println("optionData: "+ a);
		}
		
		//Element codeElement = googleDocument.select("Code").first();
		
		//btnk = codeElement.text();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return btnk;
	}
	
	
	
}
