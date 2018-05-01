/**
 * 
 */

$(document).ready(function(){
	
	$('#searchBtn').on('click',function(){
		var searchId = document.getElementById('searchId').value;
		$.ajax({
			url:'searchUser',
			data:{'searchId':searchId},
			type:'get',
			success:function(data){
				console.log(data);
				makeContent(data);
			},
			error:function(e){
				console.log('검색실패');
			}
		});
	});
	
	$('#inviteForm').on('submit',function(){
		window.close();
	});
	
});

function inviteUser(memberid){
	console.log('여기까지 왔음');
	var userId = memberid;
	document.getElementById('userId').value = userId;
	var inviteForm = document.getElementById('inviteForm');
	inviteForm.submit();
}

function makeContent(data){
	if (data != null && data != ''){
		var content = '<table id="resultTable">';
		content += '<tr><td>ID</td><td>'+data.memberid+'</td>';
		content += '<td rowspan="2"><button class="w3-button w3-blue-grey" style="height:50px;width:100%;" onclick="javascript:inviteUser(\''+data.memberid+'\')">초대하기</button></td></tr>';
		content += '<tr><td>name</td><td>' + data.member_name+'</td><td></td></tr>';
		content += '</table>';
		$('#searchResult').html(content);
	}
	
	if (data == null || data == ''){
		$('#searchResult').html('검색결과가 없습니다');
	}
}
