package com.sinosoft.sinoep.modules.zhbg.xxkh.test.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.services.TestService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 王富康
 * @Description //TODO 参加考试的Controller
 * @Date 2018/9/4 16:05 
 * @Param 
 * @return 
 **/
@Controller
@RequestMapping("/zhbg/xxkh/test")
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService testService;

    /**
     * @Author 王富康
     * @Description //TODO 保存答题的数据
     * @Date 2018/9/6 20:23
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer
     **/
    @LogAnnotation(value = "save",opName = "保存答题的数据")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(@RequestBody List<Answer> answerlist) {

        // 每道题一条数据，需要接受的数据为 ：
        // 考试id (testId);
        // 试卷id(paperId);
        // 试题id(questionId);
        // 答题者填写/选择的答案的串，以$分割(optionContent);如果是填空题，那么则需要填写这个字
        // 答题者填写的答案id的串，以$分割(optionId);如果是单选，多选，判断题，则填写这个字段，存1,2,3,4，..等
        // 题型
        JSONObject json = new JSONObject();

        try {
            List<Answer> answerList= testService.save(answerlist);
            json.put("flag", 1);
            json.put("data", answerList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 暂存简答题分数
     * @Date 2018/9/18 15:22
     * @Param [answerlist]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "暂存简答题分数")
    @RequestMapping(value = "saveSortAnswer", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveSortAnswer(@RequestBody List<Answer> answerlist) {

        // 每道题一条数据，需要接受的数据为 ：
        // 考试id (testId);
        // 试卷id(paperId);
        // 试题id(questionId);
        // 答题者填写/选择的答案的串，以$分割(optionContent);如果是填空题，那么则需要填写这个字
        // 答题者填写的答案id的串，以$分割(optionId);如果是单选，多选，判断题，则填写这个字段，存1,2,3,4，..等
        // 题型
        JSONObject json = new JSONObject();

        try {
            testService.saveSortAnswer(answerlist);
            json.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 交卷后显示答案得到数据
     * @Date 2018/9/11 21:03
     * @Param [paperInfo, testId, paperId]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "交卷后显示答案得到数据")
    @RequestMapping("getTestResult")
    @ResponseBody
    public JSONObject getTestResult(XxkhPaperInfo paperInfo,String testId,String userId, String questionType){
        JSONObject json = new JSONObject();
        json.put("flag", "1");
        XxkhPaperInfo one = testService.getTestResult(paperInfo,testId,userId, questionType);
        json.put("data", one);
        return json;
    }

    //用于刷新session的返回值，用同一个，防止资源浪费
    private static Map<String, Object> timedTaskMap = new HashMap<>();

    /**
     * @Author 王富康
     * @Description //TODO 定时任务,防止session过期
     * @Date 2018/9/20 20:17
     * @Param [request]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @LogAnnotation(value = "query",opName = "定时任务,防止session过期")
    @RequestMapping("timedtask")
    @ResponseBody
    public Map<String, Object> timedTask(HttpServletRequest request){
        HttpSession session = request.getSession();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(session.getLastAccessedTime());
        log.info("LastAccessedTime:"+format.format(date));
        log.info("session.getMaxInactiveInterval()"+session.getMaxInactiveInterval());
        return timedTaskMap;
    }
}
