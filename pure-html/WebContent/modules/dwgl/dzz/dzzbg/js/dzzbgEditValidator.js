/**
 * notEmpty：非空验证
 * stringLength：字符串长度验证
 * regexp：正则表达式验证；
 * emailAddress：邮箱地址验证（都不用我们去写邮箱的正则了~~）
 * between：验证输入值必须在某一个范围值以内，比如大于10小于100；
 * creditCard：身份证验证；
 * date：日期验证；
 * ip：IP地址验证；
 * numeric：数值验证；
 * phone：电话号码验证；
 * uri：url验证；
 * 
 * 注：对于类似选择人员部门的控件进行校验，需要添加trigger:"change"监听事件，同时，在选择回填的时候需手动触发onchange事件；
 */
$(function () {
    $('#form').bootstrapValidator({
        message: 'This value is not valid',
        group:".rowGroup",
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
      //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
            code: {
                message: '变更后组织编码验证失败！',
                validators: {
                    notEmpty: {
                        message: '变更后组织编码不能为空！'
                    },
                    numeric: {
                        message: '更后组织编码不能为非数字！'
                    },
                    stringLength: {
                        min: 3,
                        max: 3,
                        message: '填写后3位变更后组织编码！'
                    },
                    callback: {
                        callback : function() {
                            return uniqCheck("党组织编码不能重复！");
                        }
                    }
                }
            },
            changeDate: {
                message: '变更日期验证失败！',
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: '变更日期不能为空！'
                    }
                }
            },
            reason: {
                message: '变更原因验证失败！',
                validators: {
                    stringLength: {
                        max: 500,
                        message: '变更原因长度不能超过500！'
                    },
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});

function uniqCheck(msg){
    var json = {};
    var valid = true;
    if($("#code").val().length == 3){
        $.ajax({
            type: "GET",
            url: "/djgl/ddjs/dzz/dzzgl/check",
            async: false,
            data: {
                orgId: $("#targetOrgId").val() + $("#code").val()
            },
            dataType: "json",
            success: function (res) {
                valid = res.valid;
            }
        });
    }
    json.valid = valid;
    json.message = msg;
    return json;
}