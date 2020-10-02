$(function(){
	var theRequest = GetRequest();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
			httpRequest("get", "/mypage/wmgl/index/order/edit", datas, function(data) {
				console.info(data)
				//if(data.data.fullDeptName && data.data.fullDeptName != "\"\"") {
						$("#deptName1").text(data.data.fullDeptName);
				//}
				DisplayData.playData({data: data});
			});
			
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









