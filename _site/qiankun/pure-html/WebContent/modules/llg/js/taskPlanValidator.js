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
            title: {
                message: '标题验证失败！',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '标题长度必须不能超过18位'
                    }
                }
            }/*,
            zhubPersonNm: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '主办人不能为空！'
                    }
                }
            },
            zhubDeptNm: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '主办部门不能为空！'
                    }
                }
            },
            xiebPerson: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '协办人不能为空！'
                    }
                }
            },
            xiebDept: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '协办部门不能为空！'
                    }
                }
            }*/
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});