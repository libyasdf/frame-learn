$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	
	// 渲染会议室状态
	if($("#opertation").val() != "VIEW"){
		//初始化字典项
	    Dictionary.init({position:"doorNumber",hasSelect:false,mark:"meetingRoomPlan",type:"1",name:"doorNumber",domType:"select"});
	}
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/basicSet/meetingRoom/edit",datas,function (data){
			
			DisplayData.playData({data: data});
			var doorNumber = data.data.doorNumber;
			//加载图片
			checkDoorNumber(doorNumber);
			
			if($("#opertation").val() == "VIEW"){
				console.log(data);
				var meetingroomState = "";
				if(data.data.meetingroomState == '0'){meetingroomState = "可用";}
				if(data.data.meetingroomState == '1'){meetingroomState = "不可用";}
				console.log(data.data.meetingroomState);
				$("#meetingroomState").text(meetingroomState);
			}
			
		});
	}
})

/**
 * 门牌号修改加载对应门牌好的平面图
 * 门牌号选择时判断门牌号的唯一性 and by hlt
 */
$("#doorNumber").change(function(){
	var doorNumber = $("#doorNumber").val();
	$.ajax({
		url:"/zhbg/hygl/basicSet/meetingRoom/getByDoorNum",
		data:{"doorNumber":doorNumber},
		async: false,
		success:function(json){
			if (json.data) {
				if(json.data=='1'){
				var bb=layer.alert('门牌号已存在 请重新选择！',{icon:0,closeBtn:1,
					yes:function(){
						$('#doorNumber option:first').prop("selected","selected");
						layer.close(bb);
						$("#form").data('bootstrapValidator').updateStatus("doorNumber","notEmpty", null).validateField("doorNumber");
					},
					cancel:function(){
						$('#doorNumber option:first').prop("selected","selected");
					}
					}
				)
				}else{
					// 加载平面图
				checkDoorNumber(doorNumber);	
				}
				if(json.data=='-1'){
					layer.alert('查询出错 请联系开发人员！',{icon:0})
					return;
					}
			}
		}
	});
	
});

//判断是否拥有该门牌号的平面图（手动输入的判断方式）
/*function checkDoorNumber(doorNumber){
	$.ajax({
		type: "get",
		url:"/system/config/dictionary/list",
		data:{"mark":"meetingRoomPlan","type":"1","code":doorNumber,"resId":$("#resId").val()},
		async: false,
		success:function(json){
			if (json.data) {
				if(json.data.rows.length != 0){
					var path = "/static/images/hygl/"+doorNumber+".png"
					//加载图片
					$("#img").attr("src",path);
				}else{
					$("#img").attr("src","/static/images/hygl/1701.png");
				}
			}
		}
	});
}*/

// 下拉选加载平面图
function checkDoorNumber(doorNumber){
	if(doorNumber == ""){
		//TODO 默认图片地址
		$("#img").attr("src","/static/images/hygl/1701.png");
	}else{
		var path = "/static/images/hygl/"+doorNumber+".png"
		//加载图片
		$("#img").attr("src",path);
	}
	
	
}

/**
 * 提交表单
 * @returns
 */
function comitForm(){
	isMeetingRoomExist();
}

//保存时判断是否有会议室信息
function isMeetingRoomExist(){
	var doorNumber = $("#doorNumber").val();
	var oper=$("#opertation").val();
	$.ajax({
		url:"/zhbg/hygl/basicSet/meetingRoom/getByDoorNum",
		data:{"doorNumber":doorNumber},
		async: false,
		success:function(json){
			if (json.data) {
				if(oper=='NEW' && json.data=='1'){
				var bb=layer.alert('门牌号已存在 请重新选择！',{icon:0,closeBtn:1,
					yes:function(){
						$('#doorNumber option:first').prop("selected","selected");
						layer.close(bb);
						$("#form").data('bootstrapValidator').updateStatus("doorNumber","notEmpty", null).validateField("doorNumber");
					},
					cancel:function(){
						$('#doorNumber option:first').prop("selected","selected");
					}
					}
				)
				}else{
				 var data = saveForm();
				 if(data){
					layer.msg("保存成功！", {icon: 1});
					//刷新列表
					parent.TableInit.refTable('right_table');
				 }	
				}
				if(json.data=='-1'){
					layer.alert('查询出错 请联系开发人员！',{icon:0})
					return;
					}
			}
		}
	});
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
    	$.ajax({
    		type: "POST",
    		url:"/zhbg/hygl/basicSet/meetingRoom/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json) {
    				application = json.id;
    				$("#id").val(json.id);
    			}
    		}
    	});
    }
    return application;
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
 * 获取应用导航在数据字典中
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
            						 +imageList[i].code+"'><img src='"
            						 +imageList[i].name+"'></label>");
            				 if (i % 2 != 0 && i != imageList.length - 1) {
                                 $("#path").append("</br>");
							 }
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






