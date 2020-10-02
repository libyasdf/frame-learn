$(function() {
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(theRequest.id);
	$("#resId").val(theRequest.resId);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {
		// 表单数据渲染
		var datas = {
			"id" : $("#id").val(),
			"resId" : $("#resId").val()
		};
		$.ajax({
    		url:"/mypage/wmgl/notice/edit",
    		data:datas,
    		success:function(json){
    			if (json.flag == '1') {
    				DisplayData.playData({
    					data : json
    				});
    			}
    		},
    		error:function(){
    			layer.msg("获取异常！", {icon: 2});
    		}
    	});
	}
	//校验
	$('#form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            content: {
                message: '通知内容验证失败！',
                validators: {
                    notEmpty: {
                        message: '通知内容不能为空'
                    },
                    stringLength: {
                        max: 200,
                        message: '通知内容不能超过200位'
                    }
                }
            },
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
})

/**
 * 保存
 */
function saveForm(state){
	debugger
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	var datas = {
    			id:$("#id").val(),
    			resId:$("#resId").val(),
    			content:$("#content").val(),
    			isValid:$("input[name='isValid']:checked").val(),
    			status:state
    	};
    	$.ajax({
    		type: "POST",
    		url:"/mypage/wmgl/notice/save",
    		data:datas,
    		success:function(json){
    			if (json.flag == '1') {
    				res = json.data.id;
    				$("#id").val(json.data.id);
    				if(json.data.status=='0'){
    					layer.msg("保存成功！", {icon: 1})
    					//刷新列表
    					parent.TableInit.refTable('right_table');
    				}else{
    					layer.msg("发布成功！", {icon: 1,time:2000},function(){
    						//刷新列表
    						parent.TableInit.refTable('right_table');
    						MyLayer.closeOpen();
    					})
    				}
    			}else {
    				if(datas.status=='0'){
    					layer.msg("保存失败！", {icon: 2});
    				}else{
    					layer.msg("发布失败！", {icon: 2});
    				}
    			}
    		},
    		error:function(){
    			if(datas.status=='0'){
					layer.msg("保存异常！", {icon: 2});
				}else{
					layer.msg("发布异常！", {icon: 2});
				}
    		}
    	});
    }
}
