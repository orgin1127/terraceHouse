/**
 * 
 */
$(document).ready(function() {
	
	$('#myPageBtn').on('click', function() {
		
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
				content += '<p><input type="button" id="rSubmitBtn"></p></form></div>';
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
				content += '<p><input type="button" id="urSubmitBtn"></p></form></div>';
				$('#afterSelectRadioDiv').html(content);
				$('#urSubmitBtn').on('click', regiTerraceRoom);
			}
		});
	});
	
});

function regiTerraceRoom() {
	var terrace_room_name = $('#terraceName').val();
	var terrace_room_mop = $('#terraceMop option:selected').val();
	var dto = {terrace_room_name: terrace_room_name, terrace_room_mop: terrace_room_mop};
	
	if (terrace_room_name != ''){
		alert('js 작동');
		$.ajax({
			url: 'registTerraceRoom',
			type: 'post',
			data: JSON.stringify(dto),
			dataType: 'JSON',
			contentType:'application/json; charset=utf-8',
			success: function(result) {
				alert('방 등록 성공');
				console.log(result);
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
	var member_number = 2;
	var regular_terrace = {terrace_name: terrace_name, terrace_mop: terrace_mop, terrace_freq: terrace_freq, terrace_date: terrace_date, member_number: member_number};
	if (terrace_date !='blank' && terrace_freq != 'blank' && terraceMoP != 'blank' && terrace_name != '') {
		$.ajax({
			url: 'regularTerraceRegi',
			type: 'post',
			data: JSON.stringify(regular_terrace),
			dataType: 'json',
			contentType:'application/json; charset=utf-8',
			success: function(result) {
				
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
        +'<input type="radio" class="terraceTypeRadioClass" id="terraceTypeR" name="terraceType" value="Rterrace" checked="checked" />'
        +'<label for="contactChoice1">정기 Terrace</label>'
        +'<input type="radio" class="terraceTypeRadioClass" id="terraceTypeUR" name="terraceType" value="URterrace" />'
        +'<label for="contactChoice2">비정기 Terrace</label></div></fieldset></form>'
        +'<div id="afterSelectRadioDiv"></div>'
	$('#myTerraceRegisterModalBody').html(terraceResFormContent);

}

function closeTerraceRegisterModal() {
	$('#myTerraceRegisterModal').css('display', 'none');
}