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
        	meetingRoomNo: {
                message: '会议室编号验证失败！',
                validators: {
                    notEmpty: {
                        message: '会议室编号不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '会议室编号长度不能超过18'
                    },
                    callback: {
                        callback : function(value, validator, $field) {
                            return uniqCheck($field.attr("meetingRoomNo"),"会议室编号不能重复");
                        }
                    }
                }
            },
        	meetingRoomName: {
                message: '会议室名称验证失败！',
                validators: {
                    notEmpty: {
                        message: '会议室名称不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '会议室名称长度不能超过18'
                    }
                }
            },
            location: {
            	message: '会议室地点验证失败！',
                validators: {
                    notEmpty: {
                        message: '会议室地点不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '会议室地点长度不能超过18'
                    }
                }
            },
            content: {
            	message: '可容纳人数验证失败！',
                validators: {
                    notEmpty: {
                        message: '可容纳人数不能为空'
                    },
                    regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                        regexp: /^[1-9]{1}\d*$/,
                        message: '只能是大于0的数字'
                    },
                    stringLength: {
                        max: 4,
                        message: '长度不能超过4'
                    }
                }
            },
            doorNumber: {
            	message: '可容纳人数验证失败！',
                validators: {
                    notEmpty: {
                        message: '门牌号不能为空'
                    }
                }
            },
            layout: {
                message: '布局验证失败！',
                validators: {
                    notEmpty: {
                        message: '布局不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '布局长度不能超过18'
                    }
                }
            },
            equipment: {
                message: '设备验证失败！',
                validators: {
                    notEmpty: {
                        message: '设备情况不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '设备情况长度不能超过18'
                    }
                }
            },
            manageDeptName: {
            	trigger:"change",
                message: '管理部门验证失败！',
                validators: {
                    notEmpty: {
                        message: '管理部门不能为空'
                    }
                }
            },
            network: {
                message: '网络验证失败！',
                validators: {
                    notEmpty: {
                        message: '网络情况不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '网络情况长度不能超过18'
                    }
                }
            },
            manageUserName: {
                message: '管理员验证失败！',
                validators: {
                    notEmpty: {
                        message: '管理员不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '管理员长度不能超过18'
                    }
                }
            },
            phone: {
                message: '联系方式验证失败！',
                validators: {
                    notEmpty: {
                        message: '联系方式不能为空'
                    },
                   /* stringLength: {
                        max: 11,
                        message: '联系方式长度不能超过11'
                    },*/
                    regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                        //regexp: /^[0-9-]*$/,
                        //regexp:/^((1\d{10})|(0\d{2,3}-?\d{7,8}))$/,
                        regexp: /^[0-9]*$/,
                        message: '请输入正确的联系方式'
                    }
                }
            },
            remark:{
            	message: '备注验证失败！',
                validators: {
                    stringLength: {
                        max: 200,
                        message: '长度不能超过200'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});

//判断重复
function uniqCheck(checkItem,msg){
    var json = {};
    var valid = false;
    $.ajax({
        type:"GET",
        url:"/zhbg/hygl/basicSet/meetingRoom/check",
        async:false,
        data:{
        	id:$("#id").val(),
        	meetingRoomNo:$("#meetingRoomNo").val()
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