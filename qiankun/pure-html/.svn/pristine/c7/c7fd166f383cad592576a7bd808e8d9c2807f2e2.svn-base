package com.sinosoft.sinoep.flowsccredit.controller;

import cn.hutool.core.util.EscapeUtil;
import com.sinosoft.ep.webform.tool.Utiles;
import com.sinosoft.log.LogHelper;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.flowsccredit.entity.FlowSccredit;
import com.sinosoft.sinoep.flowsccredit.services.FlowSccreditService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.util.exception.DAOException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;

/**
 *
 * <B>系统名称：</B><BR>
 * <B>模块名称：代办授权控制类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */

@Controller
@RequestMapping("sccredit")
public class SccreditController {

    private LogHelper logger = new LogHelper(this.getClass());

    @Autowired
    FlowSccreditService flowSccreditService;

    @Autowired
    UserInfoService userInfoService;

    /**
     *
     * <B>方法名称：保存代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:53:55
     * @param flowSccredit：代办授权实体类
     * @param fileTypeItem：文件类型
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "save", produces = "application/json; charset=utf-8")
    public String save( @ModelAttribute FlowSccredit flowSccredit,
                        @RequestParam(value = "fileTypeItem") String[] fileTypeItem) {
        JSONObject json = new JSONObject();
        json.put("result","0");
        int size = fileTypeItem.length;
        for(int n = 0;n<size;n++){
            String itemStr = fileTypeItem[n];
            if(!itemStr.contains(";")) {//如果是加密的（APP发来的请求）才进入以下方法
                String item = EscapeUtil.unescape(itemStr);
                String[] items = item.split(",");
                fileTypeItem = new String[items.length];
                for (int i = 0; i < items.length; i++) {
                    fileTypeItem[i] = items[i];
                }
            }
        }
        try{
            if(StringUtils.isNotBlank(flowSccredit.getSccreditid())){
                //修改
                JSONObject delRes = flowSccreditService.delAuth(flowSccredit.getSccreditid());
                if("1".equals(delRes.getString("res"))){
                    //删除成功后，新增
                    String result = flowSccreditService.save(flowSccredit, fileTypeItem);
                    if(StringUtils.isNotBlank(result)){
                        result = result.replaceAll("新增","修改");
                        json = JSONObject.fromObject(result);
                    }
                }else{
                    json.put("msg","修改授权失败");
                }
            }else{
                //新增
                String result = flowSccreditService.save(flowSccredit, fileTypeItem);
                if(StringUtils.isNotBlank(result)){
                    json = JSONObject.fromObject(result);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            json.put("msg","保存授权信息异常");
        }
        return json.toString();
    }

    /**
     *
     * <B>方法名称：删除代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:09
     * @param id：授权实体id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "del", produces = "application/json; charset=utf-8")
    public JSONObject delete(String id) {
        JSONObject res = new JSONObject();
        res.put("flag", "0");
        res.put("msg", "删除授权信息失败！");
        try{
            if (Utiles.isNullStr(id)) {
                return res;
            }
            JSONObject json = flowSccreditService.delAuth(id);
            if("1".equals(json.getString("res"))){
                res.put("flag", "1");
                res.put("msg", json.getString("message"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            res.put("msg", "删除授权信息异常");
        }
        return res;
    }

    /**
     *
     * <B>方法名称：查看代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:16
     * @param id：授权实体id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "view", produces = "application/json; charset=utf-8")
    public String view(String id) {
        JSONObject res = new JSONObject();
        res.put("result", "0");
        String result = "获取授权信息失败！";
        if (Utiles.isNullStr(id)) {
            return res.toString();
        }
        try{
            FlowSccredit flowSccredit = flowSccreditService.viewAuth(id);
            if (flowSccredit != null) {
                res = JSONObject.fromObject(flowSccredit);
                res.put("result", "1");
                res.put("msg", "获取授权信息成功！");
            }else {
                res.put("msg", result);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return res.toString();
    }

    /**
     *
     * <B>方法名称：修改代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:26
     * @param flowSccredit：授权实体类
     * @param fileTypeItem：文件类型
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "update", produces = "application/json; charset=utf-8")
    public String update(@ModelAttribute FlowSccredit flowSccredit,
                         @RequestParam(value = "fileTypeItem") String[] fileTypeItem) {
        JSONObject res = new JSONObject();
        res.put("result", "0");
        String result = "修改授权信息失败！";
        res.put("msg", result);
        try{
            if (Utiles.isNull(flowSccredit) || fileTypeItem.length > 1 || fileTypeItem.length == 0) {
                return res.toString();
            }
            String fileTypeItemEscape = fileTypeItem[0];
            if(StringUtils.isNotBlank(fileTypeItemEscape)) {
                fileTypeItemEscape = EscapeUtil.unescape(fileTypeItemEscape);
            }
            if(fileTypeItemEscape.contains(",")){//包含逗号代表传递了多个（移动端特殊处理）
                return res.toString();
            }
            result = flowSccreditService.updateAuth(fileTypeItemEscape, flowSccredit);
            res.put("result", "1");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        res.put("msg", result);
        return res.toString();
    }

    /**
     *
     * <B>方法名称：根据条件查询数据，出列表用，要做分页查询</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:34
     * @param flowSccredit：授权实体类
     * @param role 1:总收发（查看本单位）;2:部门综合（查看本部门）
     * @return String
     * @throws DAOException
     */
    @RequestMapping(value = "flowSccreditList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public PageImpl queryFlowSccredit(@ModelAttribute FlowSccredit flowSccredit, PageImpl pageImpl,String role){
        try{
            if (pageImpl.getPageSize() == null) {
                pageImpl.setPageSize(ConfigConsts.PAGE_SIZE);
            }
            if(StringUtils.isNotBlank(role)){
                pageImpl = flowSccreditService.queryFlowSccredit(pageImpl, flowSccredit,role);
            }else{
                pageImpl = flowSccreditService.queryFlowSccredit(pageImpl, flowSccredit);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * TODO 查询所有流程分类
     * @author 李利广
     * @date 2020年04月22日 14:08:01
     * @param
     * @return net.sf.json.JSONObject
     */
    @RequestMapping(value = "getAllFlowTypes", produces = "application/json; charset=utf-8")
    @ResponseBody
    public JSONObject getAllFlowTypes(){
        JSONObject json = new JSONObject();
        try{
            Collection<JSONObject> list = flowSccreditService.getAllFlowType();
            json.put("flows",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return json;
    }

}
