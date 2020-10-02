package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.controller;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.service.NdjhService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.entity.NdzjEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.service.NdzjServiceI;
import com.sinosoft.sinoep.urge.entity.SysUrge;
import com.sinosoft.sinoep.urge.services.SysUrgeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李帅
 * @Date: 2018/9/8 :09
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/ndzj/")
public class NdzjController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NdzjServiceI ndzjService;
    @Autowired
    private DwxtOrgService dwxtOrgService;
    @Autowired
    private NotityService notityService;
    @Autowired
    private NdjhService ndjhService;
    @Autowired
    private SysUrgeService sysUrgeService;
    /**
     * 年底计划 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年9月8日
     * @param pageImpl
     * @param ndzjEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询年度计划列表")
    @ResponseBody
    @RequestMapping("getNdzjList")
    public PageImpl getNdzjList(PageImpl pageImpl, NdzjEntity ndzjEntity, DwxtOrg dwxtOrg, String typeVal) {
        if(typeVal.equals("hz")) {
            List<Map<String, Object>> list = dwxtOrgService.getTree(dwxtOrg);
            if (list.size() > 0) {
                String ids = "";
                for (Map<String, Object> map : list) {
                    if (map.get("type").toString().equals("org")) {
                        ids += map.get("id").toString() + ",";
                    }
                }
                ids = ids.substring(0, ids.length() - 1);
                Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
                String startTime ="";
                String endTime ="";
                if (StringUtils.isNotBlank(ndzjEntity.getReportingTime())) {
                    startTime =ndzjEntity.getReportingTime().substring(0,(ndzjEntity.getReportingTime().length()+1)/2-1).trim();
                    endTime =ndzjEntity.getReportingTime().substring((ndzjEntity.getReportingTime().length()+1)/2,ndzjEntity.getReportingTime().length()).trim();
                }
                PageImpl pageList = ndzjService.getPageList(pageable, pageImpl, ndzjEntity,startTime,endTime, typeVal,ids);

                return pageList;
            } else {
                return null;
            }
        }else{
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            String startTime ="";
            String endTime ="";
            if (StringUtils.isNotBlank(ndzjEntity.getReportingTime())) {
                startTime =ndzjEntity.getReportingTime().substring(0,(ndzjEntity.getReportingTime().length()+1)/2-1).trim();
                endTime =ndzjEntity.getReportingTime().substring((ndzjEntity.getReportingTime().length()+1)/2,ndzjEntity.getReportingTime().length()).trim();
            }
            PageImpl pageList = ndzjService.getPageList(pageable, pageImpl, ndzjEntity,startTime,endTime, typeVal,"");

            return pageList;
        }
    }

    /**
     * 三会一课年底总结 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param ndzj
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存三会一课年底总结")
    @RequestMapping("saveNdzj")
    @ResponseBody
    public JSONObject saveNdzj(NdzjEntity ndzj) {
        JSONObject json = new JSONObject();
        try {
            ndzj = ndzjService.saveNdzj(ndzj);
            json.put("flag", 1);
            json.put("data", ndzj);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
    /**
     * TODO 打开只读、修改页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年9月8日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        NdzjEntity ndzj = null;
        try {
            ndzj = ndzjService.getById(id);
            json.put("flag", "1");
            json.put("data", ndzj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
    /**
     * 根据id逻辑删除年底总结对应列表
     * @author 李帅
     * @Date 2018年8月28日
     * @param ndzj
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除年底总结对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(NdzjEntity ndzj) {
        JSONObject json = new JSONObject();
        try {
            int n = ndzjService.delete(ndzj);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }

    /**
     * 新增时判断今年是否有年底总结
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param annual
     * @param partyOrganizationId
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询本年度是否有年底总结")
    @ResponseBody
    @RequestMapping("addFrom")
    public JSONObject addFrom( String annual,String partyOrganizationId){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        NdzjEntity  ndzj =null;
        try {
            ndzj = ndzjService.addFrom( annual,partyOrganizationId);
            json.put("flag", "1");
            json.put("data", ndzj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
    /**
     *
     * @Author 李帅
     * @Description: 催报
     * @Date 2018/10/19
     */
    @LogAnnotation(value = "save",opName = "催报")
    @RequestMapping("urge")
    @ResponseBody
    @Transactional
    public JSONObject urge(String ids, String annual) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            String[]orgIdVals = ids.split(",");
            for(String orgId:orgIdVals){
                String userId = ndjhService.selectUserId(orgId);
                if(!userId.equals("")){
                    saveMessageAndUrge(userId,annual);
                    json.put("flag", "1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }
    /**
     *
     * @Author 李帅
     * @Description: 保存消息及催报记录
     * @Date 2018/10/19
     */
    @LogAnnotation(value = "save",opName = "催报")
    @RequestMapping("saveMessageAndUrge")
    @ResponseBody
    @Transactional
    public void saveMessageAndUrge(String userId, String yearVal) {
        NotityMessage message = new NotityMessage();
        message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
        message.setSubject(yearVal+"年底总结催报");//标题
        message.setContent("请尽快上报本党支部的"+yearVal+"年年底总结！");//内容
        message.setSendTime(new Date());//发送时间
        message.setAcceptTime(new Date());//接收时间
        message.setAccepterId(userId);//接收人id
        message.setStatus("0");//状态
        message.setType("3");//类型  1手机  2邮箱   3栈内
        message.setMsgUrl("");//消息链接
        notityService.add(message);

        SysUrge sysUrge = new SysUrge();
        sysUrge.setSenderId(UserUtil.getCruUserId());
        sysUrge.setSendTime(JDateToolkit.getNowDate4());
        sysUrge.setAcceptTime(JDateToolkit.getNowDate4());
        sysUrge.setAccepterId(userId);
        sysUrge.setModuleType("党务工作");
        sysUrge.setSubject(yearVal+"年年底总结催报");
        sysUrge.setContent("请尽快上报本党支部的"+yearVal+"年年底总结！");
        sysUrgeService.saveUrge(sysUrge);
    }


}
