/**
 * 方法说明：公共方法遮罩层的打开和关闭
 * 使用方法：Overlay.名称
**/

var Overlay = {
	//**打开遮罩
	 init_waitting:function(){
	    var w = window.screen.width/2-50;
	    $(document.body).append('<div class="overlay" style="z-index:9999" ></div>');
	    $(document.body).append('<div id="AjaxLoading" class="showbox" style="margin:0px auto;"><div class="loadingWord"><img src="'+BASEPATH+'/product/workflow/image/waiting.gif">加载中，请稍候...</div></div>');
	    $(".overlay").css({'display':'inline','opacity':'0.8'});
	    $(".showbox").stop(true).animate({'margin-top':'250px','opacity':'1'},200);
	    $(".loadingWord").css({'left':w,'width':'300px','heigth':'100px'});
	},
	//**关闭遮罩
	close_waitting:function(){
	    $(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
	    $(".overlay").css({'display':'none','opacity':'0'});
	    $(".overlay").remove();
	    $(".showbox").remove();
	}
}