var PageEcho = function () {
    /**
     *
     * @type {{data: string, tableFun: tableFun}}
     */
    var argument = {
        data:"",
        tableFun:function(){}
    };

    /**
     * 解析json数据，并回显
     * @param json
     */
    var parseDate = function (json){
            // 合并参数
            var _argument = $.extend(argument, json);
            var data = _argument.data;

            // 循环处理
            for(var item in data){
                var _value = data[item];
                if(_value){
                    var _type = typeof _value;
                    // 如果是表单元素，直接赋值
                    if(_type == "string"){
                        $('[name='+item+']').html(data[item]);
                    }else if(_type == "object"){
                        var _t = _value['type'];
                        switch(_t){
                            case 'table':
                                _argument.tableFun(item,_value);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

    var hx = function(){
        for(var item in data){
            var type = $('[name='+item+']').attr('tagName')
            switch(type){
                case 'input':
                    $('[name='+item+']').val(data[item]);
                    break;
                case 'textarea':
                    $('[name='+item+']').text(data[item]);
                    break;
                case 'select':
                    $('[name='+item+']').val(data[item]);
                    break;
                case 'radio':
                    $(":radio[name='"+item+"'][value='" + data[item] + "']").prop("checked", "checked");
                    break;
                case 'checkbox':
                    $(":checkbox[name='"+item+"'][value='" + data[item] + "']").prop("checked", "checked");
                    break;
                default:
                    break;
            }
        }
    }

    return {
        parseData:function (json) {
            parseDate(json);
        }
    }
}();


