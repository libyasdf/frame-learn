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
            tenureDate: {
                message: '任职日期验证失败！',
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: '任职日期不能为空！'
                    }
                }
            },
            leaderName: {
                trigger:'change',
                validators: {
                    callback: {
                        callback : function(value, validator, $field) {
                            if(value!=null&&value!=""){
                                return changeFin();
                            }else{
                                return changeFinFalse();
                            }

                        }
                    }
                }
            },
            tenureMode: {
                message: '任职方式验证失败！',
                validators: {
                    notEmpty: {
                        message: '任职方式不能为空！'
                    }
                }
            },
            duties: {
                message: '党内职务名称验证失败！',
                validators: {
                    notEmpty: {
                        message: '党内职务名称不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '党内职务名称长度不能超过50！'
                    }
                }
            },
            tenureFileCode: {
                message: '任职文件文号验证失败！',
                validators: {
                    stringLength: {
                        max: 50,
                        message: '任职文件号长度不能超过50！'
                    }
                }
            },
            remark: {
                message: '职务说明验证失败！',
                validators: {
                    stringLength: {
                        max: 500,
                        message: '职务说明长度不能超过500！'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});