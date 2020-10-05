package com.sinosoft.sinoep.modules.zhbg.zbgl.config.source;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.services.ConfigService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.services.DutyWorkService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class Task {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigService configService;

    @Autowired
    private NotifyController notifyController ;

    @Autowired
    private DutyWorkService dutyWorkService;

    /**
     * @Author 王富康
     * @Description //TODO 每月第一日0时自动生成下月基础配置
     * @Date 2018/8/30 16:33
     * @Param []
     * @return void
     **/
    @Scheduled(cron="0 0 0 1 * ?")//每月第一日0时执行
    public void creJcpzOnFistDay(){
        try {
                Date today = new Date();
                Calendar cale = Calendar.getInstance();
                cale.setTime(today);
                cale.set(Calendar.DAY_OF_MONTH,1);
                cale.add(Calendar.MONTH, 1);

                int yearA = cale.get(Calendar.YEAR);
                int monthA = cale.get(Calendar.MONTH) + 1;
                String monthB = "";
                if(monthA==0){
                    monthA=12;
                    yearA=yearA+1;
                }
                if (monthA < 10) {
                    monthB = "0" + monthA;
                }else{
                    monthB = "" + monthA;
                }

            String dutyMonth = yearA+"-"+monthB;
            List<Config> list = configService.queryOne(dutyMonth);
            if(list.size() == 0){
                //如果list为空，则证明为新的一个月，那么则新增这个月的基础配置（根据上一个月的数据去新增，考虑上报截止日期如果为31号，其他月有没有）
                //获取上个月份
                String lastMonth = DateUtil.getLastDate(dutyMonth);
                List<Config> lastMonthConfig = configService.queryOne(lastMonth);
                if(lastMonthConfig.size() != 0){//如果有上个月的数据，则吧上个月的数据新增到下个月中
                    //对上个月的上报截止日期做处理（上报截止日期如果为31号，其他月有没有）
                    String lastMonthReportOverDate = lastMonthConfig.get(0).getReportOverDate();//获取上个月的上报截止日期
                    if(DateUtil.validate(lastMonthReportOverDate)){
                        //如果上个月的的日期不合法，则会导致后续的上报日期错误。例如，7-32，那么下个月的上报截止日期就为9.30.
                        String temp[]=lastMonthReportOverDate.split("-");//年月分割，获得年月（年月之间必须为“-”）
                        int lastMonthYear = Integer.parseInt(temp[0]);
                        int lastMonthMonth = Integer.parseInt(temp[1]);

                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(sdf.parse(lastMonthReportOverDate));
                        calendar.add(calendar.MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
                        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月

                        Date date = calendar.getTime();
                        String reportOverDate = sdf.format(date);//上报截止日期


                        Config cf = new Config();
                        cf.setDutyMonth(dutyMonth);
                        if(DateUtil.validate(reportOverDate) && !(lastMonthReportOverDate.equals(DateUtil.getLastDayOfMonth(lastMonthYear,lastMonthMonth)))){
                            //如果日期合法，并且上个月的上报截止日期不为最后一天
                            cf.setReportOverDate(reportOverDate);
                        }else{
                            //如果上个月的上报截止日期为最后一天，则下月也应存最后一天
                            String reportTemp[]=reportOverDate.split("-");//年月分割，获得年月（年月之间必须为“-”）
                            int year = Integer.parseInt(reportTemp[0]);
                            int month = Integer.parseInt(reportTemp[1]);
                            reportOverDate = DateUtil.getLastDayOfMonth(year,month);
                            cf.setReportOverDate(reportOverDate);
                        }
                        cf.setPromptMessageContent(lastMonthConfig.get(0).getPromptMessageContent());
                        cf.setUnitId(lastMonthConfig.get(0).getUnitId());
                        cf.setUnitName(lastMonthConfig.get(0).getUnitName());
                        cf =configService.save(cf,"System");

                        //获取上报员的id
                        String unitId = "["+cf.getUnitId()+"]";
                        String[] unitName = cf.getUnitName().split(",");
                        String unitNames = "[";
                        for(int i = 0;i<unitName.length;i++){
                            unitNames +="\""+unitName[i]+"\",";
                        }
                        if(unitNames.length()>1){
                            unitNames = unitNames.substring(0,unitNames.length()-1);
                        }
                        unitNames +="]";
                        //获取值班上报员的id,这里不需要判断是否有没有上报员，因为这里是根据上个月的数据填写的，上个月的数据需要通过校验有没有上报员
                        JSONObject jsonObject = dutyWorkService.hasReportUserByDeptId(unitId,unitNames);

                        for (Object fan : jsonObject.getJSONArray("data")) {
                            JSONObject object = (JSONObject)fan;
                            //发送消息
                            NotityMessage ms = new NotityMessage();
                            ms.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
                            ms.setSubject("请上报"+cf.getDutyMonth()+"值班表");//消息主题
                            ms.setContent("请前往值班表管理添加"+cf.getDutyMonth()+"值班信息，并填写完毕后进行上报，此次值班表上报截止日期为："+cf.getReportOverDate());//消息内容
                            ms.setAccepterId(""+object.get("userid")+"");//接收人id
                            ms.setStatus("0");//0：未读,1：已读
                            ms.setType("3");//3：站内消息
                            ms.setMsgUrl("");//打开消息的url
                            notifyController.save(ms);
                        }
                        list.add(cf);
                    }
                }else{
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
