/**
 * JS for searching terrace
 * created by SEO K
 */
$(document).ready(function() {
	$('#searchTerraceBtn').on('click', function() {
		$('#terraceSearchModal').css('display', 'block');
		$('#terraceSearchModalCloser').on('click', closeSearchingModal);
		$('#searchTerraceRoomBtn').on('click', startSearchTerraceRoom);
	});
});

function startSearchTerraceRoom() {
	console.log('검색 실행');
	var inputTitle =  $('#searchTerracTitleInput').val();
	console.log(inputTitle);
	if(inputTitle != '') {
		$.ajax({
			url : 'searchTerraceRoom'
			, type : 'post'
			, data : {'inputTitle' : inputTitle}
			, dataType: 'json'
			
			, success: function(searchedTerraceRoomList) {
				if (searchedTerraceRoomList != null){
					console.log(searchedTerraceRoomList);
					printSearchedTerraceResult(searchedTerraceRoomList);
				}
				else {
					alert('찾으시는 방이 없습니다.');
				}
			}, error: function(e) {
				alert(JSON.stringify(e));
			}	
		});
	}
}

function printSearchedTerraceResult(searchedTerraceRoomList) {
	var content = '';
	content += '<table id="searchedTerraceRoom"><tr><th id="trNumTH">번호</th><th id="trNameTH">방 제목</th><th id="trMoPTH">제한인원</th><th id="trCreatorTH">creator</th></tr>';
	$.each(searchedTerraceRoomList, function(index, values){
		content += '<tr><td>' + (index+1) + '</td><td>' + values.terrace_room_name + '</td><td>' + values.terrace_room_mop + '</td>'
		content += '<td>' + values.member.member_name + '</td><td><button class="enterTRBtn" onclick="javascript:enterTheTR()" roomNum="'+ values.terrace_room_number+'">입장</button></td></tr>';
	});
	content += '</table>';
	$('#searchedTerraceDiv').html(content);
}

function enterTheTR() {
	alert('작동');
}

function closeSearchingModal() {
	$('#terraceSearchModal').css('display', 'none');
}