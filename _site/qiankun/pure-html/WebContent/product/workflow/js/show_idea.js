
var getShowIdeaURL="/workflow/getformalidea";	//获取正式意见
var getBackIdeaURL="/workflow/getBackIdea";		//获取退回意见
var api = frameElement.api, W = api.opener;
var recordid=api.data.recordid;
var filetypeid=!api.data.workflowid?api.data.filetypeid:api.data.workflowid;
var deptid=getcookie("deptid");
var userid=getcookie("userid");
if(!recordid){
	$('table#tableShowIdea').append('<tr style="font-size:13px;" height="20"><td colspan="6" style="text-align: center;">暂无意见信息！</td></tr>');
}else{

	httpRequest("GET",getShowIdeaURL+"?pkValue="+recordid+"&filetypeid="+filetypeid+"&deptid="+deptid+"&userid="+userid,"",function(data){
		//获取到后台返回的数据渲染到页面上
		var num = 0;
	    var trs = "";
		for ( var i in data) {
			for ( var j in data[i].ideaList) {
				if(data[i].ideaList!=null&&data[i].ideaList!=""){
					data[i].ideaList[j].sortname = data[i].note;
					var idea = data[i].ideaList[j];
		            var trCsName = num%2 == 0 ? "onetrclass" : 'twotrclass';
		            var tdCsName = idea.ideaFieldName=='BACK_IDEA'?'ideared':'';
		            trs += '<tr class="'+trCsName+'"><td height="15" align="center" valign="middle" >'+parseInt(++num)+'</td>'
		                +'<td class="'+tdCsName+'" height="15" align="center" valign="middle">'+idea.sortname+'</td>'
		                +'<td height="15" align="left" valign="middle">'+idea.idea+'</td>'
		                +'<td height="15" align="left" valign="middle">'+idea.username+'</td>'
		                +'<td height="15" align="left" valign="middle">'+idea.deptname+'</td>'
		                +'<td height="15" align="center" valign="middle">'+idea.ideatime+'</td>'
		                +'</tr>';
				}
			}
		}
		
	    httpRequest("GET",getBackIdeaURL+"?pkValue="+recordid+"&filetypeid="+filetypeid+"&deptid="+deptid+"&userid="+userid,"",function(data){
	    	for ( var i in data) {
	    		var idea = data[i];
			    var trCsName = num%2 == 0 ? "onetrclass" : 'twotrclass';
	            trs += '<tr class="'+trCsName+'"><td height="15" align="center" valign="middle" >'+parseInt(++num)+'</td>'
	                +'<td class="ideared '+tdCsName+'" height="15" align="center" valign="middle">退回意见</td>'
	                +'<td height="15" align="left" valign="middle">'+idea.idea+'</td>'
	                +'<td height="15" align="left" valign="middle">'+idea.username+'</td>'
	                +'<td height="15" align="left" valign="middle">'+idea.deptname+'</td>'
	                +'<td height="15" align="center" valign="middle">'+idea.ideatime+'</td>'
	                +'</tr>';
	    	}
	    	
	    	if (trs=='') {
		        $('table#tableShowIdea').append('<tr style="font-size:13px;" height="20"><td colspan="6" style="text-align: center;">暂无意见信息！</td></tr>');
		    }
		    $('table#tableShowIdea').append(trs);
	    });
	    
	   
	});
}


function httpRequest(method,url,data,callback){
	$.ajax({
        url : basePath + url,
        type : method,
        data : data,
        dataType : "json",
        success : function(data) {
            if(data){
                callback(data);
            }
        },
        error : function(error) {
        	showDialogAlert("获取失败，请刷新重试或联系管理员");
        }
    });
}
//tableShowIdea
