var tName = ""; // 当前表名
var id = ""; // 系统唯一关键字

var form = []; // 表单数据
var formDefault = {}; // 表单默认值

var $tr = null;

$(function () {

    // 获取表名和系统唯一关键字
    tName = $('.tab-active', window.parent.document).attr('data-TABLE_NAME');
    $tr = $('.tab-panel table tbody tr.readonly', window.parent.document);
    id = $tr.find('td:eq(0) [type="checkbox"]').val();
    console.log("表名", tName, "\n系统唯一关键字", id);

    // 获取表单默认值
    getFormDefault();
});

/**
 * 获取表单默认值
 */
function getFormDefault() {
    $.ajax({
        url: '/dagl/bmgl/findById',
        data: {
            tName: tName, // 表名
            id: id
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            // 解析 data 并给全局变量赋值
            console.log("获取表单默认值", data[0]);
            formDefault = data[0];

            // 加载表单
            initForm();
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表单失败，错误信息：\n", err);
        }
    });
}

// 初始化表单
function initForm() {

    // findById
    $.ajax({
        url: '/dagl/bmgl/findAdd',
        data: {
            tName: tName // 表名
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            // 解析数据，并根据 COLUMN_ORDER 对数据进行排序，并修改数据中的默认值
            var usefullData = [];

            for (var i = 0; i < data.length; ++i) {
                if (data[i].COLUMN_VISIBLE === "T") {
                    usefullData.push(data[i]);
                    usefullData[usefullData.length - 1].COLUMN_VALUE_DEFAULT = formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')];
                }
            }

            usefullData.sort(sortByNumber('COLUMN_ORDER'));
            console.log("加载表单有效的数据\n", usefullData);

            // 给全局变量赋值
            form = usefullData;

            // 渲染表单控件
            for (var i = 0; i < usefullData.length; ++i) {
                loadInput(usefullData[i]);
            }

            // 让 iframe 自适应高度，但最高 500px
            // $('iframe', window.parent.document).height($(document).height()).css({
            //     'max-height': 'none',
            //     overflow: 'auto'
            // });

            // 关闭动画
            layer.closeAll();

            // 给保存按钮绑定方法
            $('.app .btn.btn-primary').click(closeMe);

        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表单失败，错误信息：\n", err);
        },
        complete:function(){//查询自定义样式执行
            $.ajax({
                'url' :'/dagl/bmgl/findColumnWidth',
                'type':'POST',
                'dataType':'text',
                'async': false,//无此,样式会被看到
                'data' : {
                	tName:tName
                }
            }).done(function( data, textStatus, jqXHR ) {
            	data =$.parseJSON(data);
            	
                //返回值格式
                //var data=[{"key":"basefolder_unit","value":"col-xs-4"},{"key":"folder_no","value":"col-xs-4"},{"key":"year_folder_no","value":"col-xs-3"},{"key":"maintitle","value":"col-xs-6"},{"key":"piece_num","value":"col-xs-6"},{"key":"page_num","value":"col-xs-6"},{"key":"filing_year","value":"col-xs-6"},{"key":"retention","value":"col-xs-6"},{"key":"doc_start_time","value":"col-xs-6"},{"key":"doc_end_time","value":"col-xs-6"},{"key":"sbt_word","value":"col-xs-6"},{"key":"pigeonhole_date","value":"col-xs-6"},{"key":"note","value":"col-xs-4"},{"key":"leibiehao","value":"col-xs-4"},{"key":"object_quantity","value":"col-xs-6"},{"key":"fonds_no","value":"col-xs-4"},{"key":"ad_folderno","value":"col-xs-6"},{"key":"temp_no","value":"col-xs-6"},{"key":"archive_entity_status","value":"col-xs-6"},{"key":"archive_ctg_no","value":"col-xs-6"},{"key":"quantity","value":"col-xs-6"},{"key":"folder_location","value":"col-xs-5"},{"key":"security_class","value":"col-xs-6"},{"key":"content_no","value":"col-xs-6"},{"key":"barcode","value":"col-xs-6"},{"key":"swlx","value":"col-xs-6"},{"key":"classification","value":"col-xs-6"}]
                for (var intt = data.length; intt >0; intt--){
                    var key=data[intt-1].key;
                    var value=data[intt-1].value;
                    var form_name_class=$("[name='"+key+"']").parents(".form-group").attr("class").replace("form-group ","");
                    $("[name='"+key+"']").parents(".form-group").removeClass(form_name_class).addClass(value);
                    $("form").prepend($("[name='"+key+"']").parents(".form-group")[0]);

                }

            })}
    });
}

/**
 * 加载表单控件
 * @param data 加载表单控件所需要的数据对象
 */
function loadInput(data) {
    // T text S select A textarea(只会占一行) D treelayer F 
    switch (data.COLUMN_INPUT_TYPE) {
        case "T":
            loadTextInput(data);
            break;
        case "S":
            loadTextInput(data);
            break;
        case "A":
            loadTextArea(data);
            break;
        case "D":
            loadTextInput(data);
            break;
        default:
            break;
    }
}

/**
 * 加载文本框
 * @param data 加载文本框所需要的数据对象
 */
function loadTextInput(data) {
    $('.app form.row').append($(''
        + '<div class="form-group col-xs-6">'
        + '<div class="col-xs-3">'
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '</div>'
        + '<div class="col-xs-9">'
        + '<input type="text" disabled="disabled" class="form-control" value="'
        + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "")
        + '" name="' + data.COLUMN_NAME + '">'
        + '<em class="input-em text-info"></em>'
        + '</div>'
        + '</div>')
    );
}

/**
 * 加载文本域
 * @param data 加载文本域所需要的数据对象
 */
function loadTextArea(data) {
    $('.app form.row').append($(''
        + '<div class="form-group col-sm-12">'
        + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '<textarea readonly>' + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "") + '</textarea>'
        + '<em class="input-em text-info"></em>'
        + '</div>'
    ));
}

/**
 * 关闭本页面
 */
function closeMe() {
	
	
    // 当你在iframe页面关闭自身时
    var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
    parent.layer.close(index); // 再执行关闭
}