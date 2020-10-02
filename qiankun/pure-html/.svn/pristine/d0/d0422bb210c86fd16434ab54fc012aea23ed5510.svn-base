$(function(){
	selectAll();
})

function selectAll(page){
	$("#weblist").html("");
	$("#allIds").prop("checked",false);
	var rowNum = 10;
	$.ajax({
	    async : false,  
        cache:false,  
		type: "post",  
		url : basePath+'/flowSccredit/flowSccreditList',
		data:{
			pageNum:getPageNum(page),
			rowNum:rowNum,
			filetypename:$("#filetypename").val(),
			sccreditusername:$("#sccreditusername").val()
		},    
		dataType:"json",
		success: function(pageBean) {
			var data = pageBean.recordList;
			var i = pageBean.indexNum;
			if(data!=""){
				for(var o in data){
					var type = data[o].sccredittype;
					if(type=='1'){
						type = "单独待办"; 
					}else{
						type = "共同办理";
					}
					var tr = $("<tr>").clone(true);
					//tr.attr('class','');
					tr.empty();
					var $td = $("<td>").attr({
								//"title" : "点击可查看详情",
								//"onclick":"toRead('"+data[o].fileCodeId+"')",
								"align":"center",
								"style":"text-align:center;cursor:pointer;"
								});
					tr.append($td.clone().text(i));
					tr.append($td.clone().text(tab(getNowFormatDate(),data[o].sccenddate)?"授权中":"已过期"));
					tr.append($td.clone().text(data[o].filetypename));
					tr.append($td.clone().text(data[o].username));
					tr.append($td.clone().text(data[o].deptname));
					tr.append($td.clone().text(data[o].sccreditusername));
					tr.append($td.clone().text(data[o].sccbegindate));
					tr.append($td.clone().text(data[o].sccenddate));
					tr.append($td.clone().text(type));
					tr.append($td.clone().text(data[o].issccredit=='0'?"否":"是"));
					//tr.append($td.clone(true).attr({"width":"7%"}).append("<input name=\"delId\" value=\""+data[o].sccreditid+"\" type=\"checkbox\">"));
					tr.append($td.clone().append("<span value='修改' title='修改' class='opr-btn glyphicon glyphicon-pencil' style='margin-right:10px;' onclick='edit_auth(\""+data[o].sccreditid+"\")'></span><span value='删除' title='删除' class='glyphicon glyphicon-trash' style='margin-right:10px;color:red;' onclick='del_auth(\""+data[o].sccreditid+"\")'></span>"));
					
					$("#weblist").append(tr);
					i++;
				}
			}else{
				$("#weblist").append("<tr><td colspan='12'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'></span>无数据</td></tr>");
			}
			initPage(pageBean);
		},  
		error: function(request) {  
			alert("error!");
		}    
	});
}

//新增授权信息
function do_auth(){
	var id = "";
	showdialog(id);
}
//修改授权信息
function edit_auth(id){
	showdialog(id)
}
function showdialog(id){
	$.dialog({
		id : '#doauth',
		title : '授权信息',
		lock : true,
		padding : 0,
		width : 1100,
		height : 550,
		zIndex :100,
		content : 'url:'+basePath+"/modules/workflow/personal/pendingAuthorizationEdit.html",
		data : {"id":id},
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
		cancelVal : '关闭',
		cancel : true,
		button : [ {
			name : '确定',
			callback : function() {
				this.button({
					name : "确定",
					disabled : false
				});
				var result = this.content.formsubmit();
				if(typeof(result)=="undefined"){
					selectAll();
					return false;
				}else{
					return false;
				}
			},
			focus : true
		} ]

	});
}

function tab(date1,date2){
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() > oDate2.getTime()){
        return false;
    } else {
    	return true;
    }
}

function del_auth(id){
	if(id!=""){
		$.dialog.confirm('确定删除此条信息吗？', function() {
			var data={"ids":id};
			getDataByAjax(basePath+'/flowSccredit/del?',data,function(data) {
				if (data["result"] == "0") {
					result = data["msg"];
					showDialogAlert(result,false);
				} else {
					result = data["msg"];
					showDialogAlert(result,true);
				}
				selectAll();
			});
		}, function() {
			//选择取消执行动作
		});
	}else{
		Overlay.unableAlert("请选择记录！");
		return false;
	}
}
	
	
	
	function clear_on(){
		$("#addedit").find("input[type='text']").each(function () {
             if ($(this).val() != "") {
                 $(this).val("");
             }
   		 });
   		$("#addedit").find("select").each(function () {
           	$(this).find("option[value='']").prop("selected",true);
 		});
 		/* $("#scopeone").prop("checked",true);
 		$("#typeShow").hide(); */
 		delAll();
 		$("#updateAuth").show();
   	 	$("#updateSubmit").hide();
	}
	function allcheckIds(obj){
		if($(obj).prop("checked")){
			$("input[type=checkbox][name=delId]").prop("checked",true);
		}else{
			$("input[type=checkbox][name=delId]").prop("checked",false);
		}
	}

	//参数  isDay 值为true表示只显示日期  不传显示日期和时间
	function getNowFormatDate(isDay) {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var strHour = date.getHours();
	    var strMin = date.getMinutes();
	    var strSeconds = date.getSeconds();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    if (strHour >= 0 && strHour <= 9) {
	    	strHour = "0" + strHour;
	    }
	    if (strMin >= 0 && strMin <= 9) {
	    	strMin = "0" + strMin;
	    }
	    if (strSeconds >= 0 && strSeconds <= 9) {
	    	strSeconds = "0" + strSeconds;
	    }
	    
	    
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	    if(!isDay){

	    	currentdate += " " + strHour + seperator2 + strMin
	        + seperator2 + strSeconds
	    }
	    return currentdate;
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
