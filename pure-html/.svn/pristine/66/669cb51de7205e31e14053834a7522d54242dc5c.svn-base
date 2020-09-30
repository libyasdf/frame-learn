package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.DymzpyEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.MzpyEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.service.MzpyServiceI;
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

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 党组织管理 民主评议Controller
 * @Author: 李帅
 * @Date: 2018/9/9 11:06
 */
@Controller
@RequestMapping("djgl/ddjs/dzz/mzpy/")
public class MzpyController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MzpyServiceI mzpyService;

    /**
     * 民主评议 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param mzpyEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存民主评议")
    @RequestMapping("saveMzpy")
    @ResponseBody
    public JSONObject saveMzpy(MzpyEntity mzpyEntity) {
        JSONObject json = new JSONObject();
        try {
            mzpyEntity = mzpyService.saveMzpy(mzpyEntity);
            json.put("flag", 1);
            json.put("data", mzpyEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 民主评议 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param pageImpl
     * @param mzpyEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询民主评议列表")
    @ResponseBody
    @RequestMapping("getMzpyList")
    public PageImpl getMzpyList(PageImpl pageImpl, MzpyEntity mzpyEntity) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        String startDate ="";
        String endDate ="";
        if (StringUtils.isNotBlank(mzpyEntity.getStartTime())) {
            startDate =mzpyEntity.getStartTime().substring(0,(mzpyEntity.getStartTime().length()+1)/2-1).trim();
            endDate =mzpyEntity.getStartTime().substring((mzpyEntity.getStartTime().length()+1)/2,mzpyEntity.getStartTime().length()).trim();
        }
        PageImpl pageList = mzpyService.getPageList(pageable, pageImpl, mzpyEntity,startDate,endDate);

        return pageList;
    }


    /**
     * 根据id逻辑删除民主评议会 对应列表
     * @author 李帅
     * @Date 2018年9月9日
     * @param mzpy
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除民主评议对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(MzpyEntity mzpy) {
        JSONObject json = new JSONObject();
        try {
            int n = mzpyService.delete(mzpy);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }


    /**
     * TODO 打开只读、修改页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年9月9日
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
        MzpyEntity mzpy = null;
        try {
            mzpy = mzpyService.getById(id);
            json.put("flag", "1");
            json.put("data", mzpy);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * 党员民主评议 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param dymzpyEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存党员民主评议")
    @RequestMapping("saveDymzpy")
    @ResponseBody
    public JSONObject saveDymzpy(DymzpyEntity dymzpyEntity) {
        JSONObject json = new JSONObject();
        try {
            dymzpyEntity = mzpyService.saveDymzpy(dymzpyEntity);
            json.put("flag", 1);
            json.put("data", dymzpyEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 党员民主评议 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param pageImpl
     * @param dymzpyEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党员民主评议列表")
    @ResponseBody
    @RequestMapping("getMzpyListDymzpy")
    public PageImpl getMzpyListDymzpy(PageImpl pageImpl, DymzpyEntity dymzpyEntity,boolean isAll) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        PageImpl pageList = mzpyService.getPageListDymzpy(pageable, pageImpl, dymzpyEntity,isAll);
        return pageList;
    }
    /**
     * 根据id逻辑删除党员民主评议会 对应列表
     * @author 李帅
     * @Date 2018年9月10日
     * @param dymzpy
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除党员民主评议对应列表")
    @ResponseBody
    @RequestMapping("deleteByIdDymzpy")
    public JSONObject deleteByIdDymzpy(DymzpyEntity dymzpy) {
        JSONObject json = new JSONObject();
        try {
            int n = mzpyService.deleteDymzpy(dymzpy);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }
    /**
     * TODO 打开只读、修改页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年9月10日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改党员民主评议页面")
    @ResponseBody
    @RequestMapping("editDymzpy")
    public JSONObject editDymzpy( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DymzpyEntity mzpy = null;
        try {
            mzpy = mzpyService.getByIdDymzpy(id);

            json.put("flag", "1");
            json.put("data", mzpy);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * 保存多条党员民主评议 方法
     * TODO
     * @author 李帅
     * @Date 2018年9月19日
     *
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存多条党员民主评议")
    @RequestMapping("saveDymzpyNew")
    @ResponseBody
    public JSONObject saveDymzpyNew(String partyNames,String partyIds, String reviewid,String startTime,String endTime ) {
        JSONObject json = new JSONObject();
        List<DymzpyEntity> dymzpyList  =new ArrayList<DymzpyEntity>();
        try {
            dymzpyList = mzpyService.saveDymzpys(partyNames,partyIds,reviewid,startTime,endTime);
            json.put("flag", 1);
            json.put("data", dymzpyList);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
}
