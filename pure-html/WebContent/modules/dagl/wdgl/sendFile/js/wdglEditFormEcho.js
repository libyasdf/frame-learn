var EditFormEcho = function () {
    var arg = {
    	domId: null //ID回显存储的隐藏域ID
        , recordId: null//业务ID
        , url: ""
        , tableId: ""
        , tableParams: {}
        , fileParams: null
    };

    
    
    var theRequest = GetRequest();
	//资源id
	var resId = theRequest.resId;
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
                    	var data = result.data;
                    	
                        //数据回显，这里需要根据需要重写
                        for (variable in data) {//variable为属性名
                            var ems = document.getElementsByName(variable);
                            var em;
                            var tagName;//标签名
                            if (ems.length > 0) {
                                em = ems[0];
                                tagName = em.tagName;
                            } else {
                                continue;
                            }

                            if (data[variable]) { //是否有该属性
                                var resVar = data[variable];
                                //input输入框
                                if (tagName === "INPUT") {
                                	if (tagName === "INPUT") {
                                        if (em.type === "checkbox") {//checkbox 复选框
                                            $.each(ems, function (x, n) {
                                                $.each(resVar.split(","), function (j, jn) {
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
                        $("#securityClass").find("option").each(function(){
                        	if($(this).text()==data.securityClass){
                        		$(this).prop("selected",true);
                        	};
                        })
                        //基本信息数据回显 end
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
            return echo(json);
        }
    }
}();



