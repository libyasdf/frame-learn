var index = 0; // 当前视频是第几集
var isEnter = 0; // 鼠标是否进入过历史记录列表
var number = "1";
var videoId;//当前播放的视频id
var contentId;
var a;
var timer;  // 播放下一个视频定时器
$(function () {
	 index = number;
	var theRequest = GetRequest();
	var url = decodeURIComponent(theRequest.videoUrl);
	
	var name = theRequest.fileFullName;
	  //内容id
     contentId = theRequest.infoId;
    //视频id
     videoId = theRequest.videoId
     //alert(videoId)
    //内容标题imgPath
    var series = decodeURIComponent(theRequest.infoName);
    //var poster = decodeURIComponent(theRequest.imgPath);
    // 初始化折叠按钮
    initToggleButton();
    
    $('.ysp-player-menu-title span').html(series);
    $('.video-name span:eq(0)').html(series + ":");
    $('.video-name span:eq(1)').html(name);
    initPlayer(url);
    //历史记录
    loadHistory("0");
    
    // 加载菜单
    loadMenu()
    // 加载视频控件
    initVideoCon()
});
//加载菜单
function loadMenu(fileName){
	 $.ajax({
	        type: "get",
	        url: "/video/content/getVideoByContentId",
	        data:{contentId:contentId,fileName:fileName},
	        dataType: "json",
	        success: function (data) {

	            // 解析 data
	            //console.log(data);
	            var data = data.data;

	            // 加载视频目录
	            if(data.length>0){
	            	$('#cd-timeline').empty()
	            	for (var i = 0; i < data.length; ++i) {
		                loadSeries(data[i], i);
		            }
	            	 $('div[videoid="'+videoId+'"]').addClass('menu-playing');
	            }else{
	            	$('#cd-timeline').empty()
	            }
	            // 给当前播放的视频添加样式
	            //$('#cd-timeline .cd-timeline-block:eq(' + (index - 1) + ')').addClass('menu-playing');
	            // 修改伪类高度，即垂直线高度
	            $('style#timeline').html('#cd-timeline::before { height: ' + ($('#cd-timeline').children().length * 36 - 36) + 'px; }');

	        },
	        error: function (err) {

	        }
	    });
}
/**
 * 加载历史记录
 */
function loadHistory(flag){
	//console.info("历史记录")
	var historyStrs;
    $.post("/video/background/historyRecord/getLastestHistoryRecord", { },function(data){
    	//console.info(data)
    	
    	var data=data.data
    	if(data.length>0){
    		$("#historyList").empty();
    		if(flag=="0"){
    			$("#history").append("<ul class='history-list' id='historyList'></ul>")
    		}
    		for(var i=0;i<data.length;i++){
        		historyStrs="<li onclick='intoIndex(\""+data[i].contentId+"\",\"" +data[i].videoId+ "\",\""+data[i].fileName+"\",\""+data[i].contentTitle+"\",\""+data[i].uuid+"\")'><p>" + data[i].contentTitle + ":" + data[i].fileName + "</p></li>"
        		$("#historyList").append(historyStrs)
        	}
    		$("#history").append("<span class='history-triangle glyphicon glyphicon-triangle-top'></span>")
    	}
    	
    	
    	
    } );
}

/**
 * 初始化折叠按钮
 */
function initToggleButton() {
    // 隐藏菜单按钮
    $('.extend_hide').click(function () {
        $('.ysp-player-menu').hide();
        $('.ysp-player').width(1250);
        $('.extend_show').show();
    });

    // 显示菜单按钮
    $('.extend_show').click(function () {
        $(this).hide();
        $('.ysp-player').width(900);
        $('.ysp-player-menu').show();
    });
}

/**
 * 加载目录
 * @param {*} data  数据
 * @param {*} i     索引
 */
function loadSeries(data, i) {
	
	/**/
		//data.url=a;
    var name = data.name;
    var state = data.state;
    //var duration = data.duration;
    var playClass = "";
    switch (state) {
        case "0":
            playClass = "cd-play";
            break;
        case "1":
            playClass = "cd-played";
            break;
        case "2":
            playClass = "cd-finish";
            break;
        default:
            break;
    }
  if(i=="0" && playClass == "cd-play"){
	  playClass = "cd-played"
  }
 
    // 格式化序号
    if (i < 9) {
        i = '00' + i;
    } else if (i < 99) {
        i = '0' + i;
    }

    // 声明 html 元素
    var $dom = $(''
        + '<div class="cd-timeline-block" name="' + name + '" videoid="' + data.videoId + '"  data-uuid="' + data.uuid + '">'
        //+ '<div class="cd-timeline-number">'
       // + '<span class="cd-date">' + i + '</span>'
        //+ '</div>'
        + '<div class="cd-timeline-img ' + playClass + '"></div>'
        + '<div class="cd-timeline-content">'
        + '<span class="cd-date fl">' + name + '</span>'
        //+ '<span class="cd-date" style="display: none;">' + duration + '</span > '
        + '</div>'
        + '</div>'
    );

    $dom.click(function () {

        var $this = $(this);

        // 如果是没看过的视频
        if ($this.find('.cd-timeline-img').hasClass('cd-play')) {
        	$this.find('.cd-timeline-img').removeClass('cd-play').addClass('cd-played');
            // 发送请求修改视频状态
            /*$.ajax({
                type: "get",
                url: "./data/changeVideoState.json",
                // data: "data",
                dataType: "json",
                success: function (data) {
                    if (data.flag == 1) {
                        $this.find('.cd-timeline-img').removeClass('cd-play').addClass('cd-played');
                    }
                },
                error: function (err) {

                }
            });*/
        }

        // 改变正在播放的目录项
        $('.menu-playing').removeClass('menu-playing');
       var filename = $(this).attr("name");
      
       $('.video-name span:eq(1)').html(filename);
        $(this).addClass('menu-playing');
        videoId=$(this).attr("videoid")
        var dataUrl = $(this).attr("data-url")
        if(dataUrl=='' || dataUrl==null){
        	// 重新初始化播放器
            var videoUrl = Config.hBServerIp+"/api/v1/load_balancer/vod_play_url?AccessToken="+Config.AccessToken
    	        +"&OriginalFileUuid="+$dom.attr('data-uuid')+"&ServiceType=vod_http&FileExtName=.mp4";
            $.ajax({
    			type:"GET",
    			url:videoUrl,
    			async: true,
    			//dataType:"json",
    			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    			success:function(data){
    				//获取播放地址并转码
    				 videoUrl = data[0].Url;
    				
    				 $dom.attr("data-url",videoUrl);
    				 changeVideoSrc(videoUrl);
    			},
    			error:function(data){
    				layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
    			}
    		});
        }else{
        	
        	 changeVideoSrc(dataUrl);
        }
        
    });

    $('#cd-timeline').append($dom);
}

/**
 * 初始化播放器
 * @param {*}   src     视频地址
 * @param {*}   poster  封面地址
 */
function initPlayer(src) {
	//console.info(src)
	//alert(src)
    $('#player').attr({
        'src': src
        //'poster': poster
    });

    // 给播放器添加事件
    addVideoEvent();
}

/**
 * 给播放器添加事件
 * @param {*} player 
 */

function addVideoEvent() {
	var isFirstPlay="0";//是否第一次播放,0表示第一次播放
	$('#player').on('play',function(){
			
	    	if(isFirstPlay=="0"){
	    		
	    		isFirstPlay="1"
	    		/*var jqueryObj =	$(window.opener.document.getElementById(contentId));
	    		jqueryObj.text(parseInt(jqueryObj.text())+1)
	    		addHistory();*/
	    		
	    	}
	    	
	  });

    // 视频播放结束
    $('#player').on('ended', function () {
    	
        var $menuState = $('.menu-playing .cd-timeline-img');
        var  historyId = $('.menu-playing .cd-timeline-content').attr("historyId")

        playerPlay()  // 修改暂停按钮状态
        
       /* if (!$menuState.hasClass('cd-finish')) {
            // 修改视频播放状态 
            $.ajax({
                type: "get",
                url: "/video/background/historyRecord/updateState",
                data: {historyId:historyId},
                dataType: "json",
                success: function (data) {

                    if (data.flag == 1) {

                        // 修改播放状态
                        $menuState.removeAttr('class');
                        $menuState.attr('class', 'cd-timeline-img cd-finish');
                    }
                }
            });
        }*/
  
        // 自动播放下一集
        if ($('.menu-playing').next().length) {
        	//alert("ddjj")
            var $content = ''
                + '<div class="next">'
                + '<span>ｘ</span>'
                + '<span>5秒后播放下一集</span>'
                + '<button>立即播放</button>'
                + '</div>';

            //询问框
            layer.open({
                type: 1,
                content: $content,
                title: false,
                shade: 0,
                closeBtn: 0,
                area: ['200px', '30px'],
                resize: false,
                offset: ['528px', '5px'],
                success: function ($dom, index) {

                    var time = 4;

                    // 位置
                    $dom.css('position', 'relative');
                    $('div.video-js').append($dom);

                    // 5秒倒计时
                    clearInterval(timer)
                    timer = setInterval(function () {
                        $dom.find('.next span:eq(1)').html(time + '秒后播放下一集');
                        time--;
                        if (time === -1) {
                            $('.menu-playing').next().trigger('click');
                            layer.close(index);
                        }
                    }, 1000);

                    // 关闭按钮
                    $dom.find('.next span:eq(0)').click(function () {
                        clearInterval(timer)
                        layer.close(index);
                    });

                    // 立即播放
                    $dom.find('.next button').click(function () {
                        clearInterval(timer)
                        $('.menu-playing').next().trigger('click');
                        layer.close(index);
                    });
                }
            });
        } else {
            console.log('没有下一集了');
        }
    });
}

/**
 * 修改视频地址
 * @param {*} src 视频地址
 * @param {*} poster 封面地址
 */
function changeVideoSrc(src) {
	
    // 重新实例化
    initPlayer(src);
}

//添加历史记录
function addHistory(){
		//alert(videoId)
		$.post("/video/background/historyRecord/save", { contentId: contentId,videoId:videoId},function(data){
			var data = data.data
			  $('.menu-playing .cd-timeline-content').attr("historyId",data.id)
			  loadHistory("1")
	    } );
}

//搜索
function search(){
	var searchText = $("#searchText").val()
	
	loadMenu(searchText)
}

//注销
function logout(){
	document.location.href = Config.sso_logout_url+"?gotoURL="+path;
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
			window.location="/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+contentId+"&infoName="+encodeURIComponent(contentTitle)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileName);
		},
		error:function(data){
			layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
		}
	});
}

// 上一集
function prevPlay(){
    $('.menu-playing').prev().trigger('click');
}
// 下一集
function nextPlay(){
    $('.menu-playing').next().trigger('click');
}


// 暂停/播放
function playPause()
{ 

} 

function playerPlay(){
    $('#pausePlay').removeClass('glyphicon-pause').addClass('glyphicon-play')
}
function playerPause(){
    $('#pausePlay').removeClass('glyphicon-play').addClass('glyphicon-pause')
}
function initVideoCon(){
     let video = $('#player');
     let myVideo = document.getElementById('player');
     //Play/Pause control clicked
     $('.btnPlay').on('click', function() {
        if(video[0].paused) {
           video[0].play();
        }
        else {
           video[0].pause();
        }
        return false;
    });

    // 时间
    video.on('loadedmetadata', function() {
        $('.duration').text((video[0].duration/120).toFixed(2));
    });
    video.on('timeupdate', function() {
        $('.current').text((video[0].currentTime/120).toFixed(2));
    });

    video.on('timeupdate', function() {
        var currentPos = video[0].currentTime; //Get currenttime
        var maxduration = video[0].duration; //Get video duration
        var percentage = 100 * currentPos / maxduration; //in %
        $('.timeBar').css('width', percentage+'%');
    });

    var timeDrag = false;   /* Drag status */
    $('.progressBar').mousedown(function(e) {
        timeDrag = true;
        updatebar(e.pageX);
     });
     $(document).mouseup(function(e) {
        if(timeDrag) {
           timeDrag = false;
           updatebar(e.pageX);
        }
     });
     $(document).mousemove(function(e) {
        if(timeDrag) {
           updatebar(e.pageX);
        }
     });
      
     //update Progress Bar control
     var updatebar = function(x) {
        var progress = $('.progressBar');
        var maxduration = video[0].duration; //Video duraiton
        var position = x - progress.offset().left; //Click pos
        var percentage = 100 * position / progress.width();
      
        //Check within range
        if(percentage > 100) {
           percentage = 100;
        }
        if(percentage < 0) {
           percentage = 0;
        }
      
        //Update progress bar and video currenttime
        $('.timeBar').css('width', percentage+'%');
        video[0].currentTime = maxduration * percentage / 100;
     };

    // 缓冲区
    //loop to get HTML5 video buffered data
    var startBuffer = function() {
        if(video[0].buffered.length!=0){
            var maxduration = video[0].duration;
            var currentBuffer = video[0].buffered.end(0);
            var percentage = 100 * currentBuffer / maxduration;
            $('.bufferBar').css('width', percentage+'%');
        
            if(currentBuffer < maxduration) {
            setTimeout(startBuffer, 500);
            }
        }
    };
    setTimeout(startBuffer, 500);


    $('.fullscreenBtn').on('click', function() {
        openFullscreen(myVideo)
        return false;
    });

    $('#prevPlay').on('click',function () {
        prevPlay();
    })
    $('#nextPlay').on('click',function () {
        nextPlay();
    })
    $('#pausePlay').on('click',function () {
        if (myVideo.paused) 
            myVideo.pause(); 
        else 
            myVideo.play(); 
    })

}

//打开全屏方法
function openFullscreen(element) {
    if (element.requestFullscreen) {
        element.requestFullscreen();
    } else if (element.mozRequestFullScreen) {
        element.mozRequestFullScreen();
    } else if (element.msRequestFullscreen) {
        element.msRequestFullscreen();
    } else if (element.webkitRequestFullscreen) {
        element.webkitRequestFullScreen();
    }
}

//退出全屏方法
function exitFullScreen(element) {
    if (element.exitFullscreen) {
        element.exitFullscreen();
    } else if (element.mozCancelFullScreen) {
        element.mozCancelFullScreen();
    } else if (element.msExitFullscreen) {
        element.msExiFullscreen();
    } else if (element.webkitCancelFullScreen) {
        element.webkitCancelFullScreen();
    } else if (element.webkitExitFullscreen) {
        element.webkitExitFullscreen();
    }
}