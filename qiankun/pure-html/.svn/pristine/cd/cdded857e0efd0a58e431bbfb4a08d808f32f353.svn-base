var dateLog;
var from;
$(function(){
	var theRequest = GetRequest();
	//$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	$("#datePlan").val(theRequest.datePlan);
	var year = '';
	var month = '';
	var day = '';
	year = theRequest.datePlan.split('-')[0] + '年';
    if (theRequest.datePlan.split('-')[1].substr(0, 1) == 0) {
        month = theRequest.datePlan.split('-')[1].substr(1, 2)+'月' ;
    } else {
        month = theRequest.datePlan.split('-')[1] + '月';
    }
	if (theRequest.datePlan.split('-')[2].substr(0, 1) == 0) {
        day = theRequest.datePlan.split('-')[2].substr(1, 2)+'日' ;
    } else {
        day = theRequest.datePlan.split('-')[2] + '日';
    }
	$("#tishi").text("您正在添加的是"+year+month+day+"的计划和日志");
	$("#resId").val(theRequest.resId);
	
initFormData();
})

function initFormData(){
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#datePlan").val() != ""){
		
		//表单数据渲染
		var datas = {"datePlan":$("#datePlan").val(),"id":$("#id").val(),"resId":$("#resId").val()};
		if($("#opertation").val()=="VIEW"){
			httpRequest("get","/mypage/workplan/edit",datas,function (data){
				/*$("#dateLog").text(data.data.dateLog)
				$("#content").text(data.data.content)*/
				
				//DisplayData.playData({data: data});
			});
		}else{
			httpRequest("get","/mypage/workplan/edit",datas,function (data){
				//dateLog=data.data.datePlan;
				
				//DisplayData.playData({data: data});
				//$("#content").val(data.data.content).change();
			});
		}
	 }
	if($("#isFinish").val()=='1'){
		$("#anniu").css("display","none");
	}else{
		$("#anniu").css("display","inline-block");
	}
}



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
    		url:"/mypage/workplan/findByDatePlan",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(data){
    			if ($("#id").val()==""&&data.id) {
    				layer.msg("已存在当前日期的计划，请选择其他日期！", {
    					icon : 0
    				});
    				
    			}else if ($("#id").val()&&data.id&&data.id!=$("#id").val()){
    				layer.msg("已存在当前日期的计划，请选择其他日期！", {
    					icon : 0
    				});

    			}else{
    				$.ajax({
    	        		type: "POST",
    	        		url:"/mypage/workplan/saveForm",
    	        		data:$("#form").serialize(),
    	        		async: false,
    	        		success:function(json){
    	        			if (json) {
    	        				info = json.id;
    	        				$("#id").val(json.id);
    	        				if($('#year', window.parent.document).val()&&$('#month', window.parent.document).val()){
    	        					parent.setData($('#year', window.parent.document).val().substring(0,$('#year', window.parent.document).val().indexOf("年")), $('#month', window.parent.document).val().substring(0,$('#month', window.parent.document).val().indexOf("月")))
    	        				}//parent.toToday();
    	        				if($('#year', window.top.document).val()&&$('#month', window.top.document).val()){
    	        					top.setData($('#year', window.top.document).val().substring(0,$('#year', window.top.document).val().indexOf("年")), $('#month', window.top.document).val().substring(0,$('#month', window.top.document).val().indexOf("月")))
    	        				}
    	        			}
    	        		}
    	        	});
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
		
			parent.TableInit.refTable('workLog_table');
		
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
	var data = "";
var bootstrapValidator = $("#form").data('bootstrapValidator');
	
    //手动触发验证
    bootstrapValidator.validate();
    
    if(bootstrapValidator.isValid()){
layer.confirm('确认是否已完成工作计划，点击确定后不能修改！',{icon:3,title:'完成'},function(){
	data = saveForm();
	if(data){
		layer.msg("计划完成保存成功！", {icon: 1});
		$("#anniu").css("display","none")
	
		//刷新列表
		
			parent.TableInit.refTable('workLog_table');
		
	}
	});
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



