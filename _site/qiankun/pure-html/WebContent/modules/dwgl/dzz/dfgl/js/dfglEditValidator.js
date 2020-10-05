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
            partyOrganizationName: {
                message: '党组织验证失败！',
                validators: {
                    notEmpty: {
                        message: '党组织不能为空！'
                    }
                }
            },
            partyName: {
                validators: {
                    notEmpty: {
                        message: '党员姓名不能为空！'
                    }
                }
            },
            annual: {
                validators: {
                    notEmpty: {
                        message: '年份不能为空！'
                    }
                }
            },
            monthval: {
                validators: {
                    notEmpty: {
                        message: '月份不能为空！'
                    }
                }
            },
            approvedMonthPartyfee: {
                validators: {
                    numeric: {
                        message: '核定月交纳党费金额不能为非数字！'
                    },
                    stringLength: {
                        max: 10,
                        message: '核定月交纳党费金额长度不能超过10！'
                    },
                    callback: {
                        callback : function(value, validator, $field) {
                            var msgVal = "核定月";
                            return changeFin(msgVal,value);
                        }
                    }
                }
            },
            currentPaymentAmount: {
                validators: {
                   /* notEmpty: {
                        message: '当前交纳金额不能为空！'
                    },*/
                    numeric: {
                        message: '当前交纳金额不能为非数字！'
                    },
                    stringLength: {
                        max: 10,
                        message: '当前交纳金额长度不能超过10！'

                    },
                    callback: {
                        callback : function(value, validator, $field) {
                            var msgVal = "当前";
                          return changeFin(msgVal,value);
                        }
                    }
                  /*  regexp: {
                        regexp:/^\d+(\.\d{1,3})?$/,
                        message: '当前交纳金额小数位长度不能超过3！'
                    }*/
                }
            }

}

    });
});