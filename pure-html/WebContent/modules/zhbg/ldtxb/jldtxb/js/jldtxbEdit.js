//流程文件storage.js中需要该变量回调方法
//var api = frameElement.api, W=api.opener;
$(function(){
    var theRequest = GetRequest();
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.oper);
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        var datas = {"id":$("#id").val()};
        if($("#opertation").val()=="VIEW"){	//只读页渲染
            httpRequest("get","/jldtxb/edit",datas,function (data){
                DisplayData.playReadonly({data: data});
            });
        }else{
            DisplayData.playEdit({
                recordId: $("#id").val(),//业务ID
                url: "/jldtxb/edit"
            })
        }
    }
})

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
	editFunction(basePath+"/workflow/getStartWfButton",startdatas,"1",oper);
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
//	if(!$("#id").val()){
	//	$("#subflag").val(Config.startflag);
//	}
	var plan = "";
	$.ajax({
		type: "POST",
		url:"/jldtxb/saveForm",
		data:$("#form").serialize(),
		async: false,
		success:function(json){
			if (json) {
				plan = json.id;
				$("#id").val(json.id);
				$("#subflag").val(json.subflag);
			}
		}
	});
	return plan;
}

/**
 * 提交流程
 */
function comitFlow(){
	if(autoCheckForm(document.getElementById("form"))){
		var plan = saveForm();
		if(plan!=""){
			var workflowid = "";
			if($("#workflowid").val()==""){
				workflowid = "filetypeid";
			}else{
				workflowid = "workflowid";
			}
			if($("#workitemid").val()==""){
				saveFormAndToFlow('NEW',this,"","","id","attr","attr1","workitemid",workflowid);
			}else{
				saveFormAndToFlow('EDIT',this,"","","id","attr","attr1","workitemid",workflowid);
			}
		}
	}
}

/**
 *  工作流回调该方法，用于改变业务数据状态
 * @param subflag 状态位
 * @returns
 */
function updateSubFlag(subflag){
	$.ajax({
	    type: "POST",
	    url: basePath+"/taskplan/updateFlag",
	    data:{
	    	id:$("#id").val(),
	    	subflag:subflag
	    },
	    async: false,
	    success:function(data){
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