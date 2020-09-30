$(document).ready(function() {
	var ideaStyleStr = $("#ideaarea").attr("style");
	if (ideaStyleStr == "display: block;") {
		getNotionNormal();
	}
	getNotionNormal();
});

/**
 * 初始化意见域，当前用户意见域可写，并将意见域名称放入页面
 * @param workItemId 待办事项ID
 * @param domId 存放意见域名称的隐藏域ID
 */
function initIdeaArea(workItemId,domId){
	if(!domId){
		domId = "ideaName";
	}
	var ideaName = "";
	if(workItemId){
		$.ajax({
			type:"get",
			url:"/flowService/getDaiBanData",
			data:{
				"workItemId":workItemId
				},
			dataType: "json",
			async:false,
			success:function (flowData){
				if(flowData){
					if(flowData.ideaFieldVec.length > 0){
						for(var i=0;i<flowData.ideaFieldVec.length;i++){
							if(flowData.ideaFieldVec[i].fillmode == "2"){
								ideaName = flowData.ideaFieldVec[i].name;
								var textarea = document.getElementsByName(ideaName);
								//意见域改为可编辑
								$(textarea[0]).removeAttr("readonly");
								//取临时意见回显
								$(textarea[0]).text(getIdeaTemp(workItemId));
								$("#"+domId).val(ideaName);
							}
						}
					}
				}
			}
		});
	}
}

/**
 * 获取临时意见
 * @param workItemId
 * @returns {String}
 */
function getIdeaTemp(workItemId){
	var temp = "";
	if(workItemId){
		$.ajax({
			type:"get",
			url:"/flowService/getTempIdea",
			data:{
				"workItemId":workItemId
				},
			dataType: "json",
			async:false,
			success:function (json){
				if(json){
					temp = json.tempIdea;
				}
			}
		});
	}
	return temp;
}

/**
 * 保存临时意见
 * @param workItemId
 * @param idea 意见
 * @param ideaName 意见域名称
 * @returns {Boolean}
 */
function saveIdeaTemp(workItemId,idea,ideaName){
	var res = false;
	if(!ideaName){
		ideaName = "idea";
	}
	/*if(!idea){
		idea = " ";
	}*/
	if(workItemId){
		$.ajax({
			type:"get",
			url:"/flowService/saveTempIdea",
			data:{
				"workItemId":workItemId,
				"idea":idea,
				"ideaName":ideaName
				},
			dataType: "json",
			async:false,
			success:function (json){
				res = json.res;
			}
		});
	}
	return res;
}

/**
 * 获取正式意见
 * @param id 业务ID
 * @param fileTypeId 流程类型ID
 * @returns {Array} 数据格式参照：/mock/formalIdeas.json
 */
function getFormalIdeas(id,fileTypeId){
	var ideas = [];
	var datas = {
		pkValue:id,
		filetypeid:fileTypeId,
		userid:getcookie("userid"),
		deptid:getcookie("deptid")
	};
	$.ajax({
		type:"get",
		url:"/workflow/getformalidea",
		data:datas,
		dataType: "json",
		async:false,
		success:function (res){
			ideas = res;
		}
	});
	return ideas;
}

/**
 * 初始化常用意见下拉框
 */
function getNotionNormal() {
	var nodes = "<option value=''>--请选择--</option>";
	$.ajax({
		type: "POST",
		url: "/commonNotion/list",
		// async: false,
		dataType: "json",
		success: function(data) {
			var list = data.list;
			for(var i in list) {
				nodes += "<option  value='" + list[i].notion + "' title='" + list[i].notion + "'>" + list[i].notion + "</option>";
			}
			$("#CuOpinion").html(nodes);
		}
	});
}

/**
 * 选择下拉框时，同时改变意见域中的文本
 * @param value
 * @param ideaDomId 意见域控件的ID
 */
function onSelect(value,ideaDomId) {
	if(!ideaDomId){
		ideaDomId = "idea";
	}
	if(value != "") {
		$("#"+ideaDomId).val(value);
	}
    if($('#'+ideaDomId).val() != null && $('#'+ideaDomId).val() != ""){
        var len=$('#'+ideaDomId).val().length
        $('#idea-count').text(len);
    }
    $('#'+ideaDomId).keyup(function() {
        var len=this.value.length
        $('#idea-count').text(len);
    });
}

/**
 * 添加常用意见（个人意见）
 * @param ideaDomId 意见域控件的ID
 */
function add(ideaDomId) {
	$("#table").css("display","none");
	if(!ideaDomId){
		ideaDomId = "idea";
	}
	var notion = $("#"+ideaDomId).val();
	if(("undefined" != notion || null != notion || "null" != notion) && notion.length > 50) {
		notion = "";
	}
	var url = '/common/html/notion/Notion_add.html?notion=' + notion;
	layer.open({
		id:"notionNormal",
		type:2,
		title:'添加常用意见',
		content:encodeURI(url),
		area: ['400px', '250px'],
		btn: ['保存','关闭'],
		yes:function(index, layero){	//保存按钮的回调
			var iframeId = $("#notionNormal").find('iframe').attr('id')
			var result = document.getElementById(iframeId).contentWindow.notionAdd();
			if(result) {
				getNotionNormal();
			}
		},
		btn2: function(index, layero){	//关闭按钮的回调
			getNotionNormal();
		},
		end:function(){		//层销毁的回调，无论确定还是关闭，都会回调，不带任何参数
			getNotionNormal();
            $("#table").css("display","block");
		}
	}); 
}