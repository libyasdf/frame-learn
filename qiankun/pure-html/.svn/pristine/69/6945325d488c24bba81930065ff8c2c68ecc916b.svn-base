package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.entity.DdjsSqrglPartybasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.service.SqrxxServiceI;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 申请人管理-申请人员情况-Controller
 *
 * author 李帅
 */
@Controller
@RequestMapping("djgl/ddjs/sqrgl/sqrxx/")
public class SqrxxController {
    @Autowired
private SqrxxServiceI sqrxxService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 申请人信息保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年9月13日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存申请人信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(DdjsDyglUserbasicinfoEntity entity, DdjsSqrglPartybasicinfoEntity partybasicinfoEntity, DdjsDyglWorkingEntity workingEntity,
                           DdjsDyglDegreeEntity degreeEntity) {
        JSONObject json = new JSONObject();
        try {
            entity = sqrxxService.saveForm(entity,partybasicinfoEntity,workingEntity,degreeEntity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * TODO 对编辑数据进行渲染
     * @author 李帅
     * @Date 2018年9月13日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id,String type){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            Object entity = sqrxxService.getById(id, type);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * 申请人信息 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageImpl
     * @param partybasicinfo
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询申请人信息列表")
    @ResponseBody
    @RequestMapping("getSqrxxList")
    public PageImpl getSqrxxList(PageImpl pageImpl, DdjsSqrglPartybasicinfoEntity partybasicinfo) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        String startTime ="";
        String endTime ="";
        if (StringUtils.isNotBlank(partybasicinfo.getApplicationTime())) {
            startTime =partybasicinfo.getApplicationTime().substring(0,(partybasicinfo.getApplicationTime().length()+1)/2-1).trim();
            endTime =partybasicinfo.getApplicationTime().substring((partybasicinfo.getApplicationTime().length()+1)/2,partybasicinfo.getApplicationTime().length()).trim();
        }
        PageImpl pageList = sqrxxService.getPageList(pageable, pageImpl, partybasicinfo,startTime,endTime);

        return pageList;
    }

    /**
     * 新增申请人时判断该人员是否已经为申请人
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询人员是否已经为申请人")
    @ResponseBody
    @RequestMapping("queryUser")
    public JSONObject queryUser( String userId){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglUserbasicinfoEntity  entity =null;
        try {
            entity = sqrxxService.queryUser(userId)==null?new DdjsDyglUserbasicinfoEntity():sqrxxService.queryUser(userId);
            if(entity.getTypeOfPersonnel()!=null&&(!entity.getTypeOfPersonnel().equals(""))){
                String[] typeSplit = entity.getTypeOfPersonnel().split(",");
                List<String> list1 = Arrays.asList(typeSplit);
              //  01—申请人、02-积极分子、03-发展对象、04-预备党员、05-党员、06-历史党员
                if(list1.contains("05")){
                    entity.setTypeOfPersonnel("党员");
                }else if(list1.contains("04")){
                    entity.setTypeOfPersonnel("预备党员");
                }else if(list1.contains("03")){
                    entity.setTypeOfPersonnel("发展对象");
                }else if(list1.contains("02")){
                    entity.setTypeOfPersonnel("积极分子");
                }else if(list1.contains("01")){
                    entity.setTypeOfPersonnel("申请人");
                }
            }
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
}
