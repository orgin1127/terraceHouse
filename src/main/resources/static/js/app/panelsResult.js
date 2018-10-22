/**
 * 
 */
var customResultList;
$(document).ready(function() {
	$.ajax({
		url: 'getList',
		type: 'post',
		success: function(result) {
			customResultList = result;
			
			
			//결과값의 구조 ArrayList 안에 String[] 이 각각 들어가 있음
			//ex) list.get(0) -> String[] srt 
			//각 배열은 길이가 다름 ex) 0번 length 3, 1번 length 4, 2번 length 5 등등
			//그렇기 때문에 무조건 6번 돌리면서 td를 찍어낸다(5번과 6번은 같은 5열에 인쇄
			var i;
			var str="";
			str+='<table class="col-md-12 type04">';
			$.each(customResultList, function(index, value) {				
				str+='<tr>';
			
				for(i = 0; i<5; i++){
					
					
					if(value[i] != null){
						
						if(i == 0){
							str+='<td class="col-md-2 tableTd1">'+value[i]+'</td>';
						}else if(i == 1){
							str+='<td class="col-md-2 tableTd2">'+value[i]+'</td>';
						}else if(i == 2){
							str+='<td class="col-md-2 tableTd3">'+value[i]+'</td>';
						}else if(i == 3){
							str+='<td class="col-md-2 tableTd4">'+value[i]+'</td>';
						}else if(i == 4){
							str+='<td class="col-md-2 tableTd5">'+value[i]+'</td>';
						}
					
					} else if(value[i] == null) {
						str+='<td class="col-md-3"> </td>';
					}	
					
				}
				
				str+='</tr>';						
			});//each끝
			
			str+='</table>';				
			$('#resultTable').html(str);
			
		}//success끝
	
	})//ajax끝

});

