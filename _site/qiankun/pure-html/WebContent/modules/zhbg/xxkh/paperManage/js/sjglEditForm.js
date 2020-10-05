  $(function(){
	  var theRequest = GetRequest();
	  $("#type").val(theRequest.type);
	  $("#nodeId").val(theRequest.nodeId)
	  $("#id").val(theRequest.id)
	  $("#createType").val(theRequest.createType)
	 if(theRequest.createType==1){
		 getSjAutoData();
		 $("#tip").html("操作步骤：1.添加题型；2.设置简单、一般、困难数量；3.点击组卷按钮进行组卷；4.设置每题分数；")
	 }
	  if(theRequest.createType==0){
		 getsjdata();
		 $("#tip").html("操作步骤：1.添加题型；2.选择试题；3.设置每题分数；")
	 }
	 
	  
  })  
  //用与是否重新计算分数用
  var tbody =false;
  //还未用到
  var autoTbody = false;
  function getSjAutoData(){
	  $("#autoTbody").html("");
	  if($("#id").val()!=""){
		  $.get("/zhbg/xxkh/xxkhPaperInfo/getAutoById",{id:$("#id").val(),zz:Math.random()},function(data){
			  DisplayData.playData({
					data : data
				});
			  $("#zongfen").html(data.data.fullScore);
			  if(data.data.state==1){
				  $("#isState1").prop("checked",true);
			  }else{
				  $("#isState0").prop("checked",true);
			  }
			  if(data.data.isShare==1){
				  $("#isShare1").prop("checked",true);
			  }else{
				  $("#isShare0").prop("checked",true);
			  }
			  var questionType = "";
			  var isZujuan="";
			  $.each(data.data.group,function(i,v){
				  if(v.zujuanStatus==1){
					  isZujuan="readonly='readonly'"
				  }
				    if (v.questionType == 1) {questionType="单选";}
					if (v.questionType == 2) {questionType="多选";}
					if (v.questionType == 3) {questionType="判断";}
					if (v.questionType == 4) {questionType="填空";}
					if (v.questionType == 5) {questionType="简答";}
					var weitiao = "";
						if(v.zujuanStatus==0){
							weitiao = "display:none"
						}
					var noCkecked = "";
					if(v.zujuanStatus==1){
						noCkecked = "disabled='true'";
							if(typeof($("#deptType").val())!="undefined"){
								$("#deptType").prop("disabled",true)
							}
						}else{
							noCkecked = "checked";
						}
					
				  $("#autoTbody").append('<tr id="'+v.questionType+'">\
							<td class="text-center"><input id='+v.id+' type="checkbox" '+noCkecked+' /></td>\
							<td class="text-center">'+(i+1)+'</td>\
							<td class="text-center">'+questionType+'</td>\
							<td class="text-center"><input class="form-control"\
								type="text" name="jiandan" id="jiandan" '+isZujuan+' value="'+v.simpleCount+'" onblur="jiancetishu(this,1)" onfocus="clean(this)" /></td>\
							<td class="text-center"><input class="form-control"\
								type="text" name="yiban" id="yiban" '+isZujuan+' value="'+v.normalCount+'" onblur="jiancetishu(this,2)" onfocus="clean(this)" /></td>\
							<td class="text-center"><input class="form-control"\
								type="text" name="kunnan" id="kunnan" '+isZujuan+' value="'+v.hardCount+'" onblur="jiancetishu(this,3)" onfocus="clean(this)" /></td>\
							<td class="text-center"><input class="form-control"\
								type="text" name="everyScore" id="everyScore" onblur="autoZuJuan(\''+v.id+'\',this)" value="'+v.everyScore+'" /></td>\
							<td class="text-center">'+v.fullScore+'</td>\
							<td class="text-center">'+(v.zujuanStatus==0?"未组卷":"已组卷")+'</td>\
							<td class="text-center"><i\
								class="glyphicon glyphicon-edit" onclick="selectQuestionAuto(\''+v.questionType+'\',\''+v.id+'\')" title=\'微调\' \
								style="cursor: pointer;'+weitiao+'"></i><i\
								class="glyphicon glyphicon-trash p-l-10" title=\'删除\'\
								style="cursor: pointer;" onclick="deleteTiZuAuto(\''+v.id+'\',this)"></i></td>\
						</tr>');
				  isZujuan="";
			  });
			  if(autoTbody){
				  var  sjfullScore = 0;
				  $("#autoTbody tr").each(function(){
					  sjfullScore+=$(this).find("td").eq(7).text()-0;
				  })
				  $("#zongfen").html(sjfullScore);
					$.get("/zhbg/xxkh/xxkhPaperInfo/updataFenShu",{id:$("#id").val(),fullScore:sjfullScore,zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
					parent.TableInit.refTable('right_table');
					
					if($("#autoTbody").find("tr").length<=0){
						if(typeof($("#deptType").val())!="undefined"){
							$("#deptType").prop("disabled",false)
						} 
					}
				  autoTbody=false;
			  }
		  })
  }
  }
  
  //获取试卷数据
  function getsjdata(){
	  $("#tbody").html("");
	  if($("#id").val()!=""){
		  $.get("/zhbg/xxkh/xxkhPaperInfo/getById",{id:$("#id").val(),zz:Math.random()},function(data){
			  $("#zongfen").html(data.data.fullScore);
			  //关闭选择试题页面就不走这了
			  if(!tbody){
			  DisplayData.playData({
					data : data
				});
			  if(data.data.state==1){
				  $("#isState1").prop("checked",true);
			  }else{
				  $("#isState0").prop("checked",true);
			  }
			  if(data.data.isShare==1){
				  $("#isShare1").prop("checked",true);
			  }else{
				  $("#isShare0").prop("checked",true);
			  }
			  }
			  var questionType = "";
			  $.each(data.data.group,function(i,v){
					if (v.questionType == 1) {questionType="单选";}
					if (v.questionType == 2) {questionType="多选";}
					if (v.questionType == 3) {questionType="判断";}
					if (v.questionType == 4) {questionType="填空";}
					if (v.questionType == 5) {questionType="简答";}
				  $("#tbody").append('<tr id="'+v.questionType+'" >\
						  <td class="text-center" >'+(i+1)+'</td>\
						  <td class="text-center" >'+questionType+'</td>\
						  <td class="text-center" >'+((v.questionCount!='')?(v.questionCount):0)+'</td>\
						  <td class="text-center" ><input class="form-control" type="text" onblur=\"suanzongfen(\''+v.id+'\',this)\" name="everyScore" id=\"'+v.id+'\" value=\"'+v.everyScore+'\"/></td>\
						  <td class="text-center" >'+((v.fullScore!='')?(v.fullScore):0)+'</td>\
						  <td class="text-center" >\
						  <i class="glyphicon glyphicon-edit" title=\'选择试题\' style="cursor:pointer;" onclick="selectQuestion(\''+v.questionType+'\',\''+v.id+'\')"></i>\
						  <i class="glyphicon glyphicon-trash p-l-10" title=\'删除\' style="cursor:pointer;" onclick="deleteTiZu(\''+v.id+'\')"></i>\
						  </td>\
						  </tr>');
			  })
			  if(tbody){
				  var zongfen= 0;
					$("#tbody tr").each(function(){
						var tiZuFen =	$(this).find("td").eq(2).text()*$(this).find("td").eq(3).find("input").val();
						$(this).find("td").eq(4).text(tiZuFen);
						zongfen +=tiZuFen;
						$.get("/zhbg/xxkh/qusetiongroup/update",{id:$(this).find("td").eq(3).find("input").attr("id"),fullScore:$(this).find("td").eq(3).find("input").parent().prev().text()*$(this).find("td").eq(3).find("input").val(),everyScore:$(this).find("td").eq(3).find("input").val(),zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
					})  
						$("#fullScore").val(zongfen);
						$("#zongfen").html(zongfen);
						$.get("/zhbg/xxkh/xxkhPaperInfo/updataFenShu",{id:$("#id").val(),fullScore:zongfen,zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
						parent.TableInit.refTable('right_table');
						tbody=false;
			  }
		  },"json");
	  }
  }
//表单验证
$('#form').bootstrapValidator({
    	message : 'This value is not valid',
    	feedbackIcons : {
    		valid : 'glyphicon glyphicon-ok',
    		invalid : 'glyphicon glyphicon-remove',
    		validating : 'glyphicon glyphicon-refresh'
    	},
    	// excluded:[":hidden",":disabled",":not(visible)"]
    	// ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
    	fields : {
    		name : {
    			trigger : "change", // 监听onchange事件
    			validators : {
    				notEmpty : {
    					message : '试卷名称不能为空！'
    				}
    			}
    		}
    	}
    
    });
	/**
	 * 预览
	 * @returns
	 */
	function commitForm(){
		   MyLayer.layerOpenUrl({
               url:'/modules/zhbg/xxkh/paperManage/paperReadonly.html?id='+$("#id").val()+"&"+Math.random(),
               title:"试卷预览",
               width:"95%",
               height:"95%"
           })
	}


    /**
     * 保存
     */
    function saveForms() {
    	var bootstrapValidator = $("#form").data('bootstrapValidator');
    	// 手动触发验证
    	bootstrapValidator.validate();
    	if (bootstrapValidator.isValid()) {
    		$.get("/zhbg/xxkh/xxkhPaperInfo/save",$("#form").serialize(),function(data){
    			if(data.flag==1){
    				layer.msg("保存成功！", {
    					icon : 1
    				});
    				$("#id").val(data.data.id);
    				parent.TableInit.refTable('right_table');
    			}else{
    				layer.msg("保存异常！", {
    					icon : 0
    				});
    			}
    		},"json")
    	}
    }
  function  suanzongfen(fid,e){
	  var patrn  =/^[1-9]\d{0,2}$/;
		  if(!patrn.test($("#"+fid).val())){
			  layer.msg("分数为1-3位纯数字", {
					icon : 0
				});
			  return;
		  }
		  
		  if($(e).parent().prev().text()==0){
			  layer.msg("请先选择试题后，再设置每题分数", {
					icon : 0
				});
			  $("#"+fid).val("");
			  return;
		  }
	  var zongfen= 0;
	$("#tbody tr").each(function(){
		
		var tiZuFen =	$(this).find("td").eq(2).text()*$(this).find("td").eq(3).find("input").val();
		$(this).find("td").eq(4).text(tiZuFen);
		zongfen +=tiZuFen;
	})  
		$("#fullScore").val(zongfen);
		$("#zongfen").html(zongfen);
		$.get("/zhbg/xxkh/xxkhPaperInfo/updataFenShu",{id:$("#id").val(),fullScore:zongfen,zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
		$.get("/zhbg/xxkh/qusetiongroup/update",{id:fid,fullScore:$("#"+fid).parent().prev().text()*$("#"+fid).val(),everyScore:$("#"+fid).val(),zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
		parent.TableInit.refTable('right_table');
  }
  	/*
   	* 删除题组
    */
  function deleteTiZu(id){
		//加载动画
		var	index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
			layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','width':'200px','color':'#000000'});
			} }); 
	 // MyLayer.deleteOpt({url : '/zhbg/xxkh/qusetiongroup/delete?id=' + id})
	  //$("#tbody").html("<tr style='width: 700px;height:100px;'><td  colspan='6' style='text-align: center'><img style='width: 50px;height:50px;' src='/modules/zhbg/xxkh/common/images/lading.gif'/></td></tr>");
	  $.get("/zhbg/xxkh/qusetiongroup/delete",{id:id,zz:Math.random()},function(data){
		  if(data.flag==1){
			  tbody=true;
			  getsjdata();
			  layer.close(index);
			  if($("#tbody tr").length<=0){
//					$("#autoZuJuan").show();
//					$("#renZuJuan").show();
			  }
		  }else{
			  layer.msg("删除异常，请刷新页面重试", {
					icon : 0
				});
			  layer.close(index);
		  }
	  },"json")
  
  }
  
  /**
   * 删除自动组卷题型
   * @param id
   * @param obj
   */
  function deleteTiZuAuto(id,obj){
	//加载动画
	var	index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
		layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','width':'200px','color':'#000000'});
		} });
	
	$.get("/zhbg/xxkh/qusetiongroup/delete",{id:id,zz:Math.random()},function(data){
	  if(data.flag==1){
		  autoTbody=true;
		  //getSjAutoData();
		  layer.close(index);
		  if($("#autoTbody").find("tr").length<=0){

		  }
		  	//删除该行
			$(obj).parent("td").parent("tr").remove();
			//更新序号
			var totalCount = 0;
			$("#autoTbody").find("tr").each(function(index,element){
				$(this).find("td:eq('1')").html(index+1);
				totalCount = totalCount + parseInt($(this).find("td:eq('7')").html());
			});
			//页面更新总分
			$("#zongfen").text(totalCount);
			//数据库更新总分、刷新父页面
			$.get("/zhbg/xxkh/xxkhPaperInfo/updataFenShu",{id:$("#id").val(),fullScore:totalCount,zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
	  }else{
		  layer.msg("删除异常，请刷新页面重试", {icon : 0});
		  layer.close(index);
	  }
  },"json")
	  
}
  /**
   * 选择试题
   * @param questionType
   * @param id
   * @returns
   */
  function selectQuestion(questionType,id){
	  MyLayer.layerOpenUrl({
		  url:'/modules/zhbg/xxkh/paperManage/ifram.html?questionType='+questionType+'&id='+id+'&treeType='+$("#type").val()+"&"+Math.random()
		  ,title:'选择试题'
		  ,width: '90%'
		  ,height:'97%'
		  ,cancel:function(){
			  tbody=true;
			  getsjdata();
		  }
		})
  }
  
  //计算自动组卷分数
  function autoZuJuan(id,e){
	  var patrn1  =/^([1-9]\d{0,2}|0)$/;
	  if(!patrn1.test($(e).val())){
		  layer.msg("分数为首位非0，1-3位纯数字", {
				icon : 0
			});
		  $(e).val("0");
		  return;
	  }
	  
	  var patrn  =/^([1-9]\d*|0)$/;
	  if(!patrn.test($(e).val())){
		  layer.msg("请填写数字", {
				icon : 0
			});
		  $(e).val("0");
		  return;
	  }
	  if($(e).parent().next().next().html()=="未组卷"){
		  layer.msg("请组卷之后再填写分数", {
				icon : 0
			});
		  $(e).val("0");
		  return;
	  }
	  var kunnan =$(e).parent().prev().find("input").val();
	  var yiban =$(e).parent().prev().prev().find("input").val();
	  var jiandan =$(e).parent().prev().prev().prev().find("input").val();
	  var fullScore = ((kunnan-0)+(yiban-0)+(jiandan-0))*$(e).val();
	  $(e).parent().next().text(fullScore);
	  $.get("/zhbg/xxkh/qusetiongroup/update",{id:id,everyScore:$(e).val(),fullScore:fullScore,zz:Math.random()},function(data){if(data.flag==1){console.log("OK")}},"json");
	  var  sjfullScore = 0;
	  $("#autoTbody tr").each(function(){
		  sjfullScore+=$(this).find("td").eq(7).text()-0;
	  })
	  $("#zongfen").text(sjfullScore);
		$.get("/zhbg/xxkh/xxkhPaperInfo/updataFenShu",{id:$("#id").val(),fullScore:sjfullScore,zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
		parent.TableInit.refTable('right_table');
  }
  /**
   * 自动组卷微调选择试题
   * @param questionType
   * @param id
   * @returns
   */
  function selectQuestionAuto(questionType,id){
	  MyLayer.layerOpenUrl({
		  url:'/modules/zhbg/xxkh/paperManage/ifram.html?questionType='+questionType+'&id='+id+'&treeType='+$("#type").val()+"&"+Math.random()
		  ,title:'选择试题'
		  ,width: '90%'
		  ,height:'97%'
		  ,cancel:function(){
			  $.get("/zhbg/xxkh/qusetiongroup/getLevelCount",{id:id,zz:Math.random()},function(data){
				  var e =$("#"+id).parent().next().next().next();
				  e.find("input").val(data.data.simpleCount);
				  e.next().find("input").val(data.data.normalCount);
				  e.next().next().find("input").val(data.data.hardCount);
				  e.next().next().next().next().html(data.data.fullScore);
				  
				  var  sjfullScore = 0;
				  $("#autoTbody tr").each(function(){
					  sjfullScore+=$(this).find("td").eq(7).text()-0;
				  })
				  $("#zongfen").html(sjfullScore);
					$.get("/zhbg/xxkh/xxkhPaperInfo/updataFenShu",{id:$("#id").val(),fullScore:sjfullScore,zz:Math.random()},function(data){if(data.flag==1){console.log("ok");parent.TableInit.refTable('right_table');}},'json');
					parent.TableInit.refTable('right_table');
			  },"json");
		  }
		})
	  
  }
  