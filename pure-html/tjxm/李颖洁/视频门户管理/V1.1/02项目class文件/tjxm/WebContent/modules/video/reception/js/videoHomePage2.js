(function(window){
	/**
	 * 设置首页展示的内容
	 */
	var pageData = {
		//视频门户首页导航栏上的栏目
		setHomePageColumns:function(json){
			//console.info(json)
			var str;
			$.post(json.url, {},function(data){
				for(var i=0;i<data.length;i++){
					 str = "<li class='fl' id='" + data[i].id + "'  onclick='nav(\""+data[i].id+"\")'>" + data[i].columnName + "</li>";
					$("#"+json.dom).append(str)
					str=""
				}
			} );
		},
		//设置首页内容
		setHomePageContent:function(json){
			$.post(json.url, { columnId: "",flag:'3' },function(data){
				console.info(data)
				var latestList = data.data.latestList;
				var columnList = data.data.columnList;
				var strs="";
				for(var i=0;i<latestList.length;i++){
					if(i==0){
						strs+="<div class='yspList clearFloat' id='latest'>"
						strs+="<div class='yspList-left fl'  onclick='display(\""+latestList[i].id+"\",\""+latestList[i].uuid+"\",\""+latestList[i].fileName+"\",\""+latestList[i].title+"\",\""+latestList[i].videoId+"\")' >";
						strs+="<img src='" + latestList[i].imgPath + "' />";
						strs+="<div class='fl searchBox'><h4>" + latestList[i].title + "</h4><div class='bofangliang'>播放" + latestList[i].playTimes + "</div></div>";
						strs+="</div></div>"
						$("#"+json.dom).append(strs);
						strs="";
					} else if(i==1){
						strs+="<ul class='yspList-right fl' id='latestUl' >";
						strs+="<li class='fl' onclick='display(\""+latestList[i].id+"\",\""+latestList[i].uuid+"\",\""+latestList[i].fileName+"\",\""+latestList[i].title+"\",\""+latestList[i].videoId+"\")' >";
						strs+="<img src='" + latestList[i].imgPath + "' />";
						strs+="<div class='articleTitle'><h4>" + latestList[i].title + "</h4><div class='bofangliang'>播放" + latestList[i].playTimes + "</div></div>";
						strs+="</li>";
						strs+="</ul>";
						$("#latest").append(strs);
						strs="";
					} else{
						strs="<li class='fl' onclick='display(\""+latestList[i].id+"\",\""+latestList[i].uuid+"\",\""+latestList[i].fileName+"\",\""+latestList[i].title+"\",\""+latestList[i].videoId+"\")'><img src='" + latestList[i].imgPath + "'  />";
						strs+="<div class='articleTitle'><h4>" + latestList[i].title + "</h4><div class='bofangliang'>播放" + latestList[i].playTimes + "</div></div>";
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
							columsStr+="<img src='" + contentList[j].imgPath  + "' />";
							columsStr+="<div class='articleTitle'><h4>" + contentList[j].title + "</h4><div class='bofangliang'>播放" + contentList[j].playTimes + "</div></div>";
							columsStr+="</div></div>";
							$("#columnContents").append(columsStr);
						}else if(j==1){
							id1="ul"+i+""+j;
							//alert(id1)
							columsStr ="<ul class='yspList-right fl' id='" + id1 + "'>";
							columsStr+="<li class='fl' onclick='display(\""+contentList[j].id+"\",\""+contentList[j].uuid+"\",\""+contentList[j].fileName+"\",\""+contentList[j].title+"\",\""+contentList[j].videoId+"\")' >";
							columsStr+="<img src='" + contentList[j].imgPath + "' />";
							columsStr+="<div class='articleTitle'><h4>" + contentList[j].title + "</h4><div class='bofangliang'>播放" + contentList[j].playTimes + "</div></div>";
							columsStr+="</li>";
							columsStr+="</ul>";
							$("#"+id).append(columsStr)
						}else{
							columsStr="<li class='fl' onclick='display(\""+contentList[j].id+"\",\""+contentList[j].uuid+"\",\""+contentList[j].fileName+"\",\""+contentList[j].title+"\",\""+contentList[j].videoId+"\")' ><img src='" + contentList[j].imgPath + "' />";
							columsStr+="<div class='articleTitle'><h4>" + contentList[j].title + "</h4><div class='bofangliang'>播放" + contentList[j].playTimes + "</div></div>";
							columsStr+="</li>";
							$("#"+id1).append(columsStr);
						}
					} 
				}
				
				
			} )
			
		}
		
	}
		
	window.setPageData = pageData;
})(window);


//导航栏点击切换效果
function nav(columnId){
	$(this).siblings('li').removeClass('checked');
	$(this).addClass('checked');
	window.location="/modules/video/reception/videoList.html?columnId="+columnId+"&flag=0"
}

//更多 
function gt(id){
	window.open("/modules/video/reception/videoList.html?columnId="+id+"&flag=0")
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
		window.location="/modules/video/reception/videoList.html?columnName="+$("#searchText").val()+"&flag="+flag
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



