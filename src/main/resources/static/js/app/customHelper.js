/**
 * 
 */

function uploadExcel() {
	var form = document.getElementById('excelUpload');
	var formData = new FormData(form);
	
	$.ajax({
		url: 'uploadExcel',
		type: 'post',
		enctype: 'multipart/form-data',
        data: formData,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        success: function(result) {
			if (result == 1){
				console.log('success');
			}
		},
		error: function (e) {
			console.log(e);
		}
	});
}