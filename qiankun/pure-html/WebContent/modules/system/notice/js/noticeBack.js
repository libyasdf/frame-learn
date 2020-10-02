$(function(){
	
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#oper").val(theRequest.oper);
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"noticeId":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/system/notice/view",datas,function (data){
			DisplayData.playData({data:data});
			//发布部门
			var dept = "";
        	if(data.data.creDeptName != null){
        		dept += data.data.creDeptName;
        	}
        	if(data.data.creChushiName != null && data.data.creChushiId != data.data.creDeptId){
        		dept = data.data.creChushiName + "/" + dept;
        	}
        	if(data.data.creJuName != null && data.data.creJuId != data.data.creChushiId && data.data.creJuId != data.data.creDeptId){
        		dept = data.data.creJuName + "/" + dept;
        	}
        	$("#pubDeptName").text(dept);
			//如果不需要反馈信息，则隐藏反馈按钮及textarea
			if(data.data.isBack == "0"){
				$(".backDiv").css("display","none");
				$("#backBtn").css("display","none");
			}else{
				//如果需要填写反馈信息，查询反馈信息
				getBack();
			}
		});
	}
	
	//初始化文件上传
	iniFileUpload();
})

/**
 * 获取当前用户的反馈信息
 */
function getBack(){
	$.ajax({
		type: "GET",
		url:"/system/notice/getBack",
		data:{
			noticeId: $("#id").val()
		},
		dataType:"json",
		success:function(json){
			if (json.flag == '1') {
				$("#backId").val(json.data.id);
				$("#backContent").val(json.data.backContent);
				//layer.msg("反馈成功",{icon:1});
			}else{
				//layer.msg("反馈失败，请刷新重试",{icon:2});
			}
		},
		error:function(){
			//layer.msg("反馈失败，请刷新重试",{icon:2});
		}
	});
}

/**
 * 保存反馈信息
 */
function saveBack(){
	var backId = $("#backId").val();
	if(backId){
		layer.confirm("您已提交反馈，是否再次提交反馈信息？",{icon:3,title:"提示"},function(index){
			layer.close(index);
            saveBackAjax();
		});
	}else{
        saveBackAjax();
	}
}

function saveBackAjax(){
    var backContent = $("#backContent").val().trim();
    var backId = $("#backId").val();
    if(backContent){
        $.ajax({
            type: "POST",
            url:"/system/notice/saveBack",
            data:{
                noticeId: $("#id").val(),
                backContent: backContent,
                id: backId
            },
            dataType:"json",
            success:function(json){
                if (json.flag == '1') {
                    $("#backId").val(json.data.id);
                    layer.msg("反馈成功",{icon:1});
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files2"});
                }else{
                    layer.msg("反馈失败，请刷新重试",{icon:2});
                }
            },
            error:function(){
                layer.msg("反馈失败，请刷新重试",{icon:2});
            }
        });
    }else{
        layer.msg("请填写反馈信息",{icon:0});
    }
}

function iniFileUpload(){
	MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"sys_notice"},
		editOrView:$("#oper").val(),
        maxFileSize:10*1024*1024 //10M
    });
	
	//反馈的附件
	MyFileUpload.init({
    	viewParams: {"tableId":$("#backId").val(),"tableName":"sys_notice_back"},
    	domId: "fileupload2",
    	fileListId: "files2",  //文件列表显示的div
    	maxFileSize:10*1024*1024 //10M
    });
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