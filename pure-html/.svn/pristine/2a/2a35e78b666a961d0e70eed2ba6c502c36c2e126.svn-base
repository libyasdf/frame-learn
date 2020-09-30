
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
            url: "",
            height:null,
            sidePagination:"server",
            pagination:true,
            striped:true,
            classes: 'table table-hover',
            fixedColumns: true,//是否固定列
            fixedNumber: "", //固定列数
            pageList: "[10, 25, 50, 100]",
            detailView:false,
            onExpandRow:function(){
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
            onEditableSave:function (field, row, oldValue, $el) {
                //可进行异步操作
            }
        };
        var _arg = $.extend(argument, json);
        if (_arg.url.indexOf("?")>=0){
            _arg.url += "&" + new Date().getTime();
        }else {
            _arg.url += "?" + new Date().getTime();
        }
        $('#' + _arg.domId).bootstrapTable({
            url: _arg.url,
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
            pageList: _arg.pageList, //分页可选的每页数据条数
            pagination: _arg.pagination, //在表格底部显示分页条
            striped: _arg.striped,//隔行变色
            classes:_arg.classes,
            queryParams: _arg.queryParams,  // 请求参数，这个关系到后续用到的异步刷新
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort // 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber
            columns: _arg.columns,
            onLoadSuccess: _arg.readOnlyFn,
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
            //编辑后保存方法
            onEditableSave:_arg.onEditableSave,
            detailView:_arg.detailView,
            onExpandRow:_arg.onExpandRow
        });
    };

    /**
     * 搜索按钮触发事件，刷新一个table
     * @param id:表格id
     */
    var refTable = function (id) {
    	//增加刷新options方法，解决不在首页条件查询的显示问题
    	var opt = $('#' + id).bootstrapTable('getOptions').pageNumber = 1;
        $('#' + id).bootstrapTable('refresh');
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
    };
    return {
        init: function (json) {
            init(json);
        },
        refTable: function (id) {
            refTable(id);
        }
    }
}();
