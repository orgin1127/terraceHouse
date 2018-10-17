package com.SpringBoot.Demo.WebRestController;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Demo.Domain.HRBCGuider.Guide;
import com.SpringBoot.Demo.Domain.HRBCGuider.HRBCField;
import com.SpringBoot.Demo.Domain.HRBCGuider.HRBCOption;
import com.SpringBoot.Demo.Domain.HRBCGuider.Utile_Excel;
import com.SpringBoot.Demo.Domain.HRBCGuider.Utile_HRBC;


@RestController
public class GuideController {
	
	@GetMapping("/guide/get")
	public ArrayList<Guide> getGuidance(ArrayList<Guide> inputList, String classification){
		
		
		//for compare , HRBC
		ArrayList<HRBCField> fieldList_HRBC = new Utile_HRBC().getField(classification);
		
		
		ArrayList<HRBCOption> optionList_HRBC = new Utile_HRBC().getOption();
		
		//for result 
		ArrayList<Guide> guideList = new ArrayList<Guide>();
	
		// make data or info for inputList : ananlyzing the data, or fields in inputlist and remember
		
		for(Guide a:inputList){
			
				// case for necessary field: 이름, 전화번호, 업종 >> 메소드 따로만들어서 처리하기 
				//공목 특성이 반영되어야 하는 항목.  
				if(a.isNecessary()){
					
					
					// if field is necessary , make info in this method and go for next;
					
					guideList.add(a);
					
					continue;
				}
				
				// case for special case; ?? 1차적으로 제외 , 2차 수정 가능하면 넣는게 어떨까 ; 
				if(a.getDataNumber() == 1){

					
					
					guideList.add(a);
					continue; 
				}
				
				// case for general case; 
				if(fieldList_HRBC.contains(a.getExelFieldName())){
				 		
						// 공목명 같고 데이터타입이 선택지가 아닌 경우 
				 		for(HRBCField f:fieldList_HRBC){
				 			if (a.getExelFieldName().equals(f.getFieldName())&&!f.getDataType().equals("selective")){
				 				a.setFieldNameonHRBC(f.getFieldName());
				 				
				 				// HRBC항목의 데이터형식 불러와서 숫자/통화형이면 숫자만인지 체크. 
				 					
				 				// HRBC 숫자형 <> Exel 문자형  인경우 안내메시지 띄어주고 매칭 종료 . 
				 				
				 				// 그리고, 매칭처리;
				 				
				 		// 공목명이 같고 데이터타입이 선택지인 경우 
				 			} else if(a.getExelFieldName().equals(f.getFieldName())&&f.getDataType().equals("selective")){
				 				
				 				//선택지 내용 비교 해야합니다. 
				 				
				 				
				 				// 아에 포함이 되는지 체크 엑셀>Hrbc false && Hrbc>excel true 인 경우에는 임포트 설정; 항목 발견해서 추가 안내 메세지; 
				 				
				 				
				 				// 위 두개 비교 및 부족한 값 추출
				 				
				 				
				 				//매칭 처리와 추가 임포트설정 또는 선택지 추가사항 안내 
				 			}
				 		}
				 		continue;
				 }
				 	
				 	
				// 공목명은 다르나, 내용이 같은 경우 -------------------------------------------------------------------------------------------
				// 엑셀의 정보 중에, HRBC 옵션내용과 일치하는 옵션데이타가 있는 지 확인 >> 이 선택지 옵션을쓰고 있는 항목 추출 해서 추천 ㅇ
				if(optionList_HRBC.contains(a.getExelFieldData())){
					
					//optionList의 몇번째 열이  임포트 대상의 데이터값을 포함하고 있는지 식별 >> 그 옵션의 엘리어스  
					
					String optionAlias = "임식값";
					
					
					// 옵션 엘리어스로 그 옵션을 쓰고 있는 항목명을 검사.
					String fieldName  = new Utile_HRBC().findField(optionAlias);
					
					a.setFieldNameonHRBC(fieldName);
					
				}
				
					// 신규작성의 경우-----------------------------------------------------------------------------------------------------------
					a.setMatchingGuide("新規作成");
					a.setMatchingGuide_simple(2);
					// HRBC 항목 이름 같게 합니다.  
					a.setFieldNameonHRBC(a.getExelFieldName());
					
					// 데이터 타입 추천 , 문자열을 보고 숫자만으로 숫자,통화 / 텍스트 복수형 추천
			
		
					
					// 선택지 타입인 경우, 추가해야할 선택지 내용을 안내.
					if(a.getMatchingGuide_detail().equals("選択肢型")){
						
					//OptionData recommendation
				
					a.setAdditionalGuide("‐　選択肢追加");
			
	
				}		
			}
		
		return guideList; 
	} 
}
