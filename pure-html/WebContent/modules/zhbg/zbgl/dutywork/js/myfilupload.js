var MyFileUpload = function () {
	
    /**
     * 回显文件列表的生成列表方法
     * @param fileListId
     * @param data
     */
    var echoFile = function (fileListId, data, editOrView, fileFlag) {
        if (editOrView != "VIEW") {
	    	$.each(data.files, function (index, file) {
	    		if(fileFlag){
		    		if(fileListId == file.fileListId){
			    		$('<div/>').addClass("row m-b-5").append(
			                $('<div/>').addClass("col-md-12").append(
			                	$('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;")
			                ).append(
			                	$('<i title="删除" style="cursor:pointer;"/>').addClass("glyphicon glyphicon-remove").click(function (e) {
		                        	MyFileUpload.fileDelete({fileId: file.id},e)
		                        })
			                )
			            ).appendTo('#' + fileListId);
		    		}
	    		}else{
	    			$('<div/>').addClass("row m-b-5").append(
			                $('<div/>').addClass("col-md-12").append(
			                	$('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;")
			                ).append(
			                	$('<i title="删除" style="cursor:pointer;"/>').addClass("glyphicon glyphicon-remove").click(function (e) {
		                        	MyFileUpload.fileDelete({fileId: file.id},e)
		                        })
			                )
			            ).appendTo('#' + fileListId);
	    		}

	        });
        } else {
    		$.each(data.files, function (index, file) {
    			if(fileFlag){
	        		if(fileListId == file.fileListId){
                        $('<div/>').addClass("row m-b-5").append(
                            $('<div/>').addClass("col-md-12").append(
                                $('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;")
                            )
                        ).appendTo('#' + fileListId);
	                	/*$('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;").appendTo('#' + fileListId);*/
	        		}else{
                        $('<div/>').addClass("row m-b-5").append(
                            $('<div/>').addClass("col-md-12").append(
                                $('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;")
                            )
                        ).appendTo('#' + fileListId);
	        			/*$('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;").appendTo('#' + fileListId);*/
	        		}
    			}else{
                    $('<div/>').addClass("row m-b-5").append(
                        $('<div/>').addClass("col-md-12").append(
                            $('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;")
                        )
                    ).appendTo('#' + fileListId);
    				/*$('<a id=' + file.id + ' href="/system/component/affix/download?affixId=' + file.id + '"/>').html(file.name + "&nbsp;&nbsp;&nbsp;").appendTo('#' + fileListId);*/
    			}
	        });
        	
        	
        }
    };
    
    /**
     * 选择文件后回显列表
     */
    var echoFileBefore = function (fileListId, data) {
    	//console.log(data.formData.recordId);
    	$.each(data.files, function (index, file) {
            $('<div/>').addClass("row m-b-5").append(
                $('<div/>').addClass("col-md-12").append(
                	$('<a id="" href=""/>').html(file.name)
                ).append("&nbsp;&nbsp;&nbsp;<span>0%</span>")
            ).appendTo('#' + fileListId);
        });
    };

    /**
     * 初始化文件上传
     * @param json
     */
    var init = function (json) {
    	//console.log(json);
    	/**
         * 上传文件初始化默认参数
         * @type {{domId: string, progressId: string, fileListId: string, uploadUrl: string, viewUrl: string, doneFn: doneFn, progressFn: progressFn}}
         */
        var affixMap = Dictionary.getNameAndCode({mark:"affixSize",type:"1",resId:$("#resId").val()});
        var affixSize =0;
        for(var item in affixMap){
            affixSize = parseInt(item) * 1024 * 1024;
            break;
        }
        //上传类型
        debugger;
        var affixType = Dictionary.getNameAndCode({mark:"affixTypes",type:"1",resId:$("#resId").val()});
        var affixTypes = [];
        for(var item in affixType){
            affixTypes.push(item);
        }
        var arg = {
            domId: "fileupload"  				//上传文件的input框id
            , progressId: "progress"  			//进度条ID
            , fileListId: "files"  				//文件列表显示的div
            , uploadUrl: '/system/component/affix/fileupload'  		//文件上传地址
            , viewUrl: "/system/component/affix/list" 				//只读页地址
            , editOrView: "EDIT"									//EDIT:编辑页回显，VIEW：只读页回显
            , maxFileSize: affixSize					//文件不超过5M
            , fileTypes: affixTypes.join(",")	//文件格式限制
            , viewParams: {}  							//回显请求的额外传参，比如可以传一个业务ID，来加载该业务数据的所有文件列表
            , doneFn: function (e, data, fileListId, pId) {  		//文件上传完成后的回调（不管成功失败）
                var msg = data.result.msg;
                if (data.result.flag === "1") {
                    if (!msg) {
                        msg = "上传成功";
                    }
                    layer.msg(msg, {icon: 1});
                    //把附件ID放回a标签
                	var $aTab = $("a:contains('"+data.files[0].name+"')");
                    $.each(data.result.data.files, function(index,file){
                        $.each($aTab,function (index,item) {
                            if(item.id == ""){
                                item.id=file.id;
                                item.href="/system/component/affix/download?affixId="+file.id ;
                            }
                        })
                    });
                    //var aTabs = $("a:contains('"+data.files[0].name+"')");
                	$aTab.next("span").removeAttr("style").html(
                    	$('<i title="删除" style="cursor:pointer;"/>').addClass("glyphicon glyphicon-remove").click(function (e) {
                        	MyFileUpload.fileDelete({fileId: data.result.data.files[0].id},e)
                        })
                    )
                } else {
                    if (msg) {
                        msg = "上传失败，请重试！";
                    }
                    layer.alert(msg, {icon: 0});
                    var $aTab = $("a:contains('"+data.files[0].name+"')");
                	$aTab.next("span").css("color","red").html("上传失败！")
                    $aTab.parent().parent().remove();
                }
            }
            , progressFn: function (e, data, pId) {  //文件上传进度条回调
            	var $aTab = $("a:contains('"+data.files[0].name+"')");
                var progress = parseInt((data.loaded - data.loaded / 10 ) / data.total * 100, 10);
                $.each($aTab,function (index,item) {
                    if(item.id==""){
                        item.style.color="blue";
                        $("span:contains(0%)").html(progress+"%")
                    }
                })
            	//$aTab.next("span").css("color","blue").html(progress + "%");
            }
            ,changeFn:function (e,data,maxfileSize,fileTypes) {
                var flag = true;
                for(var n =0;n < data.files.length;n++){
                    if(data.files[n].size > maxfileSize){
                        layer.alert("文件不能超过"+maxfileSize/1024/1024+"M", {icon: 0});
                        flag = false;
                    }
                }
                if(fileTypes && fileTypes != ""){
                    var suffix = data.files[0].name.substring(data.files[0].name.lastIndexOf(".") +1);
                    if(fileTypes.indexOf(",")<0){//如果格式限制当中只有一个格式（eg:docx），那么，就意味着只能上传该格式的文档
                        if(fileTypes != suffix){

                            layer.msg("只能上传"+fileTypes+"格式的文件！",{icon:0});
                            flag = false;
                        }
                    }else{
                        //如果想允许多个，用英文逗号分隔（eg：
                        //1. docx,xlsx,jpg,png(用逗号分隔)
                        //2. docx,这种形式的可允许doc及docx，xlsx,同样允许xls及xlsx(这样的话提示信息会稍微有歧义，建议写为doc,docx ||xls,xlsx)）
                        if(fileTypes.indexOf(suffix)<0){

                            layer.msg("只能上传"+fileTypes+"格式的文件！",{icon:0});
                            flag = false;
                        }
                    }

                }
                return flag
            }
        };

        var _arg = $.extend(arg, json);
        //绑定上传文件附件
        $('#' + _arg.domId).fileupload({
            url: _arg.uploadUrl,
            dataType: 'json',
            //singleFileUploads: false,	//支持多文件同时上传
            limitMultiFileUploads:1,
            maxFileSize: _arg.maxFileSize,
            formData:{
            	tableName:_arg.viewParams.tableName,
            	tableId:_arg.viewParams.tableId,
            	fileListId:_arg.fileListId
            },
            change:function(e,data){
               var flag = _arg.changeFn(e,data,_arg.maxFileSize,_arg.fileTypes);
               if(!flag){
                   return flag;
               }
            },
            done: function (e, data) {
                _arg.doneFn(e, data, _arg.fileListId,_arg.progressId);
            },
            progress:function(e, data){//进度条
            	_arg.progressFn(e, data, _arg.progressId);
            },
            send: function (e, data) {
            	echoFileBefore(_arg.fileListId,data);
            }
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
        //如果有回显传参就进行回显
        var aTabs = $("#"+_arg.fileListId).find("a");
        if (JSON.stringify(_arg.viewParams) !== "{}" && aTabs.length == 0){
        	fileView(_arg);
        }
    };
    
    /**
     * 回显文件列表的方法
     * @param json
     */
    var fileView = function (json) {
    	var returnFlag = false;
    	var arg = {
    		fileListId:"files",
    		viewUrl:"/system/component/affix/list",
    		viewParams:{},
    		editOrView:"VIEW",
    		fileFlag:true
    	};
        var _viewArg = $.extend(arg, json);
        $.getJSON(_viewArg.viewUrl, _viewArg.viewParams, function (data) {
            if(data.data){
            	echoFile(_viewArg.fileListId, data.data, _viewArg.editOrView,_viewArg.fileFlag);
            	returnFlag = true;
            }else{
            	returnFlag = false;
            }
        });
        return returnFlag;
    };

    /**
     * 删除文件方法的默认参数
     * @type {{url: string, fileId: string}}
     */
    var delArg = {
        url: "/system/component/affix/delete"
        , fileId: ""
    };

    /**
     * 删除文件的方法
     * @param json
     * @param e
     */
    var fileDelete = function (json, e) {
        var _delArg = $.extend(delArg, json);
        var evt = e ? e : window.event;
        if (evt.stopPropagation) {
            evt.stopPropagation();
        } else {
            evt.cancelBubble = true;
        }
        // console.log(evt);
        //发送ajax请求删除
        $.ajax({
            url: _delArg.url
            , type: "GET"
            , data: {affixId: _delArg.fileId}
            , dataType: "json"
            , async: "false"
            , success: function (result) {
                var msg = result.msg;
                if (result.flag === "1") {
                    //通过event获取要删除的文件项
                	$(evt.currentTarget).parent().parent().remove();
                    //$(evt.path[1]).remove();
                    // console.log($(evt.path[1]));
                    if (!msg) {
                        msg = "删除成功";
                    }
                    layer.msg(msg, {icon: 1});
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
    
    /**
     * 保存业务ID到附件表
     * @param json
     */
    var saveDocIdToAffix = function(json){
    	var saveParam = {
	    	docId: ""	//业务ID
	    	, fileListId: "files"  //文件列表显示的div
	    }
    	
    	var _saveParam = $.extend(saveParam, json);
    	var fileIds = _saveParam.fileListId.split(",");
    	var aTabs = [];
    	$(fileIds).each(function(i,n){
    		var aTab = $("#"+n).find("a");
    		aTabs.push(aTab);
    	})
    	
    	var affixIds = [];
    	if(aTabs.length > 0){
    		for(var i=0;i<aTabs.length;i++){
    			for(var j=0;j<aTabs[i].length;j++){
    				affixIds.push($(aTabs[i][j]).attr("id"));
    			}
    		}
    	}
    	console.log(affixIds);
    	if(affixIds.length > 0){
    		$.ajax({
                url: "/system/component/affix/saveIdToAffix"
                , type: "POST"
                , data: {affixIds: affixIds.join(","),docId:_saveParam.docId}
                , dataType: "json"
                , async: "false"
                , success: function (result) {
                }
                , error: function () {
                }
            })
    	}
    }

    return {
        init: function (json) {
            init(json);
        },
        fileDelete: function (json, e) {
            fileDelete(json, e);
        },
        fileView: function (json) {
            return fileView(json);
        },
        saveDocIdToAffix: function(json){
        	saveDocIdToAffix(json);
        }
    }
}();