<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>
<title>test중입니다.</title>
<style>
	video {
		width: 40%;
		border-radius: 15px;
		
	}
</style>
</head>
<body>
<H1>test 중입니다.</H1>

<input id="txt-roomid" placeholder="unique room id">
<button id="btn-open-or-join-room">Open or Join</button>
<script>
var connection = new RTCMultiConnection();


connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';

// if you want audio+video conferencing
connection.session = {
    audio: true,
    video: true
};

connection.sdpConstraints.mandatory = {
		OfferToReceiveAudio: true,
		OfferToReceiveVideo: true
};

var roomid = document.getElementById('txt-roomid');
roomid.value = connection.token();
document.getElementById('btn-open-or-join-room').onclick = function() {
	this.disabled = true;
	
	connection.openOrJoin(roomid.value || 'predefined-roomid');
};
</script>


</body>
</html>