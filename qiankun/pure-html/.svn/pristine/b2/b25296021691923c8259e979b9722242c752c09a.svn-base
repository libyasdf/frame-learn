$(function(){

    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#partyBranchId").val(theRequest.partyBranchId);
    $("#opertation").val(theRequest.oper);
    $("#orgType").val('666');

    $.ajax({
        type: "get",
        url:"/djgl/ddjs/dzz/dzzgl/findOne",
        data:{id:theRequest.partyBranchId,
            resId:theRequest.resId
        },
        dataType:"json",
        success:function(data){
            $("#parentName").text(data.data.orgName);
            $("#superId").val(data.data.id);

        },
        error:function(data){

        }
    });

    function joinBorderHtml(ids,names,id,name){
        var html = [];
        html.push('<ol>');
        for(var i=0; i<ids.length; i++){
            html.push('<li class="item" id="'+ids[i]+'" > ');
            html.push(names[i]+'<span forId="'+id+'" forName="'+name+'" onclick="deleteThisNode(this)" class="glyphicon glyphicon-remove"></span>');
            html.push('</li>');
        }
        html.push('</ol>');
        return html.join('');
    }

    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/dzzgl/findOneAndUser",datas,function (data){
            DisplayData.playData({data:data});
            if ($("#opertation").val() != "VIEW") { // 只读页渲染
                $("#selectIds").val(data.selectIds);
                $("#selectNames").val(data.selectNames);
                if (data.selectIds) {
                    var html = joinBorderHtml(data.selectIds.split(","), data.selectNames.split(","), "selectIds", "selectNames");
                    $('#selectUser2').empty().append(html);
                }
            }else{
                $("#selectNames").text(data.selectNames);
            }
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
            url:"/djgl/ddjs/dzz/dzzgl/saveDxz",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(data){
                if ('1' == data.flag) {
                    var selectIds = $("#selectIds").val();
                    var count = selectIds.split(",").length;
                    if($("#id").val() == ""){
                        $("#id").val(data.data.id);
                        $("#orgId").val(data.data.orgId);
                        var name = data.data.orgName;
                        if($('#ckb',window.parent.document).is(':checked')){
                            parent.dzzTree.addNode({id:data.data.id,pId:data.data.superId,name:name+"("+count+")",sname:name,count:count});// 在ztree中增加新保存的节点
                        }else{
                            parent.dzzTree.addNode({id:data.data.id,pId:data.data.superId,name:name,count:count});// 在ztree中增加新保存的节点
                        }
                        parent.refreshPage();
                    }else{
                        parent.dzzTree.updateNode(data,count);
                    }
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