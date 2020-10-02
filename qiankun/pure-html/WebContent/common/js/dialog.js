

//成功 或失败 提示框
function showDialogAlert(msg,isSuccess){
	$.dialog({
		time : 2,
		title: '提示',
		content: msg,
		zIndex :10003,
		icon: isSuccess?'success.gif':"alert.gif"
		});
	
}
