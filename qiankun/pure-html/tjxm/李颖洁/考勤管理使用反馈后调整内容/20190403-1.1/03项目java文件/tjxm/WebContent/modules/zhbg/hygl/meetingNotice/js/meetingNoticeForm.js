var fileTypeId = "1806261545148080192168901340019";
var workFlowId = "1806261545148080192168901340019";

$(function() {
	
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#resId").val(theRequest.resId);
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {
		// 修改
		// 表单数据渲染
		var datas = {
			"id" : $("#id").val()
		};
		if ($("#opertation").val() == "VIEW") { // 只读页渲染
			httpRequest("get", "/zhbg/hygl/notice/edit", datas, function(data) {
				if(data.data.isFankui=='1'){
					data.data.isFankui="是";
					if(data.data.fankuiType=='1'){
						data.data.fankuiType="参会人数";
					}else{
						data.data.fankuiType="参会人名";
					}
				}
				if(data.data.isFankui=='0'){
					data.data.isFankui="否";
					$('#lastBackTime').attr("style", "display:none;");
					$('#hasFankui').attr("style", "display:none;");
					
				}
				showMeetingRoom(data.data.applyId);
				/*if(data.data.startTime=='0'){
					data.data.startDate = data.data.startDate+' 上午';
				}else{
					data.data.startDate = data.data.startDate+' 下午';
				}
				if(data.data.endTime=='0'){
					data.data.endDate = data.data.endDate+' 上午';
				}else{
					data.data.endDate = data.data.endDate+' 下午';
				}*/
				DisplayData.playData({
					data : data
				});
			});
		} else {
			httpRequest("get", "/zhbg/hygl/notice/edit", datas, function(data) {
				//根据反馈类别 判断是否显示反馈类型和最晚反馈时间 0:否  1：反馈 
				/*if(data.data.isFeedback=='1'){
					$("#fanKui").show();
					$("#fanKuiTime").show();
				}else{
					$("#fanKui").hide();
					$("#fanKuiTime").hide();
				}*/
				//回显会议室安排列表
				$("#tablePlan").show();
				$("#time").show();
				showMeetingRoom(data.data.applyId);
				DisplayData.playData({
					data : data
				});
			});
		}

		// 流程相关（按钮、控件等）的渲染
		if ($("#workitemid").val() != "") {
			if ($("#opertation").val() == "EDIT") {
				var datas = {
					workflowid : $("#workflowid").val(),
					filetypeid : $("#filetypeid").val(),
					workitemid : $("#workitemid").val(),
					pkValue : $("#id").val(),
					userid : getcookie("userid"),
					deptid : getcookie("deptid"),
					oper : $("#opertation").val()
				};
				// 流转中不可修给表单--开始
				// $('form').find('input,textarea,select').not('#idea').prop('readonly',true);
				// 流转中不可修给表单--结束
				DisplayData.playWorkFlow("/flowService/getFlowData", datas, $(
						"#opertation").val(), callback);
			} else {// oper='VIEW'
				var datas = {
					type : "1",
					oper : $("#opertation").val(),
					workitemid : $("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData", datas, $(
						"#opertation").val());
			}
			// 初始化意见域，回显临时意见
			initIdeaArea($("#workitemid").val());
			// 获取正式意见，渲染页面
			getIdeas();
		} else {
			// getStartButton();
		}
		
	} 
	iniFileUpload();
})

/**
 * 获取正式意见、回显
 */
function getIdeas() {
	// 返回数据格式参照：/mock/formalIdeas.json
	var res = getFormalIdeas($("#id").val(), fileTypeId);
	if (res.length > 0) {
		for (var i = 0; i < res.length; i++) {
			if (res[i].ideaList.length > 0) {
				for (var j = 0; j < res[i].ideaList.length; j++) {
					// var time = new Date(jsonArr[i].ideaList[j].ideatime);
					var textarea = document.getElementsByName(res[i].name);
					if (res[i].ideaList[j].userid.trim() != getcookie("userid")
							.trim()) {
						$(textarea).text(res[i].ideaList[j].idea);
					}
				}
			}
		}
	}
}

/**
 * 调用工作流配置的按钮
 * 
 * @returns
 */
function getStartButton() {
	console.log("2222");
	var oper = "NEW";
	var startdatas = {
		workflowid : $("#workflowid").val(),
		filetypeid : $("#filetypeid").val(),
		oper : oper
	};
	DisplayData.playWorkFlow("/workflow/getStartWfButton", startdatas, oper);
}
var $form;
function iframeCallback(form) {
	$form = $(form);
}
/**
 * 提交表单
 */
function comitForm() {
	$("#status").val("0");
	var data = saveForm();
	if (data) {
		layer.msg("保存成功！", {
			icon : 1
		});
		// 刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存数据
 */
function saveForm() {
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	$('.summernote', $("#form")).each(function() {
        var $this = $(this);
        if (!$this.summernote('isEmpty')) {
            var editor = "<input type='hidden' name='" + $this.attr("name") + "' value='" + $this.summernote('code') + "' />";
            $("#form").append(editor);
        } else {
        	layer.msg("请填写通知内容", {
				icon : 0
			})
            return false;
        }

    });
	if (bootstrapValidator.isValid()) {
		if($("#attendDeptJu").val()=="" && $("#attendDept").val()==""){
			layer.msg("请至少选择一项参会单位", {
				icon : 0
			})
			return false;
		}
		$.ajax({
			type : "POST",
			url : "/zhbg/hygl/notice/saveForm",
			data : $("#form").serialize(),
			async : false,
			success : function(json) {
				if (json) {
					res = json.id;
					$("#id").val(json.id);
					$("#subflag").val(json.subflag);
					//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
    				iniFileUpload();
    			    MyFileUpload.saveDocIdToAffix({docId:res,fileListId: "files"});
				}
			}
		});
		//保存临时意见
		var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(), tempIdea);
	}
	return res;
}

/**
 * 发布通知
 */
function publishForm() {
    var index11;
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		if($("#attendDeptJu").val()=="" && $("#attendDept").val()==""){
			layer.msg("请至少选择一项参会单位", {
				icon : 0
			})
			return false;
		}
	//Loading.open();
	$("#status").val("1");
	if($('#attendDeptJu').val()){
		var deptNames="";
		var deptIds="";
       if($('#attendDept').val()){
    	 deptNames= ($('#attendDept').val()+","+$('#attendDeptJu').val()).split(",");	
		}else{
		 deptNames=$('#attendDeptJu').val().split(",");	
		}
		if($('#attendDeptId').val()){
		   deptIds = ($('#attendDeptId').val()+","+$('#attendDeptJuId').val()).split(",");
		}else{
			deptIds = $('#attendDeptJuId').val().split(",");
		}
	}else{
		var deptNames = ($('#attendDept').val()).split(",");
  		var deptIds = ($('#attendDeptId').val()).split(",");
	}
	  		//var deptNames = ($('#attendDept').val()+","+$('#attendDeptJu').val()).split(",");
	  		//var deptIds = ($('#attendDeptId').val()+","+$('#attendDeptJuId').val()).split(",");
	  		//alert(JSON.stringify(deptIds));
	  		$.ajax({
	  			url:"/zhbg/hygl/notice/hasConfidentiUser",
	  			data:{"deptIds":JSON.stringify(deptIds),"deptNames":JSON.stringify(deptNames)},
				dataType:"json",
	  			async:true,
	  			beforeSend:function(request){
  				index11 = layer.load(2,{shade: [0.5, '#393D49'],content: '请稍候',
  				success: function(layero){
  				layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
  				} }); 
	  			},
	  			success:function(data1){
	  				//Loading.clear();
	  				if(data1.flag=="1"){
  	  				//layer.confirm('确认发布吗?点击确定按钮后将发布通知，不能再修改，同时会关闭本页面！',{icon:3,title:'发布'},function(){
  	  				var plan = saveForm();
  	  				//alert(plan);
  	  				if (plan) {
  	  				layer.close(index11);
  	  				layer.msg("发布成功！", {
  	  							icon : 1
  	  						});
  	  				$('#save').css("display", "none")//将保存设置为隐藏 
					$('#sendFLow').css("display", "none")//将反馈设置为隐藏 
					//$('#close').css("display", "block")
  	  					// 刷新列表
  	  					parent.TableInit.refTable('right_table');
  	  				}
	  				}else{
	  					layer.close(index11);
	  					var names = data1.deptNames.split(",");
	  					layer.alert('发布失败！'+names+'等单位没有配置处室或部门收发人员角色，请联系系统管理员进行配置后再试！',
	  							{icon:0})
	  							//layer.close(index);
	  				}
	  			}
	  		});
	}
	
}

/**
 * 初始化文件上传HYGL_MEETING_NOTICE
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"HYGL_MEETING_NOTICE"},
		editOrView:$("#opertation").val(),
        maxFileSize:5*1024*1024 //5M
    });

}

/**
 * 空值设置
 * 
 * @param val
 * @returns
 */
function regVlaue(val) {
	if (!val) {
		val = "";
	}
	return val;
}