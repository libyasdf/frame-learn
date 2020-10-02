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
	   url: basePath+"/workflow/yblist",
	   data: {
		   pageNum:getPageNum(page),
//		   beginTime:$("#beginTime").val(),
		   startTime:$("#startTime").val(),
		   endTime:$("#endTime").val(),
		   title:$("#title").val()
	   },
	   success: function(pageBean){
		   var data = pageBean.recordList;
		   var index = pageBean.indexNum;
		   if(data==""){
			   view += "<tr><td colspan='8'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'></span>无数据</td></tr>"
		   }else{
			   for ( var i in data) {
				   view+="<tr style=\"curosr: pointer\" onclick=\"getFormUrl('"+data[i].formid+"','"+data[i].url+"','"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workitemid+"','"+data[i].workFlowName+"')\">"
					   	+ "<td>"+index+"</td>"
					   	+ "<td>"+data[i].workFlowName+"</td>"
					   	+ "<td>"+data[i].title+"</td>"
					   	+ "<td>"+data[i].readTime+"</td>"
					   	+ "<td>"+data[i].username+"</td>"
					   	+ "<td>"+data[i].createTime+"</td>"
//					   	+ "<td>" 
					   	//+"<input type=\"button\" class=\"btn btn-primary\" onclick=\"viewWebForm('"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"')\" value=\"查看\" >" 
//					   	+"</td>";
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
	window.open(pathurl+"?oper=VIEW&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid+"&formId="+formid+"&sysId="+Config.sysId+"&title="+encodeURI(workFlowName),
			'status=yes,maximize=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,top=0,left=0,resizable=yes,width='+(screen.availWidth - 10)+', height='+ (screen.availHeight - 56)).focus();
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
				oper:"VIEW",
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
}