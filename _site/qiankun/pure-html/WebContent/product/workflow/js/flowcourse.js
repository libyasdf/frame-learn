var getFlowStatusURL="/workflow/getflowcourse";
var api = frameElement.api, W = api.opener;
var fileTypeId=!api.data.workflowid?api.data.filetypeid:api.data.workflowid;
var recordId=api.data.recordid;

if(!fileTypeId || !recordId){
	$('table#flowcourse_table').append('<tr style="font-size:13px;" height="20"><td colspan="7" style="text-align: center;">暂无流转办理进度！</td></tr>');
}else {
	httpRequest("GET",getFlowStatusURL+"?fileTypeId="+fileTypeId+"&recordId="+recordId,"",function(data){
		if(data){
	        //获取后台返回的数据渲染到页面
			var str="";
	        for(var i=0;i<data.length;i++){
	            var flowcourses = data[i];
	            var trCsName = i%2 == 0 ? "onetrclass" : 'twotrclass';
	            var flag = flowcourses.receivewfName ? '-->'+flowcourses.receivewfName:'';
	            var flags = flowcourses.staytime ? flowcourses.staytime:'';
	            str += '<tr class="'+trCsName+'"><td height="15" align="center" valign="middle" >'+(parseInt(i)+1)+'</td>'
	                +'<td height="15" align="center" valign="middle">'+flowcourses.sendtime+'</td>'
	                +'<td height="15" align="left" valign="middle">'+flowcourses.sendname+'</td>'
	                +'<td height="15" align="left" valign="middle">'+flowcourses.receivename+'</td>'
	                +'<td height="15" align="left" valign="middle">'+flowcourses.operatename+'</td>'
	                +'<td height="15" align="center" valign="middle">'+flowcourses.wflevename+flag+'</td>'
	                +'<td height="15" align="center" valign="middle">'+flags+'</td>'
	                +'</tr>';
	        }
	        if (data.length<1) {
	            $('table#flowcourse_table').append('<tr style="font-size:13px;" height="20"><td colspan="7" style="text-align: center;">暂无流转办理进度！</td></tr>');
	        }else{
	            $('table#flowcourse_table').append(str);
	        }
	        

		}
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
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null){
    	return unescape(r[2]); 
    } 
    return "";
}
