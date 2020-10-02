var flg;
$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#pid").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	$("#workItemId1").val(theRequest.workItemId1);
	$("#fankunUserId").val(theRequest.fankunUserId);//服务 反馈人d
	flg=theRequest.flg;
	
	if($("#fankunUserId").val()){
		//某人服务的反馈情况
		$("#addWorkTask").hide()
	}
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#pid").val() != ""){
		showMeetingRoom($("#pid").val());
		//主表单数据渲染
		var datas = {"id":$("#pid").val(),"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/meetingApply/edit",datas,function (data){
			var time = data.data.meetingTime;
			if(data.data.meetingTime){
				data.data.meetingStartDate = time.substr(0,10);
				data.data.meetingEndDate = time.substr(22,10);
				data.data.meetingStartTime = time.substr(11,9);
				data.data.meetingEndTime = time.substr(33,9);
			}
			DisplayData.playData({data: data});
			//checkbox会议服务
			var meetingServices = data.data.meetingServices.split(",");
			var ar = [];
			$.each(meetingServices, function (i, n) {
				var datas = {"id":n};
				httpRequest("get","/zhbg/hygl/basicSet/meetingService/edit",datas,function (data){
					
					if(data.data){
						ar.push(data.data.meetingService);
					}
					
				})
                	
            })
			$("#meetingServices").text(ar.join(", "));
			
		});
		
		//渲染子表
		//showMeetingServices();
		
	}else{
		//没有pid的时候
		$("#addId").click(function(){
			  var serviceDeptName = $("#serviceDeptName").val(); 
			  if(serviceDeptName == undefined || serviceDeptName == "" || serviceDeptName == null){
				  layer.msg('请先选择会务服务单位',{icon:0});
			  }
		});
		
		$("#deleteId").click(function(){
			layer.msg('请选择要删除的行',{icon:0});
		})
	}
	
})


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


