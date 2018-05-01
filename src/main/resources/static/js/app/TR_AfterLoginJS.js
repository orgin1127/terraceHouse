/**
 *  
 */

var week = new Array('sun','mon','tue','wed','thu','fri','sat');
var yobi;


$(document).ready(function() {
	var d = new Date();
	yobi = week[d.getDay()];
	
	$('#myPageBtn').on('click',function(){
		console.log('클릭됨');
		location.href='myPage';
	});
	
	$('#makeTerraceBtn').on('click', function() {
		$('#myTerraceRegisterModal').css('display', 'block');
		makeTerraceRegister();
		$('#myTerraceRegisterModalCloser').on('click', closeTerraceRegisterModal);
		$("input[name=terraceType]").change(function() {
			var radioValue = $(this).val();
			if (radioValue == "Rterrace") {
				var content = '';
				content += '<div id="terraceInfoDiv"><form>';
				content += '<p><select id="regularTerraceDay">';
				content += '<option value="blank" selected="selected">==요일을 선택하여 주세요==</option>';
				content += '<option value="mon">월요일</option>';
				content += '<option value="tue">화요일</option>';
				content += '<option value="wed">수요일</option>';
				content += '<option value="thu">목요일</option>';
				content += '<option value="fri">금요일</option>';
				content += '<option value="sat">토요일</option>';
				content += '<option value="sun">일요일</option>';
				content += '</select></p>';
				content += '<p><select id="terraceDuration">';
				content += '<option value="blank" selected="selected">==테라스 빈도를 선택하여 주세요==</option>';
				content += '<option value="1">주 1회</option>';
				content += '<option value="2">격주 1회</option>';
				content += '<option value="3">월 1회</option>';
				content += '</select></p>';
				content += '<p><select id="terraceMoP">';
				content += '<option value="blank" selected="selected">==테라스 인원을 선택하여 주세요==</option>';
				content += '<option value="2">2</option>';
				content += '<option value="3">3</option>';
				content += '<option value="4">4</option>';
				content += '</select></p>';
				content += '<p><input type="text" id="rTerraceName" placeholder="Put your Terrace name"></p>';
				content += '<p><input type="button" id="rSubmitBtn" value="테라스 만들기" class="w3-button w3-blue-grey"></p></form></div>';
				$('#afterSelectRadioDiv').html(content);
				$('#rSubmitBtn').on('click', regularTerraceRegi);
			}
			else if(radioValue == "URterrace"){
				var content = '';
				content += '<div id="URterraceInfoDiv"><form>';
				content += '<p><select id="terraceMop">';
				content += '<option value="2">2</option>';
				content +=	'<option value="3">3</option>';
				content +=	'<option value="4">4</option>';
				content +=	'</select></p>';
				content += '<p><input type="text" id="terraceName" placeholder="Put your Terrace name"></p>';
				content += '<p><input type="button" id="urSubmitBtn" value="테라스 만들기" class="w3-button w3-blue-grey"></p></form></div>';
				$('#afterSelectRadioDiv').html(content);
				$('#urSubmitBtn').on('click', regiTerraceRoom);
			}
		});
	});
	
	$('#myFilesBtn').on('click', function() {
		$.ajax({
			url:'myFiles',
			data:{},
			type:'Get',
			success:function(data){
				var arr = data;
				toMyFiles(arr);
				
			},
			error:function(e){
				console.log('실패');
			}
		});
		
			
	});
	
	$('#myTerraceBtn').on('click', function() {
		$.ajax({
			url:'getMyRegularTerrace',
			data:{},
			type:'get',
			success:function(data){
				console.log('data : '+data);
				makeRegularContent(data);
				$('#myRegularTerraceModalCloser').on('click', closeRegularTerraceModal);
			},
			error:function(e){
				console.log('실패');
			}
		});
	});
	
	$('#noticeBtn').on('click', function() {
		$.ajax({
			url:'getMyRegularTerrace',
			data:{},
			type:'get',
			success:function(data){
				commonNotifi(data);
			},
			error:function(e){
				console.log('정기목록 가져오기 실패');
			}
		});
		$.ajax({
			url:'getAllInvitation',
			data:{},
			type:'Post',
			success:function(data){
				inviteNotifi(data);
			},
			error : function(e){
				console.log('초대목록 받아오기 실패');
			}
		});
		$('#myNotificationModal').css('display', 'block');
		$('#myNotificationModalCloser').on('click', closeMyNotificationModal);
	})
	
	$('#terraceListBtn').on('click', function() {
		showAllRoom();
	});
	
	$('#wholeTerraceModalCloser').on('click',function(){
		closeWholeTerraceModal();
	});
});

function closeMyNotificationModal() {
	$('#myNotificationModal').css('display', 'none');
}

function closeRegularTerraceModal() {
	$('#myRegularTerraceModal').css('display', 'none');
}

function makeRegularTerrace() {
	
}

function makeRegularContent(data){
		
	var content = '';
	
	$.each(data, function(index, values){
			
		content +='<h2 class =" w3-text-teal"><b>'+values.regularTerrace.terrace_name+'</b></h2>';
		content +='<h6 class ="rightAr">날짜 : '+values.regularTerrace.create_date+'</h6>';
		content +='<p> 인원 : '+values.regularTerrace.terrace_mop+'</p>';
		switch (values.regularTerrace.terrace_freq){
		case 1: content += '<p>빈도 : 주 1회 </p>';break;
		case 2: content += '<p>빈도 : 격주 1회 </p>';break;
		case 3: content += '<p>빈도 : 월 1회 </p>';break;
		}
		switch (values.regularTerrace.terrace_date){
			
		case 'mon':content += '<p>요일 : 월요일 </p>';break;
		case 'tue':content += '<p>요일 : 화요일 </p>';break;
		case 'wed':content += '<p>요일 : 수요일 </p>';break;
		case 'thu':content += '<p>요일 : 목요일 </p>';break;
		case 'fri':content += '<p>요일 : 금요일 </p>';break;
		case 'sat':content += '<p>요일 : 토요일 </p>';break;
		case 'sun':content += '<p>요일 : 일요일 </p>';break;
		}
			
		content += '<input type="button" value="유저 초대하기" onclick="javascript:inviteUser('+values.regularTerrace.regular_terrace_number+',\''+values.regularTerrace.terrace_name+'\')">';
			
	});
	$('#myRegularTerraceModalBody').html(content);
	$('#myRegularTerraceModal').css('display', 'block');
		
		
	 }

function inviteUser(terrace_num,terrace_name){
	
	window.open('/inviteUser?terrace_num='+terrace_num+'&terrace_name='+terrace_name,'invite','height=300,width=300');
	
}

function commonNotifi(data){
	var content = '';
	console.log(yobi);
	$.each(data,function(index,values){
		if (values.regularTerrace.terrace_date == yobi){
			content += '<h3 class =" w3-text-teal"><b> 오늘 '+values.regularTerrace.terrace_name+' 테라스가 있습니다. </b></h3>'
		}
	});
	content += '<hr>';
	$('#commonNotifi').html(content);
}

function inviteNotifi(data){
	var content='';
	console.log(data);
	$.each(data,function(index,values){
		content += '<h3 class =" w3-text-teal"><b>'+values.sender+'님이 초대하셨습니다.<b></h3>';
		content += '<h4>'+values.terrace_name+'으로 입장</h4>';
		content += '<a onclick = "javascript:takeInvite(\''+values.notification_number+'\')"><img src="image/acceptButton.png"></a>';
	});
	$('#inviteNotifi').html(content);
}

function takeInvite(num){
	
	$.ajax({
		url:'acceptRTInvite',
		data : {'regular_terrace_number' : num},
		type:'Post',
		success: function(data){
			console.log('초대받기 성공');
		},
		error : function (e){
			console.log('초대받기 실패');
		}
	});
	/*window.open(url,'TerraceRoom','height=' + 744 + ',width=' + 1395 + 'fullscreen=yes');*/
}

function toMyFiles(arr){
	
	var content = '';
	
	$.each(arr, function(index, values){
		content += '<h2 class="w3-text-teal"><b>'+values.terrace_room_number.terrace_room_name+'</b></h2>';
		content += '<h6 class="rightAr">날짜:'+values.terrace_room_number.create_date+'</h6>';
		content += '<p>개인파일 : <a href="myFilesDownload?filePath='+values.terrace_room_number.saved_file_path+'&fileName='+values.saved_personal_file_name+'"><img src="image/flatButton.png"></a>';
		content += '<p>공유파일 : <a href="myFilesDownload?filePath='+values.terrace_room_number.saved_file_path+'&fileName='+values.terrace_room_number.shared_file_name+'"><img src="image/flatButton.png"></a></p>';
		content += '<p>원본파일 : <a href="myFilesDownload?filePath='+values.terrace_room_number.saved_file_path+'&fileName='+values.terrace_room_number.saved_file_name+'"><img src="image/flatButton.png"></a></p>';
		content += '<hr>';
	});
	
	$('#terraceDownload').html(content);
	document.getElementById('ggo1').style.display='block';
	
}

function showAllRoom(){
$.ajax({
		
		url:'roomsList',
		type:'Get',
		data:{},
		success: function(dat){
			console.log('목록 가져왔음');
			var arr = dat;
			console.log(arr);
			makeContent(arr);
		},
		error:function(e){
			console.log('실패');
		}
	});
	
}

function closeWholeTerraceModal(){
	document.getElementById('wholeTerraceModal').style.display = 'none';
}

function makeContent(arr){
	var content = '';
	content +='<table><tr>';
	$.each(arr, function(index, values){
		if (index % 3 == 0 && index != 0){
			
			content += '</tr><tr>';
		}
		content += '<td style="border: 30px;">';
		content += '<div class="TterraceRoom">';
		content += '<p class="Tname">'+values.terrace_room_name+'</p>';
		content += '<p class="Tinfo">인원 : '+values.terrace_room_mop+'<br>';
		content += '방장 : '+values.member.member_name+'</p>';
		content += '<a class="Tbtn" onclick="javascript:enterRoom('+values.terrace_room_number+',\''+values.member.memberid+'\')"><img src="image/enterButton.png"></a>';
		content += '</div>';
		content += '</td>';
		
	});
	
	content += '</tr>';
	content += '</table>';
	$('#wholeDiv').html(content);
	document.getElementById('wholeTerraceModal').style.display = 'block';
}

function enterRoom(roomNum,creator){
	
	window.open("/tr2?terrace_room_number="+roomNum+"&creator="+creator,'terraceRoom','height=' + 744 + ',width=' + 1395 + 'fullscreen=yes');
}

function regiTerraceRoom() {
	var terrace_room_name = $('#terraceName').val();
	var terrace_room_mop = $('#terraceMop option:selected').val();
	var dto = {terrace_room_name: terrace_room_name, terrace_room_mop: terrace_room_mop};
	
	if (terrace_room_name != ''){
		$.ajax({
			url: 'registTerraceRoom',
			type: 'post',
			data: JSON.stringify(dto),
			dataType: 'JSON',
			contentType:'application/json; charset=utf-8',
			success: function(result) {
				if(result != 0){
					alert('방 등록 성공');
					//location.href="/tr?terrace_room_number="+result
					window.open("/tr?terrace_room_number="+result,'terraceRoom','height=' + 744 + ',width=' + 1395 + 'fullscreen=yes');
					closeTerraceRegisterModal();
				}
			}
		, error: function(e) {
			alert(JSON.stringify(e));
		}
		});
	}
}


function regularTerraceRegi() {
	var terrace_date = $("#regularTerraceDay option:selected").val();
	var terrace_freq = $('#terraceDuration option:selected').val();
	var terrace_mop = $('#terraceMoP option:selected').val();
	var terrace_name = $('#rTerraceName').val();
	var regular_terrace = {terrace_name: terrace_name, terrace_mop: terrace_mop, terrace_freq: terrace_freq, terrace_date: terrace_date};
	if (terrace_date !='blank' && terrace_freq != 'blank' && terraceMoP != 'blank' && terrace_name != '') {
		$.ajax({
			url: 'regularTerraceRegi',
			type: 'post',
			data: JSON.stringify(regular_terrace),
			dataType: 'json',
			contentType:'application/json; charset=utf-8',
			success: function(result) {
				if(result != 0){
					alert('등록에 성공하였습니다.');
					closeTerraceRegisterModal();
				}
				else {
					alert ('입력사항을 다시 확인하여 주세요');
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

function hideExclude(excludeId) {
	$("#terraceInfoDiv").children().each(function() {
		$(this).hide();
	});
	// 파라미터로 넘겨 받은 id 요소는 show
	$("#" + excludeId).show();
}

function makeTerraceRegister() {
	var terraceResFormContent = '<form><fieldset style="margin-left: 5px;"><legend style="color:white; background-color:black">Please select your Terrace Type</legend>'
        +'<div id="selectTerraceTypeRadioDiv" style="margin-left:150px;">'
        +'<input type="radio" class="terraceTypeRadioClass" id="terraceTypeR" name="terraceType" value="Rterrace"/>'
        +'<label for="contactChoice1">정기 Terrace</label>'
        +'<input type="radio" class="terraceTypeRadioClass" id="terraceTypeUR" name="terraceType" value="URterrace" />'
        +'<label for="contactChoice2">비정기 Terrace</label></div></fieldset></form>'
        +'<div id="afterSelectRadioDiv"></div>'
	$('#myTerraceRegisterModalBody').html(terraceResFormContent);
}

function closeTerraceRegisterModal() {
	$('#myTerraceRegisterModal').css('display', 'none');
}