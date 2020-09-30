$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	//渲染图片选择
	showImage("cygj",1);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/system/config/utilLink/edit",datas,function (data){
			DisplayData.playData({data: data});
			if($("#opertation").val() == "VIEW"){
				if(data.data.path){
					$("#showPath").attr("class",data.data.path+' iconStyle');
				}
				getAffixList("sys_util_link",$("#id").val());
			}
		});
	}
	//初始化附件上传
	iniFileUpload();
})


/**
 * 提交表单
 * @returns
 */
function comitForm(){
	var data = saveForm();
	if(data === "1"){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}else if(data === "3"){
		layer.alert('已存在此标题常用工具，请修改后重试！',{icon:0,title:'警告'})
	}
}

/**
 * 保存
 */
function saveForm(){
	var application = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	//TODO 如果没有id
    	/*if(!$("#id").val()){
    		$("#subflag").val(Config.startflag);
    	}*/
    	$.ajax({
    		type: "POST",
    		url:"/system/config/utilLink/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json) {
    				application = json.flag;
    				if(json.flag === "1"){
        				$("#id").val(json.data.id);
        				iniFileUpload();
        			    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files"});
    				}
    			}
    		}
    	});
    }
    return application;
}

/**
 * 初始化文件上传
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({viewParams: {"tableId":$("#id").val(),"tableName":"sys_util_link"},editOrView:$("#opertation").val()});
}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}

/**
 * 获取常用工具在数据字典中
 * @param mark
 * @param type
 */
function showImage(mark,type){
	 $.ajax({
         type:"get",
         url:"/system/config/dictionary/getListByMark",
         data:{
         	"mark":mark,
         	"type":type
         },
         dataType:"json",
         async:false,
         success:function(data){
             if(data.flag == "1"){
            	 if(data.data){
            		 var imageList = data.data;
            		 if (imageList && imageList.length > 0) {
            			 for (var i = 0; i < imageList.length; i++) {
            				 $("#path").append("<label class='radio-inline'><input type='radio'  name='path' value='"
            						 +imageList[i].code+"'><i class='"+imageList[i].code+" iconStyle' title='"+imageList[i].name+"'></i></label>");
            			 }
            		 }
            	 }
             }
         },
         error:function(){
         	layer.msg("加载字典项异常！请刷新重试！",{icon:2});
         }
     })
}

/**
 * 只读页面展示附件
 * @param tableName
 * @param tableId
 */
function getAffixList(tableName,tableId){
	$.ajax({
        type:"get",
        url:"/system/component/affix/list",
        data:{
        	"tableName":tableName,
        	"tableId":tableId
        },
        dataType:"json",
        async:false,
        success:function(data){
          if(data.flag == "1"){
           	 if(data.data.files){
           		 var affixList = data.data.files;
           		 if (affixList && affixList.length > 0) {
           			 for (var i = 0; i < affixList.length; i++) {
           				 $("#affixlist").append("<span><i title='下载' style='cursor:pointer;' class='glyphicon glyphicon-download-alt' onclick='downloadAffix(\""+affixList[i].id+"\")'></i>&nbsp;&nbsp;&nbsp;<a id='"+affixList[i].id+"' title='下载' href='/system/component/affix/downloadInServer?affixId="+affixList[i].id+"'>"+affixList[i].name+"</a></span><br/>");
           			 }
           		 }
           	 }
            }
        },
        error:function(){
        	layer.msg("加载数据异常！请刷新重试！",{icon:2});
        }
    })
}
/**
 * 
 */
function downloadAffix(id){
	window.location.href = "/system/component/affix/downloadInServer?affixId="+id
}






