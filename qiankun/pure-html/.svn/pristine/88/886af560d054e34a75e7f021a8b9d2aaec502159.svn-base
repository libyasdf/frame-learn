var ImgMyFileUpload = function () {
    /**
     * 初始化文件上传
     * @param json
     */
    var init = function (json) {
        var affixMap = Dictionary.getNameAndCode({mark:"affixSize",type:"1",resId:$("#resId").val()});
        var affixSize =0;
        for(item in affixMap){
            affixSize = parseInt(item) * 1024 * 1024;
            break;
        }
        var arg = {
            domId: "fileupload"  				//上传文件的input框id
            , fileListId: "image"  				//显示的div
            , imageId:"imageId"               //记录附件id的隐藏域
            , uploadUrl: '/system/component/affix/uploadToServerPath'  		//文件上传地址
            , viewUrl: "/system/component/affix/list" 				//只读页地址
            , editOrView: "EDIT"									//EDIT:编辑页回显，VIEW：只读页回显
            , maxFileSize: affixSize				//文件不超过5M
            , viewParams: {}  							//回显请求的额外传参，比如可以传一个业务ID，来加载该业务数据的所有文件列表
            , doneFn: function (e, data, fileListId,imageId) {  		//文件上传完成后的回调（不管成功失败）
                var msg = data.result.msg;
                if (data.result.flag === "1") {
                    if (!msg) {
                        msg = "上传成功";
                    }
                    layer.msg(msg, {icon: 1});
                    //把附件ID放回隐藏域中
                    $("#"+imageId).val(data.result.data.files[0].id);
                	var $aTab = $("#"+fileListId);
                	var savePath =data.result.data.files[0].path;
                    savePath = savePath.substring(savePath.indexOf("upload")-1);
                    $aTab.attr("src",savePath);
                } else {
                    if (msg) {
                        msg = "上传失败，请重试！";
                    }
                    layer.alert(msg, {icon: 0});
                }
            }
            ,changeFn:function (e,data,maxfileSize) {
                var flag = true;
                for(var n =0;n < data.files.length;n++){
                    if(data.files[n].size > maxfileSize){
                        layer.alert("文件不能超过"+maxfileSize/1024+"K", {icon: 0});
                        flag = false;
                    }
                }
                var flag1 = /.(jpg|png|gif|bmp)$/.test(data.files[0].name)?true:(function() {
                    layer.alert("图片格式仅支持jpg、png、gif、bmp格式，且区分大小写",{icon:7});
                    return false;
                })();
                return flag && flag1;
            }
        };
        
        var _arg = $.extend(arg, json);
        //绑定上传文件附件
        $('#' + _arg.domId).fileupload({
            url: _arg.uploadUrl,
            dataType: 'json',
            singleFileUploads: false,	//支持多文件同时上传
            limitMultiFileUploads:1,
            maxFileSize: _arg.maxFileSize,
            acceptFileTypes:_arg.acceptFileTypes,
            formData:{
            	tableName:_arg.viewParams.tableName,
            	tableId:_arg.viewParams.tableId,
            	fileListId:_arg.fileListId,
            	path:_arg.path
            },
            change:function(e,data){
                var flag = _arg.changeFn(e,data,_arg.maxFileSize);
                if(!flag){
                    return flag;
                }
            },
            done: function (e, data) {
                var oldImageId = $("#"+_arg.imageId).val();
                if(oldImageId !=""&&oldImageId!=null && oldImageId != undefined){
                    fileDelete({fileId:oldImageId});//先删除之前的
                }
                _arg.doneFn(e, data, _arg.fileListId,_arg.imageId);
            }
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
        //如果有回显传参就进行回显
        if (JSON.stringify(_arg.viewParams) !== "{}"){
            fileView(_arg);
        }
    };
    
    /**
     * 回显文件列表的方法
     * @param json
     */
    var fileView = function (json) {
    	var arg = {
    		fileListId:"image",
    		viewUrl:"/system/component/affix/list",
    		viewParams:{},
    		editOrView:"VIEW"
    	};
        var _viewArg = $.extend(arg, json);
        $.getJSON(_viewArg.viewUrl, _viewArg.viewParams, function (data) {
            if(data.data){
            	echoFile(_viewArg.fileListId, data.data, _viewArg.imageId);
            }
        });
    };
    /**
     * 回显文件列表的生成列表方法
     * @param fileListId
     * @param data
     */
    var echoFile = function (fileListId, data, imageId) {
        $.each(data.files, function (index, file) {
            if(fileListId == file.fileListId){
                $("#"+imageId).val(file.id);
                var $aTab = $("#"+fileListId);
                var savePath =file.path;
                savePath = savePath.substring(savePath.indexOf("upload")-1);
                $aTab.attr("src",savePath);
            }
        });
    };

    /**
     * 删除文件方法的默认参数
     * @type {{url: string, fileId: string}}
     */
    var delArg = {
        url: "/system/component/affix/deleteInServer"
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
            ,imageId:"imageId"   //图片附件Id隐藏域
	    }
    	
    	var _saveParam = $.extend(saveParam, json);
    	var affixIds = $("#"+_saveParam.imageId).val();
        $.ajax({
            url: "/system/component/affix/saveIdToAffix"
            , type: "POST"
            , data: {affixIds: affixIds,docId:_saveParam.docId}
            , dataType: "json"
            , async: "false"
            , success: function (result) {
            }
            , error: function () {
            }
        })
    }

    return {
        init: function (json) {
            init(json);
        },
        fileDelete: function (json, e) {
            fileDelete(json, e);
        },
        fileView: function (json) {
            fileView(json);
        },
        saveDocIdToAffix: function(json){
        	saveDocIdToAffix(json);
        }
    }
}();