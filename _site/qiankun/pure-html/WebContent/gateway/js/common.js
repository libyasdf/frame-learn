/**
 * 信息门户页面公共js
 * 包括：顶部用户信息、注销、搜索、导航栏样式
 */
$(function(){
	
	//获取用户信息
	userName();
	
	//获取用户头像
	//getUserImg();
	
	//注销
	$("#logout").click(function(){
		window.location.href=Config.sso_logout_url + "?gotoURL=" + path ;
	});
	
	// 导航栏样式
    $('.nav li').click(function () {
        if ($(this).hasClass('nav-active')) return undefined;
        if ($('.nav .nav-active').length) {
            for (var i = 0; i < $('.nav .nav-active').length; ++i) {
                $('.nav .nav-active:eq(' + i + ')').removeClass('nav-active').css('background-color', '#058edc');
            }
        }
        $(this).addClass('nav-active');
    });

    $('.nav .navList li').mouseover(function () {
        if (!$(this).hasClass('nav-active')) $(this).css('background-color', '#047dcb');
    });

    $('.nav .navList li').mouseout(function () {
        if (!$(this).hasClass('nav-active')) $(this).css('background-color', '#058edc');
    });
	
	//给所搜框添加回车事件
	$("#search").keyup(function(e){
		var key = e.which;
		if (key == 13) {
			searchContentByColumn();
		}
	})

	var navTime; // 显示菜单定时器
	// 显示更多菜单
    $('.nav .btnMoreNav').mouseover(function () {
        clearTimeout(navTime);
    	$('.moreNav').show()
    });
    $('.nav .btnMoreNav').mouseout(function () {
        navTime = setTimeout(function () {
            $('.moreNav').hide()
        },100)
    });
    $('.moreNav').mouseover(function () {
		clearTimeout(navTime);
    })
    $('.moreNav').mouseout(function () {
        navTime = setTimeout(function () {
            $('.moreNav').hide()
        },100)
    })
})

/**
 * 获取用户信息
 */
function userName(){
	var num ;
	if(getcookie('usernm')){
		$('#userInfo').html('您好！' + getcookie('usernm'));
		clearTimeout(num);
	}else{
		num = setTimeout("userName()", 500);
	}
	console.log("1");
}

/**
 * 搜索框
 */
function searchContentByColumn(){
    debugger;
	var columnCode = $("#searchColumnCode").prop("data-columnCode");
	var title = $("#search").val().trim();
	/*title = encodeURI(title);*/
	if(title && columnCode){
		title = encodeURI(encodeURI(title));
		//跳转列表页
		$("#searchA").prop("href","/gateway/infoList.html?title=" + title + "&columnCode=" + columnCode);
		$("#searchA")[0].click();	//触发a标签
	}
}

