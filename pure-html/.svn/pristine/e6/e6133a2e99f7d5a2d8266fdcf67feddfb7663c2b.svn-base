
var cateId ="";//点击查询表格式初始化门类，用于新增和删除该门类下的立卷单位数据

var addOrEdit = "";//区分新增或是删除
//获取数据字典
Dictionary.init({
    mark:'dagl_ljdw',
    type:'1',
    position:"ljdwMark",
    domType:"select",
    hasSelect:true});

Dictionary.init({
    mark:'dagl_ljdw',
    type:'1',
    position:"ljdwMarkOfSearch",
    domType:"select",
    hasSelect:true});

//三个搜索框的onchange事件
/*$('#ljdwMarkOfSearch').change(function(){
        //刷新列表
        $('#tb_customer1').bootstrapTable('refresh');
})

$('#lrrNamesOfSearch').change(function(){
        //刷新列表
        $('#tb_customer1').bootstrapTable('refresh');
})

$('#ljdwAdminNamesOfSearch').change(function(){
        //刷新列表
        $('#tb_customer1').bootstrapTable('refresh');
})*/


// 绑定点击新增按钮显示模态框事件
$('#btnShowAdd').on('click',function(){
    var nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    if (nodes[0].pId == "" || nodes[0].pId == null) {
        layer.msg("该节点无法字段信息，请在子节点下新增！", {
            icon: 0
        });
        return;
    }

    $('#myModal').modal('show');

    layer.open({
        type:1,//类型
        skin: 'layui-layer-demo', //样式类名
        title:['新 增', 'font-size:16px;font-weight:bold;'],//题目
        closeBtn: 1, //不显示关闭按钮
        anim: 2,
        shadeClose:true,//点击遮罩层关闭
        content: $('.modal-body'), //打开的内容
        area: ['50%', '55%'],
        end:function () {
            relationInitForm();
            $('.modal-body').hide();
        }
    });

    $("i[data-bv-icon-for='COLUMN_AS_SIMQUERY']").css({top:-5, 'line-height':'none'});
    $("i[data-bv-icon-for='COLUMN_VISIBLE']").css({top:-5, 'line-height':'none'});
    $("i[data-bv-icon-for='COLUMN_CAN_NULL']").css({top:-5, 'line-height':'none'});
    $("i[data-bv-icon-for='COLUMN_LIST_ISSHOW']").css({top:-5, 'line-height':'none'});
})

// 绑定点击新增按钮显示模态框事件
$('#btnShowAdd1').on('click',function(){

    var nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    if (nodes[0].pId == "" || nodes[0].pId == null) {
        layer.msg("该节点无法创建立卷单位，录入人，立卷单位管理员关系，请在子节点下新增！", {
            icon: 0
        });
        return;
    }

    if("q2" == cateId){
        layer.alert("q2档案无法创建立卷单位，录入人，立卷单位管理员关系，档案馆管理员在归档库可直接对q2档案进行维护。",{icon:4});
    }else{
        addOrEdit = "add";
        // $('#myModal1').modal('show');
        layer.open({
            type:1,//类型
            skin: 'layui-layer-demo', //样式类名
            title:['新 增', 'font-size:16px;font-weight:bold;'],//题目
            closeBtn: 1, //不显示关闭按钮
            anim: 2,
            shadeClose:true,//点击遮罩层关闭
            content: $('.addModal'), //打开的内容
            area: ['40%', '40%'],
            end:function () {
                relationInitForm();
                $('.addModal').hide();
            }
        });
    }

})


// 增加每个元素的删除按钮
//function operateFormatter(){
//    return [
//        '<i  id="TableDelete" class="glyphicon glyphicon-trash p-l-10" style="cursor:pointer"></i>'
//    ].join("");
//}

function findTreeParent(obj){
	var node = obj.getParentNode();
	//门类管理目前添加了全宗，获取门类的节点时（node.level==1），控制不要选到全宗的节点 王磊 20190411
	if(node.pId!=null && node.level !='1'){
		return findTreeParent(node);
	}else{
		return node;
	}
}

/**
 * 删除数据
 * @params selectOption Object 选择的内容
 * @params ids Array  主键的值
 * */
// 删除数据ajax请求
function delectSelect(selectOption,ids,isAll){
	var a =findTreeParent($.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0])
	var toDown = 0;
	$.ajax({
		url:"/dagl/xtpz/category/isKeYiDelete",
		type : 'post',
		dataType:"JSON",
		async:false,
		data:{
            tName:selectOption.TABLE_NAME,
            columnName:selectOption.COLUMN_NAME,
            code:a.id
        },
		success:function(data){
			if(data.result==0){

			}else if(data.result==1){
				toDown=1;
				layer.msg(data.msg,{icon:0,time:2000})
			}else{
				toDown=1;
				layer.msg("删除出现错误，请刷新页面再次尝试",{icon:0,time:2000})
			}
		},
        error:function(){
            layer.msg('删除出错，请刷新页面重新尝试',{icon:2});
        }
	})
	if(toDown==1){
		return;
	}
	if(isAll){
		 var $table =  $('#tb_customer');
		    $.ajax({
		        url:'/dagl/xtpz/category/deleteZiDuan',
		        method:'post',
		        data:{
		            tName:selectOption.TABLE_NAME,
		            columnName:selectOption.COLUMN_NAME
		        },
		        success:function(data){
		            if (data.flag==1){

		                $table.bootstrapTable('remove',
		                    {
		                        field:'COLUMN_NAME',
		                        values:ids
		                    }
		                );
		                console.log('删除成功！')
		                layer.msg('删除成功！',{icon:1});

		            }else if(data.flag==2){
		                layer.msg('该字段无法删除！',{icon:2});
		            }else{
		            	 layer.msg('删除失败！',{icon:2});
		            }
		        },
		        error:function(){
		            layer.msg('删除失败！',{icon:2});
		        }
		    });

	}else{
				layer.confirm("确定要删除此字段吗？", { icon: 3 }, function () {
			    var $table =  $('#tb_customer');
			    $.ajax({
			        url:'/dagl/xtpz/category/deleteZiDuan',
			        method:'post',
			        data:{
			            tName:selectOption.TABLE_NAME,
			            columnName:selectOption.COLUMN_NAME
			        },
			        success:function(data){
			            if (data.flag==1){

			                $table.bootstrapTable('remove',
			                    {
			                        field:'COLUMN_NAME',
			                        values:ids
			                    }
			                );
			                console.log('删除成功！')
			                layer.msg('删除成功！',{icon:1});

			            }else if(data.flag==2){
			                layer.msg('该字段无法删除！',{icon:2});
			            }else{
			            	 layer.msg('删除失败！',{icon:2});
			            }
			        },
			        error:function(){
			            layer.msg('删除失败！',{icon:2});
			        }
			    });
				});
	}
}

// 单个删除
window.operateEvents = {
    "click #TableDelete":function (e,value,row,index){
        console.log(e,value)
        console.log(row,index)
        var ids = [row.COLUMN_NAME];
        delectSelect(row,ids,false)
    }
}
// 删除所有
function delectAll(){
	var indexs = null;
    var $table =  $('#tb_customer');
    var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.COLUMN_NAME;
    })
    console.log(ids)
    if(ids.length<=0){
    	layer.msg("至少选择一项",{icon:0,time:2000})
    	return;
    }
    $.post("/dagl/xtpz/category/isDanghaoGuizhe",{code:findTreeParent($.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0]).id,columnNames:ids.toString()},function(o){
    	if(o.flag==1){
    		layer.alert(o.msg,{icon:0})
    	}else{
    		  var AllSelections = $('#tb_customer').bootstrapTable('getAllSelections');
    		    layer.confirm("确定要删除选择的字段吗？", { icon: 3 }, function (indexa) {
    			   var arr=[];
    			   indexs = layer.msg('删除中，请稍等...', {
	    		        icon: 16,
	    		        shade: [0.1, '#fff'],
	    		        time: false
	    		    });
    			   AllSelections.forEach(function(selectOption){
    				   var obj = {}
    				   obj.tableName=selectOption.TABLE_NAME
    				   obj.columnName=selectOption.COLUMN_NAME
    				   arr.push(obj)
    				   //delectSelect(selectOption,ids,true);
    			   });
    		    	
    		    	$.ajax({
    		    		url:"/dagl/xtpz/category/deleteAllZiDuan",
    		    		type : 'post',
    		    		dataType:"JSON",
    		    		async:false,
    		    		data:{
    		                jsonStr:JSON.stringify(arr)
    		            },
    		    		success:function(data){
    		    			if(data.flag==1){
    		    				$table.bootstrapTable('remove',
    				                    {
    				                        field:'COLUMN_NAME',
    				                        values:ids
    				                    }
    				                );
    		    					layer.close(indexs);
    				                layer.msg('删除成功！',{icon:1});
    		    			}else{
    		    				layer.msg('删除失败！请刷新页面重新尝试！',{icon:0});
    		    			}
    		    		},
    		    		beforeSend:function(xhr){
    		    		    
    		    		}
    		            })
    		    });
    	}
    },"json")

}

/**
 * 创建表格
 * @params tName  treeNode的id
 * @params pid   treeNode的pId
 * */
var TableInit1 = function (tName) {
    cateId = tName ;
    console.log('初始化关系表格！');
    console.log(tName)
    $("#tb_customer1").bootstrapTable('destroy');
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_customer1').bootstrapTable({
            url: '/dagl/xtpz/deptpersonrelation/getRelationData',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            contentType : "application/x-www-form-urlencoded",
            responseHandler:function(res){

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
            // data:tableData,
            //height:500,
            toolbar: '#toolbar1',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            classes: 'table',
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            //sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            queryParamsType: '',
            strictSearch: true,
            //showColumns: true,                  //是否显示所有的列
            //showRefresh: true,                  //是否显示刷新按钮
            //minimumCountColumns: 2,             //最少允许的列数
            //clickToSelect: true,                //是否启用点击选中行
            //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId: "COLUMN_NAME",                     //每一行的唯一标识，一般为主键列
            //showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            //cardView: false,                    //是否显示详细视图
            //detailView: false,                   //是否显示父子表onEditableSave
            queryParams: function (params) {
                //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                var temp = {
                    pageNumber: params.pageNumber,                         //页面大小
                    pageSize: params.pageSize,   //页码
                    //sortOrder:params.sortOrder,
                    cateId: tName,//门类
                    ljdwMark:$("#ljdwMarkOfSearch").val(),//获取的是mark值
                    lrrName:encodeURI($("#lrrNamesOfSearch").val()),
                    ljdwAdminName: encodeURI($("#ljdwAdminNamesOfSearch").val())

                };
                return temp;
            },//传递参数（*）
            columns: [{
                    title: '序号'
                    , width: '5%'
                    , order: "desc"
                    , align: "center"
                    , formatter: function (value, row, index) {
                        //计算序号，并存放开关ID，及已办事项ID
                        var pageSize=$('#tb_customer1').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                        var pageNumber=$('#tb_customer1').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                        var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
                        return "<span data-id='"+row.id+"' >"+orderNum+"</span>";    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
                    }
                },{
                    field: 'ljdwName',
                    title: '立卷单位',
                    width : '25%',
                    align : "center"//取后台数据
                }
                ,{
                    field: 'ljdwMark',
                    title: '立卷单位id',
                    align : "center",//取后台数据
                    visible: false
                },{
                    field: 'lrrName',
                    title: '录入人',
                    width : '25%',
                    align : "center"//取后台数据
                },{
                    field: 'ljdwAdminName',
                    title: '立卷单位管理员',
                    width : '25%',
                    align : "center"//取后台数据
                },{
                    field: 'operate',
                    title: '操作',
                    width : '10%',
                    align : "center",//取后台数据
                    formatter: function (value, row, index) {  //直接定义function,return就是html代码
                        var html = "";
                        html += "<i title='修改' class='glyphicon glyphicon-edit' style='cursor:pointer;' onclick='editFun(\""+row.ljdwMark+"\",\""+tName+"\")'></i>&nbsp;&nbsp;"
                        html += "<i title='删除' class='glyphicon glyphicon-trash' style='cursor:pointer;' onclick='deleteRelation(\""+row.ljdwMark+"\",\""+tName+"\")'></i>"

                        return html;
                    }
                }
            ],
            formatNoMatches: function(){
                return "没有相关的匹配结果";
            },
            formatLoadingMessage: function(){
                return "正在努力地加载数据中，请稍后......";
            },onLoadSuccess:function(){
                try {
                    mergeTableRow("ljdwName");
                }catch (e) {}

                $('tr.readOnly').find('td:not(:last)').unbind('click').bind('click',function(e){
//                    取消事件冒泡
                    var evt = e ? e : window.event;
                    if (evt.stopPropagation) {
                        evt.stopPropagation();
                    }else {
                        evt.cancelBubble = true;
                    }
//                    取消事件冒泡 end
//                     MyLayer.layerOpenUrl({url:'/modules/zhbg/zbgl/sbqkReadOnlyForm.html',title:'只读'});
                });

            }
        });

    };
    oTableInit.Init();
};

relationFormValidator();

// 初始化表单
function relationInitForm() {
    $("#tableForm1")[0].reset();
    $("#tableForm1").data('bootstrapValidator').destroy();
    $('#tableForm1').data('bootstrapValidator', null);
    $('#ljdwMark').attr('disabled',false);
    $("#ljdwMark").each(function (i, j) {
        $(j).find("option:selected").attr("selected", false);
    });
    relationFormValidator();
}

/**
 * 表单校验
 * */
function relationFormValidator(){
    $('#tableForm1').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            ljdwMark: {
                trigger:"change",
                message: '立卷单位验证失败',
                validators: {
                    notEmpty: {
                        message: '立卷单位不能为空'
                    }
                }
            },
            lrrNames: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '录入人不能为空'
                    }
                }
            },
            ljdwAdminNames: {
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '立卷单位管理员不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv',function(e,data){
        e.preventDefault();
        var formData = new Array();
        formData.push({name:'cateId',value:cateId});
        formData.push({name:'ljdwMark',value:$("#ljdwMark").val()});
        formData.push({name:'ljdwName',value:$("#ljdwMark").find("option:selected").text()});
        formData.push({name:'lrrIds',value:$("#lrrIds").val()});
        formData.push({name:'lrrNames',value:$("#lrrNames").val()});
        //录入人父部门id
        formData.push({name:'lrrDeptId',value:$("#lrrDeptId").val()});
        formData.push({name:'ljdwAdminNames',value:$("#ljdwAdminNames").val()});
        formData.push({name:'ljdwAdminIds',value:$("#ljdwAdminIds").val()});
        //立卷单位父部门id
        formData.push({name:'ljdwAdminDeptId',value:$("#ljdwAdminDeptId").val()});

        console.log('关系表单内容')
        console.log(formData)
        // console.log(e,data)
        if("add" == addOrEdit){
            $.ajax({
                url: "dagl/xtpz/deptpersonrelation/getRelationList"
                , type: "POST"
                , dataType: "json"
                ,data:{"cateId":cateId,"ljdwMark":$("#ljdwMark").val()}
                , success: function (result) {
                    if(result.length>0){
                        layer.msg("该立卷单位的关系已存在！",{icon:0,time:1000});
                        $('#ljdwMark').on('change',function(ev){
                            $('#submit1').attr('disabled',false);
                        });
                    }else{
                        //新建
                        addRelationRow(formData);  // 提交表单
                    }
                }
                , error: function () {
                    layer.msg('添加失败，请重试！', {icon: 2});
                }
            })
        }else if("edit" == addOrEdit){
            //修改
            addRelationRow(formData);
        }
    });
}

// 模态框隐藏时触发事件
$('#myModal1').on('hidden.bs.modal', function() {
    relationInitForm();
});

/**
 * 新增数据
 * @params formData 表单数据
 * */
// 新增
function addRelationRow(formData){
    /*formData.push({name:'cateId',value:cateId});
    formData.push({name:'ljdwName',value:$("#ljdwMark").find("option:selected").text()});*/

    var url = "";
    if("add" == addOrEdit){
        url = "dagl/xtpz/deptpersonrelation/saveRelation";
    }else if("edit" == addOrEdit){
        url = "dagl/xtpz/deptpersonrelation/updateRelation";
    }
    $.ajax({
        url: url
        , type: "GET"
        , dataType: "json"
        ,data:formData//JSON.stringify(formData),
        , success: function (result) {
            var msg = "";
            if (result.flag === 1) {
                msg = '添加成功！';
                if ($.trim(result.msg)) {
                    msg = result.msg;
                }
                //隐藏div框
                $('.addModal').hide();
                //$('.addModal').modal('hide');
                layer.closeAll();
                //重置div内容
                relationInitForm();
                layer.msg(msg, {icon: 1,time:1000});
                //刷新列表
                $('#tb_customer1').bootstrapTable('refresh');
            } else {
                msg = '添加失败，请重试！';
                if ($.trim(result.msg)) {
                    msg = result.msg;
                }
                layer.msg(msg, {icon: 2});
            }
        }
        , error: function () {
            layer.msg('添加失败，请重试！', {icon: 2});
        }
    })
}



/**
 * 创建表格
 * @params tName  treeNode的id
 * @params pid   treeNode的pId
 * */
var _TableInit = function (tName,pid) {
    console.log('初始化表格')
    // console.log(onClick())
    console.log(tName)
    $("#tb_customer").bootstrapTable('destroy');
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_customer').bootstrapTable({
            url: '/dagl/xtpz/category/findZiDuan',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            contentType : "application/x-www-form-urlencoded",
            queryParams: function(params){
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    tName:tName,
                    pid:pid
                };
                return temp;
            },//传递参数（*）
            responseHandler:function(res){
                return res;
            },
            // data:tableData,
            onLoadSuccess:function(){
                //组卷标识的是否表单项的点击事件去除掉，不能设置为表单项
                if($('#tb_customer tr[data-uniqueid=archive_flag]').find('a[data-name=COLUMN_VISIBLE]').length=1){
                    $('#tb_customer tr[data-uniqueid=archive_flag]').find('a[data-name=COLUMN_VISIBLE]').off('click');
                    $('#tb_customer tr[data-uniqueid=archive_flag]').find('a[data-name=COLUMN_VISIBLE]').on('click',function () {
                        layer.msg("组卷标识不可修改！",{icon:0,time:2000});
                    })
                }
                //档案实体状态的是否表单项的点击事件去除掉，不能设置为表单项
                if($('#tb_customer tr[data-uniqueid=archive_entity_status]').find('a[data-name=COLUMN_VISIBLE]').length=1){
                    $('#tb_customer tr[data-uniqueid=archive_entity_status]').find('a[data-name=COLUMN_VISIBLE]').off('click');
                    $('#tb_customer tr[data-uniqueid=archive_entity_status]').find('a[data-name=COLUMN_VISIBLE]').on('click',function () {
                        layer.msg("档案实体状态不可修改！",{icon:0,time:2000});
                    })
                }
                // 解决滚动条多出一点的问题 bugfree号32476
                var width = $('#tb_customer').outerWidth();
                var w = $('#tb_customer').parent().outerWidth();
                if((width - w) > 50 ) {
                    $('.fixed-table-body').css('overflow-x','auto')
                }else{
                    $('.fixed-table-body').css('overflow-x','hidden')
                }
            },
            height:581,
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // pagination: true,                   //是否显示分页（*）
            // sortable: false,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
            // sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            // pageNumber:1,                       //初始化加载第一页，默认第一页
            // pageSize: 10,                       //每页的记录行数（*）
            // pageList: [10],        //可供选择的每页的行数（*）
            // search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            // strictSearch: true,
            // showColumns: true,                  //是否显示所有的列
            // showRefresh: true,                  //是否显示刷新按钮
            // minimumCountColumns: 2,             //最少允许的列数
            // clickToSelect: true,                //是否启用点击选中行
            // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "COLUMN_NAME",                     //每一行的唯一标识，一般为主键列
            // showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            // cardView: false,                    //是否显示详细视图
            // detailView: false,                   //是否显示父子表onEditableSave
            columns: [{
                checkbox: true,
                formatter : function(value, row, index) {
                	if(row.COLUMN_CLASS!=4){
                		return {
                                    disabled: true,
                               }
                	}
                }
            }, {
                field: 'COLUMN_CHN_NAME',
                title: '中文名称',
                align:"center",
                editable:{
                    type:'text',              //编辑框的模式：支持popup和inline两种模式，默认是popup
                    validate: function (value) { //字段验证
                        if (!$.trim(value)) {
                            return '中文名称不能为空';
                        }
                    }
                }
            }, {
                field: 'COLUMN_NAME',
                title: '英文名称',
                align:"center"
            },
                {
                    field: 'COLUMN_TYPE',
                    title: '字段类型',
                    align:"center",
                    formatter:function(value,row,index){
                        if(value==2){
                            return '整数'
                        }else{
                            return '字符串'
                        }
                    }
                },
                {
                    field: 'COLUMN_WIDTH',
                    title: '字段长度',
                    align:"center"
                }
                ,{
                    field: 'COLUMN_INPUT_TYPE',
                    title: '著录形式',
                    align:"center",
                    formatter:function(val,row){
                        if(val=='T'){
                            return '文本输入'
                        }else{
                            return '选择输入'
                        }
                    }
                }
                ,{
                    field: 'COLUMN_SELECT_NO',
                    title: '对应字典',
                    align:"center"
                },
                {
                    field: 'COLUMN_VISIBLE',
                    title: '是否表单项',
                    align:"center",
                    editable: {
                        type: 'select',
                        title: '状态',
                        source:[{value:"T",text:"是"},{value:"F",text:"否"}]
                    },
                },
                {
                    field: 'COLUMN_CAN_NULL',
                    title: '是否为空',
                    align:"center",
                    editable: {
                        type: 'select',
                        title: '状态',
                        source:[{value:"T",text:"是"},{value:"F",text:"否"}]
                    },
                },
                {
                    field: 'COLUMN_AS_SIMQUERY',
                    title: '是否简单查询',
                    align:"center",
                    editable: {
                        type: 'select',
                        title: '状态',
                        source:[{value:"T",text:"是"},{value:"F",text:"否"}]
                    },
                },
                {
                    field: 'COLUMN_LIST_ISSHOW',
                    title: '是否列表项',
                    align:"center",
                    editable: {
                        type: 'select',
                        title: '状态',
                        source:[{value:"T",text:"是"},{value:"F",text:"否"}]
                    },
                }, {
                    field: 'COLUMN_LIST_ORDER',
                    title: '列表顺序',
                    align:"center",
                    editable:{
                        type:'text',
                        placeholder:"请填写正整数",
                           validate: function (v,e) {
                        	   v=(v==null||v=="")?" ":v;
                                if (/^[1-9][0-9]{0,}$|^\s*$/.test(v)){} else{ $(".editable-error-block.help-block").css("width","200px"); return '只能填写正整数且不能以0开头';}
                                //列表顺序不可重复
                               var rows = $("#tb_customer").bootstrapTable("getData");
                                for(var i=0;i<rows.length;i++){
                                    var colOrder = rows[i].COLUMN_LIST_ORDER;
                                    if(v == colOrder){
                                        return "列表顺序不可重复";
                                    }
                                }
                            }
                    }
                },{
                    field: 'LIST_COLUMN_WIDTH',
                    title: '列表宽度(px)',
                    align:"center",
                    editable:{
                        type:'text',
                        placeholder:"请填写正整数",
                        validate: function (v,e) {
                            var minLength = $(this.childNodes[0]).parents('tr').children().eq(1).find('a').attr('data-value').length;
                            v=(v==null||v=="")?" ":v;
                            if (/^[1-9][0-9]{0,}$|^\s*$/.test(v)){

                            }else{
                                $(".editable-error-block.help-block").css("width","200px"); return '只能填写正整数且不能以0开头';
                            }
                            minLength = minLength*14+12;
                            if(parseInt(v) < minLength){
                                $(".editable-error-block.help-block").css("width","200px");
                                return '宽度不得小于'+minLength+"px！";
                            }
                            if(parseInt(v) > 500){
                                $(".editable-error-block.help-block").css("width","200px");
                                return '宽度不得大于500px！';
                            }
                        }
                    }
                },{
                    field: 'COLUMN_INHERIT',
                    title: '值是否继承',
                    align:"center",
                    editable: {
                        type: 'select',
                        title: '状态',
                        source:[{value:"T",text:"是"},{value:"F",text:"否"}]
                    }
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter:function(value,row,index){
                    	//alert(JSON.stringify(row))
                    	if(row.COLUMN_CLASS==4){
                    		 return [
                    		        '<i  id="TableDelete" class="glyphicon glyphicon-trash p-l-10" style="cursor:pointer"></i>'
                    		    ].join("");
                    	}
                    }
                }
            ],
            formatNoMatches: function(){
                return "没有相关的匹配结果";
              },
              formatLoadingMessage: function(){
                return "正在努力地加载数据中，请稍后......";
              },
            onEditableSave: function (field, row, oldValue, index) {
                console.log(field,row,oldValue,index)
                function editerror (){  // 修改失败后的提示
                    layer.msg('修改失败',{icon:2});
                    _TableInit(treeNodeId,treeNodePid);
                    return false;
                }
                $.ajax({
                    type: "post",
                    url: "/dagl/xtpz/category/updateZiDuan",
                    data: {
                        tName: row.TABLE_NAME,//表名
                        updateColumnName :row[field],//新值
                        columnName:row.COLUMN_NAME,//table_struct_description 的COLUMN_NAME字段对应的值
                        column:field,//需要修改的字段名
                        code:findTreeParent($.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0]).id //门类

                    },
                    success: function (data, status) {
                        console.log(status)
                        if (status == 'success') {
                            if(data.flag==1){
                            	//判断当前是不是把列表项调整为否，如果是，那么调整列表顺序为Empty 王磊 20190411
                            	if("COLUMN_LIST_ISSHOW"==field && "F"==row[field]){
                                    _TableInit(treeNodeId,treeNodePid);
                            		//$("tr[data-uniqueid="+row.COLUMN_NAME+"]").find("a[data-name='COLUMN_LIST_ORDER']").html("Empty");
                            	}
                                layer.msg('修改成功！',{icon:1});
                            }else{
                                editerror();
                            }
                        }else{
                            editerror();
                        }
                    },
                    error: function () {
                        editerror();
                    },
                    complete: function () {

                    }

                });
            }
        });

    };
    oTableInit.Init();
    $('#tb_customer thead tr th:first-child').children('.th-inner').html('选择');
    $('#tb_customer thead tr th:first-child').children('.th-inner').css('padding','8px');
    
};

formValidator();

// 初始化表单
function InitForm() {
    $("#tableForm")[0].reset();
    $("#tableForm").data('bootstrapValidator').destroy();
    $('#tableForm').data('bootstrapValidator', null);
    formValidator();
}
/**
 * 表单校验
 * */
function formValidator(){
    $('#tableForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            COLUMN_CHN_NAME: {
                message: '中文名称验证失败',
                validators: {
                    notEmpty: {
                        message: '中文名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '中文名称长度必须在1到50位之间'
                    }
                }
            },
            COLUMN_NAME: {
                validators: {
                    notEmpty: {
                        message: '英文名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '英文名称长度必须在1到100位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z][a-zA-Z0-9_]+$/,
                        message: '英文名称只能包含大小写字母、数字和下划线且必须字母开头，最少输入两位'
                    }
                }
            },
            COLUMN_WIDTH: {
                validators: {
                    notEmpty: {
                        message: '请输入字段长度'
                    },
                    greaterThan: {
                        value:1,
                        message: '最小输入1'
                    },
                    lessThan: {
                        value:2000,
                        message: '最大输入2000'
                    }
                }
            },
            COLUMN_VISIBLE: {
                validators: {
                    notEmpty: {
                        message: '请选择是否显示'
                    }
                }
            },
            COLUMN_CAN_NULL: {
                validators: {
                    notEmpty: {
                        message: '请选择是否必填'
                    }
                }
            },
            COLUMN_AS_SIMQUERY: {
                validators: {
                    notEmpty: {
                        message: '请选择是否简单查询'
                    }
                }
            },
            COLUMN_LIST_ISSHOW: {
            	validators: {
            		notEmpty: {
            			message: '请选择是否列表项'
            		}
            	}
            },
        }
    }).on('success.form.bv',function(e,data){
        e.preventDefault();
        var formData = $('#tableForm').serializeArray();
        console.log('表单内容')
        console.log(formData)
        // console.log(e,data)
        addRow(formData)  // 提交表单
    });
    $('#slect_input_type').on('change',function(ev){
        if (ev.target.value=='S') {
            $('#select_no').attr('disabled',false)
            Dictionary.init({
                mark:"dagl",
                type:'0',
                position:"select_no",
                domType:"select",
                hasSelect:true
            });
            $("[name='type']").remove();
            $("#select_no").find("option").eq(0).remove();
        }else{
        	 $('#select_no').empty();
        	 $('#select_no').append("<option value='0'>请选择</option>");
            $('#select_no').attr('disabled',true)
        }
    })
    $('#column_type').on('change',function(ev){
    	$('#column_width').val("")
        if (ev.target.value==1) {
            $('#column_width').attr('disabled',false)
            $("#tableForm").bootstrapValidator("addField","COLUMN_WIDTH",{
            	 validators: {
                     notEmpty: {
                         message: '请输入字段长度'
                     },
                     greaterThan: {
                         value:1,
                         message: '最小输入1'
                     },
                     lessThan: {
                         value:2000,
                         message: '最大输入2000'
                     }
                 }
            });
            $('#tableForm').bootstrapValidator('validateField', 'COLUMN_WIDTH');
        }else{
        	//$('#tableForm').data('bootstrapValidator').resetForm(true);
        	//$('#tableForm').bootstrapValidator('updateStatus', 'COLUMN_WIDTH', 'NOT_VALIDATED');
        	$('#tableForm').bootstrapValidator('removeField', 'COLUMN_WIDTH');
        	$("#tableForm").bootstrapValidator("addField","COLUMN_WIDTH",{
        		validators:{}
        	});
        	$('#tableForm').bootstrapValidator('validateField', 'COLUMN_WIDTH');
        	$('#column_width').attr('disabled',true)
        	//$("#tableForm").bootstrapValidator('removeField','COLUMN_WIDTH');
//        	$('#tableForm').bootstrapValidator('updateStatus', 'COLUMN_NAME', 'NOT_VALIDATED')
//            .bootstrapValidator('validateField', 'COLUMN_NAME');
        }
    })
}

// 模态框隐藏时触发事件
$('#myModal').on('hidden.bs.modal', function() {
	 $('#column_width').attr('disabled',false)
	// $('#column_width').val("");
    InitForm();
});

/**
 * 新增数据
 * @params formData 表单数据
 * */
// 新增
function addRow(formData){
    var $table =  $('#tb_customer');
    var allData =  $table.bootstrapTable('getData');
    var rows = []; // 要新增的数据
    var obj = {}; //要添加的对象
    $.each(formData,function (index,formControl) {
    	if(formControl.name=="COLUMN_SELECT_NO"){
    		//console.log($("#select_no option:checked"))
    		obj[formControl.name] = $("#select_no option:checked").attr("data-mark");
    	}
    	else{
    	obj[formControl.name] = formControl.value;
    	}
    })
    rows.push(obj)
    var isTrue = true;
    // 判断新增的是否已存在
    allData.forEach(function(data){
        rows.forEach(function(newData){
            if(data.COLUMN_NAME == newData.COLUMN_NAME){  // 如果英文名称相等说明已存在
                console.log('新增的内容已存在,请重新添加！');
                layer.msg('当前新增的英文名称已存在，请重新填写！', {icon: 0})
                rows = []
                isTrue = false;
                return ; // 停止执行
            }
        })
    })
    if(isTrue){
    	 // ajax提交方法
        $.ajax({
            url:'/dagl/xtpz/category/addZiDuan',
            method:'post',
            data:{
                tName:treeNodeId,
                chnName:treeNodeCnName,
                StrJson:JSON.stringify(obj)
            },
            success:function(data){
                console.log(data)
                if (data.flag==1){
                    // 前端显示
                    $table.bootstrapTable('prepend', rows);
                    // 提交成功 表单重置
                    InitForm();
                    //$('#myModal').modal('hide');
                    $('.modal-body').hide();
                    layer.closeAll();
                    layer.msg('添加成功！', {icon: 1});
                   $('#tb_customer').bootstrapTable('refresh')
                }else{
                	layer.msg('添加失败！', {icon: 0});
                }
            },
            error:function(){
                layer.msg('添加失败，请重新添加！', {icon: 2});
            }
        })
    }
   
}

/**
 * @Author 王富康
 * @Description //TODO 根据门类及立卷单位删除对应关系
 * @Date 2019/2/13 9:59
 **/
function deleteRelation(ljdwMark,tName){

    layer.confirm("确定要删除该授权信息么？",function(){
        $.ajax({
            url: "dagl/xtpz/deptpersonrelation/deleteRelation"
            , type: "GET"
            , dataType: "json"
            ,data:{cateId:tName,ljdwMark:ljdwMark}
            , success: function (result) {
                var msg = "";
                if (result.flag === 1) {
                    msg = '删除成功！';
                    if ($.trim(result.msg)) {
                        msg = result.msg;
                    }
                    layer.msg(msg, {icon: 1,time:1000});
                    $('#tb_customer1').bootstrapTable('refresh');
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

/**
 * @Author 王富康
 * @Description //TODO 编辑某个立卷单位的信息
 * @Date 2019/2/14 9:31
 * @Param
 **/
function editFun(ljdwMark,tName){

    addOrEdit = "edit";
    //首先根据门类和立卷单位查询数据

    $.ajax({
        url: "dagl/xtpz/deptpersonrelation/getRelationList"
        , type: "POST"
        , dataType: "json"
        ,data:{"cateId":tName,"ljdwMark":ljdwMark}
        , success: function (result) {
            //内容相同的取第一个
            var ljdwMark = result[0].ljdwMark;
            var ljdwName = result[0].ljdwName;
            var ljdwAdminNames = result[0].ljdwAdminName;
            var ljdwAdminIds = result[0].ljdwAdminId;
            var ljdwAdminDeptId = result[0].ljdwAdminDeptId;
            //不同的，进行拼接
            var lrrNames ="";
            var lrrIds ="";
            var lrrDeptId = "";
            for(var i=0;i<result.length;i++){
                lrrNames += result[i].lrrName+",";
                lrrIds += result[i].lrrId+",";
                lrrDeptId += result[i].lrrDeptId+",";
            }
            if("" != lrrNames){
                lrrNames = lrrNames.substring(0,lrrNames.length-1);
                lrrIds = lrrIds.substring(0,lrrIds.length-1);
                lrrDeptId = lrrDeptId.substring(0,lrrDeptId.length-1);
            }
            //显示div框
            layer.open({
                type:1,//类型
                skin: 'layui-layer-demo', //样式类名
                title:['修 改', 'font-size:16px;font-weight:bold;'],//题目
                closeBtn: 1, //不显示关闭按钮
                anim: 2,
                shadeClose:true,//点击遮罩层关闭
                content: $('.addModal'), //打开的内容
                area: ['40%', '40%'],
                end:function () {
                    relationInitForm();
                    $('.addModal').hide();
                }
            });
            // $('#myModal1').modal('show');
            // $("#myModalLabel1").text("修改");
            //回填数据
            /*$("#ljdwMark").each(function (i, j) {
                $(j).find("option:selected").attr("selected", false);
            });*/
            $("#ljdwMark").val(ljdwMark);
            //$("#ljdwMark option[value='"+ljdwMark+"']").attr("selected",true);
            $('#ljdwMark').attr('disabled',true);
            $("#lrrNames").val(lrrNames);
            $("#lrrIds").val(lrrIds);
            $("#ljdwAdminNames").val(ljdwAdminNames);
            $("#ljdwAdminIds").val(ljdwAdminIds);
            $("#lrrDeptId").val(lrrDeptId);
            $("#ljdwAdminDeptId").val(ljdwAdminDeptId);

        }
        , error: function () {
            layer.msg('添加失败，请重试！', {icon: 2});
        }
    })

}

function openAndClose(){
    var div2=document.getElementById("SearchArea");

    var style2=window.getComputedStyle(div2);
    div2.style.display=style2.display=="none"?"block":"none";

    $('#tubiao')[0].className=div2.style.display=="none"?"glyphicon glyphicon-plus showSelect":"glyphicon showSelect glyphicon-minus";
    //glyphicon showSelect glyphicon-minus
}