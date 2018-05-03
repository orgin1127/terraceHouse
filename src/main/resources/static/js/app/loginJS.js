$(document).ready(function() {
	$('#myLoginBtn').on('click', function() {
		$('#myLoginModal').css('display', 'block');
		makeLoginForm();
		$('#loginCloser').on('click', closeModal);
		$('#myLoginBtn2').on('click', loginMember);
	});
});

function closeModal() {
	$('#myLoginModal').css('display', 'none');
};
function makeLoginForm() {
	console.log('로그인 폼 생성');
	var loginFormContent = '<form id="loginForm" action="loginMember" method="post">'
	+'<p><input type="text" id="memberid" placeholder=" ID" class="inputArea" name="memberid"></p>'
	+'<p><input type="password" id="memberpw" placeholder=" Password" class="inputArea" name="memberpw"></p>'
	+'<p><input type="button" id="myLoginBtn2" value="로그인" class="w3-button w3-light-gray"></p> </form>';
	$('#myLoginModalBody').html(loginFormContent);
	console.log('로그인 폼 생성 완료');
};
function loginMember() {
	console.log('login 작동');
	var member = {memberid: $('#memberid').val(), memberpw: $('#memberpw').val()};
	$.ajax({
		url: 'login'
		, type: 'post'
		, data: JSON.stringify(member)
		, contentType: 'application/json; charset=utf-8'
		, success: function(result) {
			if (result != null && result == 'y') {
				console.log('ajax 통신 결과값 y');
				alert('로그인 성공');
				location.href="/afterLogin";
			}
			else if (result == 'n') {
				console.log('ajax 통신 결과값 n');
				alert('메일 인증을 확인해 주세요');
				return;
			}
		}
		, error: function(e) {
			
		}
	});
};