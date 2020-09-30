/**
 * 弹出选择部门和用户等
 * chooseDeptUsers(id1,id2,type,sjGS)
 * 参数1 id1 表示存用户ids的隐藏域的name
 * 参数2 id2 表示存用户names的input 的name
 * 参数3 type 2是部门 3是人
 * 参数3 isMulti "1"单选  "0"多选
 * 参数4 backfun 执行成功后的回调
 * 参数5 isInput 选择部门框是否之处手动输入过滤
 */
function chooseDept(id1,id2,type,isMulti,backfun,isInput){
	var url=basePath + "/dept/getDeptOrUserTree",contentUrl,title;
	if(type=="3"||type==3){
		title="选择人员";
		contentUrl = "url:"+basePath+"/product/user/html/userTree.html";
	}else{
		title="选择部门" ;
		contentUrl = "url:"+basePath+"/product/user/html/deptTree.html";
	}
	$.dialog({
		id : 'chooseDeptUsers',
		title : title,
		lock : true,
		padding : 0,
		zIndex :5001,
		width : 500,
		height : 450,
		content : contentUrl,
		data:{"url":url,"isMulti":isMulti,'type':type,'id1':id1,'id2':id2,'isInput':isInput},    
		background : '#fffdee', /* 背景色 */
		opacity : 0.5, /* 透明度 */
		cancelVal : '关闭',
		cancel : true, /* 为true等价于function(){} */
		okval:"确定",
			ok : function() {
				var result = this.content.saveSelectedUsers();
				if (result) {
					if (id1 == undefined || id1 == "" || id1 == null) {
						if (backfun) {
							backfun(result);
						}
						return true;

					} else {
						$('[name="'+id1+'"]').attr("value", result.ids).val(
								result.ids);
						$('[name="'+id2+'"]').attr("value", result.names).val(
								result.names);

						
						if(isInput){//如果是支持手动输入 则显示已选择部门卡片
							//展示已选部门
							//追加li标签到ol中 显示形式为部门小div
							var idArry =  result.ids.split(',');
							var nameArry = result.names.split(',');
							var $ol = $('[name="'+id1+'"]').closest('table').find('ol').eq(0);//隐藏域必须放到 选部门的table里
							$ol.empty();
							for(var i=0;i<idArry.length;i++){
								var idVal = idArry[i],nameVal = nameArry[i];
								var liHtml = '<li class="user" data-id="'+idVal+'" data-name="'+nameVal+'">'
								+'<span class="icon"></span>'
								+'<span class="name" title="">'+nameVal+'</span>'
								+'<a href="javascript:;" class="remove" ' 
								+'onclick="delDeptLi(\'transferUnitTable\',\''+idVal+'\',\'transferUnitId\',\'transferUnit\',event)">×</a>'
								+'</li>'
								$ol.append(liHtml);
							}
						}
						if (backfun) {
							backfun(result);
						}
						//alert("保存成功！");
						return true;
					}
				}else{
					return false;
				}
				
				
			}
	});
	
}
