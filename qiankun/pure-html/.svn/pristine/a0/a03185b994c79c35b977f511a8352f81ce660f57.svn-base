//==================================================
//        数据分页JS_v2.0 2016-8-26
//	说明：需要一个id="pageDiv" 的DIV容器
//		查询业务数据方法：selectAll(pagenum,size);
//		@param pagenum	页码
//		@param size		每页数据条数
//================================================

/**
 * 加载分页信息
 * @param data  
 */
var pagesize=10;	//每页数据条数
function pageInt(data){
	
	var view="";
	view+="<ul class=\"pagination\">";
		if(data.totalResult>0){
			
			view+="<li><a>"+data.totalResult+"条记录"+data.currentPage+"/"+data.totalPage+"页</a></li>"
			if(data.currentPage==1){
				view+="<li class=\"previous\"><a> 首页</a></li>"
				+"<li><a>«</a></li>";
			}else{
				view+="<li style=\"cursor:pointer;\" class=\"previous\"><a onclick=\"getdata(1)\"> 首页</a></li>"
					+"<li style=\"cursor:pointer;\"><a onclick=\"getdata("+(data.currentPage-1)+")\">&laquo;</a></li>";
				}
			var showtag=5;//显示的页数
			var startag=1;//当前初始页
			if(data.currentPage>showtag){
				startag=data.currentPage-1;//1
			}
			var endtag = startag+showtag-1;//5
			for(var i=startag; i<=data.totalPage && i<=endtag; i++){
				if(data.currentPage==i){
					view+="<li class='active'><a><font color=\"#FFF\">"+i+"</font></a></li>";
					
				}else{
					view+="<li style=\"cursor:pointer;\"><a onclick=\"getdata("+i+")\"><font>"+i+"</font></a></li>";
				}
			}
			if(data.currentPage==data.totalPage){
				view+="	<li><a>»</a></li>"
				+"<li class='next'><a>尾页 </a></li>";
			}else{
				view+="	<li style=\"cursor:pointer;\" ><a onclick=\"getdata("+(data.currentPage+1)+")\">»</a></li>"
				+"	<li style=\"cursor:pointer;\" class='next'><a onclick=\"getdata("+data.totalPage+")\">尾页 </a></li>";
			}
			//加载后面的内容
			view+="<li class=\"next\"><a>每页</a></li>"
				+"<li><input id=\"pagesize\" type=\"text\" class=\"input\" value=\""+data.showCount+"\"></li>"
				+"<li class=\"next\"><a>条</a></li>"
				+"<li style=\"cursor:pointer;\"><a onclick=\"changeTab("+data.currentPage+","+data.showCount+")\">Go</a></li>"
				+"</ul>";
		}else{
			view+="<li ><a>0条记录 0/0页</a></li>";
				+"</ul>";
		}
		$("#pageDiv").html(view);

}
//跳到某页
function getdata(page){
	var size=$("#pagesize").val();
	selectAll(page,size);
}
//获取页数
function pagenum(page){
	if(!page){
		page = 1;
	}
	return page;
}
//获取条数
function getsize(){
	var _size=$("#pagesize").val();
	if(!_size){
		_size = pagesize;
	}
	return _size;
}
//切换每页多少条记录
function changeTab(pagenum,showcount){
	var size=$("#pagesize").val();
	if(validate(size)){
		selectAll(pagenum,size);
	}else{
		layer.msg("只能输入数字", {
			icon: 2
		});
		$("#pagesize").val(showcount);
	}
	
	
}
function validate(num){
	var g = /^[1-9]*[1-9][0-9]*$/;
	return  g.test(num);
}