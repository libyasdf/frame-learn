 
$(function(){
    
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.oper);
 
    $("#superId").val(theRequest.superId ? theRequest.superId : "");
    //$("#nodeLevel").val(++theRequest.nodeLevel);
    $("#isFirst").val(theRequest.isFirst);
    $("#nodeLevel").val(++theRequest.nodeLevel);
    
  
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/mypage/wmgl/basicSet/category/edit",datas,function (json){
            DisplayData.playData({data:json});
          
        });
    }
})
var resId = $("#resId").val();


function deleteFn(ids) {
    var flag = false;//如果删除成功改为true
    // 获取资源元素
    var resId = $("#resId").val();
    // 删除操作
    $.ajax({
        url: '/video/background/column/deleteItme?ids=' + ids + "&resId=" + resId
        , type: "GET"
        , dataType: "json"
        , async: false
        , success: function (result) {
            var msg = "";
            if (result.flag === "1") {
                msg = '删除成功！';
                flag = true;
                parent.TableInit.refTable('right_table');
                if ($.trim(result.msg)) {
                    msg = result.msg;
                }
                layer.msg(msg, {icon: 1});

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
    });
}



//保存 status:设置保存范围后的状态值，isTs:控制是否弹出保存成功提示
function saveDictionary() {

   var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    
    if(bootstrapValidator.isValid() ){
       
        Loading.open();
       
        $.ajax({
            type: "POST",
            url:"/mypage/wmgl/basicSet/category/save",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                Loading.clear();
                if ('1' == json.flag) {
                            if($("#opertation").val() == "NEW"){//新增页面
                                parent.typeTree.addNode({
                                    id:json.data.id,
                                    pId:json.data.superId,
                                    name:json.data.typeName,
                                    nodeLevel:json.data.nodeLevel,
                                    orderNo:json.data.orderNo
                                });// 在ztree中增加新保存的节点
                                $("#opertation").val("EDIT");
                            }else{
                            	console.info(json.data)
                                parent.typeTree.updateNode(json.data);
                            }
                            window.parent.refresh();
                       
                    $("#id").val(json.data.id);
                    parent.TableInit.refTable('right_table');
                    layer.msg("保存成功！", {icon: 1,time:1000}, function (index){
						//关闭当前窗口
                        closeIfram();
                    });
                }
            },
            error:function(data){
                layer.msg("保存失败!",{icon:2})
            }
        });
    }
}
//关闭当前窗口
function closeIfram(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
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


