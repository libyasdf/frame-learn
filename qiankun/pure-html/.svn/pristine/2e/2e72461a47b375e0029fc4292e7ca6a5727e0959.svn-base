package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity.DfglEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity.DzbDfgEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.service.DfglServiceI;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * TODO 党的建设 党费管理Controller
 * @Author: 李帅
 * @Date: 2018/9/9 11:06
 */
@Controller
@RequestMapping("djgl/ddjs/dzz/dfgl/")
public class DfglController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DfglServiceI dfglService;
    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 党费管理 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dfglEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存党费管理")
    @RequestMapping("saveDfgl")
    @ResponseBody
    public JSONObject saveDfgl(DfglEntity dfglEntity) {
        JSONObject json = new JSONObject();
        try {
            dfglEntity = dfglService.saveDfgl(dfglEntity);
            json.put("flag", 1);
            json.put("data", dfglEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 党费管理 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageImpl
     * @param dfglEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党费管理列表")
    @ResponseBody
    @RequestMapping("getDfglList")
    public PageImpl getDfglList(PageImpl pageImpl, DfglEntity dfglEntity, DwxtOrg dwxtOrg,String type) {
        if(!type.equals("user")){
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
                String startTime ="";
                String endTime ="";
                if (StringUtils.isNotBlank(dfglEntity.getAnnual())) {
                    startTime =dfglEntity.getAnnual().substring(0,(dfglEntity.getAnnual().length()+1)/2-1).trim();
                    endTime =dfglEntity.getAnnual().substring((dfglEntity.getAnnual().length()+1)/2,dfglEntity.getAnnual().length()).trim();
                }
                PageImpl pageList = dfglService.getPageList(pageable, pageImpl, dfglEntity,startTime,endTime,ids,type);

                return pageList;
            }else{
                return null;
            }
        }else{
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            String startTime ="";
            String endTime ="";
            if (StringUtils.isNotBlank(dfglEntity.getAnnual())) {
                startTime =dfglEntity.getAnnual().substring(0,(dfglEntity.getAnnual().length()+1)/2-1).trim();
                endTime =dfglEntity.getAnnual().substring((dfglEntity.getAnnual().length()+1)/2,dfglEntity.getAnnual().length()).trim();
            }
            PageImpl pageList = dfglService.getPageList(pageable, pageImpl, dfglEntity,startTime,endTime,"",type);

            return pageList;
        }

    }


    /**
     * 根据id逻辑删除党费管理 对应列表
     * @author 李帅
     * @Date 2018年8月28日
     * @param dfgl
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除党费管理对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(DfglEntity dfgl) {
        JSONObject json = new JSONObject();
        try {
            int n = dfglService.delete(dfgl);
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
     * @Date 2018年8月28日
     * @param
     * @param
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String partyId,String annual,String month){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DfglEntity dfgl = null;
        try {
            dfgl = dfglService.getById(partyId,annual,month);
            json.put("flag", "1");
            json.put("data", dfgl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
    /**
     * 新增党费时判断该人员该月是否已交党费
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询人员该月是否已交党费")
    @ResponseBody
    @RequestMapping("queryFee")
    public JSONObject queryFee( DfglEntity dfgl){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            dfgl = dfglService.queryFee(dfgl);

            json.put("flag", "1");
            json.put("data", dfgl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    /**
     * TODO 计算某一个党支部某个月所有党员的党费信息
     * @author 李帅
     * @Date 2018年11月27日
     * @param
     * @param
     * @return
     */
    @LogAnnotation(value = "query",opName = "计算某一个党支部某个月所有党员的党费信息")
    @ResponseBody
    @RequestMapping(value = "list")
    public JSONObject list( int pageNumber ,String month ,String year, DwxtOrg dwxtOrg,String opertation){
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
            JSONObject ndjhJson = null;
            try {
                ndjhJson =dfglService.getByIdVal(pageNumber ,ids,month,year,opertation);
                json.put("flag", "1");
                json.put("data", ndjhJson);
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
     * 按党支部保存党员党费数据
     * TODO
     * @author 李帅
     * @Date 2018年11月27日
     * @param
     * @return
     */
    @LogAnnotation(value = "save",opName = "按党支部保存党员党费数据")
    @RequestMapping("saveDzbDfgl")
    @ResponseBody
    public JSONObject saveDzbDfgl(@RequestBody DzbDfgEntity dzbDfgl) {
        JSONObject json = new JSONObject();
        try {
            dzbDfgl=dfglService.saveDzbForm(dzbDfgl);
            json.put("data",dzbDfgl);
            json.put("flag", 1);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * TODO 在党支部视角查看的list列表
     * @author 李帅
     * @Date 2018年11月28日
     * @param
     * @param
     * @return
     */
    @LogAnnotation(value = "query",opName = "在党支部视角查看的list列表")
    @ResponseBody
    @RequestMapping(value = "dzbList")
    public JSONObject dzbList(String partyOrganizationId ,String annual, DwxtOrg dwxtOrg){
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
            JSONObject ndjhJson = null;
            try {
                ndjhJson =dfglService.getDzbByIdVal(dwxtOrg.getId(),annual,ids);
                json.put("flag", "1");
                json.put("data", ndjhJson);
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
     * 得到某一个党支部有多少个人
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    @LogAnnotation(value = "query",opName = "得到某一个党支部有多少个人")
    @ResponseBody
    @RequestMapping("queryTotal")
    public JSONObject queryTotal( String ids , String month , String year){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            BigDecimal total = dfglService.getTotal(ids,month,year);

            json.put("flag", "1");
            json.put("data", total);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

}
