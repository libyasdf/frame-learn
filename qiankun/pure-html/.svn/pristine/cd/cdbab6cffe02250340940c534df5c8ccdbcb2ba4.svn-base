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
 */
$(function () {
    $('form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                trigger:'change',//这个是绑定在调用change事件后重新验证，
                                // 使用jquery的val赋值时需要调用一下change事件，
                                // 如$('#aaa').val('xx').change();
                                // （可应用于部门人员树回显）
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '用户名长度必须在6到18位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    }
                }
            },
            email: {
                trigger:'change',
                validators: {
                    notEmpty: {
                        
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址格式有误'
                    },
                    different: {
                        field: 'password',
                        message: '用户名和密码不能相同。'
                    },
                    choice: {
                        min: 2,
                        max: 4,
                        message: '请选择2-4项'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: '确认密码与密码不相同'
                    },
                    greaterThan: {
                        value: 18,
                        message: '必须大于18'
                    },
                    lessThan: {
                        value: 100,
                        message: '必须小于100'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});