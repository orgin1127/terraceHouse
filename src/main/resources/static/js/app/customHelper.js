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
        	
			$.each(itemList, function(index,values) {
				//console.log("항목 : " + values);
			});
			$.each(dataList, function(index,values) {
				//console.log("데이터 : " + values);
			});
		},
		error: function (e) {
			console.log(e);
		}
	});
}