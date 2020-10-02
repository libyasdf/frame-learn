/**
 * 先进行数据项的验证
 */
$(function () {
    $('#form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '信访人姓名验证失败',
                validators: {
                    notEmpty: {
                        message: '111信访人姓名不能为空'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '性别不能为空'
                    }
                }
            }
        },
        // form表单提交函数
        submitHandler: function (validator, form, submitButton) {
            // method="post" action="../../mock/save.json"
         alert(1)
            // alert("submit");
        }
    })
   //     .on('success.form.bv', function (e) {
//        layer.msg("提交成功")
//    });
});

/**
 * 全局变量，单例模式
 * @save 保存事件
 * @submit 提交流程事件
 * @flowEnd 办结流程
 * @flowChart 流程图
 * @flowRecord 流程记录
 * @flowIdea 流程意见
 */
var global = {
    save: function () {
       $("#form").submit();
    },
    submit: function () {
        MyLayer.layerOpenUrl({url: '../../modules/template/flowcourse.html', title: '流程'});
    },
    flowEnd: function () {

    },
    flowChart: function () {
        MyLayer.layerOpenUrl({url: '../../modules/template/flowstatus.html', title: '流程图'})
    },
    flowRecord: function () {
        MyLayer.layerOpenUrl({url: '../../modules/template/flowcourse.html', title: '流程记录'})
    },
    flowIdea: function () {
        MyLayer.layerOpenUrl({url: '../../modules/template/show_idea.html', title: '流程意见'})
    }
};

//初始化表格
//填充表格可以使用bootstrap-table或拼接字符串的方式，
// 这里使用bootstrap-table的方式作为参考
$("#otherPeople").bootstrapTable({
    striped: true,//隔行变色
    columns: [{
        width: "8%"
        , field: "id"
        , title: "编号"
    }, {
        width: "10%"
        , field: "name"
        , title: "姓名"
    }, {
        width: "20%"
        , field: "age"
        , title: "年龄"
    }, {
        width: "30%"
        , field: "address"
        , title: "地址"
    }, {
        title: "备注"
        , formatter: function addInput(value, row, index) {
            return "<input type='text' class='form-control' name='table.remark' placeholder='输入备注'>";
        }
    }]
});
$("#otherPeople").bootstrapTable("hideLoading");
//初始化表格end

//通过url传参时获取传参
var url = new Url();
$(document).ready(function (e) {
    var _parse = url.parse(window.location.href);
    var id = null;
    var viewParams = {};
    try {
        id = _parse.query.id;
        if (id) {
            $("#textId").val(id);//将ID回显
            viewParams = {docId: id}; //将业务ID添加到文件列表回显请求，注：docId是我自定义的，根据实际修改
            $.ajax({
                url: "/mock/formlist.json?"+new Date().getTime()//请求数据的url
                , data: {id: id}
                , dataType: "json"
                // , type: "POST" //请求方式，在有后台后建议开启
                , success: function (result) {
                    if (result.flag === "1") {
                        var result = result.data;
                        //数据回显，这里需要根据需要重写
                        for (variable in result) {
                            var ems = document.getElementsByName(variable);
                            var em;
                            var tagName;
                            if (ems.length > 0) {
                                em = ems[0];
                                tagName = em.tagName;
                            } else {
                                continue;
                            }

                            if (result[variable]) { //是否有该属性
                                var resVar = result[variable];
                                //input输入框
                                if (tagName === "INPUT") {
                                    if (em.type === "checkbox"){//checkbox 复选框
                                        $.each(ems, function (x, n) {
                                            $.each(resVar,function (j, jn) {
                                                if (jn === $(n).val()) {
                                                    $(n).attr("checked", "checked");
                                                }
                                            })
                                        })
                                    }else if(em.type === "text"){ //text文本输入框
                                        $(em).val(resVar);
                                    }else if (em.type === "radio"){ //单选框
                                        // $("[name='" + variable + "']").val(resVar);
                                        $.each(ems, function (x, n) {
                                            // resVar.each(function (j, jn) {
                                                if (resVar === $(n).val()) {
                                                    $(n).attr("checked", "checked");
                                                }
                                            // })
                                        })
                                    }
                                }else if (tagName === "SELECT"){
                                    $(em).val(resVar);
                                }else if (tagName === "TEXTAREA") {
                                    $(em).text(resVar);
                                }
                            }
                        }
                        //填充表格可以使用bootstrap-table或拼接字符串的方式，
                        // 这里使用bootstrap-table的方式作为参考
                        $("#otherPeople").bootstrapTable("load", result.table);
                        //数据回显 end
                    } else {
                        if ($.trim(result.msg) && result.msg !== "") {
                            layer.alert(result.msg, {icon: 5})
                        } else {
                            layer.alert("加载数据错误，请刷新重试！", {icon: 5})
                        }
                    }
                }
            });
        }
    } catch (err) {
    }
    //初始化文件上传，文件上传inputid和进度条的id、class如有修改，需要指定参数来初始化
    MyFileUpload.init({viewParams: viewParams});
});
