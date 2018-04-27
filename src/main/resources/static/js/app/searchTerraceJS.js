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
	var inputTitle = $('#searchTerracTitleInput').val();
	console.log(inputTitle);
	if(inputTitle != '') {
		$.ajax({
			url : 'searchTerraceRoom'
			, type : 'post'
			, data : {inputTitle : inputTitle}
			, dataType: 'json'
			, contentType:'application/json; charset=utf-8'
			, success: function(result) {
				if (result != null){
					console.log(result);
					printSearchedTerraceResult(result);
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
		content += '<tr><td>' + index + '</td><td>' + values.terrace_room_name + '</td><td>' + values.terrace_room_mop + '</td>'
		content += '<td>' + values.member.member_name + '</td><td><button onclick="javascript:enterTheTR()" roomNum="'+ values.terrace_room_number+'"></td></tr>';
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