
$(function(){
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	var orgId=theRequest.orgId
	var orgName=theRequest.orgName
	
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
			httpRequest("get","/system/config/orgdepmapping/edit",datas,function (data){
				$("#orgName").text(orgName)
				DisplayData.playData({data: data});
			});
	
	 }else{
		 $("#peakDeptid").val(orgId)
		 $("#orgName").text(orgName)
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
    		url:"/system/config/orgdepmapping/saveForm",
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
 * 保存表单数据
 */
function save(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
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



