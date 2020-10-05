var mySwiper ;
$(function(){
	
	//API https://www.swiper.com.cn/api/index.html
	//初始化轮播图
    mySwiper = new Swiper('#news', {
        direction: 'horizontal',
        init:false,			//当你创建一个Swiper实例时是否立即初始化,改为false后，需要初始化时使用mySwiper.init()；
        loop: true,			//让Swiper看起来是循环的,形成一个环路
        grabCursor: true,	//设置为true时，鼠标覆盖Swiper时指针会变成手掌形状，拖动时指针会变成抓手形状
        autoplay: {		//等同于autoplay:true
            delay: 3000,	//切换间隔
            stopOnLastSlide: false,		//循环切换
            disableOnInteraction: false	//用户操作swiper之后，是否禁止autoplay
        },
        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination',	//分页器容器的css选择器或HTML标签。分页器等组件可以置于container之外
            type: 'bullets',	//样式类型，
            clickable: true		//是否可点击切换
        },
        // 如果需要前进后退按钮
        navigation: {
            nextEl: '.swiper-button-next',	//前进按钮的css选择器或HTML元素。
            prevEl: '.swiper-button-prev'	//后退按钮的css选择器或HTML元素。
        },
        // 回调函数
        on: {
            slideChangeTransitionEnd: function() {	//swiper从一个slide过渡到另一个slide结束时执行。
                var index = this.activeIndex;
                if(index > $('.banner-title').length) index = 1;
                $('.banner-title').addClass('hide');
                $('.banner-title:eq(' + (index-1) + ')').removeClass('hide');
            },
            click: function(event){
            	console.log(event.target);
            }
        }
        
    });
	
	//获取轮播图地址
	getBannerImgs("JNXW");
	
	//获取宣传图片
	getBannerImgs("XCY");
	
	//获取信息发布消息
	getInfoContent("JNXW","8");	//局内新闻
	getInfoContent("GWFB");		//公文发布
	getInfoContent("ZGZL");		//政工专栏
	getInfoContent("FLFG");		//法律法规
	getInfoContent("NBGZ");		//内部规章
	getInfoContent("WLXX");		//网络信息
	//getInfoContent("CKZL");		//参考资料
	//getInfoContent("BSZN");		//办事指南
	//getInfoContent("ZXXX");		//在线学习
    getInfoContent("SZJX");		//网络信息
    getInfoContent("DSZS");		//网络信息
    getInfoContent("TWDT");		//网络信息
	//通知公告
	getNoticeList();
	
	//双栏目框添加鼠标悬浮事件
	//$(".double").mouseover(function(data){
	$(".double").click(function(data){
		 console.log(data.target);
		 $(data.target).parent().parent().find("div").removeClass("active");
         $(this).parent().addClass("active");
		 var target = $(data.target).attr("target");
		 var hide = $(data.target).attr("hide");
		 $("#" + hide).hide();
		 $("#" + target).show();
		 /************ 查询数据 ************/
		 if(target != "noticeInfo" && target != "wenjuan"){
			 getInfoContent(target);
		 }else if(target == "noticeInfo"){//通知公告
			 getNoticeList();
		 }else if(target == "wenjuan"){//获取调查问卷
			 getSurveyList();
		 }
		 /********** 改变更多链接 ***********/
		 //如果是信息发布，则添加href、添加target属性、去除onclick事件
		 if(target != "noticeInfo" && target != "wenjuan"){
			 var url = $(data.target).parent().parent().find("a").attr("data-url");
			 $(data.target).parent().parent().find("a").attr("onclick","javascript:void(0);");
			 $(data.target).parent().parent().find("a").attr("target","_blank");
			 $(data.target).parent().parent().find("a").attr("href",url+target);
		 }
		 //如果是通知公告或者问卷，则添加onclick事件、移除target属性，重置href
		 if(target == "noticeInfo"){
			 $(data.target).parent().parent().find("a").removeAttr("target");
			 $(data.target).parent().parent().find("a").attr("href","javascript:void(0);");
			 $(data.target).parent().parent().find("a").attr("onclick","moreNoticeList();");
		 }else if(target == "wenjuan"){
			 $(data.target).parent().parent().find("a").removeAttr("target");
			 $(data.target).parent().parent().find("a").attr("href","javascript:void(0);");
			 $(data.target).parent().parent().find("a").attr("onclick","moreSurveyList();");
		 }
		 /********** 改变更多链接结束 ***********/
	})
	
})

function moreSurveyList(){
	
}

/**
 * 查询通知公告列表
 */
function getNoticeList(){
	$.ajax({
		url:"/system/notice/getNoticeList",
		data:{
			pageSize:"6",
			pageNumber:"1"
		},
		dataType:"json",
		success: function(json){
			var str = "";
			if(json.flag == "1"){
				var notices = json.data.rows;
				var size = notices.length;
				if(size > 0){
					$.each(notices, function(i,notice){
						var time = notice.creTime.substring(0,10);
						str += "<li class='clearFloat'>";
						str += "<p><a href='javascript:void(0);' onclick='openNotice(\""+notice.id+"\");' title ="+notice.title+">" + notice.title + "</a></p>";
						str += "<p>" + time + "</p>";
						str += "</li>";
					})
				}else{
					str += "<img src='/static/images/gateway/noMessage.png'/>";
				}
			}else{
				str += "<img src='/static/images/gateway/noMessage.png'/>";
			}
			$("#noticeInfo").empty().append(str);
		},
		error: function(){
			console.log("error");
			var str = "";
			str += "<img src='/static/images/gateway/noMessage.png'/>";
			$("#noticeInfo").empty().append(str);
		}
	})
}

/**
 * 打开通知公告详情页
 */
function openNotice(id){
	MyLayer.layerOpenUrl({
        url: '/modules/system/notice/noticeBackForm.html?id=' + id + "&oper=VIEW",
        title: "通知公告详情"
    }) 
}

/**
 * 根据栏目编号获取栏目下的消息
 * @param columnCode 栏目编号
 * @param pageSize 每页条数
 */
function getInfoContent(columnCode,pageSize){
	$.ajax({
		url:"/info/content/getContentByColumn",
		dataType:"json",
		data:{
			columnCode: columnCode,
			pageSize: pageSize?pageSize:"6",
			pageNumber:"1"
		},
		success: function(json){
			var str = "";
			if(json.flag == "1"){
				var infos = json.data.rows;
				var size = infos.length;
				if(size > 0){
					$.each(infos, function(i,info){
						var time = info.creTime.substring(0,10);
						if(info.isZd){
							str += "<li class='clearFloat Topnews'>";
							str += "<div class='Topnum'>"+(i+1)+"</div>";
							str += "<p class='Toptitle'><a href='/gateway/infoDetail.html?columnCode="+columnCode+"&id="+info.id+"' target='_blank' title ="+info.title+">" + info.title + "</a></p>";
						}else{
							str += "<li class='clearFloat'>";
							str += "<p><a href='/gateway/infoDetail.html?columnCode="+columnCode+"&id="+info.id+"' target='_blank' title ="+info.title+">" + info.title + "</a></p>";
						}
						str += "<p>" + time + "</p>";
						str += "</li>";
					})
				}else{
					str += "<img src='/static/images/gateway/noNotice.png'/>";
				}
			}else{
				str += "<img src='/static/images/gateway/noNotice.png'/>";
			}
			$("#" + columnCode).empty().append(str);
		},
		error: function(){
			console.log("error");
			var str = "";
			str += "<img src='/static/images/gateway/noNotice.png'/>";
			$("#" + columnCode).empty().append(str);
		}
	})
}

/**
 * 获取轮播图地址
 */
function getBannerImgs(columnCode){
	$.ajax({
		url:"/info/content/getImgsByColumn",
		dataType:"json",
		data:{
			columnCode: columnCode,
		},
		success: function(json){
			console.log(json);
			if(columnCode == "JNXW"){
				appendBannerImg(json,columnCode);
			}else if(columnCode == "XCY"){
				appendNewsImg(json,columnCode);
			}
		},
		error: function(){
			console.log("error");
		}
	})
}

/**
 * 拼接轮播图html
 */
function appendBannerImg(json,columnCode){
	if(json.flag == "1"){
		var imgHtml = "";	//图片html
		var titleHtml = "";	//标题html
		var length = json.data.length;
		if(length > 0){
			for(var i=0;i<length;i++){
				var path = json.data[i].imgPath;
				var title = json.data[i].title;
				var infoId = json.data[i].infoId;
				if(path && path.indexOf("upload") > 0){
					path = path.substring(path.indexOf("upload")-1);
					imgHtml += "<div class='swiper-slide'>";
					imgHtml += "<a href='/gateway/infoDetail.html?columnCode="+columnCode+"&id="+infoId+"' target='_blank'>";
					imgHtml += "<img src='"+path+"' alt=''>";
					imgHtml += "</a>";
					imgHtml += "</div>";
					if(i == 0){
						titleHtml += "<span class='banner-title'>";
					}else{
						titleHtml += "<span class='banner-title hide'>";
					}
					titleHtml += title;
					titleHtml += "</span>";
				}
				if(i == 2){
					break;
				}
			}
		}else{
			imgHtml += "<div class='swiper-slide'>";
			imgHtml += "<img src='/static/images/gateway/noNews.jpg' alt=''>";
			imgHtml += "</div>";
		}
		$("#imgDiv").append(imgHtml);
		$("#titleDiv").append(titleHtml);
	}
	mySwiper.init();	//初始化轮播图
	//mySwiper.update(false);
}

/**
 * 拼接宣传语图片
 */
function appendNewsImg(json,columnCode){
	if(json.flag == "1"){
		var imgHtml = "";	//图片html
		var length = json.data.length;
		if(length > 0){
			for(var i=0;i<length;i++){
				imgHtml = "";
				var path = json.data[i].imgPath;
				var infoId = json.data[i].infoId;
				if(path && path.indexOf("upload") > 0){
					path = path.substring(path.indexOf("upload")-1);
					/*imgHtml += "<a href='/gateway/infoDetail.html?columnCode="+columnCode+"&id="+infoId+"' target='_blank'>";*/
					imgHtml += "<img src='"+path+"' alt='' height='200px;' width='390px;'>";
					/*imgHtml += "</a>";*/
				}
				$("#newsImg" + (i+1)).append(imgHtml);
				if(i == 2){
					break;
				}
			}
		}
		//判断有几个图片，剩余的图片填充“无新闻”图片
		var size = $("li[name='newsImgs']").find("img").size();
		imgHtml = "<img src='/static/images/gateway/noNews.jpg' alt='' height='200px;' width='390px;'>";
		for(var j = 3; j > size; j--){
			$("#newsImg" + j).append(imgHtml);
			$("#newsImg" + (j+3)).append(imgHtml);

		}

        /**
         * 轮播图
         */
        var Dynamic_time=self.setInterval( function slideshow(){
            var rightRela=$("#slideshowZH").attr("data-right");//通过.css("right获取,数值偶尔不准
            //console.log("前:"+rightRela);
            rightRela=parseInt(rightRela)+403;
            //console.log("后:"+rightRela);
            if(rightRela>=1612){
                rightRela=0;
                //console.log("赋为零:"+rightRela2);
                //console.log("使失效");
                $("#slideshowZH").css("transition","right 0s ease 0s");
                setTimeout(function(){
                    $("#slideshowZH").css("transition","right 2s ease 0s")
                    $("#slideshowZH").attr("data-right","403");
                    $("#slideshowZH").css("right","403px");
                },0);
            }else{
                //console.log("使成功");
                $("#slideshowZH").css("transition","right 2s ease 0s")
            }
            $("#slideshowZH").attr("data-right",rightRela);
            $("#slideshowZH").css("right",rightRela+"px");

        },3000);//Dynamic_time(可变)即全局变量,获取setInterval()的返回值,方便关闭;执行此表开始循环

	}
}

/**
 * 通知公告更多列表
 */
function moreNoticeList(){
	MyLayer.layerOpenUrl({
        url: '/modules/system/notice/moreNoticeList.html',
        title: "通知公告列表"
    })
}

/**
 * 获取调查问卷
 */
function getSurveyList(){
	$.ajax({
		type:"get",
		url:"/wenjuan/surveyDirectory/getSurveyList",
		data: {
			pageSize: 6,
			pageNumber: 1
		},
		dataType:"json",
		success:function(res){
			console.log("wenjuan");
			console.log(res);
			var html = "";
			if(res.flag == "1"){
				if(res.data.rows.length != 0){
					$.each(res.data.rows, function(i,msg){
						var time = new Date(msg.createDate).Format("yyyy-MM-dd");
						html += "<li class='clearFloat'>";
						html += "<p><a href='" + msg.htmlPath + "' target='_blank' title="+msg.surveyName+">" + msg.surveyName + "</a></p>";
						html += "<p>" + time + "</p>";
						html += "</li>";
					})
				}else{
					html += "<img src='/static/images/gateway/noMessage.png'/>";
				}
			}else{
				html += "<img src='/static/images/gateway/noMessage.png'/>";
			}
			$("#wenjuan").empty().append(html);
		},
		error:function(){
			console.log("error");
			var html = "<img src='/static/images/gateway/noMessage.png'/>";
			$("#wenjuan").empty().append(html);
		}
	})
}

/**
 * 调查问卷更多列表
 */
function moreSurveyList(){
	MyLayer.layerOpenUrl({
        url: '/modules/wenjuan/content/diaowen-design/moreSurveyList.html',
        title: "调查问卷列表"
    })
}
/**
 * 轮播图
 */
var Dynamic_time=self.setInterval("slideshow()",3000);//Dynamic_time(可变)即全局变量,获取setInterval()的返回值,方便关闭;执行此表开始循环
function slideshow(){
    var rightRela=$("#slideshowZH").attr("data-right");//通过.css("right获取,数值偶尔不准
    //console.log("前:"+rightRela);
    rightRela=parseInt(rightRela)+403;
    //console.log("后:"+rightRela);
    if(rightRela>=1612){
        rightRela=0;
        //console.log("赋为零:"+rightRela2);
        //console.log("使失效");
        $("#slideshowZH").css("transition","right 0s ease 0s");
        setTimeout(function(){//解决,当重新赋为初始状态时,需要等待下次Dynamic_time执行
            $("#slideshowZH").css("transition","right 2s ease 0s")
            $("#slideshowZH").attr("data-right","403");
            $("#slideshowZH").css("right","403px");
		},0);
	}else{
        //console.log("使成功");
        $("#slideshowZH").css("transition","right 2s ease 0s")
	}
    $("#slideshowZH").attr("data-right",rightRela);
    $("#slideshowZH").css("right",rightRela+"px");

}

