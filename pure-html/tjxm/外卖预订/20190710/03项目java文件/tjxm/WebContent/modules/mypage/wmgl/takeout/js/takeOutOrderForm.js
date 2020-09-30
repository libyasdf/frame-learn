$(function(){
	var theRequest = GetRequest();
    var theRequest = GetRequest();
    $("input[name=resId]").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.takeOutid));
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
			httpRequest("get", "/mypage/wmgl/takeout/takeoutInfo/edit", datas, function(data) {
				$("#takeOutId").val(data.data.id);
				$("#takeOutId1").val(data.data.id);
				DisplayData.playData({data: data});
			});
			//加载外卖的分类标签页
			loadCategory()
	 	}

	
})

//加载外卖的分类标签页
function loadCategory(){
	$.post("/mypage/wmgl/index/OrderDetil/getOrderCategory", { takeOutId:$("#id").val() },function(data){
		if(data.flg="1"){
			var dataList = data.data;
			for(var i=0;i<dataList.length;i++){
				$("#category").prepend("<li id=" + dataList[i].id + "><a style='cursor:pointer'>" + dataList[i].typeName + "</a></li>")
			}
			$("#category li:eq(0)").addClass("active");
			$("#categoryId").val($("#category li:eq(0)").attr("id"))
			
			 TableInit.refTable('right_table2');
			//标签页的单击事件
			
			$("#category").off("click").on("click","li",function(event){
					$("#category li").removeClass("active")
					$("#categoryId").val($(this).attr("id"));
					 TableInit.refTable('right_table2');
				   $(this).addClass("active")
			})

			
		}
	});
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









