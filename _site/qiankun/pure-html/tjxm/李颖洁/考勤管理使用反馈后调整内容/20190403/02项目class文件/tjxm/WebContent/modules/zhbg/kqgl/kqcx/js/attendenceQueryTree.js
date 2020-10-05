var theRequest = GetRequest();
var type = theRequest.type;
var check = "ps";
var cancle = "ps";
var mycars = new Array();
var type = $("#type").val();


/**
 * 注意这是zTree3.5版本，API必须在3.0版本以上，以下的版本API不对
 * @type {Array}
 */
var deptZtree = function(){
    var hiddenNodes = [];
    var zTreeObj = null;
    var zNodes = null;
    var ZTreeChildrenNodes = [];
    //ztree配置选项
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0
            }
        },
        callback: {
            /*onClick: function(event, treeId, treeNode){
                CommonUtil.zTreeOnclick(event, treeId, treeNode);
            },*/
            onCheck : function(event, treeId, treeNode){
            	//alert("djdj")
                CommonUtil.zTreeOnCheck(event, treeId, treeNode);
            }
        },
        check: {
            enable: true,
            autoCheckTrigger: false,
            chkStyle: "checkbox",
            chkboxType: {"Y":check,"N":cancle}
        }
    };

    // 加载数据
    var loadData = function(){
        var deptId = $("#cruDeptId").val();
        var cruJuId = $("#cruJuId").val();
        var busiRole= $("#busiRole").val();
        var url = "";
        var data = "";
        //考勤管理员角色 查看所有的数据  add by hlt
        if(busiRole!=undefined && busiRole!='' && busiRole.indexOf("D141")!=-1){
        	deptId ="441";
        	cruJuId="441";
        }
        if(deptId==""){
            deptId ="441";
        }
        if(type=="1"){
            url = '/system/component/tree/deptUserTree';
            data = {"deptId":deptId,"level":'10'};
        }else{
            url = '/system/component/tree/deptTree';
            data = {"deptId":cruJuId,"level":'1'};
        }
        
        //获取部门
        $.ajax({
            async:false,
            cache:false,
            type: "GET",//请求方式为get
            url:url,
            dataType: "json", //返回数据格式为json
            data:data,
            success: function(datas) {
                var zNodes=datas;
                treeNodes = datas;
                if (zNodes.length > 0) {
                    zNodes[0].open = true;
                }
                for (var i = 0; i < zNodes.length; i++) {
                    zNodes[i].id = zNodes[i].uuid;
                    zNodes[i].pid = zNodes[i].uupid;
                }
                zTreeObj = $.fn.zTree.init($("#tree-obj"), setting, zNodes);
                CommonUtil.echoData(zTreeObj);
            },
            error: function(request) {
                layer.msg("数据加载失败！请刷新重试！",{icon:2});
            }
        });
    }

    /**
     * 过滤
     * @return {[type]} [description]
     */
    var filter = function() {
        var treeObj = $.fn.zTree.getZTreeObj('tree-obj');
        //清除树节点所有选中的节点
        treeObj.checkAllNodes(false);

        //显示上次搜索后背隐藏的结点
        zTreeObj.showNodes(hiddenNodes);

        //查找不符合条件的叶子节点
        function filterFunc(node) {
            var _keywords = $("#keyword").val();
            /* if (node.name.indexOf(_keywords) != -1) {
                return false;
            }else{ */
            if(node.isParent){//如果是父节点，父节点下的子节点名称不包含模糊查询的名称隐藏，如果包含则不隐藏
                if (node.name.indexOf(_keywords) != -1) {
                    getChildren (node);
                    zTreeObj.expandNode(node,true,false);
                    return false;
                }else{
                    var childrenNodes = zTreeObj.getNodesByParamFuzzy("name", _keywords, node);
                    if(childrenNodes.length==0){
                        return true;
                    }else{
                        zTreeObj.expandNode(node,true,false);
                        return false;
                    }
                }
            }else{
                if (node.name.indexOf(_keywords) != -1) {
                    zTreeObj.expandNode(node,true,false);
                    return false;
                }else{
                    return true;
                }
            }
            //  }
        };

        function getChildren (oneNode){
            if (oneNode.isParent){
                var ZTreeChildrenNodes1 = oneNode.children;
                ZTreeChildrenNodes = ZTreeChildrenNodes.concat(ZTreeChildrenNodes1);
                for(var obj in oneNode.children){
                    getChildren(oneNode.children[obj]);
                }
            }
        }

        //获取不符合条件的叶子结点
        hiddenNodes = zTreeObj.getNodesByFilter(filterFunc);
        //隐藏不符合条件的叶子结点
        zTreeObj.hideNodes(hiddenNodes);
        zTreeObj.showNodes(ZTreeChildrenNodes);
        ZTreeChildrenNodes = [];

    };
    /**
     * 根据节点名称模糊查询节点并触发点击事件
     */
    var searchNodes = function() {
        var treeObj = $.fn.zTree.getZTreeObj("tree-obj");
        var nodeName = $("#keyword").val();
        var nodes = treeObj.getNodesByParamFuzzy("name", nodeName, null);
        if (nodes.length > 0) {
            //treeObj.selectNode(nodes[0]);
            filter();// 调用事件
        }else{
            layer.msg("未查询到结果", {
                icon : 0
            });
        }
    };
    return {
        init:function(){
            loadData();
        },
        filter:function(){
            filter();
        },
        getZTreeObj:function(){
            return zTreeObj;
        },
        searchNodes : function(nodeName) {
            searchNodes(nodeName);
        }
    }
}();

var CommonUtil = {
    /**
     * 回显数据
     * @return {[type]} [description]
     */
    echoData:function(zTreeObj){
        var ids = $("#idContext").val();
        var treeNodes = zTreeObj.getNodes();
        var nodes = zTreeObj.transformToArray(treeNodes);
        if(ids){
            var idArr = ids.split(",");
            for(var i=0;i<nodes.length;i++){
                for(var j=0;j<idArr.length;j++){
                    if(idArr[j] == nodes[i].id){
                        var node = zTreeObj.getNodeByTId(nodes[i].tId);
                        zTreeObj.checkNode(node,true,true);
                    }
                }
            }
        }
    },
    zTreeOnCheck:function(event, treeId, treeNode){
    	userIds="";
    	mycars.length = 0;
    	var zTree = $.fn.zTree.getZTreeObj("tree-obj");//换成实际的图层的id
		var selectedNodes = zTree.getCheckedNodes(true); //获取改变的全部结点
	
		if(treeNode.pid=='0' && treeNode.checked){
			
			//全部选中
			
			isAll="1";
			if($("#busiRole").val().indexOf("D141")==-1){
				for ( var i=0 ; i < selectedNodes.length ; i++ ){
					if(!selectedNodes[i].isParent){
						var id = selectedNodes[i].id;
						mycars.push(id);
					}
				}
			}
		}
		else{
			isAll="0";
			for ( var i=0 ; i < selectedNodes.length ; i++ ){
				if(!selectedNodes[i].isParent){
					var id = selectedNodes[i].id;
					mycars.push(id);
				}
			}
		}
		
		userIds = mycars.join(",");
		 $("#right_table").bootstrapTable("removeAll");
		TableInit.refTable('right_table');
       //alert(userIds)
    },
    zTreeOnclick: function (event, treeId, treeNode) {
        if (treeNode && !treeNode.isParent) {
            if (treeNode.checked) {//如果选中了
                id = treeNode.id;
                node = deptZtree.getZTreeObj().getNodeByParam("id", id, null);
                if (node) {
                    deptZtree.getZTreeObj().checkNode(node, false, true);
                    mycars.remove(id);
                    userIds = mycars.join(",");
                    $("#right_table").bootstrapTable("removeAll");
                    TableInit.refTable('right_table');//刷新列表
                }
            } else {//如果没有选中
                deptZtree.getZTreeObj().checkNode(treeNode, true, false);
                id = treeNode.id;

                //把id放到数组中去
                mycars.push(id);
                if(mycars.length>0){
                    //如果数组的长度不为0，用逗号分隔
                    userIds = mycars.join(",");
                }
                $("#right_table").bootstrapTable("removeAll");
                TableInit.refTable('right_table');//刷新列表
            }
        }
        $('.right_del_list li').find('img').unbind('click').bind('click', function () {
            var obj = $(this).parents('li');
            var id = $(obj).attr('id')
            $(obj).remove();
            if (deptZtree.getZTreeObj) {
                var deleteNodes = deptZtree.getZTreeObj().getNodesByParam("id", id, null);
                for (var j in deleteNodes) {
                    deptZtree.getZTreeObj().checkNode(deleteNodes[j], false, true);
                }

            }
        })
    },
    keyDown:function(){
        var ev = window.event || e;
        //13是键盘上面固定的回车键
        if (ev.keyCode == 13) {
            //你要执行的方法
            deptZtree.searchNodes();
        }
    }
}

$(function () {
    deptZtree.init();
});

function search() {
    deptZtree.searchNodes();
}

//JS的数组对象定义一个函数，用于查找指定的元素在数组中的位置，即索引，得到这个元素的索引
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

//js数组自己固有的函数去删除这个元素
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
