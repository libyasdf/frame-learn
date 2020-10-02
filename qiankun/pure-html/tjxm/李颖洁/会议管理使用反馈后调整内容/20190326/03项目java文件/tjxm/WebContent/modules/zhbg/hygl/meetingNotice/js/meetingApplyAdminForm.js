var fileTypeId = "7a12c3ecc46d4eb4bb745bd4983fd697";
var workFlowId = "7a12c3ecc46d4eb4bb745bd4983fd697";

$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#filetypeid").val(fileTypeId);
	$("#workflowid").val(workFlowId);
	$("#workitemid").val(theRequest.workItemId);
	$("#opertation").val(theRequest.oper);
	
	
	//根据mark获取一个以code、name为键值对的map，用来根据code取对应的字典名称
	var map = Dictionary.getNameAndCode({mark:"sb",type:"1"});
	var a = map[1];
	var b = map[2];
	
	if($("#opertation").val() != "VIEW"){
	    //初始化数据字典类型
	    Dictionary.init({position:"meetingType",mark:"meeting_type",type:"1",name:"meetingType",domType:"select"});
	    //加载会务服务项
	    httpRequest("get","/zhbg/hygl/basicSet/meetingService/getAllList",datas,function (result){
	    	if(result.flag == "1"){
           	 if(result.data){
           		 var meetingServiceList = result.data;
           		 if (meetingServiceList && meetingServiceList.length > 0) {
           			 $("#meetingServices").append("<label class='checkbox-inline'><input type='checkbox'  id='checkAll' >全选 </label>");
           			 for (var i = 0; i < meetingServiceList.length; i++) {
           				 $("#meetingServices").append("<label class='checkbox-inline'>"+
           				 	"<input type='checkbox' name='meetingServices' class='a' value='"+ meetingServiceList[i].id+"'> "+meetingServiceList[i].meetingService + "</label>");
           			 }
           		 }
           	 }
            }
	    })
	    
	}
	
	//会务服务的全选功能
	$("#meetingServices").on("click", "#checkAll",function() { 
		var flagV = this.checked;
		$("input[name='meetingServices']").prop("checked", flagV);
		
	});
	
	$("#meetingServices").on("click", ".a",function() { 
		var len = $("#meetingServices input[name='meetingServices']").length;
		var len1 = $("#meetingServices input[name='meetingServices']:checked").length;
		var flagV = this.checked;
		if(len==len1){
			
			$("#checkAll").prop("checked", flagV);
		}else{
			
			$("#checkAll").prop("checked", false);
		}
		
		
		
	});
	
	
	//绑定删除会议室的事件
	function bindDelect(){
        $('.delect').on('click', function(){
            var tr = $(this).parents('tr');
            var id=tr.attr("data-id");
            console.log(id)
            $.post("/zhbg/hygl/meetingRoomUseInfo/deletById", { id: id },
            	function(data){
            		if(data.flag=="1"){
            			$(tr).remove();
            		}else{
            			// alert('删除失败')
					}
            	}
            );
        });
	}

	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/meetingApply/edit",datas,function (data){
			
			DisplayData.playData({data:data});
			
			if($("#opertation").val() == "VIEW"){
				
				//checkbox会议服务
				var meetingServices = data.data.meetingServices.split(",");
				var ar = [];
				$.each(meetingServices, function (i, n) {
					var datas = {"id":n};
					httpRequest("get","/zhbg/hygl/basicSet/meetingService/edit",datas,function (data){
						if(data.data){
							ar.push(data.data.meetingService);
						}
					})
	                	
	            })
				$("#meetingServices").text(ar.join(", "));
				
				//select会议类型
				var meetingType = "";
				if(data.data.meetingType == '0'){meetingType = "一类会议";}
				if(data.data.meetingType == '1'){meetingType = "二类会议";}
				if(data.data.meetingType == '2'){meetingType = "三类会议";}
				$("#meetingType").text(meetingType);
			}
			//修改页面控制全选选中
			var len = $("#meetingServices input[name='meetingServices']").length;
			var len1 = $("#meetingServices input[name='meetingServices']:checked").length;
			if(len==len1){
				$("#checkAll").prop("checked", true);
			}
		});
		
		
		
		//流程相关（按钮、控件等）的渲染
		if($("#workitemid").val()!=""){
			if($("#opertation").val()=="EDIT"){
				var datas = {
						workflowid:$("#workflowid").val(),
						filetypeid:$("#filetypeid").val(),
						workitemid:$("#workitemid").val(),
						pkValue:$("#id").val(),
						userid:getcookie("userid"),
						deptid:getcookie("deptid"),
						oper:$("#opertation").val(),
                    	ideaArea:$("ideaArea").val()
				};
				//流转中不可修给表单--开始
				//$('form').find('input,textarea,select').not('#idea').prop('readonly',true);
				//流转中不可修给表单--结束
				DisplayData.playWorkFlow("/flowService/getFlowData",datas,$("#opertation").val(),callback);
			}else{//oper='VIEW'
				var datas = {
					type:"1",
					oper:$("#opertation").val(),
					workitemid:$("#workitemid").val()
				};
				DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
			}
		}else{
			getStartWf();
		}
		
		//加载设置的会议室信息
		var data={"applyId":$("#id").val()}
		var SelectedList = [];
		$.post("/zhbg/hygl/meetingRoomUseInfo/getByApplyId", data, function(data){
			if(data.flag=="1"){
				var list = data.data;
                sendChildData =  data.data;
				console.log('回显会议室信息列表')
				for(var i=0;i<list.length;i++){
					var trTime = list[i].useTime;
                    var startTime = trTime.split('-')
					var trMin  = startTime[0].split(":")[0];
                    var rtMax = startTime[1].split(":")[0];

					var dom = '<tr data-id="' + list[i].id + '" data-index="'+ (i+1) +'" data-mrid="' + list[i].meetingroomId + '"  data-time-max="'+ rtMax +'"  data-time-min="'+ trMin +'"> ' +
						'<td width="10%">'+ (i+1) +'</td> ' +
						'<td width="20%">'+ list[i].useDate +'</td> ' +
						'<td width="15%">'+ list[i].weekDate +'</td> ' +
						'<td>'+ list[i].meetingroomName +'</td> ' +
						'<td>'+ list[i].useTime +'</td> ' +
						'<td width="15%" ><span class="delect glyphicon glyphicon-trash p-l-10" title="删除" style="cursor: pointer" data-index="'+ (i+1) +'" id="'+ list[i].id +'"></span></td> ' +
	                '</tr>'
	                $("#table_list").append(dom)
                    bindDelect();
				}
			}
		});

	}else{
		getStartWf();
		//alert("===");
		var datas = {"resId":$("#resId").val()};
		/*httpRequest("get","/zhbg/hygl/meetingApply/add",datas,function (data){
			DisplayData.playData({data:data});
		})*/
		
		
	}
	
	iniFileUpload();
	
	
	
})
/**
 * 数据字典二级联动
 * 根据省份select初始化城市框
 */
function initCity(){
	var shengMark = $("#sheng option:selected").attr("data-mark");
	Dictionary.init({position:"city",mark:shengMark,type:"1",name:"city",domType:"select"});
}


/**
 * 提交表单
 * @returns
 */
function commitForm(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	if(!$("#id").val()){
    		$("#subflag").val(Config.approvalflag);
    	}
    	var childList = [];
    	//获取子表的数据
    	
    	$("#table_list tr").each(function(index,dom){
    		var dom={};
    		dom.id = $(this).attr("data-id");
    		dom.meetingRoomid = $(this).attr("data-mrid");
    		dom.useDate = $(this).find("td:eq(1)").text()
    		dom.weekDate = $(this).find("td:eq(2)").text()
    		dom.useTime = $(this).find("td:eq(4)").text()
    		dom.meetingroomName = $(this).find("td:eq(3)").text();
    		childList.push(dom)
    	})
    	
    	$("#meetingRoomInfo").val(JSON.stringify(childList))
    	$.ajax({
    		type: "POST",
    		url:"/zhbg/hygl/meetingApply/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json.flag == '1') {
    				res = json.data.id;
    				$("#id").val(json.data.id);
    				$("#subflag").val(json.data.subflag);
    				//初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
    				iniFileUpload();
    				  MyFileUpload.saveDocIdToAffix({docId:res,fileListId: "files"});
    			}else {
    				
    			}
    		},
    		error:function(){
    		}
    	});
    }
    return res;
}

/**
 * 提交流程
 */
function commitFlow(){
	var meetingTime = $("#meetingTime").val().substring(0,19);
	if(meetingTime<curDate){
		layer.msg("会议的开始时间必须是今天之后的日期，请重新选择会议的起止时间！", {
			icon : 0
		});
		return;
	}
	var trlength = $("#table_list tr").length;
	if(trlength==0){
		layer.msg("请先选择会议室！", {
			icon : 0
		});
		return;
	}
	var flg="1";//可以进行发布
	
	
	var paramArry=[];
	var param={applyId:$("#id").val(),paramArry:paramArry};
	var index=0;
	 $("#table_list tr").each(function(index,dom){
		 var temObj = {};
		 temObj.meetingRoomid =$(this).attr("data-mrid")
		 var useDate =$(this).find("td:eq(1)").text();
		 var useTime =$(this).find("td:eq(4)").text();
		 var startDate = useDate+" "+useTime.split("-")[0]+":00";
		 if(curDate>startDate){
			 flg="0";
			 index=index+1;
		 }
		 temObj.useDate=useDate;
		 temObj.useTime=useTime
		 paramArry.push(temObj)
	});
	 
	 if(flg=='0'){
		 var msg="第"+index+"行，设置的会议室时间段，不能大于今天日期，请该行设置的会议室时间"
		 layer.msg(msg, {
				icon : 0
		 });
		 return;
	 }
	 //检查是该时间段，是否有会议室被占用
	 $.ajax({
		   type: "POST",
		   url: "/zhbg/hygl/meetingRoomUseInfo/checkMeetingRoom",
		   data: {data:JSON.stringify(param)},
		   success: function(data){
		      if(data.flag=="0"){
		    	  //有不可设置的会议室
		    	  var msg="设置的第" + data.index + "行的会议室，已被占用，请重新设置！"
		    	  layer.msg(msg, {
						icon : 0
				 });
		    	 return;
		      }else{
		    	  //没有不可设置的会议室
		    	  var plan = saveForm();
		    		if(plan!=""){
		    			updateBusiData(Config.approvalflag);
		    		}
		      }
		   }
	});
/*	 var plan = saveForm();
		if(plan!=""){
			var oper = "";
			if($("#workitemid").val()==""){
				oper = "NEW";
			}else{
				oper = "EDIT";
			}
	        //获取意见
	        var idea = $("#idea").val();
	       	var IsNotionShow =  document.getElementById("ideaArea").style.display;
			if(IsNotionShow == "block"){
				if("2"==  document.getElementById("fillmode").value){
	                if(idea == "" || idea == null){
	                    layer.msg("请填写意见", {icon: 0})
	                    return false;
	                }
				}
			}
			submitToFlow(oper,this,$("#applyTitle").val(),idea,$("#id").val(),"attr","attr1",$("#workitemid").val(),$("#workflowid").val());
		}*/
	
}

/**
 *  工作流回调该方法，用于改变业务数据状态
 * @param subflag 状态位
 * @returns
 */
function updateBusiData(subflag){
	$.ajax({
	    type: "POST",
	    url: "/zhbg/hygl/meetingApply/updateFlag",
	    data:{
	    	id:$("#id").val(),
	    	subflag:subflag
	    },
	    async: false,
	    success:function(data){
	    	if(data.flag=='1'){
	    		parent.TableInit.refTable('right_table');
				MyLayer.closeOpen();
				window.parent.layer.msg('发布成功！',{icon:1});
	    	}
	    },
	    error:function(){
	    }
	});
}

/**
 * 初始化文件上传
 */
function iniFileUpload(){
	//初始化文件上传
    MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"hygl_meeting_apply"},
		editOrView:$("#opertation").val(),
        maxFileSize:5*1024*1024 //5M
    });

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