package com.sinosoft.sinoep.workflow.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sinosoft.ep.webform.tool.Utiles;
import com.sinosoft.log.LogHelper;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.workflow.constant.WorkFlowConfigConsts;
import com.sinosoft.sinoep.workflow.dao.FlowSccreditDAO;
import com.sinosoft.sinoep.workflow.model.FlowSccredit;
import com.sinosoft.util.exception.DAOException;
import com.sinosoft.util.tool.JDateToolkit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import workflow.spring.FlowDataService;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：待办授权</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：操作待办授权基本信息</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Service
public class FlowSccreditServiceImpl implements FlowSccreditService {

    private static LogHelper log = new LogHelper(FlowSccreditServiceImpl.class);
    @Autowired
    private FlowSccreditDAO flowSccreditDAO;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 
     * <B>方法名称：添加多条授权信息</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#addAuth(java.lang.String[],
     *      com.sinosoft.sinoep.workflow.model.FlowSccredit)
     */
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
    public String addAuth(String[] fileTypes, FlowSccredit flowSccredit) throws DAOException {
        String addInfo = "授权失败，授权信息已经存在！";
        MessageUser messageUser = userInfoService.getUserInfoById(flowSccredit.getUserid());
        flowSccredit.setCre_user(flowSccredit.getUserid());
        if (messageUser.getUserInfo().size() > 0) {
            UserInfo userInfo = messageUser.getUserInfo().get(0);
            flowSccredit.setUsername(userInfo.getUserFullName());
            flowSccredit.setCre_username(userInfo.getUserFullName());
        }
        String deptName = userInfoService.getDeptFullName(flowSccredit.getDeptid());
        flowSccredit.setDeptname(deptName);
        MessageUser sccredituser = userInfoService.getUserInfoById(flowSccredit.getSccredituserid());
        if (sccredituser.getUserInfo().size() > 0) {
            UserInfo sccredituserInfo = sccredituser.getUserInfo().get(0);
            flowSccredit.setSccreditusername(sccredituserInfo.getUserFullName());
        }
        String sccreditdeptName = userInfoService.getDeptFullName(flowSccredit.getSccreditdeptid());
        flowSccredit.setSccreditdeptname(sccreditdeptName);
        String time = JDateToolkit.getNowDate1();
        Vector vec = new Vector();
        String content = "";
        for (int i = 0; i < fileTypes.length; i++) {
            Map<String, String> map = getAllFlowType();
            if (!existSccrRecord(fileTypes[i], flowSccredit.getUserid(), flowSccredit.getSccbegindate(),
                    flowSccredit.getSccenddate(), flowSccredit.getDeptid())) {
                return addInfo = map.get(fileTypes[i]) + ":不能在同一时间段内授权给多个人";
            }
            else {
                // 判断是否允许再授权
                boolean flag = checkAuto(fileTypes[i], flowSccredit.getUserid());
                if (flag) {
                    FlowSccredit Vo = new FlowSccredit();
                    Vo.setFiletypename(map.get(fileTypes[i]));
                    Vo.setCre_username(flowSccredit.getCre_username());
                    Vo.setUsername(flowSccredit.getUsername());
                    Vo.setDeptname(flowSccredit.getDeptname());
                    Vo.setSccreditusername(flowSccredit.getSccreditusername());
                    Vo.setSccreditdeptname(flowSccredit.getSccreditdeptname());
                    Vo.setFiletype(fileTypes[i]);
                    Vo.setSccredittype(flowSccredit.getSccredittype());
                    Vo.setIssccredit(flowSccredit.getIssccredit());
                    Vo.setUserid(flowSccredit.getUserid());
                    Vo.setDeptid(flowSccredit.getDeptid());
                    Vo.setSccredituserid(flowSccredit.getSccredituserid());
                    Vo.setSccreditdeptid(flowSccredit.getSccreditdeptid());
                    Vo.setZ_status("1");
                    Vo.setSccbegindate(flowSccredit.getSccbegindate());
                    Vo.setSccenddate(flowSccredit.getSccenddate());
                    Vo.setSccreditdate(time);
                    Vo.setCre_user(flowSccredit.getCre_user());
                    Vo.setSysId(flowSccredit.getSysId());
                    Vo.setOrgId(flowSccredit.getOrgId());
                    vec.addElement(Vo);
                    content += Vo.getFiletypename() + " ";
                }
                else {
                    return addInfo = "类型为[" + map.get(fileTypes[i]) + "]该授权信息不允许再授权";
                }
            }
        }
        if (vec != null) {
            flowSccreditDAO.addBatch(vec);
            addInfo = "授权成功！";
        }
        return addInfo;

    }

    /**
     * 
     * <B>方法名称：是否存在授权记录</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月8日 下午2:43:54
     * @param fileType：文件类型
     * @param userID：用户id
     * @param startDate：开始时间
     * @param endDate：结束时间
     * @param deptId：部门id
     * @return boolean
     * @throws DAOException
     */
    public boolean existSccrRecord(String fileType, String userID, String startDate, String endDate, String deptId)
            throws DAOException {
        return flowSccreditDAO.existSccrRecord(fileType, userID, startDate, endDate, deptId);
    }

    /**
     * 
     * <B>方法名称：检查是否允许再授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:34:47
     * @param fileTypes：文件类型
     * @param userID：用户id
     * @return
     * @throws DAOException boolean
     */
    private boolean checkAuto(String fileTypes, String userID) throws DAOException {
        return flowSccreditDAO.checkAuto(fileTypes, userID);
    }

    /**
     * 
     * <B>方法名称：得到当前日期</B><BR>
     * <B>概要说明：以中文显示，包括时、分、秒</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:34:56
     * @return String
     */
    public String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        String NowYear = Integer.toString(calendar.get(Calendar.YEAR));
        String NowMonth = Integer.toString((calendar.get(Calendar.MONTH) + 1));
        String NowDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String NowHour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String NowMinute = Integer.toString(calendar.get(Calendar.MINUTE));
        String NowDate = NowYear + "年" + NowMonth + "月" + NowDay + "日 " + NowHour + "时" + NowMinute + "分";
        return NowDate;
    }

    /**
     * 
     * <B>方法名称：根据创建人获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @throws DAOException
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#queryFlowSccredit(int, int,
     *      com.sinosoft.sinoep.workflow.model.FlowSccredit, java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@Override
    public PageImpl queryFlowSccredit(Pageable pageable, PageImpl pageImpl, FlowSccredit flowSccredit, String userid)
            throws DAOException {
    	pageImpl.setFlag("0");
    	flowSccredit.setCre_user(userid);
        HqlHelper hql = new HqlHelper(FlowSccredit.class);
        if (!Utiles.isNullStr(flowSccredit.getCre_user())) {
            hql.addCondition("cre_user=?", flowSccredit.getCre_user());
        }
        if (!Utiles.isNullStr(flowSccredit.getFiletypename())) {
            hql.addCondition("filetypename like ? ", "%" + flowSccredit.getFiletypename() + "%");
        }
        if (!Utiles.isNullStr(flowSccredit.getSccreditusername())) {
            hql.addCondition("sccreditusername like ? ", "%" + flowSccredit.getSccreditusername() + "%");
        }
        hql.addOrder("sccreditdate", false);
        Page page = flowSccreditDAO.queryFlowSccredit(pageImpl.getPageNumber(), pageImpl.getPageSize(), flowSccredit, hql);
        //草稿列表，添加操作列
        List<FlowSccredit> content = page.getRecordList();
        for (FlowSccredit sccredit : content) {
        	sccredit.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        pageImpl.setFlag("1");
        pageImpl.getData().setRows(content);
        pageImpl.getData().setTotal(page.getTotalResult());
        return pageImpl;

    }

    /**
     * 
     * <B>方法名称：删除授权信息</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#delAuth(java.lang.String)
     */
    @SuppressWarnings("unused")
	@Override
    public String delAuth(String ids) {
        String res = "删除授权信息失败！";
        try {
            String[] id = ids.split(",");
            for (String o : id) {
                FlowSccredit flowSccredit = viewAuth(o);
            }
            flowSccreditDAO.delAuth(id);
            res = "删除授权信息成功！";
        }catch (DAOException e) {
            log.error("删除授权信息失败!", e);

        }
        return res;
    }

    /**
     * 
     * <B>方法名称：获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#viewAuth(java.lang.String)
     */
    @Override
    public FlowSccredit viewAuth(String id) {
        try {
            FlowSccredit flowSccredit = (FlowSccredit) flowSccreditDAO.getById(id);
            return flowSccredit;
        }
        catch (Exception e) {
            log.error("查看待办授权失败！", e);
        }
        return null;
    }

    /**
     * 
     * <B>方法名称：修改待办授权信息</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#updateAuth(java.lang.String, java.lang.String,
     *      java.lang.String, com.sinosoft.sinoep.workflow.model.FlowSccredit)
     */
    @Override
    public String updateAuth(String deptId, String sccreditdeptId, String fileTypeItem, FlowSccredit flowSccredit) {
        flowSccredit.setDeptid(deptId);
        flowSccredit.setSccreditdeptid(sccreditdeptId);
        flowSccredit.setFiletype(fileTypeItem);
        String res = "修改授权信息失败！";
        try {
            MessageUser messageUser = userInfoService.getUserInfoById(flowSccredit.getUserid());
            if (messageUser.getUserInfo().size() > 0) {
                UserInfo userInfo = messageUser.getUserInfo().get(0);
                flowSccredit.setUsername(userInfo.getUserFullName());
            }
            String deptName = userInfoService.getDeptName(flowSccredit.getDeptid());
            flowSccredit.setDeptname(deptName);
            MessageUser sccredituser = userInfoService.getUserInfoById(flowSccredit.getSccredituserid());
            if (sccredituser.getUserInfo().size() > 0) {
                UserInfo sccredituserInfo = sccredituser.getUserInfo().get(0);
                flowSccredit.setSccreditusername(sccredituserInfo.getUserFullName());
            }
            String sccreditdeptName = userInfoService.getDeptName(flowSccredit.getSccreditdeptid());
            flowSccredit.setSccreditdeptname(sccreditdeptName);
            Map<String, String> map = getAllFlowType();
            FlowSccredit f = (FlowSccredit) flowSccreditDAO.getById(flowSccredit.getSccreditid());
            f.setFiletypename(map.get(flowSccredit.getFiletype()));
            f.setCre_username(flowSccredit.getCre_username());
            f.setUsername(flowSccredit.getUsername());
            f.setDeptname(flowSccredit.getDeptname());
            f.setSccreditusername(flowSccredit.getSccreditusername());
            f.setSccreditdeptname(flowSccredit.getSccreditdeptname());
            f.setSccredittype(flowSccredit.getSccredittype());
            f.setFiletype(flowSccredit.getFiletype());
            f.setUserid(flowSccredit.getUserid());
            f.setSccredituserid(flowSccredit.getSccredituserid());
            f.setSccbegindate(flowSccredit.getSccbegindate());
            f.setSccenddate(flowSccredit.getSccenddate());
            f.setIssccredit(flowSccredit.getIssccredit());
            flowSccreditDAO.update(f);
            res = "修改授权信息成功！";
        }
        catch (Exception e) {
            log.error("修改授权信息失败！", e);
        }
        return res;
    }

    /**
     * 
     * <B>方法名称：获取所有流程分类</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午10:23:26
     * @return Map<String,String>
     */
    public Map<String, String> getAllFlowType() {
        Map<String, String> map = new HashMap<String, String>();
        String json = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            FlowDataService flowDataService = (FlowDataService) SpringBeanUtils.getBean("flowDataService");
            json = flowDataService.getWorkflowes();
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/flowData/getWorkflowes";
            json = HttpRequestUtil.sendGet(url, "");
        }
        JSONArray jarry = JSONArray.fromObject(json);
        for (int i = 0; i < jarry.size(); i++) {
            String type = jarry.get(i).toString();
            JSONObject obj = JSONObject.fromObject(type);
            map.put(obj.get("filetypeid").toString(), obj.get("filetypename").toString());
        }
        return map;
    }

    /**
     * 
     * <B>方法名称：根据条件查询待办授权数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#findAll(com.sinosoft.sinoep.workflow.model.FlowSccredit)
     */
    @Override
    public List<FlowSccredit> findAll(FlowSccredit flowSccredit) {
        try {
            HqlHelper hql = new HqlHelper(FlowSccredit.class);
            if (!Utiles.isNullStr(flowSccredit.getUserid())) {
                hql.addCondition("userid=?", flowSccredit.getUserid());
            }
            if (!Utiles.isNullStr(flowSccredit.getDeptid())) {
                hql.addCondition("deptid=?", flowSccredit.getDeptid());
            }
            if (!Utiles.isNullStr(flowSccredit.getFiletype())) {
                hql.addCondition("filetype=?", flowSccredit.getFiletype());
            }
            if (!Utiles.isNullStr(flowSccredit.getSccbegindate())) {
                hql.addCondition("sccbegindate <= ?", flowSccredit.getSccbegindate());
            }
            if (!Utiles.isNullStr(flowSccredit.getSccenddate())) {
                hql.addCondition("sccenddate >= ?", flowSccredit.getSccenddate());
            }
            if (!Utiles.isNullStr(flowSccredit.getZ_status())) {
                hql.addCondition("z_status=?", flowSccredit.getZ_status());
            }
            if (!Utiles.isNullStr(flowSccredit.getSysId())) {
                hql.addCondition("sysId=?", flowSccredit.getSysId());
            }
            if (!Utiles.isNullStr(flowSccredit.getSccredituserid())) {
                hql.addCondition("sccredituserid=?", flowSccredit.getSccredituserid());
            }
            if (!Utiles.isNullStr(flowSccredit.getSccreditdeptid())) {
                hql.addCondition("sccreditdeptid=?", flowSccredit.getSccreditdeptid());
            }
            return flowSccreditDAO.findAll(hql);
        }
        catch (DAOException e) {
            log.error("查询待办授权数据失败!", e);
        }
        return null;
    }

    /**
     * 
     * <B>方法名称：根据条件查询待办授权数据</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#findAllForVec(com.sinosoft.sinoep.workflow.model.FlowSccredit)
     */
    @Override
    public Vector<FlowSccredit> findAllForVec(FlowSccredit flowSccredit) {
        List<FlowSccredit> flowSccreditList = findAll(flowSccredit);
        Vector<FlowSccredit> v = new Vector<FlowSccredit>();
        if (!Utiles.isNull(flowSccreditList))
            v.addAll(flowSccreditList);
        return v;
    }

    /**
     * 
     * <B>方法名称：保存待办授权</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#save(java.lang.String,
     *      com.sinosoft.sinoep.workflow.model.FlowSccredit, java.lang.String[])
     */
    @Override
    public String save(String userid, FlowSccredit flowSccredit, String[] fileTypeItem)
            throws DAOException {
        JSONObject res = new JSONObject();
        res.put("result", "0");
        String result = "";
        if (Utiles.isNull(fileTypeItem))
            return res.toString();
        if (StringUtils.isNotBlank(userid)) {
            String deptId = userInfoService.getDeptId(userid);
            //根据被授权人id获取被授权部门id
            String sccreditdeptId = userInfoService.getDeptId(flowSccredit.getSccredituserid());
            flowSccredit.setUserid(userid);
            flowSccredit.setDeptid(deptId);
            flowSccredit.setSccreditdeptid(sccreditdeptId);
            flowSccredit.setSysId(ConfigConsts.SYSTEM_ID);
            flowSccredit.setOrgId(UserUtil.getCruOrgId());
            //保存授权信息
            result = addAuth(fileTypeItem, flowSccredit);
            res.put("result", "1");
        }
        res.put("msg", result);
        return res.toString();
    }

    /**
     * 
     * <B>方法名称：获取所有流程分类供待办授权使用</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#getLicenseFlowType(java.lang.String)
     */
    @Override
    public String getLicenseFlowType(String userId) {
        JSONArray arry = new JSONArray();
        String json = "";
        if (StringUtils.isNotBlank(userId)) {
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                FlowDataService flowDataService = (FlowDataService) SpringBeanUtils.getBean("flowDataService");
                json = flowDataService.getWorkflowes();
            }
            else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
                String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/flowData/getWorkflowes";
                json = HttpRequestUtil.sendGet(url, "");
            }
            JSONArray jarry = JSONArray.fromObject(json);
            for (int i = 0; i < jarry.size(); i++) {
                JSONObject flowType = new JSONObject();
                String type = jarry.get(i).toString();
                JSONObject obj = JSONObject.fromObject(type);
                flowType.put("flowTypeId", obj.get("filetypeid").toString());
                flowType.put("flowTypeName", obj.get("filetypename").toString());
                arry.add(flowType);
            }
        }
        return arry.toString();
    }

    /**
     * <B>方法名称：获取所有流程分类</B><BR>
     * <B>概要说明：根据sysId， orgId筛选当前系统的流程分类</B><BR>
     * 
     * @see com.sinosoft.sinoep.workflow.service.FlowSccreditService#getFlowTypeList()
     */
    @Override
    public String getFlowTypeList(String orgId) {
        String sysId = ConfigConsts.SYSTEM_ID;
        String res = "";
        if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
            FlowDataService flowDataService = (FlowDataService) SpringBeanUtils.getBean("flowDataService");
            res = flowDataService.getAllFlowSortBySys(sysId, orgId);
        }
        else if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.REST_TYPE)) {
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/flowData/getAllFlowSortBySys";
            res = HttpRequestUtil.sendGet(url, "sysId=" + sysId + "&orgId=" + orgId);
        }
        return res;
    }
}
