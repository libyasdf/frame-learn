

$(function() {
	
	// 获取参数
	var theRequest = GetRequest();
	$("#applyId").val(regVlaue(theRequest.id));
	$("#resId").val(theRequest.resId);
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#applyId").val() != "") {
		// 修改
		// 表单数据渲染
		var datas = {
			"applyId" : $("#applyId").val()
		};
	
			httpRequest("get", "/zhbg/hygl/notice/edit1", datas, function(data) {
				//根据反馈类别 判断是否显示反馈类型和最晚反馈时间 0:否  1：反馈 
				var meetingName = data.data.meetingName;
				$("#noticeName").val(meetingName)
				if(data.data.isFankui=='1'){
					//需要反馈
					$("#fanKui").show()
					$("#fanKuiTime").show()
				}
				//回显会议室安排列表
				//$("#tablePlan").show();
				//$("#time").show();
				showMeetingRoom(data.data.applyId);
				DisplayData.playData({
					data : data
				});
			});
	} 
	iniFileUpload();
})




/**
 * 提交表单
 */
function comitForm() {
	$("#status").val("0");
	var data = saveForm();
	if (data) {
		layer.msg("保存成功！", {
			icon : 1
		});
		// 刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存数据
 */
function saveForm() {
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	$('.summernote', $("#form")).each(function() {
        var $this = $(this);
        if (!$this.summernote('isEmpty')) {
            var editor = "<input type='hidden' name='" + $this.attr("name") + "' value='" + $this.summernote('code') + "' />";
            $("#form").append(editor);
        } else {
        	layer.msg("请填写通知内容", {
				icon : 0
			})
            return false;
        }

    });
	if (bootstrapValidator.isValid()) {
		if($("#attendDeptJu").val()=="" && $("#attendDept").val()==""){
			layer.msg("请至少选择一项参会单位", {
				icon : 0
			})
			return false;
		}
		$.ajax({
			type : "POST",
			url : "/zhbg/hygl/notice/saveForm",
			data : $("#form").serialize(),
			async : false,
			success : function(json) {
				if (json) {
					res = json.id;
					$("#id").val(json.id);
					$("#subflag").val(json.subflag);
					//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
    				iniFileUpload();
    			    MyFileUpload.saveDocIdToAffix({docId:res,fileListId: "files"});
				}
			}
		});
		//保存临时意见
		var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(), tempIdea);
	}
	return res;
}

/**
 * 发布通知
 */
function publishForm() {
    var index11;
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		if($("#attendDeptJu").val()=="" && $("#attendDept").val()==""){
			layer.msg("请至少选择一项参会单位", {
				icon : 0
			})
			return false;
		}
	//Loading.open();
	$("#status").val("1");
	if($('#attendDeptJu').val()){
		var deptNames="";
		var deptIds="";
       if($('#attendDept').val()){
    	 deptNames= ($('#attendDept').val()+","+$('#attendDeptJu').val()).split(",");	
		}else{
		 deptNames=$('#attendDeptJu').val().split(",");	
		}
		if($('#attendDeptId').val()){
		   deptIds = ($('#attendDeptId').val()+","+$('#attendDeptJuId').val()).split(",");
		}else{
			deptIds = $('#attendDeptJuId').val().split(",");
		}
	}else{
		var deptNames = ($('#attendDept').val()).split(",");
  		var deptIds = ($('#attendDeptId').val()).split(",");
	}
	  		//var deptNames = ($('#attendDept').val()+","+$('#attendDeptJu').val()).split(",");
	  		//var deptIds = ($('#attendDeptId').val()+","+$('#attendDeptJuId').val()).split(",");
	  		//alert(JSON.stringify(deptIds));
	  		$.ajax({
	  			url:"/zhbg/hygl/notice/hasConfidentiUser",
	  			data:{"deptIds":JSON.stringify(deptIds),"deptNames":JSON.stringify(deptNames)},
				dataType:"json",
	  			async:true,
	  			beforeSend:function(request){
  				index11 = layer.load(2,{shade: [0.5, '#393D49'],content: '请稍候',
  				success: function(layero){
  				layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
  				} }); 
	  			},
	  			success:function(data1){
	  				//Loading.clear();
	  				if(data1.flag=="1"){
  	  				//layer.confirm('确认发布吗?点击确定按钮后将发布通知，不能再修改，同时会关闭本页面！',{icon:3,title:'发布'},function(){
  	  				var plan = saveForm();
  	  				//alert(plan);
  	  				if (plan) {
  	  				layer.close(index11);
  	  				layer.msg("发布成功！", {
  	  							icon : 1
  	  						});
  	  				$('#save').css("display", "none")//将保存设置为隐藏 
					$('#sendFLow').css("display", "none")//将反馈设置为隐藏 
					//$('#close').css("display", "block")
  	  					// 刷新列表
  	  					parent.TableInit.refTable('right_table');
  	  				}
	  				}else{
	  					layer.close(index11);
	  					var names = data1.deptNames.split(",");
	  					layer.alert('发布失败！'+names+'等单位没有配置处室或部门收发人员角色，请联系系统管理员进行配置后再试！',
	  							{icon:0})
	  							//layer.close(index);
	  				}
	  			}
	  		});
	}
	
}

/**
 * 初始化文件上传HYGL_MEETING_NOTICE
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"HYGL_MEETING_NOTICE"},
		editOrView:$("#opertation").val(),
        maxFileSize:5*1024*1024 //5M
    });

}

/**
 * 空值设置
 * 
 * @param val
 * @returns
 */
function regVlaue(val) {
	if (!val) {
		val = "";
	}
	return val;
}