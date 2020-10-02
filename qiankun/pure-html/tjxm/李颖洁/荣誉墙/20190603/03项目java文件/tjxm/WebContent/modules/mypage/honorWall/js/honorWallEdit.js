
/**
 * 初始化页面，数据加载、渲染
 */
function tableInit(tableId){
    $('#'+tableId).bootstrapTable({
        url:"/system/honor/getListDetails",
        columns:[{
            filed: 'id'
            ,title: '序号'
            , width: '5%'
            , order: "desc"
            , align: "center"
            , formatter: function (value, row, index) {
                return "<span recordId='"+row.id+"', orderNumber='"+ row.orderNumber +"'>" + (index + 1) + "</span>"
            }
        }, {
            field: 'personName'
            , title: '姓名'
            , width: '12%'
            , align: "center"
            , visible: grVisible()
            , editable: {
                type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
                mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                title: '姓名',
                //emptytext: "",
                defaultValue:"",
                placeholder:personalOrDept(),
                validate: function (v) {
                    if (!v) return '不能为空';
                    if(v.length>50) return '不能超过50个字'
                    if(v.indexOf("(")>-1||v.indexOf(")")>-1) return '请输入中文格式括号'
                }
            }
        },   {
            field: 'personName'
            , title: '单位'
            , width: '12%'
            , align: "center"
            , visible: dwVisible()
            , editable: {
                type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
                mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                title: '单位',
                //emptytext: "",
                defaultValue:"",
                placeholder:personalOrDept(),
                validate: function (v) {
                    if (!v) return '不能为空';
                    if(v.length>50) return '不能超过50个字'
                }
            }
        },  {
            field: 'honorStory'
            , title: '光荣事迹'
            , width: '50%'
            , halign: "center"
            , align: "left"
            , editable: {
                type: "textarea",                //编辑框的类型。支持text|textarea|select|date|checklist等
                mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                //emptytext: "",
                title: '英雄事迹',
                validate: function (v) {
                    if (!v) return '不能为空';
                    if(v.length>2000) return '不能超过2000个字'

                }
            }

        }, {
            title: '&nbsp;操作&nbsp;'
            , width: '15%'
            , align: "center"
            , formatter: function (value, row, index) {
                var html = "";
                html += "<i title='删除' style='cursor:pointer'class='glyphicon glyphicon-trash' onclick='deleteDetails(\"" + row.id + "\")\'></i>&nbsp;&nbsp;";
                html += "<i title='上移' style='cursor:pointer' class='glyphicon glyphicon-arrow-up'></i>&nbsp;&nbsp;";
                html += "<i title='下移' style='cursor:pointer' class='glyphicon glyphicon-arrow-down'></i>";
                return html;
            }
        }],
        striped:true,
        editable:true,//开启编辑模式
        queryParams:function(params){
            //这里将各个查询项添加到要传递的参数中
//                可以做一些校验
            params.resId = $("#resId").val();
            params.honorId = $("#honorId").val();
            params.rdm=Math.random();
            return params;
        },
        responseHandler:function (data) {
            var arry = [];
            $.each(data,function(i,obj){
                if (obj.featsType == $("#featsType").val()) {
                    arry.push(obj);
                }
            })
            return arry;
        },
        onLoadSuccess:function (datas) {
            var $up = $(".glyphicon-arrow-up");
            $up.click(function() {
                var $tr = $(this).parents("tr");
                var id = $tr.find("td span[recordId]").attr("recordId");
                var number = $tr.find("td span[orderNumber]").attr("orderNumber");
                if ($tr.index() == 0) {
                    layer.msg("首行不可上移！", {
                        icon: 0
                    });
                    return false;
                }else{
                    var $exchange = $tr.prev();
                    var exchangeId = $exchange.find("td span[recordId]").attr("recordId");
                    var exchangeNumber = $exchange.find("td span[orderNumber]").attr("orderNumber");
                    $.ajax({
                        type : "POST",
                        url : "/system/honor/changeNumber?rdm="+Math.random(),
                        data : {
                            id : id,
                            number : number,
                            exchangeId : exchangeId,
                            exchangeNumber : exchangeNumber
                        },
                        dataType : "json",
                        success : function(data) {
                            var activeId = $('.active').attr("for");
                            var index = activeId.substr(activeId.length-1,1);
                            $('#right_table'+index).bootstrapTable('refresh');
                            //$tr.fadeOut().fadeIn();
                            //$tr.prev().before($tr);
                        },
                        error : function() {

                        }
                    });
                }



            });

            var $down = $(".glyphicon-arrow-down");
            var len = $down.length;
            $down.click(function() {
                var $tr = $(this).parents("tr");
                var id = $tr.find("td span[recordId]").attr("recordId");
                var number = $tr.find("td span[orderNumber]").attr("orderNumber");
                if ($tr.index() == len - 1) {
                    layer.msg("最后一行不可下移！", {
                        icon: 0
                    });
                    return false;
                }else {
                    var $exchange = $tr.next();
                    var exchangeId = $exchange.find("td span[recordId]").attr("recordId");
                    var exchangeNumber = $exchange.find("td span[orderNumber]").attr("orderNumber");
                    $.ajax({
                        type: "POST",
                        url: "/system/honor/changeNumber?rdm="+Math.random(),
                        data: {
                            id: id,
                            number: number,
                            exchangeId: exchangeId,
                            exchangeNumber: exchangeNumber
                        },
                        dataType: "json",
                        success: function (data) {
                            var activeId = $('.active').attr("for");
                            var index = activeId.substr(activeId.length - 1, 1);
                            $('#right_table' + index).bootstrapTable('refresh');
                            //$tr.fadeOut().fadeIn();
                            //$tr.next().after($tr);
                        },
                        error: function () {

                        }
                    });
                }
            });
        },
        //保存的使用
        onEditableSave: function (field, row, oldValue, $el) {
            row.honor = {"id":$("#honorId").val()};
            row.featsType = $("#featsType").val();
            //可进行异步操作
            $.ajax({
                type: "post",
                url: "/system/honor/editHonorDetails?rdm="+Math.random(),
                data: JSON.stringify(row),
                contentType:"application/json",
                dataType: 'JSON',
                success: function (data, status) {
                    if (status == "success") {
                        layer.msg('提交数据成功！',{icon:1}); //没有数据时弹出提示信息
                    }
                },
                error: function () {
                    layer.msg('编辑失败！',{icon:0}); //没有数据时弹出提示信息
                },
                complete: function () {

                }

            });
        }
    });
}

$(function () {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#honorId").val(theRequest.honorId);
    var year = theRequest.year;
    $("#year").val(year);

    $(".name").text(year+"年度基本信息");

    tableInit('right_table1');
    $("#featsType").val(1);
    $('.main').children().addClass('hidden');
    $('#formpage1').removeClass('hidden');

})

function deleteDetails(id) {
    var resId = $("#resId").val();
    var activeId = $('.active').attr("for");
    var index = activeId.substr(activeId.length-1,1);
    layer.confirm("确定要删除吗?", {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: '/system/honor/deleteDetails?id=' + id + "&resId=" + resId+"&rdm="+Math.random()
            , type: "GET"
            , dataType: "json"
            , success: function (result) {
                var msg = "";
                if (result.flag === "1") {
                    msg = '删除成功！';
                    if ($.trim(result.msg)) {
                        msg = result.msg;
                    }
                    layer.msg(msg, {icon: 1});
                    $('#right_table' + index).bootstrapTable('refresh');
                } else {
                    msg = '删除失败，请重试！';
                    if ($.trim(result.msg)) {
                        msg = result.msg;
                    }
                    layer.msg(msg, {icon: 2});
                }
            }
            , error: function () {
                layer.msg('删除失败，请重试！', {icon: 2});
            }
        })
    });

}

function grVisible(){
    var id = $('.active').attr("for");
    var index = id.substr(id.length-1,1);
    if(index == "6" || index == "7" || index == "8"){
        return false;
    }else{
        return true;
    }
}

function dwVisible(){
    var id = $('.active').attr("for");
    var index = id.substr(id.length-1,1);
    if(index == "6" || index == "7" || index == "8"){
        return true;
    }else{
        return false
    }
}

function addRow(count){
    if(count > 0) {
        var id = $('.active').attr("for");
        var index = id.substr(id.length - 1, 1);
        var params = JSON.stringify({
            personName: "",
            honorStory: "",
            featsType: index,
            honor: {"id": $("#honorId").val()}
        });
        $.ajax({
            type: "post",
            url: "/system/honor/editHonorDetails?rdm="+Math.random(),
            data: params,
            async: false,
            contentType: "application/json",
            dataType: 'JSON',
            success: function (data, status) {
                if (status == "success") {
                    $('#right_table' + index).bootstrapTable('refresh');
                }
            },
            error: function () {
                layer.msg('新增失败！', {icon: 0}); //没有数据时弹出提示信息
            }
        });
        count--;
        addRow(count);
    }
}

function personalOrDept(){
	var id = $('.active').attr("for");
    var index = id.substr(id.length-1,1);
    if(index == "6" || index == "7" || index == "8"){
        return "单位名称";
    }else{
        return "姓名 或 姓名（单位名称）";
    }
}
/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val) {
    if (!val) {
        val = "";
    }
    return val;
}



