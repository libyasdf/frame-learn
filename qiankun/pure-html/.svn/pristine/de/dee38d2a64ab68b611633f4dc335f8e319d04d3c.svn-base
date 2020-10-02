var fileTypeId = "c14a1d4fda524c4fb88de64000d0c4b8";
var workFlowId = "c14a1d4fda524c4fb88de64000d0c4b8";

$(function(){
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
			httpRequest("get", "/zhbg/kqgl/wc/edit", datas, function(data) {
				DisplayData.playData({data: data});
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
						oper:$("#opertation").val()
				};
				//流转中不可修给表单--开始
				//$('form').find('input,textarea,select').not('#idea').prop('readonly',true);
				//流转中不可修给表单--结束
				DisplayData.playWorkFlow("/flowService/getFlowData",datas,$("#opertation").val(),callback);
				if($("#creUserId").val()==getcookie("userid")){
					$("#ideaArea").css("display","none")
					$("#noApproval").css("display","none")
					$("#approval").css("display","none")
				}
			}else{//oper='VIEW'
				var datas = {
					type:"1",
					oper:$("#opertation").val(),
					workitemid:$("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
			}
			//初始化意见域，回显临时意见
			initIdeaArea($("#workitemid").val());
			//获取正式意见，渲染页面
			getIdeas();
		}else{
			//getStartButton();
		}
	}else{
		//新增
		$("#creUserName").val(getcookie("usernm"))
		$("#applicantUnitName").val(getcookie("deptname"));
		var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#applicationTime").val(curDate)
		
		
				
	}
})

/**
 * 获取正式意见、回显
 */
function getIdeas(){
	//返回数据格式参照：/mock/formalIdeas.json
	var res = getFormalIdeas($("#id").val(),fileTypeId);
	if(res.length > 0){
		for(var i=0;i<res.length;i++){
			if(res[i].ideaList.length > 0){
				for(var j=0;j<res[i].ideaList.length;j++){
					//var time = new Date(jsonArr[i].ideaList[j].ideatime);
					var textarea = document.getElementsByName(res[i].name);
					if(res[i].ideaList[j].userid.trim() != getcookie("userid").trim()){
						$(textarea).text(res[i].ideaList[j].idea);
					}
				}
			}
		}
	}
}

/**
 * 调用工作流配置的按钮
 * @returns
 */
function getStartButton(){
	var oper = "NEW";
	var startdatas = {
    	workflowid:$("#workflowid").val(),
    	filetypeid:$("#filetypeid").val(),
    	oper:oper
    };
	DisplayData.playWorkFlow("/workflow/getStartWfButton",startdatas,oper);
}

/**
 * 提交表单
 * @returns
 */
function comitForm(){
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
	var info = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	 bootstrapValidator.validate();
	 if(bootstrapValidator.isValid()){
		 if(!$("#id").val()){
				$("#subflag").val(Config.startflag);
		 }
		 $.ajax({
				type: "POST",
				url:"/zhbg/kqgl/wc/saveForm",
				data:$("#form").serialize(),
				async: false,
				success:function(json){
					if (json) {
						info = json.id;
						$("#id").val(json.id);
						$("#subflag").val(json.subflag);
						
					}
				}
			});
		//保存临时意见
		 var tempIdea = $("#idea").val();
	    	saveIdeaTemp($("#workitemid").val(),tempIdea);
	 }
	
	return info;
}

/**
 * 提交流程
 */
function comitFlow(){
	var creUserName = $("#creUserName").val();
	var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
	
	var info = saveForm();
	if(info!=""){
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
		var title = creUserName + "于" + curDate + "发出的外出申请";
		if(!$("#goOutTitle").val()){
			$("#goOutTitle").val(title);
		}
		submitToFlow(oper,this,$("#goOutTitle").val(),idea,$("#id").val(),"attr","attr1",$("#workitemid").val(),$("#workflowid").val());
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
	    url: "/zhbg/kqgl/wc/updateFlag",
	    data:{
	    	id:$("#id").val(),
	    	subflag:subflag
	    },
	    async: false,
	    success:function(data){
	    	parent.TableInit.refTable('right_table');
	    }
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

//求出外出时长
function change(dom){
	var goOutStartDate=$("#goOutStartDate").val()
	var goOutEndDate=$("#goOutEndDate").val()
	var startAmOrPm=$("#startAmOrPm").val()
	var endAmOrPm=$("#endAmOrPm").val()
	var startDate = new Date(Date.parse(goOutStartDate));
	var endDate = new Date(Date.parse(goOutEndDate));
	if(goOutStartDate!='' && goOutEndDate!=''  ){
		if(Date.parse(startDate)>Date.parse(endDate)){
			layer.msg("结束日期不得大于开始日期！", {icon: 0});
			//alert("结束日期不得大于开始日期")
			$("#"+dom).val("")
			return ;
		}
		
	}
	if(goOutStartDate=='' || goOutEndDate=='' || startAmOrPm=='' || endAmOrPm=='' ){
		return ;
	}
 if(Date.parse(startDate)==Date.parse(endDate)){
		if(startAmOrPm<endAmOrPm){
			layer.msg("结束日期不得大于开始日期！", {icon: 0});
			//alert("结束日期不得大于开始日期")
			$("#"+dom).val("")
		}else{
			$.post("/zhbg/kqgl/wc/getGoOutTime", {startDate:goOutStartDate, endDate:goOutEndDate,startAmOrPm:startAmOrPm,endAmOrPm:endAmOrPm },function(data){
				//alert(data)
				$("#goOutLongTime").val(data)
			} );
		}
	}else{
		$.post("/zhbg/kqgl/wc/getGoOutTime", {startDate:goOutStartDate, endDate:goOutEndDate,startAmOrPm:startAmOrPm,endAmOrPm:endAmOrPm },function(data){
			$("#goOutLongTime").val(data)
		} );
	}
}