
var subTableLineNum = 1;
var count = 0;
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

                    //表单数据渲染
                    var datas = {"id": $("#id").val(), "resId": $("#resId").val()};
                    httpRequest("POST", "/dagl/bygl/studying/getStudyingById?rdm="+Math.random(), datas, function (data) {
                        DisplayData.playData({data: data});
                        var chushiIds = "";
                        var chushiNames = "";
                        if (data != undefined) {
                            if (!$.isEmptyObject(data.data.studyingSubs)) {
                                var studyingSubs = data.data.studyingSubs;
                                for(var i=0;i<studyingSubs.length;i++){
                                    chushiNames += studyingSubs[i].recChushiName+",";
                                    chushiIds += studyingSubs[i].recChushiId+",";
                                }
                                if("" != chushiIds){
                                    //去掉最后一个，号
                                    chushiIds = chushiIds.substring(0,chushiIds.length-1);
                                    chushiNames = chushiNames.substring(0,chushiNames.length-1);

                                    $("#chushiId").val(chushiIds);
                                    $("#chushiName").val(chushiNames);
                                }
                            }
                        }

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
    var studyingSubs = [];
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
    //全选删除后全选按钮取消选中
    $("#checkboxSuccess").prop("checked",false);
}

function checkMain(){
    if("" == $("#id").val()){
        layer.msg("请先保存编研信息！",{icon:0,time:1000});
    }else{
        openSelectZtree({'id':'chushiId','name':'chushiName','type':'2','asyn':false,'level':2,'url':'/system/component/tree/deptTree'});
    }
}

/**
 * 批量添加
 */
function addRows(obj) {

    var oldChushiIds = '';
    var oldChushiNames = '';

    if("" == $("#id").val()){
        layer.msg("请先保存编研信息！",{icon:0,time:1000});

        //清空已选的处室
        $("#chushiName").val("");
        $("#chushiId").val("");
    }else{
        var chushinames = $("#chushiName").val();//获取批量添加的名称
        var chushids = $("#chushiId").val();//获取批量添加的id
        if("" != chushids){
            chushinames = chushinames.split(",");
            chushids = chushids.split(",");
        }
        var addChushiNames = "";
        var deleteCchushiNames = "";

            var $subTableRows = $('#otherPeople').find("tr");//所有子表信息行
            $subTableRows.each(function (i, index) {
                var chushiId = $(index).find('[name="chushiId"]').val();
                var chushiName = $(index).find('[name="chushiName"]').val();

                if("" != chushiId){
                    if(chushids.indexOf(chushiId) != -1){//如果批量的处室中有该处室，就不在进行新增，从chushiId中删除掉
                        chushids.splice(chushids.indexOf(chushiId),1);//删除表中已经存在的处室id
                        chushinames.splice(chushinames.indexOf(chushiName),1);//删除表中已经存在的处室name
                    }else{//批量的处室中没有该处室，则证明删除了
                        deleteCchushiNames += chushiName+",";
                    }
                }
                oldChushiIds += chushiId+",";
                oldChushiNames += chushiName+",";
            });
            if("" != deleteCchushiNames){

                deleteCchushiNames = deleteCchushiNames.substring(0,deleteCchushiNames.length-1).split(",");
            }

            //将处室的名字改为【】包含的形式
            var chushiNameMsg = "";
            var deleteChushiNameMsg = "";
            for(var n =0;n<chushinames.length;n++){
                chushiNameMsg +="【"+chushinames[n]+"】";
            }
            for(var m=0;m<deleteCchushiNames.length;m++){
                deleteChushiNameMsg += "【"+deleteCchushiNames[m]+"】";
            }
            var msg = "点击确认将会 ";

            if("" != chushinames){
                msg += "\n 新增"+chushiNameMsg+"的编研信息！";
            }
            if("" != deleteCchushiNames){
                msg += "\n 删除"+deleteChushiNameMsg+"的编研信息！";
            }

            if("" != chushinames || "" != deleteCchushiNames){
                layer.confirm(msg,
                    {
                        icon:3
                        //,title:'删除'
                        ,btn: ['确定','取消'] //按钮
                        ,btn1:function(e){
                            layer.close(e);

                            //加载动画
                            index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
                                    layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
                                    setTimeout(function(){
                                        //删除
                                        var deleteChushiNames = "";
                                        if("" != deleteCchushiNames){
                                            deleteChushiNames = deleteCchushiNames;
                                        }
                                        var ids = [];
                                        var affixIds = [];//附件的id
                                        for(var m=0;m<deleteChushiNames.length;m++){
                                            var $subTableRowsA = $('#otherPeople').find("tr");//所有子表信息行
                                            $subTableRowsA.each(function (a, indexp) {
                                                var chushiId = $(indexp).find('[name="chushiId"]').val();
                                                var chushiName = $(indexp).find('[name="chushiName"]').val();
                                                if(chushiName == deleteChushiNames[m] && "" != deleteChushiNames[m]){
                                                    //相等的话，移除
                                                    //准备删除
                                                    //删除的是已经保存的数据
                                                    var fjid = $(indexp).find('a').attr("id");
                                                    var id = $(indexp).find('[name="id"]').val();
                                                    if("" != id){
                                                        ids.push(id);
                                                    }
                                                    if("" != fjid && undefined != fjid && null != fjid){
                                                        affixIds.push(fjid);
                                                    }
                                                    // 删除操作

                                                }
                                            });
                                            if(m==deleteChushiNames.length-1){
                                                if("" != chushids){
                                                    //这里防止附件重复加载，如果有新增的，只在新增那里初始化一下附件即可
                                                    deleteTabFn(ids,affixIds,"PLdelete");
                                                }else{
                                                    deleteTabFn(ids,affixIds);
                                                }

                                            }
                                        }


                                        //遍历选择的处室id，新增
                                        for(var i=0;i<chushids.length;i++){

                                            subTableLineNum = $("#otherPeople").find("tr").length;
                                            subTableLineNum++;

                                            var trCustom = "";
                                            trCustom += '<tr>';
                                            trCustom += '<td class="text-center" style="width: 50px;">' +
                                                '   <input type="checkbox" name="checkboxName" value="option1">' +
                                                '   <input type="hidden" id="id' + subTableLineNum + '" name="id">' +
                                                '</td>';
                                            trCustom += '<td class="text-center" name="numName" style="width: 50px;"></td>';
                                            trCustom += '<td class="text-center" style="width: 200px;">'+chushinames[i];
                                            trCustom += '	<input type="hidden" id="chushiName' + subTableLineNum + '" name="chushiName" value ="'+chushinames[i]+'" />';
                                            trCustom += '   <input type="hidden" id="chushiId' + subTableLineNum + '" name="chushiId" value = "'+chushids[i]+'"/><!-- 处室ID隐藏域 -->';
                                            trCustom += '</td>';
                                            trCustom += '<td class="text-center" style="width: 45px;">';
                                            trCustom += '   <div class="files row">\n' +
                                                '       <div id="' + (subTableLineNum+10000) + '" class="col-md-10" style="margin: 0 auto;width: 450px;">\n' +
                                                '       </div>\n' +
                                                '   </div>';
                                            trCustom += '</td>';
                                            trCustom += '<td class="text-center" style="width: 200px;">';
                                            trCustom += '   <span class="btn btn-primary fileinput-button">\n' +
                                                '       <i class="glyphicon glyphicon-plus"></i>\n' +
                                                '       <span>选择文件...</span>\n' +
                                                '       <input id="fileupload' + (subTableLineNum+10000) + '" type="file" name="files[]" multiple>\n' +
                                                '   </span>\n';
                                            trCustom += '</td>';
                                            trCustom += '</tr>';

                                            // 获取当前有几行
                                            var len = 0;
                                            var _tds = null;
                                            $('#otherPeople').find('tbody').append(trCustom);
                                            // 判断是否拥有序号
                                            _tds = $('#otherPeople').find("td[name= 'numName']");
                                            // 是否是升序
                                            $(_tds).each(function(i){
                                                _tds[i].innerHTML = (i+1);
                                            })
                                            if(i == chushids.length-1){
                                                //新增完页面执行保存操作
                                                saveForm();
                                            }
                                        }

                                        //新增，删除之后为了统一处室选择框和列表的统一，重新赋一遍选择框的值
                                        var chushiIdData = "";
                                        var chushiNameData = "";
                                        var $subTableRowsPLalert = $('#otherPeople').find("tr");//所有子表信息行
                                        $subTableRowsPLalert.each(function (b, indexB) {
                                            chushiIdData +=$(indexB).find('[name="chushiId"]').val()+",";
                                            chushiNameData += $(indexB).find('[name="chushiName"]').val()+",";
                                        });
                                        if("" != chushiIdData){
                                            $("#chushiId").val(chushiIdData.substring(0,chushiIdData.length-1));
                                            $("#chushiName").val(chushiNameData.substring(0,chushiNameData.length-1));
                                        }

                                        layer.close(index);
                                    },1000)
                            } });

                        },btn2:function(e){
                            //取消什么都不做
                            layer.close(e);
                            //取消的话，回填之前的处室信息
                            if("" == oldChushiIds){
                                $("#chushiName").val(oldChushiNames);
                                $("#chushiId").val(oldChushiIds);
                            }else{
                                $("#chushiName").val(oldChushiNames.substring(0,oldChushiNames.length-1));
                                $("#chushiId").val(oldChushiIds.substring(0,oldChushiIds.length-1));
                            }

                        }
                    });
            }else{
                layer.msg("暂无可新增/删除的数据！",{icon:0,time:1000});
            }
    }
}

/**
 * 添加一行
 */
function addTabFn(obj) {

    subTableLineNum = $("#otherPeople").find("tr").length;
    subTableLineNum++;

    var trCustom = "";
        trCustom += '<tr>';
        trCustom += '<td class="text-center" style="width: 50px;">' +
                    '   <input type="checkbox" name="checkboxName" value="option1">' +
                    '   <input type="hidden" id="id' + subTableLineNum + '" name="id">' +
                    '</td>';
        trCustom += '<td class="text-center" name="numName" style="width: 50px;"></td>';
        trCustom += '<td class="text-center" style="width: 200px;">';
        trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="50"  CK_info="处室" class="form-control" id="chushiName' + subTableLineNum + '" name="chushiName" onclick="openSelectZtree({\'id\':\'chushiId'+ subTableLineNum +'\',\'name\':\'chushiName'+ subTableLineNum +'\',\'type\':\'2\',\'asyn\':false,\'level\':2,\'url\':\'/system/component/tree/deptTree\',\'isMulti\':\'1\'}) "/>';
        trCustom += '   <input type="hidden" id="chushiId' + subTableLineNum + '" name="chushiId" /><!-- 处室ID隐藏域 -->';
        trCustom += '</td>';
        trCustom += '<td class="text-center" style="width: 45px;">';
        trCustom += '   <div class="files row">\n' +
                    '       <div id="' + (subTableLineNum+10000) + '" class="col-md-10" style="margin: 0 auto;width: 450px;">\n' +
                    '       </div>\n' +
                    '   </div>';
        trCustom += '</td>';
        trCustom += '<td class="text-center" style="width: 200px;">';
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
function deleteTabFn(ids,affixIds,type) {
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
                    httpRequest("POST", "/dagl/bygl/studying/getStudyingById?rdm="+Math.random(), datas, function (data) {
                        DisplayData.playData({data: data});

                        var chushiIds = "";
                        var chushiNames = "";
                        if (data != undefined) {
                            if (!$.isEmptyObject(data.data.studyingSubs)) {
                                var studyingSubs = data.data.studyingSubs;
                                for(var i=0;i<studyingSubs.length;i++){
                                    chushiNames += studyingSubs[i].recChushiName+",";
                                    chushiIds += studyingSubs[i].recChushiId+",";
                                }
                                if("" != chushiIds){
                                    //去掉最后一个，号
                                    chushiIds = chushiIds.substring(0,chushiIds.length-1);
                                    chushiNames = chushiNames.substring(0,chushiNames.length-1);
                                }
                            }
                        }
                        $("#chushiId").val(chushiIds);
                        $("#chushiName").val(chushiNames);

                        iniTable(data);
                        if("PLdelete" == type){

                        }else{
                            iniFileUpload(data);
                        }
                        layer.close(index);
                    });

                    parent.TableInit.refTable('right_table');
                    layer.msg("删除成功！", {icon: 1});
                } else {
                    layer.msg("删除失败！", {icon: 2});
                }
            }
            , error: function () {
                layer.msg('删除失败！', {icon: 2});
            },
            beforeSend:function(xhr){

            }
        });
    return flag;
}


/**
 * 发送表单
 * @returns
 */
function commitForm() {

    var json = {"flag":""};
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        var studying = {
            resId: $('#resId').val(),
            id: $("#id").val(),
            title: $("#title").val(),
            year: $("#year").val(),
            studyingSubs: new Array(),//字表数据
            msg : ""
        };//需要发送到后台的数据

        $.ajax({
            type: "POST",
            url: "/dagl/bygl/studying/saveStudying?rdm="+Math.random(),
            contentType: "application/json",
            data: JSON.stringify(studying),
            dataType: "json",
            cache:false,//不使用缓存
            async: false,
            success: function (res) {
                if (res.flag == '1') {
                    json = res;
                    $("#id").val(res.data.id);
                    $("#title").val(res.data.title);
                    $("#year").val(res.data.year);
                }
            },
            error: function () {
            }
        });
    } else {
        json.flag = "2";
    }

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
        var bootstrapValidator = $("#form").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            var data = getFormData();
            $.ajax({
                type: "POST",
                url: "/dagl/bygl/studying/saveStudying?rdm="+Math.random(),
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                cache:false,//不使用缓存
                async: false,
                success: function (res) {
                    if (res.flag == '1') {
                        json = res;
                        $("#id").val(res.data.id);
                        $("#title").val(res.data.title);
                        $("#year").val(res.data.year);
                        /*var studyingSubs = data.data.studyingSubs;
                        for(var i=0;i<studyingSubs.length;i++){
                            MyFileUpload.saveDocIdToAffix({docId:studyingSubs[i].id,fileListId: ""+(i+10001)});
                        }*/
                        iniTable(res);
                        iniFileUpload(res);
                    }
                },
                error: function () {
                }
            });
        } else {
            json.flag = "2";
        }
    return json;
}

function getFormData() {

    var studying = {
        resId: $('#resId').val(),
        id: $("#id").val(),
        title: $("#title").val(),
        year: $("#year").val(),
        studyingSubs: new Array(),//字表数据
        msg : ""
    };//需要发送到后台的数据

    //遍历页面中子表1信息 形成 subTableList
    var $subTableRows = $('#otherPeople').find("tr");//所有子表信息行
    $subTableRows.each(function (i, index) {
        var fjid = $(index).find('a').attr("id");

        if(undefined == fjid){
            studying.msg += "【"+$(index).find('[name="chushiName"]').val()+"】";
        }

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
                    editOrView: "EDIT",
                    domId: "fileupload"+(i+10001),
                    fileListId: ""+(i+10001),  //文件列表显示的div
                    fileTypes:"docx",
                    maxFileCount:1
                });
            }
        }/*else{
            subTableLineNum = $("#otherPeople").find("tr").length;

            MyFileUpload.init({
                viewParams: { "tableId": $("#id").val(), "tableName": "studying" },
                editOrView: "NEW",
                domId: "fileupload"+(subTableLineNum+10000),
                fileListId: ""+(subTableLineNum+10000),  //文件列表显示的div
                fileTypes:"docx",
                maxFileCount:1
            });
        }*/
    }/*else{
        subTableLineNum = $("#otherPeople").find("tr").length;

        MyFileUpload.init({
            viewParams: { "tableId": $("#id").val(), "tableName": "studying" },
            editOrView: "NEW",
            domId: "fileupload"+(subTableLineNum+10000),
            fileListId: ""+(subTableLineNum+10000),  //文件列表显示的div
            fileTypes:"docx",
            maxFileCount:1
        });
    }*/

}

/**
 * 发送（上报人员待办）
 */
function sendWaitNoflow(){

    var checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
    if (checkResult) {
        var bootstrapValidator = $("#form").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {

            var data = getFormData();
            if(data.msg.length >0){
                //有错误信息
                layer.alert("请上传 "+data.msg+"的附件！",{icon: 0});
            }else{
                var json = saveForm();
                if (json.flag && json.flag == "1") {

                    var unitIds = "";
                    var unitNames = "";
                    var studyingSubs = json.data.studyingSubs;
                    for(var i=0;i<studyingSubs.length;i++){
                        unitIds += studyingSubs[i].recChushiId+",";
                        unitNames += studyingSubs[i].recChushiName+",";
                    }
                    //是:上报考试人员
                    if(unitIds.length>0){
                        //去掉最后一个，号
                        unitNames = unitNames.substring(0,unitNames.length-1);
                        unitIds = unitIds.substring(0,unitIds.length-1);
                        var par = {
                            "deptNames":unitNames,//处室名称
                            "deptIds":unitIds,//处室id
                            "roleNo":"D705"//上报员角色序号
                        };
                        //选择了处室
                        var UserJson = hasReportUser(par);
                        //判断是否有上报员
                        if(UserJson.flag=="1"){
                            layer.close(layer.index);//再执行关闭
                            if(count == 0){
                                layer.confirm('确定发送吗？点击确定按钮后将分发信息,并且离开本页！',
                                    {icon:3,title:'提示'},
                                    function(){
                                        var user = UserJson.data;

                                        var index = 1;
                                        //发送待办
                                        var params = {
                                            "user":JSON.stringify(user),
                                            "id":$("#id").val(),//编研id
                                            "subject":$("#title").val(),//消息标题
                                            "content":"请查看本单位的编研信息，编辑无误后，发送档案馆管理员",//消息内容
                                            "messageURL":"/modules/dagl/bygl/studyingReadOnlyForm.html",//消息的URL
                                            "daibanURL":"/modules/dagl/bygl/studyingReadOnlyForm.html",//待办url
                                            "opName":"档案编研信息确认"//操作类型
                                        };
                                        $.ajax({
                                            url:"/dagl/bygl/studying/sendWaitNoflow?rdm="+Math.random(),
                                            data:params,
                                            dataType:"json",
                                            success:function(result){
                                                if(result.flag=="1"){
                                                    count = count +1;
                                                    //刷新列表
                                                    layer.close(index);
                                                    parent.TableInit.refTable('right_table');

                                                    MyLayer.closeOpen();
                                                    window.parent.layer.msg("发送成功！", {icon: 1});
                                                    /*layer.confirm('编研信息分发成功是否离开本页！',
                                                        {icon:3,title:'提示'},
                                                        function(){
                                                            MyLayer.closeOpen();
                                                        });*/
                                                }else{
                                                    layer.msg("发送失败！", {icon: 2});
                                                }
                                            },
                                            beforeSend:function(xhr){
                                                //加载动画
                                                index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
                                                        layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
                                                    } });
                                            }

                                        });
                                    });
                            }else{
                                layer.msg("已经发送，不可重复发送！", {icon: 0});
                            }
                        }else{
                            var names = UserJson.data;
                            layer.alert('发送失败！'+names+'没有立卷单位管理员，请联系系统管理员进行配置后再试！',
                                {icon:0,title:'警告'});
                        }
                    }else{
                        layer.msg("请选择处室进行分发！", {icon: 0});
                    }

                    //刷新列表
                    parent.TableInit.refTable('right_table');
                } else if (json.flag && json.flag == "2") {

                } else {
                    layer.msg("保存失败！", {icon: 2});
                }
            }
        }
    }

}

/**
 * 判断是否有上报员
 */
function hasReportUser(par){
    var res = {};
    var params = {
        "deptNames":par.deptNames,//处室名称
        "deptIds":par.deptIds,//处室id
        "roleNo":par.roleNo//上报员角色序号
    };
    $.ajax({
        url:"/dagl/bygl/studying/hasReportUser?rdm="+Math.random(),
        data:params,
        dataType:"json",
        async: false,
        success:function(json){
            res =  json;
        }
    });
    return res;
}