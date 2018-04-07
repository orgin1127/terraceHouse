/**
 * TerraceRoom JavaScript 
 * Modifi BY SEO
 */
var connection = new RTCMultiConnection();

// this line is VERY_important
connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';

// all below lines are optional; however recommended.

connection.session = {
    audio: true,
    video: true
};

connection.sdpConstraints.mandatory = {
    OfferToReceiveAudio: true,
    OfferToReceiveVideo: true
};


connection.onstream = function(event) {
    //document.body.appendChild( event.mediaElement );
	var addDiv = document.getElementsByClassName('videoContainer');
	console.log('실행');
	
	var temp = document.getElementById('currentJoinMember');
	var num = connection.getAllParticipants().length;
	var id = connection.getAllParticipants();
	console.log('check : ' + num + ", " + id);
	
	temp.innerHTML = '현 접속자 수 : ' + connection.getAllParticipants().length; + '<br>' + '접속자 id : ' + connection.getAllParticipants();
	
    addDiv[num].appendChild( event.mediaElement );
};
var predefinedRoomId = 'testing';

document.getElementById('btn-open-room').onclick = function() {
    this.disabled = true;
    console.log('open실행');
    connection.open( predefinedRoomId );
};

document.getElementById('btn-join-room').onclick = function() {	
	
    this.disabled = true;
    connection.join( predefinedRoomId );
};