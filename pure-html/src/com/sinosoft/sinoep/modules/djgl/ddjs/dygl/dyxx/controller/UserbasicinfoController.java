package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.controller;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.vo.DyTreeVO;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.hjxj.services.DwxtHjxjService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 党员信息控制层
 * @Author: 冯建海
 * @Date: 2018/8/28 18:35
 */
@Controller
@RequestMapping("djgl/ddjs/dygl/dyxx/")
public class UserbasicinfoController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DyxxService dyxxService;

    @Autowired
    private DwxtOrgService dwxtOrgService;

    @Autowired
    private DyxxTurnArourndService turnAourndService;

    @Autowired
    private DyxxTurnOutService turnOutService;

    @Autowired
    private DyxxWorkService workService;

    @Autowired
    private DyxxDrgeeService drgeeService;

    @Autowired
    private DyxxAdministrativeService administrativeService;

    @Autowired
    private DwxtHjxjService dwxtHjxjService;


    /**
     * 党员信息保存/更新
     * TODO
     * @author 冯建海
     * @Date 2018年8月23日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存党员信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(DdjsDyglUserbasicinfoEntity entity,DdjsDyglPartybasicinfoEntity partybasicinfoEntity,DdjsDyglIncreaseEntity increaseEntity) {
        JSONObject json = new JSONObject();
        try {
            entity = dyxxService.saveForm(entity,partybasicinfoEntity,increaseEntity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
         //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 党员信息列表查询
     * TODO
     * @author 冯建海
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党员信息列表")
    @ResponseBody
    @RequestMapping("getList")
    public PageImpl getZbdydhList(PageImpl pageImpl, DdjsDyglUserbasicinfoEntity entity,String type, DwxtOrg dwxtOrg) {
        List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
        if(list.size() > 0) {
            String ids = "";
            for (Map<String, Object> map : list) {
                if (map.get("type").toString().equals("org")) {
                    ids += map.get("id").toString() + ",";
                }
            }
            ids = ids.substring(0, ids.length() - 1);

            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());

            //        entity.setPartyOrganizationName(orgName);
            //        entity.setPartyOrganizationId(orgId);
            //entity.setTypeOfPersonnel(type);
            PageImpl pageList = dyxxService.getPageList(pageable, pageImpl, entity, type, ids);
            return pageList;
        }else{
            return  null;
        }
    }


    /**
     * 根据id逻辑删除党员信息 对应列表
     * @author 冯建海
     * @Date 2018年8月24日
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除支部党员大会对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(DdjsDyglUserbasicinfoEntity entity) {
        JSONObject json = new JSONObject();
        try {
            int n = dyxxService.delete(entity);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }


    /**
     * TODO 对编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月27日
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
            Object entity = dyxxService.getById(id, type);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * TODO 对组织关系转入编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改组织关系转入页面")
    @ResponseBody
    @RequestMapping("trunAroundEdit")
    public JSONObject trunAroundEdit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglAroundEntity entity = null;
        try {
            entity = turnAourndService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * 组织关系转入信息保存/更新
     * TODO
     * @author 冯建海
     * @Date 2018年8月30日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存组织关系转入信息")
    @RequestMapping("DdjsDyglAroundSave")
    @ResponseBody
    public JSONObject DdjsDyglAroundSave( DdjsDyglAroundEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = turnAourndService.save(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * TODO 对组织关系转入编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改组织关系转出页面")
    @ResponseBody
    @RequestMapping("outEdit")
    public JSONObject outEdit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglTurnoutEntity entity = null;
        try {
            entity = turnOutService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * 组织关系转入信息保存/更新
     * TODO
     * @author 冯建海
     * @Date 2018年8月30日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存组织关系转出信息")
    @RequestMapping("outSave")
    @ResponseBody
    public JSONObject outSave( DdjsDyglTurnoutEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = turnOutService.save(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 历史党员信息列表查询
     * TODO
     * @author 冯建海
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @return
     */
   /* @LogAnnotation(value = "query",opName = "查询党员信息列表")
    @ResponseBody
    @RequestMapping("getHistroyList")
    public PageImpl getHistroyList(PageImpl pageImpl, DdjsDyglUserbasicinfoEntity entity, DwxtOrg dwxtOrg) {
        List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
        if(list.size() > 0) {
            String ids = "";
            for (Map<String, Object> map : list) {
                if (map.get("type").toString().equals("org")) {
                    ids += map.get("id").toString() + ",";
                }
            }
            ids = ids.substring(0, ids.length() - 1);
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            PageImpl pageList = dyxxService.getHistroyList(pageable, pageImpl, entity,ids );
            return pageList;
        }else{
            return  null;
        }
    }*/
    /**
     * 历史党员信息列表查询
     * TODO
     * @author 冯建海
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党员信息列表")
    @ResponseBody
    @RequestMapping("getHistroyList")
    public PageImpl getHistroyList(PageImpl pageImpl, DdjsDyglHistoryEntity entity, DwxtOrg dwxtOrg) {
        List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
        if(list.size() > 0) {
            String ids = "";
            for (Map<String, Object> map : list) {
                if (map.get("type").toString().equals("org")) {
                    ids += map.get("id").toString() + ",";
                }
            }
            ids = ids.substring(0, ids.length() - 1);
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            PageImpl pageList = dyxxService.getHistroyList(pageable, pageImpl, entity,ids );
            return pageList;
        }else{
            return  null;
        }
    }

  /**
     * 党员信息党内职务列表查询
     * TODO
     * @author 冯建海
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党内职务列表")
    @ResponseBody
    @RequestMapping("getListByType")
    public PageImpl getListByType(PageImpl pageImpl,Object entity, String superId, String type) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        PageImpl pageList = dyxxService.getListForObject(pageable, pageImpl, entity, superId, type);
        return pageList;
    }

    @LogAnnotation(value = "query",opName = "查询党内职务列表")
    @ResponseBody
    @RequestMapping("getUserDuties")
    public List getUserDuties(String id) {
//        DdjsDyglUserbasicinfoEntity dyxx = dyxxService.findOne(id);
        return  dwxtHjxjService.findByUserId(id);
    }

    /**
     * TODO 党员队伍基本情况
     * @author 冯建海
     * @Date 2018年8月27日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询队伍基本情况")
    @ResponseBody
    @RequestMapping("getTeamSituation")
    public JSONObject getTeamSituation( String id,String type){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            Object entity = dyxxService.getTeamSituation();
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * 党员结构树
     * @param dwxtOrg
     * @return
     */
    @LogAnnotation(value = "query",opName = "党员结构树")
    @RequestMapping(value = "getTree", method = RequestMethod.GET)
    @ResponseBody
    public List<DyTreeVO> getTree(DwxtOrg dwxtOrg,String ryType) {
        return dyxxService.findChild(dwxtOrg,ryType);
    }

    /**
     * 工作岗位信息保存/更新
     * TODO
     * @author 冯建海
     * @Date 2018年8月30日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存工作岗位")
    @RequestMapping("workSave")
    @ResponseBody
    public JSONObject workSave( DdjsDyglWorkingEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = workService.save(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     *
     * @param id
     * @return
     */
    @LogAnnotation(value = "delete",opName = "删除工作岗位")
    @RequestMapping(value = "deleteWork", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteWork(String id) {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(id)) {
            try {
                workService.delete(id);
                json.put("flag", "1");
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                json.put("flag", "0");
            }
        }
        return json;
    }

    /**
     * TODO 对工作岗位编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改工作岗位页面")
    @ResponseBody
    @RequestMapping("workEdit")
    public JSONObject  workEdit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglWorkingEntity entity = null;
        try {
            entity = workService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    /**
     * 学位学历信息保存/更新
     * TODO
     * @author 冯建海
     * @Date 2018年8月30日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存学位学历")
    @RequestMapping("drgeeSave")
    @ResponseBody
    public JSONObject drgeeSave( DdjsDyglDegreeEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity =drgeeService.save(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     *
     * @param id
     * @return
     */
    @LogAnnotation(value = "delete",opName = "删除学位学历信息")
    @RequestMapping(value = "deleteDegree", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteDegree(String id) {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(id)) {
            try {
                drgeeService.delete(id);
                json.put("flag", "1");
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                json.put("flag", "0");
            }
        }
        return json;
    }

    /**
     * TODO 对学位学历编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改学历学位页面")
    @ResponseBody
    @RequestMapping("drgeeEdit")
    public JSONObject drgeeEdit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglDegreeEntity entity = null;
        try {
            entity = drgeeService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    @LogAnnotation(value = "save",opName = "保存行政职务")
    @RequestMapping("zzwSave")
    @ResponseBody
    public JSONObject xzzwSave( DdjsDyglAdministrativeEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity =administrativeService.save(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     *
     * @param id
     * @return
     */
    @LogAnnotation(value = "delete",opName = "删除行政职务")
    @RequestMapping(value = "deleteXzzw", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteXzzw(String id) {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(id)) {
            try {
                administrativeService.delete(id);
                json.put("flag", "1");
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                json.put("flag", "0");
            }
        }
        return json;
    }

    /**
     * TODO 对学位学历编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改行政职务页面")
    @ResponseBody
    @RequestMapping("dzzwEdit")
    public JSONObject xzzwEdit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglAdministrativeEntity entity = null;
        try {
            entity = administrativeService.getById(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    @LogAnnotation(value = "query",opName = "查询组织对应单位")
    @ResponseBody
    @RequestMapping("getUnitId")
    public String getUnitId(String id){
        DwxtOrg dwxtOrg = dwxtOrgService.findOne(id);
        return dwxtOrg.getAssociativeUnitId();
    }

    /**
     * TODO 申请人简要信息
     * @author 李帅
     * @Date 2018年10月30日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党员简要信息")
    @ResponseBody
    @RequestMapping("getApplicantStatistics")
    public JSONObject getApplicantStatistics( String id,String type,String orgId,String annual, DwxtOrg dwxtOrg){
        List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
        if(list.size() > 0) {
            String ids = "";
            for (Map<String, Object> map : list) {
                if (map.get("type").toString().equals("org")) {
                    ids += map.get("id").toString() + ",";
                }
            }
            ids = ids.substring(0, ids.length() - 1);
            JSONObject json = new JSONObject();
            json.put("flag", "0");
            try {
                Object entity = dyxxService.applicantStatistics(annual,ids);
                json.put("flag", "1");
                json.put("data", entity);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
            return json;
        }else{
            return  null;
        }
    }
    /**
     * 根据id逻辑删除历史党员信息 对应列表
     * @author 李帅
     * @Date 2018年11月8日
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除支部党员大会对应列表")
    @ResponseBody
    @RequestMapping("deleteHistory")
    public JSONObject deleteHistory(DdjsDyglHistoryEntity entity) {
        JSONObject json = new JSONObject();
        try {
            int n = dyxxService.deleteHistory(entity);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }

    /**
     * 保存人员基本信息保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年11月22日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存人员基本信息")
    @RequestMapping("saveUserForm")
    @ResponseBody
    public JSONObject saveUserForm(DdjsDyglUserbasicinfoEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = dyxxService.saveUserForm(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
    /**
     * 保存党员基本情况保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年11月22日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存党员基本情况")
    @RequestMapping("savePartyForm")
    @ResponseBody
    public JSONObject savePartyForm(DdjsDyglPartybasicinfoEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = dyxxService.savePartyForm(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
    /**
     * 党员增加情况保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年11月22日
     * @param entity
     * @return
     */
    @LogAnnotation(value = "save",opName = "保存党员增加情况")
    @RequestMapping("saveIncreaseForm")
    @ResponseBody
    public JSONObject saveIncreaseForm(DdjsDyglIncreaseEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = dyxxService.saveIncreaseForm(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

      /**
     * 获取个人信息
     * TODO
     * @author 李帅
     * @Date 2018年11月22日
     * @param id
     * @return
     */
    @LogAnnotation(value = "get",opName = "获取个人信息")
    @RequestMapping("persondoc")
    @ResponseBody
    public JSONObject persondoc(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", 0);
        try {
            Map<String,SysFlowUserVo> list = new HashMap<String,SysFlowUserVo>();

            list = UserUtil.getUserVo(id);
            SysFlowUserVo ll = list.get(id);
            json.put("flag", 1);
            json.put("userDeptName", ll.getUserDeptName());
            json.put("positionName", ll.getPositionName());
            json.put("positionLevel", ll.getPositionLevel());
            json.put("identity", ll.getIdentity());
            json.put("imagePath", ll.getImagePath());
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
    /**
     * 党员增加情况保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年11月22日
     * @param userId
     * @return
     */
    @LogAnnotation(value = "get",opName = "判断以前是否存在该党员")
    @RequestMapping("queryIsUser")
    @ResponseBody
    public JSONObject queryIsUser(String userId) {
        JSONObject json = new JSONObject();
        try {
            List<DdjsDyglUserbasicinfoEntity> userList = dyxxService.findByUserIdAndVisible(userId, CommonConstants.VISIBLE[0]);
            DdjsDyglUserbasicinfoEntity  entity = new DdjsDyglUserbasicinfoEntity();
            if(userList.size()>0){
                entity = userList.get(0)==null? new DdjsDyglUserbasicinfoEntity():userList.get(0);
            }
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }


}
