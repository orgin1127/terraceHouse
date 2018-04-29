/**
 *  
 */

$(document).ready(function() {
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
				content += '<p><input type="button" id="rSubmitBtn" value="테라스 만들기"></p></form></div>';
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
				content += '<p><input type="button" id="urSubmitBtn" value="테라스 만들기"></p></form></div>';
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
	
	$('#terraceListBtn').on('click', function() {
		showAllRoom();
	});
	
	$('#wholeTerraceModalCloser').on('click',function(){
		closeWholeTerraceModal();
	});
});

function toMyFiles(arr){
	
	var content = '';
	
	$.each(arr, function(index, values){
		content += '<h5 class="w3-text-teal"><b>테라스이름:'+values.terrace_room_number.terrace_room_name+'</b></h5>';
		content += '<h6 class="w3-opacity">날짜:'+values.terrace_room_number.create_date+'</h6>';
		content += '<p>개인파일 : <a href="myFilesDownload?filePath='+values.terrace_room_number.saved_file_path+'&fileName='+values.saved_personal_file_name+'">'+values.saved_personal_file_name+'</a></p>';
		content += '<p>공유파일 : <a href="myFilesDownload?filePath='+values.terrace_room_number.saved_file_path+'&fileName='+values.terrace_room_number.shared_file_name+'">'+values.terrace_room_number.shared_file_name+'</a></p>';
		content += '<p>원본파일 : <a href="myFilesDownload?filePath='+values.terrace_room_number.saved_file_path+'&fileName='+values.terrace_room_number.saved_file_name+'">'+values.terrace_room_number.saved_file_name+'</a></p>';
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
		content += '<p class="Tname">테라스 명 : '+values.terrace_room_name+'</p>';
		content += '<p class="Tinfo">인원 : '+values.terrace_room_mop+'<br>';
		content += '방장 : '+values.member.member_name+'</p>';
		content += '<p class="Tbtn"><input type="button" onclick ="javascript:enterRoom('+values.terrace_room_number+',\''+values.member.memberid+'\')"value="입장" ></p>';
		content += '</div>';
		content += '</td>';
		
	});
	
	content += '</tr>';
	content += '</table>';
	$('#wholeDiv').html(content);
	document.getElementById('wholeTerraceModal').style.display = 'block';
}

function enterRoom(roomNum,creator){
	
	window.open("/tr2?terrace_room_number="+roomNum+"&creator="+creator,'terraceRoom','height=' + screen.height + ',width=' + screen.width-10 + 'fullscreen=yes');
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
					window.open("/tr?terrace_room_number="+result,'terraceRoom','height=' + screen.height + ',width=' + screen.width + 'fullscreen=yes');
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
	var terraceResFormContent = '<form><fieldset><legend>Please select your Terrace Type</legend>'
        +'<div id="selectTerraceTypeRadioDiv">'
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