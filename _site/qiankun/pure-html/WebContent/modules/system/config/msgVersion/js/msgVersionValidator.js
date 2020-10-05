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
        group:".form-group",
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            version: {
                message: '版本号验证失败',
                validators: {
                    notEmpty: {
                        message: '版本号不能为空'
                    },
                    stringLength: {
                        max: 10,
                        message: '版本号不能超过10个字符'
                    },
                    callback: {
                        callback : function(value, validator, $field) {
                            return uniqCheck(value);
                        }
                    }
                }
            },
            content: {
            	message: '更新内容验证失败',
                validators: {
                    notEmpty: {
                        message: '更新内容不能为空！'
                    },
                    stringLength: {
                        max: 2000,
                        message: '更新内容不能超过2000个字符'
                    }
                }
            },
            creTime: {
                message: '更新日期验证失败',
                validators: {
                    notEmpty: {
                        message: '更新日期不能为空！'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});

function uniqCheck(value){
    var json = {};
    var valid = true;
    var message = "";
    //校验规则是否是1.4.1
    if(value){
        valid = num(value);
        if(valid){
            $.ajax({
                type:"GET",
                url:"/system/config/msgVersion/getByVersion",
                async:false,
                data:{
                    version:value   //版本号
                },
                dataType:"json",
                success:function(res){
                    if(res.flag == "1" || res.flag == "2"){
                        if(res.data.id != $("#id").val()){
                            valid = false;
                            message = "该版本号已存在";
                        }
                    }
                }
            });
        }else{
            message = "版本号格式不正确（例如：1.4.1）";
        }
    }
    json.valid = valid;
    json.message = message;
    return json;
}


function num(value){
    var valid = true;
    if(value.indexOf(".") != -1){
        //如果包含小数点
        var num= new Array(); //定义一数组
        num = value.split(".");
        var j = 0;//用来记录数组中不为空的个数
        for (var i=0;i<num.length ;i++ ){
            var n = num[i].trim();
            if(n){
                j++;
                if(isNaN(n)){//如果不是数字
                    valid = false;
                    break;
                }
            }else{
                valid = false;
                break;
            }
        }
        //如果数组中有为空的值
        if(num.length != j || j != 3){
            valid = false;
        }
    }else{
        valid = false;
    }
    return valid;
}