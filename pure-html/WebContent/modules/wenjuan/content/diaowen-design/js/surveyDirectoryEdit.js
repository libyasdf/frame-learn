
$(function(){
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	$("#type").val(theRequest.type);


})





/**
 * 保存
 */
function saveForm(){
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
		var info = "";
		$.ajax({
			type: "POST",
			url:"/wenjuan/surveyDirectory/save",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				console.info(json)
				if (json) {
					info = json.id;
					$("#id").val(json.id);
					var params="surveyId=" + json.id;
					//跳转列表页
					$("#searchA").prop("href","/wenjuan/surveyDesign/intoDesignPage?" + params);
					$("#searchA")[0].click();	//触发a标签
				}
			}
		});
    }
	return info;
}






/**
 * 保存表单数据
 */
function save(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		parent.TableInit.refTable('right_table1');
		parent.putBackData1()
		
		
}}






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



