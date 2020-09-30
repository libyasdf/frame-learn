package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.source;

import org.springframework.stereotype.Component;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services.TestManageService;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Calendar;
import java.util.List;

@Component
public class Task {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private org.apache.log4j.Logger requestLog = org.apache.log4j.Logger.getLogger("TESTMANAGE");

    @Autowired
    private TestManageService testManageService;

    @Autowired
    private SysWaitNoflowService sysWaitNoflowService;

    /**
     * @Author 王富康
     * @Description //TODO 根据考试id获取考试人员(不分页),发送待办。每天00:00执行
     * @Date 2018/9/4 17:24
     * @Param [TestDate]
     * @return net.sf.json.JSONObject
     **/

    @Scheduled(cron="0 10 0 * * ?")//每月第一日0时执行
    //@Scheduled(fixedDelay = 1000*60*30)//30分钟执行一次
    public void queryAllTestUser(){
        try {

             Calendar cale = null;
            cale = Calendar.getInstance();
            int year = cale.get(Calendar.YEAR);
            int monthA = cale.get(Calendar.MONTH) + 1;
            int dayA = cale.get(Calendar.DAY_OF_MONTH);
            String month = "";
            String day = "";
            if(monthA==0){
                monthA=12;
                year=year+1;
            }
            if (monthA < 10) {
                month = "0" + monthA;
            }else{
                month = ""+ monthA;
            }
            if (dayA < 10) {
                day = "0" + dayA;
            }else{
                day = "" + dayA;
            }
            String TestDate = year+"-"+month+"-"+day;
            //根据日期获取当日的考试。
            List<XxkhTestInfo> TestList = testManageService.queryTestByTestDate(TestDate);
            if(TestList.size()>0){
                //如果当天的考试数量大于0那么就获取人员id发待办
                for(int i = 0;i< TestList.size();i++){
                    List<XxkhUserPaper>  list =testManageService.queryAllTestUser(TestList.get(i).getId());
                    for(int j = 0;j<list.size();j++){
                        //拿到人员id发送不走流程待办
                        String content = TestList.get(i).getName()+"\n " +
                                "所属大类：" +TestList.get(i).getType()+"\n " +
                                "答题时长：" +TestList.get(i).getAnswerTime()+"\n " +
                                "难易程度：" +TestList.get(i).getDifficultyLevel()+"\n " +
                                "考试满分：" +TestList.get(i).getFullScore()+" 及格分数："+TestList.get(i).getPassScore()+"\n" +
                                "考试时间："+TestList.get(i).getStartTime()+" - "+TestList.get(i).getEndTime();
                        String subject = TestList.get(i).getName();
                        String messageURL = "/modules/zhbg/xxkh/testManage/ksglEnterTest.html";

                        SysWaitNoflow waitNoflw = new SysWaitNoflow();
                        waitNoflw.setReceiveDeptName(list.get(j).getCandidateChushiName());//接收人部门
                        waitNoflw.setReceiveDeptId(list.get(j).getCandidateChushiId());//接收人部门id 必填
                        waitNoflw.setReceiveUserId(list.get(j).getUserId());//接受人id
                        waitNoflw.setReceiveUserName("");//接受人name
                        waitNoflw.setRolesNo("");//接受业务角色
                        waitNoflw.setOpName("参加考试");//操作类型
                        waitNoflw.setDaibanUrl(messageURL);//待办url  必填
                        waitNoflw.setTitle(TestList.get(i).getName());//待办显示标题
                        waitNoflw.setTableId("");//业务表id
                        waitNoflw.setTableName("xxkh_test_info");//业务表名
                        waitNoflw.setSourceId(TestList.get(i).getId());//业务id
                        waitNoflw.setAttr1(TestList.get(i).getId());//预留字段1
                        waitNoflw.setAttr2(list.get(j).getPaperId());//预留字段2
                        waitNoflw.setAttr3("");//预留字段3
                        waitNoflw.setDraftUserName("考试系统");
                        sysWaitNoflowService.saveWaitNoflowSystem(waitNoflw,false,subject,content,"");

                    }
                }
            }

            //根据日期获取结束时间为昨天以前的考试，删除待办
            List<XxkhTestInfo> deleteWaitNoflwTestList = testManageService.queryTestOfdeleteWaitNoflw();

            if(deleteWaitNoflwTestList.size()>0){
                //如果当天的考试数量大于0那么就获取人员id发待办
                for(int m = 0;m< deleteWaitNoflwTestList.size();m++){
                    //根据业务表id(也就是考试的id)删除待办
                    sysWaitNoflowService.deleteWaitNoflow("",deleteWaitNoflwTestList.get(m).getId(),"","","");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            requestLog.info(e.getMessage());
        }
    }
}
