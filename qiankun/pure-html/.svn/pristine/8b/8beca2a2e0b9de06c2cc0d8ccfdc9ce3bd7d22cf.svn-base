
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
	},
	/*
	 *功能:还原按钮状态
	 *参数:id需要还原按钮状态的元素的id
	 */
	removeDisable:function(id){
	    if($("#"+id).length>0){
	    	var oldClick=$("#"+id).data('display').oldClick;
	        var oldClass=$("#"+id).data('display').oldClass;
	        $("#"+id).attr("onclick",oldClick).removeClass().addClass(oldClass);
	    }
	},
	//关闭office控件:true为隐藏，false为显示。
	closeOrOpenOffice:function(res){
		var BjzwArea=$('#singleBjzwform');
		if(typeof(BjzwArea)!="undefined"){
			if(res){
				BjzwArea.attr('style', 'visibility:hidden'); 
			}else{
				BjzwArea.removeAttr('style', 'visibility:hidden');
			}
		}
	},
	unableAlert:function(msg){
		Overlay.closeOrOpenOffice(true);
		$.dialog({title:'警告',lock:true,icon: 'alert.gif',content: msg+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",ok:function(){
			Overlay.closeOrOpenOffice(false);
		}}); //不能操作提醒
	},
	operaDiv:function(obj){
		$("#"+obj).toggle(500);
	},
	operaClassDiv:function(obj){
		$("."+obj).toggle(500);
	},
}