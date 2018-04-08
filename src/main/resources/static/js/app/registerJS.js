/**
 * JS for Member Register
 */

function registerMember() {
	var inputID = $('#memberID').val();
	var inputPW = $('#memberPW').val();
	var inputEmail = $('#memberEmail').val();
	var inputName = $('#memberName').val();
	if (inputID != '' && inputPW != '' && inputEmail != '' && inputName != '') {
		$.ajax({
			url: 'register'
			, type: 'post'
			, data: $('#resForm').serialize()
			, dataType: 'text'
			, success: function(result) {
				if (result == 1) {
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