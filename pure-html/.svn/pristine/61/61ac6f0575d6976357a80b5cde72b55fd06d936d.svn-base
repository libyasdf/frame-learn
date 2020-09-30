
//判断统计条件表单
//获取历年数据
//
function check(){
	var flag = false;
	var deptname = $("#title").val();
	var start = $("#startTime").val();
	var end = $("#endTime").val()
	if(((""!=deptname)&&(""!=start)&&(""!=end))||((""==deptname)&&(""==start)&&(""==end))){
		flag=true;
	}else{
		alert("三个条件都为必须项！");
	}
	return flag;
}
//获取历年
function getyear(){
	$.ajax({
		 type: "POST",
		 url: basePath+"/workflow/getyear",
		 success: function(data){
		     var view ="";
		     if(data.length>0){
		    	 for(var i in data){
		    		 view+="<label class=\"col-xs-2\" style=\"padding-top: 7px;\">"
		    			 +"<input type=\"checkbox\" name=\"year\" value=\""+data[i].year+"\" onclick=\"checkyear(this)\" >&nbsp;&nbsp;"
		    		     +data[i].year+"</label>";
		    	 }
		    	 $("#year_div").html(view);
		    	 $("#year_tr").css("display","table-row");
		     }
		 }
	})
}
//获取选择的历年
function getCheckYear(){
	var year="";
	$('input[name="year"]:checked').each(function(){
		year=$(this).val();
	});
	return year;
}
//历年只能选一个
function checkyear(ss){
	$('#year_div').find('input[type=checkbox][name="year"]').not(ss).attr("checked", false);
}
$(document).ready(function(){
	getyear();
	selectAll();
	
});
function selectAll(page){
	if(!check()){
		return;
	}else{
		var view = "";
		$.ajax({
			 type: "POST",
			 url: basePath+"/workflow/tjdept",
			 data: {
				 pageNum:getPageNum(page),
				 size:10,
				 username:$("#title").val(),
				 start:$("#startTime").val(),
				 end:$("#endTime").val(),
				 year:getCheckYear()
			 },
			 success: function(data){
				 var arr = data.recordList;
				 var indexNum = data.indexNum;
				 if(arr==null){
					 view += "<tr><td colspan='5' style='text-align:center'><span class='glyphicon glyphicon-info-sign' style='color:#0682E3'>暂无数据</span></td></tr>";
				 }else{
					 for ( var i in arr) {
					    	view += "<tr>"
								 + "<td style='text-align:center;vertical-align:middle;'>"+indexNum+"</td>"
								 + "<td style='text-align:center;vertical-align:middle;'>"+arr[i].signname+"</td>"
								 + "<td style='text-align:center;vertical-align:middle;'>"+arr[i].recordnum+"</td>"
								 + "<td style='text-align:center;vertical-align:middle;'>"+arr[i].dbnum+"</td>"
								 + "<td style='text-align:center;vertical-align:middle;'>"+arr[i].ybnum+"</td>"
								 + "</tr>"
					    indexNum++;
					 }
				 }
				 $("#content").html(view);
				 initPage(data);
			 }
		})
	}
	
}




