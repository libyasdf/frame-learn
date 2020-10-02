$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	// $("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	$("#type").val(theRequest.type);
	$("#nodeId").val(theRequest.nodeId);
	$("#id").val(theRequest.id);
	if(theRequest.id!=null && theRequest.id!=''){
		$.get("/zhbg/xxkh/tree/datatable/getOne", {
			id : theRequest.id
		},function(data) {
			DisplayData.playData({
				data : data
			});
		}, 'json')
		//showFiles第一个参数1表示可以删除资料的文件,第三个参数0表示不需要将学习记录存到数据库
		showFiles("1",$("#id").val(),"0");
	}
	//iniFileUpload();
})

/**
 * 保存
 */
function saveForm() {
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	// 手动触发验证
	bootstrapValidator.validate();
	if(bootstrapValidator.isValid()){
		$.ajax({
			type : "POST",
			url : "/zhbg/xxkh/tree/datatable/save",
			data : $("#form").serialize(),
			async : false,
			success : function(json){
				//alert(json.flag);
				if (json.flag == '1') {
					$("#id").val(json.data.id);
					layer.msg("保存成功！", {icon : 1});
					parent.TableInit.refTable('right_table');
				}else{
					layer.msg("不可重复保存！", {icon : 0});
				}
			},
			error:function() {
				layer.msg("服务异常，请稍后再试！", {icon : 0});
			}
		});
	}
}