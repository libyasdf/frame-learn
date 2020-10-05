// var fileTypeId = "16705d1f91c64af1b01834d341ccf439";
// var workFlowId = "16705d1f91c64af1b01834d341ccf439";

//权限申请流程版本1
// var fileTypeId = "371a7d631d29411faddab3b9e5977c85";
// var workFlowId = "371a7d631d29411faddab3b9e5977c85";
//权限申请流程版本2
// var workFlowId = "05d863c7d1074979a18940c30ebb2853";
//权限申请流程版本3
// var workFlowId = "dbd0d540dcde42b88ad87fa19dd5e0f8";
//权限申请流程版本4
var fileTypeId = "ce9c28665d1a4862806dcdee347d8a29";
var workFlowId = "ce9c28665d1a4862806dcdee347d8a29";


$(function () {

    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#filetypeid").val(fileTypeId);
    $("#workflowid").val(workFlowId);
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);

    //根据mark获取一个以code、name为键值对的map，用来根据code取对应的字典名称
    var map = Dictionary.getNameAndCode({ mark: "sb", type: "1" });
    var a = map[1];
    var b = map[2];

    if ($("#opertation").val() != "VIEW") {
        //初始化字典项
        Dictionary.init({ position: "area", mark: "mj", type: "1", name: "area", domType: "checkbox" });
        Dictionary.init({ position: "week", mark: "week", type: "1", name: "week", domType: "select" });
        Dictionary.init({ position: "sex", mark: "sex", type: "1", name: "sex", domType: "radio" });
        //初始化数据字典类型
        Dictionary.init({ position: "sheng", mark: "sf", type: "0", name: "sheng", domType: "select" });
    }

	/**
	 * 初始化页面，数据加载、渲染
	 */
    if ($("#id").val() != "") {

        //表单数据渲染
        var datas = { "id": $("#id").val(), "resId": $("#resId").val() };
        httpRequest("get", "/taskplan/edit", datas, function (data) {
            console.log('初始化页面', data);
            DisplayData.playData({ data: data });

            if ($("#opertation").val() == "VIEW") {
                //radio
                var val = "";
                if (data.data.sex == '1') { val = "男"; }
                if (data.data.sex == '0') { val = "女"; }
                $("#sex").text(val);

                //checkbox
                var areas = data.data.area.split(",");
                var ar = [];
                $.each(areas, function (i, n) {
                    if (n === "1") { ar.push("河北"); }
                    if (n === "2") { ar.push("北京"); }
                    if (n === "3") { ar.push("天津"); }
                })
                $("#area").text(ar.join(", "));

                //select
                var week = "";
                if (data.data.week == '1') { week = "星期一"; }
                if (data.data.week == '2') { week = "星期二"; }
                if (data.data.week == '3') { week = "星期三"; }
                if (data.data.week == '4') { week = "星期四"; }
                if (data.data.week == '5') { week = "星期五"; }
                if (data.data.week == '6') { week = "星期六"; }
                if (data.data.week == '7') { week = "星期日"; }
                $("#week").text(week);
            }
        });

        //流程相关（按钮、控件等）的渲染
        if ($("#workitemid").val() != "") {
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
                //流转中不可修给表单--开始
                //$('form').find('input,textarea,select').not('#idea').prop('readonly',true);
                //流转中不可修给表单--结束
                DisplayData.playWorkFlow("/flowService/getFlowData", datas, $("#opertation").val(), callback);
            } else {//oper='VIEW'
                var datas = {
                    type: "1",
                    oper: $("#opertation").val(),
                    workitemid: $("#workitemid").val()
                };
                DisplayData.playWorkFlow("/workflow/getYiBanData", datas, $("#opertation").val());
            }
        } else {//workitemid为空时，表示编辑草稿状态，加载启动节点按钮及启动节点提示信息
            getStartWf();
        }
    } else {//id为空时，表示新建草稿状态，加载启动节点按钮及启动节点提示信息
        getStartWf();
    }
    iniFileUpload();
})


/**
 * 数据字典二级联动
 * 根据省份select初始化城市框
 */
function initCity() {
    var shengMark = $("#sheng option:selected").attr("data-mark");
    Dictionary.init({ position: "city", mark: shengMark, type: "1", name: "city", domType: "select" });
}


/**
 * 提交表单
 * @returns
 */
function commitForm() {
    var flag = saveForm();
    if (flag && flag == "1") {
        layer.msg("保存成功！", { icon: 1 });
        //刷新列表
        parent.TableInit.refTable('right_table');
    } else {
        layer.msg("保存失败！", { icon: 2 });
    }
}

/**
 * 保存
 */
function saveForm() {
    var res = "";
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        if (!$("#id").val()) {
            $("#subflag").val(Config.startflag);
        }
        $.ajax({
            type: "POST",
            url: "/taskplan/saveForm",
            data: $("#form").serialize(),
            async: false,
            success: function (json) {
                if (json.flag == '1') {
                    res = json.flag;
                    $("#id").val(json.data.id);
                    $("#subflag").val(json.data.subflag);
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({ docId: json.data.id, fileListId: "files,files2" });
                }
            },
            error: function () {
            }
        });
        //保存临时意见
        var tempIdea = $("#idea").val();
        saveIdeaTemp($("#workitemid").val(), tempIdea);
    }
    console.log(res);
    return res;
}

/**
 * 提交流程
 */
function commitFlow() {
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
        if (IsNotionShow == "block") {
            if ("2" == document.getElementById("fillmode").value) {
                if (idea == "" || idea == null) {
                    layer.msg("请填写意见！", { icon: 0 })
                    return false;
                }
            }
        }
        submitToFlow(oper, this, $("#title").val(), idea, $("#id").val(), "attr", "attr1", $("#workitemid").val(), $("#workflowid").val(),"","",$("#filetypeid").val());
        /*submitToFlow({
            oper:oper,
            title:$("#title").val(),
            idea:idea,
            pkValue:$("#id").val(),
            workItemId:$("#workitemid").val(),
            workFlowId:$("#workflowid").val(),
            fileTypeId:$("#filetypeid").val()
        });*/
    } else {
        layer.msg("数据保存失败！", { icon: 2 });
    }
}

/**
 *  工作流回调该方法，用于改变业务数据状态
 * @param subflag 状态位
 * @returns
 */
function updateBusiData(subflag) {
    $.ajax({
        type: "POST",
        url: "/taskplan/updateFlag",
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
 * 初始化文件上传
 */
function iniFileUpload() {
    /*var affixMap = Dictionary.getNameAndCode({mark:"affixSize",type:"1",resId:$("#resId").val()});
    var affixSize =0;
    for(item in affixMap){
        affixSize = parseInt(item) * 1024 * 1024;
        break;
	}*/
    //初始化文件上传
    MyFileUpload.init({
        viewParams: { "tableId": $("#id").val(), "tableName": "task_plan" },
        editOrView: $("#opertation").val()
    });

    //初始化文件上传2
    MyFileUpload.init({
        viewParams: { "tableId": $("#id").val(), "tableName": "task_plan" },
        editOrView: $("#opertation").val(),
        domId: "fileupload2",
        fileListId: "files2",  //文件列表显示的div
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
