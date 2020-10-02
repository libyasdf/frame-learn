 
$(function(){
    scrollTop.init();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.oper);
 
    $("#superId").val(theRequest.superId ? theRequest.superId : "");
    //$("#nodeLevel").val(++theRequest.nodeLevel);
    $("#isFirst").val(theRequest.isFirst);
    $("#nodeLevel").val(theRequest.nodeLevel ? theRequest.nodeLevel : "");
    $("#code").val(theRequest.code ? theRequest.code : "");
    $("#oldCode").val(theRequest.code ? theRequest.code : "");
    
   
    /**
     * 是否审批
     */
    $('input[type=radio][name=isSp]').change(function() {
        if (this.value == '1') {
            $("#fbUserList").show();
        }
        else if (this.value == '0') {
            $("#fbUserList").hide();
        }
    });
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/video/background/column/edit",datas,function (json){
        	console.info("...................................")
        	console.info(json)
            DisplayData.playData({data:json});
            if(json.data.isSp == '1'){
                $("#fbUserList").show();
            }else{
            	 $("#fbUserList").hide();
            }
            if('1'== json.data.isFbfw){
                $("#fbfwShow").show();
                $("input[type=radio][name='isFBContent'][value='1']").attr("checked",'checked');//设置选择值为1的
            }
            if($("#opertation").val() == "VIEW"){
                //radio
                var isShow = "";
                if(json.data.isShow == '1'){
                	isShow = "是";
                }
                if(json.data.isShow == '0'){
                	isShow = "否";
                	$("#isRecommendShow").hide();
                }
                $("#isShow").text(isShow);
                var isSp = "";
                if(json.data.isSp == '1'){isSp = "是";}
                if(json.data.isSp == '0'){isSp = "否";}
                $("#isSp").text(isSp);
                var isFbfw = "";
                if(json.data.isFbfw == '1'){isFbfw = "是";}
                if(json.data.isFbfw == '0'){isFbfw = "否";}
                $("#isFbfw").text(isFbfw);
                
            }
            var html = $('[name=main]').html();
            nodetpl.render(html, {data:json.data.fbUserList}, function(html) {
                $('#otherPeople').find('tbody').empty().append(html);
                // 全选
                var check = new CheckAll('checkboxSuccess','checkboxName');
                // 动态增加
                new AddTr({
                    'tableId': 'otherPeople',
                    'addId':'addId',
                    'deleteId':'deleteId',
                    'numName':'numName',
                    'bottom': 'false',
                    'isNumber': 'true',
                    'numSort': 'desc',
                    'addTrFn':addTrFn,
                    "deleteFn":deleteFn
                })
            });
        });
       /* 马秋霞修改------start...................*/
        var isFbfw = $('[name="isFbfw"]:checked').val();
        //alert(isFbfw)
        if(isFbfw=="1"){
        	var datas = {
                    columnId: $("#id").val(),
                    math:Math.random(),
            	}
        	httpRequest("get","/video/authority/contentFbZwqx/getColumnAuthority",datas,function (json){
        		var isAlert="0";
        		if(json.flag == "1"){
        			var data = json.data;
        			//回显部门权限
        			var deptList = data.deptList;
        			if(!$.isEmptyObject(deptList)){
        				isAlert="1";
        			}
        			//回显群组权限
                    var groupList = data.groupList;
                    if(!$.isEmptyObject(groupList)){
                    	isAlert="1";
                    }
                  //回显职务权限
        			var zwqxList = data.zwqxList;
        			if(!$.isEmptyObject(zwqxList)){
        				isAlert="1";
        			}
        			
        			if(isAlert=="1"){
        				
        				putStatus(true)
        			}
        		}
        	})
        }
        
        	
    }else{
        var html = $('[name=main]').html();
        var data = [{
            id:"",
            fbUserName: "",
            fbUserId: "",
            shUserName:"",
            shUserId:""
        }];
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
                'bottom': 'false',
                'isNumber': 'true',
                'numSort': 'desc',
                'addTrFn':addTrFn,
                "deleteFn":deleteFn
            })
        });
    }
})
var resId = $("#resId").val();
/*function isHaveFbfw(){
    var isFbfw = $('[name="isFbfw"]:checked').val()
    saveDictionary(isFbfw);
    if(isFbfw == "1"){
        var id = $("#id").val(); //打开权限选择窗口
        MyLayer.layerOpenUrl({
            url: "/modules/info/authority/authorityColumn.html?columnId=" + id,
            title: "选择默认通知范围"
        });
    }
}*/

function openFbfw() {
	if($("#flag").val()=="1"){
		//只读
		 var id = $("#id").val(); //打开权限选择窗口
	     MyLayer.layerOpenUrl({
	          url: "/modules/video/authority/authorityColumnReadOnly.html?columnId=" + id,
	          title: "查看默认通知范围"
	     });
	}else{
		var bootstrapValidator = $("#form").data('bootstrapValidator');
	    //手动触发验证
	    bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()) {
	    	saveDictionary('1','1','1');
	    	//var f = saveForm('1','1');
	    	//if(f){
	    		var id = $("#id").val(); //打开权限选择窗口
	    		MyLayer.layerOpenUrl({
	    			url: "/modules/video/authority/authorityColumn.html?columnId=" + id,
	    			title: "选择默认通知范围"
	    		});
	    	//}else{
	    		/*layer.msg("请先保存栏目信息！", {icon: 0});*/
	    	//}
	    }
	}
    
}

// 保存 status:设置保存范围后的状态值，isTs:控制是否弹出保存成功提示,flag,是否校验发布人和审核人(1表示不需要校验，否则需要校验）
function saveDictionary(status,isTs,flag) {
    var isFbfw = $('[name="isFbfw"]:checked').val();
    if(status == null || status == undefined){
        status = $("#status").val();
    }
   var bootstrapValidator = $("#form").data('bootstrapValidator');
   var isSuccessFromHB = false;
    //手动触发验证
    bootstrapValidator.validate();
    var  checkResult=true
    if(flag!="1"){//说明是点击发布范围进行的保存，此处不需要校验发布人和审核人
    	checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
	}
    if($('[name="isSp"]:checked').val()=='1'){//需要审批，需要验证是否有发布人和审批人
    	var len = $("#otherPeople").find("tr").length;
    	 if(len==0 && flag!="1"){
    		layer.msg("请选择发布人：为必填项!",{icon:0})
    		return ;
    	}
    	
    };
    if(bootstrapValidator.isValid() && checkResult){
    	
    	//在音视频增加分类开始
    	var ClassLayer = 1;
    	var FatherId = 0;
    	//当前被选中类别在音视频系统中的Id
    	if($("#code").val()!=""){
    		FatherId = parseInt($("#code").val());
    	}
    	//当前被选中类别在音视频系统中的层级，即ClassLayer
    	if($("#nodeLevel").val()!=""){
    		ClassLayer = parseInt($("#nodeLevel").val()) + 1;
    	}
    	var newLevel={
				ClassUuid: "7c97c0fa-fdd1-4097-9bd7-34386548ac09",	
				ClassName: $("#columnName").val(),
				ClassLongName: "",
				ClassRemark: "",
				ClassLargeIcon: "",
				ClassMediumIcon: "",
				ClassSmallIcon: "",
				FatherId: FatherId,
				ClassType: 0,
				ClassLayer: ClassLayer,
				ClassReadOnly: "0",
				ClassIsDisplay: "1",
				ClassDisplayOrder: "0",
				OperatorId: 1,
				OperatorUuid: "1ea9667c-0171-11e8-a1e1-000c29ba38ba",
				AdderId: 1,
				AdderName: "admin",
				AddTime: "2018-06-04T15:47:24+08:00",
				UpdaterId: 0,
				UpdaterName: "",
				UpdateTime: "2018-06-04T15:47:24+08:00",
				DelFlag: "0"
		}
    	//默认的请求方式和url
    	var url = Config.hBServerIp+"/api/v1/file_class";
    	var requestType = "POST";
    	//如果是修改，需要修改url和请求方式
    	if($("#id").val() != ""){
    		url=Config.hBServerIp+"/api/v1/file_class/"+FatherId;
    		requestType = "PUT";
    		newLevel={
    				ClassUuid: "7c97c0fa-fdd1-4097-9bd7-34386548ac09",	
					ClassName: $("#columnName").val(),
					ClassLongName: "",
					ClassRemark: "",
					ClassLargeIcon: "",
					ClassMediumIcon: "",
					ClassSmallIcon: "",
					ClassType: 0,
					ClassLayer: ClassLayer-1,
					ClassReadOnly: "0",
					ClassIsDisplay: "1",
					ClassDisplayOrder: "0",
					OperatorId: 1,
					OperatorUuid: "1ea9667c-0171-11e8-a1e1-000c29ba38ba",
					AdderId: 1,
					AdderName: "admin",
					AddTime: "2018-06-04T15:47:24+08:00",
					UpdaterId: 0,
					UpdaterName: "",
					UpdateTime: "2018-06-04T15:47:24+08:00",
					DelFlag: "0"
			}
    	}
    	console.info(newLevel)
    	$.ajax({
    		type:requestType,
    		url:url,
    		data:JSON.stringify(newLevel),
    		async: false,
    		dataType:"json",
    		success:function(data){
    		    console.log("音视频创建分类成功："+JSON.stringify(data));
    		    //如果是修改那么音视频那边并不会返回保存后的对象，只会返回“ok”，所以下边需要判断是修改还是新增
    		    if($("#id").val() == ""){
    		    	//设置在音视频中分类Id
	    		    $("#code").val(data.Id);
	    		    //设置在音视频中分类层级
	    		    $("#nodeLevel").val(data.ClassLayer);
    		    }
    		    
    		    isSuccessFromHB = true;
    		    if(isFbfw =='1' && status!='1'){//有发布范围并且未保存
    	            layer.msg("请先保存发布范围！",{icon:0});
    	            return false;
    	        }
    	        Loading.open();
    	        var data = getFormData();
    	        console.info("......................")
    	        console.info(data)
    	        $.ajax({
    	            type: "POST",
    	            url:"/video/background/column/save?resId="+resId,
    	            contentType:"application/json",
    	            data:JSON.stringify(data),
    	            async: false,
    	            dataType:"json",
    	            success:function(json){
    	            	console.info(json)
    	                Loading.clear();
    	                if ('1' == json.flag) {
    	                    if(isTs != "1"){
    	                        layer.msg("保存成功！", {icon: 1,time:1000}, function (index) {
    	                            if($("#opertation").val() == "NEW"){//新增页面
    	                                parent.typeTree.addNode({
    	                                    id:json.data.id,
    	                                    pId:json.data.superId,
    	                                    isFirst:json.data.isFirst,
    	                                    name:json.data.columnName,
    	                                    nodeLevel:json.data.nodeLevel,
    	                                    code:json.data.code,
    	                                    orderNo:json.data.orderNo
    	                                });// 在ztree中增加新保存的节点
    	                                $("#opertation").val("EDIT");
    	                            }else{
    	                                parent.typeTree.updateNode(json.data);
    	                            }
    	                            window.parent.refresh();
    	                        });
    	                    }else{
    	                        if($("#opertation").val() == "NEW"){//新增页面
    	                            parent.typeTree.addNode({
    	                                id:json.data.id,
    	                                pId:json.data.superId,
    	                                isFirst:json.data.isFirst,
    	                                name:json.data.columnName,
    	                                nodeLevel:json.data.nodeLevel,
    	                                code:json.data.code,
    	                                orderNo:json.data.orderNo
    	                            });// 在ztree中增加新保存的节点
    	                            $("#opertation").val("EDIT");
    	                        }else{
    	                            parent.typeTree.updateNode(json.data);
    	                        }
    	                        window.parent.refresh();
    	                    }
    	                    $("#id").val(json.data.id);
    	                    var html = $('[name=main]').html();
    	                    nodetpl.render(html, {data:json.data.fbUserList}, function(html) {
    	                        $('#otherPeople').find('tbody').empty().append(html);
    	                    });
    	                    
    	                    $("#oldCode").val($("code").val());
    	                    parent.TableInit.refTable('right_table');
    	                    
    	                    
    	                }else{
    	                	layer.msg(json.msg, {icon: 2,time:2000});
    	                	 if($("#opertation").val() == "NEW"){//新增页面
    	                		 //调用接口，在音视频系统中同步删除该分类
    	                		 deleteFenLeiInHB($("#code").val());
    	                		 $("#nodeLevel").val(parseInt($("#nodeLevel").val()) - 1);
    	                		 $("#code").val($("#oldCode").val());
    	                	 }
    	                	
    	                }
    	            },
    	            error:function(data){
    	                layer.msg("保存失败!",{icon:2})
    	                if($("#opertation").val() == "NEW"){//新增页面
	                		 //调用接口，在音视频系统中同步删除该分类
	                		 deleteFenLeiInHB($("#code").val());
	                		 $("#nodeLevel").val(parseInt($("#nodeLevel").val()) - 1);
	                		 $("#code").val($("#oldCode").val());
	                	 }
    	            }
    	        });
    		},
    		error:function(data){
    			layer.msg("音视频系统创建视频栏目失败，请稍后再试！", {icon: 0});
    		}
    	});
    	
    	//音视频那边没有保存成功，则直接退出
    	
    	/*if(!isSuccessFromHB){
    		return false;
    	}*/
    	
      
       
        
    }
}

//保存form表单（去掉置顶的功能后，去掉置顶的判断）
/*function saveForm(status,isTs){
	var f = false;
	if($('[name="isShow"]:checked').val()=="0" ||($('[name="isShow"]:checked').val()=="1" && $('[name="isRecommend"]:checked').val()=="0")){
		saveDictionary(status,isTs);
		f = true;
	}else if($('[name="isShow"]:checked').val()=="1" && $('[name="isRecommend"]:checked').val()=="1"){
		if($("#count").val()>0){
			if($("#id").val()==""){//新增
				f = false;
				layer.msg("已存在置顶栏目，请重新选择！", {icon: 0});
			}else{
				if($("#recommendId").val()===$("#id").val()){
					f = true;
					saveDictionary(status,isTs);
				}else{
					f = false;
					layer.msg("已存在置顶栏目，请重新选择！", {icon: 0});
				}
			}
		}else{
			f = true;
			saveDictionary(status,isTs);
		}
	}
	return f;
}*/


function addTrFn(obj){
    var subTableLineNum = $("#otherPeople").find("tr").length;
    subTableLineNum ++;
    var trCustom = "";
    trCustom += '<tr>';
    trCustom += '<td class="text-center" style="width: 10%"><input type="checkbox" name="checkboxName" value="option1"><input type="hidden" name="id" value=""></td>';
    trCustom += '<td class="text-center" name="numName" style="width:10%">'+subTableLineNum+'</td>';
    trCustom += '<td class="text-center" style="width: 40%">';
    trCustom += '<div class="col-sm-10">' +
        '<div class="input-group">' +
        '<input type="text" id="fbUserName_'+subTableLineNum+'" name="fbUserName"  onChange="checkFBUser(this.value,event,'+subTableLineNum+')" ck_type="ck_required" ck_info="请选择发布人" unselectable="on" placeholder="请选择发布人" onclick="openSelectZtree({\'id\':\'fbUserId_'+subTableLineNum+'\',\'name\':\'fbUserName_'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'level\':\'4\',\'url\':\'/system/component/tree/deptUserTree\'})" class="form-control" >' +
        '<input type="hidden" id="fbUserId_'+subTableLineNum+'" name="fbUserId" /><!-- 人员ID隐藏域 -->' +
        '<span class="input-group-addon" onclick="openSelectZtree({\'id\':\'fbUserId_'+subTableLineNum+'\',\'name\':\'fbUserName_'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'level\':\'4\',\'url\':\'/system/component/tree/deptUserTree\'})">' +
        '<span class="glyphicon glyphicon-user"></span>' +
        '</span>' +
        '</div>' +
        '</div>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 40%">';
    trCustom += '<div class="col-sm-10">' +
        '<div class="input-group">' +
        '<input type="text" id="shUserName_'+subTableLineNum+'" name="shUserName" ck_type="ck_required" ck_info="请选择审核人" unselectable="on" ck_type="ck_required"  CK_info="审核人"  placeholder="请选择审核人" onclick="openSelectZtree({\'id\':\'shUserId_'+subTableLineNum+'\',\'name\':\'shUserName_'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'level\':\'4\',\'url\':\'/system/component/tree/deptUserTree\'})" class="form-control" >' +
        '<input type="hidden" id="shUserId_'+subTableLineNum+'" name="shUserId" /><!-- 人员ID隐藏域 -->' +
        '<span class="input-group-addon" onclick="openSelectZtree({\'id\':\'shUserId_'+subTableLineNum+'\'\',\'name\':\'shUserName_'+subTableLineNum+'\'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'level\':\'4\',\'url\':\'/system/component/tree/deptUserTree\'})">' +
        '<span class="glyphicon glyphicon-user"></span>' +
        '</span>' +
        '</div>' +
        '</div>';
    trCustom += '</td>';
    trCustom += '</tr>';
    return trCustom;
}
function checkFBUser(fb,ev,num){
	    var $subtableRows = $('#otherPeople tr');
	    var count = 0;
	    console.log($subtableRows);
	    $subtableRows.each(function (i,index) {
                //fbUserName:$(index).find('[name="fbUserName"]').val()||"",
	    	//console.log($(index).find('[name="fbUserName"]').val())
	    	//console.log($(index).find('[name="numName"]').text())
	    	if($(index).find('[name="numName"]').text()!= num){//判断是否是当前操作行，不是则判断是否值重复
	            if(fb==$(index).find('[name="fbUserName"]').val()){
            		ev.target.value = '';
            		ev.target.nextSibling.value=""
        			layer.msg("发布人不能重复，请重新选择！", {icon: 2});
            		return false;
            	}
            }
	    });
	  // alert(fb);
	   //alert(JSON.stringify(infofbColumn));
	    
}
function deleteFn(ids) {
    var flag = false;//如果删除成功改为true
    // 获取资源元素
    var resId = $("#resId").val();
    // 删除操作
    $.ajax({
        url: '/video/background/column/deleteItme?ids=' + ids + "&resId=" + resId
        , type: "GET"
        , dataType: "json"
        , async: false
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
    });
}
/**
 * 格式化数据
 */
function getFormData(){
    var infoColumn ={
        id:$('[name="id"]').val()||"",
        creUserId:$('[name="creUserId"]').val()||"",
        creUserName:$('[name="creUserName"]').val()||"",
        creDeptId:$('[name="creDeptId"]').val()||"",
        creDeptName:$('[name="creDeptName"]').val()||"",
        creChushiId:$('[name="creChushiId"]').val()||"",
        creChushiName:$('[name="creChushiName"]').val()||"",
        creJuId:$('[name="creJuId"]').val()||"",
        creJuName:$('[name="creJuName"]').val()||"",
        visible:$('[name="visible"]').val()||"",
        creTime:$('[name="creTime"]').val()||"",
        updateUserId:$('[name="updateUserId"]').val()||"",
        updateUserName:$('[name="updateUserName"]').val()||"",
        updateTime:$('[name="updateTime"]').val()||"",
        superId:$('[name="superId"]').val()||"",
        nodeLevel:$('[name="nodeLevel"]').val()||"",
        columnName:$('[name="columnName"]').val()||"",
        columnCode:$('[name="columnCode"]').val()||"",
        columnIcon:$('[name="columnIcon"]').val()||"",
        columnRemark:$('[name="columnRemark"]').val()||"",
        isShow:$('[name="isShow"]:checked').val()||"",
        isSp:$('[name="isSp"]:checked').val()||"",
        isFbfw:$('[name="isFbfw"]:checked').val()||"",
        columnGlUserIds:$('[name="columnGlUserIds"]').val()||"",
        columnGlUserNames:$('[name="columnGlUserNames"]').val()||"",
        orderNo:$('[name="orderNo"]').val()||"",
        isFirst:$("#isFirst").val()||"",
        code:$("#code").val()||"",
        fbUserList:[]
    };
    var $subtableRows = $('#otherPeople').find("tr");
    $subtableRows.each(function (i,index) {
        var disk ={
            id:$(index).find('[name="id"]').val()||"",
            fbUserId:$(index).find('[name="fbUserId"]').val()||"",
            fbUserName:$(index).find('[name="fbUserName"]').val()||"",
            shUserId:$(index).find('[name="shUserId"]').val()||"",
            shUserName:$(index).find('[name="shUserName"]').val()||"",
            columnId:$('[name="id"]').val()||""
        }
        infoColumn.fbUserList.push(disk);
    });
    return infoColumn;
}
//关闭当前窗口
function closeIfram(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
/**
 * 保存权限范围后，回调置成功状态位
 */
function putStatus(status){
    if(status){
        $("#status").val("1");
    }
}
function showFbfw(value) {
    if(value.value=='1'){
        $("#fbfwShow").show();
    }else{
        $("#fbfwShow").hide();
    }
    $("#isFbfw").val(value.value);
}
function showRecommend(value) {
	if(value.value=='1'){
		$("#isRecommendShow").show();
	}else{
		$("#isRecommendShow").hide();
	}
	$("#isFbfw").val(value.value);
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


