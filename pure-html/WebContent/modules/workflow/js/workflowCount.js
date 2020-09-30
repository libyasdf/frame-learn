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
	   url: basePath+"/workflow/getSubflagFlow",
	   data: {
		   pageNum:getPageNum(page),
		   size:10,
		   start:$("#startTime").val(),
		   end:$("#endTime").val(),
		   title:$("#title").val()
	   },
	   success: function(pageBean){
		   console.log(pageBean);
		   var data = pageBean.recordList;
		   var index = pageBean.indexNum;
		   if(data==""){
			   view += "<tr><td colspan='8'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'></span>无数据</td></tr>"
		   }else{
			   for ( var i in data) {
				   view+="<tr style=\"curosr: pointer\">"
					   	+ "<td>"+index+"</td>"
					   	+ "<td>"+data[i].workflowname+"</td>"
					   	+ "<td>"+data[i].title+"</td>"
					   	+ "<td>"+data[i].receivetime+"</td>"
					   	+ "<td>"+data[i].draftusername+"</td>"
					   	+ "<td>"+regVlaue(data[i].signtime)+"</td>";
				   if(data[i].stattag=="0"){
					   view+="<td>挂起中</td>";
				   }else if(data[i].stattag=="1"){
					   view+="<td>流转中</td>";
				   }else if(data[i].stattag=="4"){
					   view+="<td>已办结</td>";
				   }
				   view+= "<td>"; 
				   view+="<input type=\"button\" class=\"btn btn-success\" onclick=\"getFormUrl('"+data[i].formurl+"','"+data[i].waitdourl+"','"+data[i].recordid+"','"+data[i].filetypeid+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"','"+data[i].workflowinfoid+"','"+data[i].workflowname+"')\" value=\"监控\" >";
					   	
					   	view+="</td></tr>";
				   index++;
			   }
		   }
		   $("#weblist").html(view);
		   initPage(pageBean);
	   }
	});
}



function getFormUrl(formid,url,recordid,fileTypeId,workFlowId,deptId,userId,workitemid,workFlowName){
	if(formid!=null&&formid!=""&&formid!='null'){
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
	openWin(pathurl+"?oper=VIEW&pkValue="+pk+"&filetypeid="+filetypeid+"&workflowid="+workflowid+"&workitemid="+workitemid+"&deptid="+deptid+"&userid="+userid+"&formId="+formid+"&sysId="+Config.sysId+"&title="+encodeURI(workFlowName),workFlowName);
}


/** 
 * 监听打开的弹窗，关闭后刷新页面 
 */  
function openWin(url,title){  
    var winObj = window.open(url,title,
    		'status=yes,maximize=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,top=0,left=0,resizable=yes,width='+(screen.availWidth - 10)+', height='+ (screen.availHeight - 56));  
    		winObj.focus();
	var loop = setInterval(function() {       
    if(winObj.closed) {      
            clearInterval(loop);      
            selectAll();   
           // parent.location.reload();   
        }      
    }, 1);     
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