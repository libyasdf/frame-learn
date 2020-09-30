package com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.controller;


import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DeptVo;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.services.DutyWorkService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.services.ShangBaoService;
import com.sinosoft.sinoep.urge.controller.SysUrgeController;
import com.sinosoft.sinoep.urge.entity.SysUrge;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 上报Controller
 * @Date 2018/7/17 9:26
 * @Param
 * @return
 **/
@Controller
@RequestMapping("zhbg/zbgl/shangbao")
public class ShangBaoController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShangBaoService shangBaoService;

    @Autowired
    private DutyWorkService dutyWorkService;

    @Autowired
    private NotifyController notifyController;

    @Autowired
    private SysUrgeController sysUrgeController;
    /**
     * @Author 王富康
     * @Description //TODO 值班表列表查询(值班表管理使用)
     * @Date 2018/7/11 15:04
     * @Param pageImpl 分页
     * @param month 值班年月
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @LogAnnotation(value = "query",opName = "值班表列表查询(值班表管理使用)")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public PageImpl list(PageImpl pageImpl, String month, String isReport, String reportStatus){
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = shangBaoService.list(pageable,pageImpl,month,isReport,reportStatus);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 总值班室查看值班室上报情况 x/x
     * @Date 2018/7/27 10:46
     * @Param [pageImpl, month]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @LogAnnotation(value = "query",opName = "总值班室查看值班室上报情况 x/x")
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    @ResponseBody
    public PageImpl getList(PageImpl pageImpl,String month){

        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = shangBaoService.getList(pageable,pageImpl,month);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询处室上报情况详细信息
     * @Date 2018/7/27 17:51
     * @Param [month]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "查询处室上报情况详细信息")
    @RequestMapping(value = "getChushiReportStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getChushiReportStatus(String year_month){
        JSONObject json = new JSONObject();
        try {
            List<ShangBao> list = shangBaoService.getChushiReportStatus(year_month);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total", list.size());
            JSONArray array = new JSONArray();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
            jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
                    "updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
            array = JSONArray.fromObject(list, jsonConfig);
            data.put("rows", array);
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }
        log.info(json.toString());
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 上报(修改上报表的上报状态)
     * @Date 2018/8/1 10:00
     * @Param [id]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "上报(修改上报表的上报状态)")
    @RequestMapping(value = "reportDuty", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject reportDuty(String id, String month) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(id)) {
            try {
                shangBaoService.reportDuty(id, month);
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
     * @Description //TODO 根据月份查询本部门是否已经添加该月份的上报数据
     * @Date 2018/8/1 14:44
     * @Param [id, month]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据月份查询本部门是否已经添加该月份的上报数据")
    @RequestMapping(value = "queryOne", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryOne(String month) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
            try {
                List<ShangBao> list = shangBaoService.queryOne(month);
                json.put("flag", "1");
                json.put("data",list);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据月份和单位id查询出一条上报数据
     * @Date 2018/8/24 16:29
     * @Param [month, unitId]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据月份和单位id查询出一条上报数据")
    @RequestMapping(value = "queryOneByMonthAndUnitId", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryOneByMonthAndUnitId(String month, String unitId) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            List<ShangBao> list = shangBaoService.queryOneByMonthAndUnitId(month, unitId);
            json.put("flag", "1");
            json.put("data",list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增上报表数据
     * @Date 2018/8/1 15:46
     * @Param [sb]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao
     **/
    @LogAnnotation(value = "save",opName = "新增基础配置信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(ShangBao sb) {

        JSONObject json = new JSONObject();

        try {
            sb = shangBaoService.save(sb);
            json.put("flag", 1);
            json.put("data", sb);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 催办，发送消息，同时保存催催办记录
     * @Date 2018/9/3 10:11
     * @Param [deptIds, deptNames, pressContent, yearMonth]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "催办，发送消息，同时保存催催办记录")
    @RequestMapping("press")
    @ResponseBody                           //"deptIds":JSON.stringify(deptIds),"deptNames":JSON.stringify(deptNames),"pressContent":val, "yearMonth":year_month
    public JSONObject press(String deptIds, String deptNames, String pressContent, String yearMonth) {//"subject":"请上报"+year_month+"值班表",======"content":""+val+"",======"userIDs":userIDs,"moduleType":"zbgl_shangbao"

        JSONObject json = new JSONObject();

        try {
            //获取单位的值班上报员id
            json = dutyWorkService.hasReportUserByDeptId(deptIds,deptNames);
            if(json.get("flag")=="1"){

                List<DeptVo> list = new ArrayList<>();
                log.info(JSON.toJSONString(json));
                JSONArray arr = json.getJSONArray("data");
                list = JSONArray.toList(arr, DeptVo.class);
                //都有值班上报员，得到各个单位的上报员的id
                String userIDs ="";
                for(int i =0;i<list.size();i++){
                    NotityMessage notityMessage = new NotityMessage();
                    notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);  //系统ID
                    notityMessage.setSubject("请上报"+yearMonth+"值班表"); //消息主题
                    notityMessage.setContent(pressContent); //消息内容
                    notityMessage.setAccepterId(list.get(i).getUserid());  //接收人ID
                    notityMessage.setStatus("0");  //0未读；1已读
                    notityMessage.setType("3");  //3站内消息
                    //发送消息
                    notifyController.save(notityMessage);
                    userIDs += list.get(i).getUserid()+",";
                }
                userIDs = userIDs.substring(0,userIDs.length()-1);  //去掉最后一个逗号

                //催办记录
                SysUrge sysUrge = new SysUrge();
                sysUrge.setSubject("请上报"+yearMonth+"值班表");
                sysUrge.setContent(pressContent);
                sysUrge.setModuleType("zbgl_shangbao");

                sysUrgeController.saveUrge(sysUrge,userIDs);
            }else{
                //有没有值班上报员的单位，不做操作返回json就好了
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", "0");
        }

        return json;
    }

}
