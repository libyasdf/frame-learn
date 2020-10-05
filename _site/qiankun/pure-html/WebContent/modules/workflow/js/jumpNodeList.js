$(function(){
	selectAll();
})
function selectAll(page){
	var view = "";
	$.ajax({
	   type: "POST",
	   url: basePath+"/workflow/getJumpNodeList",
	   data: {
		   pageNum:getPageNum(page),
	   },
	   success: function(pageBean){
		   var data = pageBean.recordList;
		   var index = pageBean.indexNum;
		   if(data==""){
			   view += "<tr><td colspan='8'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'></span>无数据</td></tr>"
		   }else{
			   for ( var i in data) {
				   view+="<tr>"
//					   	+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+index+"</td>"
//					   	+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].workFlowName+"</td>"
//					   	+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].title+"</td>"
//					   	+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].receiveTime+"</td>"
//					   	+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].username+"</td>"
//					   	+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].createTime+"</td>"
//						+ "<td onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].preUserName+"</td>"
					   	+ "<td>"+index+"</td>"
					   	+ "<td>"+data[i].workFlowName+"</td>"
					   	+ "<td>"+data[i].title+"</td>"
					   	+ "<td>"+data[i].receiveTime+"</td>"
					   	+ "<td>"+data[i].username+"</td>"
					   	+ "<td>"+data[i].createTime+"</td>"
						+ "<td>"+data[i].preUserName+"</td>"
					    + "<td><button class='btn btn-mini btn-info' onclick=\"jumpNode('"+data[i].workitemid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].recordid+"')\">跳转节点</button></td></tr>"
				   index++;
			   }
		   }
		   $("#weblist").html(view);
		   initPage(pageBean);
	   }
	});
}
function getFormUrl(formid,url,recordid,fileTypeId,workFlowId,deptId,userId,workitemid,workFlowName){
	if(formid!=null&&formid!=""){
		$.ajax({
		   type: "POST",
		   url: basePath+"/workflow/getformurl",
		   data:{
			   formid:formid  
		   },
		   success: function(data){
			   viewWebForm(data,formid,recordid,fileTypeId,workFlowId,deptId,userId,workitemid,workFlowName);
		   }
		});
	}else{
		newWebLocation(url,recordid,fileTypeId,workFlowId,deptId,userId,workitemid,workFlowName);
	}
}
function viewWebForm(url,formid,pk,filetypeid,workflowid,deptid,userid,workitemid,workFlowName){
	var pathurl = "";
	if(url=="webform"){
		pathurl = Config.webForm_url;
	}else{
		pathurl = Config.cloudForm_url;
	}
	 $.dialog({
		id : '#OPEN',
		title : workFlowName,
		lock : true,
		padding : 0,
		width : 1000,
		height : 500,
		zIndex : 1000,
		content : 'url:'+pathurl+"?oper=VIEW&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid+"&formId="+formid+"&sysId="+Config.sysId,
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
	});
}
function newWebLocation(url,pk,filetypeid,workflowid,deptid,userid,workitemid,workFlowName){
	 $.dialog({
		id : '#OPEN',
		title : workFlowName,
		lock : true,
		padding : 0,
		width : 1000,
		height : 500,
		zIndex : 1000,
		content : 'url:'+basePath+"/default/html/"+url+"?oper=VIEW&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid,
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
	});
}
function jumpNode(workitemid,fileTypeId,workflowid,recordId){
	$.dialog({
		id : '#OPEN',
		title : "跳节点",
		lock : true,
		padding : 0,
		width : 1280,
		height : 500,
		zIndex : 500,
		max:false,
		min:false,
		content : 'url:'+basePath+"/product/workflow/html/jumpNode.html",
		data:{
			workflowid:workflowid,
			fileTypeId:fileTypeId,
			sysId:Config.sysId,
			workitemid:workitemid,
			recordId:recordId
		},
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
		cancelVal : '关闭',
		cancel : function(){
			selectAll();
		} 
	});
}