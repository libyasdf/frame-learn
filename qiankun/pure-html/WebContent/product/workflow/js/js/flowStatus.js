var getFlowStatusURL="/workflow/getFlowStatus";
$("body").height(document.body.clientHeight)
var mx=$("#myDiv").width();
var my=$("#myDiv").height();
var request = GetRequest();
var id=request.workflowid==""?request.filetypeid:request.workflowid;
var fileTypeId=request.filetypeid==""?request.workflowid:request.filetypeid;
var recordId=request.recordid;
var sysId=getcookie("sysid");
var theCanvas='';
var imageShapes=[];
var lineShapes=[];
httpRequest("GET",getFlowStatusURL+"?workflowId="+id+"&fileTypeId="+fileTypeId+"&recordId="+recordId+"&sysId="+sysId,"",function(data){
	//console.log(data)
	if(data){
		imageShapes=data.nodes?data.nodes:[];
		lineShapes=data.linkLines?data.linkLines:[];
		for(var s=0;s<imageShapes.length;s++){
				if(imageShapes[s].type!="1"&&imageShapes[s].type!="2"&&imageShapes[s].type!="17"){
					if(imageShapes[s].x+105>=mx){
						mx=imageShapes[s].x+125;
					}
					if(imageShapes[s].y+70>=my){
						my=imageShapes[s].y+90;
					}
				}else{
					if(imageShapes[s].x+40>=mx){
						mx=imageShapes[s].x+80;
					}
					if(imageShapes[s].y+40>=my){
						my=imageShapes[s].y+80;
					}
				}
			}
		// $("header").width(mx-30);
		if(my <= $(window).height()){
			my = $(window).height() - 5;
		}
		$("#myCanvas").attr("height",my);
		$("#myCanvas").attr("width",mx);

	
		/*$("#myCanvas").attr("height",$(window).height());
		$("#myCanvas").attr("width",$(window).width());*/

		drawShapes();
	}
});
function httpRequest(method,url,data,callback){
	$.ajax({
		url : basePath + url,
		type : method,
		data : data,
		dataType : "json",
		success : function(data) {
			if(data){
				callback(data);
			}
		},
		error : function(error) {
			showDialogAlert("获取失败，请刷新重试或联系管理员");
		}
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
	context.font="16px 微软雅黑";
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
	exampleImage.src="/product/workflow/image/flowstatus/example.png";//示例
	context.drawImage(exampleImage,15,5,58,89);
	for (var i=0;i<imageShapes.length;i++) {
		var image1=new Image();
		if(imageShapes[i].type=="1"){	//启动节点
			if(imageShapes[i].processStatus==""){
				image1.src="/product/workflow/image/flowstatus/start3.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
                image1.src="/product/workflow/image/flowstatus/start1.png";//办结
            }else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1 && imageShapes[i].processStatus.indexOf("接收人")==-1){
				image1.src="/product/workflow/image/flowstatus/start1.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1 && imageShapes[i].processStatus.indexOf("接收人")==-1){
				image1.src="/product/workflow/image/flowstatus/start1.png";//已办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
                image1.src="/product/workflow/image/flowstatus/start2.png";//办理中
            }else{
				image1.src="/product/workflow/image/flowstatus/start1.png";
			}
		}else if(imageShapes[i].type=="2"){	//结束节点
			image1.src="/product/workflow/image/flowstatus/end3.png";
		}else if(imageShapes[i].type=="3"){	//过程节点
			if(imageShapes[i].processStatus== ""){
				image1.src="/product/workflow/image/flowstatus/entitynode3.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1 && imageShapes[i].processStatus.indexOf("拟稿人") != -1){
                image1.src="/product/workflow/image/flowstatus/entitynode33.png";//启动 + 办结
            }else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
                image1.src="/product/workflow/image/flowstatus/entitynode32.png";//办结
            }else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="/product/workflow/image/flowstatus/entitynode31.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1 && imageShapes[i].processStatus.indexOf("接收人")==-1){
				image1.src="/product/workflow/image/flowstatus/entitynode1.png";//已办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
                image1.src="/product/workflow/image/flowstatus/entitynode2.png";//办理中
            }else{
				image1.src="/product/workflow/image/flowstatus/entitynode3.png";
			}
		}else if(imageShapes[i].type=="4"){	//子流程节点
			if(imageShapes[i].processStatus== ""){
				image1.src="/product/workflow/image/flowstatus/subflownode3.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
                image1.src="/product/workflow/image/flowstatus/subflownode3.png";//办结
            }else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="/product/workflow/image/flowstatus/subflownode3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="/product/workflow/image/flowstatus/subflownode1.png";//已办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
                image1.src="/product/workflow/image/flowstatus/subflownode2.png";//办理中
            }else{
				image1.src="/product/workflow/image/flowstatus/subflownode3.png";
			}
		}else if(imageShapes[i].type=="5"){	//自由节点
			if(imageShapes[i].processStatus== ""){
				image1.src="/product/workflow/image/flowstatus/freenode3.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
                image1.src="/product/workflow/image/flowstatus/freenode3.png";//办结
            }else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="/product/workflow/image/flowstatus/freenode3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="/product/workflow/image/flowstatus/freenode1.png";//已办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
                image1.src="/product/workflow/image/flowstatus/freenode2.png";//办理中
            }else{
				image1.src="/product/workflow/image/flowstatus/freenode3.png";
			}
		}else if(imageShapes[i].type=="6"){	//自动节点
			if(imageShapes[i].processStatus== ""){
				image1.src="/product/workflow/image/flowstatus/autonode3.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
                image1.src="/product/workflow/image/flowstatus/autonode3.png";//办结
            }else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="/product/workflow/image/flowstatus/autonode3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="/product/workflow/image/flowstatus/autonode1.png";//已办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
                image1.src="/product/workflow/image/flowstatus/autonode2.png";//办理中
            }else{
				image1.src="/product/workflow/image/flowstatus/autonode3.png";
			}
		}else if(imageShapes[i].type=="17"){	//汇聚节点
			if(imageShapes[i].processStatus== ""){
				image1.src="/product/workflow/image/flowstatus/merage3.png";//未办
			}else if(imageShapes[i].processStatus.indexOf("办结")!=-1){
                image1.src="/product/workflow/image/flowstatus/merage3.png";//办结
            }else if(imageShapes[i].processStatus.indexOf("拟稿人") != -1){
				image1.src="/product/workflow/image/flowstatus/merage3.png";//启动
			}else if(imageShapes[i].processStatus.indexOf("办理人")!=-1){
				image1.src="/product/workflow/image/flowstatus/merage1.png";//已办
			}else if(imageShapes[i].processStatus.indexOf("管理员")!=-1||imageShapes[i].processStatus.indexOf("接收人") != -1||imageShapes[i].processStatus.indexOf("办理中") !=-1){
                image1.src="/product/workflow/image/flowstatus/merage2.png";//办理中
            }else{
				image1.src="/product/workflow/image/flowstatus/merage3.png";
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
		var num=Math.ceil(imageShapes[i].label.length/6.0);
		if(num>=1){
			var showWordList=[];
			for(var p=0;p<num;p++){
				var word=imageShapes[i].label.substr(p*6,6)
				var width=context.measureText(word).width;
				var moreWidth=(width-wid)/2.0;
				if(imageShapes[i].type!="1"&&imageShapes[i].type!="2"&&imageShapes[i].type!="17"){
					context.fillText(word,imageShapes[i].x-moreWidth,imageShapes[i].y+hei/2+p*18);
				}else{
					context.fillText(word,imageShapes[i].x-moreWidth,imageShapes[i].y+hei+10+8+p*18);
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
	
	if(BrowserType()=='0'||BrowserType()=='IE8'||BrowserType()=='IE7'||BrowserType()=='IE6'){
		theCanvas.attachEvent("onmousedown", mouseDownListener);
		theCanvas.attachEvent("onmousemove", mouseMoveListener);
	}else{
		theCanvas.addEventListener("mousedown", mouseDownListener, false);
		theCanvas.addEventListener("mousemove", mouseMoveListener, false);
	}
	
}
function mouseDownListener(evt) {
	bRect = theCanvas.getBoundingClientRect();
		mouseX = (evt.clientX - bRect.left)*(theCanvas.width/bRect.width);
		mouseY = (evt.clientY - bRect.top)*(theCanvas.height/bRect.height);
		for (var i=imageShapes.length-1; i >=0; i--) {
				if	(hitImageTest(imageShapes[i], mouseX, mouseY,imageShapes[i].type)&&imageShapes[i].type=="4") {
					var id='895547718';
					var fileTypeId='';
					var recordId='201703031423';
					var sysId='1';
					window.open("./flowStatus.html?workflowId="+id+"&fileTypeId="+fileTypeId+"&recordId="+recordId+"&sysId="+sysId,'_blank','');
				}
		}
}
function mouseMoveListener(evt) {
	bRect = theCanvas.getBoundingClientRect();
	if(BrowserType()=='0'||BrowserType()=='IE8'||BrowserType()=='IE7'||BrowserType()=='IE6'){
		mouseX = (evt.clientX - bRect.left)*(theCanvas.width/ bRect.right);
		mouseY = (evt.clientY - bRect.top)*(theCanvas.height/ bRect.bottom);
	}else{
		mouseX = (evt.clientX - bRect.left)*(theCanvas.width/bRect.width);
		mouseY = (evt.clientY - bRect.top)*(theCanvas.height/bRect.height);
	}
	
	var isShow=false;
	for (var i=imageShapes.length-1; i >=0; i--) {
			if(hitImageTest(imageShapes[i], mouseX, mouseY,imageShapes[i].type)&&imageShapes[i].processStatus!="") {
				isShow=true;
				$("#warnInfo").html(imageShapes[i].processStatus);	
				break;
			}
	}
	if(!isShow){
		$("#warnInfo").hide();
	}else{
		var x=evt.clientX;	//鼠标距离页面左侧的距离
		var y=evt.clientY;	//鼠标距离页面顶部的距离
		var w=$("#warnInfo").width();
		var h=$("#warnInfo").height();
		//bRect.width//可视窗口的宽度
		if(bRect.width-x<w){
			x=x-w;
			if(x<0)x=0; //如果计算后，距离左侧距离是负数，则置为0
		}
		if(bRect.height-y<h){
			y=y-h;
			if(y<0)y=0;	//如果计算后，距离顶部距离是负数，则置为0
		}
		$("#warnInfo").css("position","absolute");
		$("#warnInfo").css("left",x);
		$("#warnInfo").css("top",y);
		$("#warnInfo").show();
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

$(window).resize(resizeCanvas);  
   
 function resizeCanvas() {  
   
	 httpRequest("GET",getFlowStatusURL+"?workflowId="+id+"&fileTypeId="+fileTypeId+"&recordId="+recordId+"&sysId="+sysId,"",function(data){
			//console.log(data)
			if(data){
				imageShapes=data.nodes?data.nodes:[];
				lineShapes=data.linkLines?data.linkLines:[];
				for(var s=0;s<imageShapes.length;s++){
						if(imageShapes[s].type!="1"&&imageShapes[s].type!="2"&&imageShapes[s].type!="17"){
							if(imageShapes[s].x+105>=mx){
								mx=imageShapes[s].x+125;
							}
							if(imageShapes[s].y+70>=my){
								my=imageShapes[s].y+90;
							}
						}else{
							if(imageShapes[s].x+40>=mx){
								mx=imageShapes[s].x+80;
							}
							if(imageShapes[s].y+40>=my){
								my=imageShapes[s].y+80;
							}
						}
					}
				// $("header").width(mx-30);
                if(my <= $(window).height()){
                    my = $(window).height() - 5;
                }
				$("#myCanvas").attr("height",my);
				$("#myCanvas").attr("width",mx);
				
				/*$("#myCanvas").attr("height",$(window).height());
				$("#myCanvas").attr("width",$(window).width());*/
			
				drawShapes();
			}
		});
   
 };  

 

 /* 
  * 描述：判断浏览器信息 
  * 编写：LittleQiang_w 
  * 日期：2016.1.5 
  * 版本：V1.1 
  */
  
 //判断当前浏览类型 
 function BrowserType() 
 { 
   var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
   var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
   var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
   var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器 
   var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器 
   var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1; //判断是否Safari浏览器 
   var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器 
  
   if (isIE)  
   { 
      var reIE = new RegExp("MSIE (\\d+\\.\\d+);"); 
      reIE.test(userAgent); 
      var fIEVersion = parseFloat(RegExp["$1"]); 
      if(fIEVersion == 7) 
      { return "IE7";} 
      else if(fIEVersion == 8) 
      { return "IE8";} 
      else if(fIEVersion == 9) 
      { return "IE9";} 
      else if(fIEVersion == 10) 
      { return "IE10";} 
      else if(fIEVersion == 11) 
      { return "IE11";} 
      else
      { return "0"}//IE版本过低 
    }//isIE end 
      
    if (isFF) { return "FF";} 
    if (isOpera) { return "Opera";} 
    if (isSafari) { return "Safari";} 
    if (isChrome) { return "Chrome";} 
    if (isEdge) { return "Edge";} 
  }//myBrowser() end 
    
  //判断是否是IE浏览器 
  function isIE() 
  { 
   var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
   var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
   if(isIE) 
   { 
     return "1"; 
   } 
   else
   { 
     return "-1"; 
   } 
  } 
    
    
  //判断是否是IE浏览器，包括Edge浏览器 
  function IEVersion() 
  { 
   var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
   var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
 var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器 
   if(isIE) 
   { 
      var reIE = new RegExp("MSIE (\\d+\\.\\d+);"); 
      reIE.test(userAgent); 
      var fIEVersion = parseFloat(RegExp["$1"]); 
      if(fIEVersion == 7) 
      { return "IE7";} 
      else if(fIEVersion == 8) 
      { return "IE8";} 
      else if(fIEVersion == 9) 
      { return "IE9";} 
      else if(fIEVersion == 10) 
      { return "IE10";} 
      else if(fIEVersion == 11) 
      { return "IE11";} 
      else
      { return "0"}//IE版本过低 
   } 
 else if(isEdge) 
 { 
  return "Edge"; 
 } 
   else
   { 
     return "-1";//非IE 
   } 
  }

   

