var fileTypeId = "ce03df96b1424d848733fd7ad27b88b0";
var workFlowId = "ce03df96b1424d848733fd7ad27b88b0";


$(function(){
	var theRequest = GetRequest();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#belongId").val(regVlaue(theRequest.categoryId));
    $("#categoryName").text(regVlaue(theRequest.typeName));
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		$("#clear").hide();
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
			httpRequest("get", "/mypage/wmgl/basicSet/goods/edit", datas, function(data) {
				if(data.data.status=='1'){
					$("#fbButton").hide()
				}
				if(data.data.amountLimit=='1'){
					$("#inventory").show()
					
				}
				
				if(data.data.buyLimit=='1'){
					$("#inventory1").show()
					
				}
				DisplayData.playData({data: data});
			});
	 }
	//图片上传
	 iniFileUpload();
})

//初始化图片上传文件
function iniFileUpload() {
    ImgMyFileUpload.init({
        viewParams: {"tableId": $("#id").val(), "tableName": "wmgl_goods"},
        editOrView: $("#opertation").val(),
        //maxFileSize: 150 * 1024,//150K
        domId: "imgfileupload",
        fileListId:"image",
        imageId:"imageId"
    });
}


/**
 * 提交表单
 * @returns
 */
function comitForm(flg){
	
	var msg=""
	if($("#status").val()!='1'){
		//草稿
		$("#status").val(flg)
	}
	if(flg=='0'){
		msg="保存成功！"
	}else{
		msg="发布成功！"
		$("#fbButton").hide()
		
		$("#clear").hide()
		
	}
	var data = saveForm();
	if(data){
		
		layer.msg(msg, {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}
}

//清空
function clear1(){
	 $("input[name=isUse]").each(function(index){
        	if(index==0){
        		$(this).prop("checked", true);
        	}else{
        		$(this).prop("checked", false);
        	}
       })
	$("input[name=amountLimit]").each(function(index){
        	if(index==0){
        		$(this).prop("checked", true);
        	}else{
        		$(this).prop("checked", false);
        	}
     })
     $("#inventory").hide()
     $("#describe").val("")
	$("#mark").val("")
	$("input[type=text]").val("").change();
	 $("#id").val("")
	  $("#status").val("")
	   $("#imageId").val("")
	   $("#image").attr("src","/modules/info/xxfb/content/upload_img.png")
	//$("input:not(.a)").val("").change()
}
/**
 * 保存
 */
function saveForm(){
	var info = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	 bootstrapValidator.validate();
	 if(bootstrapValidator.isValid()){
		 $.ajax({
				type: "POST",
				url:"/mypage/wmgl/basicSet/goods/saveFrom",
				data:$("#form").serialize(),
				async: false,
				success:function(json){
					if (json) {
						info = json.id;
						$("#id").val(json.id);
						iniFileUpload()
					}
				}
		});
	 }
	return info;
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









