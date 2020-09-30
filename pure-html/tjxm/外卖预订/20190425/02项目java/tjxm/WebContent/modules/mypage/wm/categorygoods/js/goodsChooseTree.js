var _id,_name,_mark,_index,_parentId,_parentName;
/**
 * 打开常用模块选择,数据加载方式：异步
 * @param resourceId 资源ID
 */
function openSelectZtree(json){
    var url ,contentUrl,title;
    title="选择商品";
      contentUrl = "/modules/mypage/wm/categorygoods/goodsChooseTree.html";
      url = "/mock/taskout/goodsTree.json";
    var param = "?url="+url
    $.post(url, {},function(data){
    	
    	if(data.data.length==0){
    		layer.msg("暂无可选常用模块，请联系管理员进行授权！", {
  				icon : 0
  				});
    	}else{
    		layer.open({
    	        id:"selectZtree",
    	        type: 2,
    	        content: contentUrl + param,
    	        area: ['770px', '551px'],
    	        title: [title, 'font-size:16px;font-weight:bold;'],
    	        success:function(layero, index){
    	        	_index=index;
    	        }
    	    })
    	}
    })
    
}


function putBackData(data){
	
    //关闭窗口
    layer.close(_index);
    if(data.flag=='1'){
		layer.msg("设置成功！", {icon : 1});
	}else{
		layer.msg("设置失败！", {icon : 0});
	}
}

