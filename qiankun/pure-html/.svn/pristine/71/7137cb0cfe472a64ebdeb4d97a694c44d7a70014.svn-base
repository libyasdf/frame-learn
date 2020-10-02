var fileTypeId = "fd8130ce3f604957b3eda7115e104384";
var workFlowId = "fd8130ce3f604957b3eda7115e104384";

$(function() {
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {
		// 修改
		// 表单数据渲染
		var datas = {
			"id" : $("#id").val(),
			"resId" : $("#resId").val()
		};
		if ($("#opertation").val() == "VIEW") { // 只读页渲染
			httpRequest("get", "/zhbg/kqgl/comeLateLeaveEarly/edit", datas, function(data) {
				if(data.data.state=='0'){
					$("#dateLable").text("迟到日期 :");
					$("#reason").text("迟到原因 :")
					data.data.state='迟到';
				}
				if(data.data.state=='1'){
					$("#dateLable").text("早退日期 :")
					$("#reason").text("早退原因 :")
					data.data.state='早退';
				}
				DisplayData.playData({
					data : data
				});
			});
		} else {
			httpRequest("get", "/zhbg/kqgl/comeLateLeaveEarly/edit", datas, function(data) {
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
				DisplayData.playWorkFlow("/flowService/getFlowData", datas, $("#opertation").val(), callback);
				if($('#creUserId').val()==getcookie("userid")){
					$("#approval").hide();
					$("#noApproval").hide();
					$("#ideaArea").hide();
				}
				//待办进入 判断
				if($("#state").val()!=''){
					var stateValue=$("#state").val();
					if(stateValue=='0'){
						$("#dateLable").text("迟到日期 :");
						$("#reason").text("迟到原因 :")
					}else if(stateValue=='1'){
						$("#dateLable").text("早退日期 :")
						$("#reason").text("早退原因 :")
					}else{
						$("#dateLable").text("日期 :")
						$("#reason").text("原因 :")
					}
				}
			} else {// oper='VIEW'
				var datas = {
					type : "1",
					oper : $("#opertation").val(),
					workitemid : $("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData", datas, $("#opertation").val());
			}
			// 初始化意见域，回显临时意见
			initIdeaArea($("#workitemid").val());
			// 获取正式意见，渲染页面
			getIdeas();
		} else {
			//workitemid为空时，表示编辑草稿状态，加载启动节点按钮及启动节点提示信息
            getStartWf();
		}
	} else {
		getStartWf();
		// 起草
		$("#creUserName").val(getcookie("usernm"))
		$("#creUserId").val(getcookie("userid"))
		$("#applicantUnitName").val(getcookie("deptname"));
		var curDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#applicationTime").val(curDate);
		$("#approval").hide();
		$("#noApproval").hide();
	}
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
 * 提交表单
 */
function commitForm() {
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
	bootstrapValidator.updateStatus("state","notEmpty", null).validateField("state"); 
	//bootstrapValidator.updateStatus("cdztUserName","notEmpty", null).validateField("cdztUserName"); 
	// 手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		if (!$("#id").val()) {
			$("#subflag").val(Config.startflag);
		}
		$.ajax({
			type : "POST",
			url : "/zhbg/kqgl/comeLateLeaveEarly/saveForm",
			data : $("#form").serialize(),
			async : false,
			success : function(json) {
				if (json) {
					res = json.id;
					$("#id").val(json.id);
					$("#subflag").val(json.subflag);

				}
			}
		});
		// 保存临时意见
		var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(), tempIdea);
	}
	return res;
}

/**
 * 提交流程
 */
function comitFlow() {
	if(!$("#cdztTitle").val()){
		var creUserName=$("#creUserName").val();
		var curDate=getCurrentDate("yyyy-MM-dd  HH:mm:ss");
		var title=creUserName+"于"+curDate+"发出迟到早退申请";
		$("#cdztTitle").val(title);
	}
	var plan = saveForm();
	if (plan != "") {
		var oper = "";
		if ($("#workitemid").val() == "") {
			oper = "NEW";
		} else {
			oper = "EDIT";
		}
		// 获取意见
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
		//获取起草人的角色信息
		if($("#roleNo").val()==''){
			//请求后台 获取起草人的角色信息
		    $.ajaxSettings.async = false;
			$.post("/zhbg/kqgl/comeLateLeaveEarly/getRoleNoByUserId", {
				userId:$("#creUserId").val()
			}, function(data) {
				$("#roleNo").val(data);
			});
		    $.ajaxSettings.async = true;
		}
		//判断是不是正处长起草
		var roleNo=$("#roleNo").val();
		var zkzCre="0";
		if(roleNo.indexOf("D001")!=-1){
			zkzCre="1";
		}
		submitToFlow(oper, this,$("#cdztTitle").val(), idea, $("#id").val(),"attr", "attr1", $("#workitemid").val(), $("#workflowid").val(),"zkzCre="+zkzCre);
	}
}

/**
 * 工作流回调该方法，用于改变业务数据状态
 * 
 * @param subflag
 *            状态位
 * @returns
 */
function updateBusiData(subflag) {
	$.ajax({
		type : "POST",
		url : "/zhbg/kqgl/comeLateLeaveEarly/updateFlag",
		data : {
			id : $("#id").val(),
			subflag : subflag
		},
		async : false,
		success : function(data) {
		}
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
