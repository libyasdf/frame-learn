var selectData = [];
$(function () {
    var cat = sessionStorage.getItem('selectData');
    if (cat.length > 0) {
        var arr = cat.split('$$$');
        for (var a = 0; a < arr.length; a++) {
            selectData.push(JSON.parse(arr[a]))
        }
    }
    sessionStorage.removeItem('selectData');
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    $("#flag").val(theRequest.flag);

    //根据mark获取一个以code、name为键值对的map，用来根据code取对应的字典名称
    /*var usePurposeMap = "";
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
    }*/

    /**
     * 初始化页面，数据加载、渲染
     */
    if ($("#id").val() != "") {

        //表单数据渲染
        var datas = {"id": $("#id").val(), "resId": $("#resId").val(), "flag": $("#flag").val()};
        httpRequest("get", "/dagl/daly/borrow/innewAndReborrowEdit", datas, function (data) {
            DisplayData.playData({data: data});
            $("#id").val(data.id);
            iniTable(data);
            flowTypeChange();
            $('#flowType1').attr("disabled", true);
            $('#flowType2').attr("disabled", true);
        });
        if ($("#opertation").val() == "VIEW") {
           /* $("#usePurpose").html(usePurposeMap[$("#usePurpose").text()]);*/
            if ($("#flowType").text() == '1') {
                $("#flowType").html("借阅本单位");
            } else {
                $("#flowType").html("借阅非本单位");
            }
        }
        //流程相关（按钮、控件等）的渲染
        /*if ($("#workitemid").val() != "") {
            if ($("#opertation").val() == "EDIT") {
                var datas = {
                    workflowid: $("#workflowid").val(),
                    filetypeid: $("#filetypeid").val(),
                    workitemid: $("#workitemid").val(),
                    pkValue: $("#id").val(),
                    userid: getcookie("userid"),
                    deptid: getcookie("deptid"),
                    oper: $("#opertation").val(),
                    ideaArea: $("ideaArea").val()
                };
                DisplayData.playWorkFlow("/flowService/getFlowData", datas, $("#opertation").val(), callback, function returnData(data) {
                    var wfleveid = data.flowData.wfleveid;//节点id
                    if ($("#workflowid").val() == "c333a7a057d142308a42f38e8ef74099") {//非本单位

                        if (wfleveid != "1542168786645") {
                            $('#usePurpose').attr("disabled", true);
                            $('#phone').attr("disabled", true);
                            $('#borrowUserName').attr("disabled", true);
                            $('#borrowUnitName').attr("disabled", true);
                            $('#approveUnitName').attr("disabled", true);
                            $('#remark').attr("disabled", true);
                            $(".input-group-addon").attr("onclick", "null");
                            $('#deleteId').hide();
                            $('.deleteCheckBox').hide();
                        }
                        //保存审批人、审批单位
                        if(wfleveid == "1542128965225"){
                            $('#approveUserId').val(getcookie("userid"));
                            $('#approveUserName').val(getcookie("usernm"));
                        }
                    } else {//本单位
                        if (wfleveid != "1542711815643") {
                            $('#usePurpose').attr("disabled", true);
                            $('#phone').attr("disabled", true);
                            $('#borrowUserName').attr("disabled", true);
                            $('#borrowUnitName').attr("disabled", true);
                            $(".input-group-addon").attr("onclick", "null");
                            $('#remark').attr("disabled", true);
                            $('#deleteId').hide();
                            $('.deleteCheckBox').hide();

                        }
                        //保存审批人、审批单位
                        if(wfleveid == "1542703756993"){
                            $('#approveUserId').val(getcookie("userid"));
                            $('#approveUserName').val(getcookie("usernm"));
                        }
                    }
                });
            } else {//oper='VIEW'

                var datas = {
                    type: "1",
                    oper: $("#opertation").val(),
                    workitemid: $("#workitemid").val()
                };
                DisplayData.playWorkFlow("/workflow/getYiBanData", datas, $("#opertation").val());
            }
        } else if($("#opertation").val() == "EDIT") {//workitemid为空时，表示编辑草稿状态，加载启动节点按钮及启动节点提示信息*/
        getStartWf();
        //  }
    } else {//id为空时，表示新建草稿状态，加载启动节点按钮及启动节点提示信息
        iniTable();
        flowTypeChange();
        getStartWf();
    }
    $('#flowType1').attr("disabled", true);
    $('#flowType2').attr("disabled", true);
    if ($("#subflag").val() == '0') {
        $('#usePurpose').attr("disabled", true);
        $('#borrowUserName').attr("disabled", true);
        $('#borrowUnitName').attr("disabled", true);
        $('#approveUnitName').attr("disabled", true);
        $(".input-group-addon").attr("onclick", "null");
    }
});

function fileListDisabled() {
    for (var i = 0; i < $("#fileNum").val(); i++) {
        $('#fileCategory' + (i + 1)).attr("disabled", true);
        $('#archiveNo' + (i + 1)).attr("disabled", true);
        $('#fileName' + (i + 1)).attr("disabled", true);
        $('#classification' + (i + 1)).attr("disabled", true);
        $('#useWay' + (i + 1)).attr("disabled", true);
    }
}

function isBorrowDisabled() {
    for (var i = 0; i < $("#fileNum").val(); i++) {
        $('#isBorrow' + (i + 1)).attr("disabled", true);
    }
}

function isBorrowNotDisabled() {
    for (var i = 0; i < $("#fileNum").val(); i++) {
        $('#isBorrow' + (i + 1)).attr("disabled", false);
    }
}

function finishButtonVisible() {
    // if ($("#approveUserId").val() == "") {
    $("#noApproval").hide();
    $("#approval").hide();
    // }
}


function iniTable(data) {
    fileList = [/*{
        id: '',
        categoryName: 'XXXXX',
        archiveNo: '12222',
        mainTitle: 'XXXXX',
        ljdwMark: 'yichu',
        basefolderUnit: '一处'
    }*/];
    if (data != undefined) {
        if (!$.isEmptyObject(data.data.fileList)) {
            fileList = data.data.fileList;
            // $("#fileNum").val(data.data.fileList.length);
            /*if (data.data.fileList.length > 0) {
                $("#approveUserId").val(data.data.fileList[0].approveUserId);
            }*/
        }
    }
    if (selectData.length > 0 && $("#flag").val() == "borrowRecordList") {
        for (var i = 0; i < selectData.length; i++) {
            selectData[i].oldId = selectData[i].id;
            selectData[i].id = "";
            selectData[i].recid = selectData[i].recid ? selectData[i].recid : '';
            selectData[i].tableName = selectData[i].tableName ? selectData[i].tableName : '';
            selectData[i].categoryNo = selectData[i].categoryNo ? selectData[i].categoryNo : '';
        }
        fileList = selectData
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
            'addTrFn': '',
            "deleteFn": deleteTabFn
        })
    });
}

/**
 * 添加一行
 */
/*function addTabFn(obj) {
    var subTableLineNum = $("#otherPeople").find("tr").length;
    subTableLineNum++;
    var trCustom = "";
    trCustom += '<tr>';
    trCustom += '<td class="text-center" style="width: 5%"><input type="checkbox" name="checkboxName" value="option1"></td>';
    trCustom += '<td class="text-center" name="numName" style="width: 5%"></td>';
    trCustom += '<td class="text-center" style="width: 8%">';
    trCustom += '	<input type="text" CK_info="门类名称"  placeholder="请输入门类名称" class="form-control" id="categoryName' + subTableLineNum + '" name="categoryName" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 8%">';
    trCustom += '	<input type="text" CK_info="档号"  placeholder="请输入档号" class="form-control" id="archiveNo' + subTableLineNum + '" name="archiveNo" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 10%">';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="200"  CK_info="题名"  placeholder="请输入题名" class="form-control" id="mainTitle' + subTableLineNum + '" name="mainTitle" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 27%">';
    trCustom += '<div class="input-group">';
    trCustom += '	<input type="hidden" id="ljdwMark' + subTableLineNum + '" name="ljdwMark" />';
    trCustom += '	<input type="text" id="basefolderUnit' + subTableLineNum + '" onclick="openSelectZtree({\'id\':\'ljdwMark<?=(i+1)?>\',\'name\':\'basefolderUnit<?=(i+1)\',\'isMulti\':\'1\',\'type\':\'2\',\'asyn\':false,\'level\':\'2\',\'url\':\'/system/component/tree/deptTree\'})" unselectable="on" name="basefolderUnit" class="form-control">';
    trCustom += '    <span class="input-group-addon" style="cursor: pointer;"';
    trCustom += '         onclick="openSelectZtree({\'id\':\'ljdwMark<?=(i+1)?>\',\'name\':\'basefolderUnit<?=(i+1)\',\'isMulti\':\'1\',\'type\':\'2\',\'asyn\':false,\'level\':\'2\',\'url\':\'/system/component/tree/deptTree\'})">';
    trCustom += '    <span class="glyphicon glyphicon-user" title="选择部门"></span>';
    trCustom += '    </span>';
    trCustom += '</div>';
    trCustom += '</td>';
    trCustom += '</td>';
    trCustom += '</tr>';
    return trCustom;
}*/

/*
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
    })
    return html;
}
*/


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
 * 数据字典二级联动
 * 根据省份select初始化城市框
 */

/*function initCity() {
    var shengMark = $("#sheng option:selected").attr("data-mark");
    Dictionary.init({position: "city", mark: shengMark, type: "1", name: "city", domType: "select"});
}*/


/**
 * 提交表单
 * @returns
 */
function commitForm() {
    var flag = saveForm();
    if (flag && flag == "1") {
        /*if ($("#isRenew").val() == "1"){
            layer.msg("保存成功，如果直接关闭页面，数据会在草稿列表显示！", {icon: 1});
        }else{*/
        layer.msg("保存成功！", {icon: 1});
        // }
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
    // fileListShowAndHidden();
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
                        $("#flag").val("");//保存过之后删掉，只是从续借页面进来，没有保存过时有值
                        $("#id").val(json.data.id);
                        $("#title").val(json.data.title);
                        $("#subflag").val(json.data.subflag);
                        iniTable(json);
                        // fileListShowAndHidden();
                        $('#flowType1').attr("disabled", true);
                        $('#flowType2').attr("disabled", true);
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
        subflag:$("#subflag").val(),
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
        isInnewOrReborrow: $("#isInnewOrReborrow").val(),
        fileList: new Array()//字表数据
    };//需要提交到后台的数据

    //遍历页面中子表1信息 形成 subTableList
    var $subTableRows = $('#otherPeople').find("tr");//所有子表信息行
    $subTableRows.each(function (i, index) {
        var file = {
            id: $(index).find('[name="id"]').val(),
            oldId: $(index).find('[name="oldId"]').val(),
            categoryName: $(index).find('[name="categoryName"]').val(),
            archiveNo: $(index).find('[name="archiveNo"]').val(),
            mainTitle: $(index).find('[name="mainTitle"]').val(),
            basefolderUnit: $(index).find('[name="ljdwMark"]').find("option:selected").text(),
            ljdwMark: $(index).find('[name="ljdwMark"]').val(),
            recid: $(index).find('[name="recid"]').val(),
            categoryNo: $(index).find('[name="categoryNo"]').val(),
            tableName: $(index).find('[name="tableName"]').val(),
            orderNum: i
        };
        daglBorrow.fileList.push(file);
    });

    //以下输出对象deviceInfo 就是完整的请求报文
    return daglBorrow;
}


/**
 * 提交流程
 */
function commitFlow() {
    /*if ($("#subflag").val() == 0) {
        layer.confirm("确定发送吗？发送后借阅类型不能修改！", {
            btn: ['确定', '取消']
        }, function () {
            commitFlow1();
        }, function () {
        })
    } else {*/
    commitFlow1();
    // }
}

function commitFlow1() {
    var flag = saveForm();
    if (flag != "" && flag == "1") {
        var oper = "";
        if ($("#workitemid").val() == "") {
            oper = "NEW";
        } else {
            oper = "EDIT";
        }
        //获取意见
        var idea = $("#idea").val();
        var IsNotionShow = document.getElementById("ideaArea").style.display;
        /*if (IsNotionShow == "block") {
            if ("2" == document.getElementById("fillmode").value) {
                if (idea == "" || idea == null) {
                    layer.msg("请填写意见！", {icon: 0})
                    return false;
                }
            }
        }*/
        submitToFlow(oper, this, $("#title").val(), idea, $("#id").val(), "fileUnitId", "attr1", $("#workitemid").val(), $("#workflowid").val());
    } else if (flag && flag == "2") {

    } else {
        layer.msg("数据保存失败！", {icon: 2});
    }
}

function finishFlow(finishFlag) {
    var flag = saveForm();
    completeFlow('', finishFlag);
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
            /*if(type=="1"){
                $("#fileUnitName").val("").change();
                $("#fileUnitId").val("");
                $('#borrowUserId').val("");
                $('#borrowUserName').val("").change();
                $('#borrowDeptId').val("");
                $('#borrowDeptName').val("");
            }*/
        }
        if ($('#flowType input[name="flowType"]:checked ').val() == "2") {//非本单位
            var fileTypeId = "c333a7a057d142308a42f38e8ef74099";
            var workFlowId = "c333a7a057d142308a42f38e8ef74099";
            $("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);
            $(".fileUnit").show();
            /* if(type=="1") {
                 $("#fileUnitName").val("").change();
                 $("#fileUnitId").val("");
                 $('#borrowUserId').val(getcookie("userid"));
                 $('#borrowUserName').val(getcookie("usernm")).change();
                 $('#borrowDeptId').val(getcookie("deptid"));
                 $('#borrowDeptName').val(getcookie("deptname"));
             }*/
        }
    } else {
        if ($('#flowType').html() == "1") {//本单位
            var fileTypeId = "734f44278c21455aba920ab4d3452a95";
            var workFlowId = "734f44278c21455aba920ab4d3452a95";
            $("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);
            $(".fileUnit").hide();
        }
        if ($('#flowType').html() == "2") {//非本单位
            var fileTypeId = "c333a7a057d142308a42f38e8ef74099";
            var workFlowId = "c333a7a057d142308a42f38e8ef74099";
            $("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);
            $(".fileUnit").show();
        }
    }
    // fileListShowAndHidden();
}

/*function fileListShowAndHidden() {
    if ($("#opertation").val() != "VIEW") {
        $('#form').bootstrapValidator('addField', 'borrowUserName', {
            trigger:"change",
            message: '借阅人验证失败！',
            group:".rowGroup",
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            validators: {
                notEmpty: {
                    message: '借阅人不能为空！'
                },
                stringLength: {
                    max: 50,
                    message: '借阅人长度不能超过50！'
                }
            }
        });
        $('#form').bootstrapValidator('addField', 'borrowUnitName', {
            group:".rowGroup",
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            trigger:"change", //监听onchange事件
            message: '借阅单位验证失败！',
            validators: {
                notEmpty: {
                    message: '借阅单位不能为空！'
                },
                stringLength: {
                    max: 50,
                    message: '借阅单位长度不能超过50！'
                }
            }
        });
        $('#form').bootstrapValidator('addField', 'usePurpose', {
            group:".rowGroup",
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            message: '利用目的验证失败！',
            validators: {
                notEmpty: {
                    message: '利用目的不能为空！'
                },
                stringLength: {
                    max: 50,
                    message: '利用目的长度不能超过50！'
                }
            }
        });
        $('#form').bootstrapValidator('addField', 'phone', {
            group:".rowGroup",
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            message: '电话验证失败！',
            validators: {
                notEmpty: {
                    message: '电话不能为空！'
                },
                regexp: {
                    regexp: /^[0-9]+$/,
                    message: '电话号码只能是数字'
                },
                stringLength: {
                    max: 50,
                    message: '电话长度不能超过50！'
                }
            }
        });
        $('#form').bootstrapValidator('addField', 'approveUnitName', {
            group:".rowGroup",
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            trigger:"change",
            message: '审批单位验证失败！',
            validators: {
                notEmpty: {
                    message: '审批单位不能为空！'
                },
                stringLength: {
                    max: 50,
                    message: '审批单位长度不能超过50！'
                }
            }
        });
        if ($('#flowType input[name="flowType"]:checked ').val() == "1") {//本单位
            $(".fileList").show();
            //借阅内容说明不必填
            var rowNum = $("#otherPeople>tbody>tr").length;
            if (rowNum > 0) {
                /!*var classVal  = document.getElementById("remark").getAttribute("class").replace("form-control", "form-control isNotValidate");
                document.getElementById("remark").setAttribute("class",classVal );*!/
                $("#isRed").html("");
                $("#form").bootstrapValidator('removeField','remark');
                $('#form').bootstrapValidator('addField', 'remark', {
                    validators: {
                        stringLength: {
                            max: 1000,
                            message: '借阅内容说明长度不能超过50！'
                        }
                    }
                });
            } else {
               /!* var classVal  = document.getElementById("remark").getAttribute("class").replace("form-control isNotValidate", "form-control");
                document.getElementById("remark").setAttribute("class",classVal );*!/
                $("#isRed").html("");
                $("#isRed").html("<b style=\"color: red;\">*</b> ");
                $("#form").bootstrapValidator('removeField','remark');
                $('#form').bootstrapValidator('addField', 'remark', {
                    validators: {
                        notEmpty: {
                            message: '借阅内容说明不能为空！'
                        },
                        stringLength: {
                            max: 1000,
                            message: '借阅内容说明长度不能超过50！'
                        }
                    }
                });
            }

        }
        if ($('#flowType input[name="flowType"]:checked ').val() == "2") {//非本单位
            $(".fileList").hide();
            //借阅内容说明必填，所有一处isNotValidate的class属性
           /!* var classVal  = document.getElementById("remark").getAttribute("class").replace("form-control isNotValidate", "form-control");
            document.getElementById("remark").setAttribute("class",classVal );*!/
            $("#isRed").html("");
            $("#isRed").html("<b style=\"color: red;\">*</b> ");
            $("#form").bootstrapValidator('removeField','remark');
            $('#form').bootstrapValidator('addField', 'remark', {
                validators: {
                    notEmpty: {
                        message: '借阅内容说明不能为空！'
                    },
                    stringLength: {
                        max: 1000,
                        message: '借阅内容说明长度不能超过1000！'
                    }
                }
            });
        }
    } else {
        if ($('#flowType').html() == "1") {//本单位
            $(".fileList").show();
        }
        if ($('#flowType').html() == "2") {//非本单位
            $(".fileList").hide();
        }
    }
}*/

