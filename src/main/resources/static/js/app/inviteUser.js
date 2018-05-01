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
		var content = '';
		content += 'ID : '+data.memberid+'<br>';
		content += 'name : ' + data.member_name+'<br>';
		content += '<input type="button" value="초대하기" onclick="javascript:inviteUser(\''+data.memberid+'\')">';
		$('#searchResult').html(content);
	}
	
	if (data == null || data == ''){
		$('#searchResult').html('검색결과가 없습니다');
	}
}

