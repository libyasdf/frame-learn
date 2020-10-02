$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if ($("#id").val() != "") {

		// 表单数据渲染
		var datas = {
			"id" : $("#id").val(),"resId":$("#resId").val()
		};
		httpRequest("get", "/diningMenu/edit", datas, function(data) {
			DisplayData.playData({
				data : data
			});
		});
	}
	// 初始化文件上传
})

/**
 * 提交菜谱表单
 * 
 * @returns
 */
function commitForm() {
	if (!$("#id").val()) {
		$("#state").val("0");
	}
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    // 手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        if(isExist()){
            layer.msg("当前日期食谱已经存在！", {
                icon : 0
            });
        } else if (save()){
			layer.msg("保存成功！", {
				icon : 1
			});
			// 刷新列表
			parent.TableInit.refTable('right_table');
        } else {
            layer.msg("保存失败！", {
                icon : 0
            });
		}
    }else{
        layer.msg("请填写食谱数据！", {
            icon : 0
        });
    }
}

/**
 * 判断某日期的菜谱是否已存在
 * true 存在，false 不存在
 */
function isExist() {
	var exist = false;
    var datas = {
        "id" : $("#id").val(),
        "date":$("#dateMenu").val()
    };
    httpRequest("get", "/diningMenu/isExist", datas, function(data) {
        if(data.flag == "0"){
            exist = true;
        }
    });
    return exist;
}

/**
 * 保存
 */
function save () {
	var flag = false;
    $.ajax({
        type : "POST",
        url : "/diningMenu/saveForm",
        data : $("#form").serialize(),
        async : false,
        success : function(json) {
            if (json.flag == '1') {
                $("#id").val(json.data.id);
                $("#state").val(json.data.state);
                flag = true;
            }
        },
        error : function() {
			flag = false;
        }
    });
    return flag;
}

/**
 * 发布菜谱
 */
function commitFlow() {
	$("#state").val("1");

    var bootstrapValidator = $("#form").data('bootstrapValidator');
    // 手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        if(isExist()){
            layer.msg("当前日期食谱已经存在！", {
                icon : 0
            });
        } else if (save()){
            layer.msg("发布成功！", {
                icon : 1
            });
            // 刷新列表
            parent.TableInit.refTable('right_table');
        } else {
            layer.msg("发布失败！", {
                icon : 0
            });
        }
    }else{
        layer.msg("请填写食谱数据！", {
            icon : 0
        });
    }
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