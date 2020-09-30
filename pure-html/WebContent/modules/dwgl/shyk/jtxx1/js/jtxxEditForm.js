$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	if(regVlaue(theRequest.isAdd=="add")){
		$("#partyOrganizationId").val(theRequest.orgId);
		$("#partyOrganizationName").val(theRequest.orgName);
	}
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){

		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/shyk/jtxx/edit",datas,function (data){
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
		/*if($("#meetingTime").val()==""){
			alert("时间不能为空");
		}else if($("#meetingPlace").val()==""){
			alert("地点不能为空");
		}else if($("#compere").val()==""){
			alert("主持人不能为空");
		}else if($("#noteTaker").val()==""){
			alert("记录人不能为空");
		}else if($("#numberOfPeople").val()==""){
			alert("应到不能为空");
		}else if($("#actualNumber").val()==""){
			alert("实到不能为空");
		}else if($("#primaryCoverage").val()==""){
			alert("主要内容不能为空");
		}else if($("#attendants").val()==""){
			alert("参加人员不能为空");
		}else if($("#meetingSituation").val()==""){
			alert("会议情况不能为空");
		}*/
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/shyk/jtxx/saveJtxx",
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
	var tableName ="";
	var tableId="";
	if(regVlaue($("#tableName").val())==""){
		tableName="DDJS_SHYK_COLLECTIVE";
		tableId=$("#id").val();
	}else{
		tableName =$("#tableName").val();
		tableId=$("#tableId").val();
	}
	//初始化文件上传
	MyFileUpload.init({
		viewParams: {"tableId":tableId,"tableName":tableName},
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