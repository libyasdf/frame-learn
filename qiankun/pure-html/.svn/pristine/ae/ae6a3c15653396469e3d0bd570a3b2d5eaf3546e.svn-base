var fileTypeId = "f6adebe1cf1e45ec9a35314748622e38";
var workFlowId = "f6adebe1cf1e45ec9a35314748622e38";

$(function() {
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	// 初始化字典项
	/*Dictionary.init({
		position : "leaveType",
		mark : "leaveType",
		type : "1",
		name : "leaveType",
		domType : "select"
	});
	Dictionary.init({
		position : "leaveReason",
		mark : "leaveReason",
		type : "1",
		name : "leaveReason",
		domType : "select"
	});*/
//	Dictionary.init({
//		position : "isLeaveInLieu",
//		mark : "isLeaveInLieu",
//		type : "1",
//		name : "isLeaveInLieu",
//		domType : "radio"
//	});
	//$("input[name=isLeaveInLieu]:eq(1)").attr("checked", 'checked');
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
		var leaveTimeType = "";
		var leaveTypeNum = "";
		var leaveReason = "";
		var isChoose = "";
		if ($("#opertation").val() == "VIEW") { // 只读页渲染
			httpRequest("get", "/zhbg/kqgl/qj/edit", datas, function(data) {
				//leaveTimeType = data.data.leaveTimeType;
				leaveTypeNum = data.data.leaveType;
				leaveReason = data.data.leaveReason;
				isChoose = data.data.isChoose;
				
//				if (leaveTimeType == '0') {
//					data.data.leaveTimeType = "按日期";
//				}
//				if (leaveTimeType == '1') {
//					data.data.leaveTimeType = "按小时";
//				}
				if (isChoose=="1") {
					var jsonObj = Dictionary.getNameAndCode({
						mark : "leaveReason",
						type : "1"
					});
					data.data.leaveReason = jsonObj[leaveReason];
				}else if(data.data.subflag=="0"&&isChoose!="1"){
					data.data.leaveReason = "";
				}
                if(data.data.leaveReason == "其他"){
                    $("#otherReadOnly").show();
                }
				DisplayData.playData({
					data : data
				});
			});
			//changeDivRd(leaveTimeType);
			if (leaveTypeNum != "") {
				var jsonObj = Dictionary.getNameAndCode({
					mark : "leaveType",
					type : "1"
				});
				$("#leaveType").html(jsonObj[leaveTypeNum]);
			}
		} else {
			Dictionary.init({
				position : "leaveType",
				mark : "leaveType",
				type : "1",
				name : "leaveType",
				domType : "select"
			});
			Dictionary.init({
				position : "leaveReason",
				mark : "leaveReason",
				type : "1",
				name : "leaveReason",
				domType : "select"
			});
			//var oper = $("#opertation").val();
			httpRequest("get", "/zhbg/kqgl/qj/edit", datas, function(data) {
				//leaveTimeType = data.data.leaveTimeType;
				//调休情况编辑页面显示
//				if (data.data.isLeaveInLieu == '1') {
//					isLeaDetail(data.data.leaveStartDate,data.data.leaveStartTime, data.data.leaveLongTime);
//				}
				leaveTypeNum = data.data.leaveType;
				leaveReason = data.data.leaveReason;
				isChoose = data.data.isChoose;
				if (data.data.subflag=="1"&&isChoose!="1") {//流程中
					$("#isChoose").val("");
					$("#leaveCause").html('<textarea name="leaveReason" id="leaveReason"class="form-control" rows="3"></textarea>');
				}else if(data.data.subflag=="0"&&isChoose!="1"){
					data.data.leaveReason = "";
				}
                if(data.data.leaveReason == "13"){
                    $("#otherDiv").show();
                }
				DisplayData.playData({
					data : data
				});
			});
			var start = $("#leaveStartDate").val()
			var end = $("#leaveEndDate").val()
			//日期限制开始时间和结束时间
			startDate.config.max = {
		        						year:end.substring(0,4),
		        						month:end.substring(5,7)-1,
		        						date:end.substring(8,10),
		        						hours:0,
		        						minutes:0,
		        						secondes:0
		        				}
			endDate.config.min = {
									year:start.substring(0,4),
									month:start.substring(5,7)-1,
									date:start.substring(8,10),
									hours:0,
									minutes:0,
									secondes:0
								}
			//changediv(oper);
			//$("#applicantUnitId").val(getcookie("deptid"));
			getCreUserRole();
		}
		// 流程相关（按钮、控件等）的渲染
		if ($("#workitemid").val() != "") {
			if ($("#opertation").val() == "EDIT") {
				//进入待办页面  非起草人 不能修改请假时间
				//设置隐藏域的数值
				var leaveStartDate=$('#leaveStartDate').val();
				var startAmOrPm=$('#startAmOrPm').val();
				if(startAmOrPm=='1'){
					startAmOrPm="上午";
				}
				if(startAmOrPm=='0'){
					startAmOrPm="下午";
				}
				var leaveEndDate=$('#leaveEndDate').val();
				var endAmOrPm=$('#endAmOrPm').val();
				if(endAmOrPm=='1'){
					endAmOrPm="上午";
				}
				if(endAmOrPm=='0'){
					endAmOrPm="下午";
				}
				$('#leaveStartDate1').val(leaveStartDate);
				$('#startAmOrPm1').val(startAmOrPm);
				$('#leaveEndDate1').val(leaveEndDate);
				$('#endAmOrPm1').val(endAmOrPm);
				//起草人
				var creUserName=$('#creUserName').val();
				//当前登录人
				var curUserName=getcookie("usernm");
				if(creUserName!=curUserName){
				$('#leaveStartDate').hide();
				$('#leaveStartDate1').show();
				$('#startAmOrPm').hide();
				$('#startAmOrPm1').show();
				$('#leaveEndDate').hide();
				$('#leaveEndDate1').show();
				$('#endAmOrPm').hide();
				$('#endAmOrPm1').show();
				}
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
				//getStartButtonByLeaveLongTime(leaveTimeType);
				//进入待办页面后 修改时长变化 按钮动态控制
				getStartButtonByLeaveLongTime();
			} else {// oper='VIEW'
				var datas = {
					type : "1",
					oper : $("#opertation").val(),
					workitemid : $("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData", datas, $(
						"#opertation").val());
			}
//			// 初始化意见域，回显临时意见
//			initIdeaArea($("#workitemid").val());
//			// 获取正式意见，渲染页面
//			getIdeas();
		} else {
			// getStartButton();
			getStartWf();
			getStartButtonByLeaveLongTime();
		}
	} else {
		Dictionary.init({
			position : "leaveType",
			mark : "leaveType",
			type : "1",
			name : "leaveType",
			domType : "select"
		});
		Dictionary.init({
			position : "leaveReason",
			mark : "leaveReason",
			type : "1",
			name : "leaveReason",
			domType : "select"
		});
		getStartWf();
		// 起草
		$("#creUserName").val(getcookie("usernm"))
		$("#creUserId").val(getcookie("userid"));
		$("#applicantUnitId").val(getcookie("deptid"));
		$("#applicantUnitName").val(getcookie("deptname"));
		$("#creChushiName").val(getcookie("chuShiName"));
		var curDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#applicationTime").val(curDate);
		getCreUserRole();
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
 * 获取起草人的角色信息 用户流程线的控制
 */
function getCreUserRole(){
	var creUserId=$("#creUserId").val();
	var deptId=$("#applicantUnitId").val();
	//查询起草人的角色信息
	$.ajax({
		url:"/zhbg/kqgl/qj/getRoleNoByUserId",
		data:{userId:creUserId,deptId:deptId},
		async:false,
		success:function(res){
			$("#creUserRoleNo").val(res);
			}
		})
}

/**
 * 当小于1天时，科长可以审批。当小于3天时处长可以审批。当大于3天时领导审批
 */
function getStartButtonByLeaveLongTime() {
	if($('#creUserName').val()==getcookie("usernm")){
		$("#approval").hide();
		$("#noApproval").hide();
	}
	var leaveLongTime = $("#leaveLongTime").val();
	var creUserRoleNo="";
	// 获取请假类型
	//if (leaveTimeType == '0') {
		//当前登录人的角色信息
		$.ajax({
					type : "POST",
					url : "/zhbg/kqgl/qj/getRoleInfo",
					data:{
						creUserId:$("#creUserId").val()
				    },
					async : false,
					success : function(data) {
						if(data.flag=='1'){
							$("#ifzkz").val(data.ifzkz);
							$("#iffkz").val(data.iffkz);
							$("#ifzcz").val(data.ifzcz);
							$("#iffcz").val(data.iffcz);
							$("#ifjz").val(data.ifjz);
							var creUserId=$("#creUserId").val();
							//查询起草人的角色信息
							$.ajax({
								url:"/zhbg/kqgl/qj/getRoleNoByUserId",
								data:{userId:creUserId,deptId:$("#applicantUnitId").val()},
								async:false,
								success:function(res){
									creUserRoleNo=res;
									$("#creUserRoleNo").val(res);
									if(creUserRoleNo.indexOf('D005')!=-1){
										//一般涉密人员科
										if(leaveLongTime != '' && parseFloat(leaveLongTime) > 1.0 && (data.ifzkz == '是' || data.iffkz == '是')){
											$("#approval").hide();
											//$("#noApproval").hide();
										}
										if(leaveLongTime != '' && parseFloat(leaveLongTime) > 3.0 && (data.ifzcz == '是' || data.iffcz=='是')){
											$("#approval").hide();
											//$("#noApproval").hide();
										}
									}else if(creUserRoleNo.indexOf('D002')!=-1){
										//副科长起草
										//科长起草--》处长--》科长   自己不能审批自己
										//请假小于1天
										if(leaveLongTime != '' && parseFloat(leaveLongTime) > 1.0 && data.ifzkz == '是'){
											$("#approval").hide();
											//$("#noApproval").hide();
										}
										if (leaveLongTime != ''&&parseFloat(leaveLongTime) > 3.0 && (data.ifzcz == '是' ||data.iffcz=='是')) {
											// 处长审核通过和不通过 按钮显示 科长的不显示
											$("#approval").hide();
											//$("#noApproval").show();
										   }
										
									}else if(creUserRoleNo.indexOf('D001')!=-1){
										//正科长起草
										if (leaveLongTime != ''&&parseFloat(leaveLongTime) > 3.0 && (data.ifzcz == '是' || data.iffcz == '是')) {
											// 处长审核通过和不通过 按钮显示 科长的不显示
											$("#approval").hide();
											//$("#noApproval").show();
										   }
										
									}else if(creUserRoleNo.indexOf('C001')!=-1 ){
										//正处长起草发送给部门领导
//										if($("#workitemid").val() != "" && data.ifkz == '是'){
//											$("#approval").hide();
//											$("#noApproval").hide();
//										}else{
//										}
									}else if(creUserRoleNo.indexOf('C002')!=-1){
										//副处长起草 小于3天正处长审批  大于 3部门领导审批
										if (leaveLongTime != ''&&parseFloat(leaveLongTime) > 3.0 && data.ifzcz == '是') {
											$("#approval").hide();
											//$("#noApproval").show();
										   }
									}
									if(creUserRoleNo.indexOf('C006')!=-1){
										//一般涉密人员处 请假
										if (leaveLongTime != ''&& parseFloat(leaveLongTime) > 3.0 && (data.ifzcz == '是' || data.iffcz == '是')) {
											// 处长审核通过和不通过 按钮显示 科长的不显示
											$("#approval").hide();
											//$("#noApproval").show();
										    }
									}
								},
								error:function(){
									layer.msg("获取起草人角色信息异常！", {icon: 2});
								}
								
							})
						}
						
					},
					error:function(){
						layer.msg("获取角色信息异常！", {icon: 2});
					}
				});
	//}
	//按时间请假
//	if (leaveTimeType == '1') {
//		//起草人是科员  科长审批
//		if(creUserRoleNo.indexOf('D005')>0){
//			if(leaveLongTime != '' && $("#ifkz").val() == '是'){
//				//$("#approval").show();
//				//$("#noApproval").show();
//			//	$("#sendFLow").hide();
//			}
//		}
//		//起草人是处员 处长审批
//		if(creUserRoleNo.indexOf('C006')>0){
//			if(leaveLongTime != '' && $("#ifcz").val() == '是'){
//				//$("#approval").show();
//			//	$("#noApproval").show();
//				//$("#sendFLow").hide();
//			}
//		}
//	}
}
//待办页面进入请假修改时间  按钮展示
function btnChangeByLeaveLongTime(leaveLongTime){
	var zkzRoleNo=$("#ifzkz").val();
	var fkzRoleNo=$("#iffkz").val();
	var zczRoleNo=$("#ifzcz").val();
	var fczRoleNo=$("#iffcz").val();
	var jzRoleNo=$("#ifjz").val();
	var creUserRoleNo=$("#creUserRoleNo").val();
	if(leaveLongTime!='' && $("#workitemid").val()!=''){
		//请假时间小于1天  当前登录人是科长角色  并且起草人不是科长且不是副科长起草
		//科员起草
		if(creUserRoleNo.indexOf('D005')!=-1){
			if(parseFloat(leaveLongTime)<=1.0 && (zkzRoleNo == '是' || fkzRoleNo == '是')){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>1.0 && (zkzRoleNo == '是' || fkzRoleNo == '是')){
				$("#approval").hide();
				//$("#noApproval").hide();
			}
			if(parseFloat(leaveLongTime)<=3.0 && (zczRoleNo == '是' || fczRoleNo == '是')){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>3.0 && (zczRoleNo == '是' ||fczRoleNo == '是')){
				$("#approval").hide();
				//$("#noApproval").hide();
			}
		}
		//副科长起草
		if(creUserRoleNo.indexOf('D002')!=-1 ){
			if(fkzRoleNo == '是'){
				$("#approval").hide();
				$("#noApproval").hide(); 
			}
			if(parseFloat(leaveLongTime)<=1.0 && zkzRoleNo == '是'){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>1.0 && zkzRoleNo == '是'){
				$("#approval").hide();
				//$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)<=3.0 && (zczRoleNo == '是' || fczRoleNo == '是')){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>3.0 && (zczRoleNo == '是' || fczRoleNo == '是')){
				$("#approval").hide();
				//$("#noApproval").hide();
			}
		}
		//正科长起草
		if(creUserRoleNo.indexOf('D001')!=-1){
			if(zkzRoleNo == '是'){
				$("#approval").hide();
				$("#noApproval").hide(); 
			}
			if(parseFloat(leaveLongTime)<=3.0 && (zczRoleNo == '是' || fczRoleNo == '是')){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>3.0 && (zczRoleNo == '是' || fczRoleNo == '是')){
				$("#approval").hide();
				//$("#noApproval").hide();
			}
		}
		//处员起草
		if(creUserRoleNo.indexOf('C006')!=-1){
			if(parseFloat(leaveLongTime)<=3.0 && (zczRoleNo == '是' || fczRoleNo == '是')){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>3.0 && (zczRoleNo == '是' ||fczRoleNo == '是')){
				$("#approval").hide();
				//$("#noApproval").hide();
			}
		}
		//副处长起草
		if(creUserRoleNo.indexOf('C002')!=-1){
			if(fczRoleNo == '是'){
				$("#approval").hide();
				$("#noApproval").hide();
			}
			if(parseFloat(leaveLongTime)<=3.0 && zczRoleNo == '是'){
				$("#approval").show();
				$("#noApproval").show();
			}
			if(parseFloat(leaveLongTime)>3.0 && zczRoleNo == '是'){
				$("#approval").hide();
				//$("#noApproval").hide();
			}
		}
		//正处长起草
		if(creUserRoleNo.indexOf('C001')!=-1){
			if(zczRoleNo == '是'){
				$("#approval").hide();
				$("#noApproval").hide();
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
	// 手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		if (!$("#id").val()) {
			$("#subflag").val(Config.startflag);
		}
		$.ajax({
			type : "POST",
			url : "/zhbg/kqgl/qj/saveForm",
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
	if(!$("#leaveTitle").val()){
		var creUserName=$("#creUserName").val();
		var creChushiName=$("#creChushiName").val();
		var curDate=getCurrentDate("yyyy年MM月dd日");
		var title=curDate+creUserName+creChushiName+"请假申请";
		//var title=creUserName+"于"+curDate+"发出请假申请";
		$("#leaveTitle").val(title);
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
		// 人 于 时间 请假 几天     $("#creUserName").val()+"于"+$("#applicationTime").val()+"请假"+$("#leaveLongTime").val()+"天"
		var leaLongTime=$("#leaveLongTime").val();
		//判断起草人信息 用户线的控制
		var creUserRole=$("#creUserRoleNo").val();
		var zkzCre="0";
		var zczCre="0";
		var fkzCre="0";
		if(creUserRole.indexOf("D001")!=-1){
			zkzCre="1";
		}
		if(creUserRole.indexOf("C001")!=-1){
			zczCre="1";
		}
		if(creUserRole.indexOf("D002")!=-1){
			fkzCre="1";
		}
		submitToFlow(oper, this,$("#leaveTitle").val(), idea, $("#id").val(),
				"attr", "attr1", $("#workitemid").val(), $("#workflowid").val(),"leaveLongTime="+leaLongTime+",zkzCre="+zkzCre+",zczCre="+zczCre+",fkzCre="+fkzCre);
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
		url : "/zhbg/kqgl/qj/updateFlag",
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
function changeDivRd(val) {
	if (val == "1") {
		$('#qjEndDateRd').attr("style", "display:none;");
		$('#sedTimeRd').attr("style", "display:block;");
	} else {
		$('#qjEndDateRd').attr("style", "display:block;");
		$('#sedTimeRd').attr("style", "display:none;");
	}
}
function changediv(oper) {
	// 判断按钮的选中状态
	if ($('input:radio[name="leaveTimeType"]:checked').val() == '0') {
		$('#sedTime').attr("style", "display:none;");
		$('#qjStartDate').attr("style", "display:block;");
		$('#qjEndDate').attr("style", "display:block;");
		$('#qjLongTypeTxt').html("请假时长（d）：");
		if (oper == 'NEW') {
			$('input:radio[name="isLeaveInLieu"]').eq(1).prop("checked",
					'checked');
			// $("input[name=isLeaveInLieu]:eq(1)").attr("checked",'checked');
			$('#leaveLongTime').val("");
			$('#leaveStartTime').val("");
			$('#leaveEndTime').val("");
			$('#recuperateId').empty();
			//合法性重新校验
			$("#form").data('bootstrapValidator').updateStatus("leaveStartDate","notEmpty", null).validateField("leaveStartDate");
			$("#form").data('bootstrapValidator').updateStatus("leaveEndDate","notEmpty", null).validateField("leaveEndDate");
			$("#form").data('bootstrapValidator').updateStatus("startAmOrPm","notEmpty", null).validateField("startAmOrPm");
			$("#form").data('bootstrapValidator').updateStatus("endAmOrPm","notEmpty", null).validateField("endAmOrPm");
			$("#form").data('bootstrapValidator').updateStatus("leaveLongTime","notEmpty", null).validateField("leaveLongTime");
		}
	} else {
		$('#qjStartDate').attr("style", "display:none;");
		$('#qjEndDate').attr("style", "display:none;");
		$('#sedTime').attr("style", "display:block;");
		$('#qjLongTypeTxt').html("请假时长（h）：");
		if (oper == 'NEW') {
			$('input:radio[name="isLeaveInLieu"]').eq(1).prop("checked",
					'checked');
			$('#leaveLongTime').val("");
			$('#leaveStartDate').val("");
			$('#startAmOrPm').val("");
			$('#leaveEndDate').val("");
			$('#endAmOrPm').val("");
			$('#recuperateId').empty();
			$("#form").data('bootstrapValidator').updateStatus("leaveEndTime","notEmpty", null).validateField("leaveEndTime");
			$("#form").data('bootstrapValidator').updateStatus("leaveStartTime","notEmpty", null).validateField("leaveStartTime");
			$("#form").data('bootstrapValidator').updateStatus("leaveLongTime","notEmpty", null).validateField("leaveLongTime");
		}
	}
}

// 求出外出时长
function change(dom) {
	var goOutStartDate = $("#leaveStartDate").val()
	var goOutEndDate = $("#leaveEndDate").val()
	var startAmOrPm = $("#startAmOrPm").val()
	var endAmOrPm = $("#endAmOrPm").val()
	var startDate = new Date(Date.parse(goOutStartDate));
	var endDate = new Date(Date.parse(goOutEndDate));
	if (goOutStartDate != '' && goOutEndDate != '') {
		if (Date.parse(startDate) > Date.parse(endDate)) {
			layer.msg("结束日期不得大于开始日期！", {
				icon : 0
			});
			// alert("结束日期不得大于开始日期")
			$("#" + dom).val("")
			return;
		}

	}
	if (goOutStartDate == '' || goOutEndDate == '' || startAmOrPm == ''
			|| endAmOrPm == '') {
		return;
	}
	if (Date.parse(startDate) == Date.parse(endDate)) {
		if (startAmOrPm < endAmOrPm) {
			layer.msg("结束日期不得大于开始日期！", {
				icon : 0
			});
			// alert("结束日期不得大于开始日期")
			$("#" + dom).val("")
		} else {
			$.post("/zhbg/kqgl/qj/getGoOutTime", {
				startDate : goOutStartDate,
				endDate : goOutEndDate,
				startAmOrPm : startAmOrPm,
				endAmOrPm : endAmOrPm
			}, function(data) {
				$("#leaveLongTime").val(data).change();
			});
		}
	} else {
		$.post("/zhbg/kqgl/qj/getGoOutTime", {
			startDate : goOutStartDate,
			endDate : goOutEndDate,
			startAmOrPm : startAmOrPm,
			endAmOrPm : endAmOrPm
		}, function(data) {
			$("#leaveLongTime").val(data).change();
		});
	}
}
// 判读是否是同一天
function ifOneDay(value1, value2) {
	var yearStart = new Date(Date.parse(value1)).getFullYear();
	var monthStart = new Date(Date.parse(value1)).getMonth();
	var dayStart = new Date(Date.parse(value1)).getDate();

	var yearEnd = new Date(Date.parse(value2)).getFullYear();
	var monthEnd = new Date(Date.parse(value2)).getMonth();
	var dayEnd = new Date(Date.parse(value2)).getDate();
	if (yearStart == yearEnd && monthStart == monthEnd && dayStart == dayEnd) {
		return true;
	} else {
		return false;
	}
}
//倒休情况
function isLeaDetail(leaveStartDate, leaveStartTime, leaveLongTime) {
	// 点击切换按钮 请假日期非空todo
	$('#isLeaveInLieuCondition').css("display", "block");
	var currentTime = "";
	if(leaveStartDate){
		if (leaveStartDate != '') {
			currentTime = leaveStartDate + " 00:00:00";
		} else {
			currentTime = leaveStartTime;
		}
		$.post("/zhbg/kqgl/qj/getOverTimeHours", {
			currentTime : currentTime
		}, function(data) {
			// 获取加班的总天数
			var talLongTimeH = data.talLongTimeH;
			var talLongTimeD = data.talLongTimeD;
			// 获取本季度调休天数和时长
			// var type=$("#leaveTimeType").val();
			var type = $("input[name='leaveTimeType']:checked").val();
			var longTimeD = Number(data.talLeaveLongTimeD);
			var longTimeH = Number(data.talLeaveLongTimeH);
			if (type == '0') {
				longTimeD += Number(leaveLongTime);
			}
			if (type == '1') {
				longTimeH += Number(leaveLongTime);
			}
			//获取加班的总小时
			html = "";
			$("#recuperateId").empty().append(
					html += "<span >本季度加班共<font color='red'><b>" + talLongTimeD
							+ "</b></font> 天 ,<font color='red'><b>" + talLongTimeH
							+ "</b></font> 小时; 已调休<font color='red'><b>"
							+ longTimeD + "</b></font> 天,<font color='red'><b>"
							+ longTimeH + "</b></font> 小时</span>")
		});
		
	}
}
