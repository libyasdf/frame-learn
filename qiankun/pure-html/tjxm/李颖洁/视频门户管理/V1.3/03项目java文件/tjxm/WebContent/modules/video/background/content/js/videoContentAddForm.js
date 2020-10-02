var filePath;
var id;
var isUpdataAffix="0";//是否要更新文件上传表；0表示不更新，1表示需要更新
var isUpdate="0";//0表示未上传;1 表示已上传
var isSave="0"//是否点击过保存按钮，0表示没保存过，1表示保存过
var isSuffix="1";//1表示可以上传封面，0表示不可以上传封面(提示信息是因为文件类型不符导致）
$(function() {
	scrollTop.init();

	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	 id = regVlaue(theRequest.id);
	$("#id").val(id);
	$("#columnId").val(regVlaue(theRequest.columnId));
	$("#opertation").val(regVlaue(theRequest.oper));
	$("#workItemId").val(regVlaue(theRequest.workItemId1));
	
	
    var imagePathObj=getPath11();
	 filePath =imagePathObj.imagePath;
	var con=imagePathObj.con;
	// alert("filePath: "+filePath)
	/**
	 * 初始化页面，数据加载、渲染
	 */
	var columnId = $("#columnId").val();
	var contentId = $("#id").val();
	if (columnId != "") {
		
		var datas = {
			"columnId" : columnId,
			"contentId" : contentId,
			"math" : Math.random(),
			"resId" : $("#resId").val()
		};
		httpRequest("get", "/video/content/queryQx", datas, function(data) {
			
			if ('1' == data.flag) {
				$("#isFbfw").val(data.isFbfw);
				if ('1' == data.isFbfw) {
					$("#fbfwShow").show();
					$("input[type=radio][name='isFBContent'][value='1']").attr(
							"checked", 'checked');//设置选择值为1的
				}
				if ('1' == data.fbButton) {
					$("#fbButton").show();
				}
				if ('1' == data.sendButton) {
					$("#sendButton").show();
				}
				if ('1' == data.passButton) {
					$("#passButton").show();
				}
				if ('1' == data.noPassButton) {
					$("#noPassButton").show();
				}
			}
		});
		//保存时 发布范围的提示
	    httpRequest("get","/video/authority/contentFbZwqx/getAuthority",datas,function (json){
	    		if(json.from == "1"){ //来自于栏目的权限，点保存的时候需要校验是否设置权限
	    			//回显部门权限
	    				putStatus(true);
	    		}
	    	})
	}
	if ($("#id").val() != "") {
		//表单数据渲染
		var datas = {
			"id" : $("#id").val(),
			"resId" : $("#resId").val()
		};
		httpRequest("get", "/video/content/edit", datas, function(data) {
			$("#showTime").val(
					data.data.showStartTime + " - " + data.data.showEndTime);
			DisplayData.playData({
				data : data
			});
			//使用插件调用图片上传
			fishFileInput('image',con,filePath, id);
		});
		//回显上传的视频
		//showFiles第一个参数1表示可以删除资料的文件,第三个参数0表示不需要将学习记录存到数据库
		showFiles("1", $("#id").val(), "0");
		//设置上传图片的input框值
		//$(".file-caption-name").val(con.caption);
	} else {
		fishFileInput('image',{},"", "");
		//$(".file-caption-name").val("");
		//$(".file-caption-icon").remove();
		$("#creDeptName").val(getcookie("deptnm"));
		$("#creDeptId").val(getcookie("deptid"));
		var curDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");
		$("#creTime").val(curDate);
		$("#subflag").val("0");
	}
	// iniFileUpload();
})
var resId = $("#resId").val();

//保存发送范围之前先保存下数据
function saveFbfw() {
	var isFbfw = $("#isFbfw").val();
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		if (isFbfw == "1") {//存在发布范围
			var contentId = $("#id").val();
			if (contentId == "" || contentId == null || contentId == undefined) {
				saveDictionary("", isFbfw, "0");//先保存主表数据
				contentId = $("#id").val();
			}
			var columnId = $("#columnId").val(); //打开权限选择窗口
			MyLayer.layerOpenUrl({
				url : "/modules/video/authority/authorityInfo.html?contentId="
						+ contentId + "&columnId=" + columnId,
				title : "选择发布范围"
			});
		}
	}
}
function fishFileInput(imageUpId,con,path, id) {
	
	//alert("url: "+con.url);
	$("#" + imageUpId).fileinput({
		language : 'zh', // 设置语言
		theme : 'fa',
		uploadUrl : '/video/content/imageUp/imageUpload', // 上传的地址
		enctype: 'multipart/form-data',
		allowedFileExtensions : [ 'jpg', 'png', 'bmp', 'jpeg' ],// 接收的文件后缀
		//minImageWidth: 100, //图片的最小宽度 
		//minImageHeight: 100,//图片的最小高度 
		//maxImageWidth: 1000,//图片的最大宽度 
		//maxImageHeight: 1000,//图片的最大高度 
		//maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
		maxFileSize : 3000,// 上传文件最大的尺寸
		maxFilesNum : 1,//
		overwriteInitial : true,
		 showBrowse:false, //浏览按钮
		dropZoneTitle : "支持文件拖拽到这里",
		dropZoneEnabled : true,// 是否显示拖拽区域
		uploadAsync : true, // 默认异步上传
		showUpload: false, //是否显示上传按钮
		 showRemove : false, //显示移除按钮
		 showClose:false,//不显示关闭按钮
		 showPreview : true, //是否显示预览
		showCaption : false,// 是否显示标题
		previewFileIcon :true,
		browseClass : "btn btn-primary", // 按钮样式
		initialPreviewAsData : true,
		initialPreviewFileType : 'image',
		initialPreview : [ path ], // 初始化url
		initialPreviewConfig : [ {
			caption: con.caption,// 展示的图片名称
			size : con.size,
			width : '600px',// 图片高度
			hight : '800px',
			url :con.url,// 预展示图片的删除调取路径
			key :id,// 可修改 场景2中会用的
			extra :{id:id}
		} ],// 删除参数
	    validateInitialCount : true,
	    fileActionSettings:{
            showRemove: false, //缩略图移除按钮
            showUpload: false //缩略图删除按钮
        },
        previewZoomButtonIcons:{
            fullscreen:''
		},
        previewZoomButtonClasses:{
            fullscreen:''
		},
        previewZoomButtonTitles:{
            fullscreen:'全屏'
		},
		uploadExtraData : function(previewId, index) {
			// 向后台传递id作为额外参数，是后台可以根据id修改对应的图片地址。
			var obj = {};
			obj.tableId = $("#id").val();
			obj.tableName = "video_content";
			obj.path = Config.path;
			obj.fileListId = 'image'
			return obj;
		},
	}).on('filepreupload', function(event, files) { // 上传中
		
		
	}).on("fileuploaded", function(event, data, previewId, index) { // 一个文件上传成功
	//  第二次上传图片时 删除第一个上传的图片
	       console.info("......................................................")
	       console.info(data)
        var fileSuccess = $('.file-preview-success');
        var kvZoomCache = $('.kv-zoom-cache');
        if(fileSuccess.length>=2){
            fileSuccess.eq(0).remove();
            kvZoomCache.eq(0).remove();
        }
		
		// 上传成功给出提示
		if (data) {
			
			var imageid = data.response.data.files[0].id
			$("#imageId").val(imageid);
		}
	}).on("filebatchuploadsuccess", function(event, data) {
		
	}).on("filesuccessremove", function(event, data, previewId, index) {
		// alert("删除成功后回调");
	}).on('filebatchpreupload', function(event, data) {
		// 点击上传之前的验证 add by hlt
	}).on("filepredelete", function(jqXHR) {
		// alert("文件删除之前");
		var abort = true;
		if (confirm("确定要删除图片吗?")) {
			abort = false;
		}
		return abort;
	}).on("filebatchselected", function(event, files) {
		if(files.length==0){
			layer.msg("视频封面只支持\"jpg, png, bmp, jpeg\"的文件！",{icon:2});
			isSuffix='0';
			
			isUpdataAffix="0"
			return;
		}else{
			isSuffix='1';
		}
		isUpdataAffix="1"
		// 选择文件后处理事件 进行校验
		if (($("#id").val() == "" || $("#id").val().length == 0)) {
			$('#image').fileinput('clear');
			layer.msg("请先保存资料基本信息！", {
				icon : 0
			});
			//校验合法性
			$("#form").data('bootstrapValidator').validate();
			//saveDictionary();
			return false;
		}
	})
}

function getPath11() {
	var dataPa = {
		tableId : $("#id").val(),
		tableName : "video_content"
	};
	var obj={};
	var con={};
	var imagePath = "";
	$.ajaxSettings.async = false;
	$.getJSON('/video/content/imageUp/getPicPath', dataPa, function(data) {
		console.info(data)
		if (data.data) {
			if (data.data.files.length > 0) {
				imagePath = data.data.files[0].path;
				imagePath = imagePath.substring(imagePath.indexOf("upload") - 1);
				//设置initialPreviewConfig 属性
				con.caption=data.data.files[0].name;
				con.size=data.data.files[0].fileSize;
				con.url='/video/content/imageUp/deleteInServer';
				con.key=id;
				con.extra={id:id}
			}
		}
		if (imagePath == "" || imagePath == undefined) {
			imagePath = "/modules/video/background/content/noPic.gif";
		}
	});
	$.ajaxSettings.async = true;
	obj.con=con;
	obj.imagePath=imagePath
	return obj;
}
//保存发布前的校验
function checkSaveDictionary(subflag, isTs, status) {
	//发布时，视频和图片是必传的
	if (subflag == '2') {
		var canPublish ="1"; //1表示可以发布；0表示不可以发布
		/*if ($("imageId").val()=='') {
			alert()
			canPublish ="0";
			layer.msg("发布时请上传视频封面！", {
				icon : 0
			});
			return;
		}*/
		var videoLen = $("a[fileidnum]").length
		if(videoLen<=0){
			canPublish ="0";
			layer.msg("请上传视频！", {
				icon : 0
			});
			return;
		}
		//var filesCount=$(".file-caption-name").val();
		/*if((filePath=='/modules/video/background/content/noPic.gif')&& filesCount==0){
			layer.msg("发布时请上传视频封面！", {
				icon : 0
			});
			return;
		}*/
		
		/*if ($("#imageId").val() == '' || $("#imageId").val() == undefined) {
			layer.msg("发布时请上传视频封面！", {
				icon : 0
			});
			return;
		}*/
		if(canPublish =="1"){
			saveDictionary(subflag, isTs, status);
		}
		
	} else {
		isSave="1"
		saveDictionary(subflag, isTs, status);
	}

}

// 保存，发布
function saveDictionary(subflag, isTs, status) {
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		var isFbfw = $("#isFbfw").val();
		if (status == null || status == undefined) {
			status = $("#status").val();
		}
		var workItemId = $("#workItemId").val();
		if (isFbfw == '1'
				&& status != '1'
				&& status != '0'
				&& (workItemId == null || workItemId == undefined || workItemId == "")) {//有发布范围 并且没有保存过发布范围或者不是点击选择发布范围时
			layer.msg("请先保存发布范围！", {
				icon : 0
			});
			return false;
		}
		//var html = editor.html();
		//editor.sync();
		//html = $('#editor_id').val();
		var showTime = $("#showTime").val();
		if (showTime != "") {
			var showTimeArray = showTime.split(" - ");
			$("#showStartTime").val(showTimeArray[0]);
			$("#showEndTime").val(showTimeArray[1]);
		}
		if (subflag != "" && subflag != "0" && subflag != null
				&& subflag != undefined) {
			var curDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");
			$("#fbTime").val(curDate);
			$("#subflag").val(subflag);
		}
		
		Loading.open();
		$.ajax({
					type : "POST",
					url : "/video/content/saveFrom?resId=" + resId,
					data : $("#form").serialize(),
					async : false,
					success : function(json) {
						Loading.clear();
						$("#id").val(json.data.id);
						$("#subflag").val(json.data.subflag);
						var filesCount = $('#image').fileinput('getFilesCount');
						//alert(isUpdataAffix)
						if(subflag != "2" && (filesCount==0 || isUpdataAffix=="0")){
							//没有上传图片的保存
							if (isTs != '1') {//点击选择发布范围的时候不弹出提示语句
								layer.msg("保存成功！", {
									icon : 1
								});
							}
							//点击的是保存按钮，并且未上传图片或没有更换图片都不需要保存文件信息
							
							
							parent.refreshPage();
							return;
						}else if(subflag == "2" && isUpdataAffix=="0" && isSave=="1" && $("#imageId").val()!='' && $("#imageId").val()!=null && $("#imageId").val() != undefined){
							layer.msg("发布成功！", {
								icon : 1
							});
							$("#fbButton").hide();
							parent.refreshPage();
							
							
							
						}else{
							if ($("#imageId").val()=='' && isUpdataAffix=="0") {
								var msg=""
								if(isSuffix=="0"){
									msg="请重新上传视频封面！";
								}else{
									msg="请上传视频封面！";
								}
								
								layer.msg(msg, {
									icon : 0
								});
								return;
							}
							//alert("zoul ")
							isUpdataAffix="0" //除非切换图片，否则不上传到服务器
							if ('1' == json.flag) {
								parent.TableInit.refTable('right_table1');
								parent.TableInit.refTable('right_table2');
								//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
								/*iniFileUpload();
								MyFileUpload.saveDocIdToAffix({
									docId : json.data.id,
									fileListId : "files,files2"
								});
								ImgMyFileUpload.saveDocIdToAffix({
									docId : json.data.id,
									imageId : "imageId"
								});*/
								var msg = "保存成功! ";
								//触发插件开始上传
								$('#image').fileinput('upload');
								if (subflag == "2") {//删除待办
									msg = "发布成功！";
									//setTimeout(
												if (workItemId != ""
														&& workItemId != null
														&& workItemId != undefined) {
													var datas = {
														"id" : workItemId,
														"resId" : $("#resId").val()
													};
													httpRequest(
															"get",
															"/sysWaitNoflowController/deleteWaitNoflow",
															datas,
															function(data) {
																if ('1' == data.flag) {
																	parent
																			.refreshPage();
																	parent
																			.refreshPage("daiban");
																	console
																			.log("删除待办成功！");
																}
															});
												}
												/*var index = parent.layer
														.getFrameIndex(window.name);
												parent.layer.close(index);*/
								}
								if (isTs != '1') {//点击选择发布范围的时候不弹出提示语句
									layer.msg(msg, {
										icon : 1
									});
									if (subflag == "2") {
										$("#fbButton").hide();
									}
								}
							}
						}
						
						
					
					},
					error : function(data) {
						layer.msg("保存失败!", {
							icon : 2
						})
					}
				});
		Loading.clear();
	}
}

function sendflow() {
	
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		var videoLen = $("a[fileidnum]").length
		if(videoLen<=0){
			canPublish ="0";
			layer.msg("请上传视频！", {
				icon : 0
			});
			return;
		}
		if(isSuffix=='0'){
			layer.msg("请重新上传视频封面！", {
				icon : 0
			});
			return;
		}else if ($("#imageId").val()=='' && isUpdataAffix=="0") {
			layer.msg("请上传视频封面！", {
				icon : 0
			});
			return;
		}
		
		var isFbfw = $("#isFbfw").val();
		var status = $("#status").val();
		var workItemId = $("#workItemId").val();
		if (isFbfw == '1'
				&& status != '1'
				&& status != '0'
				&& (workItemId == null || workItemId == undefined || workItemId == "")) {//有发布范围 并且没有保存过发布范围或者不是点击选择发布范围时
			layer.msg("请先保存发布范围！", {
				icon : 0
			});
			return false;
		}
		//发送时判断图片有没有保存 如果保存则不用再次保存
		if(isUpdataAffix=="1" && isUpdate=="0"){
			
			$('#image').fileinput('upload');
		}
		$("#sendButton").hide();
		$("#save").hide();
		
		// var html = editor.html();
		//editor.sync();
		// html = $('#editor_id').val();
		var showTime = $("#showTime").val();
		if (showTime != "") {
			var showTimeArray = showTime.split(" - ");
			$("#showStartTime").val(showTimeArray[0]);
			$("#showEndTime").val(showTimeArray[1]);
		}
		Loading.open();
		$.ajax({
					type : "POST",
					url : "/video/content/sendFlow?resId=" + resId,
					data : $("#form").serialize(),
					async : false,
					success : function(json) {
						Loading.clear();
						if ('1' == json.flag) {
							$("#id").val(json.data.id);
							$("#subflag").val(json.data.subflag);
							parent.TableInit.refTable('right_table1');
							parent.TableInit.refTable('right_table2');
							//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
							
							/*iniFileUpload();
							MyFileUpload.saveDocIdToAffix({
								docId : json.data.id,
								fileListId : "files,files2"
							});
							ImgMyFileUpload.saveDocIdToAffix({
								docId : json.data.id,
								imageId : "imageId"
							});*/
							var msg = "发送成功,接收人:"
									+ json.waitNoflowEntity.receiveUserName;
							layer.msg(msg, {
								icon : 1
							});
							
							//setTimeout(
										if ($("#workItemId").val() != ""&& $("#workItemId").val() != null&& $("#workItemId").val() != undefined) {
											var datas = {
												"id" : $("#workItemId").val(),
												"resId" : $("#resId").val()
											};
											httpRequest(
													"get",
													"/sysWaitNoflowController/deleteWaitNoflow",
													datas,
													function(data) {
														if ('1' == data.flag) {
															parent
																	.refreshPage("daiban");
															console
																	.log("删除待办成功");
														}
													});
										}
										/*var index = parent.layer
												.getFrameIndex(window.name);
										parent.layer.close(index);*/

						}
					},
					error : function(data) {
						layer.msg("发送失败!", {
							icon : 2
						})
					}
				});
	}
}
/**
 * 审核不通过
 */
function noPass(subflag) {
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		//var html = editor.html();
		//editor.sync();
		// html = $('#editor_id').val();
		var showTime = $("#showTime").val();
		if (showTime != "") {
			var showTimeArray = showTime.split(" - ");
			$("#showStartTime").val(showTimeArray[0]);
			$("#showEndTime").val(showTimeArray[1]);
		}
		if (subflag != "" && subflag != null && subflag != undefined) {
			$("#subflag").val(subflag);
		}
		Loading.open();
		$.ajax({
			type : "POST",
			url : "/video/content/noPass?resId=" + resId,
			data : $("#form").serialize(),
			async : false,
			success : function(json) {
				Loading.clear();
				if ('1' == json.flag) {
					var msg = "退回成功,接收人:"
							+ json.waitNoflowEntity.receiveUserName;
					layer.msg(msg, {
						icon : 1
					});
					$("#id").val(json.data.id);
					$("#subflag").val(json.data.subflag);
					parent.TableInit.refTable('right_table1');
					parent.TableInit.refTable('right_table2');
					//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
					/*iniFileUpload();
					MyFileUpload.saveDocIdToAffix({
						docId : json.data.id,
						fileListId : "files,files2"
					});
					ImgMyFileUpload.saveDocIdToAffix({
						docId : json.data.id,
						imageId : "imageId"
					});*/
					$('#image').fileinput('upload');
					//setTimeout(
						parent.refreshPage("daiban");
						var datas = {
							"id" : $("#workItemId").val(),
							"resId" : $("#resId").val()
						};
						httpRequest("get",
								"/sysWaitNoflowController/deleteWaitNoflow",
								datas, function(data) {
									if ('1' == data.flag) {
										parent.refreshPage("daiban");
										console.log("删除待办成功");
									}
								});
						/*var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);*/
				}
			},
			error : function(data) {
				layer.msg("发送失败!", {
					icon : 2
				})
			}
		});
	}
}

function iniFileUpload() {
	MyFileUpload.init({
		viewParams : {
			"tableId" : $("#id").val(),
			"tableName" : "video_content"
		},
		editOrView : $("#opertation").val(),
		read : true,
		download : false
	});
	ImgMyFileUpload.init({
		viewParams : {
			"tableId" : $("#id").val(),
			"tableName" : "video_content"
		},
		editOrView : $("#opertation").val(),
		//maxFileSize: 150 * 1024,//150K
		domId : "imgfileupload",
		fileListId : "image",
		imageId : "imageId",
		path : Config.path
	});
}
//关闭当前窗口
function closeIfram() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
/**
 * 保存权限范围后，回调置成功状态位
 */
function putStatus(status) {
	if (status) {
		$("#status").val("1");
	}
}

function showFbfw(value) {
	if (value.value == '1') {
		$("#fbfwShow").show();
	} else {
		$("#fbfwShow").hide();
	}
	$("#isFbfw").val(value.value);
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