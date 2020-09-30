package com.sinosoft.sinoep.modules.zhbg.xxkh.test.dao;

import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.TestResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class TestDaoImpl implements TestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Author 王富康
     * @Description //TODO 交卷后显示答案得到数据
     * @Date 2018/9/11 21:04
     * @Param [paperInfo, testId, paperId]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo
     **/
    @Override
    public List<TestResultVo> getTestResult(String id,String testId,String paperId,String userId) {

        String sql = "  select i.* , s.option_content as optionContent, s.option_id as optionId, s.score, s.id as answerId " +
                "   from (select * from xxkh_question_qgroup q where q.question_group_id ='"+id+"') t, xxkh_question_info i, xxkh_answer s" +
                "   where t.question_id = i.id " +
                "   and t.question_id = s.question_id " +
                "   and s.test_id = '"+testId+"' " +
                "   and s.paper_id = '"+paperId+"'" +
                "   and s.cre_user_id = '"+userId+"'" +
                "   order by i.id asc";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TestResultVo.class));
    }
}
