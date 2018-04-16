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
		$("input[terraceType]").change(function() {
			var radioValue = $(this).val();
			if (radioValue == "Rterrace") {
				
			}
		});
	});
	
});

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
        +'<div id="afterSelectRadioDiv"><form>'
        +''
	$('#myTerraceRegisterModalBody').html(terraceResFormContent);
	
	
}

function closeTerraceRegisterModal() {
	$('#myTerraceRegisterModal').css('display', 'none');
}