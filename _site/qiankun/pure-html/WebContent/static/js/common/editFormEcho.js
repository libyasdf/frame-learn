var EditFormEcho = function () {
    var arg = {
    	domId: null //ID回显存储的隐藏域ID
        , recordId: null//业务ID
        , url: ""
        , tableId: null
        , tableParams: {}
        , fileParams: null
    };

    var echo = function (json) {
        var _arg = $.extend(arg, json);
        var id = _arg.recordId;
        if (id) {
            $("#" + _arg.domId).val(id);//将ID回显
            $.ajax({
                url: _arg.url//请求数据的url
                , data: {id: id}
                , dataType: "json"
                , type: "POST" //请求方式，在有后台后建议开启
                , success: function (result) {
                    if (result.flag === "1") {
                        var result = result.data;
                        //数据回显，这里需要根据需要重写
                        for (variable in result) {
                            var ems = document.getElementsByName(variable);
                            var em;
                            var tagName;
                            if (ems.length > 0) {
                                em = ems[0];
                                tagName = em.tagName;
                            } else {
                                continue;
                            }

                            if (result[variable]) { //是否有该属性
                                var resVar = result[variable];
                                //input输入框
                                if (tagName === "INPUT") {
                                    if (em.type === "checkbox") {//checkbox 复选框
                                        $.each(ems, function (x, n) {
                                            $.each(resVar, function (j, jn) {
                                                if (jn === $(n).val()) {
                                                    $(n).attr("checked", "checked");
                                                }
                                            })
                                        })
                                    } else if (em.type === "text") { //text文本输入框
                                        $(em).val(resVar);
                                    } else if (em.type === "radio") { //单选框
                                        $.each(ems, function (x, n) {
                                            if (resVar === $(n).val()) {
                                                $(n).attr("checked", "checked");
                                            }
                                        })
                                    }
                                } else if (tagName === "SELECT") {
                                    $(em).val(resVar);
                                } else if (tagName === "TEXTAREA") {
                                    $(em).text(resVar);
                                }
                            }
                        }
                        if (_arg.tableId) {
                            //初始化表格
                            //填充表格可以使用bootstrap-table或拼接字符串的方式，
                            // 这里使用bootstrap-table的方式作为参考
                            $("#" + _arg.tableId).bootstrapTable(_arg.tableParams);
                            $("#" + _arg.tableId).bootstrapTable("hideLoading");
                            //初始化表格end
                            //填充表格可以使用bootstrap-table或拼接字符串的方式，
                            // 这里使用bootstrap-table的方式作为参考
                            $("#" + _arg.tableId).bootstrapTable("load", result.table);
                        }
                        if (_arg.fileParams) {
                            //初始化文件上传，文件上传inputid和进度条的id、class如有修改，需要指定参数来初始化
                            MyFileUpload.init({viewParams: _arg.fileParams});
                        }
                        //数据回显 end
                    } else {
                        if ($.trim(result.msg) && result.msg !== "") {
                            layer.alert(result.msg, {icon: 5})
                        } else {
                            layer.alert("加载数据错误，请刷新重试！", {icon: 5})
                        }
                    }
                },
                error: function () {
                    layer.alert("请求数据错误，请刷新重试！", {icon: 5})
                }
            });
        }
    };

    return {
        echo: function (json) {
            echo(json);
        }
    }
}();



