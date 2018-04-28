/**
 * 
 */


$(document).ready(function(){
	
	$.ajax({
		
		url:'roomsList',
		type:'Get',
		data:{},
		success: function(dat){
			
			var arr = dat;
			makeContent(arr);
		},
		error:function(e){
			console.log('실패');
		}
	});
	
});


function makeContent(arr){
	var content = '';
	content +='<table><tr>';
	$.each(arr, function(index, values){
		content += '<td>';
		content += '<div class="terraceRoom">';
		content += '이름 : '+values.terrace_room_name+'<br>';
		content += '인원 : '+values.terrace_room_mop+'<br>';
		content += '방장 : '+values.member.member_name+'<br>';
		content += '<input type="button" onclick ="javascript:enterRoom('+values.terrace_room_number+',\''+values.member.memberid+'\')"value="입장">';
		content += '</div>';
		content += '</td>';
		if (index % 3 == 0 && index != 0){
		
			content += '</tr><tr>';
		}
	});
	
	content += '</tr>';
	content += '</table>';
	$('#didididi').html(content);
}

function enterRoom(roomNum,creator){
	window.open("/tr2?terrace_room_number="+roomNum+"&creator="+creator,'terraceRoom','height=' + screen.height + ',width=' + screen.width-10 + 'fullscreen=yes');
}
