/**
 * 动态表格
 */
function DynamicTable(arg){
    /**
     * 默认参数
     * tableId: 表格ID
     * getUrl: 获取数据的url
     * deleteFun: 删除数据的回调函数
     * saveFun: 编辑数据的回调函数
     * @type {Object}
     */
    this.tableId = arg.tableId || "";
    this.getUrl = arg.getUrl || "";
    this.deleteFun = arg.deleteFun || function(obj){}
    this.saveFun = arg.saveFun || function(obj){}
    this.allDeleteFun = arg.allDeleteFun || function(objs){}
    this.cache = null;
}

DynamicTable.prototype.init = function(){
    this.loadData();
}
// 加载数据
DynamicTable.prototype.loadData = function(){
    var that = this;
    $.getJSON(that.getUrl, function (data) {
        // 编辑数据 data.select
        var html = that.cache = that.joinHtml(data.select);
        var d = data.data;
        // 回显数据
        if(d){
            // 清空
            $("#"+that.tableId).find('tbody').empty();
            for(var i = 0;i < d.length;i++) {
                var $Html = $(html);
                var item = d[i];
                for(var j in item){
                    if(j != "number"){
                        $Html.find('[name='+j+']').val(item[j]).attr("disabled","true");
                    }else{
                        $Html.find('[name='+j+']').html(item[j]);
                    }
                }
                $Html.find('.glyphicon-edit').removeClass('hidden')
                $Html.find('.glyphicon-ok').addClass('hidden')
                $("#"+that.tableId).find('tbody').append($Html);
            }
        }else{
            $("#"+that.tableId).find('tbody').empty().append(html);
        }

        // 绑定处理事件
        that.bindFun();
    })
}

// 拼接html
DynamicTable.prototype.joinHtml = function(data){
    var html = [], item, val, option, options;
    html.push('<tr>');
    for (item in data) {
        val = data[item];
        if(val.type == "text"){
            html.push('<td><input type="text" class="form-control" name="'+item+'"></td>');
        }else if(val.type == "select"){
            html.push('<td><select class="form-control" name="'+item+'">');
            options = val.data;
            for(option in options){
                html.push('<option value="'+option+'">'+options[option]+'</option>');
            }
            html.push('</select></td>')
        }else if(val.type == "number"){
            html.push('<td  class="text-center" >');
            html.push('<div class="checkbox">');
            html.push('<label><input type="checkbox" name="allRemove"><span name="'+item+'" ></span></label>');
            html.push('</div>');
            html.push('</td>');
        }else if(val.type == "hidden"){
            html.push('<input type="hidden" name="'+item+'" />' );
        }
    }
    html.push('<td class="text-center" >');
    html.push('<span class="glyphicon glyphicon-ok"></span> ');
    html.push('<span class="glyphicon glyphicon-edit hidden"></span> ');
    html.push('<span class="glyphicon glyphicon-remove"></span> ');
    html.push('</td>');
    html.push('</tr>');
    return html.join("");
}

// 绑定事件
DynamicTable.prototype.bindFun = function(){
    var that = this;
    // add
    $("#"+that.tableId).siblings('div').find('.addTR').unbind('click').bind('click',function(){
       
        var $HTML = $(that.cache);
        
        // 添加到末尾
        //$HTML.find('[name=number]').html($("#"+that.tableId).find('tbody').find('tr').length +1)
        //$("#"+that.tableId).find('tbody').append($HTML);
       
        // 添加到第一行
        $("#"+that.tableId).find('tbody').prepend($HTML);
        $("#"+that.tableId).find('tr').each(function(i,item){
            $(this).find('[name=number]').html(i);
        })
        
    })

    // all select 
   $("#"+that.tableId).siblings('div').find('.allSelect').unbind('click').bind('click',function(){
        var $Obj =  $("#"+that.tableId).find('tbody');
        if($Obj.find('[name=allRemove]:checked').length > 0){
            $Obj.find('[name=allRemove]').removeProp("checked"); 
        }else{
            $Obj.find('[name=allRemove]').prop("checked","true")
        }
    })

    // delete
    $("#"+that.tableId).find('.glyphicon-remove').unbind('click').bind('click',function(){
        if(that.deleteFun){
            var $tr = $(this).parent().parent();
            that.deleteFun($tr);
        }
    })

    // all delete
    $("#"+that.tableId).siblings('div').find('.allDelete').unbind('click').bind('click',function(){
        if(that.allDeleteFun){
            var objs = $("#"+that.tableId).find('tbody');
            if(objs.find('[name=allRemove]:checked').length > 0 ){
                 that.allDeleteFun(objs.find('tr'));
            }else{
                alert('请选择要删除的数据')
            }
           
        }
    })


    // save
    $("#"+that.tableId).find('.glyphicon-ok').unbind('click').bind('click',function(){
        if(that.saveFun){
            var $tr = $(this).parent().parent();
            that.saveFun($tr);
        }
    })

    // edit
    $("#"+that.tableId).find('.glyphicon-edit').unbind('click').bind('click',function(){
        var $tr = $(this).parent().parent('tr');
        // 切换到可编辑状态
        $tr.find(':disabled').removeAttr("disabled");
        $(this).addClass('hidden');
        $(this).siblings('.glyphicon-ok').removeClass('hidden');
        
    })
}


/**
 * 分页表格
 * 编辑,删除,全选删除
 */

function PagingTable(arg){
	this.tableId = arg.tableId || "";
	this.pageId = arg.pageId || "";
	this.page = arg.page || 1;
	this.length = arg.length || 5;
	this.totalPages = arg.totalPages || 1;
    this.getUrl = arg.getUrl || "";
    this.deleteFun = arg.deleteFun || function(obj){};
    this.saveFun = arg.saveFun || function(obj){};
    this.allDeleteFun = arg.allDeleteFun || function(objs){};
}

PagingTable.prototype.init = function(){
	this.loadData();
}

PagingTable.prototype.loadData = function(){
    var that = this;
    $.getJSON(that.getUrl, {page:that.page,length:that.length},function (data) {
        // 编辑数据 data.select
        var html = that.cache = that.joinHtml(data.select);
        var d = data.data;
        // 回显数据
        if(d){
            // 清空
            $("#"+that.tableId).find('tbody').empty();
            for(var i = 0;i < d.length;i++) {
                var $Html = $(html);
                var item = d[i];
                for(var j in item){
                    if(j != "number"){
                        $Html.find('[name='+j+']').val(item[j]).attr("disabled","true");
                    }else{
                        $Html.find('[name='+j+']').html(item[j]);
                    }
                }
                $Html.find('.glyphicon-edit').removeClass('hidden')
                $Html.find('.glyphicon-ok').addClass('hidden')
                $("#"+that.tableId).find('tbody').append($Html);
            }
        }else{
            $("#"+that.tableId).find('tbody').empty().append(html);
        }

        // 绑定处理事件
        that.bindFun();

        // 设置总条数
        that.totalPages = data.totalPages;
        // that.page = data.page || that.page;

        // 分页
        $('#'+that.pageId).bootstrapPaginator({
		     currentPage: that.page,//当前的请求页面。
		     totalPages: that.totalPages,//一共多少页。
		     size:"normal",//应该是页眉的大小。
		     bootstrapMajorVersion: 3,//bootstrap的版本要求。
		     alignment:"right",
		     numberOfPages:that.length,//一页列出多少数据。
		     //如下的代码是将页眉显示的中文显示我们自定义的中文。
		     itemTexts: function (type, page, current) {
		         switch (type) {
		         case "first": return "首页";
		         case "prev": return "上一页";
		         case "next": return "下一页";
		         case "last": return "末页";
		         case "page": return page;
		         }
		     },
		     onPageClicked: function(event, originalEvent, type, page){
		     	that.page = page;
		     	that.loadData();
		     }
		});

    })
}


PagingTable.prototype.joinHtml = function(data){
    var html = [], item, val, option, options;
    html.push('<tr>');
    for (item in data) {
        val = data[item];
        if(val.type == "text"){
            html.push('<td><input type="text" class="form-control" name="'+item+'"></td>');
        }else if(val.type == "select"){
            html.push('<td><select class="form-control" name="'+item+'">');
            options = val.data;
            for(option in options){
                html.push('<option value="'+option+'">'+options[option]+'</option>');
            }
            html.push('</select></td>')
        }else if(val.type == "number"){
            html.push('<td  class="text-center" >');
            html.push('<div class="checkbox">');
            html.push('<label><input type="checkbox" name="allRemove"><span name="'+item+'" ></span></label>');
            html.push('</div>');
            html.push('</td>');
        }else if(val.type == "hidden"){
            html.push('<input type="hidden" name="'+item+'" />' );
        }
    }
    html.push('<td class="text-center" >');
    html.push('<span class="glyphicon glyphicon-ok"></span> ');
    html.push('<span class="glyphicon glyphicon-edit hidden"></span> ');
    html.push('<span class="glyphicon glyphicon-remove"></span> ');
    html.push('</td>');
    html.push('</tr>');
    return html.join("");
}

PagingTable.prototype.bindFun = function(){
	var that = this;
	// all select 
   $("#"+that.tableId).siblings('div').find('.allSelect').unbind('click').bind('click',function(){
        var $Obj =  $("#"+that.tableId).find('tbody');
        if($Obj.find('[name=allRemove]:checked').length > 0){
            $Obj.find('[name=allRemove]').removeProp("checked"); 
        }else{
            $Obj.find('[name=allRemove]').prop("checked","true")
        }
    })

    // delete
    $("#"+that.tableId).find('.glyphicon-remove').unbind('click').bind('click',function(){
        if(that.deleteFun){
            var $tr = $(this).parent().parent();
            that.deleteFun($tr);
        }
    })

    // all delete
    $("#"+that.tableId).siblings('div').find('.allDelete').unbind('click').bind('click',function(){
        if(that.allDeleteFun){
            var objs = $("#"+that.tableId).find('tbody');
            if(objs.find('[name=allRemove]:checked').length > 0 ){
                 that.allDeleteFun(objs.find('tr'));
            }else{
                alert('请选择要删除的数据')
            }
           
        }
    })


    // save
    $("#"+that.tableId).find('.glyphicon-ok').unbind('click').bind('click',function(){
        if(that.saveFun){
            var $tr = $(this).parent().parent();
            that.saveFun($tr);
        }
    })

    // edit
    $("#"+that.tableId).find('.glyphicon-edit').unbind('click').bind('click',function(){
        var $tr = $(this).parent().parent('tr');
        // 切换到可编辑状态
        $tr.find(':disabled').removeAttr("disabled");
        $(this).addClass('hidden');
        $(this).siblings('.glyphicon-ok').removeClass('hidden');
        
    })

    
}
