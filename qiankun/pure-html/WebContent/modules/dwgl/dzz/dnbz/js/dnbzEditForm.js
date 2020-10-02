$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	if(regVlaue(theRequest.addOrUpdate)=="add"){
		$("#partyOrganizationId").val(theRequest.orgId);
		$("#partyOrganizationName").val(theRequest.orgName);
	}
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dzz/dnbz/edit",datas,function (data){
			DisplayData.playData({data:data});
		});

	}
})

function commitForm() {
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
	var myDate = new Date();
	/*var time = myDate.toLocaleDateString().split('/').join('-');
	$("#reportingTime").val(time);*/
	if(bootstrapValidator.isValid()){
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/dzz/dnbz/saveDnbz",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				if (json.flag == '1') {
					res = json.flag;
					$("#id").val(json.data.id);
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