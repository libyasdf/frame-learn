package com.sinosoft.sinoep.modules.zhbg.xxkh.test.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.dao.XxkhPaperInfoDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.dao.QuestionDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import com.sinosoft.sinoep.modules.zhbg.xxkh.question.services.OptionService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.dao.XxkhQuestionGroupDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.serivces.XxkhQuestionGroupService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.dao.TestDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer;
import com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.TestResultVo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services.TestManageService;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 参加考试Service实现层
 * @Date 2018/9/4 16:08
 * @Param
 * @return
 **/
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Autowired
    private XxkhQuestionGroupService xxkhQuestionGroupService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private TestManageService testManageService;

    @Autowired
    private XxkhPaperInfoDao dao;

    @Autowired
    private XxkhQuestionGroupDao xxkhQuestionGroupDao;

    @Autowired
    private QuestionDao questionDao;

    /**
     * @Author 王富康
     * @Description //TODO 保存答题的数据
     * @Date 2018/9/6 20:23
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer
     **/
    public List<Answer> save(List<Answer> answerList){
        List<Answer> answerlist = new ArrayList<>();
        int totalPoints = 0;//客观题总分
        for(int i = 0;i < answerList.size();i++){

            //加入一些必要字段信息
            answerList.get(i).setCreUserId(UserUtil.getCruUserId());
            answerList.get(i).setCreUserName(UserUtil.getCruUserName());
            answerList.get(i).setCreDeptId(UserUtil.getCruDeptId());
            answerList.get(i).setCreDeptName(UserUtil.getCruDeptName());
            answerList.get(i).setCreChushiId(UserUtil.getCruChushiId());
            answerList.get(i).setCreChushiName(UserUtil.getCruChushiName());
            answerList.get(i).setCreJuId(UserUtil.getCruJuId());
            answerList.get(i).setCreJuName(UserUtil.getCruJuName());
            answerList.get(i).setVisible(CommonConstants.VISIBLE[1]);
            answerList.get(i).setCreTime(JDateToolkit.getNowDate4());

            //加入这道题的得分
            //根据试题id和试卷id获取这道题的分数
            String score = answerList.get(i).getScore();//xxkhQuestionGroupService.getEveryScore(answerList.get(i).getQuestionId(),answerList.get(i).getTestId()).getEveryScore();
            //根据问题id获取正确答案
            List<Option> rightOptions = optionService.getRightOptionsById(answerList.get(i).getQuestionId());
            //根据试题id去查询正确答案，正确则得分，错误则分数为0
            /* singleChoise: [],
                 multipleChoise: [],
                 judgement: [],
                 completion: [],
                 shortAnswer: []*/
            if("singleChoise".equals(answerList.get(i).getQuestionType())||"judgement".equals(answerList.get(i).getQuestionType())){

                //单选，判断
                if(rightOptions.get(0).getSequence().equals(answerList.get(i).getOptionId())){ //这里的空之后替换成磊哥的接口获取的正确答案数据
                    //如果用户的答案跟正确答案相同
                    answerList.get(i).setScore(score);
                    totalPoints += Integer.parseInt(score);
                }else{
                    answerList.get(i).setScore("0");
                }
            }else if("multipleChoise".equals(answerList.get(i).getQuestionType())){
                //多选
                //把新建一个正确答案的数组
                String[] RightOptions =new String[rightOptions.size()];
                //把正确答案放到数组中去
                for (int j = 0;j<rightOptions.size();j++){
                    RightOptions[j] = rightOptions.get(j).getSequence();
                }

                //用户答案的数组，根据#来分割
                String[] userOptionsOfDx = answerList.get(i).getOptionId().split("#");

                Arrays.sort(RightOptions);//排序数组
                Arrays.sort(userOptionsOfDx);//排序数组

                if(Arrays.equals(RightOptions, userOptionsOfDx)){//判断内容是否相同
                    //两个数组内容相同，得分
                    answerList.get(i).setScore(score);
                    totalPoints += Integer.parseInt(score);
                }else{
                    answerList.get(i).setScore("0");
                }

            }else if("completion".equals(answerList.get(i).getQuestionType())){
                //填空
                //把新建一个正确答案的数组
                String[] RightOptions =new String[rightOptions.size()];
                //把正确答案放到数组中去
                for (int j = 0;j<rightOptions.size();j++){
                    RightOptions[j] = rightOptions.get(j).getContent();
                }

                //用户答案的数组，根据#来分割
                String[] userOptionsOfTk = answerList.get(i).getOptionContent().split("#");

                Arrays.sort(RightOptions);//排序数组
                Arrays.sort(userOptionsOfTk);//排序数组

                if(Arrays.equals(RightOptions, userOptionsOfTk)){//判断内容是否相同
                    //两个数组内容相同，得分
                    answerList.get(i).setScore(score);
                    totalPoints += Integer.parseInt(score);
                }else{
                    answerList.get(i).setScore("0");
                }
            }else{
                //问答,暂时问答的设置为0分，人工评卷之后添加分数
                answerList.get(i).setScore("");
            }
            //把题型改为12345
            if("singleChoise".equals(answerList.get(i).getQuestionType())){//单选

                answerList.get(i).setQuestionType("1");
            }else if("multipleChoise".equals(answerList.get(i).getQuestionType())){//多选

                answerList.get(i).setQuestionType("2");
            }else if("judgement".equals(answerList.get(i).getQuestionType())){//判断

                answerList.get(i).setQuestionType("3");
            }else if("completion".equals(answerList.get(i).getQuestionType())){//填空

                answerList.get(i).setQuestionType("4");
            }else if("shortAnswer".equals(answerList.get(i).getQuestionType())){//问答

                answerList.get(i).setQuestionType("5");
            }
            Answer newDut = testDao.save(answerList.get(i));

            answerlist.add(newDut);
        }
        //这里把客观题得分放到人员试卷关联表中（xxkh_user_paper）,并且修改考试状态
        XxkhUserPaper xxkhUserPaper = new XxkhUserPaper();
        xxkhUserPaper.setTestId(answerlist.get(0).getTestId());
        xxkhUserPaper.setPaperId(answerlist.get(0).getPaperId());
        xxkhUserPaper.setUserId(answerlist.get(0).getCreUserId());
        xxkhUserPaper.setPaperObjectiveScore(totalPoints+"");
        testManageService.updateObjectiveScore(xxkhUserPaper);

        return answerlist;
    }

    /**
     * @Author 王富康
     * @Description //TODO 暂存简答题分数
     * @Date 2018/9/18 15:25 
     * @Param [answerList]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity.Answer>
     **/
    public int saveSortAnswer(List<Answer> answerList){
        List<Answer> answerlist = new ArrayList<>();
        int updateDuty = 0;
        for(int i = 0;i < answerList.size();i++){
            String jpql ="update Answer t set t.score = ? where id = ?";
            updateDuty += testDao.update(jpql, answerList.get(i).getScore(),answerList.get(i).getId());
        }
        return updateDuty;
    }
    
    /**
     * @Author 王富康
     * @Description //TODO 交卷后显示答案得到数据
     * @Date 2018/9/11 21:04
     * @Param [paperInfo, testId, paperId]
     * @return com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo
     **/
    @Override
    public XxkhPaperInfo getTestResult(XxkhPaperInfo info,String testId,String userId, String questionType){
        XxkhPaperInfo findOne = dao.getTestResult(info, testId, userId);
        List<XxkhQuestionGroup> byList = xxkhQuestionGroupDao.getSortAnswerList(findOne.getId(), questionType);//byList(findOne.getId());
        findOne.setGroup(byList);
        for(XxkhQuestionGroup group : byList) {
            List<TestResultVo> testResult = testDao.getTestResult(group.getId(),testId,info.getId(),userId);
            if (testResult!=null&&testResult.size()>0) {
                group.setTestResultList(testResult);
                for(TestResultVo testResults : testResult) {
                    //应该放到每道题对应的选项上，不应该放到每个选项对应的实体上
                    List<Option> options = questionDao.getOptionList(testResults.getId());
                    if(options!=null&&options.size()>0) {
                        testResults.setList(options);
                    }
                }
            }
        }
        return findOne;
    }
    /**
     * @Author 王富康
     * @Description //TODO 用来判断多选或者填空是否正确,值得注意的是必须为长度相同的两个字符串。才会返回true就是说试题的答案分割的话，分割的标点个数及数量要一样。
     *              //TODO test.equels("语文,数学,英语","数学英语语文,,")返回的也是true(暂时没有用)
     * @Date 2018/9/7 15:26
     * @Param [first, second]
     * @return boolean
     **/
    public static boolean equels(String first , String second ) {
        first = first.toLowerCase() ;
        second = second.toLowerCase() ;

        byte[] ch = first.getBytes() ;
        byte[] ch1 = second.getBytes() ;
        sort(ch);
        sort(ch1);

        return  new String(ch).equals(new String(ch1));
    }


    private static void sort(byte[] ch) {
        for(int i=0 ; i<ch.length-1 ; i++) {
            int lag = ch[i];
            int index = i ;
            for(int j=i+1 ; j<ch.length ; j++) {
                if(lag < ch[j]) {
                    lag = ch[j];
                    index = j ;
                }
            }
            ch[index] = ch[i];
            ch[i] = (byte) lag ;
        }
    }


}
