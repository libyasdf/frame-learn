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
		   startTime:$("#startTime").val(),
		   endTime:$("#endTime").val(),
		   title:$("#title").val()
	   },
	   success: function(pageBean){
		   var data = pageBean.recordList;
		   var index = pageBean.indexNum;
		   var page = pageBean.currentPage;
		   if(data==""){
			   view += "<tr><td colspan='8'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'></span>无数据</td></tr>"
		   }else{
			   for ( var i in data) {
				   view+="<tr style=\"curosr: pointer\">"
					   	+ "<td>"+index+"</td>"
					   	+ "<td>"+data[i].workFlowName+"</td>"
					   	+ "<td>"+data[i].title+"</td>"
					   	+ "<td>"+data[i].readTime+"</td>"
					   	+ "<td>"+data[i].username+"</td>"
					   	+ "<td>"+data[i].createTime+"</td>";
				   if(data[i].stattag=="0"){
					   view+="<td>挂起中</td>";
				   }else if(data[i].stattag=="1"){
					   view+="<td>流转中</td>";
				   }else if(data[i].stattag=="4"){
					   view+="<td>已办结</td>";
				   }
				   view+= "<td>"; 
					   	if(isDisAdd_DelHq(data[i].workitemid)){
					   		view+="<input type=\"button\" class=\"btn btn-success\" onclick=\"add_DelHq_('"+data[i].workitemid+"','"+page+"')\" value=\"增删会签\" >";
					   	}
					   	//+"<input type=\"button\" class=\"btn btn-primary\" onclick=\"viewWebForm('"+data[i].recordid+"','"+data[i].fileTypeId+"','"+data[i].workflowid+"','"+data[i].deptid+"','"+data[i].userid+"')\" value=\"查看\" >" 
					   	view+="</td></tr>";
				   index++;
			   }
		   }
		   $("#weblist").html(view);
		   initPage(pageBean);
	   }
	});
}
//判断显示增删会签
function isDisAdd_DelHq(id){
	var falg=false;
	if(id!=null&&id!=""){
		$.ajax({
			   type: "POST",
			   url: basePath+"/operationcs/check",
			   data:{
				   workitemid:id  
			   },
			   async: false,
			   dataType:"json",
			   success: function(data){
				   if(data.flag=="false"){
					   falg=false;
				   }else{
					   falg=true;
				   }
			   }
			});
	}
	 return falg;
}
//进行增加删除会签操作
function add_DelHq_(id,page){
	newWebLocation(id,page);
}
function toHistory(){
	window.history.back();
}
function newWebLocation(workitemid,page){
	 $.dialog({
		id : '#OPEN',
		title : "增删会签分支页面",
		lock : true,
		padding : 0,
		width : 650,
		height : 300,
		zIndex : 1000,
		content : 'url:'+basePath+"/modules/workflow/addAndDelSignBranch.html",
		data:{
			workitemid:workitemid
		},
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
		cancelVal : '关闭',
		cancel :function(){
			selectAll(page);
		} ,
		button : [ {
			name : '确定',
			callback : function() {
				this.button({
					name : "确定",
					disabled : false
				});
				var result = this.content.addDelSubmit();
				if(!result){
					showDialogAlert("操作成功！",true);
					return true;
				}else{
					showDialogAlert("操作失败！",false);
					return false;
				}
				selectAll(page);
				this.button({
					name : "确定",
					disabled : false
				});
			},
			focus : true
		} ]
	});
}
