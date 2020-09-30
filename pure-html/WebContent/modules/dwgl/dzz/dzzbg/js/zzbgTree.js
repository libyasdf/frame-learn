var zzbgTree = function(){

	/**
	 * ztree的初始化参数
	 */
    var setting = {
        edit: {
            enable: true,
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

        },
        view:{
            selectedMulti:false
        }
    };
    

    /**
     * 点击事件，展示字典项
     */
    function onClick(event, treeId, treeNode) {
        var originalTree = $.fn.zTree.getZTreeObj("originalTree");
        var targetTree = $.fn.zTree.getZTreeObj("targetTree");
        if(treeId == 'targetTree'){   //目标组织
            var originalNode = originalTree.getSelectedNodes()[0];
            if(!originalNode){
                targetTree.cancelSelectedNode();
                layer.msg("请先选择迁移组织！", {icon: 0});
                return false;
            }
            if(treeNode.id == originalNode.id){
                targetTree.cancelSelectedNode();
                layer.msg("目标党组织与迁移党组织不能是同一个组织！", {icon: 0});
                return false;
            }else if(originalNode.orgType == '611' ){
                if(treeNode.orgType == '621' || treeNode.orgType == '631' || treeNode.orgType == '666'){
                    targetTree.cancelSelectedNode();
                    layer.msg("目标党组织不能是迁移党组织或者下级党组织！", {icon: 0});
                    return false;
                }
            }else if(originalNode.orgType == '621'){
                if(treeNode.orgType == '621' || treeNode.orgType == '631' || treeNode.orgType == '666'){
                    targetTree.cancelSelectedNode();
                    layer.msg("目标党组织属性不能是同级党组织或者下级党组织！", {icon: 0});
                    return false;
                }
            }else if(originalNode.orgType == '631'){
                if(treeNode.orgType == '631' || treeNode.orgType == '666'){
                    targetTree.cancelSelectedNode();
                    layer.msg("目标党组织属性不能是同级党组织或者下级党组织！", {icon: 0});
                    return false;
                }
            }else if(originalNode.orgType == '666'){
                targetTree.cancelSelectedNode();
                layer.msg("党小组不能迁移！", {icon: 0});
                return false;
            }
            if(treeNode.id == originalNode.getParentNode().id){
                targetTree.cancelSelectedNode();
                layer.msg("目标党组织不能是迁移党组织的上一级党组织！", {icon: 0});
                return false;
            }
        }
        if(treeId == 'originalTree'){ //迁移组织
            var targetNode = targetTree.getSelectedNodes()[0];
            if(targetNode){
                if(treeNode.orgType == '611'){
                    if(targetNode.orgType != '611'){
                        originalTree.cancelSelectedNode();
                        layer.msg("迁移党组织属性不能是目标党组织的上级党组织！", {icon: 0});
                        return false;
                    }
                }else if(treeNode.orgType == '621') {
                    if (targetNode.orgType != '611') {
                        originalTree.cancelSelectedNode();
                        layer.msg("迁移党组织属性不能是目标党组织的同级党组织或者上级党组织！", {icon: 0});
                        return false;
                    }
                }else if(treeNode.orgType == '631') {
                    if (targetNode.orgType == '631' || targetNode.orgType == '666') {
                        originalTree.cancelSelectedNode();
                        layer.msg("迁移党组织属性不能是目标党组织的同级党组织或者上级党组织！", {icon: 0});
                        return false;
                    }
                }else if(treeNode.orgType == '666') {
                    originalTree.cancelSelectedNode();
                    layer.msg("党小组不能迁移！", {icon: 0});
                    return false;
                }else if(treeNode.orgType == '621'){
                    originalTree.cancelSelectedNode();
                    layer.msg("迁移党组织属性不能是同级党组织或者上级党组织！", {icon: 0});
                    return false;
                }
                var childrenNodes = targetNode.children;
                if (childrenNodes) {
                    for (var i = 0; i < childrenNodes.length; i++) {
                        if(childrenNodes[i].id == treeNode.id){
                            originalTree.cancelSelectedNode();
                            layer.msg("迁移党组织不能是目标党组织的下一级党组织！", {icon: 0});
                            return false;
                        }
                    }
                }

            }
        }
    }



    var init = function (json) {
        var resId = $('#left_ul').find('a.active').attr("id");
        $.ajaxSettings.async = false;
        $.getJSON("/djgl/ddjs/dzz/dzzgl/getTree?resId="+resId+"&id="+json.id+"&type="+json.type, function (json) {
            var originalTree = $.fn.zTree.init($("#originalTree"), setting, json);
            var targetTree = $.fn.zTree.init($("#targetTree"), setting, json);
        });
        $.ajaxSettings.async = true;
    }


    return {
        init:function (json) {
            init(json);
        }
    }
}();
