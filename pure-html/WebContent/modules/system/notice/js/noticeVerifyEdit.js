$(function(){
    scrollTop.init();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);

    debugger;
    //表单数据渲染
    var datas = {"resId":$("#resId").val()};
    httpRequest("get","/system/noticeVerify/edit",datas,function (json){
        DisplayData.playData({data:json});
        if(json.deptLevel == '1'){
            $("#glUserList").show();
        }
        if(json.data == ""){
            var html = $('[name=main]').html();
            var data = [{
                id:"",
                fbUserName: "",
                fbUserId: "",
                shUserName:"",
                shUserId:"",
                fbUserDeptId:"",
                shUserDeptId:""
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
        }else{
            if(json.data.isSP == '1'){
                $("#fbUserList").show();
            }
            var html = $('[name=main]').html();
            nodetpl.render(html, {data:json.data.noticeUserList}, function(html) {
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

    });

    /**
     * 是否审批
     */
    debugger;
    $('input[type=radio][name=isSP]').change(function() {
        if (this.value == '1') {
            $("#fbUserList").show();
        }
        else if (this.value == '0') {
            $("#fbUserList").hide();
        }
    });
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


// 保存 status:设置保存范围后的状态值，isTs:控制是否弹出保存成功提示
function saveDictionary() {
    Loading.open();
    debugger;
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    var checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
    if(bootstrapValidator.isValid() && checkResult){
        var data = getFormData();
        $.ajax({
            type: "POST",
            url:"/system/noticeVerify/save?resId="+resId,
            contentType:"application/json",
            data:JSON.stringify(data),
            async: false,
            dataType:"json",
            success:function(json){
                debugger;
                Loading.clear();
                if(json.treeDept =='1'){
                    if ('1' == json.flag) {
                        layer.msg("保存成功！", {icon: 1,time:1500}, function (index) {
                            if('1' == json.shFlag){
                                layer.msg("授权已提交审核！", {icon: 1});
                            }
                        });
                        $("#id").val(json.data.id);
                        var html = $('[name=main]').html();
                        nodetpl.render(html, {data:json.data.noticeUserList}, function(html) {
                            $('#otherPeople').find('tbody').empty().append(html);
                        });
                    }else{
                        layer.msg("保存失败!",{icon:2})
                    }
                }else if(json.treeDept =='0'){
                    layer.msg("没有相对应的业务角色，请重新选择人员！", {icon: 3});
                }else if(json.treeDept =='2'){
                    layer.msg("选择的人员中有局级管理员，请重新选择人员！", {icon: 3});
                }
            },
            error:function(data){
                layer.msg("保存失败!",{icon:2})
            }
        });
    }else{
        Loading.clear();
    }
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
        '<input type="text" id="fbUserName_'+subTableLineNum+'" name="fbUserName" ck_type="ck_required" ck_info="请选择发布人" unselectable="on" placeholder="请选择发布人" onclick="openDeptTree(\''+subTableLineNum+'\')" class="form-control" >' +
        '<input type="hidden" id="fbUserId_'+subTableLineNum+'" name="fbUserId" /><!-- 人员ID隐藏域 -->' +
        '<input type="hidden" id="fbUserDeptId_'+subTableLineNum+'" name="fbUserDeptId" /><!-- 人员部门ID隐藏域 -->'+
        '<span class="input-group-addon" onclick="openDeptTree(\''+subTableLineNum+'\')">' +
        '<span class="glyphicon glyphicon-user"></span>' +
        '</span>' +
        '</div>' +
        '</div>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 40%">';
    trCustom += '<div class="col-sm-10">' +
        '<div class="input-group">' +
        '<input type="text" id="shUserName_'+subTableLineNum+'" name="shUserName" ck_type="ck_required" ck_info="请选择审核人" unselectable="on" ck_type="ck_required"  CK_info="审核人"  placeholder="请选择审核人" onclick="openDeptTree1(\''+subTableLineNum+'\')" class="form-control" >' +
        '<input type="hidden" id="shUserId_'+subTableLineNum+'" name="shUserId" /><!-- 人员ID隐藏域 -->' +
        '<input type="hidden" id="shUserDeptId_'+subTableLineNum+'" name="shUserDeptId" /><!-- 人员部门ID隐藏域 -->'+
        '<span class="input-group-addon" onclick="openDeptTree1(\''+subTableLineNum+'\')">' +
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
        url: '/system/noticeVerify/deleteItme?ids=' + ids + "&resId=" + resId
        , type: "GET"
        , dataType: "json"
        , async: false
        , success: function (result) {
            var msg = "";
            if(result.flag == "1"){
                flag = true;
                layer.msg("删除成功！", {icon: 1,time:1500}, function (index) {
                    if('1' == result.shFlag){
                        layer.msg("取消授权已提交审核！", {icon: 1});
                    }
                });
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
        id:"",
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
        isSP:$('[name="isSP"]:checked').val()||"",
        noticeGlUserIds:$('[name="noticeGlUserIds"]').val()||"",
        noticeGlUserNames:$('[name="noticeGlUserNames"]').val()||"",
        deptGLUserDeptId:$('[name="deptGLUserDeptId"]').val()||"",
        noticeUserList:[]
    };
    var $subtableRows = $('#otherPeople').find("tr");
    $subtableRows.each(function (i,index) {
        var disk ={
            id:$(index).find('[name="id"]').val()||"",
            fbUserId:$(index).find('[name="fbUserId"]').val()||"",
            fbUserName:$(index).find('[name="fbUserName"]').val()||"",
            shUserId:$(index).find('[name="shUserId"]').val()||"",
            shUserName:$(index).find('[name="shUserName"]').val()||"",
            fbUserDeptId:$(index).find('[name="fbUserDeptId"]').val()||"",
            shUserDeptId:$(index).find('[name="shUserDeptId"]').val()||"",
            verifyId:$('[name="id"]').val()||""
        }
        infoColumn.noticeUserList.push(disk);
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
 * 打开发布人人员选择框
 */
function openDeptTree(subTableLineNum){
    debugger;
    var rolesNo = getcookie("rolesNo");
    var juId = getcookie("orgid");
    if(rolesNo.indexOf("C612") != -1 || rolesNo.indexOf("D612") != -1){//部门级管理员
        juId = getcookie("juId");
    }
    openSelectZtree({'id':'fbUserId_'+subTableLineNum+'','name':'fbUserName_'+subTableLineNum+'','parentId':'fbUserDeptId_'+subTableLineNum+'','deptId':juId,'isMulti':'1','type':'1','asyn':false,'level':'4','url':'/system/component/tree/deptUserTree'});
}

/**
 * 打开审核人人员选择框
 */
function openDeptTree1(subTableLineNum){
    debugger;
    var rolesNo = getcookie("rolesNo");
    var juId = getcookie("orgid");
    if(rolesNo.indexOf("C612") != -1 || rolesNo.indexOf("D612") != -1){//部门级管理员
        juId = getcookie("juId");
    }
    openSelectZtree({'id':'shUserId_'+subTableLineNum+'','name':'shUserName_'+subTableLineNum+'','parentId':'shUserDeptId_'+subTableLineNum+'','deptId':juId,'isMulti':'1','type':'1','asyn':false,'level':'4','url':'/system/component/tree/deptUserTree'});
}