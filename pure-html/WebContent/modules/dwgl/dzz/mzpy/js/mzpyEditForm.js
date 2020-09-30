$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	if(regVlaue(theRequest.addOrUpdate)=="add"){
		$("#partyOrganizationId").val(theRequest.orgId);
		$("#partyOrganizationName").val(decodeURI(regVlaue(theRequest.orgName)));
	}
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dzz/mzpy/edit",datas,function (data){
			DisplayData.playData({data:data});
			if($("#opertation").val() == "VIEW"){
                //初始化表格
                TableInit.init({
                    domId: "right_table",
                    url: "/djgl/ddjs/dzz/mzpy/getMzpyListDymzpy",
                    columns: right_table_col,
                    queryParams: function (params) {
                        var theRequest = GetRequest();
                        params.reviewid = $("#id").val();
                        params.isAll =false;
                        return params;
                    },
                    readOnlyFn: function (res) {
						$('tr.readOnly').find('td:not(:last)').attr("title","点击查看详情");//悬停显示
                        $('tr.readOnly').find('td').unbind('click').bind('click',function(e) {
                                //                    取消事件冒泡
                                var evt = e ? e
                                    : window.event;
                                if (evt.stopPropagation) {
                                    evt
                                        .stopPropagation();
                                } else {
                                    evt.cancelBubble = true;
                                }
                                //   取消事件冒泡 end
                                var id = $(this).parent().find("span[recordid]").attr("recordid");
                                var resId = $('#resId').val();
                                MyLayer.layerOpenUrl({
                                    url : '/modules/dwgl/dzz/mzpy/dymzpyReadOnlyEditForm.html?id='+id+"&resId="+resId+"&opertation=VIEW",
                                    title : '民主评议',
                                    width : '90%',
                                    height : '85%'
                                });
                            });
                    }
                })

            }
		});

	}
})



function commitForm() {
	var data = saveForm();
	if (data) {

		layer.msg("保存成功！", {
			icon : 1
		});
//		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
		// 刷新列表
		parent.TableInit.refTable('right_table');

	}
}


/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	var myDate = new Date();
	/*var time = myDate.toLocaleDateString().split('/').join('-');
	$("#reportingTime").val(time);*/
	if(bootstrapValidator.isValid()){
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/dzz/mzpy/saveMzpy",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				if (json.flag == '1') {
					res = json.flag;
					$("#id").val(json.data.id);
					MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files"});
				}
			},
			error:function(){
			}
		});
		//保存临时意见
		/*var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(),tempIdea)*/;
	}
	console.log(res);
	return res;
}




/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val||val=="undefined"){
		val = "";
	}
	return val;
}