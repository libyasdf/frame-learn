var dateLog;
var from;
$(function(){
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	from=regVlaue(theRequest.from)
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		if($("#opertation").val()=="VIEW"){
			httpRequest("get","/mypage/worklog/edit",datas,function (data){
				/*$("#dateLog").text(data.data.dateLog)
				$("#content").text(data.data.content)*/
				
				DisplayData.playData({data: data});
			});
		}else{
			httpRequest("get","/mypage/worklog/edit",datas,function (data){
				dateLog=data.data.dateLog;
				DisplayData.playData({data: data});
			});
		}
		
		
	 }
})





/**
 * 保存
 */
function saveForm(){
	var info = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	$.ajax({
    		type: "POST",
    		url:"/mypage/worklog/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json) {
    				info = json.id;
    				$("#id").val(json.id);
    				
    			}
    		}
    	});
    }
	
	
	return info;
}



/**
 * 保存
 * @returns
 */
function comitForm(){
	save();
	
}


/**
 * 保存表单数据
 */
function save(){
	$("#flag").val("0");
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		if(from=="1"){
			parent.loadWorkLogData1()
		}else{
			parent.TableInit.refTable('workLog_table');
		}
}}


/**
 * 上报
 */
function report(){
	report1();
}

/**
 * 上报
 */
function report1(){
	$("#flag").val("1");
	var data = saveForm();
	if(data){
		layer.msg("上报成功！", {icon: 1});
		$("#anniu").css("display","none")
	
		//刷新列表
		if(from=="1"){
			parent.loadWorkLogData1()
		}else{
			parent.TableInit.refTable('workLog_table');
		}
	}
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



