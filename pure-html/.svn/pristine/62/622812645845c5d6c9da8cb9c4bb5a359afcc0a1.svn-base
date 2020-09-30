$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	if(regVlaue(theRequest.addOrUpdate)=="add"){
		$("#partyOrganizationId").val(theRequest.orgId);
		$("#partyOrganizationName").val(decodeURI(regVlaue(theRequest.orgName)));
		$("#partyName").val(decodeURI(regVlaue(theRequest.partyName)));
		$("#partyId").val(theRequest.partyId);

		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		if($("#annual").val()==""){
			$("#annual").val(year)
		}
		if($("#monthval").val()==""){
			$("#monthval").val(month+"月")
		}

		edit($("#partyId").val(),year,month);
	}
	/**
	 * 初始化页面，数据加载、渲染
	 */
	/*if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dzz/dfgl/edit",datas,function (data){
			DisplayData.playData({data:data});
		});

	}*/
})
function edit(partyId,annual,month) {
	var datas = {"partyId":partyId,"annual":annual,"month":month};
	httpRequest("get","/djgl/ddjs/dzz/dfgl/edit",datas,function (data){
		$("#approvedMonthPartyfee").val(data.data.approvedMonthPartyfee);
		$("#currentPaymentAmount").val(data.data.currentPaymentAmount);
		DisplayData.playData({data:data});
		var theRequest = GetRequest();
		$("#partyOrganizationName").val(decodeURI(regVlaue(theRequest.orgName)));
	});
}


function commitForm() {
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
	var datas = {"partyId":$("#partyId").val(),"annual":$("#annual").val(),"monthval":$("#monthval").val()};
	httpRequest("get","/djgl/ddjs/dzz/dfgl/queryFee",datas,function (data) {
		if (data.data.id != null && data.data.id != "" && data.data.id != $("#id").val()) {
			layer.msg(data.data.partyName +data.data.annual+ "年" + data.data.monthval + "月党费已交！", {icon: 7});
			res= false;
			}else{

			var bootstrapValidator = $("#form").data('bootstrapValidator');
			//手动触发验证
			bootstrapValidator.validate();
			var myDate = new Date();
			/*var time = myDate.toLocaleDateString().split('/').join('-');
			 $("#reportingTime").val(time);*/
			if(bootstrapValidator.isValid()){
				$.ajax({
					type: "POST",
					url: "/djgl/ddjs/dzz/dfgl/saveDfgl",
					data:$("#form").serialize(),
					async: false,
					success:function(json){
						if (json.flag == '1') {
							res = json.flag;
							$("#id").val(json.data.id);
							MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files"});
							layer.msg("保存成功！", {
								icon : 1
							});
							parent.TableInit.refTable('right_table');
						}
					},
					error:function(){
					}
				});
				//保存临时意见
				/*var tempIdea = $("#idea").val();
				 saveIdeaTemp($("#workitemid").val(),tempIdea)*/;
			}
		}
		return res;
	});

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