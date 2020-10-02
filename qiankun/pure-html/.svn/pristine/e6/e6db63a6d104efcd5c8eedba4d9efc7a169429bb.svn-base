var paperId;
var testId;
var userId;
$(function () {
	var theRequest = GetRequest();
	 testId = theRequest.testId;
	 paperId = theRequest.paperId;
	 userId = theRequest.userId;
    $.get("/zhbg/xxkh/test/getTestResult",{id:paperId,testId:testId,paperId:paperId,userId:userId,questionType:"5"}, function (data) {
      // alert(JSON.stringify(data))
    	console.log(data)
    	// 初始化评卷面板
        $markQuestion.init(data.data);

        // 给按钮绑定方法
        $('.mark-footer [name="save"]').click(clickSaveButton);
        $('.mark-footer [name="submit"]').click(clickSubmitButton);
        $('.mark-footer [name="close"]').click(clickCloseButton);
    },"json");
});

/*
** 评卷面板组件
** @$markQuestionListItem   { String }      面板DOM
** @init                    { Function }    初始化的方法
** @data                    { Object }      初始化方法调用的参数，从接口获取
*/
var $markQuestion = function () {
    var $markQuestionListItem = $(''
        + '<div class="mark-paper">'
        + '<div class="row">'
        + '<div class="col-md-12 ">'
        + '<div class="block_title">'
        + '<span class="name">问题一 （20分）</span>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '<br>'
        + '<div class="form-group">'
        + '<label>问题：</label>'
        + '<div class="col-md-10">'
        + '<textarea></textarea>'
        + '</div>'
        + '</div>'
        + '<div class="form-group">'
        + '<label>考生答案：</label>'
        + '<div class="col-md-10 a">'
        + '<textarea></textarea>'
        + '</div>'
        + '</div>'
        + '<div class="form-group">'
        + '<label>参考答案：</label>'
        + '<div class="col-md-10">'
        + '<textarea></textarea>'
        + '</div>'
        + '</div>'
        + '<div class="form-group">'
        + '<label><span style="color:red">* </span>评分：</label>'
        + '<div class="col-md-3">'
        + '<input class="form-control" type="number"/>'
        + '</div>'
        + '</div>'
        + '</div>');

    function init(data) {
        console.log(data);
        for (var i = 0; i < data.group[0].testResultList.length; ++i) {
            var $markQuestionListItemClone = $markQuestionListItem.clone();
            $markQuestionListItemClone.find('.block_title .name').html('题目' + ConvertIntNumberToChinese(i + 1) + ' （' + data.group[0].everyScore + '分）');
            $markQuestionListItemClone.find('textarea:eq(0)').val(data.group[0].testResultList[i].describe);
            $markQuestionListItemClone.find('textarea:eq(1)').val(data.group[0].testResultList[i].optionContent);
            $markQuestionListItemClone.find('textarea:eq(2)').val(data.group[0].testResultList[i].analysis);
            $markQuestionListItemClone.find('textarea').attr('rows', 3).attr('readonly', 'readonly').addClass('form-control');
            $markQuestionListItemClone.find('label').addClass('col-md-2').addClass('control-label');
            $markQuestionListItemClone.find('[type="number"]').val(data.group[0].testResultList[i].score)
                .attr('name', data.group[0].paperId + '#' + data.group[0].testResultList[i].id + '#' + data.group[0].testResultList[i].answerId)
                .attr('min', 0)
                .attr('max',data.group[0].everyScore-0)
                .keypress(validateKey);
            $('.container-fluid .row:eq(0) div:eq(0)').append($markQuestionListItemClone);
        }
    }

    // 字符级数据合法性校验
    function validateKey(e) {
        if (/[a-zA-Z`~!@#$%^&*()=_+\[\]{}|;:'",<>/?\\-]/.test(e.key)) e.preventDefault();
    }

    return {
        init: init
    };
}();

// 点击暂存按钮的回调函数
function clickSaveButton(o) {
    var group = [];
    
    for (var i = 0; i < $('.mark-content .mark-paper [type="number"]').length; ++i) {
        var markItem = {};
        var $score = $('.mark-content .mark-paper:eq(' + i + ') [type="number"]');
        var score = $score.val();
        
        var score = Number($score.val());
        var min = Number($score.attr('min'));
        var max = Number($score.attr('max'));
        
       /* var min = $score.attr('min');
        var max = $score.attr('max');*/
        if(score >= min && score <= max) {
            markItem.score = $score.val();
        } else if(score > max) {
            // 提示信息，可以用layui或bootstrap的提示框
        	layer.msg("评分不得大于该题满分", {
				icon : 0
			});
            $score.focus(); 
            return undefined;
        } else if (score < min) {
            // 提示信息，可以用layui或bootstrap的提示框
        	layer.msg("不可以填写负数", {
				icon : 0
			});
            $score.focus();
            return undefined;
        }
        
        var infoList = $('.mark-content .mark-paper:eq(' + i + ') [type="number"]').attr('name');
        //markItem.score = $('.mark-content .mark-paper:eq(' + i + ') [type="number"]').val();
        markItem.paperId = infoList.split('#')[0];
        markItem.questionId = infoList.split('#')[1];
        markItem.id = infoList.split('#')[2];
        group.push(markItem);
    }
    console.log(group);
    //提交数据到后台
    $.ajax({
        type: "POST",
        url:"/zhbg/xxkh/test/saveSortAnswer",
        contentType:"application/json;charsetset=UTF-8",
        data:JSON.stringify(group),
        dataType:"json",
        success:function(data){
            if ('1' == data.flag) {
            	if(o==0){}else{
                layer.msg("保存成功！", {icon: 1,time:1000});
                //parent.location.reload(); 
            	}
            }
        },
        error:function(data){
            layer.msg("保存失败！", {icon: 0,time:1000});
        }
    });
}
//点击提交按钮的回调函数
function clickSubmitButton() {
    var group = [];
    var sumScore = 0;
    for (var i = 0; i < $('.mark-content .mark-paper [type="number"]').length; ++i) {
        var markItem = {};
        var $score = $('.mark-content .mark-paper:eq(' + i + ') [type="number"]');
        var score = $score.val();
        if(score==""){
        	layer.msg("评分不得为空", {
				icon : 0
			});
        	$score.focus(); 
            return undefined;
        }
         score = Number($score.val());
        var min = Number($score.attr('min'));
        var max = Number($score.attr('max'));
        
     /*   var min = $score.attr('min');
        var max = $score.attr('max');
       */
        if(score >= min && score <= max) {
            markItem.score = score;
        } else if(score > max) {
            // 提示信息，可以用layui或bootstrap的提示框
        	layer.msg("评分不得大于该题满分", {
				icon : 0
			});
            $score.focus(); 
            return undefined;
        } else if (score < min) {
            // 提示信息，可以用layui或bootstrap的提示框
        	layer.msg("不可以填写负数", {
				icon : 0
			});
            $score.focus();
            return undefined;
        }
        var infoList = $('.mark-content .mark-paper:eq(' + i + ') [type="number"]').attr('name');
        sumScore += markItem.score = $('.mark-content .mark-paper:eq(' + i + ') [type="number"]').val()-0;
        markItem.paperId = paperId;
        markItem.questionId = infoList.split('#')[1];
        markItem.answerId = infoList.split('#')[2];
        group.push(markItem);
    }
    console.log(group);
    clickSaveButton(0);
    //提交数据到后台
    $.ajax({
        type: "get",
        url:"/zhbg/xxkh/testmanage/updatePingJuan",
        data:{paperId:paperId,testId:testId,userId:userId,paperSubjectiveScore:sumScore},
        dataType:"json",
        success:function(data){
            if ('1' == data.flag) {
                //parent.TableInit.refTable('right_table');
               // parent.location.reload();  
                layer.msg("评卷成功！", {icon: 1,time:1000});
                $(window.parent.$("#left"))[0].contentWindow.shuaxing();
                $('.mark-footer [name="save"]').hide();
                $('.mark-footer [name="submit"]').hide();
                $("#close").show();
            }
        },
        error:function(data){
            layer.msg("保存失败！", {icon: 0,time:1000});
        }
    });
}
function clickCloseButton(){
	MyLayer.closeOpen();
}

/*
** 将整数转换为汉字
** @section     { Number, String }      传入的整数，可以是字符串或者数字类型
** @return      { String }              转化成的汉字
*/
function ConvertIntNumberToChinese(section) {
    var section = Number(section);
    var chnNumChar = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
    var chnUnitSection = ["", "万", "亿", "万亿", "亿亿"];
    var chnUnitChar = ["", "十", "百", "千"];
    var strIns = '', chnStr = '';
    var unitPos = 0;
    var zero = true;
    while (section > 0) {
        var v = section % 10;
        if (v === 0) {
            if (!zero) {
                zero = true;
                chnStr = chnNumChar[v] + chnStr;
            }
        } else {
            zero = false;
            strIns = chnNumChar[v];
            strIns += chnUnitChar[unitPos];
            chnStr = strIns + chnStr;
        }
        unitPos++;
        section = Math.floor(section / 10);
    }
    return chnStr;
}