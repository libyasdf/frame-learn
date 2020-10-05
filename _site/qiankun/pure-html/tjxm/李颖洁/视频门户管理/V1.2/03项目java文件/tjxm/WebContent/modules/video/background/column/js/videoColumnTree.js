var typeTree = function(){
	
	var dragId;//用于存储被拖拽节点的父id

	/**
	 * ztree的初始化参数
	 */
    var setting = {
        edit: {
            drag: {
            	borderMax: 20,
                autoExpandTrigger: true,
                prev: true,
                inner: true,
                next: true
            },
            //enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            beforeDrag: beforeDrag,//用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
            beforeDrop: beforeDrop,//用于捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作
            onDrag: null,//用于捕获节点被拖拽的事件回调函数
            onDrop: onDrop //用于捕获节点拖拽操作结束的事件回调函数
        }
    };
    
    /**
     * 实现栏目拖拽排序
     */
    function onDrop(event, treeId, treeNodes, targetNode, moveType) {
    	if(targetNode == null){
    		return;
    	}
    	for (var i=0,l=treeNodes.length; i<l; i++) {
    		var dropId = treeNodes[i].id;
    		var pId = treeNodes[i].pId;
    		var ids = [];
    		var node = treeNodes[i].getParentNode();
    		var nodeChildren = node.children;
    		for(var j=0;j<nodeChildren.length;j++){
    			ids.push(nodeChildren[j].id);
    		}
    		var nodeLevel = ++ node.nodeLevel;
    		$.ajax({
            	type:"post",
            	url:"/video/background/column/updatetreeSort?ids=" + ids +"&dropId="+dropId+"&superId="+pId+"&nodeLevel="+nodeLevel,
            	dataType:"json",
            	success:function(res){
            		if(res.flag == "1"){
            			layer.msg("排序成功！",{icon:1});
            		}else{
            			layer.msg("排序失败！",{icon:2});
            		}
            	},
            	error:function(){
            		layer.msg("排序异常，请刷新重试！",{icon:2});
            	}
            })
    	}
    };
    
    /**
     * 点击事件，展示字典项
     */
    function onClick(event, treeId, treeNode) {
        $("#superId").val(treeNode.id);
        $('#right_table').bootstrapTable('refresh');
        $('#right_table3').bootstrapTable('refresh');
        parent.showCz(treeNode.id,treeNode.isFirst);
    }
    
    /**
     * 将被拖拽的节点父id存到dragId
     */
    function beforeDrag(treeId, treeNodes) {
        for (var i=0,l=treeNodes.length; i<l; i++) {  
               dragId = treeNodes[i].pId;
            if (treeNodes[i].drag === false) {  
                return false;  
            }  
        }  
        return true;  
    }
    
    /**
     * 判断拖拽到节点的父id是否和被拖到节点的父id一样，实现只允许同级目录拖拽
     */
    function beforeDrop(treeId, treeNodes, targetNode, moveType) {
        /*if(targetNode.pId == dragId){
        	return true;
        }else{
        	layer.msg('只允许同级之间目录拖动！',{icon:2})
        	return false;
        } */
    	return true;
    } 

    /**
     * 初始化左侧字典类型树
     */
    var init = function () {
    	var userId = getcookie("userid");
        $.ajaxSettings.async = false;
        $.getJSON("/video/background/column/getColumnTree?columnName=&userId="+userId+"&math="+Math.random(), function (json) {
        	console.log(json)
            if ("1" == json.flag) {
                for (var i = 0, length = json.data.length; i < length; i++) {
                    if (json.data[i].pId=="0") {
                        json.data[i].open = true;
                    }
                }
                var ztree = $.fn.zTree.init($("#treeDemo"), setting, json.data);
                var nodes = ztree.getNodes();
                if (nodes.length > 0) {
                    ztree.selectNode(nodes[0]);
                }
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
        ztree.addNodes(parentNode, json);
    }

    /**
     * 更新节点
     */
    var updateNode = function(data){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("id", data.id, null);
        if(node){
            node.pId = data.superId;
            node.name = data.columnName;
            node.nodeLevel = data.nodeLevel;
            node.orderNo = data.orderNo;
        }
        treeObj.updateNode(node);
    }

    /**
     * 删除节点
     */
    var deletNode = function(nodeId){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("id", nodeId, null);
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
        init:function () {
            init();
        },
        addNode:function (json) {
            addNode(json);
        },
        updateNode:function(json){
            updateNode(json);
        },
        delNode:function(nodeId){
            deletNode(nodeId);
        },
        searchNodes:function(nodeName){
            searchNodes(nodeName);
        }
    }
}();