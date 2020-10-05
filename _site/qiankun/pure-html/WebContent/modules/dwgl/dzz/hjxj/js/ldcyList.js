$(function(){
	var theRequest = GetRequest();
    $("#hjxjId").val(theRequest.id);
	$("#resId").val(theRequest.resId);
	$("#dwxtOrgId").val(regVlaue(theRequest.dwxtOrgId));
    $("#orgType").val(regVlaue(theRequest.orgType));
	$("#opertation").val(theRequest.oper);
    //初始化表格
    TableInit.init({
        domId: "right_table",
        url: "/djgl/ddjs/dzz/hjxj/ldcyList",
        columns: right_table_col,
        queryParams: function (params) {
            //这里将各个查询项添加到要传递的参数中
            //可以做一些校验
            params.resId = $("#resId").val();
            params.hjxjId = $("#hjxjId").val();
            params.isTenure = $("#isTenure").val();
            return params;
        },
        readOnlyFn: function () {
            $('tr.readOnly').find('td:not(:last)').attr("title","点击查看详情");//悬停显示
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

    $.ajax({
        type: "get",
        url:"/djgl/ddjs/dzz/dzzgl/findOne",
        data:{id:theRequest.dwxtOrgId,
            resId:theRequest.resId
        },
        dataType:"json",
        success:function(data){
            $("#orgId").text(data.data.orgId);
            $("#orgName").text(data.data.orgName);
        },
        error:function(data){

        }
    });


    if($("#hjxjId").val() != ""){
        var datas = {"id":$("#hjxjId").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/hjxj/findOne",datas,function (data){
            data.data.modality = Dictionary.getNameAndCode({mark:"dwgl_modality",type:"1"})[data.data.modality];
            DisplayData.playData({data: data});
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