/**
 * 
 */


$(document).ready(function(){
	
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
	
});


function makeContent(arr){
	var content = '';
	content +='<table><tr>';
	$.each(arr, function(index, values){
		if (index % 3 == 0 && index != 0){
			
			content += '</tr><tr>';
		}
		content += '<td style="border: 30px;">';
		content += '<div class="terraceRoom">';
		content += '<p class="name">테라스 명 : '+values.terrace_room_name+'</p>';
		content += '<p class="info">인원 : '+values.terrace_room_mop+'<br>';
		content += '방장 : '+values.member.member_name+'</p>';
		content += '<p class="btn"><input type="button" onclick ="javascript:enterRoom('+values.terrace_room_number+',\''+values.member.memberid+'\')"value="입장" ></p>';
		content += '</div>';
		content += '</td>';
		
	});
	
	content += '</tr>';
	content += '</table>';
	$('#didididi').html(content);
}

function enterRoom(roomNum,creator){
	window.open("/tr2?terrace_room_number="+roomNum+"&creator="+creator,'terraceRoom','height=' + screen.height + ',width=' + screen.width-10 + 'fullscreen=yes');
}
