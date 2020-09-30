$(function(){
	//getHtmlView();
	getflowinfo();
})
function selectAll(){
	var flow = "";
	$.ajax({
	   type: "POST",
	   url: basePath+"/workflow/getworkflow",
	   async: false,
	   success: function(data){  
		 //  flow = JSON.parse(data);
		   flow = data;
	   }
	});
	return flow;
}

//获取当前用户的信息
function getNowUserInfo(){
	var nowUserInfo = "";
	$.ajax({
	   type: "POST",
	   url: basePath+"/user/info",
	   async: false,
	   success: function(data){
		   nowUserInfo = data;
	   }
	});
	return nowUserInfo;
}




//设置流程内容
function getflowinfo(){
	var flowdata = selectAll();
	var userId = flowdata.userId;
	var deptId = flowdata.deptId;
    var flows = flowdata.flows;
	var dh = "";
	for ( var i in flows) {//流程分类
		var flowtype = flows[i];//流程类型
		var flowList = flowtype.flowList;
		dh += "<div class='panel panel-default'>"
	     	+ "<div class='panel-heading dhStyle'>"
	     	+ "<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion1' href='#collapseOne"+i+"'>"+flowtype.name+"<span class='glyphicon glyphicon-chevron-down' ></span></a></h4>"
	     	+ "</div>"
	     	+ "<div id='collapseOne"+i+"' class='panel-collapse collapse'>"
	     	+ "<div class='panel-body dtStyle' id='type"+i+"'>";
			var ttye = 0;
			for ( var j in flowList) {
					ttye++;
					dh += "<a href='#' onclick='getFormUrl(\""+flowList[j].form+"\",\""+flowList[j].waitdourl+"\",\""+flowList[j].fileTypeId+"\",\""+flowList[j].workflowid+"\",\""+deptId+"\",\""+userId+"\",\""+flowList[j].workflowname+"\")' class='well col-md-3 col-sm-4 col-xs-6 atStyle'><dl><dt>"+ttye+"</dt><dd>"+flowList[j].workflowname+"</dd></dl></a>";
			}
			dh += "</div></div></div>";
	
	}
	$("#accordion1").html(dh);
	
}
function getFormUrl(formid,waitUrl,fileTypeId,workFlowId,deptId,userId,workFlowName){
	if(formid!=null&&formid!=""){
		$.ajax({
			type: "POST",
			url: basePath+"/workflow/getformurl",
			data:{
				formid:formid  
			},
			success: function(data){
				newWebForm(data,formid,fileTypeId,workFlowId,deptId,userId,workFlowName);
			}
		});
	}else{
		newWebLocation(waitUrl,fileTypeId,workFlowId,deptId,userId,workFlowName);
	}
}
function newWebForm(url,formid,fileTypeId,workFlowId,deptid,userid,workFlowName){
	var pathurl = "";
	if(url=="webform"){
		pathurl = Config.webForm_url;
	}else{
		pathurl = Config.cloudForm_url;
	}
     window.open(pathurl+"?oper=NEW&pkValue=&filetypeid="+fileTypeId+"&workFlowId="+workFlowId+"&deptid="+deptid+"&userid="+userid+"&formId="+formid+"&sysId="+Config.sysId+"&title="+encodeURI(workFlowName),
				'status=yes,maximize=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,top=0,left=0,resizable=yes,width='
				+(screen.availWidth - 10)+', height='+ (screen.availHeight - 56)).focus();
}

function newWebLocation(url,fileTypeId,workFlowId,deptid,userid,workFlowName){
	$.dialog({
		id : '#OPEN',
		title : workFlowName,
		lock : true,
		padding : 0,
		width : 1280,
		height : 500,
		zIndex : 1000,
		content : 'url:'+basePath+url,
		data:{
			oper:"NEW",
			filetypeid:fileTypeId,
			workflowid:workFlowId,
			workFlowName:workFlowName,
			userid:userid,
		},
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
	});
}
/*
 * 刷新方法
 */
function refreshPage(){
	selectAll();
}