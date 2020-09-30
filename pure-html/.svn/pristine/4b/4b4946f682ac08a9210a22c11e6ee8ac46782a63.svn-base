var theRequest = GetRequest();
var id = theRequest.id;theRequest.attr ? theRequest.attr : "";
var testId = theRequest.testId ? theRequest.testId : "";
var isShowAnswer = theRequest.isShowAnswer ? theRequest.isShowAnswer : "";
var workItemId1 = theRequest.workItemId1 ? theRequest.workItemId1 : "";
var answerTime = theRequest.answerTime ? theRequest.answerTime : "";

var index="";
$(function () {

    $.ajaxSetup({
        async: false
    });

    answerTime = secondToDate(answerTime);
    $(".time").html(answerTime);
    // 获取数据
    $.getJSON("/zhbg/xxkh/xxkhPaperInfo/getById",{id:id}, function (data) {
        console.log(data);
        // 初始化答题须知，试卷名，考生信息
         //$examNote.init(data.data.testNotice);
        $paperName.init(data.data.name);
        // $examineeInfo.init();
        if (data.data.group.length) {
            for (var i in data.data.group) {
                loadQuestionFormQuestionType(data.data.group[i].questionType, data.data.group[i]);
            }
        } else {
            throw new Error("There are no question in the paper! Please check it.");
        }
        $('.exam_map ul li a').click(function (e) {

            e.preventDefault();

            var type = $(e.target).attr('href');
            var title = $(type + ' .red');
            var position = title.offset().top;

            $(document).scrollTop(position - 80);
        });
        $('.exam_top .btn').click(data.data, getAnswer);

        var timer = window.setInterval(function() {
            showTimeout(reduceTime($('.time').html()));
            if(!reduceTime($('.time').html())) window.clearInterval(timer);
        }, 1000);

// 显示计时器
        function showTimeout(timeStr) {
            $('.time').html(timeStr);
        }

// 减少时间
        function reduceTime(timeStr) {

            var hour = timeStr.split(':')[0];
            var min = timeStr.split(':')[1];
            var sec = timeStr.split(':')[2];

            if(hour === "00" && min === "05" && sec === "00") {
                //$('#timeTip').modal('show');
                layer.alert('考试即将结束，请抓紧时间！！',{icon:6,title:'温馨提示'})
            }

            debugger;
            //当分钟数最后一位为9或者4的时候取访问后台防止session失效；5分钟
            var minn = min.substring(1,min.length);
            if(minn ==="9" || minn === "4"){
                keepAlive();
            }

            if(hour === "00" && min === "00" && sec === "00") {
                $('.pull-right button').trigger('click');
            }

            if (sec === "00") {
                if (min === "00") {
                    if (hour === "00") {
                        return false;
                    } else {
                        min = "59";
                        sec = "59";
                        hour = reduceNumStr(hour);
                    }
                } else {
                    sec = "59";
                    min = reduceNumStr(min);
                }
            } else {
                sec = reduceNumStr(sec);
            }

            return hour + ":" + min + ":" + sec;
        }

// 处理数字内容字符串
        function reduceNumStr(numStr) {

            numStr = String(Number(--numStr));
            if (numStr.length <= 1) numStr = "0" + numStr;

            return numStr;
        }
    });

});

// 根据题型加载题目
function loadQuestionFormQuestionType(questionType, group) {
    var questionType = Number(questionType);

    switch (questionType) {
        case 1:
            $singleChoise.init(group);
            loadNavFormQuestionType("single-choise", "单选题");
            break;
        case 2:
            $multipleChoice.init(group);
            loadNavFormQuestionType("multiple-choice", "多选题");
            break;
        case 3:
            $judgement.init(group);
            loadNavFormQuestionType("judgement", "判断题");
            break;
        case 4:
            $completion.init(group);
            loadNavFormQuestionType("completion", "填空题");
            break;
        case 5:
            $shortAnswer.init(group);
            loadNavFormQuestionType("short-answer", "简答题");
            break;
        default:
            throw new Error("Uncaught questionType: " + questionType);
            break;
    }
}

// 根据题型初始化导航栏
function loadNavFormQuestionType(questionBlockId, questionTypeStr) {
    $('.exam_map ul').append($('<li><a href="#' + questionBlockId + '">' + questionTypeStr + '</a></li>'));
}


// 答题须知
/*var $examNote = function() {
    var $dom = $(''
        +' <div class="exam_page_top">'
            //+'答题须知：<br> 1. 请用钢笔或签字笔在试卷上直接作答，并准确填写个人信息，每人限答一份。<br> 2. 本试卷为内部资料，由中科软保密办统一收回，个人不得复制、留存。<br> 3. 其他文字资料，文字提示语言，以实际为准。'
        +'</div>');

    function init(testNotice) {
        $dom.html(testNotice);
        $('.exam_page').prepend($dom);
    }

    return {
        init: init
    };
}();*/

// 试卷名
var $paperName = function() {
    var $dom = $('<div class="exam_page_h1"></div>');

    function init(name) {
        $dom.html(name);
        $('.exam_page').append($dom);
    }

    return {
        init: init
    }
}();

// 考生信息
var $examineeInfo = function() {
    var $dom = $(''
        +'<div class="exam_page_block">'
            +'<table width="90%" border="0" cellspacing="0" cellpadding="0" class="exam_page_block_table">'
                +'<tbody>'
                    +'<tr>'
                        +'<td width="20%" class="text-right">考生姓名：</td>'
                        +'<td width="30%">张三</td>'
                        +'<td width="20%" class="text-right">考试时间：</td>'
                        +'<td width="30%">2017年2月2日</td>'
                    +'</tr>'
                    +'<tr>'
                        +'<td class="text-right">部门职务：</td>'
                        +'<td>经理</td>'
                        +'<td class="text-right">身份证号：</td>'
                        +'<td>263882198602839283</td>'
                    +'</tr>'
                    +'<tr>'
                        +'<td class="text-right">联系电话：</td>'
                        +'<td>13433667788</td>'
                        +'<td class="text-right">答题时间：</td>'
                        +'<td>2小时</td>'
                    +'</tr>'
                +'</tbody>'
            +'</table>'
        +'</div>');

    function init() {
        $('.exam_page').append($dom);
    }

    return {
        init: init
    };
}();

// 单选题
var $singleChoise = function() {
    var $dom = $(''
        +'<div class="single-choise" id="single-choise">'
            +'<div class="exam_page_h2">'
                +'<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> '
                +'<b class="red">单选题</b>'
                +'<span></span>'
            +'</div>'

            +'<div class="exam_page_block"></div>'
        +'</div>');

    var $question = $(''
        +'<div class="exam_one_question">'
            +'<div class="exam_subject"></div>'
            +'<div class="exam_answer"></div>'
        +'</div>');

    var $option = $(''
        +'<div class="radio">'
            +'<label>'
                +'<input type="radio" name="" value="">'
                +"<span>A Option one is this and that&mdash;be sure to include why it's great</span>"
            +'</label>'
        +'</div>');

    // 初始化
    function init(data) {
        $dom.find('.exam_page_h2 span:eq(1)').html('（共' + data.questionCount + '题，每题' + data.everyScore + '分）');


        for(var i = 0; i < data.question.length; ++i) {

            // 添加题目
            var $questionClone = $question.clone();
            $questionClone.find('.exam_subject').html(i+1+"."+data.question[i].describe);
            $questionClone.attr("name", data.question[i].id);
            $dom.find('.exam_page_block').append($questionClone);

            for(var j = 0; j < data.question[i].list.length; ++j) {

                // 添加选项
                var $optionClone = $option.clone();
                $optionClone.find('label input')
                    .attr("name", data.question[i].id)
                    .attr("value", data.question[i].list[j].content);
                $optionClone.find('label span').html(convertNumberToLetter(j+1)+'. '+data.question[i].list[j].content);
                $dom.find('.exam_answer:eq(' + i + ')').append($optionClone);
            }
        }
        $('.exam_page').append($dom);
    }

    return {
        init: init
    };

}();

// 多选题
var $multipleChoice = function() {
    
    var $dom = $(''
        +'<div class="multiple-choice" id="multiple-choice">'
            +'<div class="exam_page_h2">'
                +'<span class="glyphicon glyphicon-list" aria-hidden="true"></span>'
                +'<b class="red"> 多选题</b>'
                +'<span></span>'
            +'</div>'

            +'<div class="exam_page_block"></div>'
        +'</div>');

    var $question = $(''
        +'<div class="exam_one_question">'
            +'<div class="exam_subject">2. 您最愿意接受应急救护的培训形式有：</div>'
            +'<div class="exam_answer"></div>'
        +'</div>');

    var $option = $(''
        +'<div class="checkbox">'
            +'<label>'
                +'<input type="checkbox" name="" value="">'
                +'<span></span>'
            +'</label>'
        +'</div>');

    // 初始化
    function init(data) {

        $dom.find('.exam_page_h2 span:eq(1)').html('（共' + data.questionCount + '题，每题' + data.everyScore + '分）');

        for(var i = 0; i < data.question.length; ++i) {

            // 添加题目
            var $questionClone = $question.clone();
            $questionClone.find('.exam_subject').html(i+1+"."+data.question[i].describe);
            $questionClone.attr("name", data.question[i].id);
            $dom.find('.exam_page_block').append($questionClone);

            for(var j = 0; j < data.question[i].list.length; ++j) {

                // 添加选项
                var $optionClone = $option.clone();
                $optionClone.find('label input').attr("value", data.question[i].list[j].content);
                $optionClone.find('label span').html(convertNumberToLetter(j+1)+'. '+data.question[i].list[j].content);
                $dom.find('.exam_answer:eq(' + i + ')').append($optionClone);
            }
        }
        $('.exam_page').append($dom);
    }

    return {
        init: init
    }
    
}();

// 判断题
var $judgement  = function() {
    
    var $dom = $(''
        +'<div class="judgement" id="judgement">'
            +'<div class="exam_page_h2">'
                +'<span class="glyphicon glyphicon-check"></span>'
                +'<b class="red"> 判断题</b>'
                +'<span></span>'
            +'</div>'

            +'<div class="exam_page_block"></div>'
        +'</div>');

    var $question = $(''
        +'<div class="exam_one_question">'
            +'<div class="exam_subject"></div>'
            +'<div class="exam_answer"></div>'
        +'</div>');

    var $option = $(''
        +'<label class="radio-inline">'
            +'<input type="radio" name="" value="">'
            +'<span></span>'
        +'</label>');

    // 初始化
    function init(data) {

        $dom.find('.exam_page_h2 span:eq(1)').html('（共' + data.questionCount + '题，每题' + data.everyScore + '分）');

        for(var i = 0; i < data.question.length; ++i) {

            // 添加题目
            var $questionClone = $question.clone();
            $questionClone.find('.exam_subject').html(i+1+"."+data.question[i].describe);
            $questionClone.attr("name", data.question[i].id);
            $dom.find('.exam_page_block').append($questionClone);

            for(var j = 0; j < data.question[i].list.length; ++j) {

                // 添加选项
                var $optionClone = $option.clone();
                $optionClone.find('input')
                    .attr("name", data.question[i].id)
                    .attr("value", data.question[i].list[j].content);
                $optionClone.find('span').html(convertNumberToLetter(j+1)+'. '+data.question[i].list[j].content);
                $dom.find('.exam_answer:eq(' + i + ')').append($optionClone);
            }
        }
        $('.exam_page').append($dom);
    }

    return {
        init: init
    };
}();

// 填空题
var $completion  = function() {
    
    var $dom = $(''
        +'<div class="completion" id="completion">'
            +'<div class="exam_page_h2">'
                +'<span class="glyphicon glyphicon-check"></span>'
                +'<b class="red"> 填空题</b>'
                +'<span></span>'
            +'</div>'

            +'<div class="exam_page_block"></div>'
        +'</div>');

    var $question = $(''
        +'<div class="exam_one_question">'
            +'<div class="exam_subject"></div>'
            +'<div class="exam_answer"></div>'
        +'</div>');

    var $option = $('<input class="line_input" type="text" name="" maxlength="500"><span> ， </span>');

    // 初始化
    function init(data) {

        $dom.find('.exam_page_h2 span:eq(1)').html('（共' + data.questionCount + '题，每题' + data.everyScore + '分）');

        for(var i = 0; i < data.question.length; ++i) {

            // 添加题目
            var $questionClone = $question.clone();
            $questionClone.find('.exam_subject').html(i+1+"."+data.question[i].describe);
            $questionClone.attr("name", data.question[i].id);
            $dom.find('.exam_page_block').append($questionClone);
            $dom.find('.exam_answer:eq('+i+')').prepend($('<span>答案：</span>'));

            for(var j = 0; j < data.question[i].list.length; ++j) {

                // 添加选项
                var $optionClone = $option.clone();

                if(j == data.question[i].list.length-1) $($optionClone[1]).html("。");
                $optionClone.find('span').html(convertNumberToLetter(j+1)+'. '+data.question[i].list[j].content);
                $dom.find('.exam_answer:eq(' + i + ')').append($optionClone);
            }
            //$question.find('input').attr('maxlength',500);
        }
        $('.exam_page').append($dom);
    }

    return {
        init: init
    };
}();

// 简答题
var $shortAnswer  = function() {
    
    var $dom = $(''
        +'<div class="short-answer" id="short-answer">'
            +'<div class="exam_page_h2">'
                +'<span class="glyphicon glyphicon-edit"></span>'
                +'<b class="red" >简答题</b>'
                +'<span></sapn>'
            +'</div>'

            +'<div class="exam_page_block"></div>'
        +'</div>');

    var $question = $(''
        +'<div class="exam_one_question">'
            +'<div class="exam_subject"></div>'
            +'<div class="exam_answer"></div>'
        +'</div>');

    var $option = $('<textarea class="form-control" rows="3" maxlength="1000"></textarea>');

    // 初始化
    function init(data) {

        $dom.find('.exam_page_h2 span:eq(1)').html('（共' + data.questionCount + '题，每题' + data.everyScore + '分）');

        for(var i = 0; i < data.question.length; ++i) {

            // 添加题目
            var $questionClone = $question.clone();
            $questionClone.find('.exam_subject').html(i+1+"."+data.question[i].describe);
            $questionClone.attr("name", data.question[i].id);
            $dom.find('.exam_page_block').append($questionClone);

            // 添加选项
            var $optionClone = $option.clone();
            $dom.find('.exam_answer:eq(' + i + ')').append($optionClone);
        }
        $('.exam_page').append($dom);
    }

    return {
        init: init
    };
}();

/**
 * @Author 王富康
 * @Description //TODO 这个方法判断是人工点击交卷还是js调用交卷
 * @Date 2018/9/20 14:35
 **/
function getAnswer(event) {

    if (event.originalEvent) {//event&&event.target==event.currentTarget---event.currentTarget === $(this)
        //用户点击的
        layer.confirm("是否确认交卷？",{icon:3},function(e){
            layer.close(e);
            index = layer.load(1,{shade: [0.5, '#393D49'],content: '正在交卷中，请稍候',success: function(layero){
                    layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'12px','width':'200px','color':'#000000'});
                } });
            submitTest(event);
        });

    } else {
        index = layer.load(1,{shade: [0.5, '#393D49'],content: '正在交卷中，请稍候',success: function(layero){
                layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'12px','width':'200px','color':'#000000'});
            } });
        // JS代码调的
        submitTest(event);
    }

}


/**
 * @Author 王富康
 * @Description //TODO 提交试卷
 * @Date 2018/9/20 14:37
 * @Param
 * @return
 **/
function submitTest(event){

    //Loading.open();

    console.log(event.data);

    var answerList = new Array();

    // 单选题
    var $pageBlock = $('#single-choise .exam_page_block');
    if($pageBlock.length) {
        for(var i = 0; i < $pageBlock.find('.exam_answer').length; ++i) {
            var $answer = $pageBlock.find('.exam_answer:eq(' + i + ')');

            var text = "";
            var questionId = $pageBlock.find('.exam_one_question:eq('+i+')').attr("name");
            var questionScore = $('#single-choise .exam_page_h2 span:eq(1)').html().split("题")[2].split("分")[0];

            for(var j = 0; j < $answer.children().length; ++j) {
                if($answer.find('[type="radio"]:eq(' + j + ')').is(':checked')) {
                    text += (j+1);

                    break;
                }
            }
            var answer = {
                paperId: event.data.id,//试卷id
                //name: event.data.name,
                questionType:"singleChoise",
                questionId:questionId,
                optionId:text,//单选，判断存optionId
                testId:testId,
                score:questionScore
            };
            answerList.push(answer);
        }
    }

    // 多选题
    var $pageBlock = $('#multiple-choice .exam_page_block');
    if($pageBlock.length) {
        for(var i = 0; i < $pageBlock.find('.exam_answer').length; ++i) {
            var $answer = $pageBlock.find('.exam_answer:eq(' + i + ')');

            var text = "";
            var questionId = $pageBlock.find('.exam_one_question:eq('+i+')').attr("name");
            var questionScore = $('#multiple-choice .exam_page_h2 span:eq(1)').html().split("题")[2].split("分")[0];

            for(var j = 0; j < $answer.children().length; ++j) {
                if($answer.find('[type="checkbox"]:eq(' + j + ')').is(':checked')) {
                    text += (j+1) + "#";
                }
            }
            text = text.slice(0, text.length-1);
            var answer = {
                paperId: event.data.id,//试卷id
                //name: event.data.name,
                questionType:"multipleChoise",
                questionId:questionId,
                optionId:text,//填空，简答存optionContent
                testId:testId,
                score:questionScore
            };
            answerList.push(answer);
        }
    }

    // 判断题
    var $pageBlock = $('#judgement .exam_page_block');
    if($pageBlock.length) {
        for(var i = 0; i < $pageBlock.find('.exam_answer').length; ++i) {
            var $answer = $pageBlock.find('.exam_answer:eq(' + i + ')');

            var text = "";
            var questionId = $pageBlock.find('.exam_one_question:eq('+i+')').attr("name");
            var questionScore = $('#judgement .exam_page_h2 span:eq(1)').html().split("题")[2].split("分")[0];

            for(var j = 0; j < $answer.children().length; ++j) {
                if($answer.find('[type="radio"]:eq(' + j + ')').is(':checked')) {
                    text += (j+1);
                    //answer.judgement.push(text);
                    break;
                }
            }

            var answer = {
                paperId: event.data.id,//试卷id
                //name: event.data.name,
                questionType:"judgement",
                questionId:questionId,
                optionId:text,//单选，判断存optionId
                testId:testId,
                score:questionScore
            };
            answerList.push(answer);
        }
    }

    // 填空题
    var $pageBlock = $('#completion .exam_page_block');
    if($pageBlock.length) {
        for(var i = 0; i < $pageBlock.find('.exam_answer').length; ++i) {
            var $answer = $pageBlock.find('.exam_answer:eq(' + i + ')');

            var text = "";
            var questionId = $pageBlock.find('.exam_one_question:eq('+i+')').attr("name");
            var questionScore = $('#completion .exam_page_h2 span:eq(1)').html().split("题")[2].split("分")[0];

            for(var j = 0; j < $answer.find('[type="text"]').length; ++j) {
                text += $answer.find('[type="text"]:eq(' + j + ')').val().trim() + "#";
            }
            text = text.slice(0, text.length-1);
            //answer.completion[i] = { questionId: questionId, answer: text };
            var answer = {
                paperId: event.data.id,//试卷id
                questionType:"completion",
                questionId:questionId,
                optionContent:text,//多选，填空，简答存optionContent
                testId:testId,
                score:questionScore
            };
            answerList.push(answer);
        }
    }

    // 简答题
    var $pageBlock = $('#short-answer .exam_page_block');
    if($pageBlock.length) {
        for(var i = 0; i < $pageBlock.find('.exam_answer').length; ++i) {
            var $answer = $pageBlock.find('.exam_answer:eq(' + i + ')');

            var text = "";
            var questionId = $pageBlock.find('.exam_one_question:eq('+i+')').attr("name");
            var questionScore = $('#short-answer .exam_page_h2 span:eq(1)').html().split("题")[2].split("分")[0];

            for(var j = 0; j < $answer.find('textarea').length; ++j) {

                console.log($answer.find('textarea:eq(' + j + ')').val());

                text += $answer.find('textarea:eq(' + j + ')').val().trim();
            }

            var answer = {
                paperId: event.data.id,//试卷id
                //name: event.data.name,
                questionType:"shortAnswer",
                questionId:questionId,
                optionContent:text,//多选，填空，简答存optionContent
                testId:testId,
                score:questionScore
            };
            answerList.push(answer);
        }
    }


    console.log(answerList);

    //提交数据到后台
    $.ajax({
        type: "POST",
        url:"/zhbg/xxkh/test/save",
        contentType:"application/json;charsetset=UTF-8",
        data:JSON.stringify(answerList),
        dataType:"json",
        success:function(data){
            if ('1' == data.flag) {
                //删除待办
                deleteWaitNoflow();
                if("1"==isShowAnswer){
                    //closeIfram();
                    //Loading.clear();
                    //window.parent.TestResult();//调取aa函数
                    layer.close(index);
                    window.location.replace("/modules/zhbg/xxkh/test/testResult.html?id="+id+"&testId="+testId);
                }else{
                    //Loading.clear();
                    layer.close(index);
                    layer.alert('交卷成功，您可前往综合办公-学习考核-个人管理-成绩查询去查询本次考试详情', {icon: 1}, function(){
                        closeIfram();
                        //window.parent.closeIfram();//调取关闭按钮
                    })
                }

            }
        },
        error:function(data){
            layer.msg("保存失败！", {icon: 0,time:1000});
        }
    });
}
//根据待办id删除待办
function deleteWaitNoflow() {
    $.ajax({
        url:"/sysWaitNoflowController/deleteWaitNoflow",
        data:{"id":workItemId1},
        dataType:"json",
        success:function(result){
            if(result.flag=="1"){
                parent.refreshPage("daiban");//刷新待办列表
                parent.parent.refreshPage("daiban");//从更多进入考试时刷新待办列表
            }else{
            }
        }
    });
}

//关闭当前窗口
function closeIfram(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

// 将数字转换成字母
function convertNumberToLetter(number) {
    var number = Number(number);
    switch (number) {
        case 0:
            return "";
            break;
        case 1:
            return "A";
            break;
        case 2:
            return "B";
            break;
        case 3:
            return "C";
            break;
        case 4:
            return "D";
            break;
        case 5:
            return "E";
            break;
        case 6:
            return "F";
            break;
        case 7:
            return "G";
            break;
        case 8:
            return "H";
            break;
        case 9:
            return "I";
            break;
        case 10:
            return "J";
            break;
        case 11:
            return "K";
            break;
        default:
            throw new Error("Can not convert this number to letter!");
            break;
    }
}

//分钟数转为时分秒填到倒计时中
function secondToDate(result) {
    var h = Math.floor(result / 60) < 10 ? '0'+Math.floor(result / 60) : Math.floor(result / 60);
    var m = Math.floor((result % 60)) < 10 ? '0' + Math.floor((result % 60)) : Math.floor((result % 60));
    var s = "00";
    return result = h + ":" + m + ":" + s;
}
/**
 * @Author 王富康
 * @Description //TODO 请求后台刷新session
 * @Date 2018/9/20 20:19
 * @Param
 * @return
 **/
function keepAlive() {
    $.ajax({
        type: 'GET',
        url: "/zhbg/xxkh/test/timedtask",
        success: function(ret) {
            /*debugger;
            var jsonRet = JSON.parse(ret);
            console.dir(jsonRet);*/
        }
    });
}

/** 定时访问后台保持session*/
//setInterval(keepAlive, 1 * 60 * 1000);

/**
 * function setInterval(arg1, arg2)
 * @param {Function} callback
 * @param {Number} delay
 * @memberOf Window
 * @returns {Number}
 */
//Window.prototype.setInterval=function(callback, delay){return 0;};

/*window.onbeforeunload = function() {
    debugger;
//  这是用来设定一个时间, 时间到了, 就会执行一个指定的 method。
    setTimeout(onunloadcancel, 10);
    return "真的离开?";
}*/

/*function checkLeave(){
    debugger;
    event.returnValue="确定离开当前页面吗？";
}*/

    /* window.onbeforeunload = onbeforeunload_handler;
     window.onunload = onunload_handler;
    function onbeforeunload_handler(){
             var warning="确认退出?";
             return warning;
         }

     function onunload_handler(){
             var warning="谢谢光临";
             alert(warning);
        }*/
