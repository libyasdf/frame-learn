fileList = [/*{
    id: '',
    categoryName: '',
    archiveNo: '',
    mainTitle: '',
    ljdwMark: '',
    basefolderUnit: '',
    recid: '',
    categoryNo: '',
    tableName:''
}*/];
$(function () {

    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    $("#type").val(theRequest.type);
    //根据mark获取一个以code、name为键值对的map，用来根据code取对应的字典名称
    var usePurposeMap = "";
    if ($("#opertation").val() != "VIEW") {
        if (theRequest.type == "borrow") {
            getBorrowData();
        }
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
            console.log('初始化页面', data);
            DisplayData.playData({data: data});
            iniTable(data);
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
        }
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
            "deleteFn": ''
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
            $('#approveUnitName').val("").change();
            $('#approveUnitId').val("").change();
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
}

