/**
 * 表单验证
 * @return {[type]} [description]
 */
(function(win, $, undefined) {
    "use strict";
    var _global;

    var aotoCheckForm = {
        check: function(objs) {
            if (objs.length <=0) {
                // alert('验证元素为空');
                layer.msg('验证元素为空',{icon:0});
                return false;
            }
            var _result = true;
            for (var i=0; i<objs.length;i++) {
                var _obj = objs[i];
                var _res = $(_obj).attr('ck_type');
                var _info = $(_obj).attr('ck_info');
                var _val = $(_obj).val();
                var _arr = [];
                if (_res) {
                    _arr = _res.split(',');
                }

                for (var j = 0; j < _arr.length; j++) {
                    switch(_arr[j]) {
                        case 'ck_required': //必填
                            // 判断是否为 radio
                            if ($(_obj).attr('type') == 'ck_radio') {
                                var _name = $(_obj).attr('name');
                                _result = aotoCheckForm._radio(_name, _info);
                            } else {
                                _result = aotoCheckForm._required(_val, _info);
                            }
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        case 'ck_spechars':  //特殊字符
                            _result = aotoCheckForm._spechars(_val, _info);
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        case 'ck_max':
                            var _max = $(_obj).attr('ck_max')
                            _result = aotoCheckForm._max(_val, _max,_info);
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        case 'ck_min':
                            var _min = $(_obj).attr('ck_min')
                            _result = aotoCheckForm._min(_val, _min,_info);
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        case 'ck_email':
                            _result = aotoCheckForm._email(_val, _info);
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        case 'ck_number':
                            _result = aotoCheckForm._number(_val, _info);
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        case 'ck_chinese':
                            _result = aotoCheckForm._chinese(_val, _info);
                            if (!_result) {
                                aotoCheckForm._position(_obj);
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            return _result;
        },
        stopCheck: function(obj) {
            $(obj).attr('back',$(obj).attr('ck_type'));
            $(obj).attr('ck_type','');
        },
        startCheck: function() {
            $(obj).attr('ck_type',$(obj).attr('back'));
            $(obj).attr('back','');
        },
        _position: function(obj) {
            $('html, body').animate({  
                scrollTop: $(obj).offset().top  
            }, 2000); 
        },
        _radio: function(name, info) {
            var flag = 0;
            $('[name='+name+']').each(function(){
                if ($(this)[0].checked == true) {
                    flag = 1;
                    return;
                }
            })
            if (!flag) {
                // alert(info+': 必须选择一个');
                layer.msg(info + '：必须选择一个',{icon:0});
                return false;
            } else{
                return true;
            }
        },
        _required: function(val, info) {
            // alert(val);
            console.log(val + "--" +info);
            if (val) {
                return true;
            } else {
                // alert(info + ': 为必填项');
                layer.msg(info + '：为必填项',{icon:0});
                return false;
            }
        },
        _spechars: function(val, info) {
            var _res = /[`~!#\$%\^\&\*\(\)_\+<>\?:"\{\},\\\/;'\[\]]/im;
            if(_res.test(val)){
                // alert(info + ': 含有特殊字符');
                layer.msg(info + '：含有特殊字符',{icon:0});
                return false;     
            } else {
                return true;
            }    
        },
        _max: function(val, max,info) {
            if (val.length > max) {
                // alert(info + ': 最大字符为 '+ max);
                layer.msg(info + '：最大字符为 '+ max,{icon:0});
                return false;
            } else {
                return true;
            }
        },
        _min: function(val, min,info) {
            if (val.length < min) {
                // alert(info + ': 最小字符为 '+ min);
                layer.msg(info + '：最小字符为 '+ min,{icon:0});
                return false;
            } else {
                return true;
            }
        },
        _email: function(val, info) {
            var _res = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
            if(!_res.test(val)){
                // alert(info + ': 必须填写正确邮箱');
                layer.msg(info + '：必须填写正确邮箱',{icon:0});
                return false;     
            } else {
                return true;
            } 
        },
        _number: function(val, info) {
            var _res = /^[0-9]*$/;
            if(!_res.test(val)){
                // alert(info + ': 必须为数字');
                layer.msg(info + '：必须为数字',{icon:0});
                return false;     
            } else {
                return true;
            } 
        },
        _chinese: function(val, info) {
            var _res =  /^[\u4E00-\u9FA5]{1,}$/;
            if(!_res.test(val)){
                // alert(info + ': 必须全为中文');
                layer.msg(info + '：必须全为中文',{icon:0});
                return false;     
            } else {
                return true;
            } 
        }
    };
    
    
    // 最后将插件对象暴露给全局对象
    _global = (function(){ return this || (0, eval)('this'); }());
    if (typeof module !== "undefined" && module.exports) {
        module.exports = aotoCheckForm;
    } else if (typeof define === "function" && define.amd) {
        define(function(){return aotoCheckForm;});
    } else {
        !('aotoCheckForm' in _global) && (_global.aotoCheckForm = aotoCheckForm);
    }
})(window, jQuery)