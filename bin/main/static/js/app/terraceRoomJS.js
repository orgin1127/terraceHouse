/**
 * TerraceRoom JavaScript 
 * Modifi BY SEO D
 */
window.enableAdapter = true;

var connection = new RTCMultiConnection();

//오늘 날짜
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; 
var yyyy = today.getFullYear();

if(dd<10) {
    dd='0'+dd
} 

if(mm<10) {
    mm='0'+mm
} 

var todayString = yyyy+''+mm+''+dd;
//유저 관련
var userList = new Array();
var userCount = 0;

//캔버스 관련
var canvas;
var imageOnly;
var ctx;
var imagePaste;
var sx, sy;				//좌표를 받는 변수	
var img;
var firstX,firstY,lastX,lastY;		//네모를 그릴때 사용할 변수
var lineColor = '#000000';
var drawing = false;
var lines = new Array();//선의 정보가 있는 배열
var canvasLineCnt = 0;
var loginId; //임시로 사용할 ID
var drawMode = 'pen';
var eraserSize = 15;
var lineAct = false;
var endOfPage;
var cPage = 0;
var control = false;
var ownerId;
var lwidth = 5;
var terraceName = document.getElementById('terraceName').value;
//채팅 관련
var chatContainer = document.querySelector('.chat-output');
var chatInputArea = document.getElementById('input-text-chat');

// this line is VERY_important
connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';

// all below lines are optional; however recommended.

connection.session = {
    audio: true,
    video: true,
    data:true
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
	
	
	
    addDiv[0].appendChild( event.mediaElement );
    
};

/*connection.onUserStatusChanged = function(event){
	
	var userId = event.userid;
	
};*/

var predefinedRoomId = null;

document.getElementById('btn-open-room').onclick = function() {
	predefinedRoomId = document.getElementById('loginId').value;
    this.disabled = true;
    console.log('open실행');
    connection.open( predefinedRoomId );
    control = true;
    ownerId = predefinedRoomId;
    var creator = document.getElementById('creator');
    creator.value = ownerId;
    userList[userCount] = document.getElementById('loginId').value;
    userCount++;
    var userSpace = document.getElementById('userList');
    reUserList();
};

document.getElementById('btn-join-room').onclick = function() {	
	
	ownerId = document.getElementById('OwnerId').value;
    this.disabled = true;
    connection.join( ownerId );
    control = false;
    var creator = document.getElementById('creator');
    creator.value = ownerId;
};

connection.onstreamended = function(event){
	
	var mediaElement = document.getElementById(event.streamid);
    if (mediaElement) {
        mediaElement.parentNode.removeChild(mediaElement);
    }
	
	var tempid = loginId;
	var deleteID = {};
	deleteID.mode = 'exit';
	deleteID.id = tempid;
	connection.send(JSON.stringify(deleteID));
	
};

document.getElementById('btn-save-progress').onclick = function(){
	var tempimg = document.getElementById('image1');
	var creator = document.getElementById('creator').value;
	var imageArray = new Array();
	cPage = 0;
	for (var i = 0; i < endOfPage ;i++){
	
		tempimg.src = "https://s3.ap-northeast-2.amazonaws.com/terracehouse-user-bucket/tr-user-files/"+loginId+"/"+todayString+"/"+terraceName+"image/myImage"+cPage+".png";
		var tempCanvas = document.getElementById('imageOnly');
		var tempImage = document.getElementById('image1');
		var tempCtx = tempCanvas.getContext('2d');
		
		tempCanvas.setAttribute("width","595px");
		tempCanvas.setAttribute("height","842px");
		
		tempCtx.drawImage(tempImage,0,0);
		
		tempCtx.lineCap="round";
		tempCtx.lineWidth = lwidth;
		for(var j = 0;j < lines.length;j++){
			
			if (lines[j][2] == 'none')
			{
				continue;
			}
			if (i+1 != lines.length && lines[j][3] == cPage){
			tempCtx.strokeStyle = lines[j][5];
			tempCtx.beginPath();
			tempCtx.moveTo(lines[j][0],lines[j][1]);
			tempCtx.lineTo(lines[j+1][0],lines[j+1][1]);
			tempCtx.stroke();
			}
		
		var strimg = tempCanvas.toDataURL('image/png');
		imageArray[i] = strimg;
		
		}
		cPage++;
	}
	if (imageArray[endOfPage] != ''){
		var terrace_room_number = document.getElementById('terraceNumber').value;
		$.ajax({
			
			url:'makePDF',
			type:'POST',
			traditional: true,
			data:{'imageArray' : imageArray, 'terrace_room_number' : terrace_room_number},
			
			success:function(e){
				console.log('보내짐');
			}
		});
	}
	
};

//공용 칠판 영역
window.onload = start();

function start(){		
	
	loginId = document.getElementById('loginId').value;
	
	imageOnly = document.getElementById('imageOnly');
	imagePaste = imageOnly.getContext('2d');
	canvas = document.getElementById('mycanvas');	
	ctx = canvas.getContext('2d');
	ctx.lineWidth = lwidth;
	img = document.getElementById('image1');
	if (img != null){
		imagePaste.drawImage(img,0,0);
	}

	ctx.lineCap="round";
	//캔버스위를 클릭 시 이벤트
	canvas.onmousedown = function(e) {
		
		if (control == false){
			console.log('권한이 없음');
			return;
		}

		loginId = document.getElementById('loginId').value;
		id = loginId;
		e.preventDefault();
		
		sx = canvasX(e.clientX);
		sy = canvasY(e.clientY);
		
		//undo를 위한 id가 비어있는 선
		lines[canvasLineCnt] = new Array();
	    lines[canvasLineCnt][0] = sx;
	    lines[canvasLineCnt][1] = sy;
	    lines[canvasLineCnt][2] = 'none';
	    lines[canvasLineCnt][3] = cPage;
	    canvasLineCnt++;		
	    
	    //다른 클라이언트에게 보내는 선 정보
	    var location = {};
	    location.x = sx;
	    location.y = sy;
	    location.id = 'none';
	    location.mode = 'draw';
	    connection.send(JSON.stringify(location));
		
		drawing = true;
		if (drawMode == 'rectangle'){
			firstX = sx;
			firstY = sy;
		}		
		
		if (drawMode == 'circle'){
			firstX = sx;
			firstY = sy;
		}

		if (drawMode == 'line'){
			if (!(lineAct)){
			firstX = sx;
			firstY = sy;
			lineAct = true;
			
			}
			else{
			lastX = sx;
			lastY = sy;
			lineAct = false;
			
			}
		}
		
	};
	
	//캔버스에서 움직일 때 이벤트
	canvas.onmousemove = function(e) {
		
		
		if (drawing) {
			e.preventDefault();			
			
			if (drawMode == 'pen'){
			//좌표 정보를 선 배열에 저장
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = sx;
			lines[canvasLineCnt][1] = sy;
			lines[canvasLineCnt][2] = id;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][5] = lineColor;

			canvasLineCnt++;
			
			var location = {};
		    location.x = sx;
		    location.y = sy;
		    location.id = id;

		    location.color = lineColor;

		    location.mode = 'draw';
		    connection.send(JSON.stringify(location));
			 
			//움직일 때마다 좌표를 새로 받음	
			sx = canvasX(e.clientX);
			sy = canvasY(e.clientY);
			
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = sx;
			lines[canvasLineCnt][1] = sy;
			lines[canvasLineCnt][2] = id;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][5] = lineColor;

			canvasLineCnt++;
			
			location.x = sx;
		    location.y = sy;
		    location.id = id;

		    location.color = lineColor;

		    location.mode = 'draw';
		    connection.send(JSON.stringify(location));
			
			redraw();
			}
			
			if (drawMode == 'eraser'){
				
				sx = canvasX(e.clientX);
				sy = canvasY(e.clientY);
				
				for (var i = 0 ; i < lines.length ; i++)
				{					
					if (lines[i][0] <= (sx + eraserSize) && lines[i][0] >= (sx - eraserSize)
							&& lines[i][1] <= (sy + eraserSize) && lines[i][1] >= (sy - eraserSize))
						{
							var temporary = new Array();
							temporary[2] = 'none';
							lines.splice(i,0,temporary);		
							lines.splice(i+1,0,temporary);
							lines.splice(i+2,1);
							canvasLineCnt = lines.length;
							redraw();
							
							var location = {};
							location.i = i;
							location.mode = 'eraser';
							connection.send(JSON.stringify(location));					
							break;					
						}
				}			
				return;				
			}
			if (drawMode == 'rectangle'){
						
				redraw();

				ctx.strokeStyle=lineColor;
				
				ctx.moveTo(sx,sy);
				ctx.lineTo(canvasX(e.clientX),sy);
				ctx.moveTo(canvasX(e.clientX),sy);
				ctx.lineTo(canvasX(e.clientX),canvasY(e.clientY));
				ctx.moveTo(sx,sy);
				ctx.lineTo(sx,canvasY(e.clientY));
				ctx.moveTo(sx,canvasY(e.clientY));
				ctx.lineTo(canvasX(e.clientX),canvasY(e.clientY));		
				ctx.stroke();	
				
			}
			
			if (drawMode == 'circle'){
				redraw();
				ctx.strokeStyle = lineColor;
				ctx.beginPath();
				var centerX = sx + canvasX(e.clientX);
				var centerY = sy + canvasY(e.clientY);
//				ctx.arc((sx+canvasX(e.clientX))/2,(sy+canvasY(e.clientY))/2,Math.abs(sx - canvasX(e.clientX)),0,2 * Math.PI);
				ctx.arc(centerX / 2 
						,centerY / 2 
						,Math.abs(sx > canvasX(e.clientX) ? sx - canvasX(e.clientX) : canvasX(e.clientX) - sx) > Math.abs(e.clientY > sy ? e.clientY - sy : sy - e.clientY) ? (((canvasX(e.clientX) > sx) ? (canvasX(e.clientX) - sx) : (sx - canvasX(e.clientX)))) : (((canvasY(e.clientY) > sy) ? (canvasY(e.clientY) - sy) : (sy - canvasY(e.clientY))))
						,0
						,2 * Math.PI);
				ctx.stroke();
				
			}
			

			if (drawMode == 'line'){
				
				if (lineAct)
				{
					redraw();
					
					ctx.beginPath();
					ctx.strokeStyle = lineColor;
					ctx.moveTo(firstX,firstY);
					ctx.lineTo(canvasX(e.clientX),canvasY(e.clientY));
					ctx.stroke();
				}
								
			}			
		}
		
	};
	
	//버튼에서 손을 땠을 때
	canvas.onmouseup = function(e) {
		
		if (control == false){
			console.log('권한이 없음');
			return;
		}
		
		drawing = false;		

		if (drawMode == 'rectangle'){
			lastX = canvasX(e.clientX);
			lastY = canvasY(e.clientY);
			
			rectMaker(firstX,firstY,lastX,lastY,loginId,lineColor);
			
			var location = {};
			location.firstX = firstX;
			location.firstY = firstY;
			location.lastX = lastX;
			location.lastY = lastY;
			location.id = loginId;
			location.color = lineColor;
			location.mode = 'rectangle';
			connection.send(JSON.stringify(location));
		}
		
		if (drawMode == 'circle'){
			lastX = canvasX(e.clientX);
			lastY = canvasY(e.clientY);
			
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = firstX;
			lines[canvasLineCnt][1] = firstY;
			lines[canvasLineCnt][2] = loginId;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][4] = 'circle';
			lines[canvasLineCnt][5] = lineColor;
			lines[canvasLineCnt][6] = lastX;
			lines[canvasLineCnt][7] = lastY;
			canvasLineCnt++;
			
		}
		
		if (drawMode == 'line'){
			drawing = true;
			if (!(lineAct)){
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = firstX;
			lines[canvasLineCnt][1] = firstY;
			lines[canvasLineCnt][2] = loginId;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][5] = lineColor;
			canvasLineCnt++;
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = lastX;
			lines[canvasLineCnt][1] = lastY;
			lines[canvasLineCnt][2] = loginId;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][5] = lineColor;
			canvasLineCnt++;
			redraw();
			drawing = false;
			
			var location = {};
			location.firstX = firstX;
			location.firstY = firstY;
			location.lastX = lastX;
			location.lastY = lastY;
			location.id = loginId;
			location.color = lineColor;
			location.mode = 'line';
			connection.send(JSON.stringify(location));			
			}
		}
		
		//undo를 위한 id가 비어있는 점
		lines[canvasLineCnt] = new Array();
	    lines[canvasLineCnt][0] = sx;
	    lines[canvasLineCnt][1] = sy;
	    lines[canvasLineCnt][2] = 'none';
	    lines[canvasLineCnt][3] = cPage;

	    canvasLineCnt++;
	    
	    var location = {};
	    location.x = sx;
	    location.y = sy;
	    location.id = 'none';
	    location.mode = 'draw';
	    connection.send(JSON.stringify(location));
	};
	

	//채팅 관련 
	chatInputArea.onkeyup = function(e) {
		
		if (e.keyCode != 13) return;
	    
	    this.value = loginId+' : '+this.value.replace(/^\s+|\s+$/g, '');
	    if (!this.value.length) return;
		
	    var chatContent = {};
	    chatContent.text = this.value;
	    chatContent.mode = 'chat';    
	    connection.send(JSON.stringify(chatContent));
	    
	    appendDIV(this.value);
	    this.value = '';
	};

	
};

connection.onopen = function(){
	var userInfo = {};
	userInfo.mode = 'giveMyInfo';
	userInfo.id = loginId;
	connection.send(JSON.stringify(userInfo));
	
	if (userCount == 0){
		userList[userCount] = loginId;
		userCount++;
		reUserList();
	}
}
connection.onmessage = function(event){
	
	if (event instanceof ArrayBuffer || event instanceof DataView){
		
		appendDIV(event);
		return;
	}
	
	var drawData = JSON.parse(event.data);
	
	if (drawData.mode == 'giveMyInfo'){
		
		var otherId = drawData.id;
		for (var i = 0 ;i < userList.length;i++){
			if (userList[i] == otherId)
			{
				return;
			}
		}
		userList[userCount] = otherId;
		userCount++;
		reUserList();
	}
	
	if (drawData.mode == 'succeed'){
		
		if (drawData.id == loginId){
			control = true;
		}
	}
	
	if (drawData.mode == 'exit'){
		var tempid = drawData.id;
		for (var i = 0;i < userList.length;i++){
			if (tempid == userList[i]){
				 userList.splice(i,1);
    			 userCount = userList.length;
    			 reUserList();        			 
    			 break;        
			}
		}
		var creator = document.getElementById('creator').value;
		if (creator == loginId){
			control = true;
		}
	}
	
	
	if (drawData.mode == 'draw'){		
		lines[canvasLineCnt] = new Array();
		lines[canvasLineCnt][0] = drawData.x;
		lines[canvasLineCnt][1] = drawData.y;
		lines[canvasLineCnt][2] = drawData.id;
		lines[canvasLineCnt][3] = cPage;
		lines[canvasLineCnt][5] = drawData.color;

		canvasLineCnt++;
	}
	
	if (drawData.mode == 'undo'){
		var i = drawData.i;
		lines.splice(i-1,1);

		canvasLineCnt = lines.length;

	}
	
	if (drawData.mode == 'eraser'){
		var i = drawData.i;
		var temporary = new Array();
		temporary[2] = 'none';
		lines.splice(i,0,temporary);		
		lines.splice(i+1,0,temporary);
		lines.splice(i+2,1);
		canvasLineCnt = lines.length;		
	}

	redraw();

	if (drawData.mode == 'rectangle'){
		
		var firstX = drawData.firstX;
		var firstY = drawData.firstY;
		var lastX = drawData.lastX;
		var lastY = drawData.lastY;
		var id = drawData.id;
		var rectColor = drawData.color;
		
		rectMaker(firstX,firstY,lastX,lastY,id,rectColor);
	}
	if (drawData.mode == 'line'){
		
		lines[canvasLineCnt] = new Array();
		lines[canvasLineCnt][0] = drawData.firstX;
		lines[canvasLineCnt][1] = drawData.firstY;
		lines[canvasLineCnt][2] = drawData.id;
		lines[canvasLineCnt][3] = cPage;
		lines[canvasLineCnt][5] = drawData.color;
		canvasLineCnt++;
		
		lines[canvasLineCnt] = new Array();
		lines[canvasLineCnt][0] = drawData.lastX;
		lines[canvasLineCnt][1] = drawData.lastY;
		lines[canvasLineCnt][2] = drawData.id;
		lines[canvasLineCnt][3] = cPage;
		lines[canvasLineCnt][5] = drawData.color;
		canvasLineCnt++;
	}
	
	if (drawData.mode == 'uploadImage'){
		
		var imgSrc = drawData.data;
		endOfPage = drawData.pages;
		img = document.getElementById('image1');
		img.src = imgSrc;
		img.onload = function(){
			cPage = 0;
			imageOnly = document.getElementById('imageOnly');
			imagePaste = imageOnly.getContext('2d');
			imagePaste.drawImage(img,0,0);			
		};		
		lines = new Array();
		cPage = 0;
		canvasLineCnt = 0;
	}
	
	if (drawData.mode == 'backwardPage'){
		
		cPage--;
		
		var imgSrc = drawData.data;
		img = document.getElementById('image1');
		img.src = imgSrc;
		img.onload = function(){
			
			imageOnly = document.getElementById('imageOnly');
			imagePaste = imageOnly.getContext('2d');
			imagePaste.drawImage(img,0,0);			
		};		
	}
	
	if (drawData.mode == 'forwardPage'){
		cPage++;
		
		var imgSrc = drawData.data;
		img = document.getElementById('image1');
		img.src = imgSrc;
		img.onload = function(){
			imageOnly = document.getElementById('imageOnly');
			imagePaste = imageOnly.getContext('2d');
			imagePaste.drawImage(img,0,0);			
		};		
	}
	
	redraw();
	
	if (drawData.mode == 'chat'){
		
		appendDIV(drawData.text);		
	}
	
	if (drawData.mode =='file'){
		console.log('보내짐??');
	}

};
//실행취소 버튼
function canvasUndo(){
	
	var currentId = id;	   
	  
	   
	   for (var i = lines.length;i>0;i--){
		   
		   if (lines[i-1][2] == currentId){
			   
			   while ((lines[i-1][2] == currentId && lines[i-1][3] == cPage)
					   || (lines[i-1][2] == currentId && lines[i-1][4] == 'rectangle')){
				   
				   lines.splice(i-1,1);
				   var location = {};
				   location.i = i;
				   location.mode = 'undo';
				   connection.send(JSON.stringify(location));
				   i--;
				  
			   }				 
			   
			   redraw();
			   break;
		   }			   
	   }
	   canvasLineCnt = lines.length;		
};


function rectMaker(firstX,firstY,lastX,lastY,id,rectC){

	
	var fX = firstX;
	var fY = firstY;
	var lX = lastX;
	var lY = lastY;		
	var idd = id;
	var rectColor = rectC;
	
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++;
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = lX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++;
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = lX;
	lines[canvasLineCnt][1] = lY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++;
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = lY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++; 
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;
	canvasLineCnt++;	

	
	redraw();
	
	return;
}	

function canvasPen(){	
	drawMode = 'pen';
	return;
}

function canvasER(){	
	drawMode = 'eraser';
	return;
}

function canvasRect(){
	drawMode = 'rectangle';
	return;
}

function canvasCircle(){
	drawMode = 'circle';
	return;
}

function canvasLine(){
	drawMode = 'line';
	return;
}

function canvasUpload(){
	
	if (control == false){
		console.log('권한이 없음');
		return;
	}
	
	var uploadBtn = document.getElementById('file');
	uploadBtn.click();
}

function UploadtoServer(){
	
	if (control == false){
		console.log('권한이 없음');
		return;
	}
	
	var form = document.getElementById('uploadForm');
	var formData = new FormData(form);
	
	
	$.ajax({
		
		url:'uploadPDF',
		type: 'post',
        enctype: 'multipart/form-data',
        data: formData,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	if(data != '') {
        		console.log('success');
        		lines = new Array();
        		canvasLineCnt = 0;
        		cPage = 0;
        		endOfPage = data;
        		console.log('마지막 페이지:' + endOfPage);
        		img = document.getElementById('image1');
        		img.src = "https://s3.ap-northeast-2.amazonaws.com/terracehouse-user-bucket/tr-user-files/"+loginId+"/"+todayString+"/"+terraceName+"image/myImage"+cPage+".png";
        		img.onload = function(){
        			
        			var tempCanvas = document.getElementById('imageOnly');
        			var tempCtx = tempCanvas.getContext('2d');
        			tempCtx.drawImage(img,0,0);
        			redraw();
        			
        			var imgData = {};
        			imgData.data = tempCanvas.toDataURL();
        			imgData.pages = endOfPage;
        			imgData.mode = 'uploadImage';
        			connection.send(JSON.stringify(imgData));        			
        		};
        	}
        },
        error: function (e) {

           console.log('실패');

        }
		
	});
	
}

document.getElementById('canvasDownload').addEventListener('click',
	function(){
	
	var tempCanvas = document.getElementById('imageOnly');
	var tempImage = document.getElementById('image1');
	var tempCtx = tempCanvas.getContext('2d');
	
	tempCanvas.setAttribute("width","595px");
	tempCanvas.setAttribute("height","842px");
	
	tempCtx.drawImage(tempImage,0,0);
	
	tempCtx.lineCap="round";
	tempCtx.lineWidth = lwidth;
	for(var i = 0;i < lines.length;i++){
		
		if (lines[i][2] == 'none')
		{
			continue;
		}
		if (i+1 != lines.length && lines[i][3] == cPage){
		tempCtx.strokeStyle = lines[i][5];
		tempCtx.beginPath();
		tempCtx.moveTo(lines[i][0],lines[i][1]);
		tempCtx.lineTo(lines[i+1][0],lines[i+1][1]);
		tempCtx.stroke();
		}
	}
	
	downloadCanvas(this, 'imageOnly', 'savedImg.png');
			
},false);

function downloadCanvas(link, canvasId, filename) {
    link.href = document.getElementById(canvasId).toDataURL();
    link.download = filename;
    
    var tempCanvas = document.getElementById('imageOnly');
	var tempImage = document.getElementById('image1');
	var tempCtx = tempCanvas.getContext('2d');
	
	tempCanvas.setAttribute("width","595px");
	tempCanvas.setAttribute("height","842px");
	
	tempCtx.drawImage(tempImage,0,0); 
}

function canvasBlackBoard(){
	
	var creator = document.getElementById('creator').value;
	console.log('creator: '+creator);
	window.open('myBlackBoard?creator='+creator+'&pages='+endOfPage,'myBlackBoard','top=50,left=600,width=800,height=750');
}

function backwardPage(inputId){
	var tempId;
	
	if (control == false){
		console.log('권한이 없음');
		return;
	}

	if (loginId != null)
	{
		tempId = inputId;
	}
	if (cPage > 0)
	{
		cPage--;
	}
	else{
		alert('첫페이지입니다');
		return;
	}
	img.src = "https://s3.ap-northeast-2.amazonaws.com/terracehouse-user-bucket/tr-user-files/"+loginId+"/"+todayString+"/"+terraceName+"image/myImage"+cPage+".png";
	img = document.getElementById('image1');
	img.src = stringURL;
	img.onload = function(){
		imageOnly = document.getElementById('imageOnly');
		imagePaste = imageOnly.getContext('2d');
		imagePaste.drawImage(img,0,0);
		redraw();
		
		var imgData = {};
		imgData.data = imageOnly.toDataURL();
		
		imgData.mode = 'backwardPage'; 		
		connection.send(JSON.stringify(imgData));
	};	
	
	
	
}

function forwardPage(inputId){
	var tempId;
	
	if (control == false){
		console.log('권한이 없음');
		return;
	}

	if (loginId != null)
	{
		tempId = inputId;
	}
	if (cPage < endOfPage)
	{
		cPage++;
	}
	else{
		console.log('끝페이지 : '+endOfPage);
		alert('마지막 페이지입니다');
		return;
	}
	var stringURL = img.src = "https://s3.ap-northeast-2.amazonaws.com/terracehouse-user-bucket/tr-user-files/"+loginId+"/"+todayString+"/"+terraceName+"image/myImage"+cPage+".png";
	img = document.getElementById('image1');
	img.src = stringURL;
	img.onload = function(){
		imageOnly = document.getElementById('imageOnly');
		imagePaste = imageOnly.getContext('2d');
		imagePaste.drawImage(img,0,0);
		redraw();
		
		var imgData = {};
		imgData.data = imageOnly.toDataURL();
		
		imgData.mode = 'forwardPage'; 		
		connection.send(JSON.stringify(imgData));
	};	
}

//선 배열대로 캔버스에 그리는 펑션
function redraw(){
	
	
	canvas = document.getElementById('mycanvas');
	ctx = canvas.getContext('2d');

	canvas.setAttribute("width","595px");
	canvas.setAttribute("height","842px");
	ctx.lineWidth = lwidth;
	ctx.lineCap="round";
	
	for(var i = 0;i < lines.length;i++){
		
		if (lines[i][2] == 'none')
		{
			continue;
		}
		if (i+1 != lines.length && lines[i][3] == cPage){
			
		ctx.strokeStyle = lines[i][5];
		ctx.beginPath();
		if(lines[i][4] == 'circle'){
			/*var centerX = lines[i][0]+lines[i][6];
			var centerY = lines[i][1]+lines[i][7];
			ctx.arc(lines[i][0]+lines[i][],0,2 * Math.PI);
			ctx.stroke();*/
			
		}
		ctx.moveTo(lines[i][0],lines[i][1]);
		ctx.lineTo(lines[i+1][0],lines[i+1][1]);
		ctx.stroke();
		}
	}		
	return;		
}

function colorPicker(){
	var colorCode = document.getElementById('lineColor').value;
	lineColor = colorCode;
}

//좌표를 얻기 위한 펑션
function canvasX(clientX) {
	var bound = canvas.getBoundingClientRect();
	var bw = 5;
	return (clientX - bound.left - bw) * (canvas.width / (bound.width - bw * 2));
};

function canvasY(clientY) {
	var bound = canvas.getBoundingClientRect();
	var bw = 5;
	return (clientY - bound.top - bw) * (canvas.height / (bound.height - bw * 2));
};

//유저 리스트
function reUserList(){
	var userSpace = document.getElementById('userList');
	var listCode = '';
		listCode += '<select id ="ruler">';
		for (var i = 0; i < userList.length ;i++){
			
			listCode += '<option value ="';
			listCode += userList[i];
			listCode += '">'+userList[i]+'</option>"'; 			
		}
		listCode += '</select>';
		var pSpace = document.getElementById('userList');
		userSpace.innerHTML = '';
		userSpace.innerHTML = listCode;	
}

//권한 넘기기
function power(){
	var selectedID;
	if (control){
		var selectedID = document.getElementById('ruler').value;
		console.log(selectedID);
		var succeed = {};
		succeed.id = selectedID;
		succeed.mode = 'succeed';
		connection.send(JSON.stringify(succeed));
	}
	control = false;
	var creator = document.getElementById('creator');
	creator.value = selectedId;
}

//채팅 관련
function appendDIV(event) {
    var div = document.createElement('div');
    div.innerHTML = event.data || event;
    chatContainer.insertBefore(div, /*chatContainer.lastChild*/null);
    div.tabIndex = 0;
    div.focus();
    document.getElementById('input-text-chat').focus();
};

//파일공유

connection.enableFileSharing = true;
connection.filesContainer = document.getElementById('file-container');
document.getElementById('share-file').disabled = false;

document.getElementById('share-file').onclick = function() {
    var fileSelector = new FileSelector();
    fileSelector.selectSingleFile(function(file) {
    	connection.send(file);
    });
};