/**
 * TerraceRoom JavaScript 
 * Modifi BY SEO
 */
var connection = new RTCMultiConnection();


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


//공용 칠판 영역
window.onload = start();

function start(){		
	

	imageOnly = document.getElementById('imageOnly');
	imagePaste = imageOnly.getContext('2d');
	canvas = document.getElementById('mycanvas');	
	ctx = canvas.getContext('2d');
	img = document.getElementById('image1');
	imagePaste.drawImage(img,0,0);
	

	ctx.lineCap="round";
	//캔버스위를 클릭 시 이벤트
	canvas.onmousedown = function(e) {
		

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
	   /* lines[canvasLineCnt][3] = cPage;*/
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
		
		if (drawMode == 'line'){
			drawing = true;
			if (!(lineAct)){
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = firstX;
			lines[canvasLineCnt][1] = firstY;
			lines[canvasLineCnt][2] = loginId;
			lines[canvasLineCnt][5] = lineColor;
			canvasLineCnt++;
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = lastX;
			lines[canvasLineCnt][1] = lastY;
			lines[canvasLineCnt][2] = loginId;
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
	   /* lines[canvasLineCnt][3] = cPage;*/

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
	    
	    this.value = this.value.replace(/^\s+|\s+$/g, '');
	    if (!this.value.length) return;
		
	    var chatContent = {};
	    chatContent.text = this.value;
	    chatContent.mode = 'chat';    
	    connection.send(JSON.stringify(chatContent));
	    
	    appendDIV(this.value);
	    this.value = '';
	};

	
};
connection.onmessage = function(event){
	
	var drawData = JSON.parse(event.data);
	
	if (drawData.mode == 'draw'){		
		lines[canvasLineCnt] = new Array();
		lines[canvasLineCnt][0] = drawData.x;
		lines[canvasLineCnt][1] = drawData.y;
		lines[canvasLineCnt][2] = drawData.id;
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
		console.log('수신한 id : ' + drawData.id);
		lines[canvasLineCnt] = new Array();
		lines[canvasLineCnt][0] = drawData.firstX;
		lines[canvasLineCnt][1] = drawData.firstY;
		lines[canvasLineCnt][2] = drawData.id;
		lines[canvasLineCnt][5] = drawData.color;
		canvasLineCnt++;
		
		lines[canvasLineCnt] = new Array();
		lines[canvasLineCnt][0] = drawData.lastX;
		lines[canvasLineCnt][1] = drawData.lastY;
		lines[canvasLineCnt][2] = drawData.id;
		lines[canvasLineCnt][5] = drawData.color;
		canvasLineCnt++;
	}
	redraw();
	
	if (drawData.mode == 'chat'){
		
		appendDIV(drawData.text);		
	}

};
//실행취소 버튼
function canvasUndo(){
	
	var currentId = id;	   
	  
	   
	   for (var i = lines.length;i>0;i--){
		   
		   if (lines[i-1][2] == currentId){
			   
			   while ((lines[i-1][2] == currentId /*&& lines[i-1][3] == cPage*/)
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
	/*lines[canvasLineCnt][3] = cPage;*/
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++;
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = lX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	/*lines[canvasLineCnt][3] = cPage;*/
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++;
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = lX;
	lines[canvasLineCnt][1] = lY;
	lines[canvasLineCnt][2] = idd;
	/*lines[canvasLineCnt][3] = cPage;*/
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++;
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = lY;
	lines[canvasLineCnt][2] = idd;
	/*lines[canvasLineCnt][3] = cPage;*/
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;

	canvasLineCnt++; 
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	/*lines[canvasLineCnt][3] = cPage;*/
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


function canvasLine(){
	drawMode = 'line';
	return;
}

function canvasUpload(){
	var uploadBtn = document.getElementById('file');
	uploadBtn.click();
}

function UploadtoServer(){
	var form = document.getElementById('uploadForm');
	var formData = new FormData(form);
	
	$.ajax({
		
		url:'uploadPDF',
		type: 'get',
        enctype: 'multipart/form-data',
        data: formdata,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

           console.log('성공');

        },
        error: function (e) {

           console.log('실패');

        }
		
	});
	
}

function canvasBlackBoard(){
	
	window.open('myBlackBoard','myBlackBoard','top=50,left=600,width=800,height=750');
}

//선 배열대로 캔버스에 그리는 펑션
function redraw(){
	
	canvas = document.getElementById('mycanvas');
	ctx = canvas.getContext('2d');

	canvas.setAttribute("width","500px");
	canvas.setAttribute("height","800px");
	
	ctx.lineCap="round";
	
	for(var i = 0;i < lines.length;i++){
		
		if (lines[i][2] == 'none')
		{
			continue;
		}
		if (i+1 != lines.length /*&& lines[i][3] == cPage*/){
		ctx.strokeStyle = lines[i][5];
		ctx.beginPath();
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

//채팅 관련
function appendDIV(event) {
    var div = document.createElement('div');
    div.innerHTML = event.data || event;
    chatContainer.insertBefore(div, /*chatContainer.lastChild*/null);
    div.tabIndex = 0;
    div.focus();
    document.getElementById('input-text-chat').focus();
};