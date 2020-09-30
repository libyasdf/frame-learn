/**
 * 所有部门人员选择框添加keydown事件，阻止手动写入数据
 *     注：如果有特殊需要，需要手写的input框上请添加canWrite属性（如果input上没有onclick事件可不加canWrite属性）
 *      例如：<input type="text" id="userName" canWrite onclick=""/>
 *      <input type="text" id="userName"/>
 */
$(function(){
    $(".glyphicon-user").parent(".input-group-addon").siblings("input[onclick]:not([canWrite])").on("keydown",function(){
        this.blur();
        return false;
    })
})

var _id,_name,_mark,_index,_parentId,_parentName,_treeId,_callbak;
/**
 * 打开人员选择,数据加载方式：异步
 * json:
 * @param resId 存放资源ID的隐藏域ID,默认为resId
 * @param id 存放ID的隐藏域ID
 * @param name 存放name的控件ID
 * @param parentId 存放用户父部门ID的隐藏域ID
 * @param parentName 存放用户父部门name的控件ID
 * @param type 1人员 2部门
 * @param isMulti "1"单选  "0"多选 ,默认0
 * @param mark 多选时，采用DIV回显时，显示DIV的ID
 * @param level 级别
 * @param deptId 部门ID
 * @param asyn 是否是异步方式加载（false同步；true异步）
 * @param check 选中时的级联操作，默认s,级联子节点；可选值：p/s/ps,或者不传。
 * @param cancle 取消选中时的级联操作，默认s,级联取消子节点；可选值：p/s/ps,或者不传。
 * @param select 是否是查询条件使用的选择框，（fasle:不是 ；true:是）
 * @param url 查询方法的映射路径
 * @param positionName 职务，1,2,3,4...
 * @param height 弹出框高度
 * @param zw 职务权限(根据职务权限获取人员树用到)
 * @param zj 职级(根据职级获取人员树用到)
 * @param zwjb 职务级别(根据职务级别获取人员树用到)
 * @param rolesNo 业务角色编号(业务角色树用到)
 * @Param deptNames 查询部门名称（）
 * @param callback 自定义回调方法，用于自定义回显数据到表单页面
 * @return {[type]} [description]
 */
function openSelectZtree(json){
	var resId = json.resId || "resId";
    var id = json.id || "";
    var name =  json.name || "";
    var parentId = json.parentId || "";
    var parentName = json.parentName || "";
    var treeId = json.treeId || "";
    var type = json.type || "";
    var isMulti = json.isMulti || "0";
    var mark = json.mark || "";
    var deptId = json.deptId || "441";
    var level = json.level || "";
    var asyn = json.asyn || false;
    var url = json.url;
    var positionName = json.positionName;
    var check = setVlaue(json.check);
    var cancle = setVlaue(json.cancle);
    var select  = json.select || false;
    var height = json.height || "551px";
    var zw = json.zw || "";
    var zj = json.zj || "";
    var zwjb = json.zwjb || "";
    var rolesNo = json.rolesNo || "";
    var deptNames = json.deptNames || "";
    var callback = json.callback || null;
    deptNames = encodeURI(encodeURI(deptNames));
    console.log(check+"==="+cancle);
    _id = id,_name = name,_mark = mark,_parentId = parentId,_parentName = parentName,_treeId = treeId,_callbak = callback;
    var contentUrl,title;
    if(type != ""){
    	if(type=="1"||type==1){
    		title="选择人员";
    		if (asyn && !select) {
                contentUrl = "/modules/system/component/tree/asynUserTree.html";
                // url = "/system/component/tree/getDeptOrUserTree?resId="+$("#"+resId).val();
            } else if(asyn && select){
    		    contentUrl="/modules/system/component/tree/asynUserTreeOnlySelect.html"
            } else {
                contentUrl = "/modules/system/component/tree/selectUserTree.html";
                // url = "/system/component/tree/deptUserTree?resId="+$("#"+resId).val();
            }
    	}else{
    		title="选择部门" ;
    		if (asyn && !select) {
                contentUrl = "/modules/system/component/tree/asynDeptTree.html";
                // url = "/system/component/tree/getDeptOrUserTree=?resId"+$("#"+resId).val();
            } else if(asyn && select) {
                contentUrl = "/modules/system/component/tree/synDeptTreeSelect.html";
                // url = "/system/component/tree/deptTree?resId="+$("#"+resId).val();
            }else if(!asyn && select){
                contentUrl="/modules/system/component/tree/asynUserTreeOnlySelect.html"
            } else{
                contentUrl = "/modules/system/component/tree/synDeptTree.html";
            }
    	}
    }
    
    var param = "?url="+url+"&isMulti="+isMulti+"&deptId="+deptId+"&check="+check+"&cancle="+cancle+
        "&resId="+$("#"+resId).val()+"&positionName="+positionName+"&zw="+zw+"&zj="+zj+"&zwjb="+zwjb+
        "&rolesNo="+rolesNo+"&deptNames="+deptNames;
    if (!asyn) {
        param = param + "&level="+level;
    }
    layer.open({
        id:"selectZtree",
        type: 2,
        content: contentUrl + param,
        area: ['770px', height],
        title: [title, 'font-size:16px;font-weight:bold;'],
        success:function(layero, index){
        	_index = index;
           // 通过iframe 操作
           var iframeId = $("#selectZtree").find('iframe').attr('id')
           var obj = $(window.frames[""+iframeId+""].document)

           $(obj).find('#id').val(id)
           $(obj).find('#name').val(name)
           $(obj).find('#mark').val(mark)
           $(obj).find('#idContext').val($('#'+id).val())
           $(obj).find('#nameContext').val($('#'+name).val())
           $(obj).find('#deptIdContext').val($('#'+parentId).val())
           $(obj).find('#deptNameContext').val($('#'+parentName).val())
           $(obj).find('#treeidContext').val($('#'+treeId).val());

           // 调用数据回显方法
           document.getElementById(iframeId).contentWindow.CommonUtil.eachSelectData();
           // 初始化树
           document.getElementById(iframeId).contentWindow.deptZtree.init();
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
    /**
     * 如果有回调方法，按照自定义回调回显数据
     * 参数：{ids:"",names:"",treeids:"",parentIds:"",parentNames:""}
     */
    if(_callbak){
        _callbak(res);
    }else{
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