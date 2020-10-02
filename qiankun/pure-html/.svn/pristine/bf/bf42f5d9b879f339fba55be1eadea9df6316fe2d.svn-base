var fileTypeId = "0700e2df488a400a988b482c1f8a6757";
var workFlowId = "0700e2df488a400a988b482c1f8a6757";

$(function(){
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	//初始化字典项
    Dictionary.init({position:"supplementSignType",mark:"supplementSignType",type:"1",name:"supplementSignType",domType:"select"});
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		if($("#opertation").val()=="VIEW"){	//只读页渲染
			httpRequest("get","/zhbg/kqgl/bq/edit",datas,function (data){
				DisplayData.playData({data: data});
			});
		}else{
			httpRequest("get","/zhbg/kqgl/bq/edit",datas,function (data){
				DisplayData.playData({data: data});
				btnIfable(data.data.supplementSignType);
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
						oper:$("#opertation").val()
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
			//initIdeaArea($("#workitemid").val());
			//获取正式意见，渲染页面
			//getIdeas();
		}else{
			//getStartButton();
			getStartWf();
		}
	}else{
		getStartWf();
		$("#creUserName").val(getcookie("usernm"))
		$("#applicantUnitName").val(getcookie("deptname"));
		var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#applicationTime").val(curDate);
		$("#approval").hide();
		$("#noApproval").hide();
	}
})
  
/**
 * 获取正式意见、回显
 */
function getIdeas(){
	//返回数据格式参照：/mock/formalIdeas.json
	var res = getFormalIdeas($("#id").val(),fileTypeId);
	if(res.length > 0){
		for(var i=0;i<res.length;i++){
			if(res[i].ideaList.length > 0){
				for(var j=0;j<res[i].ideaList.length;j++){
					//var time = new Date(jsonArr[i].ideaList[j].ideatime);
					var textarea = document.getElementsByName(res[i].name);
					if(res[i].ideaList[j].userid.trim() != getcookie("userid").trim()){
						$(textarea).text(res[i].ideaList[j].idea);
					}
				}
			}
		}
	}
}

/**
 * 提交表单
 * @returns
 */
function comitForm(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
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
    	if(!$("#id").val()){
    		$("#subflag").val(Config.startflag);
    	}
    	$.ajax({
    		type: "POST",
    		url:"/zhbg/kqgl/bq/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if(json){
    				res = json.id;
    				$("#id").val(json.id);
    				$("#subflag").val(json.subflag);
    				
    			}
    		}
    	});
    	//保存临时意见
    	var tempIdea = $("#idea").val();
    	saveIdeaTemp($("#workitemid").val(),tempIdea);
    }
    return res;
}
/**
 * 提交流程
 */
function comitFlow(){
    //手动触发验证
	if(!$("#leaveTitle").val()){
		var creUserName=$("#creUserName").val();
		var curDate=getCurrentDate("yyyy-MM-dd  HH:mm:ss");
		var title=creUserName+"于"+curDate+"发出补签申请";
		$("#leaveTitle").val(title);
	}
	var plan = saveForm();
	if(plan!=""){
			var oper = "";
			if($("#workitemid").val()==""){
				oper = "NEW";
			}else{
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
			//var idea = $(document.getElementsByName($("#ideaName").val())[0]).val();
			//submitToFlow(oper,this,$("#creUserName").val()+"于"+$("#applicationTime").val()+"申请补签",idea,$("#id").val(),"attr","attr1",$("#workitemid").val(),$("#workflowid").val());
			submitToFlow(oper,this,$("#leaveTitle").val(),idea,$("#id").val(),"attr","attr1",$("#workitemid").val(),$("#workflowid").val());
	}
}

/**
 * 工作流回调该方法，用于改变业务数据状态
 * @param subflag
 *            状态位
 * @returns
 */
function updateBusiData(subflag) {
	//根据补签类型判断补签时间：
	var supplementSignType=$("#supplementSignType").val();
	var supplementSignTime="";
	if(supplementSignType=='0'){
		supplementSignTime=$("#supplementSignStartTime").val();
	}
	if(supplementSignType=='1'){
		supplementSignTime=$("#supplementSignEndTime").val();
		}
	if(supplementSignType=='2'){
		supplementSignTime=$("#supplementSignStartTime").val()+" - "+$("#supplementSignEndTime").val();
	}
	$.ajax({
		type : "POST",
		url : "/zhbg/kqgl/bq/updateFlag",
		data : {
			id : $("#id").val(),
			userId:getcookie("userid"),
			supplementSignDate:$("#supplementSignDate").val(),
			supplementSignTime:supplementSignTime,
			supplementSignType:supplementSignType,
			subflag : subflag
		},
		async : false,
		success : function(data) {
		}
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
function bqTime(){
	//判断补签日期是否为空
	if($("#supplementSignDate").val()!=''){
		$.ajax({
			type : "POST",
			url : "/zhbg/kqgl/bq/getQrQcInfo",
			data : {
				userId:getcookie("userid"),
				supplementSignDate:$("#supplementSignDate").val()
			},
			async : true,
			success : function(data) {
				var supplementSignType=$("#supplementSignType").val();
				var supplementSignDate=$("#supplementSignDate").val();
				if(data){
					if(data.flag=='1'){
					  supplementSignTypeEvent(supplementSignType,supplementSignDate,data.QrTime,data.QcTime);
					}
					if(data.flag=='0'){
					  supplementSignTypeEvent(supplementSignType,supplementSignDate);
					}
				}
			},
			error:function(){
				supplementSignTypeEvent(supplementSignType,supplementSignDate);
			}
		});
	}else{
		var signType=$("#supplementSignType").val();
		btnIfable(signType)
	}
}
//根据补签类型回显数据
function supplementSignTypeEvent(signType,signDate,qrTime,qcTime){
	btnIfable(signType);
	simplifyData(qrTime,qcTime,signDate);
}
function btnIfable(signType){
	if(signType=='0'){
		$("#signStartTimeId").show();
		$("#signEndTimeId").hide();
		//$("#supplementSignStartTime").attr("disabled",false);
	//	$("#supplementSignEndTime").attr("disabled",true);
	}
	if(signType=='1'){
		$("#signStartTimeId").hide();
		$("#signEndTimeId").show();
		//$("#supplementSignStartTime").attr("disabled",true);	
		//$("#supplementSignEndTime").attr("disabled",false);
		}
	if(signType=='2'){
		$("#signStartTimeId").show();
		$("#signEndTimeId").show();
		//$("#supplementSignStartTime").attr("disabled",false);
		//$("#supplementSignEndTime").attr("disabled",false);
	}
}

function simplifyData(qrTime,qcTime,signDate){
	if(qrTime!=''&& qrTime!=undefined){
		$("#supplementSignStartTime").val(qrTime).change();
	}else{
		$("#supplementSignStartTime").val(signDate+BqConfig.start_time_at_work).change();	
	}
	if(qcTime!=''&& qcTime!=undefined){
		$("#supplementSignEndTime").val(qcTime).change();
	}else{
		$("#supplementSignEndTime").val(signDate+BqConfig.end_time_at_work).change();	
	}
}
