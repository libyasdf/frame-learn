$(function(){
	selectAll();
	/*
	格式化时间日期
	 */
	Date.prototype.Format = function(fmt) { //author: meizz 
		var o = {
			"M+" : this.getMonth() + 1, //月份 
			"d+" : this.getDate(), //日 
			"h+" : this.getHours(), //小时 
			"m+" : this.getMinutes(), //分 
			"s+" : this.getSeconds(), //秒 
			"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
			"S" : this.getMilliseconds()
		//毫秒 
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1,(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
})
function selectAll(page){
	var view = "";
	$.ajax({
	   type: "POST",
	   url: basePath+"/workflow/dblist",
	   data: {
		   pageNum:getPageNum(page),
		   beginTime:$("#beginTime").val(),
		   endTime:$("#endTime").val(),
		   title:$("#title").val()
	   },
	   success: function(pageBean){
		   var data = pageBean.recordList;
		   var index = pageBean.indexNum;
		   if(data==""){
			   view += "<tr><td colspan='8'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'></span>无数据</td></tr>"
		   }else{
			   for( var i in data) {
				   view+="<tr>"
					   	+ "<td><input id='c"+data[i].workitemid+"' name='box' type='checkbox' onchange=\"chooseFlow('"+data[i].workitemid+"','"+data[i].recordid+"')\"/></td>"
				   		+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+index+"</td>"
					   	+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].workFlowName+"</td>"
					   	+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].title+"</td>"
					   	+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].receiveTime+"</td>"
					   	+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].username+"</td>"
					   	+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].createTime+"</td>"
						+ "<td style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"+data[i].preUserName+"</td>"
				   index++;

			   }
		   }
		   $("#workitemid").val("");
		   $("#weblist").html(view);
		   initPage(pageBean);
	   }
	});
}
function chooseFlow(workitemid,recordid){
	if($("#c"+workitemid).is(":checked")){
		var result = $("#workitemid").val();
		var resultone = $("#id").val();
		result += workitemid + ",";
		resultone += recordid + ",";
		$("#workitemid").val(result);
		$("#id").val(resultone);
	}else{
		var result = $("#workitemid").val();
		var resultone = $("#id").val();
		var arr = result.split(",");
		var arrone = resultone.split(",");
		var newResult = "";
		var newResultone = "";
		for (var i = 0; i < arr.length; i++) {
			if(workitemid!=arr[i]){
				if(i!=arr.length-1){
					newResult += arr[i] + ",";
					newResultone += arr[i] + ",";
				}else{
					newResult += arr[i];
					newResultone += arr[i];
				}
			}
		}
		for (var i = 0; i < arrone.length; i++) {
			if(recordid!=arrone[i]){
				if(i!=arrone.length-1){
					newResultone += arrone[i] + ",";
				}else{
					newResultone += arrone[i];
				}
			}
		}
		$("#workitemid").val(newResult);
		$("#id").val(newResultone);
		
	}
}
function banjie(){
	$.ajax({
	   type: "POST",
	   url: basePath+"/flowService/completeFlow",
	   data: {
		   workitemid:$("#workitemid").val(),
		   recordid:$('#id').val(),
	   },
	   success: function(data){
		   if(data.result=="success"){
			   updateCurFlag(data);
			   showDialogAlert("办结成功", true);
			   selectAll();
		   }
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
function toHistory(){
	window.history.back();
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
		content : 'url:'+pathurl+"?oper=EDIT&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid+"&formId="+formid+"&sysId="+Config.sysId,
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
		cancelVal : '关闭',
		cancel : function(){
			selectAll();
		} 
	});
	//obj.location = "http://192.30.30.14:18004/CloudForm/project/"+url+"/display/display_data.html?oper=EDIT&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid+"&formId="+formid;
}
function newWebLocation(url,pk,filetypeid,workflowid,deptid,userid,workitemid,workFlowName){
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
			oper:"EDIT",
			id:pk,
			filetypeid:filetypeid,
			workflowid:workflowid,
			workitemid:workitemid
		},
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
		cancelVal : '关闭',
		cancel : function(){
			selectAll();
		} 
	});
	//obj.location = basePath+"/default/html/"+url+"?oper=EDIT&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid;
}
