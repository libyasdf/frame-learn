<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<style type="text/css">
.panel-controls {
	position: absolute;
	right: 40px;
	top: 10px;
}

.panel-controls>i.showSelect {
	font-size: 16px;
	color: #acb1b8;
	cursor: pointer;
}

label {
	font-weight: normal;
}
</style>
<div class="container-fluid">
	<div class="row">
		<!--查询-->
		<form class="form-horizontal" id="form">
			<div class="col-md-12">
				<div class="panel panel-default toggle">
					<div class="panel-heading" style="cursor: pointer;">
						<h3 class="panel-title">查询项</h3>
						<div class="panel-controls ">
							<i class="glyphicon glyphicon-plus showSelect"></i>
						</div>
					</div>
					<div class="panel-body" style="display: none;">
						 <div class="form-group">
                             <label class="col-md-2 control-label"> 问卷标题：</label>
							<div class="col-md-3">
								<input class="form-control" type="text" id="surveyName" name="surveyName"/>
								 <input type="hidden" name="subflag" id="subflag">
							</div> 
							<label class="col-md-2 control-label"> 状态：</label>
                            <div class="col-md-4"  >
                            	<select id="surveyState" name="surveyState" class="form-control" ></select>
                            </div>
                        </div>
						 <div class="form-group">
                             <label class="col-md-2 control-label"> 创建日期：</label>
							<div class="col-md-3">
								<input class="form-control" type="text" id="timeRange" />
								 <input type="hidden" name="subflag" id="subflag">
							</div> 
                        </div>
						<div class="form-group">
							<div class="col-md-12">
								<div class="col-md-12" style="text-align: center">
									<button type="button" class="btn btn-primary"
										onclick="TableInit.refTable('right_table1')">
										查&nbsp;&nbsp;询</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-primary"
										onclick="clearAll();">
										重&nbsp;&nbsp;置</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!--查询结束-->
		<a id="searchA" href="" target="_blank"></a>
		<div class="col-md-12">
			<div class="list_button_area">
				<button class="list_table_btn2" onclick="draft()">
					<span class="glyphicon glyphicon-plus-sign"></span> 起草
				</button>
				<!-- <button class="list_table_btn2" onclick="exportDate()">
					<span class="glyphicon glyphicon-chevron-right"></span> 导出
				</button> -->
			</div>
			<!--bootstrap-table表格-->
			<table id="right_table1"></table>
		</div>
	</div>
</div>



<script type="text/javascript" src="/common/js/getrequest.js"></script>
<script type="text/javascript" src="/product/workflow/js/common/getCook.js"></script>

<script type="text/javascript" src="/modules/system/component/tree/js/deptUserTree.js"></script>
<script src="/common/js/config.js"></script>
<script type="text/javascript" src="/common/js/dateutil.js"></script>
<script src="/common/js/commonFunction.js"></script>
<script type="text/javascript">
	$("#subflag").val(Config.startflag)
	 Dictionary.init({mark:'surveyState',type:'1',position:"surveyState",domType:"select",name:"surveyState"}); 
	//参考：http://www.layui.com/laydate/
	//绑定日期输入input

	//日期范围
	laydate.render({
		elem : '#timeRange',
		range : true
	}); 
	
	//起草
	var _index;
	function draft(){
		  var resId = $('#left_ul').find('a.active').attr("id");
               
                layer.open({
                    type: 2,
                    content: '/modules/wenjuan/content/diaowen-design/surveyDirectoryGenalsEditForm.html?oper=NEW&resId=' + resId + "&type=" + parameter.type,
                    area: ['600px', 'auto'],
                    title: ['新建问卷、表单', 'font-size:16px;font-weight:bold;'],
                    success:function(layero, index){
        	        	_index=index;
        	        }
                })
                
	}
	  //关闭导入窗口
	  function putBackData1(){
	    //关闭窗口
	    layer.close(_index);
	 }
	//定义bootatrap-table数据列
	//    sortable：开启列排序，其他参考API
	var right_table_col = [
			{
				field : 'id',
				title : '序号',
				width : '5%',
				order : "desc",
				align : "center",
				formatter : function(value, row, index) {
					//计算序号，并存放业务ID，及已办事项ID
					var pageSize = $('#right_table1').bootstrapTable(
							'getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber = $('#right_table1').bootstrapTable(
							'getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
					return "<span data-id='"+row.id+"' data-workitemid='"+row.yibanid+"'>"
							+ orderNum + "</span>";
				}
			},
			/* {
				field : 'overTimeTitle',
				title : '标题',
				width : '17%',
				align : "center"
			}, */
			{
				field : 'surveyName',
				title : '问卷标题',
				width : '8%',
				align : "center",
			   formatter: function (value, row, index) {
		        	if(value!=null){
		        		var length = value.length
			  	   	      var val = '';
			  	   	      if(length>21){
			  	   	    	  val = value.substring(0,20)+"...";//
			  	   	      }else{
			  	   	    	  val = value;
			  	   	      }
		  	   	   		return  "<span data-content='"+row.content+"'>"+val+"</span>";
		        	}else{
		        		return "";
		        	}
		        	 
		        }
			},
			
			{
				field : 'creDateLable',
				title : '创建时间',
				width : '10%',
				align : "center"
			},
			{
				field : 'surveyState',
				title : '状态',
				width : '10%',
				align : "center",
				formatter : function(value, row, index) {
					if(value=='0'){
						return '设计'
					} else if(value=='1'){
						return '执行中'
					}else{
						return '结束'
					}
				}
			},
        {
            field : 'subflag',
            title : '流程状态',
            width : '10%',
            align : "center",
            formatter: function (value, row, index) {
                if(value == '0'){
                    return "草稿";
                }else if(value == '5'){
                    return "已发布";
                }else if(value == '1'){
                    return "流程中";
                }else if(value == '3'){
                    return "撤办";
                }else if(value == '6'){
                    return "审批未通过";
                }
            }
        },
			{
				field : 'cz',
				title : '操作',
				width : '6%',
				align : "center",
				formatter : function(value, row, index) { //直接定义function,return就是html代码
					var opt = value.split(",");
					var html = "";
					 for (var i = 0; i < opt.length; i++) {
						if (opt[i] == "add") {

						} else if (opt[i] == "update") {
							html += "<i class='glyphicon glyphicon-edit' title='设计' onclick=\'list.editFun(\""
									+ row.id + "\")\'></i>&nbsp;&nbsp;";
						} else if (opt[i] == "delete") {
							html += "<i class='glyphicon glyphicon-trash' title='删除' onclick=\'list.deleteFun(\""
									+ row.id + "\")\'></i>&nbsp;&nbsp;";
						}else if (opt[i] == "tongji") {
							html += "<i class='glyphicon glyphicon-file' title='统计' onclick=\'list.tongji(\""
								+ row.id + "\")\'></i>";
					    }
					} 
					return html;
				}
			} ];
	
	
	var parameter;	

	$(document)
			.ready(
					function(e) {
						var url = $("#left_ul").find("a.active").attr("url");
						 parameter = new GetParameter(url);
						//初始化表格
						TableInit
								.init({
									domId : "right_table1",
									url : "/wenjuan/surveyDirectory/getGenalsList",
									columns : right_table_col,
									queryParams : function(params) {
										//这里将各个查询项添加到要传递的参数中
										//                可以做一些校验
										 params.resId = $('#left_ul').find('a.active').attr("id");
										 params.type = parameter.type;
										 params.surveyState=$('#surveyState').val()
										  params.timeRange=$('#timeRange').val()
										 params.surveyName=$('#surveyName').val()
										//params.subflag = $("#subflag").val();
										return params;
									},
									readOnlyFn : function() {
										/*  $('tr.readOnly').find('td:not(:last)').bind('mouseover', function (e) {
							                     $(this).attr("title","点击查看详情") 
						                  }); */
									}
								});
					});

	//    表格数据项的操作
	var list = {
		editFun : function(id) {
			 var resId = $('#left_ul').find('a.active').attr("id");
			 var params="surveyId=" + id;
			//window.location.href="/wenjuan/surveyDesign/intoDesignPage?"+params;
			//window.open("/wenjuan/surveyDesign/intoDesignPage?"+params,'','width=1500,height=700')
            var datas = {"id":id};
            $.ajax({
                type: "POST",
                url:"/wenjuan/surveyDirectory/findSurveyId",
                data:datas,
                async: false,
                success:function(json){
                    var subflag = json.subflag;
                    if(subflag == '0'){
                        //跳转列表页
                        $("#searchA").prop("href","/wenjuan/surveyDesign/intoDesignPage?" + params);
                        $("#searchA")[0].click();	//触发a标签
                    }else{
                        layer.msg("该条数据已发送，不可再进行操作！", {icon: 3});
                        TableInit.refTable("right_table1");
                    }
                }
            });
		},
		deleteFun : function(id) {
			var resId = $('#left_ul').find('a.active').attr("id");
            var datas = {"id":id};
            $.ajax({
                type: "POST",
                url:"/wenjuan/surveyDirectory/findSurveyId",
                data:datas,
                async: false,
                success:function(json){
                    var subflag = json.subflag;
                    if(subflag == '0'){
                        MyLayer.deleteOpt({
                            url: '/wenjuan/surveyDirectory/delete?id=' + id + "&resId=" + resId,
                            tableId:'right_table1'
                        })
                    }else{
                        layer.msg("该条数据已发送，不可再进行操作！", {icon: 3});
                        TableInit.refTable("right_table1");
                    }
                }
            });
		},
		stopCollect : function(id) {
			var resId = $('#left_ul').find('a.active').attr("id");
       
            $.post("/wenjuan/surveyDirectory/stopCollect", { id: id },function(){
            	TableInit.refTable('right_table1');
            } );
		},	
		startCollect : function(id) {
			var resId = $('#left_ul').find('a.active').attr("id");
            $.post("/wenjuan/surveyDirectory/startCollect", { id: id },function(){
            	TableInit.refTable('right_table1');
            } );
		},
		tongji : function(id) {
			var resId = $('#left_ul').find('a.active').attr("id");
       		//跳转列表页
			$("#searchA").prop("href","/wenjuan/surveyReport/defaultReport?surveyId=" + id);
			$("#searchA")[0].click();	//触发a标签
		}
	}
	/**
	 * 导出按钮的事件
	 */
	function exportDate() {
		$('#form').attr('action','/zhbg/kqgl/overTime/export');
		$('#form').attr('method','get');
		$("#form").submit();
	};
	 //清空查询条件
	function clearAll(){
		$("#timeRange").val("");
    	$("#surveyState").val("");
    	$("#surveyName").val("");
    	
	}
	//刷新方法，流程中需要回调
	function refreshPage(){
		TableInit.refTable("right_table");
	}
</script>
