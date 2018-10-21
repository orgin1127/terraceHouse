/**
 * 
 */

var resultContainsAll;
var resultMap;

function uploadExcel() {
	var form = document.getElementById('excelUpload');
	var formData = new FormData(form);
	var itemsList = [];
	var dataList = [];
	$.ajax({
		url: 'uploadExcel',
		type: 'post',
		enctype: 'multipart/form-data',
        data: formData,
        dataType: 'json',
        //contentType:'application/json; charset=utf-8',
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        success: function(result) {
        	
        	
        	itemList = result.itemList;
        	dataList = result.dataList;
        	
        	//console.log(JSON.stringify(result.itemList));
        	
        	var str ='<form action="itemChange" method="post">'; 
    		str+='<table class = "type05">';
    		str+='<tr><td class="itemcheckboxes"><label class="container">';
    		str+='<input type="checkbox" checked="checked" id="allItems">';
    		str+='<span class="checkmark"></span></label></td>';
    		str+='<th class="allItems">Select All</th></tr>';
			$.each(itemList, function(index,values) {
				console.log("항목 : " + values);
				str+='<tr> <td class="itemcheckboxes">';
				str+='<label class="container">';
				str+='<input type="checkbox" checked="checked" id="item'+index+'" value="'+values+'" name="excelItems">';	
				str+='<span class="checkmark"></span></label></td><td>';
				str+=values;
				str+='</td></tr>';
			});
			str+='</table>';
			str+='<div class = "rightFloat">';
			str+='<input type="button" class="buttonCss" id="itemSelectBtn" value="Submit"></form>';
			str+='</div>';
			
			$('#itemListCheckArea').html(str);
			
			$("#allItems").click(function(){ 
				if($("#allItems").is(":checked")) { 
					$("input[name='excelItems']").prop("checked", true); 
				} else { 
					$("input[name='excelItems']").prop("checked", false); 
				} 
			});
			
			$('#itemSelectBtn').on('click', confirmCheck);
			
			
		},
		error: function (e) {
			console.log(e);
		}
	});
}

function confirmCheck() {
	var checkedItemList = [];
	var rownum = 0;
		
	$('input:checkbox[name="excelItems"]').each(function() {
		if ($(this).is(":checked") == true) {
			checkedItemList.push($(this).val());
		}
	});
	console.log(checkedItemList.length);
	
	$.ajax({
		url: 'confirmCheck',
		type: 'post',
		data: {'checkedItemList' : checkedItemList},
		dataType: 'json',
		traditional: true,
		success: function(result) {
			var str= '<table class="col-md-12 type04">';
			var fieldListOpts = result.fieldList;
			resultMap = result;
			resultContainsAll = result.optionList;
			
			
			$.each(result.customList, function(index, values) {

				str+='<tr>';
				//entry sample 1 파일에서 온 항목명
				str+='<td class="col-md-2 tableTd1" id="tableTd1_'+rownum+'">'+values.itemName+'</td>';
				//data sample 1 파일에서 온 데이터 샘플
				str+='<td class="col-md-2 tableTd2" id="tableTd2_'+rownum+'">'
				str+='<textarea readonly="readonly" cols="34" rows="1">'+values.itemData+'</textarea>';
				str+='</td>';
				//matching data sample 1 HRBC와 매칭 결과
				str+='<td class="col-md-3 tableTd3" id="tableTd3_'+rownum+'">';
				if(values.matchingResult=='신규작성'){
					str+='<b>新規作成</b>&nbsp&nbsp&nbsp&nbsp';
					str+='<select id="td3Select_'+rownum+'" class="createNewField" matchingResult="0"';
					str+='onchange=fourthColOpt(this.options[this.selectedIndex].value,'+rownum+');';
					str+='fifthColOptList(this.options[this.selectedIndex].value,'+rownum+');>';
					str+='<option>選択してください▼</option>';
					str+='<option value="参照型">参照型</option>';
					str+='<option value="テキスト1行型">テキスト1行型</option>';
					str+='<option value="テキスト複数行型">テキスト複数行型</option>';
					str+='<option value="年月日型">年月日型</option>';
					str+='<option value="年月日時分型">年月日時分型</option>';
					str+='<option value="年齢型">年齢型</option>';
					str+='<option value="数値型">数値型</option>';
					str+='<option value="通貨型">通貨型</option>';
					str+='<option value="電話番号型">電話番号型</option>';
					str+='<option value="URL型">URL型</option>';
					str+='<option value="メール型">メール型</option>';
					str+='<option value="画像型">画像型</option>';
					str+='<option value="選択肢型">選択肢型</option>';
					str+='</select>';
				} else {
					str+='<b>HRBC項目</b>&nbsp&nbsp';
					str+='<select id="td3Select_'+rownum+'" onchange=fourthColHRBC(this.options[this.selectedIndex].value,'+rownum+') matchingResult="1">';
					str+='<option value="'+values.matchingResult+'">'+values.matchingResult+'</option>';
					$.each(fieldListOpts, function(index, values){		
						str+='<option value="'+values.fieldName+'">'+values.fieldName+'</option>';				
					});
					str+='<option value="createNew">◆ 新規作成</option>'
					str+='</option></select>'
						
					//$('#fieldListArea').append(optStr);
				}				
				str+='</td>';
				//setting data sample 1 데이터 형식 고르기
				str+='<td class="col-md-3 tableTd4" id="tableTd4'+rownum+'" rowNumber="'+rownum+'" ></td>';
				//option data sample 1 추가 형식 고르기
				str+='<td class="col-md-3 tableTd5" id="tableTd5'+rownum+'" rowNumber="'+rownum+'" ></td>';	
				str+='</tr>';
				
				rownum++;
			
			});
			str+='</table>';
			str+='<div class = "rightFloat">';
			str+='<input type="button" class="buttonCss" value = "Next" onclick="confirmCustom()">';
			str+='</div>';
			
			$('#customiseGuideArea').html(str);	

		},
		error: function(e) {
			console.log(e);
		}
	});
	
	
}

function fourthColOpt(value, number){		
	
	var str = "";
	if(value=="選択肢型"){
		str+='<select id="td4Select_'+number+'" matchingResult="0">'; // onchange="fifthColOptList('+number+')"
		str+='<option>選択肢型▼</option>';
		str+='<option value="チェックボックス形式">チェックボックス形式</option>';
		str+='<option value="ラジオボタン形式">ラジオボタン形式</option>';
		str+='<option value="ドロップダウン形式">ドロップダウン形式</option>';
		str+='<option value="サーチボックス形式">サーチボックス形式</option>';				
		str+='</select>';
	} else {
		
	}

	$('#tableTd4'+number).html(str);
}//fourthColOpt 끝

function fifthColOptList(value, number){
	if(value=="選択肢型"){
		var str='<select id="td5Select_'+number+'" matchingResult="0">';			
		str+='<option>選択肢マスタ▼</option>';
		$.each(resultContainsAll, function(index, values) {
			str+='<option>'+values.optionName+'</option>';	
		});	
		str+='<option>◆ 選択肢マスタ新規作成</option>';
		str+='</select>';
	} 
	
	else {
		
	}
	$('#tableTd5'+number).html(str);
}

function fourthColHRBC(value, number){

	var str = "";
	if(value=="createNew"){
		str+='<b>新規作成</b>&nbsp&nbsp&nbsp&nbsp';
		str+='<select id="td4Select_'+number+'" matchingResult="1" class="createNewField" onchange=fifthColOptCreate(this.options[this.selectedIndex].value,'+number+')>';
		str+='<option>選択してください▼</option>';
		str+='<option value="参照型">参照型</option>';
		str+='<option value="テキスト1行型">テキスト1行型</option>';
		str+='<option value="テキスト複数行型">テキスト複数行型</option>';
		str+='<option value="年月日型">年月日型</option>';
		str+='<option value="年月日時分型">年月日時分型</option>';
		str+='<option value="年齢型">年齢型</option>';
		str+='<option value="数値型">数値型</option>';
		str+='<option value="通貨型">通貨型</option>';
		str+='<option value="電話番号型">電話番号型</option>';
		str+='<option value="URL型">URL型</option>';
		str+='<option value="メール型">メール型</option>';
		str+='<option value="画像型">画像型</option>';
		str+='<option value="選択肢型">選択肢型</option>';
		str+='</select>';
	} 
	
	else {
		
	}

	$('#tableTd4'+number).html(str);
}//fourthColHRBC 끝

function fifthColOptCreate(value, number){	
	var str = "";
	if(value=="選択肢型"){
		str+='<select id="td5SelectOpt_'+number+'" matchingResult="1">';
		str+='<option>選択肢型▼</option>';
		str+='<option value="チェックボックス形式">チェックボックス形式</option>';
		str+='<option value="ラジオボタン形式">ラジオボタン形式</option>';
		str+='<option value="ドロップダウン形式">ドロップダウン形式</option>';
		str+='<option value="サーチボックス形式">サーチボックス形式</option>';				
		str+='</select>';
		str+='<br>';
		str+='<select id="td5SelectOptSch_'+number+'" matchingResult="1">';			
		str+='<option>選択肢マスタ▼</option>';
		$.each(resultContainsAll, function(index, values) {
			str+='<option>'+values.optionName+'</option>';	
		});	
		str+='<option>選択肢マスタ新規作成</option>';
		str+='</select>';
	} 
	else {
		
	}
	
	$('#tableTd5'+number).html(str);
}

function confirmCustom() {
	console.log('check : ' + 'yay');
	
	var itemList = resultMap.customList;
	var i;
	var count = 0;
	var customResultList = [];
	
	var td1 = "tableTd1_";
	var td2 = "tableTd2_";
	var td3 = "td3Select_";
	var td4 = "td4Select_";
	var td5 = "td5Select_";
	var td5_1= "td5SelectOpt_";
	var td5_2 = "td5SelectOptSch_";
	
	var selOp = [td1, td2, td3, td4, td5, td5_1, td5_2];
	
	
	$.each(itemList, function(index, values) {
		var customResult = [];
		for (i = 0; i<7; i++){
			var ex = selOp[i] + count;
			
			var ids = '#'+ex;
			
			if (i>=2) {
				//3열 매칭 여부 확인 값 추출
				var findMatchingOp = '#'+selOp[i]+count;
				var matchingResultValue = $(findMatchingOp).attr('matchingresult');
				
				if(typeof matchingResultValue == 'undefined'){
					console.log("undefined 감지");
					continue;
				}
				
				console.log('mrv : ' + matchingResultValue+", i : " + i);
				//3열 선택된 값 추출
				var selectedValue = $(ids).find(":selected").text();
				
				//4열 이후 선택 값 추출
				var findSelectedValue = findMatchingOp +' option:selected'
				var value = $(findSelectedValue).text();
				
				//3열 
				if (i == 2) {
					//매칭 됐을때
					if (matchingResultValue == 1) {
						selectedValue = 'HRBC項目-'+selectedValue;
					}
					//매칭 안됐을때
					else {
						if (selectedValue == '選択してください▼'){
							selectedValue = '新規作成-';
						}
						else {						
							selectedValue = '新規作成-'+selectedValue;
						}
					}
				}
				
				//4열
				else if(i == 3){
					/*selectedValue = $(findSelectedValue).text();*/
					//매칭 됐을때
					if (matchingResultValue == 1) {
						selectedValue = value;
					}
					//매칭 안됐을때
					else {
						/*selectedValue = $(findSelectedValue).text();*/
						if (selectedValue == '選択肢型▼') {
							selectedValue = '新規作成-選択肢型▼';
						}
						else {
							
						}
					}
				}
				
				//5열
				else if(i >= 4){
					//매칭 됐을때
					if (matchingResultValue == 1){
						
						if (i == 5) {
							
							if(selectedValue == '選択肢型▼'){
								selectedValue = '新規作成-選択肢型▼';
							}
							else {
								
							}
						}
						
						else if (i == 6) {
							
							if(selectedValue == '選択肢マスタ▼'){
								selectedValue = '新規作成-選択肢マスタ▼';
							}
							else {
								
							}
						}
					}
					//매칭 안됐을때
					else {
						
						if(selectedValue == '選択肢マスタ▼') {
							selectedValue = '新規作成-選択肢マスタ▼';
						}
						else {
							
						}
					}
				}
				
				customResult.push(selectedValue);
			}
			else if(i <= 1) {
				console.log($(ids).text());
				var str = $(ids).text();
				customResult.push(str);
			}
		}
		customResultList.push(customResult);
		count++;
	});
	
	$.ajax({
		url: 'customHelperResult',
		type: 'post',
		data: {'customResultList' : customResultList},
		traditional: true,
		success: function(result) {
			location.href='panelsResult'
		}
	});
}
