package com.sinosoft.sinoep.modules.zhbg.xxkh.test.dao;

import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.TestResultVo;
import java.util.List;

public interface TestRepository {

    /**
     * @Author 王富康
     * @Description //TODO 交卷后显示答案得到数据
     * @Date 2018/9/11 21:04
     * @Param [paperInfo, testId, paperId]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo
     **/
    List<TestResultVo> getTestResult(String id, String testId, String paperId, String userId);
}
