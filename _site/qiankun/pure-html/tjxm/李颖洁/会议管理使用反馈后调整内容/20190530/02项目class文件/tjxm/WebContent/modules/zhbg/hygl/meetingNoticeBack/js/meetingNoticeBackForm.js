
$(function() {
	
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
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
			httpRequest("get", "/zhbg/hygl/meetingNoticeBack/edit", datas, function(data) {
				$("#creUserId").val(data.data.creUserId);
				$("#attendDept").val(data.data.attendDept);
				$("#feedbackType1").val(data.data.feedbackType);
				$('input').attr('readOnly','true');
				$('input').attr('disabled','disabled');
				$('textarea').attr('disabled','disabled');
				$('textarea').attr('readOnly','true');
				$('#sendFLow').css("display","none");
				$('#save').css("display","none");
				//判断反馈类型
				if(data.data.feedbackType=='1'){
					$('#renshu').css("display", "block")
					data.data.feedbackType = '参会人数';
				}else{
					data.data.feedbackType = '参会人名';
					$('#selectRen').css("display", "block")
					$('#renshu2').css("display", "block")
					/*$('#renming').css("display", "block")
					$('#chuzhangrenming').css("display", "block")
					$('#fenguanrenming').css("display", "block")
					$('#realNum').css("display", "block")
					$('#shouldNum').css("display", "block")*/
				}
				//无需反馈  不显示反馈详情和最晚反馈时间
				if(data.data.state=='2'){
					$("#lastFkTime").hide();
					$("#fanKuiDisplay").hide();
					$("#backTime2").hide();
					data.data.state = "否";
				}else if(data.data.state=='1'){
					data.data.state = "是";
					$("#fanKuiDisplay").show();
				}
				//未反馈 不显示反馈详情
				if(data.data.state=='0'){
					$("#fanKuiDisplay").hide();
					data.data.state = "是";
				}
				DisplayData.playData({
					data : data
				});
				showMeetingRoom(data.data.meetingApplyId);
			});
		} else {
			$("#opertation").val("VIEW");
			httpRequest("get", "/zhbg/hygl/meetingNoticeBack/edit", datas, function(data) {
				$("#creUserId").val(data.data.creUserId);
				$("#attendDept").val(data.data.attendDept);
				$("#feedbackType1").val(data.data.feedbackType);
				$("#state").val(data.data.state);
				if(data.data.state=='2'){
					$("#backTime2").hide();
					data.data.state = "否";
					$('#sendFLow').css("display","none");
					$('#save').css("display","none");
				}else if(data.data.state=='1'||data.data.state=='0'){
					if(data.data.state=='1'){
						$('#sendFLow').css("display","none");
						$('#save').css("display","none");
						$('input').attr('readOnly','true');
						$('textarea').attr('readOnly','true');
					}
					$('#fanKuiDisplay').css("display", "block")
					if($('#feedbackType1').val() == '1'){
						$('#renshu').css("display", "block")
					}else{
						$('#renshu2').css("display", "block")
						$('#selectRen').css("display", "block")
					}
					data.data.state = "是";
					$("#fanKuiDisplay").show();
				}
				if(data.data.feedbackType=='0'){
					data.data.feedbackType = '参会人名';
				}else if(data.data.feedbackType=='1'){
					data.data.feedbackType = '参会人数';
				}
				DisplayData.playData({
					data : data
				});
				showMeetingRoom(data.data.meetingApplyId);
				console.log($("#state").val())
			});
		}

		// 流程相关（按钮、控件等）的渲染
/*		if ($("#workitemid").val() != "") {
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
		}*/
		
	} 
	iniFileUpload();
})

/**
 * 获取正式意见、回显
 */
/*function getIdeas() {
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
}*/

/**
 * 调用工作流配置的按钮
 * 
 * @returns
 */
/*function getStartButton() {
	var oper = "NEW";
	var startdatas = {
		workflowid : $("#workflowid").val(),
		filetypeid : $("#filetypeid").val(),
		oper : oper
	};
	DisplayData.playWorkFlow("/workflow/getStartWfButton", startdatas, oper);
}*/
var $form;
function iframeCallback(form) {
	$form = $(form);
}
/**
 * 保存
 */
function comitForm() {
	$("#state").val("0");
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
 * 表单反馈
 */
function fankuiForm() {
	$("#state").val("1");
	var data = saveForm();
	if (data) {
		layer.msg("反馈成功！", {
			icon : 1
		});
		// 刷新列表
		$('#save').css("display", "none")//将保存设置为隐藏 
		$('#sendFLow').css("display", "none")//将反馈设置为隐藏 
		parent.refreshPage("daiban");//刷新待办列表
		parent.TableInit.refTable('right_table');
        parent.getMsgCount();//刷新消息数量
        parent.getMsgList();//刷新消息列表
      //反馈后发消息
        $("#creUserId").val()
    	MessageUtil.sendMsg({
			subject:"会议通知反馈",		//消息主题
			content:$("#attendDept").val()+":已反馈【"+$('span[name="meetingName"]').text()+"】会议的"+$('span[name="feedbackType"]').text(),		//消息内容
			accepterId:$("#creUserId").val()	//接收人ID
		});
	}
}

/**
 * 保存数据
 */
function saveForm() {
	
	var res = "";
	var attendChuName = $("#attendPersonChuName").val();
	var attendLeaveName = $("#attendPersonLeaveName").val();
	var attendName = $("#attendPersonName").val();
	var notAttendName = $("#notAttendPersonName").val();
	var notAttendChuName = $("#notAttendPersonChuName").val();
	var notAttendLeaveName = $("#notAttendPersonLeaveName").val();
	if(!attendChuName && !attendLeaveName && !attendName&& !notAttendName
			&& !notAttendChuName&& !notAttendLeaveName){
		layer.msg("至少添加一项参会人员！", {
			icon : 2
		})
		return false;
	}
	//手动触发验证
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		var num1 = $("#shouldAttendNum").val();
		var num2 = $("#realAttendNum").val();
		var f=false;
		if($("#feedbackType1").val()=='0'){
			if(parseInt(num1) < parseInt(num2)){
				layer.msg("应参会人数必须大于或小于实参会人数！", {
					icon : 2
				})
				return false;
			}
			
		}
		$.ajax({
			type : "POST",
			url : "/zhbg/hygl/meetingNoticeBack/saveForm",
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
	$("#state").val("1");
	var plan = saveForm();
	if (plan != "") {
		parent.getWorkFlowData("daiban");
		var oper = "";
		if ($("#workitemid").val() == "") {
			oper = "NEW";
		} else {
			oper = "EDIT";
		}
		//获取意见
		var idea = $("#idea").val();
		var IsNotionShow = document.getElementById("ideaArea").style.display;
		if (IsNotionShow == "block") {
			if ("2" == document.getElementById("fillmode").value) {
				if (idea == "" || idea == null) {
					layer.msg("请填写意见", {
						icon : 0
					})
					return false;
				}
			}
		}
		
	}
}

/**
 * 初始化文件上传
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({
		viewParams: {"tableId":$("#noticeId").val(),"tableName":"HYGL_MEETING_NOTICE"},
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