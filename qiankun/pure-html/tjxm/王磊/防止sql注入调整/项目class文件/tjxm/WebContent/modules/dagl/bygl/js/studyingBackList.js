
var subTableLineNum =1;

$(function () {

    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));

    //加载等待
    index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候...',success: function(layero){
            layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
            setTimeout(function () {
                /**
                 * 初始化页面，数据加载、渲染
                 */
                if ($("#id").val() != "") {

                    //表单数据渲染（原始数据）
                    var datas = {"id": $("#id").val(), "resId": $("#resId").val()};
                    httpRequest("get", "/dagl/bygl/studying/getStudyingById", datas, function (data) {
                        console.log('初始化页面', data);
                        DisplayData.playData({data: data});
                        iniTable(data);
                        iniFileUpload(data);
                        layer.close(index);
                    });

                } else {//id为空时，表示新建草稿状态，加载启动节点按钮及启动节点提示信息
                    iniTable();
                    iniFileUpload();
                    layer.close(index);
                }
            },1000)
        }
    });



});


function iniTable(data) {
    studyingSubs = [{
        id: '',
        recChushiName: '',
        recChushiId: '',
        fj: ''
    }];
    if (data != undefined) {
        if (!$.isEmptyObject(data.data.studyingSubs)) {
            studyingSubs = data.data.studyingSubs;
        }
    }

    var html = $('[name=main]').html();
    nodetpl.render(html, {data: studyingSubs}, function (html) {
        $('#otherPeople').find('tbody').empty().append(html);
        // 全选
        var check = new CheckAll('checkboxSuccess', 'checkboxName');
        // 动态增加
        new AddTr({
            'tableId': 'otherPeople',
            'addId': 'addId',
            'deleteId': 'deleteId',
            'numName': 'numName',
            'bottom': 'true',
            'isNumber': 'true',
            'numSort': 'asc',
            'addTrFn': addTabFn,
            "deleteFn": deleteTabFn
            //"subTableLineNum":$("#otherPeople").find("tr").length
        })
    });
}

function initBackTable(data) {
    studyingSubs = [{
        id: '',
        recChushiName: '',
        recChushiId: '',
        fj: ''
    }];
    if (data != undefined) {
        if (!$.isEmptyObject(data.data.studyingSubs)) {
            studyingSubs = data.data.studyingSubs;
        }
    }

    var html = $('[name=main1]').html();
    nodetpl.render(html, {data: studyingSubs}, function (html) {
        $('#studyingBack').find('tbody').empty().append(html);
        // 全选
        var check = new CheckAll('checkboxSuccess', 'checkboxName');
        // 动态增加
        new AddTr({
            'tableId': 'otherPeople',
            'addId': 'addId',
            'deleteId': 'deleteId',
            'numName': 'numName',
            'bottom': 'true',
            'isNumber': 'true',
            'numSort': 'asc',
            'addTrFn': addTabFn,
            "deleteFn": deleteTabFn
            //"subTableLineNum":$("#otherPeople").find("tr").length
        })
    });
}

/**
 * 添加一行
 */
function addTabFn(obj) {

    subTableLineNum = $("#otherPeople").find("tr").length;
    subTableLineNum++;

    var trCustom = "";
    trCustom += '<tr>';
    trCustom += '<td class="text-center" style="width: 5%">' +
        '   <input type="checkbox" name="checkboxName" value="option1">' +
        '   <input type="hidden" id="id' + subTableLineNum + '" name="id">' +
        '</td>';
    trCustom += '<td class="text-center" name="numName" style="width: 5%"></td>';
    trCustom += '<td class="text-center" style="width: 20%">';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="50"  CK_info="处室" class="form-control" id="chushiName' + subTableLineNum + '" name="chushiName" onclick="openSelectZtree({\'id\':\'chushiId'+ subTableLineNum +'\',\'name\':\'chushiName'+ subTableLineNum +'\',\'type\':\'2\',\'asyn\':false,\'level\':2,\'url\':\'/system/component/tree/deptTree\',\'isMulti\':\'1\'}) "/>';
    trCustom += '   <input type="hidden" id="chushiId' + subTableLineNum + '" name="chushiId" /><!-- 处室ID隐藏域 -->';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 45%">';
    trCustom += '   <div class="files row">\n' +
        '       <div id="' + (subTableLineNum+10000) + '" class="col-md-10" >\n' +
        '       </div>\n' +
        '   </div>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 20%">';
    trCustom += '   <span class="btn btn-primary fileinput-button">\n' +
        '       <i class="glyphicon glyphicon-plus"></i>\n' +
        '       <span>选择文件...</span>\n' +
        '       <input id="fileupload' + (subTableLineNum+10000) + '" type="file" name="files[]" multiple>\n' +
        '   </span>\n';
    trCustom += '</td>';
    trCustom += '</tr>';

    return trCustom;
}

/**
 * 删除一行
 */
function deleteTabFn(ids,affixIds) {
    var resId = $("#resId").val();
    var flag = false;//如果删除成功改为true
    // 删除操作
    $.ajax({
        url: '/dagl/bygl/studying/deleteStudyingSub?ids=' + ids + "&affixIds=" + affixIds + "&resId=" + resId
        , type: "GET"
        , dataType: "json"
        , async: false
        , success: function (result) {
            if (result.flag === "1") {
                flag = true;

                //删除之后重新表单数据渲染,因为删除之后继续新增一行的时候，部门的input框名称重复
                var datas = {"id": $("#id").val(), "resId": $("#resId").val()};
                httpRequest("get", "/dagl/bygl/studying/getStudyingById", datas, function (data) {
                    console.log('初始化页面', data);
                    DisplayData.playData({data: data});
                    iniTable(data);
                    iniFileUpload(data);
                });

                parent.TableInit.refTable('right_table');
                layer.msg("删除成功！", {icon: 1});
            } else {
                layer.msg("删除失败！", {icon: 2});
            }
        }
        , error: function () {
            layer.msg('删除失败！', {icon: 2});
        }
    });
    return flag;
}


/**
 * 提交表单
 * @returns
 */
function commitForm() {
    var json = saveForm();
    if (json.flag && json.flag == "1") {
        /*if ($("#isRenew").val() == "1"){
            layer.msg("保存成功，如果直接关闭页面，数据会在草稿列表显示！", {icon: 1});
        }else{*/
        layer.msg("保存成功！", {icon: 1});
        // }
        //刷新列表
        parent.TableInit.refTable('right_table');
    } else if (json.flag && json.flag == "2") {

    } else {
        layer.msg("保存失败！", {icon: 2});
    }
}

/**
 * 保存
 */
function saveForm() {
    var json = {"flag":""};
    var checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
    if (checkResult) {
        var bootstrapValidator = $("#form").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {

            var data = JSON.stringify(getFormData());
            $.ajax({
                type: "POST",
                url: "/dagl/bygl/studying/saveStudying",
                contentType: "application/json",
                data: data,
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.flag == '1') {

                        json = data;
                        $("#id").val(data.data.id);
                        $("#title").val(data.data.title);
                        $("#year").val(data.data.year);
                        var studyingSubs = data.data.studyingSubs;
                        for(var i=0;i<studyingSubs.length;i++){
                            MyFileUpload.saveDocIdToAffix({docId:studyingSubs[i].id,fileListId: ""+(i+10001)});
                        }
                        iniTable(data);
                        iniFileUpload(data);
                    }
                },
                error: function () {
                }
            });
        } else {
            json.flag = "2";
        }
    } else {
        json.flag = "2";
    }
    console.log(json.flag);
    return json;
}

function getFormData() {

    var studying = {
        resId: $('#resId').val(),
        id: $("#id").val(),
        title: $("#title").val(),
        year: $("#year").val(),
        studyingSubs: new Array()//字表数据
    };//需要提交到后台的数据

    //遍历页面中子表1信息 形成 subTableList
    var $subTableRows = $('#otherPeople').find("tr");//所有子表信息行
    $subTableRows.each(function (i, index) {
        var studyingSubs = {
            id: $(index).find('[name="id"]').val(),
            recChushiName: $(index).find('[name="chushiName"]').val(),
            recChushiId: $(index).find('[name="chushiId"]').val(),
            fjId: $(index).find('a').attr("id"),
            sequence: i+1
        }
        studying.studyingSubs.push(studyingSubs);
    });
    return studying;
}


function finishFlow(finishFlag) {
    var flag = saveForm();
    completeFlow('', finishFlag);
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


/**
 * 初始化文件上传
 */
function iniFileUpload(data) {
    if (data != undefined) {
        if (!$.isEmptyObject(data.data.studyingSubs)) {
            for(var i=0;i<data.data.studyingSubs.length;i++){
                var id = data.data.studyingSubs[i].id;
                MyFileUpload.init({
                    viewParams: { "tableId": id, "tableName": "studying" },
                    editOrView: "VIEW",
                    domId: "fileupload"+(i+10001),
                    fileListId: ""+(i+10001),  //文件列表显示的div
                    fileTypes:"docx",
                    maxFileCount:1
                });
                if('1' == data.data.studyingSubs[i].isBack){
                    //如果信息反馈回来了，那么久回显附件，不然则不显示
                    MyFileUpload.init({
                        viewParams: { "tableId": id, "tableName": "studying_bak" },
                        editOrView: "VIEW",
                        domId: "fileupload"+(i+20001),
                        fileListId: ""+(i+20001),  //文件列表显示的div
                        fileTypes:"docx",
                        maxFileCount:1
                    });
                }
            }
            //初始化汇总的文件
            MyFileUpload.init({
                viewParams: { "tableId": $("#id").val(), "tableName": "dagl_bygl_main" },
                editOrView: "VIEW",
                domId: "fileupload",
                fileListId: "files",  //文件列表显示的div
                fileTypes:"docx",
                maxFileCount:1
            });
        }
    }else{
        subTableLineNum = $("#otherPeople").find("tr").length;

        MyFileUpload.init({
            viewParams: { "tableId": $("#id").val(), "tableName": "studying" },
            editOrView: "NEW",
            domId: "fileupload"+(subTableLineNum+10000),
            fileListId: ""+(subTableLineNum+10000),  //文件列表显示的div
            fileTypes:"docx",
            maxFileCount:1
        });
        //初始化汇总的文件
        MyFileUpload.init({
            viewParams: { "tableId": $("#id").val(), "tableName": "dagl_bygl_main" },
            editOrView: "VIEW",
            domId: "fileupload",
            fileListId: "files",  //文件列表显示的div
            fileTypes:"docx",
            maxFileCount:1
        });
    }

}

/**
 * 确认按钮
 */
function StudyingSubOk(id,chushiName,makeSureAId,sendBackAId,statusTdId){

    $("#resId").val($('#left_ul').find('a.active').attr("id"));
    layer.confirm(chushiName+"编研信息确定无误？确定之后不可再次退回到处室！",{icon:3,title:'确认消息'}, function(e) {
        layer.close(e);
        $.ajax({
            url : "/dagl/bygl/studying/StudyingSubOk",
            data : {
                resId:$("#resId").val(),
                id: id,
            },
            type : "get",
            success : function(data){
                if("1" == data.flag){ //0 查询有数据， 可导出
                    $("#"+makeSureAId).hide();//根据id隐藏确认按钮
                    $("#"+sendBackAId).hide();
                    $("#"+statusTdId).text("已确认");
                    layer.msg('确认信息成功！', {icon : 1});

                }else{

                    layer.msg('确认信息失败！', {icon : 0});
                }
            }
        })
    })
}

/**
 * @Author 王富康
 * @Description //TODO 退回
 * @Date 2019/1/2 17:37
 **/
function sendBack(id,chushiName,makeSureAId,sendBackAId,statusTdId,fjDivId,chushiId) {
    $("#resId").val($('#left_ul').find('a.active').attr("id"));
    layer.confirm("确定退回编研信息到"+chushiName+"，进行重新编辑？",{icon:3,title:'确认消息'}, function(e) {
        layer.close(e);
        $.ajax({
            url : "/dagl/bygl/studying/updateStudyingSubIsBack?rdm="+Math.random(),
            data : {
                resId:$("#resId").val(),
                id: id,
                type:"send"
            },
            type : "get",
            success : function(data){
                if("1" == data.flag){ //0 更新状态成功
                    $("#"+makeSureAId).hide();//根据id隐藏确认按钮
                    $("#"+sendBackAId).hide();
                    $("#"+statusTdId).text("未反馈");//暂时修改页面的状态
                    document.getElementById(fjDivId).style.display="none";//隐藏
                    //发送待办

                    //发送待办
                        //去掉最后一个，号
                        var par = {
                            "deptNames":chushiName,//处室名称
                            "deptIds":chushiId,//处室id
                            "roleNo":"D705",//上报员角色序号
                        };
                        //选择了处室
                        var UserJson = hasReportUser(par);
                        //判断是否有上报员
                        if(UserJson.flag=="1"){
                            layer.close(layer.index);//再执行关闭

                            var user = UserJson.data;
                            var index = 1;
                            //发送待办
                            var params = {
                                "user":JSON.stringify(user),
                                "id":$("#id").val(),//编研id
                                "subject":$("#title").text(),//消息标题
                                "content":"请查看本单位的编研信息，编辑无误后，发送档案馆管理员",//消息内容
                                "messageURL":"/modules/dagl/bygl/studyingReadOnlyForm.html",//消息的URL
                                "daibanURL":"/modules/dagl/bygl/studyingReadOnlyForm.html",//待办url
                                "opName":"退回档案编研信息确认"//操作类型
                            };
                            $.ajax({
                                url:"/dagl/bygl/studying/sendWaitNoflow?rdm="+Math.random(),
                                data:params,
                                dataType:"json",
                                success:function(result){
                                    if(result.flag=="1"){
                                        layer.close(index);
                                        layer.msg('退回编研信息成功！', {icon : 1});
                                    }else{
                                        layer.msg("退回编研信息失败！", {icon: 2});
                                    }
                                },
                                beforeSend:function(xhr){
                                    //加载动画
                                    index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
                                            layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
                                        } });
                                }

                            });
                        }else{
                            var names = UserJson.data;
                            console.log(UserJson.data);
                            layer.alert('提交失败！'+names+'没有立卷单位管理员，请联系系统管理员进行配置后再试！',
                                {icon:0,title:'警告'});
                        }
                }else{

                    layer.msg('退回编研信息失败！', {icon : 0});
                }
            }
        })
    })
}
/**
 * 判断是否有上报员
 */
function hasReportUser(par){
    var res = {};
    var params = {
        "deptNames":par.deptNames,//处室名称
        "deptIds":par.deptIds,//处室id
        "roleNo":par.roleNo,//上报员角色序号
    };
    $.ajax({
        url:"/dagl/bygl/studying/hasReportUser",
        data:params,
        dataType:"json",
        async: false,
        success:function(json){
            res =  json;
        }
    });
    return res;
}

/**
 * @Author 王富康
 * @Description //TODO 汇总
 * @Date 2019/1/2 17:37
 **/
function huizong(){
    //首先查询还未确认的编研信息
    $.ajax({
        url:"/dagl/bygl/studying/queryNotConfirm?rdm="+Math.random(),
        data:{"bianYanId":$("#id").val()},
        dataType:"json",
        success:function(result){
            if(result.flag=="1"){

                $("#resId").val($('#left_ul').find('a.active').attr("id"));
                if(result.data.length > 0){
                    //询问
                    //判断，如果反馈的数量等于列表的数量，则为没有反馈的处室
                    subTableLineNum = $("#otherPeople").find("tr").length;
                    var NotMakeSureNum = result.data.split(",").length;
                    if(NotMakeSureNum == subTableLineNum){
                        layer.msg("无可汇总的编研信息！", {icon: 0,time:1000});
                        return;
                    }
                    layer.confirm(result.data+"编研信息尚未确认，是否继续进行汇总？",{icon:3,title:'确认消息'}, function(e) {
                        layer.close(e);
                        $.ajax({
                            url : "/dagl/bygl/studying/huiZongById?rdm="+Math.random(),
                            data : {
                                resId:$("#resId").val(),
                                id: $("#id").val()
                            },
                            type : "get",
                            dataType:"json",
                            success : function(data){
                                if("1" == data.flag){ //0 查询有数据， 可导出
                                    //关闭等待框
                                    layer.close(index);
                                    //下载附件
                                    downloadAffix(data.data);
                                    //清空div的数据
                                    $('#files').html("");
                                    //回显新的附件
                                    MyFileUpload.init({
                                        viewParams: { "tableId": $("#id").val(), "tableName": "dagl_bygl_main" },
                                        editOrView: "VIEW",
                                        domId: "fileupload",
                                        fileListId: "files",  //文件列表显示的div
                                        fileTypes:"docx",
                                        maxFileCount:1
                                    });
                                    parent.TableInit.refTable('right_table');
                                    layer.msg('汇总成功！', {icon : 1});
                                }else{
                                    layer.msg('汇总失败！', {icon : 0});
                                }
                            },
                            beforeSend:function(xhr){
                                //加载动画
                                index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
                                        layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
                                    } });
                            }
                        })
                    })
                }else{
                    //判断，如果列表无信息则无法汇总
                    subTableLineNum = $("#otherPeople").find("tr").length;
                    var NotMakeSureNum = result.data.split(",").length;
                    if(0 == subTableLineNum){
                        layer.msg("无可汇总的编研信息！", {icon: 0,time:1000});
                        return;
                    }
                    //全部已确认，直接汇总
                    layer.confirm("确认汇总？",{icon:3,title:'确认消息'}, function(e) {
                        layer.close(e);
                        $.ajax({
                            url : "/dagl/bygl/studying/huiZongById?rdm="+Math.random(),
                            data : {
                                resId:$("#resId").val(),
                                id: $("#id").val()
                            },
                            type : "get",
                            dataType:"json",
                            success : function(data){
                                if("1" == data.flag){ //0 查询有数据， 可导出
                                    //关闭等待框
                                    layer.close(index);
                                    //下载附件
                                    downloadAffix(data.data);
                                    //清空div的数据
                                    $('#files').html("");
                                    //回显新的附件
                                    MyFileUpload.init({
                                        viewParams: { "tableId": $("#id").val(), "tableName": "dagl_bygl_main" },
                                        editOrView: "VIEW",
                                        domId: "fileupload",
                                        fileListId: "files",  //文件列表显示的div
                                        fileTypes:"docx",
                                        maxFileCount:1
                                    });
                                    layer.msg('汇总成功！', {icon : 1});
                                }else{
                                    //关闭等待框
                                    layer.close(index);
                                    layer.msg('汇总失败！', {icon : 0});
                                }
                            },
                            beforeSend:function(xhr){
                                //加载动画
                                index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
                                        layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
                                    } });
                            }
                        })
                    })
                }


            }else{
                layer.msg("查询编研信息失败失败！", {icon: 2});
            }
        }
    });
}

/**
 * 下载总的附件
 */
function downloadAffix(id){
    window.location.href = "/system/component/affix/download?affixId="+id
}

//关闭弹框
function closeForm(){
    MyLayer.closeOpen()
}