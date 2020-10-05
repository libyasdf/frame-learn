var fankuiType;
var attendDeptType;
var attentionItem;
var flg;//为1时表示点击座位的发布按钮，
$(function(){
	var theRequest = GetRequest();
	$("#noticeId").val(regVlaue(theRequest.noticeId));
	$("#resId").val(theRequest.resId);
	 fankuiType = regVlaue(theRequest.fankuiType);
	 attendDeptType = regVlaue(theRequest.attendDeptType);
	 attentionItem = regVlaue(theRequest.attentionItem);
	 flg = regVlaue(theRequest.flg);
	 if(attentionItem!=null && attentionItem!="null"){
		 $("#remark").val(attentionItem)
	 }
	

})





/**
 * 保存
 */
var parentFn;
function saveForm(){
	var info = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){

    	$.post("/zhbg/hygl/notice/updateAttentionItem", { meetingNoticeId: $("#noticeId").val(), attentionItem: $("#remark").val()},function(data){
    		
    		if(flg=='1'){
    			parentFn()
    			layer.load(2);
    			
    		}else{
    			//window.open('/modules/zhbg/hygl/meetingNotice/seatPrint/seatTicket.html?meetingNoticeId=' + $("#noticeId").val() + "&remark=" + encodeURI($("#remark").val()) + "&fankuiType="+fankuiType + "&attendDeptType="+attendDeptType); 
        		if(data.flag=='1'){
        			
        			parent.putBackData1()
        		}
    			
    		}
        	
    	} );
    	
    }
	
	
	return info;
}

function freshTable(){
	//alert("1")
	parent.fresh()
}




/**
 * 保存表单数据
 */
function save(){
	var data = saveForm();
}






/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}


/**
 * 重置
 */
function chongzhi(){
	$("#deptId").val("")
	$("#deptName").val("")
}



