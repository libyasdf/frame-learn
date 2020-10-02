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
			httpRequest("get", "/mypage/wmgl/takeout/takeoutInfo/edit", datas, function(data) {
				if(data.data.status=='1'){
					$("#fbButton").hide()
					//$("#zuhe").hide()
				}
				$("#title").text(data.data.title)
				DisplayData.playData({data: data});
			});
			//加载外卖的详情信息
			//loadCategory()
			
			
	 }else{
		 //获取外卖标题
		 $.post("/mypage/wmgl/takeout/takeoutInfo/getTitle", function(data){
			 if(data.flg=='1'){
				 var title = data.title;
				 $("#title").text(title)
				  $("#title1").val(title)
			 }
		 } );
		 //获取本年的外卖注意事项
		 $.post("/mypage/wmgl/config/getAttendItem", function(data){
			 console.info(data)
			 if(data.flag=='1'){
				 $("#attentionItem").val(data.data)
				
			 }
		 } );
		 
	 }
	loadCategory1();
	
})
//设置商品成功后的回调函数
function putBackData(data){
    	 if(data.flg=="1"){
    		 layer.closeAll(); 
    		 layer.msg("设置成功!",{icon:1})
    		 loadCategory()
    		 //TableInit.refTable('right_table');
    	 }
 }
//加载外卖的分类标签页
var categoryLength=0;
function loadCategory(){
	$.post("/mypage/wmgl/takeout/takeoutDetil/getCategoryByInfoId", { infoId:$("#id").val() },function(data){
		$("#category").empty();
		if(data.flg="1"){
			var dataList = data.data;
			categoryLength=dataList.length;
			if(categoryLength==0){
				$("#classId").val("");
			}
			for(var i=0;i<dataList.length;i++){
				$("#category").append("<li id=" + dataList[i].id + "><a style='cursor:pointer'>" + dataList[i].typeName + "</a></li>")
			}
			if($("#category li").length>0){
				$("#category li:eq(0)").addClass("active");
				$("#classId").val($("#category li:eq(0)").attr("id"))
				 TableInit.refTable('right_table');
			}
			
			//标签页的单击事件
			
			$("#category").off("click").on("click","li",function(event){
					$("#category li").removeClass("active")
					$("#classId").val($(this).attr("id"));
					 TableInit.refTable('right_table');
				   $(this).addClass("active")
			})

			
		}
	});
}

/**
 * 提交表单
 * @returns
 */
function comitForm(flg){
	var start = $("#deadlineTime").val().substring(0,13);
	var end = $("#takeFoodTime").val().substring(0,13)
	if(start>end){
		layer.msg("取餐时间不能大于最晚下单时间！", {icon: 0});
		return;
	}
	var msg=""
	if($("#status").val()!='1'){
		//草稿
		$("#status").val(flg)
	}
	var data = saveForm();
	if(data){
		if(flg=='0'){
			msg="保存成功！"
		}else{
			msg="发布成功！"
				$("#fbButton").hide()
				//$("#zuhe").hide()
		}
		
		layer.msg(msg, {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存
 */
function saveForm(){
	var info = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	
	 bootstrapValidator.validate();
	 if(bootstrapValidator.isValid()){
		 if($("#status").val()=='1' && !$("#classId").val()){//发布，没有列表数据
			 layer.msg("请先设置商品信息！", {icon: 0});
			 $("#fbButton").show()
			 return;
		 }else{
			 $.ajax({
					type: "POST",
					url:"/mypage/wmgl/takeout/takeoutInfo/saveFrom",
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
	 }
	return info;
}

//加载套餐的分类标签页
var flg = '0';
function loadCategory1(){
	$.post("/mypage/wmgl/takeout/setmeal/getCategoryByInfoId", { infoId:$("#id").val() },function(data){
		$("#category1").empty();
		if(data.flg="1"){
			var dataList = data.data;
			for(var i=0;i<dataList.length;i++){
				$("#category1").append("<li id=" + dataList[i].id + "><a style='cursor:pointer'>" + dataList[i].typeName + "</a></li>")
			}
			if($("#category1 li").length>0)
				if($("#category1 li[id='"+$("#classId").val()+"']").length>0){
					$("#category1 li[id='"+$("#classId").val()+"']").addClass("active");
					$("#classId1").val($("#category1 li[id='"+$("#classId").val()+"']").attr("id"));
				}else{
					$("#category1 li:eq(0)").addClass("active");
					$("#classId1").val($("#category1 li:eq(0)").attr("id"));
				}
				if(flg=='0'){
					flg = '1';
					$("#category1 li:eq(0)").addClass("active");
					$("#classId1").val($("#category1 li:eq(0)").attr("id"))
					getInitTable2();
				}else{
					TableInit.refTable('right_table2');
				}
			}else{
				getInitTable2();
			}
			
			//标签页的单击事件
			$("#category1").off("click").on("click","li",function(event){
					$("#category1 li").removeClass("active")
					$("#classId1").val($(this).attr("id"));
					 TableInit.refTable('right_table2');
				   $(this).addClass("active")
			})

			
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









