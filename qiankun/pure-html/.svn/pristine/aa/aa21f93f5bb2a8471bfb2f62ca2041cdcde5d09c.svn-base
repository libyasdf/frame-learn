$(function() {
	// 获取参数
	var theRequest = GetRequest();
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	$("#resId").val(theRequest.resId);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {
		// 修改
		// 表单数据渲染
		var datas = {
			"id" : $("#id").val(),
			"resId" : $("#resId").val()
		};
		httpRequest("get", "/zhbg/kqgl/absenteeism/edit", datas, function(data) {
				DisplayData.playData({
					data : data
				});
			});
	} else {
		// 起草
		$("#creUserName").val(getcookie("usernm"))
		$("#creUserId").val(getcookie("userid"))
		$("#applicantUnitName").val(getcookie("deptname"));
		var curDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#applicationTime").val(curDate);
	}
})
/**
 * 提交表单
 */
function commitForm() {
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
	// 手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		$.ajax({
			type : "POST",
			url : "/zhbg/kqgl/absenteeism/saveForm",
			data : $("#form").serialize(),
			async : false,
			success : function(json) {
				if (json) {
					res = json.id;
					$("#id").val(json.id);
					$("#subflag").val(json.subflag);

				}
			}
		});
		// 保存临时意见
		var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(), tempIdea);
	}
	return res;
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
