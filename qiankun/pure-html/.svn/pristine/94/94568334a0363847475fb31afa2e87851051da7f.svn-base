package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.controller;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhTjEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.service.NdjhService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 年度计划主表Controller
 * @Author: 李帅
 * @Date: 2018/8/30 9:34
 */
@Controller
@RequestMapping("djgl/ddjs/shyk/ndjh/")
public class NdjhController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NdjhService ndjhService;
    @Autowired
    private DwxtOrgService dwxtOrgService;
    @Autowired
    private NotityService notityService;
    @Autowired
    private SysUrgeService sysUrgeService;
    /**
     * 年度计划 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param pageImpl
     * @param ndjhEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询年度计划列表")
    @ResponseBody
    @RequestMapping("getNdjhList")
    public PageImpl getNdjhList(PageImpl pageImpl, NdjhEntity ndjhEntity, DwxtOrg dwxtOrg, String typeVal) {
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
                String startTime = "";
                String endTime = "";
                if (StringUtils.isNotBlank(ndjhEntity.getAnnual())) {
                    startTime = ndjhEntity.getAnnual().substring(0, (ndjhEntity.getAnnual().length() + 1) / 2 - 1).trim();
                    endTime = ndjhEntity.getAnnual().substring((ndjhEntity.getAnnual().length() + 1) / 2, ndjhEntity.getAnnual().length()).trim();
                }
                PageImpl pageList = ndjhService.getPageList(pageable, pageImpl, ndjhEntity, startTime, endTime, typeVal,ids);

                return pageList;
            } else {
                return null;
            }
        }else{
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            String startTime = "";
            String endTime = "";
            if (StringUtils.isNotBlank(ndjhEntity.getAnnual())) {
                startTime = ndjhEntity.getAnnual().substring(0, (ndjhEntity.getAnnual().length() + 1) / 2 - 1).trim();
                endTime = ndjhEntity.getAnnual().substring((ndjhEntity.getAnnual().length() + 1) / 2, ndjhEntity.getAnnual().length()).trim();
            }
            PageImpl pageList = ndjhService.getPageList(pageable, pageImpl, ndjhEntity, startTime, endTime, typeVal,"");

            return pageList;

        }

    }



    /**
     * 保存年度计划
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param ndjh
     * @return
     */
    //@SameUrlData
    @LogAnnotation(value = "save",opName = "保存年度计划")
    @RequestMapping("saveNdjh")
    @ResponseBody
    public JSONObject saveNdjh(@RequestBody NdjhEntity ndjh) {
        JSONObject json = new JSONObject();
        try {
            ndjh=ndjhService.saveForm(ndjh);
            json.put("data",ndjh);
            json.put("flag", 1);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
    /**
     * TODO 更新业务表流程状态
     *
     * @author 李帅
     * @Date 2018年9月28日
     * @param
     * @return
     */
    @LogAnnotation(value = "update",opName = "修改流程状态")
    @ResponseBody
    @RequestMapping("updateFlag")
    public JSONObject updateFlag(String id, String subflag) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        NdjhEntity ndjhEntity=null;
        try {
            ndjhEntity=  ndjhService.updateFlag(id, subflag);
            json.put("data",ndjhEntity);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
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
        message.setSubject(yearVal+"年度计划催报");//标题
        message.setContent("请尽快上报本党支部的"+yearVal+"年年度计划！");//内容
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
        sysUrge.setSubject(yearVal+"年年度计划催报");
        sysUrge.setContent("请尽快上报本党支部的"+yearVal+"年年度计划！");
        sysUrgeService.saveUrge(sysUrge);
    }

    /**
     * 根据id逻辑删除年度计划
     * @author 李帅
     * @Date 2018年8月30日
     * @param ndjh
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除年度计划")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(NdjhEntity ndjh) {
        JSONObject json = new JSONObject();
        try {
            int n = ndjhService.delete(ndjh);
            json.put("flag", ""+n+"");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }


    /**
     * TODO 打开新增页面、只读页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年8月30日
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
        JSONObject ndjhJson = null;
        try {
            ndjhJson = ndjhService.getById(id);
            json.put("flag", "1");
            json.put("data", ndjhJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    /**
     * 新增时判断今年是否有年度计划
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param annual
     * @param partyOrganizationId
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询本年度是否有年度计划")
    @ResponseBody
    @RequestMapping("addFrom")
    public JSONObject addFrom( String annual,String partyOrganizationId){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        NdjhEntity  ndjh =null;
        try {
            ndjh = ndjhService.addFrom( annual,partyOrganizationId);
            json.put("flag", "1");
            json.put("data", ndjh);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
    /**
     *年度计划统计
     * TODO
     * @author 李帅
     * @Date 2018年9月6日
     * @return
     * */
    @LogAnnotation(value = "query",opName = "查询年度计划统计")
    @ResponseBody
    @RequestMapping("getStatistics")
    public PageImpl getStatistics(PageImpl pageImpl, NdjhTjEntity ndjhTjEntity,String sqlName) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        String startTime ="";
        String endTime ="";
        if (StringUtils.isNotBlank(ndjhTjEntity.getAnnual())) {
            startTime =ndjhTjEntity.getAnnual().substring(0,(ndjhTjEntity.getAnnual().length()+1)/2-1).trim();
            endTime =ndjhTjEntity.getAnnual().substring((ndjhTjEntity.getAnnual().length()+1)/2,ndjhTjEntity.getAnnual().length()).trim();
        }
        PageImpl pageList = ndjhService.Statistics(pageable,sqlName,startTime,endTime);

        return pageList;
    }




    /**
     *查询未上报的组织列表
     * TODO
     * @author 李帅
     * @Date 2018年9月6日
     * @param annual
     * @return
     * */
    @LogAnnotation(value = "query",opName = "查询未上报的组织列表")
    @ResponseBody
    @RequestMapping("getUnreported")
    public PageImpl getUnreported(PageImpl pageImpl, String annual,String sqlName) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        PageImpl pageList = ndjhService.getUnreported(pageable,annual,sqlName);

        return pageList;
    }
    /**
     * TODO 打开只读、修改页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("editVal")
    public JSONObject editVal( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        json.put("total","300");
        NdjhEntity ndjhJson = null;
        try {
            ndjhJson = ndjhService.getByIdVal(id);
            json.put("flag", "1");
            json.put("data", ndjhJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

}
