 var publishState;
	 var attendDeptId;
	 var backId;
	 var meetingNoticeId;
	 //var remark=theRequest.remark;
	 var userid;
	 var id;
$(function() {
	
	// 获取参数
	var theRequest = GetRequest();
	  publishState = theRequest.publishState;
	  attendDeptId = theRequest.attendDeptId;
	  backId = (theRequest.backId).split("?")[0];
	  //backId=backId.split("?")[0]
	  meetingNoticeId=theRequest.meetingNoticeId;
	  remark=theRequest.remark;
	  userid=regVlaue(theRequest.userid);
	  id=theRequest.id;
	  if(userid){
		  
		  $("#close").hide();
	  }
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if (backId != "") {
		// 修改
		// 表单数据渲染
		var datas = {
			"id" : backId
		};
			httpRequest("get", "/zhbg/hygl/meetingNoticeBack/edit", datas, function(data) {
				console.info("................................")
				console.info(data)
				//判断反馈类型
				if(data.data.feedbackType=='1'){
					$('#renshu').css("display", "block")
					data.data.feedbackType = '参会人数';
				}else{
					data.data.feedbackType = '参会人名';
					$('#selectRen').css("display", "block")
					$('#renshu2').css("display", "block")
					
				}
				//无需反馈  不显示反馈详情和最晚反馈时间
				if(data.data.state=='2'){
					$("#lastFkTime").hide();
					$("#fanKuiDisplay").hide();
					$("#backTime2").hide();
					data.data.state = "否";
				}else if(data.data.state=='1'){
					data.data.state = "是";
					$("#fanKuiDisplay").show();
				}
				//未反馈 不显示反馈详情
				if(data.data.state=='0'){
					$("#fanKuiDisplay").hide();
					data.data.state = "是";
				}
				DisplayData.playData({
					data : data
				});
				showMeetingRoom(data.data.meetingApplyId);
			});
	


		
	} 
	iniFileUpload();
	
	
	
	
})


	//查看座位图
	function showSeatMap(){
		MyLayer.layerOpenUrl({
	          url: "/modules/zhbg/hygl/meetingNotice/arrangeSeat/seatMap.html?publishState=1&meetingNoticeId="+meetingNoticeId+"&attendDeptId="+attendDeptId+"&userid="+userid
	          //title: "查看座位图"
	     });
	}

//查看座位票
function showSeatTicket(){
	MyLayer.layerOpenUrl({
          url: "/modules/zhbg/hygl/meetingNotice/seatPrint/seatTicket.html?meetingNoticeId="+meetingNoticeId+"&attendDeptId=" + attendDeptId + "&userid="+userid
          //title: "查看座位票"
     });
}

/**
 * 调用工作流配置的按钮
 * 
 * @returns
 */

var $form;
function iframeCallback(form) {
	$form = $(form);
}






/**
 * 初始化文件上传
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({
		viewParams: {"tableId":$("#noticeId").val(),"tableName":"HYGL_MEETING_NOTICE"},
		editOrView:$("#opertation").val(),
        maxFileSize:5*1024*1024 //5M
    });
}

/**
 * 空值设置
 * 
 * @param val
 * @returns
 */
function regVlaue(val) {
	if (!val) {
		val = "";
	}
	return val;
}