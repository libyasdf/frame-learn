package com.sinosoft.sinoep.modules.zhbg.zbgl.config.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.services.ConfigService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
            con = configService.save(con,"");
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
