$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	//渲染图片选择
	//showImage("yydh",1);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/basicSet/serviceDept/edit",datas,function (data){
			DisplayData.playData({data: data});
			if($("#opertation").val() == "VIEW"){
				if(data.data.path){
					$("#showPath").attr("src",data.data.path);
				}
			}else if($("#opertation").val() == "EDIT"){
				// 修改页面不允许修改服务单位
				$("#serviceDeptName").attr("onclick","");
				$("#serviceDeptName").attr("readonly","readonly");
				$(".input-group-addon").attr("onclick","");
			}
		});
	}
})


/**
 * 提交表单
 * @returns
 */
function comitForm(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存
 */
function saveForm(){
	var application = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	$.ajax({
    		type: "POST",
    		url:"/zhbg/hygl/basicSet/serviceDept/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json) {
    				application = json.id;
    				$("#id").val(json.id);
    			}
    		}
    	});
    }
    return application;
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

