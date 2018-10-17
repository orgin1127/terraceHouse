/**
 * 
 */

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
        	
        	console.log("success");
        	
        	itemList = result.itemList;
        	dataList = result.dataList;
        	
        	//console.log(JSON.stringify(result.itemList));
        	
        	var str ='<form action="itemChange" method="post">'; 
        		str+= '<table class = "type05">';
			$.each(itemList, function(index,values) {
				console.log("항목 : " + values);
				str+='<tr> <th>';
				str+='<label class="container">';
				str+='<input type="checkbox" checked="checked" id="item'+index+'" value="'+values+'">';	
				str+='<span class="checkmark"></span></label></th><td>';
				str+=values;
				str+='</td></tr>';
			});
			str+='</table>';
			str+='<input type="button" id="itemReset" value="確定"></form>';
			
			$('#itemListUpdate').html(str);
						
			$.each(dataList, function(index,values) {
				console.log("데이터 : " + values);
			});
		},
		error: function (e) {
			console.log(e);
		}
	});
}