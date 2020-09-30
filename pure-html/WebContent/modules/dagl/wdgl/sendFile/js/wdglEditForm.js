
$(function() {
	//日期自动补零
	$(".auto").blur(function(){
		 var _val = $(this).val();
   		 if(_val.length<8){
   			switch (_val.length)
   			{
   			case 0:
   			  _val ="00000000";
   			  break;
   			case 1:
   			 _val =_val+"0000000";
   			  break;
   			case 2:
   				_val =_val+"000000";
   			  break;
   			case 3:
   				_val =_val+"00000";
   			  break;
   			case 4:
   				_val =_val+"0000";
   			  break;
   			case 5:
   				_val =_val+"000";
   			  break;
   			case 6:
   				_val =_val +"00";
   			  break;
   			case 7:
   				_val = _val+"0";
   			  break;
   			}
   			$(this).val(_val).change();
   		 }
	});
	var theRequest = GetRequest();
	//资源id
	var resId = theRequest.resId;
	$("#resId").val(theRequest.resId);
	//获取数据字典
	Dictionary.init({
		mark:'04',
		type:'1',
		position:"securityClass",
		domType:"select", 
	 	hasSelect:true});
	
	Dictionary.init({
		mark:"dagl_fileType",
		type:'1',
		position:"type",
		domType:"select", 
	 	hasSelect:true});
	Dictionary.init({
		mark:"dagl_urgency_degree",
		type:'1',
		position:"urgencyDegree",
		domType:"select", 
		hasSelect:true});
	Dictionary.init({
		mark:"dagl_file_type",
		type:'1',
		position:"fileType",
		domType:"select", 
		hasSelect:true});
	//文件基本信息id
	var id = theRequest.id;
	$("#id").val(theRequest.id);
	if(id !="" && id!= null){
		//渲染文件基本信息
		EditFormEcho.echo({
			domId: "id" //ID回显存储的隐藏域ID
				, recordId: id//业务ID
				, url: "/dagl/wdgl/sendFile/edit"
		});
		
	}
});

/**
 * 保存
 */
function saveForm(){
	var par = getParams();
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    		$.ajax({
	    		type: "POST",
	    		url:"/dagl/wdgl/sendFile/save",
	    		data:par,
	    		success:function(json){
	    			if (json.flag == '1') {
	    				//保存文件id
	    				$("#id").val(json.data.id);
	    				//保存文件标题
	    				$("#preTitle").val(json.data.title);
	    				layer.msg("保存成功！", {icon: 1});
	    				//刷新列表
	    	    		parent.TableInit.refTable('right_table');
	    			}else {
	    				layer.msg("保存失败！", {icon: 0});
	    			}
	    		},
	    		error:function(){
	    		}
	    	});
    }
}

/**
 * 获取表单参数
 */
function getParams(){
	//获取表单信息
	var params = {
			"id":$("#id").val(),//文件id
			"title":$("#title").val(),//文件标题
			"fileNum":$("#fileNum").val(),//文件编号（文号）
			"pageNum":$("#pageNum").val(),//页数
			"quantity":$("#quantity").val(),//份数
			"createdDate":$("#createdDate").val(),//成文日期
			"fileTime":$("#fileTime").val(),//收文日期
			"fileDept":$("#fileDept").val(),//来文单位
			"securityClass":$("#securityClass option:selected").text(),//密级
			"type":$("#type").val(),//文件类型
			"serialNum":$("#serialNum").val(),//文件编号
			"fileType":$("#fileType").val(),//文种
			"zhusUnit":$("#zhusUnit").val(),//主送单位
			"barcode":$("#barcode").val(),//条码编号
			"urgencyDegree":$("#urgencyDegree").val(),//紧急程度
			"note":$("#note").val()//备注
			};
	return params;
}

/**
 * 存加
 */
function saveFormAndAdd(){
	var par = getParams();
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	$.ajax({
    		type: "POST",
    		url:"/dagl/wdgl/sendFile/save",
    		data:par,
    		success:function(json){
    			if (json.flag == '1') {
    				//文件id清空
    				$("#id").val("");
    				//清空表单
    				document.getElementById("form").reset();
    				$("#note").val("");
    				//验证销毁重构
			        $("#form").data('bootstrapValidator').resetForm();
    				//保存文件标题
    				$("#preTitle").val(json.data.title);
    				layer.msg("保存成功！", {icon: 1});
    				//刷新列表
    	    		parent.TableInit.refTable('right_table');
    			}else {
    				layer.msg("保存失败！", {icon: 0});
    			}
    		},
    		error:function(){
    		}
    	});
    }
}







