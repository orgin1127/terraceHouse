/**
 * JS for Member Register and login
 * create by Seo
 */
$(document).ready(function() {

	$('#myResBtn').on('click', function() {
		$('#myRegisterModal').css('display', 'block');
		makeResForm();
		$('#registerCloser').on('click', closeRegisterModal);
	});
});
function closeRegisterModal() {
	$('#memberid').html('');
	$('#memberpw').html('');
	$('#member_email').html('');
	$('#member_name').html('');
	$('#myRegisterModal').css('display', 'none');
}


function makeResForm() {
	var resFormContent = '<form id ="resForm" action="registerMember" method="post">'
		        +'<p><input type="text" id="memberid" placeholder=" ID" class="inputArea" name="memberid"></p>'
		        +'<p><input type="password" id="memberpw" placeholder=" Password" class="inputArea" name="memberpw"></p>'
		        +'<p><input type="text" id="member_email" placeholder=" Email" class="inputArea" name="member_email"></p>'
		        +'<p><input type="email" id="member_name" placeholder=" Name" class="inputArea" name="member_name"></p>'
		       	+'<a class="formBtn" href="javascript:registerMember()">회원가입</a> <a class="formBtn" id="resCancle">취소</a></form>';
	$('#myRegisterModalBody').html(resFormContent);
}

function registerMember() {
	var inputID = $('#memberid').val();
	var inputPW = $('#memberpw').val();
	var inputEmail = $('#member_email').val();
	var inputName = $('#member_name').val();
	
	if (inputID != '' && inputPW != '' && inputEmail != '' && inputName != '') {
		var member = {memberid: inputID, memberpw: inputPW, member_email: inputEmail, member_name: inputName};
		$.ajax({
			url: 'memberRegi'
			, type: 'post'
			, data: JSON.stringify(member)
			, dataType: 'json'
			, contentType:'application/json; charset=utf-8'
			, success: function(result) {
				if (result != null) {
					alert('회원가입 성공');
					closeRegisterModal();
					location.reload;
				}
				else {
					alert('회원가입 실패 : ' + result);
				}
			}
			, error: function(e) {
				alert(JSON.stringify(e));
			}
		});
	}
	else {
		alert('입력 항목을 정확하게 입력하여 주세요.');
	}
}

