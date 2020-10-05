package com.sinosoft.sinoep.modules.zhbg.zbgl.config.controller;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.services.ConfigService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.services.DutyWorkService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

/**
 * @Author 王富康
 * @Description //TODO 值班基础配置Controller层
 * @Date 2018/7/13 15:22
 **/
@Controller
@RequestMapping("zhbg/zbgl/config")
public class ConfigController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigService configService;

    @Autowired
    NotifyController notifyController ;

    @Autowired
    private DutyWorkService dutyWorkService;

    /**
     * @Author 王富康
     * @Description //TODO 页面初始化，查询数据库的那条配置信息。
     * @Date 2018/7/16 14:09
     * @Param [con]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "查询基础配置信息")
    @RequestMapping(value = "queryOne", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryOne(String dutyMonth, String sysId) {

        JSONObject json = new JSONObject();
        try {
            List<Config> list = configService.queryOne(dutyMonth);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total",list.size());
            data.put("rows", list);
            json.put("data", data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }

        log.info(json.toString());
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 每月第一日0时自动生成下月基础配置
     * @Date 2018/8/30 16:33
     * @Param []
     * @return void
     **/
    @Scheduled(cron="0 0 0 1 * ?")//每月第一日0时执行
    private void creJcpzOnFistDay(){
        try {
            Calendar cale = null;
            cale = Calendar.getInstance();
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
                        cf =configService.save(cf);

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
                            System.out.println("  userId:" + object.get("userid") + ",处室名称:" + object.get("deptname")+"处室id："+object.get("deptid"));
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

    /**
     * @Author 王富康
     * @Description //TODO 以往基础配置信息列表
     * @Date 2018/7/30 18:15
     * @Param [pageImpl, name, key, describe]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @LogAnnotation(value = "query",opName = "查询以往基础配置信息")
    @ResponseBody
    @RequestMapping("list")
    public PageImpl list(PageImpl pageImpl, String dutyMonthOfSearch,String unitNameOfSearch){
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = configService.list(pageable,pageImpl,dutyMonthOfSearch, unitNameOfSearch);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增基础配置
     * @Date 2018/7/13 16:33
     * @Param [con, key]
     * @return net.sf.json.JSONObject
     **/
    @SameUrlData
    @LogAnnotation(value = "save",opName = "新增基础配置信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(Config con, String sysId) {

        JSONObject json = new JSONObject();

        try {
            con = configService.save(con);
            json.put("flag", 1);
            json.put("data", con);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改基础配置
     * @Date  17:56
     * @Param [con]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "修改基础配置信息")
    @RequestMapping("updateConfig")
    @ResponseBody
    public JSONObject updateConfig(Config con) {

        JSONObject json = new JSONObject();

        json.put("flag", "0");
        if (StringUtils.isNotBlank(con.getId())) {
            try {
                configService.updateConfig(con);
                json.put("flag", "1");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询修改后单位跟之前配置的单位有什么不同，新增了那些，去除了哪些
     * @Date 2018/8/7 15:10
     * @Param [con]
     * @return java.util.Map<java.lang.String,java.lang.String[]>
     **/
    @LogAnnotation(value = "query",opName = "修改基础配置信息")
    @RequestMapping("getDefferentUnitIds")
    @ResponseBody
    public JSONObject getDefferentUnitIds(Config con){

        JSONObject json = new JSONObject();

        json.put("flag", "0");
        if (StringUtils.isNotBlank(con.getId())) {
            try {
                List<Config> dut = configService.queryOne(con.getDutyMonth());//得到未修改之前的基础配置
                //id
                String oldUnitIds = dut.get(0).getUnitId();//得到未修改前的单位ids
                String newUnitIds = con.getUnitId();//修改后的单位ids
                List<String> oldUnitIdsList = Arrays.asList(oldUnitIds.split(","));
                List<String> newUnitIdsList = Arrays.asList(newUnitIds.split(","));
                String addUnitIds = "";
                String deleteUnitIds = "";

                //name
                String oldUnitName = dut.get(0).getUnitName();//得到未修改前的单位ids
                String newUnitName = con.getUnitName();//修改后的单位Name
                List<String> oldUnitNameList = Arrays.asList(oldUnitName.split(","));
                List<String> newUnitNameList = Arrays.asList(newUnitName.split(","));
                String addUnitName = "";
                String deleteUnitName = "";

                //对比查看去除的单位
                for(int i = 0; i<oldUnitIdsList.size();i++){
                    if(newUnitIdsList.contains(oldUnitIdsList.get(i))){

                    }else{
                        deleteUnitIds += oldUnitIdsList.get(i)+",";
                        deleteUnitName += oldUnitNameList.get(i)+",";
                    }
                }
                //对比查看新增的单位
                for(int i = 0; i<newUnitIdsList.size();i++){
                    if(oldUnitIdsList.contains(newUnitIdsList.get(i))){

                    }else{
                        addUnitIds +=newUnitIdsList.get(i)+",";
                        addUnitName +=newUnitNameList.get(i)+",";
                    }
                }

                //为了返回跟基础配置一样的字符串，把最后一个“，”去掉
                if(deleteUnitIds.length()>0){
                    deleteUnitIds = deleteUnitIds.substring(0,deleteUnitIds.length()-1);
                    deleteUnitName = deleteUnitName.substring(0,deleteUnitName.length()-1);
                }

                if(addUnitIds.length()>0){
                    addUnitIds = addUnitIds.substring(0,addUnitIds.length()-1);
                    addUnitName = addUnitName.substring(0,addUnitName.length()-1);
                }

                json.put("addUnitIds", addUnitIds);
                json.put("deleteUnitIds", deleteUnitIds);
                json.put("addUnitName", addUnitName);
                json.put("deleteUnitName", deleteUnitName);
                json.put("flag", "1");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 是否按时上报
     * @Date 2018/8/1 10:33
     * @Param month 上报的考勤表的月份
     * @return boolean
     **/
    public boolean isOnTime(String month){
        return configService.isOnTime(month);
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据月份和当前登录人的处室id查询当月本部门是否需要上报(暂时用于上报时，查看新增的数据是否可以上报)
     * @Date 2018/8/15 12:03
     * @Param [month]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据月份和当前登录人的处室id查询当月本部门是否需要上报(暂时用于上报时，查看新增的数据是否可以上报)")
    @RequestMapping("getConfigByMonthAndUnitId")
    @ResponseBody
    public JSONObject getConfigByMonthAndUnitId(String month){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            List<Config> con = configService.getConfigByMonthAndUnitId(month);
            json.put("flag","1");
            json.put("data",con);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }

}
