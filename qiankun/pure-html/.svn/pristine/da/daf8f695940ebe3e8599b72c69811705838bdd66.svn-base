var fileTypeId = "ce03df96b1424d848733fd7ad27b88b0";
var workFlowId = "ce03df96b1424d848733fd7ad27b88b0";


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
			httpRequest("get", "/mypage/wmgl/basicSet/goods/edit", datas, function(data) {
				if(data.data.amountLimit=='1'){
					$("#inventory").show();
				}else{
					$("#inventory").hide();
				}
				if(data.data.buyLimit=='1'){
					$("#inventory1").show()
				}
				DisplayData.playData({data: data});
				if(data.data.isUse=='1'){
					$("#isUse").text("是")
				}else{
					$("#isUse").text("否")
				}
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









