$(function(){
    scrollTop.init();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.oper);
    $("#superId").val(theRequest.superId);
    $("#nodeLevel").val(++theRequest.nodeLevel);
    /**
     * 是否审批
     */
 /*   $('input[type=radio][name=isSp]').change(function() {
        if (this.value == '1') {
            $("#fbUserList").show();
        }
        else if (this.value == '0') {
            $("#fbUserList").hide();
        }
    });*/
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/info/column/edit",datas,function (json){
            DisplayData.playData({data:json});
            if(json.data.isSp == '1'){
                $("#fbUserList").show();
            }
            if('1'== json.data.isFbfw){
                $("#fbfwShow").show();
                $("input[type=radio][name='isFBContent'][value='1']").attr("checked",'checked');//设置选择值为1的
            }
            if($("#opertation").val() == "VIEW"){
                //radio
                var isShow = "";
                if(json.data.isShow == '1'){isShow = "是";}
                if(json.data.isShow == '0'){isShow = "否";}
                $("#isShow").text(isShow);
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
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()) {
        saveDictionary('1','1');
        var id = $("#id").val(); //打开权限选择窗口
        MyLayer.layerOpenUrl({
            url: "/modules/info/authority/authorityInfo.html?contentId=" + id+"&typeCode=01",
            title: "选择默认通知范围"
        });
    }
}

// 保存 status:设置保存范围后的状态值，isTs:控制是否弹出保存成功提示
function saveDictionary(status,isTs) {
    var isFbfw = $('[name="isFbfw"]:checked').val();
    if(status == null || status == undefined){
        status = $("#status").val();
    }
   var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    var checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
    if(bootstrapValidator.isValid() && checkResult){
        if(isFbfw =='1' && status!='1'){//有发布范围并且未保存
            layer.msg("请先保存发布范围",{icon:0});
            return false;
        }
        Loading.open();
        var data = getFormData();
        $.ajax({
            type: "POST",
            url:"/info/column/save?resId="+resId,
            contentType:"application/json",
            data:JSON.stringify(data),
            async: false,
            dataType:"json",
            success:function(json){
                Loading.clear();
                if ('1' == json.flag) {
                    if(isTs != "1"){
                        layer.msg("保存成功！", {icon: 1,time:1000}, function (index) {
                            if($("#opertation").val() == "NEW"){//新增页面
                                parent.typeTree.addNode({
                                    id:json.data.id,
                                    pId:json.data.superId,
                                    name:json.data.columnName,
                                    nodeLevel:json.data.nodeLevel,
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
                                name:json.data.columnName,
                                nodeLevel:json.data.nodeLevel,
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
                    parent.TableInit.refTable('right_table');
                }
            },
            error:function(data){
                layer.msg("保存失败!",{icon:2})
            }
        });
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
        '<input type="text" id="fbUserName_'+subTableLineNum+'" name="fbUserName" ck_type="ck_required" ck_info="请选择发布人" unselectable="on" placeholder="请选择发布人" onclick="openSelectZtree({\'id\':\'fbUserId_'+subTableLineNum+'\',\'name\':\'fbUserName_'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'level\':\'3\',\'deptId\':\'441\',\'rolesNo\':\'C602,D602\',\'url\':\'/system/component/tree/getDeptUserByDeptIdAndRolesNo\'})" class="form-control" >' +
        '<input type="hidden" id="fbUserId_'+subTableLineNum+'" name="fbUserId" /><!-- 人员ID隐藏域 -->' +
        '<span class="input-group-addon" onclick="openSelectZtree({\'id\':\'fbUserId_'+subTableLineNum+'\',\'name\':\'fbUserName_'+subTableLineNum+'\',\'isMulti\':\'1\',\'type\':\'1\',\'asyn\':false,\'level\':\'3\',\'deptId\':\'441\',\'rolesNo\':\'C602,D602\',\'url\':\'/system/component/tree/getDeptUserByDeptIdAndRolesNo\'})">' +
        '<span class="glyphicon glyphicon-user"></span>' +
        '</span>' +
        '</div>' +
        '</div>';
    trCustom += '</td>';
    trCustom += '</tr>';
    return trCustom;
}

function deleteFn(ids) {
    var flag = false;//如果删除成功改为true
    // 获取资源元素
    var resId = $("#resId").val();
    // 删除操作
    $.ajax({
        url: '/info/column/deleteItme?ids=' + ids + "&resId=" + resId
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
        isFbfw:$('[name="isFbfw"]:checked').val()||"",
        orderNo:$('[name="orderNo"]').val()||"",
        status:$('[name="status"]').val()||"",
        fbUserList:[]
    };
    var $subtableRows = $('#otherPeople').find("tr");
    $subtableRows.each(function (i,index) {
        var disk ={
            id:$(index).find('[name="id"]').val()||"",
            fbUserId:$(index).find('[name="fbUserId"]').val()||"",
            fbUserName:$(index).find('[name="fbUserName"]').val()||"",
          /*  shUserId:$(index).find('[name="shUserId"]').val()||"",
            shUserName:$(index).find('[name="shUserName"]').val()||"",*/
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