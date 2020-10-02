$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
    $("#superId").val(regVlaue(theRequest.superId));
	$("#opertation").val(theRequest.opertation);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){

		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dygl/dyxx/outEdit",datas,function (data){
			DisplayData.playData({data:data});
		});

	}

})

function commitForm(flag) {
//	$("#subflag").val(flag);
	//console.log($("#subflag").val()+"a"+flag)
	var data = saveForm();

	if (data) {

		layer.msg("保存成功！", {
			icon : 1
		});
//		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
		// 刷新列表
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
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/dygl/dyxx/outSave",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				parent.MyLayer.layerOpenUrl({
                    url: '/modules/dwgl/dygl/zzgxjsx/zzgxjsxAipFrom.html?transfer='+encodeURI(encodeURI($("#transfer").val()))+'&partyId='+ $("#superId").val(),
                    title: "党员组织关系转出"
                })
                parent.TableInit.refTable('right_table');
				MyLayer.closeOpen();
			},
			error:function(){
			}
		});
	}
	console.log(res);
	return res;
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