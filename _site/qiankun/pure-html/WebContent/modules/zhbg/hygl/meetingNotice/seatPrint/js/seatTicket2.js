$(function(){
	var theRequest = GetRequest();
	var noticeId = regVlaue(theRequest.meetingNoticeId);
	var fankuiType = regVlaue(theRequest.fankuiType);
	var remark = regVlaue(theRequest.remark);
	var attendDeptType = regVlaue(theRequest.attendDeptType);
	alert(remark)
	
	
	
	$.get("/zhbg/hygl/arrangeSeat/ticketList", { meetingNoticeId:noticeId,attendDeptType:attendDeptType },function(data){
		console.info(data)
		var id="ul"
		var idflag=id;
		for(var i=0;i<data.length;i++){
			
			/*if(data[i].state==4){
				continue;
			}*/
			//alert( (i%2 ==0) ?"偶数":"奇数");
			id+=i;
			var lis="";
			if(i%2==0){
				//为基数
				lis+="<ul class='cardList clearfix' id='" + id + "'>"
				idflag=id;
					id="ul"
					lis+="<li><div class='cardBox'>"
					lis+="<h2 class='title'>"+data[i].noticeName+"</h2>"
					lis+="<p class='position'><span class='lineBoder'>" + data[i].row + "</span>排<span class='lineBoder'>" + data[i].col + "</span>座</p>"
					
					lis+="<div class='clearfix mescon'>"
					lis+="<p class='inforP marginTop fl'>时间:<span class='meetingTime'>" + data[i].startDate +"   " + data[i].startTime + "</span></p>"
					lis+="<p class='inforP fl'>地点:<span class='meetingPlace'></span>" + data[i].meetingRoom +"</p>"
					
						lis+="<p class='inforP fl'>部门:<span class='meetingTime'>" + data[i].attendDeptName + "</span></p>"
						if(fankuiType==0){
							lis+="<p class='inforP fl'>人员:<span class='meetingTime'>" + data[i].owner + "</span></p>"
						}
					lis+="</div>"
						
					lis+="<p class='inforNotice clearfix'><span class='fl'>注意事项:</span><span class='meetingNotice fl'>" + remark + "</span></p>"
					lis+="<span class='flower'><img src='img/huaL.png'></span>"	
					lis+="<span class='flower flowerR'><img src='img/huaR.png'></span>"	
					lis+="</div></li>"
			
				lis+="</ul>"
				$("#ul").append(lis);
				lis="";
			}else{
				//alert(idflag)
				//偶数
				    lis+="<li><div class='cardBox'>"
					lis+="<h2 class='title'>"+data[i].noticeName+"</h2>"
					
					lis+="<p class='position'><span class='lineBoder'>" + data[i].row + "</span>排<span class='lineBoder'>" + data[i].col + "</span>座</p>"
					lis+="<div class='clearfix mescon'>"
					lis+="<p class='inforP marginTop fl'>时间:<span class='meetingTime'>" + data[i].startDate +"   " + data[i].startTime + "</span></p>"
					lis+="<p class='inforP fl'>地点:<span class='meetingPlace'></span>" + data[i].meetingRoom +"</p>"
					
						lis+="<p class='inforP fl'>部门:<span class='meetingTime'>" + data[i].attendDeptName + "</span></p>"
						if(fankuiType==0){
							lis+="<p class='inforP fl'>人员:<span class='meetingTime'>" + data[i].owner + "</span></p>"
						}
				    lis+="</div>"
					lis+="<p class='inforNotice clearfix'><span class='fl'>注意事项:</span><span class='meetingNotice fl'>" + remark + "</span></p>"

					
					lis+="<span class='flower'><img src='img/huaL.png'></span>"	
					lis+="<span class='flower flowerR'><img src='img/huaR.png'></span>"	
					lis+="</div></li>"
					$("#"+idflag+"").append(lis)
					lis="";
			}
			
			$("#ul").append(lis)
			
			

		}
	} );

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
	 bdhtml=window.document.body.innerHTML;      
    sprnstr="<!--startprint1-->";      
    eprnstr="<!--endprint1-->";      
    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+18);      
    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));      
    window.document.body.innerHTML=prnhtml;   
    window.print();
}
