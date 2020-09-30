

$(document).ready(function(){
	//加载首页内容
	setHomePageContent()
	//加载用户照片
	getUserImg();
	//历史记录
    loadHistory("0");
    //加载首页栏目
    loadColumn();
});
//加载首页栏目
function loadColumn(){
	var str;
	$.post("/video/background/column/getHomePageColumns", {},function(data){
		for(var i=0;i<data.length;i++){
			 str = "<li class='fl' id='" + data[i].id + "'  onclick='nav(\""+data[i].id+"\")'>" + data[i].columnName + "</li>";
			$("#columns").append(str)
			str=""
		}
	} );
}
//设置首页内容
function setHomePageContent(){
	$.post("/video/content/getHomePageContent", { columnId: "",flag:'3' },function(data){
		console.info(data)
		var latestList = data.data.latestList;
		var columnList = data.data.columnList;
		var strs="";
		for(var i=0;i<latestList.length;i++){
			if(i==0){
				strs+="<div class='yspList clearFloat' id='latest'>"
                strs+="<a class='showMore' onclick='gt(\""+Config.videoRecommendId+"\")' id='showMore'>更多></a>"
				strs+="<div class='yspList-left fl'  onclick='display(\""+latestList[i].id+"\",\""+latestList[i].uuid+"\",\""+latestList[i].fileName+"\",\""+latestList[i].title+"\",\""+latestList[i].videoId+"\")' >";
				strs+="<div class='imgBox-lg'><img src='" + latestList[i].imgPath + "' /></div>";
				strs+="<div class='articleTitle'><h4>" + latestList[i].title + "</h4><div class='bofangliang'>播放<span id='" + latestList[i].id + "'>" + latestList[i].playTimes + "</span></div></div>";
				strs+="</div></div>"
				$("#columnContents").append(strs);
				strs="";
			} else if(i==1){
				strs+="<ul class='yspList-right fl' id='latestUl' >";
				strs+="<li class='fl' onclick='display(\""+latestList[i].id+"\",\""+latestList[i].uuid+"\",\""+latestList[i].fileName+"\",\""+latestList[i].title+"\",\""+latestList[i].videoId+"\")' >";
				strs+="<div class='imgBox-sm'><img src='" + latestList[i].imgPath + "' /></div>";
				strs+="<div class='articleTitle'><h4>" + latestList[i].title + "</h4><div class='bofangliang'>播放<span id='" + latestList[i].id + "'>" + latestList[i].playTimes + "</span></div></div>";
				strs+="</li>";
				strs+="</ul>";
				$("#latest").append(strs);
				strs="";
			} else{
				strs="<li class='fl' onclick='display(\""+latestList[i].id+"\",\""+latestList[i].uuid+"\",\""+latestList[i].fileName+"\",\""+latestList[i].title+"\",\""+latestList[i].videoId+"\")'><div class='imgBox-sm'><img src='" + latestList[i].imgPath + "'  /></div>";
				strs+="<div class='articleTitle'><h4>" + latestList[i].title + "</h4><div class='bofangliang'>播放<span id='" + latestList[i].id + "'>" + latestList[i].playTimes + "</span></div></div>";
				strs+="</li>";
				$("#latestUl").append(strs);
			}  
			
		}
		var columsStr = '';
		for(var i=0;i<columnList.length;i++){
			//alert(i+"...."+columnList.length)
			columsStr="<div class='yspListHead clearFloat'><h2 class='fl'>" + columnList[i].columnName + "</h2><a href='#' onclick='gt(\""+columnList[i].id+"\")'  class='fr'>更多></a></div>"
			$("#columnContents").append(columsStr);
			var contentList = columnList[i].contentList;
			var id;
			var id1;
			for(var j=0;j<contentList.length;j++){
				if(j==0){
					id="column"+i;
					columsStr="<div class='yspList clearFloat' id='" + id + "'><div class='yspList-left fl' onclick='display(\""+contentList[j].id+"\",\""+contentList[j].uuid+"\",\""+contentList[j].fileName+"\",\""+contentList[j].title+"\",\""+contentList[j].videoId+"\")' >";
					columsStr+="<div class='imgBox-lg'><img src='" + contentList[j].imgPath  + "' /></div>";
					columsStr+="<div class='articleTitle'><h4>" + contentList[j].title + "</h4><div class='bofangliang'>播放<span id='" + contentList[j].id + "'>" + contentList[j].playTimes + "</span></div></div>";
					columsStr+="</div></div>";
					$("#columnContents").append(columsStr);
				}else if(j==1){
					id1="ul"+i+""+j;
					//alert(id1)
					columsStr ="<ul class='yspList-right fl' id='" + id1 + "'>";
					columsStr+="<li class='fl' onclick='display(\""+contentList[j].id+"\",\""+contentList[j].uuid+"\",\""+contentList[j].fileName+"\",\""+contentList[j].title+"\",\""+contentList[j].videoId+"\")' >";
					columsStr+="<div class='imgBox-sm'><img src='" + contentList[j].imgPath + "' /></div>";
					columsStr+="<div class='articleTitle'><h4>" + contentList[j].title + "</h4><div class='bofangliang'>播放<span id='" + contentList[j].id + "'>" + contentList[j].playTimes + "</span></div></div>";
					columsStr+="</li>";
					columsStr+="</ul>";
					$("#"+id).append(columsStr)
				}else{
					columsStr="<li class='fl' onclick='display(\""+contentList[j].id+"\",\""+contentList[j].uuid+"\",\""+contentList[j].fileName+"\",\""+contentList[j].title+"\",\""+contentList[j].videoId+"\")' ><div class='imgBox-sm'><img src='" + contentList[j].imgPath + "' /></div>";
					columsStr+="<div class='articleTitle'><h4>" + contentList[j].title + "</h4><div class='bofangliang'>播放<span id='" + contentList[j].id + "'>" + contentList[j].playTimes + "</span></div></div>";
					columsStr+="</li>";
					$("#"+id1).append(columsStr);
				}
			} 
		}
		
		
	} )
}
//导航栏点击切换效果
function nav(columnId){
	if(columnId!="01"){
		$(this).siblings('li').removeClass('checked');
		$(this).addClass('checked');
		window.location="/modules/video/reception/videoList.html?columnId="+columnId+"&flag=0"
	}
	
}

//更多 
function gt(id){
	//window.open("/modules/video/reception/videoList.html?columnId="+id+"&flag=0")
	window.location="/modules/video/reception/videoList.html?columnId="+id+"&flag=0";

}
//播放
function display(id,uuid,fileName,title,videoId){
	
	displayFile(id,uuid,fileName,title,videoId);
}
//注销
function logout(){
	document.location.href = Config.sso_logout_url+"?gotoURL="+path;
}
//搜索
function search(flag){
	if($("#searchText").val()){
		//window.open("/modules/video/reception/videoList.html?columnName="+$("#searchText").val()+"&flag="+flag)
		window.location="/modules/video/reception/videoList.html?columnName="+encodeURIComponent($("#searchText").val())+"&flag="+flag
	}else{
		layer.msg("请输入搜索内容！", {icon: 0});
	}
	
}


/**
 * 播放视频或者浏览pdf
 * @param fileUuid 文件的id
 * @param fileName 文件名称
 * @param showFileAreaId 文件展示区域id，通过id中是否为videos或者pdfs来打开不同的页面
 * @param isRecord 是否需要保存记录到数据库（用于区分资料维护和资料学习），0：不需要，1：需要记录
 * @returns
 */
function displayFile(id,fileUuid,fileFullName,title,videoId){
	//alert(videoId)
	//判断当前用户是否可以再打开一个页面结束
		var getVideoUrl = Config.hBServerIp+"/api/v1/load_balancer/vod_play_url?AccessToken="+Config.AccessToken
			+"&OriginalFileUuid="+fileUuid+"&ServiceType=vod_http&FileExtName=.mp4";
	
		$.ajax({
			type:"GET",
			url:getVideoUrl,
			async: true,
			//dataType:"json",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success:function(data){
				/* //当前电脑不存在学习页面
				if(isRecord == "1"){
					setCookie("isLearning","isLearning");
				} */
				
				
				
				//获取播放地址并转码
				var videoUrl = encodeURIComponent(data[0].Url);
				//window.open("/modules/video/reception/displayVideoAndPdf/playVideo.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&fileFullName="+encodeURIComponent(fileFullName));
				window.open("/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileFullName));
				//window.location="/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileFullName)
			},
			error:function(data){
				layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
			}
		});

}

// 加载历史记录
function loadHistory(flag,isLoadParentHistory,data) {
	
    //console.info("历史记录")
	if(isLoadParentHistory=="1"){
		  
	        if (data.length > 0) {
	            $("#historyList").empty();
	            if (flag == "0") {
	                $("#history").append("<ul class='history-list' id='historyList'></ul>")
	            }
	            for (var i = 0; i < data.length; i++) {
	                historyStrs = "<li onclick='intoIndex(\"" + data[i].contentId + "\",\"" + data[i].videoId + "\",\"" + data[i].fileName + "\",\"" + data[i].contentTitle + "\",\"" + data[i].uuid + "\")'><p>" + data[i].contentTitle + ":" + data[i].fileName + "</p></li>"
	                $("#historyList").append(historyStrs)
	            }
	            $("#history").append("<span class='history-triangle glyphicon glyphicon-triangle-top'></span>")
	            $("#historyList").find("li").each( function() {
	                $(this).mouseenter(function() {
	                    $(this).css({
	                        backgroundColor:'#0073aa',
	                        color:'#ffffff'
	                    });
	                } );
	                $(this).mouseleave(function() {
	                    $(this).css({
	                        backgroundColor:'#ffffff',
	                        color:'#000000'
	                    });
	                } );
	            } );
	        }
	}else{
	    var historyStrs;
	    $.post("/video/background/historyRecord/getLastestHistoryRecord", {}, function (data) {
	        //console.info(data)

	        var data = data.data
	        if (data.length > 0) {
	            $("#historyList").empty();
	            if (flag == "0") {
	                $("#history").append("<ul class='history-list' id='historyList'></ul>")
	            }
	            for (var i = 0; i < data.length; i++) {
	                historyStrs = "<li onclick='intoIndex(\"" + data[i].contentId + "\",\"" + data[i].videoId + "\",\"" + data[i].fileName + "\",\"" + data[i].contentTitle + "\",\"" + data[i].uuid + "\")'><p>" + data[i].contentTitle + ":" + data[i].fileName + "</p></li>"
	                $("#historyList").append(historyStrs)
	            }
	            $("#history").append("<span class='history-triangle glyphicon glyphicon-triangle-top'></span>")
	            $("#historyList").find("li").each( function() {
	                $(this).mouseenter(function() {
	                    $(this).css({
	                        backgroundColor:'#0073aa',
	                        color:'#ffffff'
	                    });
	                } );
	                $(this).mouseleave(function() {
	                    $(this).css({
	                        backgroundColor:'#ffffff',
	                        color:'#000000'
	                    });
	                } );
	            } );
	        }

	    });
	}

}

function intoIndex(contentId,videoId,fileName,contentTitle,uuid){
	//判断当前用户是否可以再打开一个页面结束
	var getVideoUrl = Config.hBServerIp+"/api/v1/load_balancer/vod_play_url?AccessToken="+Config.AccessToken
		+"&OriginalFileUuid="+uuid+"&ServiceType=vod_http&FileExtName=.mp4";

	$.ajax({
		type:"GET",
		url:getVideoUrl,
		async: true,
		//dataType:"json",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success:function(data){
			//获取播放地址并转码
			var videoUrl = encodeURIComponent(data[0].Url);
			//window.open("/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileFullName));
			window.open("/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+contentId+"&infoName="+encodeURIComponent(contentTitle)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileName));
		},
		error:function(data){
			layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
		}
	});
}

/**
 * 获取用户头像
 */
function getUserImg(){
	$.ajax({
		type: 'get',
		url: '/user/getUserImg',
		dataType: "json",
		success: function(data) {
			if (data.flag) {
				$("#userImg").prop("src",data.path);
			} else {
				$("#userImg").prop("src","/static/images/login_user.png");
			}
		},
		error: function() {
			$("#userImg").prop("src","/static/images/login_user.png");
		}
	});
}

//返回
function goBack(){
	history.back()
}



function intoIndex(contentId,videoId,fileName,contentTitle,uuid){
	//判断当前用户是否可以再打开一个页面结束
	var getVideoUrl = Config.hBServerIp+"/api/v1/load_balancer/vod_play_url?AccessToken="+Config.AccessToken
		+"&OriginalFileUuid="+uuid+"&ServiceType=vod_http&FileExtName=.mp4";

	$.ajax({
		type:"GET",
		url:getVideoUrl,
		async: true,
		//dataType:"json",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success:function(data){
			//获取播放地址并转码
			var videoUrl = encodeURIComponent(data[0].Url);
			//window.open("/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileFullName));
			//window.location="/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+contentId+"&infoName="+encodeURIComponent(contentTitle)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileName);
			window.open("/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+contentId+"&infoName="+encodeURIComponent(contentTitle)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileName));

		},
		error:function(data){
			layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
		}
	});
}


