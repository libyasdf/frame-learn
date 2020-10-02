$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#noticeId").val(regVlaue(theRequest.noticeId));

    // tab页切换
    $('.nav-tabs > li').unbind('click').bind('click', function () {
        var id = $(this).attr('for');
        $(this).addClass('active').siblings('li').removeClass('active');
        $('.main').children().addClass('hidden');
        $('#' + id).removeClass('hidden');
    })

	//已反馈列表
    getBackList();

    //未反馈人员
    getBackUserList();

    //未反馈处室
    getBackDeptList();

})

/**
 * 已反馈列表
 */
function getBackList(){
    var right_table_col = [{
        title: '序号'
        , width: '5%'
        , align: "center"
        , formatter: function (value, row, index) {
            //计算序号，并存放业务ID
            var pageSize=$('#backTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
            var pageNumber=$('#backTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
            var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
            return orderNum;
        }
    }, {
        field: 'backUserName'
        , title: '反馈人'
        , width: '10%'
        , align: "center"
    }, {
        title: '单位'
        , width: '20%'
        , align: "center"
        , formatter: function(value, row, index){
            var dept = "";
            if(row.backChushiName != null && row.backChushiId != row.backDeptId){
                dept = row.backChushiName ;
            }
            if(row.backJuName != null && row.backJuId != row.backChushiId && row.backJuId != row.backDeptId){
                dept = row.backJuName + "/" + dept;
            }
            return dept;
        }
    }, {
        field: 'backTime'
        , title: '反馈时间'
        , order: "desc"
        , width: '20%'
        , sortable: true
        , align: "center"
    }, {
        field: 'backContent'
        , title: '反馈内容'
        , width: '30%'
        , align: "center"
    }, {
        field: 'hasAffix'
        , title: '附件'
        , width: '10%'
        , align: 'center'
        , formatter: function (value, row, index) {
            if(value){
                return "<a href='javascript:void(0);' onclick='scanAffix(\"" + row.id + "\");' title='点击查看附件'>查看附件</a>";
            }else{
                return "暂无附件"
            }
        }
    }];

    //初始化表格
    TableInit.init({
        domId: "backTable",
        url: "/system/notice/getAllBack",
        columns: right_table_col,
        queryParams: function (params) {
            params.resId = $('#resId').val();
            params.noticeId = $("#noticeId").val();
            return params;
        },
        rowStyle: function (row, index) {	//默认样式：居中
            return {
                classes: 'readOnly'
                ,css: {"text-align":"center"}
            };
        }
    });
}

/**
 * 查看反馈附件
 */
function scanAffix(id){
	layer.open({
        type: 2,
        content: "/modules/system/notice/backAffixList.html?id="+id,
        area: ['500px', '300px'],
        title: ['附件列表', 'font-size:16px;font-weight:bold;']
    })
}

/**
 * 查询未反馈用户
 */
function getBackUserList(){
    console.log("getBackUserList");
    var right_table_col = [{
        title: '序号'
        , width: '10%'
        , align: "center"
        , formatter: function (value, row, index) {
            //计算序号，并存放业务ID
            var pageSize=$('#backTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
            var pageNumber=$('#backTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
            var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
            return orderNum;
        }
    }, {
        field: 'userName'
        , title: '未反馈人'
        , width: '45%'
        , align: "center"
    }, {
        title: '处室'
        , width: '45%'
        , align: "center"
        , formatter: function(value, row, index){
            var dept = "";
            if(row.noBackChuId != ""){
                dept = "/" + row.noBackChuName ;
            }
            if(row.noBackJuId != "" && row.noBackJuId != row.noBackChuId){
                dept = row.noBackJuName + dept;
            }
            return dept;
        }
    }];

    //初始化表格
    TableInit.init({
        domId: "noBackUserTable",
        url: "/system/notice/getAllNoBackUser",
        columns: right_table_col,
        sidePagination:"client",
        queryParams: function (params) {
            params.resId = $('#resId').val();
            params.noticeId = $("#noticeId").val();
            return params;
        },
        responseHandler:function (res) {//采用前端分页，直接返回json数据即可
            if (res.flag === '1'){
                return res.data.rows;
            }else {
                layer.msg("加载列表发生错误!", {icon: 0});
            }
            return {};
        },
        rowStyle: function (row, index) {	//默认样式：居中
            return {
                classes: 'readOnly'
                ,css: {"text-align":"center"}
            };
        }
    });
}

/**
 * 查询未反馈处室
 */
function getBackDeptList(){
    console.log("getBackDeptList");
    var right_table_col = [{
        title: '序号'
        , width: '20%'
        , align: "center"
        , formatter: function (value, row, index) {
            //计算序号，并存放业务ID
            var pageSize=$('#backTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
            var pageNumber=$('#backTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
            var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
            return orderNum;
        }
    }, {
        title: '未反馈处室'
        , width: '80%'
        , align: "center"
        , formatter: function(value, row, index){
            var dept = "";
            if(row.noBackChuId != ""){
                dept = "/" + row.noBackChuName ;
            }
            if(row.noBackJuId != "" && row.noBackJuId != row.noBackChuId){
                dept = row.noBackJuName + dept;
            }
            return dept;
        }
    }];

    //初始化表格
    TableInit.init({
        domId: "noBackDeptTable",
        url: "/system/notice/getAllNoBackChu",
        columns: right_table_col,
        sidePagination:"client",
        queryParams: function (params) {
            params.resId = $('#resId').val();
            params.noticeId = $("#noticeId").val();
            return params;
        },
        responseHandler:function (res) {//采用前端分页，直接返回list数据即可
            if (res.flag === '1'){
                return res.data.rows;
            }else {
                layer.msg("加载列表发生错误!", {icon: 0});
            }
            return {};
        },
        rowStyle: function (row, index) {	//默认样式：居中
            return {
                classes: 'readOnly'
                ,css: {"text-align":"center"}
            };
        }
    });
}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}