/**
 * 打开人员选择
 * @return {[type]} [description]
 */
function openSelectZtree(json){
    var id = json.id || "";
    var name =  json.name || "";
    var mark = json.mark || "";
    layer.open({
        id:"selectZtree",
        type: 2,
        content: "/modules/template/selectUser.html",
        area: ['770px', '551px'],
        title: ['选择人员', 'font-size:16px;font-weight:bold;'],
        success:function(json){
           // 通过iframe 操作
           var iframeId = $("#selectZtree").find('iframe').attr('id')
           var obj = $(window.frames[""+iframeId+""].document)

           $(obj).find('#id').val(id)
           $(obj).find('#name').val(name)
           $(obj).find('#mark').val(mark)
           $(obj).find('#idContext').val($('#'+id).val())
           $(obj).find('#nameContext').val($('#'+name).val())

           // 调用数据回显方法
           document.getElementById(iframeId).contentWindow.CommonUtil.eachSelectData();
           // 初始化树
           document.getElementById(iframeId).contentWindow.deptZtree.init();
           document.getElementById(iframeId).contentWindow.groupZtree.init();
           // 初始化tab页
           document.getElementById(iframeId).contentWindow.CommonUtil.bindFun()
           
        }
    })
}

/**
 * 删除节点
 * selectUser.html 的回调函数，必须的
 */

function deleteThisNode(obj){
    var id = $(obj).attr('forId');
    var name = $(obj).attr('forName');
    var ids = $(obj).parents('ol').parent('div').siblings('#'+id).val();
    var names = $(obj).parents('ol').parent('div').siblings('#'+name).val();
    var _id= $(obj).parent('li').attr('id');
    var _name = $(obj).parent('li').text();
    var _ids = ids.split(",")
    var _names = names.split(",");
    var idArray =[], nameArray = [];
    for(var i=0;i<_ids.length;i++){
        if(_ids[i] != _id){
            idArray.push(_ids[i])
        }
    }

    for(var j=0;j<_names.length;j++){
        if(_names[j] != _name){
            nameArray.push(_names[j])
        }
    }
    $(obj).parents('ol').parent('div').siblings('#'+id).val(idArray.join(','));
    $(obj).parents('ol').parent('div').siblings('#'+name).val(nameArray.join(','));
    $(obj).parents('li').remove();

}