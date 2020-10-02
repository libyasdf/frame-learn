$(function() {
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(theRequest.id);
	$("#resId").val(theRequest.resId);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {
		// 修改
		// 表单数据渲染
		var datas = {
			"id" : $("#id").val(),
			"resId" : $("#resId").val()
		};
		$.ajax({
    		url:"/mypage/wmgl/config/edit",
    		data:datas,
    		success:function(json){
    			if (json.flag == '1') {
    				DisplayData.playData({
    					data : json
    				});
    			}
    		},
    		error:function(){
    			layer.msg("保存异常！", {icon: 2});
    		}
    	});
		
	}
})

/**
 * 提交表单
 * @returns
 */
function commitForm(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1,time:2000},function(){
			//刷新列表
			parent.TableInit.refTable('right_table');
			MyLayer.closeOpen();
		});
	}else{
		layer.msg("保存失败！", {icon: 2});
	}
}

/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	var datas = {
    			id:$("#id").val(),
    			resId:$("#resId").val(),
    			period:$("#period").text(),
    			isvalid:$("input[name='isvalid']:checked").val(),
    			lostCreditLimt:$("#lostCreditLimt").val(),
    			lockTime:$("#lockTime").val(),
    			attentionItme:$("#attentionItme").val()
    	};
    	$.ajax({
    		type: "POST",
    		url:"/mypage/wmgl/config/update",
    		data:datas,
    		async: false,
    		success:function(json){
    			if (json.flag == '1') {
    				res = json.data.id;
    				$("#id").val(json.data.id);
    			}else {
    				
    			}
    		},
    		error:function(){
    			layer.msg("保存异常！", {icon: 2});
    		}
    	});
    }
    return res;
}