var _id,_name,_mark,_index,_parentId,_parentName,_treeId;
/**
 * 打开群组人员树,数据加载方式：异步
 * json:
 * @param resId 存放资源ID的隐藏域ID,默认为resId
 * @param id 存放ID的隐藏域ID
 * @param name 存放name的控件ID
 * @param asyn 是否是异步方式加载（false同步；true异步）
 * @param check 选中时的级联操作，默认s,级联子节点；可选值：p/s/ps,或者不传。
 * @param cancle 取消选中时的级联操作，默认s,级联取消子节点；可选值：p/s/ps,或者不传。
 * @param url 查询方法的映射路径
 * @Param subId 系统id 如:97206,85585
 * @return {[type]} [description]
 */
function openGroupSelectZtree(json){
	var resId = json.resId || "resId";
    var id = json.id || "";
    var name =  json.name || "";
    //var asyn = json.asyn || false;
    var check = setVlaue(json.check);
    var cancle = setVlaue(json.cancle);
    var url = json.url;
    var subId = json.subId || "";
    _id = id,_name = name;
    var contentUrl,title;
    title="选择群组";
    contentUrl = "/modules/system/component/groupTree/asynGroupUserTree.html";
    var param = "?url="+url+"&resId="+$("#"+resId).val()+"&subId="+subId;
    layer.open({
        id:"selectZtree",
        type: 2,
        content: contentUrl + param,
        area: ['770px', '450px'],
        title: [title, 'font-size:16px;font-weight:bold;'],
        success:function(layero, index){
        	_index = index;
           // 通过iframe 操作
           var iframeId = $("#selectZtree").find('iframe').attr('id')
           var obj = $(window.frames[""+iframeId+""].document)

           $(obj).find('#id').val(id)
           $(obj).find('#name').val(name)
           $(obj).find('#idContext').val($('#'+id).val())
           $(obj).find('#nameContext').val($('#'+name).val())

           // 调用数据回显方法
           document.getElementById(iframeId).contentWindow.CommonUtil.eachSelectData();
           // 初始化树
           document.getElementById(iframeId).contentWindow.groupZtree.init();
           // if(type == '1' || type == 1){
        	//    document.getElementById(iframeId).contentWindow.groupZtree.init();
           // }
           // 初始化tab页
           document.getElementById(iframeId).contentWindow.CommonUtil.bindFun()
           
        }
    })
}

/**
 * 空值设置
 * @param val
 * @returns
 */
function setVlaue(val){
	console.log(val);
	if(val == undefined){
		val = "s";
	}else if(val == ""){
		val = "";
	}
	console.log(val);
	return val;
}

/**
 * 数据回显到页面
 */
function putBackData(){
	// 通过iframe 操作
    var iframeId = $("#selectZtree").find('iframe').attr('id')
    var res = document.getElementById(iframeId).contentWindow.CommonUtil.saveSelectedData();
    if(_mark != ""){
		$('#'+_mark).empty().append(res.mark);
    }
    $('#'+_id).val(res.ids).change();	//人员（部门）ID
    $('#'+_name).val(res.names).change();	//人员（部门）名
    if(_treeId){
    	$('#'+_treeId).val(res.treeids).change();	//部门treeId
    }
    if(_parentId){
    	$('#'+_parentId).val(res.parentIds).change();	//人员所在部门ID（所选部门父部门ID）
    }
    if(_parentName){
    	$('#'+_parentName).val(res.parentNames).change();	//人员所在部门名（所选部门父部门name）
    }
    //关闭窗口
    layer.close(_index);  
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