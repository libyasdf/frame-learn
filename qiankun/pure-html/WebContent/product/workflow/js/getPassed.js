$(function() {
	//var api = frameElement.api,W = api.opener;
	//var workitemid = api.data.workitemid;
	var request = new GetRequest();
	var workitemid = request.workitemid;
	$("#workitemid").val(workitemid);
	getJumpWflevePassed(workitemid);
})

function getJumpWflevePassed(workitemid) {
	var view = "";
	$.ajax({
		async: false,
		cache: false,
		url: BASEPATH + "/workflow/getJumpWflevePassed",
		type: "post",
		data: {
			"workitemid": workitemid,
		},
		dataType: "json",
		success: function(data) {
			var arr = data.res;
			for(var i in arr) {
				//view += "<option value='"+arr[i].wfleveid+"'>"+arr[i].wflevename+"</option>";
				view += "<li><label class='radio-inline'>"+
					"<input type='radio' name='passed' onclick='getPassedUser(\"" + arr[i].wfleveid + 
					"\")'/>&nbsp;&nbsp;" + arr[i].wflevename + "</li></label>";
			}
			$("#passed").html(view);
		},
		error: function(data) {
			layer.msg("办结成功！",{icon:6});
		}

	});
}

function getwfleveId() {
	var mdata = {
		wfleveid: $("#wfleveId").val(),
		workitemid: $("#workitemid").val(),
		rolesId: $("#nodeid").val(),
		idea:$("#passidea").val()
	}
	return mdata;
}

function getPassedUser(wfleveId) {
	$("#wfleveId").val(wfleveId);
	var recordId = $("#id").val();
	var type = $("#type").val();
	var workitemId = $("#workitemid").val();
	var zNodes = new Array();
	var pnode = new Array();
	var pchild = new Array();
	pnode.name = "待选人员";
	pnode.nocheck = true;
	$.ajax({
		type: "POST",
		url: basePath + "/workflow/getJumpWfAppointWrite",
		async: false,
		data: {
			wfleveId: wfleveId,
			recordId: recordId,
			docType:type,
			workitemId:workitemId
		},
		success: function(data) {
			for(var i in data) {
				var dnode = new Array();
				var dchild = new Array();
				dnode.name = data[i].deptName;
				dnode.nocheck = true;
				var arr = data[i].res;
				for(var j in arr) {
					var cnode = new Array();
					cnode.id = arr[j].deptId + "*" + arr[j].userid;
					cnode.name = arr[j].userNm;
					dchild.push(cnode);
				}
				dnode.children = dchild;
				pchild.push(dnode);
			}
			pnode.children = pchild;
		}
	});
	zNodes.push(pnode);

	var zTreeobj;
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio"
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid",
				rootPId: "0"
			}
		},
		callback: {
			onCheck: zTreeOnClick,
		}
	};
	zTreeobj = $.fn.zTree.init($("#userArea"), setting, zNodes);
	zTreeobj.expandAll(true); 
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#nodeid").val(treeNode.id);
}
////成功 或失败 提示框
//function showDialogAlert(msg, isSuccess) {
//	$.dialog({
//		title: '提示',
//		content: msg,
//		zIndex: 999999,
//		icon: isSuccess ? 'success.gif' : "alert.gif",
//		cancel: function() {
//			objEle.get('jumpNode', 1).close();
//		},
//		button: [{
//			name: '确定',
//			callback: function() {
//				objEle.get('jumpNode', 1).close();
//			},
//			focus: true
//		}]
//	});
//}

function getDataByAjax(url,data,callback){
		var options = {
			async : false,  
	        cache : false,  
			type: "post",  
			url : url,
			data:data,   
			dataType : 'json'
		};
		var returnVal = {};
		$.when(
			$.ajax(options)
		).then(function(data){
			returnVal = data;
			if(callback){
				callback(data);
			}
		},function(data){
			returnVal = data;
		});
		
		return returnVal;
	}

function jumpNode(){
	var result = "";
	var mdata = {
		wfleveid: $("#wfleveId").val(),
		workitemid: $("#workitemid").val(),
		rolesId: $("#nodeid").val()
	}
	getDataByAjax(basePath + "/flowService/jumpToWfleve", mdata, function(data) {
		result = data.res;
		if(data.res) {
			layer.msg("退回成功！",{icon:6});
		} else {
			layer.msg("退回失败！",{icon:5});
		}
	});
	return result;
}
