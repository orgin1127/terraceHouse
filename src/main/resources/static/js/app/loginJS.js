	/**
	 * 
	 */
	$(document).ready(function() {
		$('#myLoginBtn').on('click', function() {
			$('#myLoginModal').css('display', 'block');
			makeLoginForm();
			$('#loginCloser').on('click', closeModal);
		});
	});
	
	function closeModal() {
		$('#myLoginModal').css('display', 'none');
	};
	function makeLoginForm() {
		var loginFormContent = '<form id="loginForm" action="" method="get">'
		+'<p><input type="text" id="memberid" placeholder=" ID" class="inputArea" name="memberid"></p>'
		+'<p><input type="password" id="memberpw" placeholder=" Password" class="inputArea" name="memberpw"></p>'
		+'<a class="formBtn" href="javascript:loginmember()">로그인</a> <a class="formBtn" id="loginCancle">취소</a></form>';
		$('#myLoginModalBody').html(loginFormContent);
	};
	function loginmember() {
		var member = {memberid: $('#memberid').val(), memberpw: $('#memberpw').val()};
		$.ajax({
			url: 'login'
			, type: 'post'
			, data: JSON.stringify(member)
			, contentType: 'application/json; charset=utf-8'
				, success: function(result) {
					if (result != null && result == 'y') {
						alert('로그인 성공');
						location.href="/afterLogin"
					}
					else if (result == 'n') {
						alert('메일 인증을 확인해 주세요');
						return;
					}
				}
				, error: function(e) {
					alert(JSON.stringify(e));
				}
			});
	};