$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);;
	$("#id").val(regVlaue(theRequest.id));
    $("#superId").val(regVlaue(theRequest.superId));
    $("#partyMember").val(regVlaue(theRequest.partyMember));
    $("#partyMemberId").val(regVlaue(theRequest.partyMemberId));
    $("#partyOrganization").val(regVlaue(theRequest.partyOrganization));
    $("#partyOrganizationId").val(regVlaue(theRequest.partyOrganizationId));
	$("#opertation").val(theRequest.opertation);

    if($("#opertation").val() != "VIEW"){
    	//奖励原因
        Dictionary.init({position:"rewardReason",mark:"dwgl_dygl_jlqk_jlyy",type:"1",name:"rewardReason",domType:"select"});
        //奖励内容
        Dictionary.init({position:"rewardContent",mark:"dwgl_dygl_jlqk_jlnr",type:"1",name:"rewardContent",domType:"select"});
        //批准机关
        Dictionary.init({position:"approvalOrganLevel",mark:"dwgl_dygl_jlqk_pzjg",type:"1",name:"approvalOrganLevel",domType:"select"});
    }
	/**
	 * 初始化页面，数据加载、渲染
	 */
    var id = $("#id").val();
    if(id != "") {
        //表单数据渲染
        var datas = {"id": $("#id").val(), "superId": $("#superId").val(), "resId": $("#resId").val()};
        httpRequest("get", "/djgl/ddjs/dygl/dyjl/edit", datas, function (data) {
            if($("#opertation").val() == "VIEW") {
                //奖励原因
                data.data.rewardReason = Dictionary.getNameAndCode({mark:"dwgl_dygl_jlqk_jlyy",type:"1"})[data.data.rewardReason];
                //奖励内容
                data.data.rewardContent = Dictionary.getNameAndCode({mark:"dwgl_dygl_jlqk_jlnr",type:"1"})[data.data.rewardContent];
				//批准机关
                data.data.approvalOrganLevel = Dictionary.getNameAndCode({mark:"dwgl_dygl_jlqk_pzjg",type:"1"})[data.data.approvalOrganLevel];
            }
            DisplayData.playData({data: data});
        });
    }

	if( $("#opertation").val()=="EDIT"){
		var start = $('#rewardTime').val();
		var end =$('#rewardRevokeTime').val();
		if(start!=""){
			rewardRevokeTime.config.min ={
				year:start.substring(0,4),
				month:start.substring(5,7)-1,
				date:start.substring(8,10),
				hours:0,
				minutes:0,
				seconds:0
			}
		}

		if(end!=""){
			rewardTime.config.max ={
				year:end.substring(0,4),
				month:end.substring(5,7)-1,
				date:end.substring(8,10),
				hours:0,
				minutes:0,
				seconds:0
			}
		}

	}
})

function commitForm(flag) {
	var data = saveForm();
	if (data) {
		layer.msg("保存成功！", {
			icon : 1
		});
		var index = parent.layer.getFrameIndex(window.name);
		/*parent.layer.close(index);*/
		// 刷新列表
		parent.TableInit.refTable('right_table');

	}
}


/**
 * 保存
 */
function saveForm(){
	var res = "";
    var record = ""
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if(bootstrapValidator.isValid()){
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/dygl/dyjl/save",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
                $("#id").val(json.data.id);
                record = json.data.id;
			},
			error:function(){
			}
		});
	}
	console.log(record);
	return record;
}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val||val=="undefined"){
		val = "";
	}
	return val;
}