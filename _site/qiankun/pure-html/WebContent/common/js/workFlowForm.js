$(function(){
	var request = GetRequest();
	$("#id").val(regVlaue(request.id));
	$("#filetypeid").val(regVlaue(request.flowId));
	$("#workflowid").val(regVlaue(request.workflowid));
	$("#workitemid").val(regVlaue(request.workItemId));
	$("#opertation").val(regVlaue(request.oper));
	if($("#workitemid").val()==""&&$("#id").val()==""){
		//从cookie中获取值：创建人id，创建人，系统id，组织机构id，创建部门id，创建部门等
		//以下字段以请假管理为例
		$("#creUserId").val(getcookie("userid"));
		$("#creUserName").val(getcookie("usernm"));
		$("#sysId").val(getcookie("sysid"));
		$("#orgId").val(getcookie("orgid"));
		$("#creSuperDeptId").val(getcookie("deptid"));
		$("#creSuperDeptName").val(getcookie("deptnm"));
	}
	
})

$(document).ready(function(){
	if($("#id").val()!=null&&$("#id").val()!=""){
		var datas = {"id":$("#id").val()};
		//以请假管理为例
		editFunction(basePath+"/leaveManagement/view",datas,"0","",hideForm);
		$("#subflow").css("display","block");
		$("#referBtn").attr("onclick","comitFlow()");
		if($("#workitemid").val()!=null&&$("#workitemid").val()!=""){
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
				editFunction(basePath+"/flowService/getFlowData",datas,"1",$("#opertation").val());
			}else{
				var datas = {
					type:"1",
					oper:$("#opertation").val(),
					workitemid:$("#workitemid").val()
				};
				
				/**
				 * 附件 start
				 */
				$("#attmentUpload").show();
				$("#contentUpload").show();
				$("#attmentUpload .delfuj").remove();
				$("#contentUpload .delfuj").remove();
				/**
				 * 附件 end
				 */
				$("#preserveBtn").css("display","none");
				$("#subflow").css("display","none");
				$("#FirstPage input.txt").attr("readOnly","readOnly");
				$("#FirstPage textarea").attr("readOnly","readOnly");//请假原因只读
				$("#FirstPage select").attr("disabled","disabled");
				$("#FirstPage .button1,.uploadifive-button").remove();
				editFunction(basePath+"/flowService/getYiBanData",datas,"1",$("#opertation").val());
			}
		}else{
			getStartButton();
			/**
			 * 附件 start
			 */
			$("#contentUpload").css("display","block");
			$("#attmentUpload").css("display","block");
			/**
			 * 附件 end
			 */
		}
	}
});

function selectOption(data){
	var arr = $("#frm select");
	for (var i = 0; i < arr.length; i++) {
		var name = $(arr[i]).attr("id");
		var selectId = data[name];
		$("select option[data-id='"+selectId+"']").attr("selected","selected");
	}
}
//控制页面流转中不可修改表单
function hideForm(data){
	var subflag = data.subFlag;
	if (subflag != "5") {
		$("#title").attr("disabled","disabled");
	}
}
function getStartButton(){
	var oper = "NEW";
	var startdatas = {
    	workflowid:$("#workflowid").val(),
    	filetypeid:$("#filetypeid").val(),
    	oper:oper
    };
	editFunction(basePath+"/workflow/getStartWfButton",startdatas,"1",oper);
}
//提交流程
function comitFlow(){
	if($("#workitemid").val()==""){
		if($("#referencequeue").children().length>0){
			var data = saveForm();
			$("#attr1").val(Config.leaveInfo);//模块的标识，需要在config.js中配置 
			if(data!=""){
				var workflowid = "";
				if($("#workflowid").val()==""){
					workflowid = "filetypeid";
				}else{
					workflowid = "workflowid";
				}
				saveFormAndToFlow('NEW',this,"","","id","attr","attr1","workitemid",workflowid);
			}
		}else{
			showDialogAlert("请先上传正文文件！",false);
		}
	}else{
		var data = saveForm();
		$("#attr1").val(Config.leaveInfo);//模块的标识，需要在config.js中配置 
		if(data!=""){
			var workflowid = "";
			if($("#workflowid").val()==""){
				workflowid = "filetypeid";
			}else{
				workflowid = "workflowid";
			}
			saveFormAndToFlow('EDIT',this,"","","id","attr","attr1","workitemid",workflowid);
		}
	}
}
//保存表单
function saveForm(){
	var result = "";
	var id = $("#id").val();
	var flowId = $("#filetypeid").val();
	if(autoCheckForm(document.getElementById("frm")) ){
		//调用后台保存方法，以请假管理为例
		$.ajax({
			 type: "POST",
			 url: basePath+"/leaveManagement/save",
			 data:{
				 title:$("#title").val(), //其他需要的字段
				 id:id,//主键id
				 workflowId:flowId,//工作流id
				 sysId:$("#sysId").val(),
				 orgId:$("#orgId").val(),
				 /**
				  * 附件 start
				  */
				 fuj:$("#attachmentdata").val(),
				 delfuj:$("#attachmentdeldata").val(),
				 conj:$("#referencedata").val(),
				 delconj:$("#referencedeldata").val(),
				 /**
				  * 附件 end
				  */
		         type:"1",//保存意见时需要
		         workitemid:$("#workitemid").val()   //保存意见时需要
			 },
			 async: false,
		     dataType: "text",
		     success: function(data) {
		    	 $("#id").val(data);
		    	 //附件处理
				 $("#referencedata").val("");
				 $("#attachmentdata").val("");
				 $("#attachmentdeldata").val("");
				 $("#referencedeldata").val("");
		    	 result = data;
		        }
		});
		
	}
	return result;
}
//提交表单、发送
function comitForm(){
	var data = saveForm();
	if(data!=""){
		$("#id").val(data);
		$("#subflow").css("display","block");
		$("#referBtn").attr("onclick","comitFlow()");
		if($("#workitemid").val()==""){
			getStartButton();
		}
		showDialogAlert("保存成功!!",true);
	}
}

//流程流转中，更新业务状态  ，以请假管理为例
function updateSubFlag(subflag){
	$.ajax({
        type: "POST",
        url: basePath+"/leaveManagement/updateSubFlag",
        data:{
        	id:$("#id").val(),
        	subflag:subflag
        },
        async: false,
        success:function(data){
        	if(data.flag==1){
        	}
        }
	});
}
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}

//附件 start
function showAttment(){
	$("#attmentUpload").show();
}
function viewAttment(){
	$("#attmentUpload").show();
	$("#attmentUpload .uploadifive-button").remove();
	$("#attmentUpload .delfuj").remove();
}
function showContentAtt(){
	$("#contentUpload").show();
}
function viewContentAtt(){
	$("#contentUpload").show();
	$("#contentUpload .uploadifive-button").remove();
	$("#contentUpload .delfuj").remove();
}
//附件 end