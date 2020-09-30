
/**
 * 播放视频或者浏览pdf
 * @param fileUuid 文件的id
 * @param fileName 文件名称
 * @param showFileAreaId 文件展示区域id，通过id中是否为videos或者pdfs来打开不同的页面
 * @param isRecord 是否需要保存记录到数据库（用于区分资料维护和资料学习），0：不需要，1：需要记录
 * @returns
 */
function displayFile(fileUuid,fileFullName,showFileAreaId,isRecord){
	
	//判断当前用户是否可以再打开一个页面开始
	//只有需要保存学习记录到数据库时才会校验cookie
	if(isRecord == "1"){
		var isLearning=getcookie("isLearning");
		if(isLearning != null && isLearning.length > 0 && isLearning=="isLearning"){
			//当前电脑已经打开了学习页面
			layer.confirm('当前已经存在学习页面，不能同时打开两个学习页面！', 
					{
						icon:0
						,title:'信息'
						,btn: ['确定'] //按钮
						,btn1:function(index){
							layer.close(index);
							//window.close();
						},end:function(index){
							layer.close(index);
							//window.close();
						}
				});
			//清除cookie
			//clearCookie("isLearning");
			return false;
		}
	}
	//判断当前用户是否可以再打开一个页面结束
	if(showFileAreaId.indexOf("videos") != -1){
		var getVideoUrl = Config.hBServerIp+"/api/v1/load_balancer/vod_play_url?AccessToken="+Config.AccessToken
			+"&OriginalFileUuid="+fileUuid+"&ServiceType=vod_http&FileExtName=.mp4";
		$.ajax({
			type:"GET",
			url:getVideoUrl,
			async: true,
			//dataType:"json",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success:function(data){
				//当前电脑不存在学习页面
				//页面加载出来后，设置cookie，防止页面被浏览器阻止后，用户无法进入学习页面；王磊 2019-03-01
				/*if(isRecord == "1"){
					setCookie("isLearning","isLearning");
				}*/
				//获取播放地址并转码
				var videoUrl = encodeURIComponent(data[0].Url);
				window.open("/modules/zhbg/xxkh/displayVideoAndPdf/playVideo.html?videoUrl="+videoUrl+"&infoId="+$("#id").val()+"&infoName="+encodeURIComponent($("span#dataName").text())+"&isRecord="+isRecord+"&fileFullName="+encodeURIComponent(fileFullName));
			},
			error:function(data){
				layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
			}
		});
	}else{
		//请求播放pdf的地址
	    var getPdfUrl = Config.hBServerIp+"/api/v1/load_balancer/course_play_url?ServiceType=vod_http&FileExtName=.pdf&OriginalFileUuid="+fileUuid+"&AccessToken="+Config.AccessToken;
	    $.ajax({
			type:"get",
			url:getPdfUrl,
			async: true,
			dataType:"json",
			success:function(data){
				//alert(JSON.stringify(data));
				//当前电脑不存在学习页面
				//页面加载出来后，设置cookie，防止页面被浏览器阻止后，用户无法进入学习页面；王磊 2019-03-01
				/*if(isRecord == "1"){
					setCookie("isLearning","isLearning");
				}*/
				pdfUrl = data[0].Url;
				window.open("/modules/zhbg/xxkh/displayVideoAndPdf/displayPdf.html?pdfUrl="+encodeURIComponent(pdfUrl)+"&infoId="+$("#id").val()+"&infoName="+encodeURIComponent($("span#dataName").text())+"&isRecord="+isRecord+"&fileFullName="+encodeURIComponent(fileFullName));
			},
			error:function(data){
				layer.msg("文件还未转码完成，稍后再试！", {icon: 0});
			}
		});
	}
}

/**
 * 上传文件时，初始化对应的文件展示列表
 * @param fileFullName 文件名带后缀
 * @param fileUuid 文件id 根据此id查找文件的播放地址
 * @param showFileAreaId 文件展示区域id（后续会根据此id来区分该打开视频还是展示pdf，所以在命名上要遵循**video**或者是**pdf**的规则）
 * @param isEdit 是否可编辑，0：只读不可删除，1：编辑可以删除
 * @param isRecord 是否将学习记录存入数据库，0：不需要，1：需要
 * @param fileIdNum 文件在音视频系统中的fileId,目前是在音视频系统中删除文件用
 * @returns {Boolean}
 */
function initMovies(fileFullName,fileUuid,showFileAreaId,isEdit,isRecord,fileIdNum){
	//console.log("infoId:"+infoId+"---"+"infoName:"+infoName+"isRecord:"+isRecord);
	//显示上传的文件名及初始化进度条
	var html = '<div class="row m-b-5" >'
			+'<div class="col-md-12" >'
			+'<a style="color:blue;cursor:pointer;" fileIdNum="'+fileIdNum+'" name="'+showFileAreaId+'" value="'+fileUuid+'">'+fileFullName+'</a> &nbsp;&nbsp;&nbsp;'
			+'<span><i title="删除" class="glyphicon glyphicon-remove" style="cursor: pointer;" onclick="deleteFileDiv(this)"></i></span></div></div>';
	$("#"+showFileAreaId).append(html);
	$("#"+showFileAreaId).find("a:last").click(function(){
		displayFile($(this).attr("value"),$(this).html(),$(this).attr("name"),isRecord);
	});
	if(isEdit == 0){
		$("#"+showFileAreaId).find("a:last").next("span").remove();
	}
	return true;
}

/**
 * 获得文件上传地址并上传文件
 * @param fileAreaId
 * @param showFileAreaId
 * @returns {Boolean}
 */
function getUrlAndUpload(fileAreaId,showFileAreaId){
	//校验id是否存在
	if(($("#id").val() == "" || $("#id").val().length == 0) && null != document.getElementById(fileAreaId).files[0]){
		layer.msg("请先保存资料基本信息！", {icon: 0});
		document.getElementById(fileAreaId).value="";
		return false;
	}
	//从参数中获得类别全路径
	var theRequest = GetRequest();
	//文件在音视频中的分类id
	var classId = theRequest.classId;
	//alert(theRequest.filePath+"---"+filePath);
	console.log("从列表获得的类别classIDs："+classId);
	//构建一个对象，用于封装分片上传地址和文件id等
	var uploadUrlData={
		ServiceUrl:"",
		Id:"",
		TaskUuid:"",
		fileIdNum:""
	};
    //获取上传地址
    var blob = document.getElementById(fileAreaId).files[0];
	if(null == blob){
		console.log("上传结束后清空文件框，不再上传文件");
		return false;
	}
	var fileFullName = blob.name;//文件带后缀全称
	if(!checkFileExt(fileAreaId,fileFullName)){
		layer.msg("请上传word、txt、excel、pdf类型的文件！", {icon: 0});
		//清空所选文件
		$("#"+fileAreaId).val("");
		return false;
	}
	var fileName = fileFullName.substring(0,fileFullName.lastIndexOf('.'));
	var fileType = fileFullName.substring(fileFullName.lastIndexOf('.'), fileFullName.length);
	var fileSize = blob.size;
	//异步获取分片上传地址和文件id等信息
	var url = Config.hBServerIp+"/api/v1/load_balancer/file_upload_url?AccessToken="+Config.AccessToken
			+"&FileName="+fileName+"&FileExtName="+fileType+"&FileSize="+fileSize+"&FileIsAudited=1&OperatorUuid=network_tv"
			+"&ClassIDs="+classId;
	//alert("getUrlAndUpload="+url);
	$.ajax({
		type: "get",
		url:encodeURI(url),
		async: true,
		dataType:"json",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success:function(data){
			//alert(JSON.stringify(data));
			uploadUrlData.ServiceUrl=data.ServiceUrl;
			uploadUrlData.Id=data.Id;
			uploadUrlData.TaskUuid=data.TaskUuid;
			uploadUrlData.fileIdNum=data.FileId;
			//初始化文件点击事件
			//initMovies(fileFullName,data.FileUuid,showFileAreaId);
			uploadFile(blob,uploadUrlData,fileFullName,data.FileUuid,fileAreaId,showFileAreaId);
		},
		error:function(data){
			layer.msg("请稍后再试！", {icon: 0});
		}
	});
    
}

/**
 * 上传文件
 * @param blob 被上传的文件
 * @param uploadUrlData 自己分装的对象，属性见：getUrlAndUpload方法
 * @param fileFullName 带后缀的文件名
 * @param fileUuid 文件在音视频中的uuid
 * @param fileAreaId 上传文件的id
 * @param showFileAreaId 用于文件回显的id
 */
function uploadFile(blob,uploadUrlData,fileFullName,fileUuid,fileAreaId,showFileAreaId) {
	//显示进度条
	//$("#progress").show();
	var index = layer.open({
		title:"进度条",
		type: 1,
		closeBtn:0,
		/*btn: ['确定'],
		    yes: function(){
		    	//关闭当前弹出层
		    	layer.close(index);
		    },*/
		//skin: 'layui-layer-rim', //加上边框
		area: ['500px'], //宽高
		content: '<div id="progress"><div id="bar"></div></div>',
		success: function(layero, index) {
			//高度自适应
            layer.iframeAuto(index);
        }

	});
	//隐藏layer.open的title并设置弹框样式
	$("div.layui-layer-title").hide();
	$("div.layui-layer.layui-layer-page").addClass("classdiv");
	
	var tempUrl = uploadUrlData.ServiceUrl+"?Id="+uploadUrlData.Id+"&TaskUuid="+uploadUrlData.TaskUuid+"&AccessToken="+Config.AccessToken
			+"&FileBeginPos=0&FileEndPos="+blob.size;
    //ajax上传文件
    var xhr = new XMLHttpRequest();
    var fd = new FormData();
	fd.append("file",blob);
    xhr.open('POST',tempUrl,true);
    //利用xhr2的新标准，为上传过程，写一个监听函数
    xhr.upload.onprogress = function(ev){
        if(ev.lengthComputable){//文件长度可计算
            var percent = 100*ev.loaded/ev.total;//计算上传的百分比
            document.getElementById('bar').style.width = percent + '%';//更改上传进度
            document.getElementById('bar').innerHTML = parseInt(percent)+'%';//显示上传进度
            if(percent==100){
            	//上传成功，这里添加上传成功的业务
            	layer.msg("上传成功！", {icon: 1});
            	$("#"+fileAreaId).val("");
    			layer.close(index);
    			initMovies(fileFullName,fileUuid,showFileAreaId,"1","0",uploadUrlData.fileIdNum);
    			var isVideo = showFileAreaId.indexOf("video")==-1?0:1;
    			saveFile(fileFullName,fileUuid,isVideo,uploadUrlData.fileIdNum);
            }
        }
    }
    xhr.send(fd);//发送请求
}

/**
 * 获得当前时间：年月日时分秒
 * @returns {String}
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var strH = date.getHours();
    var strM = date.getMinutes();
    var strS = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (strH >= 0 && strH <= 9) {
    	strH = "0" + strH;
    }
    if (strM >= 0 && strM <= 9) {
    	strM = "0" + strM;
    }
    if (strS >= 0 && strS <= 9) {
    	strS = "0" + strS;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + strH + seperator2 + strM
            + seperator2 + strS;
    return currentdate;
}

/**
 * 校验文件后缀是否符合要求（视频类型较多，这里先不做校验）
 * @param fileAreaId
 * @param fileFullName
 * @returns {Boolean}
 */
function checkFileExt(fileAreaId,fileFullName){
	var passChecked = false;
	if(fileAreaId.indexOf("pdf") != -1){
		//校验非流媒体
		var fileExtArr = ["txt","doc","docx","pdf","xlsx","xls"];
		var index = fileFullName.lastIndexOf(".");
		var ext = fileFullName.substr(index+1);
		for(var i=0;i<fileExtArr.length;i++){
			if(ext == fileExtArr[i].toLowerCase()){
				passChecked = true;
				break;
			}
		}
	}else{
		//视频类型较多，这里先不做校验
		passChecked = true;
	}
	return passChecked;
}

/**
 * 保存资料对应的文件
 * @param fileFullName 文件带后缀名
 * @param fileUuid 文件在音视频系统中的uuid，用于读取文件用
 * @param isVideo 是否视频，0：不是，1：是
 * @param fileId 文件id，在音视频删除文件用
 */
function saveFile(fileFullName,fileUuid,isVideo,fileId){
	//异步保存这个文件
	var videoAndPdf={
			fileName:fileFullName,
			fileId:fileUuid,
			infoId:$("#id").val(),
			fileType:isVideo,
			fileIdNum:fileId
	}
	//alert(JSON.stringify(videoAndPdf));
	$.ajax({
		type: "post",
		url:"/zhbg/xxkh/videoAndPdf/saveFile",
		data:JSON.stringify(videoAndPdf),
		contentType:"application/json",
		async: true,
		dataType:"json",
		success:function(data){
			if(data.flag!='1'){
				layer.msg("文件保存失败，请联系管理员！", {icon: 1});
			}
		},
		error:function(data){
			layer.msg("请稍后再试！", {icon: 1});
		}
	});
}

/**
 * 根据资料id获得所有的文件
 * @param infoId
 * @returns
 */
function getFilesByInfoId(infoId){
	var jsonData;
	$.ajax({
		type:"get",
		url:"/zhbg/xxkh/videoAndPdf/getByInfoId?infoId="+infoId,
		async: false,//设置同步，否则返回的jsonData为undefined
		dataType:"json",
		success:function(data){
			if(data.flag == 1){
				jsonData = data.fileList;
			}else{
				layer.msg("获取文件列表异常！", {icon: 0});
			}
		},
		error:function(data){
			layer.msg("请稍后再试！", {icon: 0});
		}
	});
	return jsonData;
}

/**
 * 资料对应的文件回显（资料维护和资料学习）：根据资料id和是否更新页面标识来控制文件列表展示
 * @param isEdit 是否更新页面，0：只读页面，1：更新页面
 * @param infoId 资料id
 * @param isRecord 是否保存记录到数据库
 */
function showFiles(isEdit,infoId,isRecord){
	var fileList = getFilesByInfoId(infoId);
	//alert(fileList.length);
	for(var i=0;i<fileList.length;i++){
		if(fileList[i].fileType==0){
			//文件
			initMovies(fileList[i].fileName,fileList[i].fileId,"pdfs",isEdit,isRecord,fileList[i].fileIdNum);
		}else{
			//视频
			initMovies(fileList[i].fileName,fileList[i].fileId,"videos",isEdit,isRecord,fileList[i].fileIdNum);
		}
	}
}

/**
 * 资料对应文件的删除
 * @param obj
 */
function deleteFileDiv(obj){
	var theRequest = GetRequest();
	//当前所选资料类别在音视频系统中的classId，删除分类下文件用
	var classId = theRequest.classId;
	//在音视频系统中的fileUuid，获取播放地址用
	var fileId = $(obj).parent("span").prev("a").attr("value");
	//在音视频系统中的fileId，在音视频系统中删除文件用
	var fileIdNum = $(obj).parent("span").prev("a").attr("fileIdNum");
    layer.confirm('确定删除吗？', 
		{
			icon:3
			//,title:'删除'
			,btn: ['确定','取消'] //按钮
			,btn1:function(index){
				//后台逻辑删除该文件
		    	$.ajax({
		    		type:"post",
		    		url:"/zhbg/xxkh/videoAndPdf/deleteById?fileId="+fileId,
		    		async: true,
		    		dataType:"json",
		    		success:function(data){
		    			if(data.flag == 1){
		    				layer.close(index);
		    				$(obj).parent("span").parents("div.row.m-b-5").remove();
		    				layer.msg("删除成功！", {icon: 1});
		    				//在音视频系统中删除该文件
		    				deleteFileInHB(fileIdNum,classId);
		    			}else{
		    				layer.msg("删除失败！", {icon: 0});
		    			}
		    		},
		    		error:function(data){
		    			layer.msg("请稍后再试！", {icon: 0});
		    		}
		    	});
			},btn2:function(){
				//取消什么都不做
			}	
		});
}

/**
 * 调用接口，在音视频系统中删除文件
 * @param fileId 文件在音视频系统中的fileId
 * @param classId 文件在音视频系统中分类id
 */
function deleteFileInHB(fileId,classId){
	var sqlValue = fileId+","+classId+","+"1";
	var delPara = {
			SqlKey:"exec_file_info",
			Parms:[{"SqlName":"ExecFileDelete","SqlValue":sqlValue}]
	};
	console.log("JSON.stringify(delPara)="+JSON.stringify(delPara));
	$.ajax({
		type: "post",
		url:Config.hBServerIp+"/api/v1/sqlbykey",
		data:JSON.stringify(delPara),
		async: false,
		success:function(data){
			//layer.msg("音视频删除文件成功！", {icon: 1});
			console.log("音视频删除文件成功！");
			//alert(data);
		},
		error:function(data){
			layer.msg("音视频系统删除文件异常，请稍后再试！", {icon: 0});
		}
	});
}

/**
 * 调用接口，在音视频系统中删除分类
 * @param classId 
 */
function deleteFenLeiInHB(classId){
	var delPara={
			DelFlag:'1'
	};
	$.ajax({
		type: "put",
		url:Config.hBServerIp+"/api/v1/file_class/"+classId,
		data:JSON.stringify(delPara),
		async: true,
		success:function(data){
			console.log("音视频删除分类成功！");
			//alert(data);
		},
		error:function(data){
			layer.msg("音视频系统删除分类异常，请稍后再试！", {icon: 0});
		}
	});
}

/**
 * @Author 王富康
 * @Description 请求后台刷新session
 * @Date 2018/9/20 20:19
 * @Param
 * @return
 **/
function keepAlive() {
    $.ajax({
        type: 'GET',
        url: "/zhbgkh/test/timedtask",
        success: function(ret) {
            /*debugger;
            var jsonRet = JSON.parse(ret);
            console.dir(jsonRet);*/
        }
    });
}
