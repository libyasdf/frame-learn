var fileTypeId = "7a12c3ecc46d4eb4bb745bd4983fd697";
var workFlowId = "7a12c3ecc46d4eb4bb745bd4983fd697";

$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	
	//根据mark获取一个以code、name为键值对的map，用来根据code取对应的字典名称
	var map = Dictionary.getNameAndCode({mark:"sb",type:"1"});
	var a = map[1];
	var b = map[2];
	
	if($("#opertation").val() != "VIEW"){
	    //初始化数据字典类型
	    Dictionary.init({position:"meetingType",mark:"meeting_type",type:"1",name:"meetingType",domType:"select"});
	    //加载会务服务项
	    httpRequest("get","/zhbg/hygl/basicSet/meetingService/getAllList",datas,function (result){
	    	if(result.flag == "1"){
           	 if(result.data){
           		 var meetingServiceList = result.data;
           		 if (meetingServiceList && meetingServiceList.length > 0) {
           			 for (var i = 0; i < meetingServiceList.length; i++) {
           				 $("#meetingServices").append("<label class='checkbox-inline'>"+
           				 	"<input type='checkbox' name='meetingServices' value='"+ meetingServiceList[i].id+"'> "+meetingServiceList[i].meetingService + "</label>");
           			 }
           		 }
           	 }
            }
	    })
	    
	}
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/meetingApply/edit",datas,function (data){
			if(data.data.meetingStartTime=='0'){
				$("#meetingStartTimeDis").val('上午');
				$("#meetingStartTimeDisr").text('上午');
			}else{
				$("#meetingStartTimeDis").val('下午');
				$("#meetingStartTimeDisr").text('下午');
			}
			if(data.data.meetingEndTime=='0'){
				$("#meetingEndTimeDis").val('上午');
				$("#meetingEndTimeDisr").text('上午');
			}else{
				$("#meetingEndTimeDis").val('下午');
				$("#meetingEndTimeDisr").text('下午');
			}
			DisplayData.playData({data:data});
			
			if($("#opertation").val() == "VIEW"){
				
				//checkbox会议服务
				var meetingServices = data.data.meetingServices.split(",");
				var ar = [];
				$.each(meetingServices, function (i, n) {
					var datas = {"id":n};
					httpRequest("get","/zhbg/hygl/basicSet/meetingService/edit",datas,function (data){
						if(data.data){
							ar.push(data.data.meetingService);
						}
					})
	                	
	            })
				$("#meetingServices").text(ar.join(", "));
				
				//select会议类型
				var meetingType = "";
				if(data.data.meetingType == '0'){meetingType = "一类会议";}
				if(data.data.meetingType == '1'){meetingType = "二类会议";}
				if(data.data.meetingType == '2'){meetingType = "三类会议";}
				$("#meetingType").text(meetingType);
			}
		});
		
		//流程相关（按钮、控件等）的渲染
		if($("#workitemid").val()!=""){
			if($("#opertation").val()=="EDIT"){
				var datas = {
						workflowid:$("#workflowid").val(),
						filetypeid:$("#filetypeid").val(),
						workitemid:$("#workitemid").val(),
						pkValue:$("#id").val(),
						userid:getcookie("userid"),
						deptid:getcookie("deptid"),
						oper:$("#opertation").val(),
                    	ideaArea:$("ideaArea").val()
				};
				//流转中不可修给表单--开始
				//$('form').find('input,textarea,select').not('#idea').prop('readonly',true);
				//流转中不可修给表单--结束
				DisplayData.playWorkFlow("/flowService/getFlowData",datas,$("#opertation").val(),callback);
			}else{//oper='VIEW'
				var datas = {
					type:"1",
					oper:$("#opertation").val(),
					workitemid:$("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
			}
		}else{
			getStartWf();
		}
	}else{
		getStartWf();
		//alert("===");
		var datas = {"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/meetingApply/add",datas,function (data){
			DisplayData.playData({data:data});
		})
		
	}
	
	iniFileUpload();
})


/**
 * 数据字典二级联动
 * 根据省份select初始化城市框
 */
function initCity(){
	var shengMark = $("#sheng option:selected").attr("data-mark");
	Dictionary.init({position:"city",mark:shengMark,type:"1",name:"city",domType:"select"});
}


/**
 * 提交表单
 * @returns
 */
function commitForm(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	if(!$("#id").val()){
    		$("#subflag").val(Config.startflag);
    	}
    	$.ajax({
    		type: "POST",
    		url:"/zhbg/hygl/meetingApply/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json.flag == '1') {
    				res = json.data.id;
    				$("#id").val(json.data.id);
    				$("#subflag").val(json.data.subflag);
    				//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
    				iniFileUpload();
    				  MyFileUpload.saveDocIdToAffix({docId:res,fileListId: "files"});
    			}else {
    				
    			}
    		},
    		error:function(){
    		}
    	});
    	//保存临时意见
    	var tempIdea = $("#idea").val();
    	saveIdeaTemp($("#workitemid").val(),tempIdea);
    }
    return res;
}

/**
 * 提交流程
 */
function commitFlow(){
	var plan = saveForm();
	if(plan!=""){
		var oper = "";
		if($("#workitemid").val()==""){
			oper = "NEW";
		}else{
			oper = "EDIT";
		}
        //获取意见
        var idea = $("#idea").val();
       	var IsNotionShow =  document.getElementById("ideaArea").style.display;
		if(IsNotionShow == "block"){
			if("2"==  document.getElementById("fillmode").value){
                if(idea == "" || idea == null){
                    layer.msg("请填写意见", {icon: 0})
                    return false;
                }
			}
		}
		submitToFlow(oper,this,$("#applyTitle").val(),idea,$("#id").val(),"attr","attr1",$("#workitemid").val(),$("#workflowid").val());
	}
}

/**
 *  工作流回调该方法，用于改变业务数据状态
 * @param subflag 状态位
 * @returns
 */
function updateBusiData(subflag){
	$.ajax({
	    type: "POST",
	    url: "/zhbg/hygl/meetingApply/updateFlag",
	    data:{
	    	id:$("#id").val(),
	    	subflag:subflag
	    },
	    async: false,
	    success:function(data){
	    },
	    error:function(){
	    }
	});
}

/**
 * 初始化文件上传
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"hygl_meeting_apply"},
		editOrView:$("#opertation").val(),
        maxFileSize:5*1024*1024 //5M
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