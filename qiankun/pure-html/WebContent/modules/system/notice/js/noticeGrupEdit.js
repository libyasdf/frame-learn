
$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#oper").val(theRequest.oper);
    debugger;

	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/system/noticeGrup/view",datas,function (data){
			DisplayData.playData({data:data});
			var html = $('[name=main]').html();
			nodetpl.render(html, {data:data.data.sysNoticeUserList}, function(html) {
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
	}else{
        var html = $('[name=main]').html();
        var data = [{
            id:"",
            sysUserName: "",
            sysUserId: "",
            sysDeptId:"",
            sysDeptName:""
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
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    var checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
    if(bootstrapValidator.isValid() && checkResult){
    	Loading.open();
        var data = getFormData();
    	$.ajax({
    		type: "POST",
    		url:"/system/noticeGrup/save",
            contentType:"application/json",
            data:JSON.stringify(data),
    		async: false,
            dataType:"json",
    		success:function(json){
    			Loading.clear();
                res = json.flag;
    			if (json.flag == '1') {
    				$("#id").val(json.data.id);
                    //刷新列表
                    parent.TableInit.refTable('right_table');
                    layer.msg("保存成功！", {icon: 1});
                    var html = $('[name=main]').html();
                    nodetpl.render(html, {data:json.data.sysNoticeUserList}, function(html) {
                        $('#otherPeople').find('tbody').empty().append(html);
                    });
                    }
    		},
    		error:function(){
    			Loading.clear();
                layer.msg("保存失败！", {icon: 2});
    		}
    	});
    }
    return res;
}

/**
 * 打开通知人员选择框
 */
function openDeptTree(subTableLineNum){
    var juId = getcookie("juId");
    openSelectZtree({'id':'sysUserId_'+subTableLineNum+'','name':'sysUserName_'+subTableLineNum+'','parentId':'sysDeptId_'+subTableLineNum+'','parentName':'sysDeptName_'+subTableLineNum+'','deptId':juId,'isMulti':'1','type':'1','asyn':false,'level':'4','url':'/system/component/tree/deptUserTree'});
}

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
        '<input type="text" id="sysUserName_'+subTableLineNum+'" name="sysUserName" ck_type="ck_required" ck_info="请选择通知人员" unselectable="on" placeholder="请选择通知人员" onclick="openDeptTree(\''+subTableLineNum+'\')" class="form-control" >' +
        '<input type="hidden" id="sysUserId_'+subTableLineNum+'" name="sysUserId" /><!-- 人员ID隐藏域 -->' +
        '<input type="hidden" id="sysDeptId_'+subTableLineNum+'" name="sysDeptId" /><!-- 人员ID隐藏域 -->' +
        '<input type="hidden" id="sysDeptName_'+subTableLineNum+'" name="sysDeptName" /><!-- 人员ID隐藏域 -->' +
        '<span class="input-group-addon" onclick="openDeptTree(\''+subTableLineNum+'\')">' +
        '<span class="glyphicon glyphicon-user"></span>' +
        '</span>' +
        '</div>' +
        '</div>';
    trCustom += '</td>';
    trCustom += '</tr>';
    return trCustom;
}

function deleteFn(ids) {
    debugger;
    var flag = false;//如果删除成功改为true
    // 获取资源元素
    var resId = $("#resId").val();
    // 删除操作
    $.ajax({
        url: '/system/noticeGrup/deleteItme?ids=' + ids + "&resId=" + resId
        , type: "GET"
        , dataType: "json"
        , async: false
        , success: function (result) {
            var msg = "";
            if(result.flag == "1"){
                flag = true;
                layer.msg("删除成功！", {icon: 1,time:1500});
            }else{
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
        sysGrupName:$('[name="sysGrupName"]').val()||"",
        isGenarals:$('[name="isGenarals"]').val()||"",
        typeCode:$('[name="typeCode"]').val()||"",
        sysNoticeUserList:[]
    };
    var $subtableRows = $('#otherPeople').find("tr");
    $subtableRows.each(function (i,index) {
        var disk ={
            id:$(index).find('[name="id"]').val()||"",
            sysUserName:$(index).find('[name="sysUserName"]').val()||"",
            sysUserId:$(index).find('[name="sysUserId"]').val()||"",
            sysGrupName:$(index).find('[name="sysGrupName"]').val()||"",
            sysDeptId:$(index).find('[name="sysDeptId"]').val()||"",
            sysDeptName:$(index).find('[name="sysDeptName"]').val()||"",
            grupId:$('[name="id"]').val()||""
        }
        infoColumn.sysNoticeUserList.push(disk);
    });
    return infoColumn;
}


