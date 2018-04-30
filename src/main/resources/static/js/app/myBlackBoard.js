/**
 * 
 */
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
var control = true;
var creator;
var lwidth = 5;
var terraceName;
var terrace_room_number;
var imageArray = new Array();
window.onload = start();

function start(){		
	
	terraceName = document.getElementById('terrace_name').value;
	terrace_room_number = document.getElementById('terrace_room_number').value;
	loginId = 'I';
	creator = document.getElementById('creator').value;
	console.log('creator : '+creator);
	endOfPage = document.getElementById('pages').value;
	console.log(endOfPage);
	makeHiddenImg(endOfPage);
	imageOnly = document.getElementById('imageOnly');
	imagePaste = imageOnly.getContext('2d');
	canvas = document.getElementById('mycanvas');	
	ctx = canvas.getContext('2d');
	img = document.getElementById('image1');	
	if (window.opener.document.getElementById('hi0') != null 
			&& window.opener.document.getElementById('hi0') != ''){
		img.src = window.opener.document.getElementById('hi0').src;
	}
	
	img.onload = function (){
		
			imagePaste.drawImage(img,0,0);
		
	};
	

	ctx.lineCap="round";
	ctx.lineWidth = lwidth;
	//캔버스위를 클릭 시 이벤트
	canvas.onmousedown = function(e) {
		
		loginId = 'I';
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
		
		if (drawMode == 'circle'){
			firstX = sx;
			firstY = sy;
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
			lines[canvasLineCnt][8] = lwidth;

			canvasLineCnt++;
			 
			//움직일 때마다 좌표를 새로 받음	
			sx = canvasX(e.clientX);
			sy = canvasY(e.clientY);
			
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = sx;
			lines[canvasLineCnt][1] = sy;
			lines[canvasLineCnt][2] = id;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][5] = lineColor;
			lines[canvasLineCnt][8] = lwidth;

			canvasLineCnt++;
			
			
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
							break;					
						}
				}			
				return;				
			}
			if (drawMode == 'rectangle'){
						
				redraw();

				ctx.strokeStyle=lineColor;
				ctx.lineWidth = lwidth;
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
					ctx.lineWidth = lwidth;
					ctx.strokeStyle = lineColor;
					ctx.moveTo(firstX,firstY);
					ctx.lineTo(canvasX(e.clientX),canvasY(e.clientY));
					ctx.stroke();
				}
								
			}			
			
			if (drawMode == 'circle'){
				redraw();
				ctx.strokeStyle = lineColor;
				ctx.lineWidth = lwidth;
				ctx.beginPath();
				ctx.moveTo(firstX, firstY + (canvasY(e.clientY) - firstY) / 2);
				ctx.bezierCurveTo(firstX, firstY, canvasX(e.clientX), firstY, canvasX(e.clientX), firstY + (canvasY(e.clientY) - firstY) / 2);
				ctx.bezierCurveTo(canvasX(e.clientX), canvasY(e.clientY), firstX, canvasY(e.clientY), firstX, firstY + (canvasY(e.clientY) - firstY) / 2);
				/*ctx.closePath();*/				    
				ctx.stroke();
				
			}
			
		}
		
	};
	
	//버튼에서 손을 땠을 때
	canvas.onmouseup = function(e) {
		
		
		drawing = false;		

		if (drawMode == 'rectangle'){
			lastX = canvasX(e.clientX);
			lastY = canvasY(e.clientY);
			
			rectMaker(firstX,firstY,lastX,lastY,loginId,lineColor,lwidth);
			
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
			lines[canvasLineCnt][8] = lwidth;
			canvasLineCnt++;
			lines[canvasLineCnt] = new Array();
			lines[canvasLineCnt][0] = lastX;
			lines[canvasLineCnt][1] = lastY;
			lines[canvasLineCnt][2] = loginId;
			lines[canvasLineCnt][3] = cPage;
			lines[canvasLineCnt][5] = lineColor;
			lines[canvasLineCnt][8] = lwidth;
			canvasLineCnt++;
			redraw();
			drawing = false;
				
			}
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
			lines[canvasLineCnt][8] = lwidth;
			canvasLineCnt++;		
		}
		
		
		//undo를 위한 id가 비어있는 점
		lines[canvasLineCnt] = new Array();
	    lines[canvasLineCnt][0] = sx;
	    lines[canvasLineCnt][1] = sy;
	    lines[canvasLineCnt][2] = 'none';
	    lines[canvasLineCnt][3] = cPage;

	    canvasLineCnt++;
	    
	};
};

document.getElementById('canvasDownload').addEventListener('click',
		function(){
		
		var tempCanvas = document.getElementById('imageOnly');
		var tempImage = document.getElementById('image1');
		var tempCtx = tempCanvas.getContext('2d');
		
		tempCanvas.setAttribute("width","595px");
		tempCanvas.setAttribute("height","842px");
		
		tempCtx.drawImage(tempImage,0,0);
		
		tempCtx.lineCap="round";
		for(var i = 0;i < lines.length;i++){
			
			if (lines[i][2] == 'none')
			{
				continue;
			}
			if (i+1 != lines.length && lines[i][3] == cPage){
				
			tempCtx.strokeStyle = lines[i][5];
			tempCtx.lineWidth = lines[i][8];
			tempCtx.beginPath();
			if(lines[i][4] == 'circle'){			
				tempCtx.moveTo(lines[i][0], lines[i][1] + (lines[i][7] - lines[i][1]) / 2);
				tempCtx.bezierCurveTo(lines[i][0], lines[i][1], lines[i][6], lines[i][1], lines[i][6], lines[i][1] + (lines[i][7] - lines[i][1]) / 2);
				tempCtx.bezierCurveTo(lines[i][6], lines[i][7], lines[i][0], lines[i][7], lines[i][0], lines[i][1] + (lines[i][7] - lines[i][1]) / 2);
				/*tempCtx.closePath();*/				    
				tempCtx.stroke();
				tempCtx.stroke();
				continue;
			}
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

document.getElementById('save-progress').onclick = function(){
		

	var eop = endOfPage;
	
	for (var i = 0; i < eop ;i++){
		console.log('i : '+i);
		var strr = 'hi'+i;
		var tempCanvas = document.getElementById('imageOnly');
		
		var tempImage = document.getElementById(strr);
		console.log(tempImage.src);
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
			if (j+1 != lines.length && lines[j][3] == i){
			console.log('i :'+i);	
			tempCtx.strokeStyle = lines[j][5];
			tempCtx.lineWidth = lines[j][8];
			tempCtx.beginPath();
			if(lines[j][4] == 'circle'){			
				tempCtx.moveTo(lines[j][0], lines[j][1] + (lines[j][7] - lines[j][1]) / 2);
				tempCtx.bezierCurveTo(lines[j][0], lines[j][1], lines[j][6], lines[j][1], lines[j][6], lines[j][1] + (lines[j][7] - lines[j][1]) / 2);
				tempCtx.bezierCurveTo(lines[j][6], lines[j][7], lines[j][0], lines[j][7], lines[j][0], lines[j][1] + (lines[j][7] - lines[j][1]) / 2);
				/*ctx.closePath();*/				    
				tempCtx.stroke();
				
				continue;
			}
			tempCtx.moveTo(lines[j][0],lines[j][1]);
			tempCtx.lineTo(lines[j+1][0],lines[j+1][1]);
			tempCtx.stroke();
			}
		}
		imageArray[i] =  tempCanvas.toDataURL('image/png');	
		console.log('배열 크기:'+imageArray.length);
	}
	
	if (imageArray[endOfPage-1] != '' && imageArray[endOfPage-1] != null){
		console.log('에이잭스 실행');
		var terrace_room_number = document.getElementById('terrace_room_number').value;
		$.ajax({
				
			url:'makePersonalPDF',
			type:'POST',
			traditional: true,
			data:{'imageArray' : imageArray, 'terrace_room_number' : terrace_room_number},
				
			success:function(e){
				console.log('보내짐');	
				$.ajax({
					url:'endOfTerraceRoom',
					data:{'terrace_room_number':terrace_room_number},
					type:'get'
				});
				var tempCanvas = document.getElementById('imageOnly');
				var strr = 'hi'+cPage;
				var tempImage = document.getElementById(strr);
				console.log(tempImage.src);
				var tempCtx = tempCanvas.getContext('2d');
				
				tempCanvas.setAttribute("width","595px");
				tempCanvas.setAttribute("height","842px");
				
				tempCtx.drawImage(tempImage,0,0);
				redraw();
				
			}
		});
		
	}
};




function canvasUndo(){
	
	var currentId = id;	   
	  
	   
	   for (var i = lines.length;i>0;i--){
		   
		   if (lines[i-1][2] == currentId  && lines[i-1][3] == cPage){
			   
			   while ((lines[i-1][2] == currentId && lines[i-1][3] == cPage)
					   || (lines[i-1][2] == currentId && lines[i-1][4] == 'rectangle')){
				   
				   lines.splice(i-1,1);

				   i--;
				  
			   }				 
			   
			   redraw();
			   break;
		   }			   
	   }
	   canvasLineCnt = lines.length;		
};


function rectMaker(firstX,firstY,lastX,lastY,id,rectC,lineWid){

	
	var fX = firstX;
	var fY = firstY;
	var lX = lastX;
	var lY = lastY;		
	var idd = id;
	var rectColor = rectC;
	var wid = lineWid;
	
	
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;
	lines[canvasLineCnt][8] = wid;

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
	lines[canvasLineCnt][8] = wid;

	canvasLineCnt++; 
	lines[canvasLineCnt] = new Array();
	lines[canvasLineCnt][0] = fX;
	lines[canvasLineCnt][1] = fY;
	lines[canvasLineCnt][2] = idd;
	lines[canvasLineCnt][3] = cPage;
	lines[canvasLineCnt][4] = 'rectangle';
	lines[canvasLineCnt][5] = rectColor;
	lines[canvasLineCnt][8] = wid;
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

function canvasCircle(){
	drawMode = 'circle';
	
	return;
}

function changeLineWidth(){
	var ww = document.getElementById('lineWidth').value;
	lwidth = ww;
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
	if (cPage <= 0){
		return;
	}
	if (cPage > 0)
	{
		cPage--;
	}
	var str = 'hi'+cPage;
	img = document.getElementById('image1');
	img.src = document.getElementById(str).src;
	img.onload = function(){
		imageOnly = document.getElementById('imageOnly');
		imagePaste = imageOnly.getContext('2d');
		imagePaste.drawImage(img,0,0);
		redraw();
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
	if (cPage >= endOfPage-1){
		return;
	}
	if (cPage < endOfPage-1)
	{
		cPage++;
	}
	
	var str = 'hi'+cPage;
	img = document.getElementById('image1');
	img.src = document.getElementById(str).src;
	img.onload = function(){
		imageOnly = document.getElementById('imageOnly');
		imagePaste = imageOnly.getContext('2d');
		imagePaste.drawImage(img,0,0);
		redraw();
		
		
	};	
}
	
function makeHiddenImg(pages){
	console.log('실행됨');
	var hiddenImg = document.getElementById('hiddenImg');
	var str = '';
	var tempImgSrc = '';
	
	for (var i = 0; i < pages; i++){
		tempImgSrc = 'hi'+i;
		str += '<img  hidden="hidden" id = "hi'+i+'"';
		str += 'src="';
		str += window.opener.document.getElementById(tempImgSrc).src+'" crossOrigin=anonymous>';
	}
	
	hiddenImg.innerHTML = str;
}

function redraw(){
	
	
	canvas = document.getElementById('mycanvas');
	ctx = canvas.getContext('2d');

	canvas.setAttribute("width","595px");
	canvas.setAttribute("height","842px");
	
	ctx.lineCap="round";
	ctx.lineWidth = lwidth;
	for(var i = 0;i < lines.length;i++){
		
		if (lines[i][2] == 'none')
		{
			continue;
		}
		if (i+1 != lines.length && lines[i][3] == cPage){
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