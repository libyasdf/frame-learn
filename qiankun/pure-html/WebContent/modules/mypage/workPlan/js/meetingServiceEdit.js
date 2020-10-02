$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#pid").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#pid").val() != ""){
		
		//主表单数据渲染
		var datas = {"id":$("#pid").val(),"resId":$("#resId").val()};
		httpRequest("get","/zhbg/hygl/basicSet/serviceDept/edit",datas,function (data){
			DisplayData.playData({data: data});
			if($("#opertation").val() == "EDIT"){
				// 修改页面不允许修改服务单位
				$("#serviceDeptName").attr("onclick","");
				$("#serviceDeptName").attr("readonly","readonly");
				$(".input-group-addon").attr("onclick","");
			}
		});
		
		//渲染子表
		showMeetingServices();
		
	}else{
		//没有pid的时候
		$("#addId").click(function(){
			  var serviceDeptName = $("#serviceDeptName").val(); 
			  if(serviceDeptName == undefined || serviceDeptName == "" || serviceDeptName == null){
				  layer.msg('请先选择会务服务单位',{icon:0});
			  }
		});
		
		$("#deleteId").click(function(){
			layer.msg('请选择要删除的行',{icon:0});
		})
	}
	
})

//部门改变
$("#serviceDeptName").change(function(){
	var serviceDeptName = $("#serviceDeptName").val();
	if(serviceDeptName != undefined && serviceDeptName != "" && serviceDeptName != null){
		
		var json = uniqCheck();
		if(json.valid){
			//渲染子表
			showMeetingServices();
		}else{
			layer.msg("会务服务单位不能重复",{icon:0});
		}
		
		
		
	}else{
		//TODO 清空子表
		$('#otherPeople').find('tbody').empty();
		//TODO 改变添加的click方法
		$("#addId").unbind('click').bind('click', function(){
			 var serviceDeptName = $("#serviceDeptName").val(); 
			  if(serviceDeptName == undefined || serviceDeptName == "" || serviceDeptName == null){
				  layer.msg('请先选择会务服务单位',{icon:0});
			  }
		})
		
	}
	  console.log("change");
});
//增加会务服务负责人时判断是否是会务服务人员
$("#serviceDeptName").change(function(){
	var serviceDeptName = $("#serviceDeptName").val();
	if(serviceDeptName != undefined && serviceDeptName != "" && serviceDeptName != null){
		
		var json = uniqCheck();
		if(json.valid){
			//渲染子表
			showMeetingServices();
		}else{
			layer.msg("会务服务单位不能重复",{icon:0});
		}
		
		
		
	}else{
		//TODO 清空子表
		$('#otherPeople').find('tbody').empty();
		//TODO 改变添加的click方法
		$("#addId").unbind('click').bind('click', function(){
			 var serviceDeptName = $("#serviceDeptName").val(); 
			  if(serviceDeptName == undefined || serviceDeptName == "" || serviceDeptName == null){
				  layer.msg('请先选择会务服务单位',{icon:0});
			  }
		})
		
	}
	  console.log("change");
});


//判断重复
function uniqCheck(msg){
    var json = {};
    var valid ;
    $.ajax({
        type:"GET",
        url:"/zhbg/hygl/basicSet/serviceDept/check",
        async:false,
        data:{
        	id:$("#pid").val(),
        	serviceDeptId:$("#serviceDeptId").val()
        },
        dataType:"json",
        success:function(res){
            valid = res.valid;
        }
    });
    json.valid = valid;
    json.message = msg;
    return json;
}

/**
 * 获取子表数据并调用加载子表的方法
 */
function showMeetingServices(){
	 $.ajax({
         type:"get",
         url:"/zhbg/hygl/basicSet/meetingService/getList",
         data:{"serviceDeptId":$("#pid").val(),"responsibleUserDeptId":$("#serviceDeptId").val(),"resId":$("#resId").val(),"math":Math.random()},
         dataType:"json",
         async:false,
         success:function(result){
        	 if(result){
        		 if(result.flag == "1"){
        			 var data = result.data;
        			 //循环加载子表
        			 showTable(data);
        		     subTableLineNum = $("#otherPeople").find("tr").length;
	             }
        	 }
         },
         error:function(){
         	layer.msg("加载字典项异常！请刷新重试！",{icon:2});
         }
     })
}

/**
 * 将数组转出'','',''格式
 * @param list
 * @returns {String}
 */
function listToString(list){
	var string = '';
	for(var i=0;i<list.length;i++){
		
		if(i == list.length-1){
			string = string + "'" + list[i] + "'";
		}else{
			string = string + "'" + list[i] + "'"+",";
		}
	}
	return string;
}

/**
 * 循环加载子表
 * @param data 自表中的数据
 */
function showTable(data){
	var html = $('[name=main]').html();
    nodetpl.render(html, {data:data}, function(html) {
        $('#otherPeople').find('tbody').empty().append(html);
        // 全选
        var check = new CheckAll('checkboxSuccess','checkboxName');
        // 动态增加
        new AddTr({
            'tableId': 'otherPeople',
            'addId':'addId',
            'deleteId':'deleteId',
            'numName':'numName',
            'bottom': 'true',
            'isNumber': 'true',
            'numSort': 'asc',
            'addTrFn':function(obj){
                subTableLineNum ++;
                var serviceDeptId = $("#serviceDeptId").val();
                var trCustom = "";
                trCustom += '<tr>';
                trCustom += '<td class="text-center" style="width: 5%"><input type="checkbox" name="checkboxName" value="option1"></td>';
                trCustom += '<td class="text-center" name="numName" style="width: 5%"></td>';
                trCustom += '<td class="text-center" style="width: 10%">';
                trCustom += ' 	<input type="text"  name="meetingService" class="form-control" value="" CK_type="required,max" max="10" CK_info="会务服务项"  >';
                trCustom += '</td>';
                trCustom += '<td class="text-center" style="width: 15%">';
                trCustom += '	<div class="input-group">';
                trCustom += '		<input type="hidden" name="meetingServiceId" />';
                trCustom += '		<input type="text" id="responsibleUserName'+subTableLineNum+'" onchange="getIfHwPerson('+subTableLineNum+');" name="responsibleUserName" unselectable="on" onclick="openSelectZtree({\'id\':\'responsibleUserId'+subTableLineNum+'\',\'name\':\'responsibleUserName'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'deptId\':\''+serviceDeptId+'\',\'url\':\'/system/component/tree/deptAndUserTree\'})"  class="form-control" CK_type="required"  CK_info="负责人" >';
                trCustom += ' 		<input type="hidden" class="userName" id="responsibleUserId'+subTableLineNum+'" name="responsibleUserId" />';
                trCustom += '		<span class="input-group-addon" onclick="openSelectZtree({\'id\':\'responsibleUserId'+subTableLineNum+'\',\'name\':\'responsibleUserName'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'deptId\':\''+serviceDeptId+'\',\'url\':\'/system/component/tree/deptAndUserTree\'})" >';
                trCustom += '			<span class="glyphicon glyphicon-user"></span>';
                trCustom += '		</span>';
                trCustom += '	</div>';
                trCustom += '		<input type="hidden" name="meetingServiceId" />';
                trCustom += '</td>';
                trCustom += '<td class="text-center" style="width: 15%">';
                trCustom += '	<input  type="text" name="responsibleUserTelephone" class="form-control" value="" CK_type="required,phone,max" max="11" CK_info="联系方式" >';
                trCustom += '</td>';
                trCustom += '</tr>';
                return trCustom;
            },
            'deleteFn':function(ids){
            	var flag = false;//如果删除成功改为true
            	// 将选中的id 改为可以用in 批量删除的数据
            	ids =  listToString(ids);
            	// 获取资源元素
            	var resId = $("#resId").val();
            	// 删除操作
            	$.ajax({
            		 url: '/zhbg/hygl/basicSet/meetingService/deleteIds?ids=' + ids + "&resId=" + resId
                    , type: "GET"
                    , dataType: "json"
                    , async:false
                    , success: function (result) {
                        var msg = "";
                        if (result.flag === "1") {
                            msg = '删除成功！';
                            flag = true;
                            parent.TableInit.refTable('right_table');
                            if ($.trim(result.msg)) {
                                msg = result.msg;
                            }
                            layer.msg(msg, {icon: 1});
                            
                        } else {
                            msg = '删除失败，请重试！';
                            if ($.trim(result.msg)) {
                                msg = result.msg;
                            }
                            layer.msg(msg, {icon: 2});
                        }
                    }
                    , error: function () {
                        layer.msg('删除失败，请重试！', {icon: 2});
                    }
                })
                return flag;
            }
        })
    });
}

//判断是否会务人员
function getIfHwPerson(ids){
	//根据负责人id判断是不是会务服务人员
  	$.ajax({
          url:"/zhbg/hygl/basicSet/meetingService/getRoleNoByUserId",
          async:true,
          data:{
          	responsibleUserIdd:$("#responsibleUserId"+ids).val()
          },
          success:function(res){
        	 if(res){
        	   if(res!=1){
        		var win=layer.alert("该用户不是会务服务人员！",
        				    {icon:0},
        				    function(){
        				    $("#responsibleUserId"+ids).val("");
        					 $("#responsibleUserName"+ids).val("");
        					 layer.close(win);
        					}); 
        		 }
        	  }
          }
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


