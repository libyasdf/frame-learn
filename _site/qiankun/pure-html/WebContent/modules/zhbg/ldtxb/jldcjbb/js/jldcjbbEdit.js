//流程文件storage.js中需要该变量回调方法
//var api = frameElement.api, W=api.opener;

$(function(){
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	//$("#filetypeid").val(theRequest.fileTypeId);
	//$("#workflowid").val(theRequest.workFlowId);
	//$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val()};
		if($("#opertation").val()=="VIEW"){	//只读页渲染
			httpRequest("get","/zhbg/ldtxb/jldcjbb/edit",datas,function (data){
				PageEcho.parseData({data: data});
			});
		}else{
			editFunction("/zhbg/ldtxb/jldcjbb/edit",datas,"0","");
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
				editFunction(basePath+"/flowService/getFlowData",datas,"1",$("#opertation").val());
			}else{//oper='VIEW'
				var datas = {
					type:"1",
					oper:$("#opertation").val(),
					workitemid:$("#workitemid").val()
				};
				editFunction(basePath+"/workflow/getYiBanData",datas,"1",$("#opertation").val());
			}
		}else{
			getStartButton();
		}
	}
})

/**
 * 调用工作流配置的按钮
 * @returns
 */
function getStartButton(){
	var oper = "NEW";
	var startdatas = {
    	workflowid:$("#workflowid").val(),
    	filetypeid:$("#filetypeid").val(),
    	oper:oper
    };
	editFunction(basePath+"/workflow/getStartWfButton",startdatas,"1",oper);
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
	//if(!$("#id").val()){
		//$("#subflag").val(Config.startflag);
	//}
	var plan = "";
	$.ajax({
		type: "POST",
		url:"/zhbg/ldtxb/jldcjbb/saveForm",
		data:$("#form").serialize(),
		async: false,
		success:function(json){
			if (json) {
				plan = json.id;
				$("#id").val(json.id);
				//$("#subflag").val(json.subflag);
			}
		}
	});
	return plan;
}

/**
 * 提交流程
 */
function comitFlow(){
	if(autoCheckForm(document.getElementById("form"))){
		var plan = saveForm();
		if(plan!=""){
			var workflowid = "";
			if($("#workflowid").val()==""){
				workflowid = "filetypeid";
			}else{
				workflowid = "workflowid";
			}
			if($("#workitemid").val()==""){
				saveFormAndToFlow('NEW',this,"","","id","attr","attr1","workitemid",workflowid);
			}else{
				saveFormAndToFlow('EDIT',this,"","","id","attr","attr1","workitemid",workflowid);
			}
		}
	}
}

/**
 *  工作流回调该方法，用于改变业务数据状态
 * @param subflag 状态位
 * @returns
 */
function updateSubFlag(subflag){
	$.ajax({
	    type: "POST",
	    url: basePath+"/taskplan/updateFlag",
	    data:{
	    	id:$("#id").val(),
	    	subflag:subflag
	    },
	    async: false,
	    success:function(data){
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