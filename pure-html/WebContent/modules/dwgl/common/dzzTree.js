var dzzTree = function(){

	/**
	 * ztree的初始化参数
	 */
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,

        },
        view:{
            selectedMulti:false
        }
    };
    

    /**
     * 点击事件，展示字典项
     */
    function onClick(event, treeId, treeNode) {
        refreshTable(treeNode);
    }



    /**
     * 初始化左侧字典类型树
     */
    var init = function (json) {
        var resId = $('#left_ul').find('a.active').attr("id");
        $.ajaxSettings.async = false;
        $.getJSON("/djgl/ddjs/dzz/dzzgl/getTree?resId="+resId+"&id="+json.id+"&type="+json.type, function (json) {
            if(json.length > 0){
                var ztree = $.fn.zTree.init($("#treeDemo"), setting, json);
                ztree.selectNode(ztree.getNodes()[0]);
            }
        });
        $.ajaxSettings.async = true;
    }

    /**
     * 增加节点
     */
    var addNode = function (json) {
        var ztree = $.fn.zTree.getZTreeObj("treeDemo");
        var parentNode = ztree.getNodeByParam("id",json.pId);
        if(json.orgType == "611" && json.pId != '000'){
            json.icon = "/modules/dwgl/common/img/folder_Close_red.gif";
        }else if(json.orgType == "621"){
            json.icon = "/modules/dwgl/common/img/folder_Close_yellow.gif";
        }else if(json.orgType == "631"){
            json.icon = "/modules/dwgl/common/img/folder_Close_blue.gif";
        }else{
            json.icon = "/modules/dwgl/common/img/folder_Close_green.gif";
        }
        ztree.addNodes(parentNode, json);
    }

    /**
     * 更新节点
     */
    var updateNode = function(data,count){
        var ztree = $.fn.zTree.getZTreeObj("treeDemo");
        var node = ztree.getNodeByParam("id",data.data.id, null);
        if(node){
            node.pId = data.data.superId;
            node.orgType = data.data.orgType;
            if(count){
                node.count = count;
            }
            if(node.pId == '000'){
                node.icon = "/modules/dwgl/common/img/timg.gif";
            }else if(node.orgType == "611" && node.pId != '000'){
                node.icon = "/modules/dwgl/common/img/folder_Close_red.gif";
            }else if(node.orgType == "621"){
                node.icon = "/modules/dwgl/common/img/folder_Close_yellow.gif";
            }else if(node.orgType == "631"){
                node.icon = "/modules/dwgl/common/img/folder_Close_blue.gif";
            }else{
                node.icon = "/modules/dwgl/common/img/folder_Close_green.gif";
            }
            if($('#ckb',window.parent.document).is(':checked')){
                node.sname = data.data.orgName;
                node.name = data.data.orgName+"("+node.count+")";
            }else{
                node.name = data.data.orgName;
            }

        }
        ztree.updateNode(node);
        onClick(null, ztree.setting.treeId,ztree.getSelectedNodes()[0]);//调用事件

    }

    /**
     * 删除节点
     */
    var deletNode = function(id){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("id", id, null);
        treeObj.removeNode(node);
    }

    /**
     * 根据节点名称模糊查询节点并触发点击事件
     */
    var searchNodes = function(nodeName){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodesByParamFuzzy("name", nodeName, null);
        if (nodes.length > 0) {
            treeObj.selectNode(nodes[0]);
            onClick(null, treeObj.setting.treeId, nodes[0]);//调用事件
        }
    }

        return {
        init:function (json) {
            init(json);
        },
        addNode:function (json) {
            addNode(json);
        },
        updateNode:function(json,count){
            updateNode(json,count);
        },
        delNode:function(id){
            deletNode(id);
        },
        searchNodes:function(nodeName){
            searchNodes(nodeName);
        }
    }
}();
