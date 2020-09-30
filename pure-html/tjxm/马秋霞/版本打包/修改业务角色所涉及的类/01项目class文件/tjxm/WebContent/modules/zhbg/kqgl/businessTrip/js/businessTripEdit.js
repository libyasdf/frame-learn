var fileTypeId = "ac579d480f194e909b361e1db24c87f4";
var workFlowId = "ac579d480f194e909b361e1db24c87f4";

$(function() {
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	Dictionary.init({position:"busiTripType",mark:"busiTripType",type:"1",name:"busiTripType",domType:"radio"});
	 $("input[name=busiTripType]:eq(1)").attr("checked",'checked'); 
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {
		// 表单数据渲染
		var datas = {
			"id" : $("#id").val(),"resId":$("#resId").val()
		};
		var busiTripTypeNum="";
		if($("#opertation").val()=="VIEW"){	//只读页渲染
			httpRequest("get","/zhbg/kqgl/businessTrip/edit",datas,function (data){
				busiTripTypeNum=data.data.busiTripType;
				if(data.data.isHaveReceptionFees=='1'){
					data.data.isHaveReceptionFees="有";
				}
				if(data.data.isHaveReceptionFees=='0'){
					data.data.isHaveReceptionFees="无";
					$('#hasReceptionFees').attr("style", "display:none;");
				}
				DisplayData.playData({data: data});
			});
			//只读页面数据字典翻译
			if(busiTripTypeNum!=""){
				$("#busiTripType").html(Dictionary.getNameAndCode({mark:"busiTripType",type:"1"})[busiTripTypeNum]);
			}
		}else{
			httpRequest("get","/zhbg/kqgl/businessTrip/edit",datas,function (data){
				if(data.data.endTime!=null && data.data.endTime!=""){
					data.data.startTime=data.data.startTime+' - '+data.data.endTime;
				}
				var type=data.data.busiTripType
				$("input[name=busiTripType]").get(type).checked = true;  
				DisplayData.playData({data: data});
			});
		}
		//流程相关（按钮、控件等）的渲染
		if($("#workitemid").val()!=""){
			if($("#opertation").val()=="EDIT"){
				var datas = {
						workflowid:$("#workflowid").val(),
						filetypeid:$("#filetypeid").val(),
						workitemid:$("#workitemid").val(),
						pkValue:$("#id").val(),
						userid:getcookie("userid"),
						deptid:getcookie("deptid"),
						oper:$("#opertation").val(),
                    	ideaArea:$("ideaArea").val()
				};
				//流转中不可修给表单--开始
				//$('form').find('input,textarea,select').not('#idea').prop('readonly',true);
				//流转中不可修给表单--结束
				DisplayData.playWorkFlow("/flowService/getFlowData",datas,$("#opertation").val(),callback);
				if($('#creUserId').val()==getcookie("userid")){
					$("#approval").hide();
					$("#noApproval").hide();
					$("#ideaArea").hide();
				}
			}else{//oper='VIEW'
				var datas = {
					type:"1",
					oper:$("#opertation").val(),
					workitemid:$("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
			}
			//初始化意见域，回显临时意见
			//initIdeaArea($("#workitemid").val(),'ideaName');
			//获取正式意见，渲染页面
			//getIdeas();
		}else{//workitemid为空时，表示编辑草稿状态，加载启动节点按钮及启动节点提示信息
			getStartWf();
		}
	}else{//id为空时，表示新建草稿状态，加载启动节点按钮及启动节点提示信息
		getStartWf();
		//新增
		$("#creUserName").val(getcookie("usernm"));
		$("#creUserId").val(getcookie("userid"));
		$("#applicationUnitName").val(getcookie("deptname"));
		var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#applicationTime").val(curDate);
		$("#approval").hide();
		$("#noApproval").hide();
	}
	// 初始化文件上传
	/*MyFileUpload.init({
		viewParams : {
			"tableId" : $("#id").val(),
			"tableName" : "task_plan"
		},
		editOrView : $("#opertation").val()
	});*/
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

//根据当前登录人的角色信息 和起草人的角色 判断审批的权限（按钮的显示状态）todo
function ifButtonByCreUserRole(){}

/**
 * 提交表单
 * 
 * @returns
 */
function commitForm() {
	var data = saveForm();
	if (data) {
		layer.msg("保存成功！", {
			icon : 1
		});
		// 刷新列表
		parent.TableInit.refTable('right_table1');
	}
}

/**
 * 保存
 */
function saveForm() {
	//var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
	var plan = "";
	if(bootstrapValidator.isValid()){
		//$("#title").val($("#businessTripTitle").val());
	if (!$("#id").val()) {
		$("#subflag").val(Config.startflag);
	}
	$.ajax({
		type : "POST",
		url : "/zhbg/kqgl/businessTrip/saveForm",
		data : $("#form").serialize(),
		async : false,
		success : function(json) {
			if (json) {
				plan = json.id;
				$("#id").val(json.id);
				$("#subflag").val(json.subflag);
				// 初始化文件上传,并保存业务ID到附件表
				/*MyFileUpload.init({
					viewParams : {
						"tableId" : $("#id").val(),
						"tableName" : "KQGL_BUSINESS_TRIP_INFO"
					},
					editOrView : $("#opertation").val()
				});
				MyFileUpload.saveDocIdToAffix({
					docId : plan
				});*/
			}
		}
	});
	// 保存临时意见
	//保存临时意见
	var tempIdea = $("#idea").val();
	saveIdeaTemp($("#workitemid").val(),tempIdea);
	}
	return plan;
}
/**
 * 提交流程
 */
function commitFlow() {
	if(!$("#businessTripTitle").val()){
		var creUserName=$("#creUserName").val();
		var curDate=getCurrentDate("yyyy-MM-dd  HH:mm:ss");
		var title=creUserName+"于"+curDate+"发出出差申请";
		$("#businessTripTitle").val(title);
	}
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	// 手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		var plan = saveForm();
		if (plan != "") {
			var oper = "";
			if ($("#workitemid").val() == "") {
				oper = "NEW";
			} else {
				oper = "EDIT";
			}
			//获取意见
	        var idea = $("#idea").val();
	       	var IsNotionShow =  document.getElementById("ideaArea").style.display;
			if(IsNotionShow == "block"){
				if("2"==  document.getElementById("fillmode").value){
	                if(idea == "" || idea == null){
	                    layer.msg("请填写意见", {icon: 0})
	                    return false;
	                }
				}
			}
			if($("#roleNo").val()==''){
				//请求后台 获取起草人的角色信息
			    $.ajaxSettings.async = false;
				$.post("/zhbg/kqgl/businessTrip/getRoleNoByUserId", {
					userId:$("#creUserId").val()
				}, function(data) {
					$("#roleNo").val(data);
				});
			    $.ajaxSettings.async = true;
			}
			//判断是不是正处长起草
			var roleNo=$("#roleNo").val();
			var zczCre="0";
			if(roleNo.indexOf("C001")!=-1){
				zczCre="1";
			}
			submitToFlow(oper,this,$("#businessTripTitle").val(),idea,$("#id").val(),"attr","attr1",$("#workitemid").val(),$("#workflowid").val(),"zczCre="+zczCre);
		}
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
		url : "/zhbg/kqgl/businessTrip/updateFlag",
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
//计算出差时间
function change(dom){
	var timeRange=$("#startTime").val()
	$.post("/zhbg/kqgl/businessTrip/getGoOutTime", {timeRange:timeRange},function(data){
		$("#longTime").val(data.data).change();
	} );
}