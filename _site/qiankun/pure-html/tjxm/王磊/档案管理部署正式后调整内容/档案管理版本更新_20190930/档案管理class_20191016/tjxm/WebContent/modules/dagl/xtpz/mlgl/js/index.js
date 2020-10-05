var nodes = null;
$(function() {
	typeTree.init({});
	nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
	$("#menleiCode").val(nodes[0].id)
	$("#menleiName").val(nodes[0].name)
})
function updateCss(){
	
	 nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
		  layer.open({
		        type: 2,
		        title:[ '新增','font-size:16px;font-weight:bold;'],
		        content: '/modules/dagl/bmgl/formCss.html?tName='+nodes[0].id,
		        area: ['1100px', '660px'],
		        offset: '30px',
		        resize: false,
		        maxmin: true,
		        success: function (layero, index) {

		        },
		        full: function (layero) {

		            // 让 iframe 高度自适应
		            layero.find('iframe').css('max-height', 'none');
		        },
		        restore: function (layero) {

		        	
		        	
		            // 让 iframe 最高高度为 600px
		            layero.find('iframe').css('max-height', '660px');
		        },
		        end: function () { 

		        }
		    });
}
//添加门类
function addType(){
	layer.open({
		type : 2,
		shadeClose : true,
		content : "modules/dagl/xtpz/mlgl/categoryAddForm.html" + "?resId=" + $('#left_ul').find('a.active').attr("id"),
		area : [ '770px', '350px' ],
		title : ["添加门类", 'font-size:16px;font-weight:bold;']
	})
}
// 修改按钮
function editType() {
	nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
	var url = "/modules/dagl/xtpz/mlgl/tree/treeAdd.html?name=" + encodeURIComponent(nodes[0].name)
			+ "&pId=" + nodes[0].pId + "&id="
			+ nodes[0].id+"&columnName="+nodes[0].columnName;
	var title = "修改名称";
	layer.open({
		type : 2,
		shadeClose : true,
		content : url + "&resId=" + $('#left_ul').find('a.active').attr("id"),
		area : [ '770px', '230px' ],
		title : [ title, 'font-size:16px;font-weight:bold;' ]
	})
}

//删除按钮
function deletType(){
	nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
	if(nodes[0].pId==null || nodes[0].pId=="" || nodes[0].pId=="null" || nodes[0].columnName=="menleicolumn"){
		layer.msg("该节点不能删除，只能删除门类信息！",{icon:0,time:2000});
		return;
	}
    //查询门类下业务表是否含有数据，（包含归档库_dak）

	var tableCodes = getTableNames(nodes[0]);
    $.ajax({
        url:"/dagl/xtpz/category/canDelete",
        data:{tableCodes:tableCodes},
        async:false,
        traditional:true,
        type: "POST",
        success:function(data){

            if(data.flag==1){
				//无数据执行删除
                if(0 == data.num){
                    layer.confirm("确定要删除吗？", { icon: 3 }, function () {

                        $.ajax({
                            url:"/dagl/xtpz/category/deleteTreeName",
                            data:{id:nodes[0].id,pId:nodes[0].pId,name:nodes[0].name,columnName:nodes[0].columnName},
                            async:false,
                            dataType:"JSON",
                            type: "POST",
                            success:function(data){
                                if(data.flag==1){
                                    //选中顶级节点
                                    var node=$.fn.zTree.getZTreeObj("treeDemo").getNodes();
                                    var ztree = $.fn.zTree.getZTreeObj("treeDemo");
                                    var firstNode = "";
                                    if (node.length > 0) {
                                        for(var j = 0;j<node.length;j++){
                                            if(node[j].isParent){
                                                ztree.selectNode(node[j].children[0]);
                                                firstNode = node[j].children[0];
                                                break;
                                            }

                                        }
                                    }
                                    //ztree.onClick(event, firstNode.id, firstNode);

                                    if(firstNode.pId != "" && firstNode.pId != null) {//排除点击全宗的点击事件
                                        var treeNodeIdOfDelete = firstNode.id;
                                        var treeNodePidOfDelete = firstNode.pId;
                                        var treeNodeCnNameOfDelete = firstNode.name;
                                        var treeNodeColumnNameOfDelete = firstNode.columnName;
                                        if ("menleicolumn" == treeNodeColumnNameOfDelete) {
                                            $('.tab-table').show();
                                            $('.tab-form').hide();
                                            _TableInit(treeNodeIdOfDelete, treeNodePidOfDelete);
                                        } else if("menlei" == treeNodeColumnNameOfDelete){
                                            $('.tab-form').show();
                                            $('.tab-table').hide();
                                            $("#menleiCode").val(treeNodeIdOfDelete)
                                            $("#menleiName").val(treeNodeCnNameOfDelete)
                                            TableInit1(treeNodeIdOfDelete);
                                        }
                                    }


                                    typeTree.delNode(nodes[0].id)
                                    layer.msg("删除成功！",{icon:1,time:2000})
                                }else if(data.flag==2){
                                    layer.msg("该节点不能删除，只能删除门类信息！",{icon:0,time:2000});
                                }else{
                                    layer.msg("删除失败，请尝试刷新页面再次删除！",{icon:0,time:2000});
                                }
                            }
                        })
                    })
                }else{
                    layer.msg("该节点不能删除，门类中已含有档案信息！",{icon:0,time:2000});
                }
            }else{
                layer.msg("删除失败，请尝试刷新页面再次删除！",{icon:0,time:2000});
            }
        }
    })

}

// 查询
function searchType() {
	typeTree.searchNodes($("#typeName").val());
}

function getTableNames(nodes) {//获取门类下业务表名
    var object = [];
    categoryCodeTableNames(object, nodes);
    return object;
}

function categoryCodeTableNames(obj, treeObj){

    if(treeObj.isParent){
        var nodesChildrens = treeObj.children;
        for(var i=0;i<nodesChildrens.length;i++){
            obj.push(nodesChildrens[i].id);
            categoryCodeTableNames(obj, nodesChildrens[i]);
        }
    }

}