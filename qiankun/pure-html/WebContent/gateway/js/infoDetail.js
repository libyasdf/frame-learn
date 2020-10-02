$(function(){
	
	//获取参数
	var request = GetRequest();
	$("#columnCode").val(regVlaue(request.columnCode));
	$("#contentId").val(regVlaue(request.id));
	$("#title").val(regVlaue(request.title));
	
	//获取文章内容
	getContentInfo();
	
	//获取栏目信息
	getColumn($("#columnCode").val());
	
	
})

/**
 * 获取文章内容及附件
 */
function getContentInfo(id){
	//隐藏列表，显示文章内容
	$("#listDiv").hide();
	$("#detailDiv").show();
	if(!id){
		id = $("#contentId").val();
	}
	if(id){
		$.ajax({
			url:"/info/content/edit",
			dataType:"json",
			data:{
				id: id
			},
			success: function(json){
				console.log(json);
				if(json.flag == "1"){
					var info = json.data;
					$("#infoTitle").html(info.title);		//标题
					if(info.author){
						$("#author").append(info.author);		//作者
					}else{
						$("#author").hide();
					}
					if(info.creDeptName){
						$("#publishDept").append(info.creDeptName);		//发布部门
					}else{
						$("#publishDept").hide();
					}
					if(info.source){
						$("#source").append(info.source);//来源
					}else{
						$("#source").hide();
					}
					
					$("#creTime").append(info.creTime);	//时间
					$("#content").html(info.content);		//内容
					
					//附件
					MyFileUpload.init({
						viewParams: {"tableId":info.id,"tableName":"info_content"},
						editOrView:"VIEW",
						read:true,
						download:false
				    });
				}
			},
			error: function(){
				
			}
		})
	}
}

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
				$("#positon").attr("href","/gateway/infoList.html?columnCode="+columnCode);
				$("#positon").text(json.data.columnName);
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
