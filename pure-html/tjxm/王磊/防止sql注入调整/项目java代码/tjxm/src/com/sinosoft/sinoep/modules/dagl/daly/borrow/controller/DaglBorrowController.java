package com.sinosoft.sinoep.modules.dagl.daly.borrow.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.dao.DaglFileDao;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglBorrow;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglFile;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.services.DaglBorrowService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
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
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.*;

/**
 * @Auther:邴秀慧
 * @Description:借阅本单位档案控制层
 * @Date:2018/11/10 9:52
 */
@Controller
@RequestMapping("/dagl/daly/borrow")
public class DaglBorrowController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DaglBorrowService daglBorrowService;

    @Autowired
    private DaglFileDao daglFileDao;

    @Autowired
    private SysWaitNoflowService sysWatiNoflowService;

    @Resource
    UserInfoService userInfoService;

    /**
     * @Auther:邴秀慧
     * @Description:草稿列表查询
     * @Date:2018/11/10 9:57
     */
    @LogAnnotation(value = "query", opName = "草稿列表查询")
    @ResponseBody
    @RequestMapping("getlist")
    public PageImpl getList(PageImpl pageImpl, DaglBorrow daglBorrow) {
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            pageImpl = daglBorrowService.getPageListDraft(pageable, pageImpl, daglBorrow);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return pageImpl;
    }

    /**
     * @Auther:邴秀慧
     * @Description: 保存主子表
     * @Date:2018/11/10 9:57
     */
    @LogAnnotation(value = "save", opName = "保存主子表")
    @ResponseBody
    @RequestMapping("saveForm")
    public JSONObject saveForm(@RequestBody DaglBorrow daglBorrow) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            daglBorrow = daglBorrowService.saveForm(daglBorrow);
            json.put("flag", "1");
            json.put("data", daglBorrow);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.put("msg", "");
        }
        return json;
    }

    /**
     * @Auther:邴秀慧
     * @Description:删除主表数据
     * @Date:2018/11/10 10:06
     */
    @LogAnnotation(value = "delete", opName = "删除主表数据")
    @ResponseBody
    @RequestMapping("deleteDaglBorrow")
    public JSONObject deleteDaglBorrow(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            int result = daglBorrowService.deleteDaglBorrow(id);
            if (result != 0) {
                json.put("flag", "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @Auther:邴秀慧
     * @Description:删除字表数据
     * @Date:2018/11/10 10:06
     */
    @LogAnnotation(value = "delete", opName = "删除字表数据")
    @ResponseBody
    @RequestMapping("deleteDaglFile")
    public JSONObject deleteDaglFile(String ids) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            int result = daglBorrowService.deleteDaglFile(ids);
            if (result != 0) {
                json.put("flag", "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @Auther:邴秀慧
     * @Description:根据ID获取主子表的数据
     * @Date:2018/11/10 11:08
     */
    @LogAnnotation(value = "query", opName = "根据ID获取主子表的数据")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DaglBorrow daglBorrow = null;
        try {
            daglBorrow = daglBorrowService.getById(id);
            /*if(UserUtil.getCruUserRoleNo().indexOf("D701")>=0){//是否是处档案管理员
                daglBorrow.setIsChuAdmin("1");
            }*/
            json.put("flag", "1");
            json.put("data", daglBorrow);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @LogAnnotation(value = "query", opName = "根据ID获取borrow对象，用于判断是否正在借阅")
    @ResponseBody
    @RequestMapping("isInBorrow")
    public JSONObject isInBorrow(String ids) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        int num = 0;
        try {
            num = daglBorrowService.getBorrowById(ids);
            json.put("flag", "1");
            json.put("data", num);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @LogAnnotation(value = "query", opName = "续借或者重借获取对象")
    @ResponseBody
    @RequestMapping("innewAndReborrowEdit")
    public JSONObject innewAndReborrowEdit(String id, String flag) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DaglBorrow daglBorrow = new DaglBorrow();
        DaglBorrow oldDaglBorrow = new DaglBorrow();
        try {
            oldDaglBorrow = daglBorrowService.getById(id);
            if (StringUtils.isNotBlank(flag) && flag.equals("borrowRecordList")) {
                daglBorrow.setBorrowUserId(oldDaglBorrow.getBorrowUserId());
                daglBorrow.setBorrowUserName(oldDaglBorrow.getBorrowUserName());
                daglBorrow.setBorrowUnitName(oldDaglBorrow.getBorrowUnitName());
                daglBorrow.setBorrowUnitId(oldDaglBorrow.getBorrowUnitId());
                daglBorrow.setUsePurpose(oldDaglBorrow.getUsePurpose());
                daglBorrow.setPhone(oldDaglBorrow.getPhone());
                daglBorrow.setRemark(oldDaglBorrow.getRemark());
                daglBorrow.setBorrowDeptId(oldDaglBorrow.getBorrowDeptId());
                daglBorrow.setBorrowDeptName(oldDaglBorrow.getBorrowDeptName());
                daglBorrow.setFlowType(oldDaglBorrow.getFlowType());
               // daglBorrow.setApproveUserId(oldDaglBorrow.getApproveUserId());
               // daglBorrow.setApproveUserName(oldDaglBorrow.getApproveUserName());
                daglBorrow.setApproveUnitId(oldDaglBorrow.getApproveUnitId());
                daglBorrow.setApproveUnitName(oldDaglBorrow.getApproveUnitName());
                daglBorrow.setSubflag("0");
                daglBorrow.setId("");
                json.put("flag", "1");
                json.put("data", daglBorrow);
            } else {
                daglBorrow = oldDaglBorrow;
                json.put("flag", "1");
                json.put("data", daglBorrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @LogAnnotation(value = "save", opName = "保存主子表")
    @ResponseBody
    @RequestMapping("saveInnewForm")
    public JSONObject saveInnewForm(@RequestBody DaglBorrow daglBorrow, String flag) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            daglBorrow = daglBorrowService.saveForm(daglBorrow);
            json.put("flag", "1");
            json.put("data", daglBorrow);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.put("msg", "");
        }
        return json;
    }

    /**
     * @Auther:邴秀慧
     * @Description:
     * @Date:2018/11/17 14:27
     */
    @LogAnnotation(value = "update", opName = "修改流程状态")
    @ResponseBody
    @RequestMapping("updateFlag")
    public JSONObject updateFlag(String id, String subflag) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            daglBorrowService.updateSubFlag(id, subflag);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @LogAnnotation(value = "query", opName = "获取部门相关信息")
    @ResponseBody
    @RequestMapping("getDeptInfoByUserId")
    public JSONObject getDeptInfoByUserId(String userId) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            Map<String, SysFlowUserVo> list = new HashMap<String, SysFlowUserVo>();
            list = UserUtil.getUserVo(userId);
            SysFlowUserVo userInfo = list.get(userId);
            json.put("flag", "1");
            json.put("borrowUnitId",userInfo.getUserChushiId());
            json.put("borrowUnitName",userInfo.getUserChushiName());
            json.put("borrowDeptId",userInfo.getUserJuId());
            json.put("borrowDeptName",userInfo.getUserJuName());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        //userInfoService.getUserBase(userId,null);
        return json;
    }


   /*
    @LogAnnotation(value = "save", opName = "保存利用效果")
    @ResponseBody
    @RequestMapping("saveUseResult")
    public JSONObject saveUseResult(DaglFile daglFile) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if(StringUtils.isNotBlank(daglFile.getId())) {
            try {
                daglFile = daglBorrowService.saveUseResult(daglFile);
                //根据业务表id删除待办
               // sysWatiNoflowService.deleteWaitNoflow("",daglFile.getId(),"","","");
                json.put("flag", "1");
                json.put("data", daglFile);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                json.put("msg", "");
            }
        }
        return json;
    }
    */

    /**
     * 根据用户id获取用户的门禁卡卡号
     *
     * @param userId
     * @return
     * @author 王磊
     * @Date 2019年2月16日 下午2:18:50
     */
    @LogAnnotation(value = "query", opName = "根据userid查询门禁卡卡号")
    @ResponseBody
    @RequestMapping("getUserCardNumByUserId")
    public JSONObject getUserCardNumByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            userId = UserUtil.getCruUserId();
        }
        return daglBorrowService.getUserCardNumByUserId(userId);
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 借阅记录（主表查询）（不分页）
     * @Date 2019/2/27 10:19
     * @Param [pageImpl, daglBorrow]
     **/
    @LogAnnotation(value = "query", opName = "借阅记录（主表查询）（不分页）")
    @ResponseBody
    @RequestMapping("getBorrowList")
    public JSONObject getBorrowList(DaglBorrow daglBorrow) {

        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        json.put("flag", "0");
        try {
            List<DaglBorrow> daglBorrows = daglBorrowService.getBorrowList(daglBorrow);
            data.put("total", daglBorrows.size());
            data.put("rows", daglBorrows);
            json.put("flag", "1");
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 借阅记录子表数据（不分页）
     * @Date 2019/2/27 10:20
     * @Param [pageImpl, daglBorrow]
     **/
    @LogAnnotation(value = "query", opName = "借阅记录子表数据（不分页）")
    @ResponseBody
    @RequestMapping("getFileList")
    public List<DaglFile> getFileList(DaglBorrow daglBorrow) {

        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        json.put("flag", "0");
        List<DaglFile> daglFiles = new ArrayList<>();
        try {
            daglFiles = daglBorrowService.getFileList(daglBorrow);
            data.put("total", daglFiles.size());
            data.put("rows", daglFiles);
            json.put("flag", "1");
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return daglFiles;
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 传统借阅/线上借阅列表查询
     * @Date 2019/3/1 15:24
     * @Param [pageImpl, daglBorrow, BlStutas-办理状态, type-traditional/onLine]
     **/
    @LogAnnotation(value = "query", opName = "借阅记录（主表查询）（不分页）")
    @ResponseBody
    @RequestMapping("traditionalOrOnLineList")
    public PageImpl traditionalOrOnLineList(PageImpl pageImpl, DaglBorrow daglBorrow, String BlStutas, String type) {
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            pageImpl = daglBorrowService.traditionalOrOnLineList(pageable, pageImpl, daglBorrow, BlStutas, type);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return pageImpl;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 档案归还（主表查询）（不分页）
     * @Date 2019/3/2 15:37
     * @Param [daglBorrow]
     **/
    @LogAnnotation(value = "query", opName = "档案归还（主表查询）（不分页）")
    @ResponseBody
    @RequestMapping("getShouldReturnBorrowList")
    public JSONObject getShouldReturnBorrowList(DaglBorrow daglBorrow) {

        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        json.put("flag", "0");
        try {
            List<DaglBorrow> daglBorrows = daglBorrowService.getShouldReturnBorrowList(daglBorrow);
            data.put("total", daglBorrows.size());
            data.put("rows", daglBorrows);
            json.put("flag", "1");
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 档案管理-借阅记录
     * @Date 2019/3/1 15:28
     * @Param [pageImpl, daglFile]
     **/
    @LogAnnotation(value = "query", opName = "档案管理-借阅记录")
    @ResponseBody
    @RequestMapping("recordList")
    public PageImpl recordList(PageImpl pageImpl, DaglFile daglFile) {
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            pageImpl = daglBorrowService.recordList(pageable, pageImpl, daglFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return pageImpl;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 档案一键归还
     * @Date 2019/3/4 9:56
     * @Param [daglBorrow]
     **/
    @LogAnnotation(value = "update", opName = "档案一键归还")
    @ResponseBody
    @RequestMapping("returnThisBorrow")
    public JSONObject returnThisBorrow(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            json = daglBorrowService.returnThisBorrow(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 档案归还（单个）
     * @Date 2019/3/4 10:16
     * @Param [daglBorrow]
     **/
    @LogAnnotation(value = "update", opName = "档案归还（单个）")
    @ResponseBody
    @RequestMapping("returnOneBorrow")
    public JSONObject returnOneBorrow(String id, String isAuto) {
        JSONObject json = new JSONObject();
        try {
            json = daglBorrowService.returnOneBorrow(id, isAuto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 催还
     * @Date 2019/3/4 17:00
     * @Param [id-借阅主表的id, pressContent-催还内容]
     **/
    @LogAnnotation(value = "update", opName = "催还")
    @ResponseBody
    @RequestMapping("returnPress")
    public JSONObject returnPress(String id, String pressContent) {
        JSONObject json = new JSONObject();
        try {
            json = daglBorrowService.returnPress(id, pressContent);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 办理借出
     * @Date 2019/3/5 16:38
     * @Param [id-借阅主表的id, borrowDate-借阅日期, shouldReturnDate-归还日期]
     **/
    @LogAnnotation(value = "update", opName = "办理借出")
    @ResponseBody
    @RequestMapping("toConductBorrow")
    public JSONObject toConductBorrow(String id, String borrowDate, String shouldReturnDate) {
        JSONObject json = new JSONObject();
        try {
            json = daglBorrowService.toConductBorrow(id, borrowDate, shouldReturnDate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @Auther:邴秀慧
     * @Description:按次查询已借的记录
     * @Date:2019/3/8 15:07
     */
    @LogAnnotation(value = "query", opName = "按次查询已借的记录")
    @ResponseBody
    @RequestMapping("getRecordPerList")
    public PageImpl getRecordPerList(PageImpl pageImpl, DaglBorrow daglBorrow) {
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
            pageImpl = daglBorrowService.getRecordPerList(pageable, pageImpl, daglBorrow);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return pageImpl;
    }
}