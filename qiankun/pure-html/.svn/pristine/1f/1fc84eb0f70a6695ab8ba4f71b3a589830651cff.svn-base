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
    orderNum: '',
    securityClass:'',
    pageNum:''

}];
var theRequest = GetRequest();
$(function () {
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);

    /**
     * 初始化页面，数据加载、渲染
     */
    if ($("#id").val() != "") {

        //表单数据渲染
        var datas = {"id": $("#id").val(), "resId": $("#resId").val()};
        httpRequest("get", "/dagl/daly/borrow/edit", datas, function (data) {
            DisplayData.playData({data: data});
            iniTable(data);
            flowTypeChange();
        });
        if ($("#opertation").val() == "VIEW") {
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
    if (theRequest.type == "search") {
        borrow();
        $('#searchId').hide();
    }
});

function iniTable(data) {
    if (data && data != undefined) {
        if (!$.isEmptyObject(data.data.fileList)) {
            fileList = data.data.fileList;
        }
    }
    var html = $('[name=main]').html();
    nodetpl.render(html, {data: fileList}, function (html) {
        $('#otherPeople').find('tbody').empty().append(html);
        var categoryName = Dictionary.getNameAndCode({mark: "leibiehao", type: "1"});
        var ljdwMap = Dictionary.getNameAndCode({mark: "dagl_ljdw", type: "1"});
        var securityClass = Dictionary.getNameAndCode({mark: "04", type: "1"});
        for (var i = 0; i < fileList.length; i++) {
            if ($("#opertation").val() != "VIEW") {
                //门类名称
                Dictionary.init({
                    position: "categoryName" + (i + 1),
                    mark: "leibiehao",
                    type: "1",
                    name: "categoryName",
                    domType: "select"
                });
                for (var j in categoryName) {
                    if(fileList[i].categoryName == categoryName[j]){
                        $("#categoryName" + (i + 1)).val(j);
                    }
                }
                //立卷单位
                Dictionary.init({
                    position: "ljdwMark" + (i + 1),
                    mark: "dagl_ljdw",
                    type: "1",
                    name: "ljdwMark",
                    domType: "select"
                });
                $("#ljdwMark" + (i + 1)).val(fileList[i].ljdwMark);
                //密级
                Dictionary.init({
                    position: "securityClass" + (i + 1),
                    mark: "04",
                    type: "1",
                    name: "securityClass",
                    domType: "select"
                });
                for (var j in securityClass) {
                    if(fileList[i].securityClass == securityClass[j]){
                        $("#securityClass" + (i + 1)).val(j);
                    }
                }
            } else {
                //门类名称
                $("#categoryName" + (i + 1)).html(categoryName[$("#categoryName" + (i + 1)).text()]);
                //立卷单位
                $("#ljdwMark" + (i + 1)).html(ljdwMap[$("#ljdwMark" + (i + 1)).text()]);
                //密级
                $("#securityClass" + (i + 1)).html(securityClass[$("#securityClass" + (i + 1)).text()]);

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
    trCustom += '<td class="text-center" name="numName" style="width: 5%"></td>';
    trCustom += '<td class="text-center" style="width: 8%"><input type="hidden" id="isBorrow' + subTableLineNum + '" name="isBorrow" value="未入库"><span >未入库</span></td>';
    trCustom += '<td class="text-center" style="width: 12%">';
    trCustom += '<input type="hidden" id="id' + subTableLineNum + '" name="id" />';
    trCustom += '<input type="hidden" id="recid' + subTableLineNum + '" name="recid" />';
    trCustom += '<input type="hidden" id="tableName' + subTableLineNum + '" name="tableName" />';
    trCustom += '<input type="hidden" id="categoryNo' + subTableLineNum + '" name="categoryNo"/>';
    trCustom += '	<select CK_info="门类名称"  placeholder="请选择门类名称" CK_type="ck_required" class="form-control" id="categoryName' + subTableLineNum + '" name="categoryName">';
    trCustom += getSelect("leibiehao", "1");
    trCustom += '</select>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 18%">';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="100" CK_info="档号"  placeholder="请输入档号" class="form-control" id="archiveNo' + subTableLineNum + '" name="archiveNo" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 24%">';
    trCustom += '	<input type="text" CK_type="ck_required,ck_max" ck_max="200"  CK_info="题名"  placeholder="请输入题名" class="form-control" id="mainTitle' + subTableLineNum + '" name="mainTitle" />';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 12%">';
    trCustom += '	<select CK_info="立卷单位"  placeholder="请选择立卷单位" class="form-control" id="ljdwMark' + subTableLineNum + '" name="ljdwMark">';
    trCustom += getSelect("dagl_ljdw", "1");
    trCustom += '</select>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 10%">';
    trCustom += '	<select CK_info="密级"  placeholder="请输入密级" class="form-control" id="securityClass' + subTableLineNum + '" name="securityClass">';
    trCustom += getSelect("04", "1");
    trCustom += '</select>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 8%">';
    trCustom += '	<input type="text"  CK_info="页数"  placeholder="请输入页数" class="form-control" id="pageNum' + subTableLineNum + '" name="pageNum" />';
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
        if(theRequest.flag=="1"){
            //传统借阅的修改页面中，保存成功，关闭页面。
            closeIfram();
            parent.layer.msg("保存成功！", {icon: 1,time:2000});
        }else{
            layer.msg("保存成功！", {icon: 1,time:2000});
        }

        //刷新列表
        parent.TableInit.refTable('right_table');
    } else if (flag && flag == "2") {

    } else {
        layer.msg("保存失败！", {icon: 2});
    }
    //layer.close(index);
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
                        iniTable(json);
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
        var categoryName = $(index).find('[name="categoryName"]').find("option:selected").text();
        var basefolderUnit = $(index).find('[name="ljdwMark"]').find("option:selected").text();
        var securityClass = $(index).find('[name="securityClass"]').find("option:selected").text();
        var file = {
            id: $(index).find('[name="id"]').val(),
            recid: $(index).find('[name="recid"]').val(),
            categoryNo: $(index).find('[name="categoryNo"]').val(),
            categoryName: "--请选择--"==categoryName?"":categoryName,
            archiveNo: $(index).find('[name="archiveNo"]').val(),
            mainTitle: $(index).find('[name="mainTitle"]').val(),
            basefolderUnit: "--请选择--"==basefolderUnit?"":basefolderUnit,
            ljdwMark: $(index).find('[name="ljdwMark"]').val(),
            tableName: $(index).find('[name="tableName"]').val(),
            securityClass: "--请选择--"==securityClass?"":securityClass,
            pageNum: $(index).find('[name="pageNum"]').val(),
            orderNum: i
        };
        daglBorrow.fileList.push(file);
    });

    //以下输出对象deviceInfo 就是完整的请求报文
    return daglBorrow;
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
           // $('#approveUnitName').val("").change();
           // $('#approveUnitId').val("").change();
          //  $(".fileUnit").hide();
        }
        if ($('#flowType input[name="flowType"]:checked ').val() == "2") {//非本单位
            var fileTypeId = "c333a7a057d142308a42f38e8ef74099";
            var workFlowId = "c333a7a057d142308a42f38e8ef74099";
            $("#filetypeid").val(fileTypeId);
            $("#workflowid").val(workFlowId);
         //   $(".fileUnit").show();
        }
    } else {
        if ($('#flowType').html() == "1") {//本单位
            var fileTypeId = "734f44278c21455aba920ab4d3452a95";
            var workFlowId = "734f44278c21455aba920ab4d3452a95";
          //  $(".fileUnit").hide();
        }
        if ($('#flowType').html() == "2") {//非本单位
            var fileTypeId = "c333a7a057d142308a42f38e8ef74099";
            var workFlowId = "c333a7a057d142308a42f38e8ef74099";
           // $(".fileUnit").show();
        }
    }
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
        flag = "0";
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
