var MyPaperUpload = function () {
	
    /**
     * 回显试卷列表的生成列表方法
     * @param fileListId 
     * @param data
     */
    var echoFile = function (fileListId, data, editOrView) {
    	$('#'+fileListId).html('');
        if (editOrView != "VIEW") {
	    	$.each(data.rows, function (index, result) {
                $('<div/>').addClass("col-md-10").append(
                	$('<a title="查看试卷信息" style= "color:blue" id=' + result.id + ' href = "javascript:void(0)" onclick="MyLayer.layerOpenUrl({url:\'/modules/zhbg/xxkh/paperManage/paperReadonly.html?id='+result.paperId+'\',width:\'90%\',height:\'98%\'});"/>').html(result.name + "&nbsp;&nbsp;&nbsp;")
                ).append(
            		$('<span/>').append(
        				$('<i title="删除" style="cursor:pointer;margin-left:10px;"/>').addClass("glyphicon glyphicon-remove").click(function (e) {
        					MyPaperUpload.paperDelete({paperId: result.id},e)
                        })	
            		)
                ).appendTo('#' + fileListId);
                });
        } else {
    		$.each(data.rows, function (index, result) {
    			$('<span/>').append($('<a title="查看试卷信息" style= "color:blue" id=' + result.id + ' href = "javascript:void(0)" onclick="MyLayer.layerOpenUrl({url:\'/modules/zhbg/xxkh/paperManage/paperReadonly.html?id='+result.paperId+'\',width:\'90%\',height:\'98%\'});"/>').html(result.name + "&nbsp;&nbsp;&nbsp;")
                ).appendTo('#' + fileListId);
	        });
        }
    };
    
    /**
     * 回显试卷列表的方法
     * @param json
     */
    var init = function (json) {
    	var returnFlag = false;
    	var arg = {
    		fileListId:"files",
    		viewUrl:"/zhbg/xxkh/testmanage/getPapers",
    		viewParams:{},
    		editOrView:"VIEW",
    		isChange:"NO"
    	};
    	var _viewArg = $.extend(arg, json);
    	//获取试卷列表
        $.getJSON(_viewArg.viewUrl, _viewArg.viewParams, function (data) {
            if(data.flag ==="1"){
            	console.log("以下获取的试卷列表：");
            	console.log(data.data.rows);
            	if(data.data.rows.length==0){
            		if(_viewArg.isChange=="NO"){
            			$("#fullScore").val("");
            		}else{
            			$("#fullScore").val("").change();
            			$("#passScore").change();
            		}
            	}else{
            		if(data.data.rows[0].paperState=="0"){
            			$("#fullScore").val("");
            			layer.msg('已选择的试卷不可用，请重新选择试卷！', {icon: 0});
            		}else{
            			//渲染试卷列表
            			echoFile(_viewArg.fileListId, data.data, _viewArg.editOrView);
            			//渲染试卷总分
            			if(_viewArg.isChange=="NO"){
            				$("#fullScore").val(data.data.rows[0].fullScore);
            			}else{
            				$("#fullScore").val(data.data.rows[0].fullScore).change();
            				$("#passScore").change();
            			}
            		}
            	}
            	returnFlag = true;
            }else{
            	if(_viewArg.isChange=="NO"){
        			$("#fullScore").val("");
        		}else{
        			$("#fullScore").val("").change();
        			$("#passScore").change();
        		}
            	returnFlag = false;
            }
        });
        return returnFlag;
    };

    /**
     * 删除试卷方法的默认参数
     * @type {{url: string, id: string}}
     */
    var delArg = {
        url: "/zhbg/xxkh/testmanage/deletePaper?rdm="+Math.random()
        , id: ""
    };

    /**
     * 删除试卷的方法
     * @param json
     * @param e
     */
    var paperDelete = function (json, e) {
        var _delArg = $.extend(delArg, json);
        var evt = e ? e : window.event;
        if (evt.stopPropagation) {
            evt.stopPropagation();
        } else {
            evt.cancelBubble = true;
        }
        //发送ajax请求删除
        $.ajax({
            url: _delArg.url
            , type: "GET"
            , data: {id: _delArg.paperId}
            , dataType: "json"
            , success: function (result) {
                var msg = result.msg;
                if (result.flag === "1") {
                    //通过event获取要删除的文件项
                	$(evt.currentTarget).parent().parent().remove();
                    //$(evt.path[1]).remove();
                    if (!msg) {
                        msg = "删除成功";
                    }
                    layer.msg(msg, {icon: 1});
                    $("#fullScore").val("").change();
                    $("#passScore").change();
                } else {
                    if (!msg) {
                        msg = "删除失败，请重试";
                    }
                    layer.alert(msg, {icon: 0});
                }
            }
            , error: function () {
                layer.alert("请求失败，请重试！", {icon: 0});
            }
        })
    };
    
    
    return {
        init: function (json) {
            init(json);
        },
        paperDelete: function (json, e) {
            paperDelete(json, e);
        },
        openFile:function(json){
        	openFile(json);
        }
    }
}();


