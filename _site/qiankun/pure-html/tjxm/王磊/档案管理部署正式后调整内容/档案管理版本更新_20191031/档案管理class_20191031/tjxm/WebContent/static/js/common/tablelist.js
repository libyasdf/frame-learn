
var TableInit = function () {

    /**
     *  初始化函数
     */
    var init = function (json) {
        initTable(json);
        funBind();
    };

    /**
     * 数据表格配置初始化
     * @param  {[type]} json [description]
     * @return {[type]}      [description]
     */
    var initTable = function (json) {
        /**
         * 默认参数
         * domId:<table>标签的id，
         * url：默认加载数据的url，
         * columns：表格列的定义，
         * queryParams：请求参数
         * readOnlyFn：只读的回调函数
         */
        var argument = {
            domId: null,
            method : 'get',
            contentType:'application/json',
            url: "",
            height:null,
            sidePagination:"server",
            pagination:true,
            striped:true,
            classes: 'table table-hover',
            fixedColumns: false,//是否固定列
            fixedNumber: "", //固定列数
            pageSize: 10,
            pageList: "[10, 25, 50, 100]",
            detailView:false,
            onExpandRow:function(){
            },
            onCollapseRow:function(){
            },
            columns: {},
            queryParams: function (params) {
                return params;
            },
            readOnlyFn: function () {
            },
            rowStyle: function (row, index) {	//默认样式：居中，鼠标为手
                return {
                    classes: 'readOnly'
                    ,css: {"text-align":"center","cursor":"pointer"}
                };
            },
            responseHandler:function (res) {//参数sidePagination为server时，该方法必须返回total和rows
                if (res.flag === '1'){
                    return {
                        "total":res.data.total
                        ,"rows":res.data.rows
                    }
                }else {
                    layer.msg("加载列表发生错误!", {icon: 0});
                }
                return {};
            },
            onReorderRowsDrag:null,
            onReorderRowsDrop:null,
            onReorderRow:null,
            onCheck: function () {
            },
            onUncheck: function () {
            },
            onCheckAll: function () {
            },
            onUncheckAll: function () {
            },
            onEditableSave:function (field, row, oldValue, $el) {
                //可进行异步操作
            },
            onClickRow:function(field, row, oldValue, $el){
                //单击事件
            }
        };
        var _arg = $.extend(argument, json);

        _arg.url = 'http://'+ window.location.host + '/'+ _arg.url;  // 解决新版本门户中请求url错误的问题,不影响旧版本。（杨奇新增）

        if (_arg.url.indexOf("?")>=0){
            _arg.url += "&" + new Date().getTime();
        }else {
            _arg.url += "?" + new Date().getTime();
        }
        $('#' + _arg.domId).bootstrapTable({
            url: _arg.url,
            method : _arg.method,
            contentType:_arg.contentType,
            height:_arg.height,
            // method:"post", //以post的方式请求，有后台后开启
            sortable: true,
            cache:false,		//设置不缓存，否则在IE下刷新方法不请求后台
            silentSort:false,	//默认值：true，设置成false时，在分页时自动记住排序项
            rowStyle:_arg.rowStyle,//自定义行样式
            //sortOrder: "desc",
            //dataField: "res",//bootstrap table 可以前端分页也可以后端分页，这里
            //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
            //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
            sidePagination: _arg.sidePagination, //分页方式为server
            fixedColumns: _arg.fixedColumns,//是否固定列
            fixedNumber: _arg.fixedNumber, //固定列数
            pageSize:_arg.pageSize,
            pageList: _arg.pageList, //分页可选的每页数据条数
            pagination: _arg.pagination, //在表格底部显示分页条
            striped: _arg.striped,//隔行变色
            classes:_arg.classes,
            queryParams: _arg.queryParams,  // 请求参数，这个关系到后续用到的异步刷新
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort // 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber
            columns: _arg.columns,
            onLoadSuccess: function (data) {
                //  激活bootstrap table 分页下拉框（兼容新版们处理）
                if ($('.dropdown-toggle:visible').length>0 && $('.dropdown-toggle:visible').dropdown){
                    $('.dropdown-toggle').dropdown();
                }
                _arg.readOnlyFn(data);
            },
            responseHandler:_arg.responseHandler,
            singleSelect:_arg.singleSelect,
			checkboxHeader:_arg.checkboxHeader,//设置 false 将在列头隐藏全选复选框
            //拖动排序方法
            onReorderRowsDrag:_arg.onReorderRowsDrag,
            onReorderRowsDrop:_arg.onReorderRowsDrop,
            onReorderRow:_arg.onReorderRow,
            //选中方法
            onCheck:_arg.onCheck,
            onUncheck:_arg.onUncheck,
            onCheckAll:_arg.onCheckAll,
            onUncheckAll:_arg.onUncheckAll,
            //编辑后保存方法
            onEditableSave:_arg.onEditableSave,
            detailView:_arg.detailView,
            onExpandRow:_arg.onExpandRow,
            onCollapseRow:_arg.onCollapseRow,
            onClickRow:_arg.onClickRow,
            onAll:function (name,args) {//所有的事件都会触发该事件，这里用来添加页面跳转功能
                //如果是加载成功的事件
                if("load-success.bs.table" == name){
                    jumpPage(_arg.domId);
                }
            }
        });
    };

    /**
     * 搜索按钮触发事件，刷新一个table
     * @param id:表格id
     */
    var refTable = function (id) {
        var pageNumber = $('#' + id).bootstrapTable('getOptions').pageNumber;
        if(pageNumber){
            //增加刷新options方法，解决不在首页条件查询的显示问题
            var opt = $('#' + id).bootstrapTable('getOptions').pageNumber = 1;
            var params = {};
            var f = true;
            var par= $('#' + id).bootstrapTable('getOptions').queryParams(params);
            console.log(par);
            if(par){
                for(var key in par){
                    var value = par[key] || "";
                    //学习考核的在线培训统计查询项需要用到小数，去掉英文小数点。王磊2019-03-01
                    //档案管理表名中存在英文的下划线_，去掉英文下划线。王磊 20190423
                    //档案管理加载列表时，参数中中存在英文的下划线{}":，去掉英文{}":。王富康20191101
                    var regEn = /[`~!@#$%^&*()+<>?\/;'[\]]/im,
                        regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
                    if(regEn.test(value) || regCn.test(value)) {
                        layer.msg("查询条件中不能包含特殊字符");
                        f = false;
                        return false;
                    }
                }

            }
            if(f){
                $('#' + id).bootstrapTable('refresh');
            }
        }
    };

    /**
     * 事件的绑定
     * @return {[type]} [description]
     */
    var funBind = function(){
        // 初始化查询
        $('.panel-heading').unbind('click').bind('click',function(){
            if($(this).find('.showSelect').hasClass('glyphicon-minus')){
                // $('.panel-body').hide("slow");
                $('.panel-body').hide();
                $(this).find('.showSelect').removeClass('glyphicon-minus').addClass('glyphicon-plus');
            }else{
                // $('.panel-body').show("slow");
                $('.panel-body').show();
                $(this).find('.showSelect').removeClass('glyphicon-plus').addClass(' glyphicon-minus');
            }
        })
        //为查询条件form表单绑定submit事件，返回false,阻止当form中只有一个input时，点击回车刷新页面的问题
        $("form.form-horizontal").unbind('submit').bind('submit',function(){return false;});
    };

    /**
     * 页码跳转的功能
     * @param domId 表格dom元素ID
     */
    var jumpPage = function(domId){
        var opt = $('#' + domId).bootstrapTable('getOptions');
        var totalPages = opt.totalPages;//总页数
        if(opt.pagination && totalPages > 1){//如果需要分页，并且总页数大于1
            //加载成功后，添加一个跳转页码的控件
            var jumpHtml = [];
            jumpHtml.push('   <span id="jump" class="page-list">跳转到第 <span class="btn-group dropup">');
            jumpHtml.push('<input class="form-control" style="width: 60px;" id="jumpPage"/>');
            jumpHtml.push('</span> 页</span>');
            $('#' + domId).parent("div").siblings(".fixed-table-pagination").find(".pagination-detail").append(jumpHtml.join(''));
            //$(".pagination-detail").append(jumpHtml.join(''));
            //每次跳转成功后，需要手动选中对应的页码
            var aTab = $("ul.pagination").find("li").find("a");
            $.each(aTab,function (i,n) {
                if($(n).text() == opt.pageNumber){
                    $(n).parent("li").addClass("active");
                }
            });
            //绑定回车跳转事件
            $("#jumpPage").on("keyup",function (e) {
                var key = e.which;
                if(key == 13){
                    var val = $("#jumpPage").val();//要跳转的页码
                    if(val != ""){
                        if(isNaN(val)){
                            console.log("不是数字");
                            layer.msg("请输入数字");
                            $("#jumpPage").val("");
                        }else if(val > totalPages || val <= 0){
                            console.log("不在1~2之间");
                            layer.msg("请输入1~"+totalPages+"之间的数字");
                            $("#jumpPage").val("");
                        }else{
                            if(val.indexOf(".") != -1){
                                console.log("不是整数");
                                layer.msg("请输入1~"+totalPages+"之间的整数");
                                $("#jumpPage").val("");
                            }else{
                                console.log("可以跳页",val);
                                $('#' + domId).bootstrapTable('selectPage', val);
                            }

                        }
                    }
                }
            })
        }
    };

    return {
        init: function (json) {
            init(json);
        },
        refTable: function (id) {
            refTable(id);
        },
        jumpPage:function (id) {
            jumpPage(id);
        }
    }
}();
