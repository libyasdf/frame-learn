var workitemid="";
$(function(){
	var api = frameElement.api, W = api.opener;
	workitemid=api.data.workitemid;
	if(workitemid!=null&&workitemid!=""){
		add_DelHq_(workitemid);
	}
	/*
	格式化时间日期
	 */
	Date.prototype.Format = function(fmt) { //author: meizz 
		var o = {
			"M+" : this.getMonth() + 1, //月份 
			"d+" : this.getDate(), //日 
			"h+" : this.getHours(), //小时 
			"m+" : this.getMinutes(), //分 
			"s+" : this.getSeconds(), //秒 
			"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
			"S" : this.getMilliseconds()
		//毫秒 
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1,(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
})
//进行增加删除会签操作
function add_DelHq_(id){
	$.ajax({
		   type: "POST",
		   url: basePath+"/operationcs/addDel",
		   data:{
			   workitemid:id  
		   },
		   async: false,
		   dataType:"json",
		   success: function(data){
			   var detail=eval(data.json);
			   var adddetail=eval(data.jsonaddhq);
			   var deldetail=eval(data.jsondelhq);
			   var view="";
			   var view2="";
			   for(var j = 0;j<detail.length;j++){
				   $("#userdetsail").html("<input type='radio' id='startHqWfleve' name='startHqWfleve' value='"+detail[j].id+"'  onclick='changeWfleve("+j+");'/><span>["+detail[j].name+"]在["+detail[j].time+"]发起的会签</span>");
				}
			   for(var j = 0;j<adddetail.length;j++){
				   view+="<tr><td><input type='checkbox' id='addHq_"+j+"' name='addHqNamen' disabled='disabled' value='"+adddetail[j].id+"'/>"+adddetail[j].name
				   		"<input type='hidden' id='addHqName' name='addHqName' value='"+adddetail[j].name+"' /></td></tr>";
			   }
			   $("#kaddhq").html(view);
			   for(var j = 0;j<deldetail.length;j++){
				   view2+="<tr><td><input type='checkbox' id='delHq_"+j+"' name='delHqNamen' disabled='disabled' value='"+deldetail[j].id+"'/>"+deldetail[j].name
				   		"<input type='hidden' id='delHqName' name='delHqName' value='"+deldetail[j].name+"' /></td></tr>";
			   }
			   $("#kdelhq").html(view2);
		   }
		});
}
//增删除会签分支页面
function add_DelHqYM(id,add,del,callBack){
	$.ajax({
		   type: "POST",
		   url: basePath+"/operationcs/add_Del",
		   data:{
			   workitemid:id,
			   delHq:del,
			   addHq:add
		   },
		   async: false,
		   dataType:"json",
		   success: function(data){
			   callBack(data);
		   }
		});
}
//页面操作方法
function changeWfleve(num){
	$("input[name='addHqNamen']").each(function(){ 
		$(this).attr("disabled",false);
	});
	$("input[name='delHqNamen']").each(function(){ 
		$(this).attr("disabled",false);
	});
}
function addDelSubmit(){
	var add="";
	var del="";
	for(var i=0;i < document.getElementsByName('addHqNamen').length;i++)
	{
		  if(document.getElementsByName('addHqNamen')[i].checked){
			
		  	if(add=='') add += document.getElementsByName('addHqNamen')[i].value;
		  	else add += ',' + document.getElementsByName('addHqNamen')[i].value;
		  }
	}
	for(var i=0;i < document.getElementsByName('delHqNamen').length;i++)
	{
		  if(document.getElementsByName('delHqNamen')[i].checked){
			
		  	if(del=='') del += document.getElementsByName('delHqNamen')[i].value;
		  	else del += ',' + document.getElementsByName('delHqNamen')[i].value;
		  }
	}
	/*$("input[id*='addHq']").each(function(){ 
		if($(this).attr("checked")==true){
			if(add==""){
				add = $(this).val();
			}else{
			   add = add+","+$(this).val();
			}
		}
	});*/
	/*$("input[id*='delHq']").each(function(){ 
		if($(this).attr("checked")==true){
			if(del==""){
				del = $(this).val();
			}else{
				del = del+","+$(this).val();
			}
		}
	});*/
	if(add!="" || del!=""){
            var demo = function(data){
            	if(data.res=='1'){
 				   return true;
 			   }else if(data.res=='强制删除会签分支成功！'){
 				   return true;
 			   }else{
 				   return false;
 			   }
            }
            add_DelHqYM(workitemid,add,del,demo);
	}else{
		showDialogAlert("请选择操作的分支",false);
		return false;
	}
}
