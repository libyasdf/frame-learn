$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){

		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/shyk/drhd/edit",datas,function (data){
			DisplayData.playData({data:data});
		});

	}
	iniFileUpload();
	//iniFileUpload();
})

function commitForm(flag) {
//	$("#subflag").val(flag);
	//console.log($("#subflag").val()+"a"+flag)
	var data = saveForm();

	if (data) {

		layer.msg("保存成功！", {
			icon : 1
		});
//		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
		// 刷新列表
		parent.TableInit.refTable('right_table');

	}
}


/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if(bootstrapValidator.isValid()){

		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/shyk/drhd/saveDrhd",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				if (json.flag == '1') {
					res = json.flag;
					$("#id").val(json.data.id);
				//	$("#subflag").val(json.data.subflag);
					//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
					iniFileUpload();
					MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files"});
				}
			},
			error:function(){
			}
		});
		//保存临时意见
		/*var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(),tempIdea)*/;
	}
	console.log(res);
	return res;
}



/**
 * 初始化文件上传
 */
function iniFileUpload(){
	//初始化文件上传
	MyFileUpload.init({
		viewParams: {"tableId":$("#tableId").val(),"tableName":$("#tableName").val()},
		editOrView:$("#opertation").val(),
		maxFileSize:5*1024*1024 //5M
	});


}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val||val=="undefined"){
		val = "";
	}
	return val;
}