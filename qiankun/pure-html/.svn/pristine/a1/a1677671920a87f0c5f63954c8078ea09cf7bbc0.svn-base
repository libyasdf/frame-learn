$(function () {

    scrollTop.init();

    var theRequest = GetRequest();
    $("#resId").val(regVlaue(theRequest.resId));
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.oper);


	/**
	 * 初始化页面，数据加载、渲染
	 */
    if ($("#id").val() != "") {

        //表单数据渲染
        var datas = { "id": $("#id").val(), "resId": $("#resId").val() };
        httpRequest("get", "/system/config/msgVersion/getById", datas, function (data) {
            console.log("data",data);
            DisplayData.playData({ data: data });
        });

    }else{
        var curDate = getCurrentDate("yyyy-MM-dd");
        $("#creTime").val(curDate);
    }
    iniFileUpload();
})

/**
 * 提交表单
 * @returns
 */
function commitForm() {
    var flag = saveForm();
    if (flag){
        if (flag == "1") {
            layer.msg("保存成功！", { icon: 1 });
            //刷新列表
            parent.TableInit.refTable('right_table');
        } else if(flag == "noAffix"){
            layer.msg("请上传客户端！", { icon: 2 });
        } else if(flag == "moreAffix"){
            layer.msg("一个版本只能上传一个客户端！", { icon: 2 });
        }else{
            layer.msg("保存失败！", { icon: 2 });
        }
    }
}

/**
 * 保存
 */
function saveForm() {
    var res = "";
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    // //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        //判断是否上传附件了
        if(hasAffix() == 1){
            $.ajax({
                type: "POST",
                url: "/system/config/msgVersion/save",
                data: $("#form").serialize(),
                async: false,
                success: function (json) {
                    if (json.flag == '1') {
                        res = json.flag;
                        $("#id").val(json.data.id);
                        //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                        iniFileUpload();
                        MyFileUpload.saveDocIdToAffix({ docId: json.data.id});
                    }
                },
                error: function () {
                }
            });
        }else if(hasAffix() == 0){
            res = "noAffix";
        }else if(hasAffix() > 1){
            res = "moreAffix";
        }
    }
    return res;
}

/**
 * 判断是否上传了附件
 */
function hasAffix(){
    var aTabs = $("#files").find("a");
    return aTabs.length;
}

/**
 * 初始化文件上传
 */
function iniFileUpload() {
    //初始化文件上传
    MyFileUpload.init({
        viewParams: { "tableId": $("#id").val(), "tableName": "message_version" },
        editOrView: $("#opertation").val()
    });
}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val) {
    if (!val) {
        val = "";
    }
    return val;
}
