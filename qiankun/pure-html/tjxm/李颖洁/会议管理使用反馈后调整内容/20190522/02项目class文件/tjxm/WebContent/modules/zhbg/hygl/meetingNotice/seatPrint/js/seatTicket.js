$(function(){
	var theRequest = GetRequest();
	var noticeId = regVlaue(theRequest.meetingNoticeId);
	//var fankuiType = regVlaue(theRequest.fankuiType);
	var attendDeptId= regVlaue(theRequest.attendDeptId)
	//var remark = regVlaue(theRequest.remark);
	var attendDeptType = regVlaue(theRequest.attendDeptType);
	var userid = regVlaue(theRequest.userid);
	
	$.get("/zhbg/hygl/arrangeSeat/ticketList", { meetingNoticeId:noticeId,attendDeptId:attendDeptId,userid:userid, time: new Date().getTime() },function(data){
		debugger
		var list=data.data;
		var attentionItem=data.attentionItem;
		var id="ul"
		var idflag=id;
		var pageId=""
		for(var i=0;i<list.length;i++){
			//alert(data[i].state)
			//alert( (i%2 ==0) ?"偶数":"奇数");
			id+=i;
			var lis="";
			var pageDiv="";
			var noticeName = "";
			if(list[i].noticeName.length>30){
				noticeName = list[i].noticeName.substr(0,30);
			}else{
				noticeName = list[i].noticeName;
			}
			if(i%2==0){
				//为基数
				if(i%4==0){
					pageId='page'+i;
					pageDiv+="<div style='page-break-after: always;' id='" + pageId + "'>"
				}
				lis+="<ul class='cardList clearfix' id='" + id + "'>"
				idflag=id;
					id="ul"
					lis+="<li><div class='cardBox'>"
					lis+="<h2 class='title'>"+noticeName+"</h2>"
					lis+="<p class='position'><span class='lineBoder'>" + list[i].row + "</span>排<span class='lineBoder'>" + list[i].col + "</span>座</p>"
					if(list[i].owner){
						lis+="<p class='inforP'>姓名:<span class='meetingTime'>" + list[i].owner + "</span></p>"
					}
					var index = list[i].startDate.lastIndexOf("-");  
					lis+="<p class='inforP'>时间:<span class='meetingPlace'></span>" + list[i].startDate.substring(0, index) +"</p>"
					lis+="<p class='inforP'>地点:<span class='meetingPlace'></span>" + list[i].meetingRoom +"</p>"
					
					lis+="<p class='inforP clearfix'><span class='fl'>注意事项:</span><span class='meetingNotice fl'>" + attentionItem + "</span></p>"
					lis+="<span class='flower'><img src='img/huaL.png'></span>"	
					lis+="<span class='flower flowerR'><img src='img/huaR.png'></span>"	
					lis+="</div></li>"
			
				lis+="</ul>"
				if(i%4==0){
					pageDiv+="</div>"
						$("#ul").append(pageDiv);
					$("#"+pageId+"").append(lis)
					
				}else{
					$("#"+pageId+"").append(lis)
				}
				
				lis="";
			}else{
				//alert(idflag)
				//偶数
				    lis+="<li><div class='cardBox'>"
					lis+="<h2 class='title'>"+noticeName+"</h2>"
					lis+="<p class='position'><span class='lineBoder'>" + list[i].row + "</span>排<span class='lineBoder'>" + list[i].col + "</span>座</p>"
					if(list[i].owner){
						lis+="<p class='inforP'>姓名:<span class='meetingTime'>" + list[i].owner + "</span></p>"
					}
				    var index = list[i].startDate.lastIndexOf("-");  
					lis+="<p class='inforP'>时间:<span class='meetingPlace'></span>" + list[i].startDate.substring(0, index) +"</p>"
					lis+="<p class='inforP'>地点:<span class='meetingPlace'></span>" + list[i].meetingRoom +"</p>"
					
					lis+="<p class='inforP clearfix'><span class='fl'>注意事项:</span><span class='meetingNotice fl'>" + attentionItem + "</span></p>"
					lis+="<span class='flower'><img src='img/huaL.png'></span>"	
					lis+="<span class='flower flowerR'><img src='img/huaR.png'></span>"	
					lis+="</div></li>"
					$("#"+idflag+"").append(lis)
					lis="";
			}
			
			//$("#ul").append(lis)
			
			

		}
	} );
	
	//opener.fresh();

})





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

function publishForm(){ 
	var oldHtml;
	 bdhtml=window.document.body.innerHTML;
	 oldHtml=bdhtml;
   sprnstr="<!--startprint1-->";      
   eprnstr="<!--endprint1-->";      
   prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+18);      
   prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));      
   window.document.body.innerHTML=prnhtml;
   if (!!window.ActiveXObject || "ActiveXObject" in window) { //是否ie
	   remove_ie_header_and_footer();
	}
   window.print();
   window.document.body.innerHTML=oldHtml;   
   return false;
}

function remove_ie_header_and_footer() {
	  var hkey_path;
	  hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	  try {
	   var RegWsh = new ActiveXObject("WScript.Shell");
	   RegWsh.RegWrite(hkey_path + "header", "");
	   RegWsh.RegWrite(hkey_path + "footer", "");
	  } catch (e) {
	  }
}
