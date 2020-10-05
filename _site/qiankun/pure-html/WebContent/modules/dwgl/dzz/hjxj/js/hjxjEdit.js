$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
    $("#dwxtOrgId").val(theRequest.dwxtOrgId);

    Dictionary.init({position:"modality",mark:"dwgl_modality",type:"1",name:"modality",domType:"select"});


    $.ajax({
        type: "get",
        url:"/djgl/ddjs/dzz/dzzgl/findOne",
        data:{id:theRequest.dwxtOrgId,
            resId:theRequest.resId
        },
        dataType:"json",
        success:function(data){
            if($("#opertation").val() == "VIEW"){
                $("#orgId").text(data.data.orgId);
                $("#orgName").text(data.data.orgName);
                var right_table_col = [{
                    title: '序号'
                    , width: '5%'
                    , order: "desc"
                    , align: "center"
                    , formatter: function (value, row, index) {
                        var pageSize=$('#right_table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                        var pageNumber=$('#right_table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                        var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
                        return "<span recordId='"+row.id+"'>"+orderNum+"</span>";
                    }
                }, {
                    field: 'leaderName'
                    , title: '姓名'
                    , width: '12%'
                    , align: "center"
                }, {
                    field: 'duties'
                    , title: '党内职务名称'
                    , width: '12%'
                    , align: "center"
                    , formatter: function (value, row, index) {
                        return dutiesMap[value];
                    }
                }, {
                    field: 'tenureDate'
                    , title: '任职日期'
                    , width: '12%'
                    , align: "center"
                }, {
                    field: 'tenureMode'
                    , title: '任职方式'
                    , width: '12%'
                    , align: "center"
                }, {
                    field: 'tenureFileCode'
                    , title: '任职文件文号'
                    , width: '15%'
                    , align: "center"
                },{
                    field: 'isTenure'
                    , title: '状态'
                    , width: '10%'
                    , align: "center"
                    , formatter: function (value, row, index) {
                        var html = "";
                        if(value){
                            if(value == "0"){
                                html = '在职';
                            }else{
                                html = '离任';
                            }
                            return html;
                        }
                    }
                }];

                var dutiesMap = Dictionary.getNameAndCode({mark:"dwgl_duties",type:"1"});

                //初始化表格
                TableInit.init({
                    domId: "right_table",
                    url: "/djgl/ddjs/dzz/hjxj/ldcyList",
                    columns: right_table_col,
                    queryParams: function (params) {
                        //这里将各个查询项添加到要传递的参数中
                        //可以做一些校验
                        params.resId = $("#resId").val();
                        params.hjxjId = $("#id").val();
                        return params;
                    },
                    readOnlyFn: function () {
                        $('tr.readOnly').find('td:not(:last)').unbind('click').bind('click', function (e) {
                            //取消事件冒泡
                            var evt = e ? e : window.event;
                            if (evt.stopPropagation) {
                                evt.stopPropagation();
                            } else {
                                evt.cancelBubble = true;
                            }
                            //取消事件冒泡 end
                            var id = $(this).parent().find("span[recordId]").attr("recordId");
                            var resId = $('#left_ul').find('a.active').attr("id");
                            //流程审批列表需要workitemid已办事项ID
                            MyLayer.layerOpenUrl({url: '/modules/dwgl/dzz/hjxj/ldcyReadOnlyForm.html?id='+id+"&oper=VIEW&resId=" + resId, title: "只读"});
                        });
                    }
                })
            }else{
                $("#orgId").val(data.data.orgId);
                $("#orgName").val(data.data.orgName);
            }
        },
        error:function(data){

        }
    });

    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/hjxj/findOne",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                data.data.modality = Dictionary.getNameAndCode({mark:"dwgl_modality",type:"1"})[data.data.modality];
            }
            DisplayData.playData({data:data});

        });

    }


})

function save() {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url:"/djgl/ddjs/dzz/hjxj/save",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(data){
                if ('1' == data.flag) {
                    if($("#id").val() == ""){
                        $("#id").val(data.data.id);
                    }
                    parent.refreshPage();
                    layer.msg("保存成功！", {icon: 1,time:3000}, function (index) {
                    });
                }
            },
            error:function(data){

            }
        });
    }

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

//@sourceURL=AAA.JS