$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#dwxtOrgId").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);

	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#dwxtOrgId").val() != ""){
        $('#right_table').bootstrapTable({
            url: "/djgl/ddjs/dzz/dzzgl/unitList",
            sidePagination:"client",
            columns: right_table_col,
            pagination:true,
            striped:true,
            classes: 'table table-hover',
            rowStyle: function (row, index) {	//默认样式：居中，鼠标为手
                return {
                    classes: 'readOnly'
                    ,css: {"text-align":"center","cursor":"pointer"}
                };
            },
            queryParams: function (params) {
                //这里将各个查询项添加到要传递的参数中
                //可以做一些校验
                params.id = $("#dwxtOrgId").val();
                return params;
            },
            onClickRow:function (row,$element,field) {
                $('tr.readOnly').find('td:not(:last)').attr("title","点击查看详情");//悬停显示
                if(field != "cz"){
                    var id = row.id;
                    var resId = $('#resId').val();
                    var theRequest = GetRequest();
                    var orgType = theRequest.orgType;
                    var dwxtOrgId = $("#dwxtOrgId").val();
                    //流程审批列表需要workitemid已办事项ID
                    MyLayer.layerOpenUrl({url: '/modules/dwgl/dzz/dzzgl/dwglReadOnlyForm.html?id='+id+"&oper=VIEW&resId=" + resId+'&dwxtOrgId='+dwxtOrgId+'&orgType='+orgType, title: "只读"});
                }
            },
            onLoadSuccess:function () {
                $('tr.readOnly').find('td:not(:last)').attr("title","点击查看详情");//悬停显示
            }
        })
        var datas = {"id":$("#dwxtOrgId").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/dzzgl/findOne",datas,function (data){
            DisplayData.playData({data:data});
            $("#orgType").text(Dictionary.getNameAndCode({mark:"dwgl_orgType",type:"1"})[data.data.orgType]);
        });
	}

})


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