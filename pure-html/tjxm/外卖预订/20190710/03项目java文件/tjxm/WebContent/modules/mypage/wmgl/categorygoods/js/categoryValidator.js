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
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
      //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
        	typeName: {
                message: '类型名称验证失败',
                validators: {
                    notEmpty: {
                        message: '类型名称不能为空'
                    },
                    stringLength: {
                        max: 6,
                        message: '类型不能超过6个字符'
                    }
                }
            },
            mark: {
            	message: '备注验证失败',
                validators: {
                	stringLength: {
                        max: 100,
                        message: '备注不能超过100个字符'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});

function uniqCheck(checkItem,msg){
    var json = {};
    var valid ;
    $.ajax({
        type:"GET",
        url:"/system/config/dictionary/check",
        async:false,
        data:{
            checkItem:checkItem,
            id:$("#id").val(),
            name:$("#name").val(),  //字典名
            type:$("#type").val(),  //字典类型
            pId:$("#pId").val(),    //父ID
            mark:$("#mark").val(),  //字典码值
            code:$("#code").val()   //字典值
        },
        dataType:"json",
        success:function(res){
            valid = res.valid;
        }
    });
    json.valid = valid;
    json.message = msg;
    return json;
}