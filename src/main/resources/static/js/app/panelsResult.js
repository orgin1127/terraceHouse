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
			$.each(customResultList, function(index, value) {
				var i;
				for(i = 0; i<=5; i++){
					//<td>
						//value[i]
					//</td>
				}
			});
		}
	})

});

