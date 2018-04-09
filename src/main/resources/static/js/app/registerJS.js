/**
 * JS for Member Register
 */

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
					window.reload;
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
		alert('blank check');
	}
}