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
            columnName: {
                message: '栏目名称验证失败！',
                validators: {
                    notEmpty: {
                        message: '栏目名称不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '栏目名称长度必须不能超过18位'
                    }
                }
            },
            columnCode:{
                message:'栏目编码验证失败',
                validators: {
                    notEmpty: {
                        message: '栏目编码不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '栏目编码长度必须不能超过18位'
                    }
                }
            },
            columnRemark:{
                message:'栏目说明验证失败',
                validators: {
                    stringLength: {
                        max: 200,
                        message: '栏目说明长度必须不能超过200字'
                    }
                }
            },
           /* columnGlUserNames: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '栏目管理人不能为空！'
                    }
                }
            },*/
            submitHandler: function (validator, form, submitButton) {
                alert("submit");
            }
        }
    });
});