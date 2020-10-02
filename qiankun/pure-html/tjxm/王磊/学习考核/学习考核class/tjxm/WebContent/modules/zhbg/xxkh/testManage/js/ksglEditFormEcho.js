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
                        //基本信息数据回显 end
                      
                        //根据需求自定义回显start
                        var tableId = "";//人员列表回显的tableId
                        var unitName = ""
                        var timeRange = result.data.startTime+" - "+result.data.endTime;
                    	$("#timeRange").val(timeRange);
                    	//判断是否上报考试人员
                        if($("#inlineRadio2").is(":checked")){
                        	$("#inlineRadio1").prop("disabled",true);//“否”按钮，不能点击
                        	$(".isCheck").unbind('click');
                        	//显示是的列表
                            $("#two").css("display","block");
                            $(".reportTestUser").css("display","block");
                            if(result.data.state=="1"){//判断是否已提交
                            	$("#state").val(result.data.state);
                            	$("#submit").hide();//隐藏提交按钮
                            	$("#didNotReport").css("display","block");//显示未上报部门
                            	$("#hasBeenReported").css("display","block");//显示已上报部门
                            	$("#unitName2").attr("disabled",true);//处室不能再选择
                            	$("#requirement").attr("readOnly",true);//人员要求不能再修改
                            	_arg.tableId = "right_table2";
                            	$("#paperOne").hide();//试卷不能再选择
                            	$("#files2").show();
                            	MyPaperUpload.init({//回显试卷只读状态
                        			fileListId:"files2",
                        			viewUrl:"/zhbg/xxkh/testmanage/getPapers?rdm="+Math.random(),
                        			viewParams:{testId:id},
                        			editOrView:"VIEW"
                        		});
                            	 //考试人员回显
                            	refreshTable(_arg.tableId);
                            }else{
                            	$("#paperOne").show();
                            	$("#submit").show();
                        		MyPaperUpload.init({//试卷列表回显
                        			fileListId:"files",
                        			viewUrl:"/zhbg/xxkh/testmanage/getPapers?rdm="+Math.random(),
                        			viewParams:{testId:id},
                        			editOrView:"EDIT"
                        		});
                            }
                            $("#unitId2").val(result.data.testChuShiIds);
                            $("#unitName2").val(result.data.testChuShiNames);
                            unitName = $("#unitName2").val();
                        }else if($("#inlineRadio1").is(":checked")){
                        	$("#paperOne").show();
                        	//是否上报考试人员：“是”按钮，不能点击
                        	$("#inlineRadio2").prop("disabled",true);
                        	$(".isCheck").unbind('click');
                        	//显示否的列表
                        	$("#one").css("display","block");
                            $(".reportTestUser2").css("display","block");
                           	$("#submit").hide();
                           	$("#unitId").val(result.data.testChuShiIds);
                            $("#unitName").val(result.data.testChuShiNames);
                            unitName = $("#unitName").val();
                            _arg.tableId = "right_table";
                            count = 0;
                            //判断职务职级是否都选择了。如果是则选中全选
                            var flag= true;
                            /*$("input[name='dutyIds']").each(function(){
         					   flag=flag&$(this).prop("checked");
         				   });
         				   $("#inlineCheckboxAll").prop("checked",flag);*/
         				   flag = true;
         				   $("input[name='levelIds']").each(function(){
         					   flag=flag&$(this).prop("checked");
         				  	});
         				   $("#inlineCheckboxAll2").prop("checked",flag);
                          //试卷列表回显
                    		MyPaperUpload.init({
                    			fileListId:"files",
                    			viewUrl:"/zhbg/xxkh/testmanage/getPapers?rdm="+Math.random(),
                    			viewParams:{testId:id},
                    			editOrView:"EDIT"
                    		});
                    		 //考试人员回显
                    		refreshTable(_arg.tableId);
                        }else if($("#inlineRadio1:not(:checked)")&&$("#inlineRadio2:not(:checked)")){//没有选择是或否
                        	//试卷列表回显
                        	$("#paperOne").show();
                    		MyPaperUpload.init({
                    			fileListId:"files",
                    			viewUrl:"/zhbg/xxkh/testmanage/getPapers?rdm="+Math.random(),
                    			viewParams:{testId:id},
                    			editOrView:"EDIT"
                    		});
                        }
                        //根据需求自定义回显end
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



