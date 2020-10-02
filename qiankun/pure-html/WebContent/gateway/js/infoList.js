var totalPage ;
$(function(){
	
	//导航列表展开闭合效果
	$(".showHide").click(function(){
		$(this).siblings().find("h4").css("background-color","#fff").end().find("i").removeClass("icon-untitled551").end().find(".listdetails").hide(500);
		$(this).find(".listdetails").show(500).end().find("h4").css("background-color","#ddd").find("i").addClass("icon-untitled551");
	});
	$(".listdetails").on("click",function(e){
		e.stopPropagation(); 
	});	
	
	//导航栏列表项选中效果
	$(".listdetails>li").click(function(){
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
	})
	
	//左侧导航 添加查询事件
	$(".showHide").click(function(){
		var columnCode = $(this).attr("id");
		getContentList(columnCode,1);
	})
	
	//默认每页10条数据
	$("#pageSize").val("10");
	$("#pageNumber").val("1");
	
	//获取参数
	var request = GetRequest();
	$("#columnCode").val(regVlaue(request.columnCode));
	$("#contentId").val(regVlaue(request.id));
	$("#title").val(regVlaue(decodeURI(request.title || "")));
    $("#search").val(regVlaue(decodeURI(request.title || "")));
	//初始化左侧导航的选中状态
	$("#" + request.columnCode).trigger("click");
	
	//根据栏目查询信息列表
	getContentList($("#columnCode").val());
	
	//获取栏目信息
	getColumn($("#columnCode").val());
	
	//获取子栏目
	//getChildClomns();
	
})

/**
 * 获取当前栏目的信息
 */
function getColumn(columnCode){
	$.ajax({
		url:"/info/column/getColumnByCode",
		dataType: "json",
		data:{
			columnCode: columnCode
		},
		success: function(json){
			console.log(json);
			if(json.flag == "1"){
				//$("#columnName").text(json.data.columnName);
				$("#positon").text(json.data.columnName);
				$("#listTile").text(json.data.columnName);
			}
		},
		error: function(){
			
		}
	})
}

/**
 * 获取子栏目
 */
function getChildClomns(columnCode){
	if(!columnCode){
		columnCode = $("#columnCode").val();
	}
	$.ajax({
		url:"/info/column/getAllColumn",
		dataType:"json",
		data:{
			columnCode: columnCode
		},
		success: function(json){
			console.log(json);
			if(json.flag == "1"){
				var columns = json.data;
				
			}
		},
		error: function(){
			
		}
	})
}
/**
 * 查询栏目下信息列表
 */
function getContentList(columnCode,pageNumber){
	//更新当前所在位置信息
	getColumn(columnCode);
	//查询前，如果页码不为空，先更新当前页码
	if(pageNumber){
		$("#pageNumber").val(pageNumber);
	}
	if(columnCode){
		$.ajax({
			url:"/info/content/getContentByColumn",
			dataType:"json",
			data:{
				columnCode: columnCode,
				title: $("#title").val(),
				pageSize: $("#pageSize").val(),
				pageNumber: $("#pageNumber").val()
			},
			success: function(json){
				console.log(json);
				if(json.flag == "1"){
					var list = json.data.rows;
					var total = json.data.total;
					totalPage = Math.ceil(total/json.pageSize);
					//遍历生成列表
					var tr = "";
					if(!$.isEmptyObject(list)){
						$("#thead").show();
						$.each(list, function(i,row){
							tr += "<li class='clearFloat'>";
							tr += "<p><a href='/gateway/infoDetail.html?columnCode="+columnCode+"&id="+row.id+"' target='_blank' title ="+row.title+">"+row.title+"</a></p>";
							tr += "<p class='itemData'>"+row.creTime.substring(0,10)+"</p>";
							/*tr += "<p class='clicksNum'>"+row.id+"</p>";*/
							tr += "</li>";
						})
					}else{
						$("#thead").hide();
						tr += "<p style='text-align: center;font-size: 15px;'>暂无数据</p>";
					}
					
					$("#rows").empty().append(tr);
					//遍历生成分页按钮
					var btn ="";
					if(totalPage > 1){ //只有数据多于一页的时候才显示翻页按钮
						if($("#pageNumber").val() != 1){//如果当前页码不等于1时，才有上一页按钮
							btn += "<li><a href='javascript:void(0);' onclick='prevPge(\""+columnCode+"\");'>&laquo;</a></li>";
						}
						for(var i=0; i<totalPage; i++){
							if(json.pageNumber == (i+1)){
								btn += "<li class='active'><a href='javascript:void(0);' style='color: red;font-size: 20px'>"+(i+1)+"</a></li>";
							}else{
								btn += "<li><a href='javascript:void(0);'style='font-size: 20px' onclick='getContentList(\""+columnCode+"\",\""+(i+1)+"\");'>"+(i+1)+"</a></li>";
							}
						}
						if($("#pageNumber").val() != totalPage){//如果当前页面不等于totalPage时，才有下一页按钮
							btn += "<li><a href='javascript:void(0);' onclick='nextPge(\""+columnCode+"\");'>&raquo;</a></li>";
						}
						$("#pagination").empty().append(btn);
					}else{
                        $("#pagination").empty().append(btn);
					}
				}
			},
			error: function(){
				
			}
		})
	}
}

/**
 * 前一页
 */
function prevPge(columnCode){
	var pageNumber = $("#pageNumber").val();
	if(pageNumber == 1){
		return;
	}else{
		//页码减一
		$("#pageNumber").val(parseInt(pageNumber)-1);
		getContentList(columnCode,parseInt(pageNumber)-1);
	}
}

/**
 * 下一页
 */
function nextPge(columnCode){
	var pageNumber = $("#pageNumber").val();
	if(pageNumber == totalPage){
		return;
	}else{
		//页码加一
		$("#pageNumber").val(parseInt(pageNumber)+1);
		getContentList(columnCode,parseInt(pageNumber)+1)
	}
}




/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}
