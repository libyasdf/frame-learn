fileList = [{
    id: '',
    isBorrow: '',
    categoryName: '',
    archiveNo: '',
    mainTitle: '',
    ljdwMark: '',
    basefolderUnit: '',
    recid: '',
    categoryNo: '',
    tableName: '',
    orderNum: ''
}];
$(function () {

    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    //根据mark获取一个以code、name为键值对的map，用来根据code取对应的字典名称
    var usePurposeMap = "";
    if ($("#opertation").val() != "VIEW") {
        //初始化字典项
        //初始化数据字典类型
        Dictionary.init({
            position: "usePurpose",
            mark: "dagl_usePurpose",
            type: "1",
            name: "usePurpose",
            domType: "select"
        });
    } else {
        usePurposeMap = Dictionary.getNameAndCode({mark: "dagl_usePurpose", type: "1"});
    }

    /**
     * 初始化页面，数据加载、渲染
     */
    if ($("#id").val() != "") {

        //表单数据渲染
        var datas = {"id": $("#id").val(), "resId": $("#resId").val()};
        httpRequest("get", "/dagl/daly/borrow/edit", datas, function (data) {
            DisplayData.playData({data: data});
            iniTable(data, "1");
            flowTypeChange();
            $('#flowType1').attr("disabled", true);
            $('#flowType2').attr("disabled", true);
        });
        if ($("#opertation").val() == "VIEW") {
            $("#usePurpose").html(usePurposeMap[$("#usePurpose").text()]);
            if ($("#flowType").text() == '1') {
                $("#flowType").html("借阅本单位");
            } else {
                $("#flowType").html("借阅非本单位");
            }
        } else {
            getStartWf();
        }
    } else {//id为空时，表示新建草稿状态，加载启动节点按钮及启动节点提示信息
        getStartWf();
        iniTable();
        flowTypeChange();
    }
    $('#flowType1').attr("disabled", true);
    $('#flowType2').attr("disabled", true);
    $('#usePurpose').attr("disabled", true);
    $('#phone').attr("disabled", true);
    $('#borrowUserName').attr("disabled", true);
    $('#borrowUnitName').attr("disabled", true);
    $('#approveUnitName').attr("disabled", true);
    $('#remark').attr("disabled", true);
    $(".input-group-addon").attr("onclick", "null");
    if ($('#flowType input[name="flowType"]:checked ').val() == "1") {//本单位
        $('#searchId').hide();
    }
});

function iniTable(data, flag) {
    if (data && data != undefined) {
        if (!$.isEmptyObject(data.data.fileList)) {
            fileList = data.data.fileList;
        } else {
            if (flag != "1") {
                fileList = data.data.fileList;
            }
            if ($("#opertation").val() == "VIEW") {
                fileList = data.data.fileList;
            }
        }
    }
    var html = $('[name=main]').html();
    nodetpl.render(html, {data: fileList}, function (html) {
        $('#otherPeople').find('tbody').empty().append(html);
        for (var i = 0; i < fileList.length; i++) {
            if ($("#opertation").val() != "VIEW") {
                Dictionary.init({
                    position: "ljdwMark" + (i + 1),
                    mark: "dagl_ljdw",
                    type: "1",
                    name: "ljdwMark",
                    domType: "select"
                });
                $("#ljdwMark" + (i + 1)).val(fileList[i].ljdwMark);
            } else {
                var ljdwMap = Dictionary.getNameAndCode({mark: "dagl_ljdw", type: "1"});
                $("#ljdwMark" + (i + 1)).html(ljdwMap[$("#ljdwMark" + (i + 1)).text()]);
            }

        }

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
        })
    });
}

/**
 * 添加一行
 */
function addTabFn(obj) {
    var subTableLineNum = $("#otherPeople").find("tr").length;
    subTableLineNum++;
    var trCustom = "";
    trCustom += '<tr>';
    trCustom += '<td class="text-center" style="width: 3%"><input type="checkbox" name="checkboxName" value="option1"></td>';
    trCustom += '<td class="text-center" name="numName" style="width: 3%"></td>';
    trCustom += '<td class="text-center" style="width: 3%"><input type="hidden" id="isBorrow' + subTableLineNum + '" name="isBorrow" value="未入库"><span >未入库</span></td>';
    trCustom += '<td class="text-center" style="width: 6%">';
    trCustom += '<input type="hidden" id="id' + subTableLineNum + '" name="id" />';
    trCustom += '<input type="hidden" id="recid' + subTableLineNum + '" name="recid" />';
    trCustom += '<input type="hidden" id="tableName' + subTableLineNum + '" name="tableName" />';
    trCustom += '<input type="hidden" id="categoryNo' + subTableLineNum + '" name="categoryNo"/>';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="50" CK_info="门类名称"  placeholder="请输入门类名称" class="form-control" id="categoryName' + subTableLineNum + '" name="categoryName" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 8%">';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="100" CK_info="档号"  placeholder="请输入档号" class="form-control" id="archiveNo' + subTableLineNum + '" name="archiveNo" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 8%">';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="200"  CK_info="题名"  placeholder="请输入题名" class="form-control" id="mainTitle' + subTableLineNum + '" name="mainTitle" />';
    trCustom += '</td>';
    /*trCustom += '<td class="text-center" style="width: 27%">';
    trCustom += '<div class="input-group">';
    trCustom += '	<input type="hidden" id="ljdwMark' + subTableLineNum + '" name="ljdwMark" />';
    trCustom += '	<input type="text" id="basefolderUnit' + subTableLineNum + '" onclick="openSelectZtree({\'id\':\'ljdwMark<?=(i+1)?>\',\'name\':\'basefolderUnit<?=(i+1)\',\'isMulti\':\'1\',\'type\':\'2\',\'asyn\':false,\'level\':\'2\',\'url\':\'/system/component/tree/deptTree\'})" unselectable="on" name="basefolderUnit" class="form-control">';
    trCustom += '    <span class="input-group-addon" style="cursor: pointer;"';
    trCustom += '         onclick="openSelectZtree({\'id\':\'ljdwMark<?=(i+1)?>\',\'name\':\'basefolderUnit<?=(i+1)\',\'isMulti\':\'1\',\'type\':\'2\',\'asyn\':false,\'level\':\'2\',\'url\':\'/system/component/tree/deptTree\'})">';
    trCustom += '    <span class="glyphicon glyphicon-user" title="选择部门"></span>';
    trCustom += '    </span>';
    trCustom += '</div>';
    trCustom += '</td>';*/
    trCustom += '<td class="text-center" style="width: 6%">';
    trCustom += '	<select CK_info="立卷单位"  placeholder="请输入立卷单位" CK_type="ck_required" class="form-control" id="ljdwMark' + subTableLineNum + '" name="ljdwMark">';
    trCustom += getSelect("dagl_ljdw", "1");
    trCustom += '</select>';
    trCustom += '</td>';
    trCustom += '</tr>';
    return trCustom;
}

function getSelect(mark, type, position) {
    var resId = $("#resId").val();
    var html = "";
    $.ajax({
        type: "get",
        url: "/system/config/dictionary/getListByMark",
        data: {
            "mark": mark,
            "type": type,
            "resId": $("#resId").val()
        },
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.flag == "1") {
                html += "<option data-mark='' value=''>--请选择--</option>";
                $.each(data.data, function (i, n) {
                    if (type == '0') {
                        html += "<option data-mark='" + n.mark + "' value='" + n.code + "'>" + n.name + "</option>";
                    } else if (type == '1') {
                        html += "<option value='" + n.code + "'>" + n.name + "</option>";
                    }
                });
            } else {
                if (data.msg) {
                    layer.msg("获取字典项失败！" + data.msg, {icon: 2});
                } else {
                    layer.msg("获取字典项异常！请刷新重试！", {icon: 2});
                }
            }
        },
        error: function () {
            layer.msg("加载字典项异常！请刷新重试！", {icon: 2});
        }
    });
    return html;
}


/**
 * 删除一行
 */
function deleteTabFn(ids) {
    if ($("#isRenew").val() == "0" || $("#id").val() != "") {
        var resId = $("#resId").val();
        var flag = false;//如果删除成功改为true
        // 删除操作
        $.ajax({
            url: '/dagl/daly/borrow/deleteDaglFile?ids=' + ids + "&resId=" + resId
            , type: "GET"
            , dataType: "json"
            , async: false
            , success: function (result) {
                if (result.flag === "1") {
                    flag = true;
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
    } else {
        flag = true;
        parent.TableInit.refTable('right_table');
        layer.msg("删除成功！", {icon: 1});
    }
    return flag;
}

/**
 * 提交表单
 * @returns
 */
function commitForm() {
    var flag = saveForm();
    if (flag && flag == "1") {
        layer.msg("保存成功！", {icon: 1});
        //刷新列表
        parent.TableInit.refTable('right_table');
    } else if (flag && flag == "2") {

    } else {
        layer.msg("保存失败！", {icon: 2});
    }
}

/**
 * 保存
 */
function saveForm() {
    //借阅内容说明不必填
    /* if ($("#isInnewOrReborrow").val() != "1") {
         fileListShowAndHidden();
     }
 */
    var res = "";
    var checkResult = aotoCheckForm.check($("#otherPeople").find("tr").find("input,select,textarea").not(':hidden'));
    if (checkResult) {
        var bootstrapValidator = $("#form").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {

            var data = getFormData();
            if (!$("#id").val()) {
                $("#subflag").val(Config.startflag);
            }
            $.ajax({
                type: "POST",
                url: "/dagl/daly/borrow/saveForm",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: false,
                success: function (json) {
                    if (json.flag == '1') {
                        res = json.flag;
                        $("#id").val(json.data.id);
                        $("#title").val(json.data.title);
                        $("#subflag").val(json.data.subflag);
                        iniTable(json);
                        /* if ($("#isInnewOrReborrow").val() != "1") {
                             fileListShowAndHidden();
                         }*/
                        if ($('#flowType input[name="flowType"]:checked ').val() == "1") {//本单位
                            $('#searchId').hide();
                        }
                    }
                },
                error: function () {
                }
            });

            //保存临时意见
            var tempIdea = $("#idea").val();
            saveIdeaTemp($("#workitemid").val(), tempIdea);
        } else {
            res = "2";
        }
    } else {
        res = "2";
    }
    return res;
}

function getFormData() {
    var daglBorrow = {
        resId: $('#resId').val(),
        id: $("#id").val(),
        borrowUnitId: $("#borrowUnitId").val(),
        borrowUnitName: $("#borrowUnitName").val(),
        borrowUserId: $("#borrowUserId").val(),
        borrowUserName: $("#borrowUserName").val(),
        borrowDeptId: $("#borrowDeptId").val(),
        borrowDeptName: $("#borrowDeptName").val(),
        usePurpose: $("#usePurpose").val(),
        phone: $("#phone").val(),
        title: $("#title").val(),
        approveUnitId: $("#approveUnitId").val(),
        approveUnitName: $("#approveUnitName").val(),
        approveUserId: $('#approveUserId').val(),
        approveUserName: $('#approveUserName').val(),
        flowType: $('#flowType input[name="flowType"]:checked ').val(),
        remark: $("#remark").val(),
        fileList: new Array()//字表数据
    };//需要提交到后台的数据

    //遍历页面中子表1信息 形成 subTableList
    //本单位 或者非 本单位不是续借
    var $subTableRows = $('#otherPeople').find("tr");//所有子表信息行
    $subTableRows.each(function (i, index) {
        var file = {
            id: $(index).find('[name="id"]').val(),
            recid: $(index).find('[name="recid"]').val(),
            categoryNo: $(index).find('[name="categoryNo"]').val(),
            categoryName: $(index).find('[name="categoryName"]').val(),
            archiveNo: $(index).find('[name="archiveNo"]').val(),
            mainTitle: $(index).find('[name="mainTitle"]').val(),
            basefolderUnit: $(index).find('[name="ljdwMark"]').find("option:selected").text(),
            ljdwMark: $(index).find('[name="ljdwMark"]').val(),
            tableName: $(index).find('[name="tableName"]').val(),
            orderNum: i
        };
        daglBorrow.fileList.push(file);
    });

    //以下输出对象deviceInfo 就是完整的请求报文
    return daglBorrow;
}

/**
 *  工作流回调该方法，用于改变业务数据状态
 * @param subflag 状态位
 * @returns
 */
function updateBusiData(subflag) {
    $.ajax({
        type: "POST",
        url: "/dagl/daly/borrow/updateFlag",
        data: {
            id: $("#id").val(),
            subflag: subflag
        },
        async: false,
        success: function (data) {
        },
        error: function () {
        }
    });
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

function flowTypeChange(type) {
    if ($("#opertation").val() != "VIEW") {
        if ($('#flowType input[name="flowType"]:checked ').val() == "1") {//本单位
            var fileTypeId = "734f44278c21455aba920ab4d3452a95";
            var workFlowId = "734f44278c21455aba920ab4d3452a95";
            $("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);
            $(".fileUnit").hide();
        }
        if ($('#flowType input[name="flowType"]:checked ').val() == "2") {//非本单位
            var fileTypeId = "c333a7a057d142308a42f38e8ef74099";
            var workFlowId = "c333a7a057d142308a42f38e8ef74099";
            $("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);
            $(".fileUnit").show();
        }
    } else {
        if ($('#flowType').html() == "1") {//本单位
            var fileTypeId = "734f44278c21455aba920ab4d3452a95";
            var workFlowId = "734f44278c21455aba920ab4d3452a95";
            /* $("#filetypeid").val(fileTypeId);
             $("#workflowid").val(workFlowId);*/
            $(".fileUnit").hide();
        }
        if ($('#flowType').html() == "2") {//非本单位
            var fileTypeId = "c333a7a057d142308a42f38e8ef74099";
            var workFlowId = "c333a7a057d142308a42f38e8ef74099";
            /*$("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);*/
            $(".fileUnit").show();
        }
    }
    /* if ($("#isInnewOrReborrow").val() != "1") {
         fileListShowAndHidden();
     }*/
}

/**
 * @Author 王富康
 * @Description //TODO 确认信息
 * @Date 2019/3/5 20:14
 **/
function verifyBorrowUser() {
    var flag= "0";
    var $subTableRows = $('#otherPeople').find("tr");//所有子表信息行
    $subTableRows.each(function (i, index) {
        if ($(index).find('[name="isBorrow"]').val() == "借出" || $(index).find('[name="isBorrow"]').val() == "部分借出") {
            // layer.msg("档案信息列表中存在“借出”或“部分借出”数据请重新选择！", {icon: 0});
            flag= "1";
            return false;
        }
        if(flag== "1"){
            return false;
        }
    });
    if(flag== "1"){
        layer.msg("档案信息列表中存在“借出”或“部分借出”数据请重新选择！", {icon: 0});
        flag= "0";
        return false;
    }
    var flag = saveForm();
    if (flag && flag == "1") {
        layer.msg("保存成功！", {icon: 1});
        //刷新列表
        parent.TableInit.refTable('right_table');
        layer.open({
            type: 2,
            content: "/modules/dagl/daly/borrow/borrowmanage/verifyBorrowUser.html?userId=" + $("#borrowUserId").val() + "&recId=" + $("#resId").val(),
            area: ['400px', '400px'],
            title: ['确认借阅人信息', 'font-size:16px;font-weight:bold;']
        })
    } else if (flag && flag == "2") {

    } else {
        layer.msg("保存失败！", {icon: 2});
    }

}

function selectReturnDate() {
    var data = getFormData();
    var categoryCode = data.fileList[0].categoryNo;
    layer.open({
        type: 2,
        content: "/modules/dagl/daly/borrow/borrowmanage/selectReturnDate.html?id=" + $("#id").val() + "&categoryCode=" + categoryCode + "&resId=" + $("#resId").val(),
        area: ['1000px', '550px'],
        title: ['设置应还日期', 'font-size:16px;font-weight:bold;']
    })
}

//关闭当前窗口
function closeIfram() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}


