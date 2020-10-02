var MessageUtil = function(){
	/**
	 * 发送消息参数
	 */
	var message = {
		senderId:Config.sysId,	//系统ID
		subject:"",		//消息主题
		content:"",		//消息内容
		accepterId:null,	//接收人ID
		status:"0",		//0未读；1已读
		type:"3",		//3站内消息
		msgUrl:""		//打开消息的url
	};


	/**
	 * 发送消息
	 * @param json
	 * @returns {Boolean}
	 */
	function sendMsg(json){
		var res = false;
		var _arg = $.extend(message, json);
		$.ajax({
			type:"post",
			url:"/notity/save",
			data:_arg,
			dataType:"json",
			success:function(res){
				if(res.flag == "1"){
					res = true;
				}
			},
			error:function(){
			}
		})
		return res;
	}
	
	var editArg = {
		id:"",
		success:null
	}
	
	/**
	 * 修改消息状态
	 */
	var editStatus = function(json){
		var _arg = $.extend(editArg, json);
		var flag = "0";
		if(_arg.id){
			$.ajax({
	    		type:"get",
	    		url:"/notity/edit",
	    		data:{id:_arg.id},
	    		dataType:"json",
	    		async:false,
	    		success:function(res){
	    			console.log(res);
	    			flag = res.flag;
	    			if(_arg.success){
	    				_arg.success();
	    			}
	    		},
	    		error:function(){
	    		}
	    	})
		}
		return flag;
	}
	
	return {
		sendMsg: function (argJson) {
			sendMsg(argJson);
        },
        editStatus: function(json){
        	return editStatus(json);
        }
    }
}();