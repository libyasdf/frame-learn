var App = angular.module('App', []);
App.controller('bodyCtrl', ["$scope","$http", function ($scope,$http) {
var getFlowStatusURL= basePath+"/workflow/getFlowStatus";
$("body").height(document.body.clientHeight)
var mx=$("#myDiv").width();
var my=$("#myDiv").height();
var api = frameElement.api;
var id=api.data.workflowid;
var workitemid = api.data.workitemid;
var fileTypeId=api.data.fileTypeId;
var recordId=api.data.recordId;
var sysId=api.data.sysId;
var theCanvas='';
var imageShapes=[];
var lineShapes=[];
httpRequest("GET",getFlowStatusURL+"?workflowId="+id+"&fileTypeId="+fileTypeId+"&recordId="+recordId+"&sysId="+sysId,"",function(data){
	if(data){
		imageShapes=data.nodes?data.nodes:[];
		lineShapes=data.linkLines?data.linkLines:[];
		for(var s=0;s<imageShapes.length;s++){
				if(imageShapes[s].type!="1"&&imageShapes[s].type!="2"&&imageShapes[s].type!="17"){
					if(imageShapes[s].x+105>=mx){
						mx=imageShapes[s].x+105;
					}
					if(imageShapes[s].y+70>=my){
						my=imageShapes[s].y+70;
					}
				}else{
					if(imageShapes[s].x+40>=mx){
						mx=imageShapes[s].x+40;
					}
					if(imageShapes[s].y+40>=my){
						my=imageShapes[s].y+40;
					}
				}
			}
		$("header").width(mx-30);
		$("#myCanvas").attr("height",$(window).height());
		$("#myCanvas").attr("width",$(window).width());
		drawShapes();
	}
});
function httpRequest(method,url,data,callback){
	$http({
		method:method,
		url:url,
		dataType:"json",
		data: data
	}).success(function(datas){
		if(datas){
			callback(datas);
		}
	}).error(function(data,status,headers,cfg) {
		alert("获取失败，请刷新重试或联系管理员");
	});
}
function drawShapes() {
	theCanvas = document.getElementById("myCanvas");
	var context = theCanvas.getContext("2d");
	var maxWidth=105;
	var maxHeight=70;
	var minWidth=40;
	var minHeight=40;
	var lineDistance=7;
	var L=10.0;
	var d=30.0/180.0*Math.PI;
	context.fillStyle = "#FFF";
	context.clearRect(0,0,theCanvas.width,theCanvas.height);
	context.fillRect(0,0,theCanvas.width,theCanvas.height);
	context.beginPath();
	context.lineWidth=0.3;
	context.strokeStyle="#CCC";
	for(var lx=15;lx<theCanvas.width;lx=lx+15){
		if(lx%60!=0){
			drawOneLine(lx,0,lx,theCanvas.height,context);
		}
	}
	for(var ly=15;ly<theCanvas.height;ly=ly+15){
		if(ly%60!=0){
			drawOneLine(0,ly,theCanvas.width,ly,context);
		}
	}
	context.stroke();
	context.beginPath();
	context.lineWidth=0.6;
	context.strokeStyle="#CCC";
	for(var lx=60;lx<theCanvas.width;lx=lx+60){
		drawOneLine(lx,0,lx,theCanvas.height,context);
	}
	for(var ly=60;ly<theCanvas.height;ly=ly+60){
		drawOneLine(0,ly,theCanvas.width,ly,context);
	}
	context.stroke();
	var exampleImage=new Image();
	exampleImage.src="../image/flowstatus/example.png";//示例
	context.drawImage(exampleImage,15,5,58,89);
	for (var i=0;i<imageShapes.length;i++) {
		var image1=new Image();
		if(imageShapes[i].type=="1"){
			if(imageShapes[i].processStatus==""){
				image1.src="../image/flowstatus/start1.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
				image1.src="../image/flowstatus/start2.png";//办理中
			}else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="../image/flowstatus/start3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
				image1.src="../image/flowstatus/start3.png";//办结
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="../image/flowstatus/start3.png";//已办
			}else{
				image1.src="../image/flowstatus/start1.png";
			}
		}else if(imageShapes[i].type=="2"){
			image1.src="../image/flowstatus/end3.png";
		}else if(imageShapes[i].type=="3"){
			if(imageShapes[i].processStatus== ""){
				image1.src="../image/flowstatus/entitynode1.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
				image1.src="../image/flowstatus/entitynode2.png";//办理中
			}else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="../image/flowstatus/entitynode31.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
				image1.src="../image/flowstatus/entitynode32.png";//办结
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="../image/flowstatus/entitynode3.png";//已办
			}else{
				image1.src="../image/flowstatus/entitynode3.png";
			}
		}else if(imageShapes[i].type=="4"){
			if(imageShapes[i].processStatus== ""){
				image1.src="../image/flowstatus/subflownode1.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
				image1.src="../image/flowstatus/subflownode2.png";//办理中
			}else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="../image/flowstatus/subflownode3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
				image1.src="../image/flowstatus/subflownode3.png";//办结
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="../image/flowstatus/subflownode3.png";//已办
			}else{
				image1.src="../image/flowstatus/subflownode3.png";
			}
		}else if(imageShapes[i].type=="5"){
			if(imageShapes[i].processStatus== ""){
				image1.src="../image/flowstatus/freenode1.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
				image1.src="../image/flowstatus/freenode2.png";//办理中
			}else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="../image/flowstatus/freenode3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
				image1.src="../image/flowstatus/freenode3.png";//办结
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="../image/flowstatus/freenode3.png";//已办
			}else{
				image1.src="../image/flowstatus/freenode3.png";
			}
		}else if(imageShapes[i].type=="6"){
			if(imageShapes[i].processStatus== ""){
				image1.src="../image/flowstatus/autonode1.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
				image1.src="../image/flowstatus/autonode2.png";//办理中
			}else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="../image/flowstatus/autonode3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
				image1.src="../image/flowstatus/autonode3.png";//办结
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="../image/flowstatus/autonode3.png";//已办
			}else{
				image1.src="../image/flowstatus/autonode3.png";
			}
		}else if(imageShapes[i].type=="17"){
			if(imageShapes[i].processStatus== ""){
				image1.src="../image/flowstatus/merage1.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
				image1.src="../image/flowstatus/merage2.png";//办理中
			}else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="../image/flowstatus/merage3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
				image1.src="../image/flowstatus/merage3.png";//办结
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="../image/flowstatus/merage3.png";//已办
			}else{
				image1.src="../image/flowstatus/merage3.png";
			}
		}
		var wid=minWidth;
		var hei=minHeight;
		if(imageShapes[i].type!="1"&&imageShapes[i].type!="2"&&imageShapes[i].type!="17"){
			wid=maxWidth;
			hei=maxHeight;
		}
		context.drawImage(image1,imageShapes[i].x,imageShapes[i].y,wid,hei);
		context.fillStyle = "#000";
		var num=Math.ceil(imageShapes[i].label.length/9.0);
		if(num>=1){
			var showWordList=[];
			for(var p=0;p<num;p++){
				var word=imageShapes[i].label.substr(p*9,9)
				var width=context.measureText(word).width;
				var moreWidth=(width-wid)/2.0;
				if(imageShapes[i].type!="1"&&imageShapes[i].type!="2"&&imageShapes[i].type!="17"){
					context.fillText(word,imageShapes[i].x-moreWidth,imageShapes[i].y+hei/2+p*8);
				}else{
					context.fillText(word,imageShapes[i].x-moreWidth,imageShapes[i].y+hei+8+p*8);
				}
			}
		}
	} 	
	for(var i=0;i<lineShapes.length; i++){
		context.beginPath();
		context.lineWidth="1.5";
		context.strokeStyle="#000"; 
		if(lineShapes[i].isSelect){
			context.strokeStyle="#33A1C9";
		}
		context.moveTo(lineShapes[i].vbNodeStart,lineShapes[i].vbNodeStartY);
		var a=0
		var b=0;
		var L0=1
		if(lineShapes[i].x1!=""&&lineShapes[i].y1!=""&&lineShapes[i].x2!=""&&lineShapes[i].y2!=""){
			context.lineTo(lineShapes[i].x1,lineShapes[i].y1);
			context.lineTo(lineShapes[i].x2,lineShapes[i].y2);
			L0=Math.sqrt(Math.pow(lineShapes[i].vbNodeEnd-lineShapes[i].x2,2)+Math.pow(lineShapes[i].vbNodeEndY-lineShapes[i].y2,2)); 
			a=(lineShapes[i].x2-lineShapes[i].vbNodeEnd)/L0;
			b=(lineShapes[i].y2-lineShapes[i].vbNodeEndY)/L0;
		}else{
			L0=Math.sqrt(Math.pow(lineShapes[i].vbNodeEnd-lineShapes[i].vbNodeStart,2)+Math.pow(lineShapes[i].vbNodeEndY-lineShapes[i].vbNodeStartY,2)); 
			a=(lineShapes[i].vbNodeStart-lineShapes[i].vbNodeEnd)/L0;
			b=(lineShapes[i].vbNodeStartY-lineShapes[i].vbNodeEndY)/L0;
		}
		context.lineTo(lineShapes[i].vbNodeEnd,lineShapes[i].vbNodeEndY);
		for(var n=0;n<2;n++){
			lineShapes[i].vbNodeEnd=parseFloat(lineShapes[i].vbNodeEnd);
			lineShapes[i].vbNodeEndY=parseFloat(lineShapes[i].vbNodeEndY);					
			var x=lineShapes[i].vbNodeEnd+L*(a*Math.cos(d)-b*Math.sin(n==0?d:-d));   
			var y=lineShapes[i].vbNodeEndY+L*(a*Math.sin(n==0?d:-d)+b*Math.cos(d));
			context.moveTo(lineShapes[i].vbNodeEnd,lineShapes[i].vbNodeEndY);
			context.lineTo(x,y);
		}
		context.stroke();
		if(lineShapes[i].isSelect&&lineShapes[i].x1!=""&&lineShapes[i].y1!=""&&lineShapes[i].x2!=""&&lineShapes[i].y2!=""){
			drawArc(lineShapes[i].x1,lineShapes[i].y1);
			drawArc(lineShapes[i].x2,lineShapes[i].y2);
		}
	}
	theCanvas.addEventListener("mousedown", mouseDownListener, false);
}
function mouseDownListener(evt) {
	bRect = theCanvas.getBoundingClientRect();
		mouseX = (evt.clientX - bRect.left)*(theCanvas.width/bRect.width);
		mouseY = (evt.clientY - bRect.top)*(theCanvas.height/bRect.height);
		$scope.isJump=false;
		for (var i=imageShapes.length-1; i >=0; i--) {
				if	(hitImageTest(imageShapes[i], mouseX, mouseY,imageShapes[i].type)&&(imageShapes[i].type=="1"||(imageShapes[i].type=="3"&&imageShapes[i].zWho!="1"))) {
					console.log(imageShapes[i]);
					$scope.isJump=true;
					$scope.nodeId=imageShapes[i].id;
					$scope.nodeName=imageShapes[i].label;
					$scope.$apply();
					break;
				}else{
					$scope.nodeId="";
					$scope.nodeName="此类型节点不允许跳";
					$scope.$apply();
				}
		}
}
$scope.jump=function(){
	if($scope.isJump){
		$.dialog({
			id : 'jumpNode',
			title : "选择参与者",
			lock : true,
			padding : 0,
			width : 1000,
			height : 450,
			zIndex : 2333,
			max:false,
			min:false,
			content : 'url:'+basePath+"/product/workflow/html/chooseUser.html",
			data:{
				wfleveId:$scope.nodeId,
				workitemid:workitemid
			},
			background : '#fffdee', /* 背景色 */
			opacity : 0.5, /* 透明度 */
			cancelVal : '关闭',
			cancel : function(){
				
			},
			button : [ {
				name : '确定',
				callback : function() {
					this.content.jumpNode();
				},
				focus : true
			} ]
		});
	}else{
		
	}
}
function hitImageTest(shape,mx,my,type) {
	var dx = mx - shape.x;
	var dy = my - shape.y;
	var vX=0;
	var vY=0;
	if(type=="1"||type=="2"||type=="17"){
		vX=vY=40;
	}else{
		vX=105;
		vY=70;
	}
	return (0<=dx&&dx<=vX&&0<=dy&&dy<=vY);
};
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null){
    	return unescape(r[2]); 
    } 
    return "";
}
function drawOneLine(sx,sy,ex,ey,context){
	context.moveTo(sx,sy);
	context.lineTo(ex,ey);
}
}])
