package com.sinosoft.sinoep.modules.zhbg.xxkh.test.services;

import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 参加考试Service层
 * @Date 2018/9/4 16:06
 * @Param
 * @return
 **/
public interface TestService {

    /**
     * @Author 王富康
     * @Description //TODO 保存答题的数据
     * @Date 2018/9/6 20:23
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer
     **/
    List<Answer> save(List<Answer> answerList);


    /**
     * @Author 王富康
     * @Description //TODO 暂存简答题分数
     * @Date 2018/9/18 15:22
     * @Param [answerlist]
     * @return net.sf.json.JSONObject
     **/
    int saveSortAnswer(List<Answer> answerList);

    /**
     * @Author 王富康
     * @Description //TODO 交卷后显示答案得到数据
     * @Date 2018/9/11 21:04
     * @Param [paperInfo, testId, paperId]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo
     **/
    XxkhPaperInfo getTestResult(XxkhPaperInfo paperInfo,String testId,String userId,String questionType);
}
