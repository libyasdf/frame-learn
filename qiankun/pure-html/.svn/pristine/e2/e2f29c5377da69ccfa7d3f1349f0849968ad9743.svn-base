package com.sinosoft.sinoep.flowsccredit.services;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.sinosoft.ep.webform.tool.Utiles;
import com.sinosoft.log.LogHelper;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.*;
import com.sinosoft.sinoep.flowsccredit.entity.FlowSccredit;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.workflow.service.WorkFlowService;
import com.sinosoft.util.exception.DAOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.service.AccreditService;
import workflow.spring.FlowDataService;

import java.util.*;

/**
 *
 * <B>系统名称：</B><BR>
 * <B>模块名称：代办授权</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：操作代办授权基本信息</B><BR>
 *
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Service
public class FlowSccreditServiceImpl implements FlowSccreditService {

    private static LogHelper log = new LogHelper(FlowSccreditServiceImpl.class);

    @Autowired
    private AccreditService accreditService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * <B>方法名称：保存代办授权</B><BR>
     * <B>概要说明：</B><BR>
     */
    @Override
    public String save(FlowSccredit flowSccredit, String[] fileTypeItem)
            throws DAOException {
        JSONObject res = new JSONObject();
        res.put("result", "0");
        String result = "";
        if (Utiles.isNull(fileTypeItem)){
            return res.toString();
        }
        if (StringUtils.isBlank(flowSccredit.getUserid())) {
            flowSccredit.setUserid(UserUtil.getCruUserId());
            flowSccredit.setDeptid(UserUtil.getCruDeptId());
        }
        flowSccredit.setOrgId(UserUtil.getCruOrgId());
        flowSccredit.setSysId(ConfigConsts.SYSTEM_ID);
        //保存授权信息
        result = addAuth(fileTypeItem, flowSccredit);
        if(result.contains("成功")){
            res.put("result", "1");
        }
        res.put("msg", result);
        return res.toString();
    }

    /**
     *
     * <B>方法名称：添加多条授权信息</B><BR>
     * <B>概要说明：</B><BR>
     */
    private String addAuth(String[] fileTypes, FlowSccredit flowSccredit) throws DAOException {
        String addInfo = "授权失败，授权信息已经存在！";
        MessageUser messageUser = userInfoService.getUserInfoById(flowSccredit.getUserid());
        flowSccredit.setCre_user(flowSccredit.getUserid());
        if (messageUser.getUserInfo().size() > 0) {
            UserInfo userInfo = messageUser.getUserInfo().get(0);
            flowSccredit.setUsername(userInfo.getUserFullName());
            flowSccredit.setCre_username(userInfo.getUserFullName());
        }
        MessageUser sccredituser = userInfoService.getUserInfoById(flowSccredit.getSccredituserid());
        if (sccredituser.getUserInfo().size() > 0) {
            UserInfo sccredituserInfo = sccredituser.getUserInfo().get(0);
            flowSccredit.setSccreditusername(sccredituserInfo.getUserFullName());
        }
        String sccreditdeptName = userInfoService.getDeptFullName(flowSccredit.getSccreditdeptid());
        flowSccredit.setSccreditdeptname(sccreditdeptName);
        JSONObject json = new JSONObject();
        JSONArray sorts = new JSONArray();
        //这里只是一个无意义的值，避免跟gwlxid值（可能为空）重复，但又不能为空
        String lastgwlxid = IdUtil.simpleUUID();
        for (int i = 0; i < fileTypes.length; i++) {
            String[] items = fileTypes[i].split(";");
            String gwlxch = items[0];
            String gwlxid = items[1];
            String fileTypeId = items[2];
            String fileTypeName = items[3];

            JSONObject sort = new JSONObject();
            JSONArray files = new JSONArray();
            JSONObject file = new JSONObject();
            if(lastgwlxid.equals(gwlxid)){
                sort = sorts.getJSONObject(sorts.size() -1);
                files = sort.getJSONArray("file");
                file.put("filetypeid",fileTypeId);
                file.put("filetypename",fileTypeName);
                files.add(file);
            }else{
                file.put("filetypeid",fileTypeId);
                file.put("filetypename",fileTypeName);
                files.add(file);
                sort.put("gwlxch",gwlxch);
                sort.put("gwlxid",gwlxid);
                sort.put("file",files);
                sorts.add(sort);
            }
            lastgwlxid = gwlxid;
        }
        if (sorts.size() > 0) {
            json.put("sccreditid",flowSccredit.getSortId());
            json.put("userid",flowSccredit.getUserid());
            json.put("deptid",flowSccredit.getDeptid());
            json.put("sysid",ConfigConsts.SYSTEM_ID);
            json.put("issccredit",flowSccredit.getIssccredit());
            json.put("sccbegindate",flowSccredit.getSccbegindate());
            json.put("sccenddate",flowSccredit.getSccenddate());
            json.put("sccreditdate", DateUtil.now());
            json.put("sccredittype",flowSccredit.getSccredittype());
            json.put("zWhen",flowSccredit.getSccredittype());
            JSONArray sccredit = new JSONArray();
            JSONObject user = new JSONObject();
            user.put("userid",flowSccredit.getSccredituserid());
            user.put("username",flowSccredit.getSccreditusername());
            user.put("deptid",flowSccredit.getSccreditdeptid());
            sccredit.add(user);
            json.put("sccredit",sccredit);
            json.put("sort",sorts);
            log.info("新增代办授权json:" + json.toString());
            String result = accreditService.addAuth(json.toString());
            log.info("添加授权结果：" + result);
            if(StringUtils.isNotBlank(result)){
                JSONObject res = JSONObject.fromObject(result);
                if("1".equals(res.getString("res"))){
                    addInfo = res.getString("message");
                }else{
                    return res.getString("message");
                }
            }
        }
        return addInfo;
    }

    /**
     * <B>方法名称：根据创建人获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     */
    @Override
    public PageImpl queryFlowSccredit(PageImpl pageImpl, FlowSccredit flowSccredit) throws DAOException{
        pageImpl.setFlag("0");
        flowSccredit.setCre_user(UserUtil.getCruUserId());

        StringBuilder coreSql = new StringBuilder();
        coreSql.append("	select t.* ");
        coreSql.append("	from epcloud.flow_sccredit_sort t ");
        coreSql.append("	where t.z_status = '1' and t.userid = '").append(UserUtil.getCruUserId()).append("'");
        coreSql.append("    and t.deptid = '").append(UserUtil.getCruDeptId()).append("'");
        if (!Utiles.isNullStr(flowSccredit.getFiletypename())) {
            coreSql.append(" and t.filetypename like '").append("%" + flowSccredit.getFiletypename() + "%'");
        }
        if (!Utiles.isNullStr(flowSccredit.getSccreditusername())) {
            coreSql.append(" and sccredituserid like '").append("%" + flowSccredit.getSccreditusername() + "%'");
        }

        StringBuilder sql = new StringBuilder("select * from (");
        sql.append(" select rownum rn, a.* from (");
        sql.append(coreSql);
        sql.append(") a");
        sql.append("	where rownum <= ").append(pageImpl.getPageNumber()*pageImpl.getPageSize());
        sql.append("    order by a.sccreditdate desc,a.filetype desc) ");
        sql.append("	where rn >= ").append((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
        List<Map<String,Object>> list = workFlowService.getDataBySql(sql.toString());
        List<FlowSccredit> content = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            FlowSccredit sccredit = getSccreditSort(list.get(i));
            content.add(sccredit);
        }
        //草稿列表，添加操作列
        for (FlowSccredit sccredit : content) {
            sccredit.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        }
        //查询总数
        StringBuilder countSql = new StringBuilder();
        countSql.append("select count(1) as count from (");
        countSql.append(coreSql);
        countSql.append(")");
        List<Map<String,Object>> countList = workFlowService.getDataBySql(countSql.toString());
        String count = countList.get(0).get("count").toString();

        pageImpl.setFlag("1");
        pageImpl.getData().setRows(content);
        pageImpl.getData().setTotal(Integer.valueOf(count));
        return pageImpl;
    }

    /**
     *
     * <B>方法名称：根据当前人角色获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:06
     * @param pageImpl：每页条数
     * @param flowSccredit：代办授权实体
     * @param role 1:总收发（查看本单位）;2:部门综合（查看本部门）
     * @return Page
     * @throws DAOException
     */
    @Override
    public PageImpl queryFlowSccredit(PageImpl pageImpl, FlowSccredit flowSccredit,String role) throws DAOException{
        pageImpl.setFlag("0");
        String deptId = "";
        if("1".equals(role)){
            //总收发（查看本单位）
            deptId = UserUtil.getCruJuId();
        }else if("2".equals(role)){
            //部门综合（查看本部门）
            deptId = UserUtil.getCruDeptId();
        }
        //查询本单位（本部门）下所有部门的授权数据
        String deptIds = "";
        StringBuilder deptIdStr = new StringBuilder("");
        JSONObject json = userInfoService.getAllDeptBySuperId(deptId);
        if(json.getString("flag").equals("1")){
            JSONArray array = json.getJSONArray("data");
            for(int i = 0;i < array.size();i++){
                String deptid = array.getJSONObject(i).getString("deptid");
                deptIdStr.append(deptid).append(",");
            }
            if(deptIdStr.length() > 0){
                deptIds = deptIdStr.substring(0,deptIdStr.length()-1);
            }
        }
        List<String[]> deptIdList = UserUtil.subUser(deptIds);

        StringBuilder coreSql = new StringBuilder();
        coreSql.append("	select t.* ");
        coreSql.append("	from epcloud.flow_sccredit_sort t ");
        coreSql.append("	where t.z_status = '1' ");
        coreSql.append("     and (");
        StringBuilder deptStr = new StringBuilder();
        for(String[] deptIdArr:deptIdList){
            String dept = ArrayUtil.join(deptIdArr,",");
            deptStr.append("  t.deptid in (").append(CommonUtils.commomSplit(dept)).append(")").append(" or ");
        }
        //去除最后的or
        coreSql.append(deptStr.substring(0,deptStr.length()-3));
        coreSql.append("     )");
        if (!Utiles.isNullStr(flowSccredit.getFiletypename())) {
            coreSql.append(" and t.filetypename like '").append("%" + flowSccredit.getFiletypename() + "%'");
        }
        if (!Utiles.isNullStr(flowSccredit.getSccreditusername())) {
            coreSql.append(" and sccredituserid like '").append("%" + flowSccredit.getSccreditusername() + "%'");
        }

        StringBuilder sql = new StringBuilder("select * from (");
        sql.append(" select rownum rn, a.* from (");
        sql.append(coreSql);
        sql.append(") a");
        sql.append("	where rownum <= ").append(pageImpl.getPageNumber()*pageImpl.getPageSize());
        sql.append("    order by a.sccreditdate desc,a.filetype desc) ");
        sql.append("	where rn >= ").append((pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1);
        List<Map<String,Object>> list = workFlowService.getDataBySql(sql.toString());
        List<FlowSccredit> content = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            FlowSccredit sccredit = getSccreditSort(list.get(i));
            content.add(sccredit);
        }
        //草稿列表，添加操作列
        for (FlowSccredit sccredit : content) {
            sccredit.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        }
        //查询总数
        StringBuilder countSql = new StringBuilder();
        countSql.append("select count(1) as count from (");
        countSql.append(coreSql);
        countSql.append(")");
        List<Map<String,Object>> countList = workFlowService.getDataBySql(countSql.toString());
        String count = countList.get(0).get("count").toString();

        pageImpl.setFlag("1");
        pageImpl.getData().setRows(content);
        pageImpl.getData().setTotal(Integer.valueOf(count));
        return pageImpl;
    }

    private FlowSccredit getSccreditSort(Map<String,Object> map){
        FlowSccredit sccredit = new FlowSccredit();
        log.info("map:"+JSONObject.fromObject(map));
        sccredit.setSortId(map.get("sccreditid").toString());
        sccredit.setSysId(map.get("sys_id").toString());
        sccredit.setZ_status(map.get("z_status").toString());
        sccredit.setSccredittype(map.get("sccredittype").toString());
        sccredit.setUserid(map.get("userid").toString());
        sccredit.setDeptid(map.get("deptid").toString());
        String userName = userInfoService.getUserFullName(sccredit.getUserid());
        sccredit.setUsername(userName);
        String deptName = userInfoService.getDeptFullName(sccredit.getDeptid());
        sccredit.setDeptname(deptName);
        String sccreditUser = map.get("sccredituserid").toString();
        JSONArray user = JSONArray.fromObject(sccreditUser);
        JSONObject userObj = user.getJSONObject(0);
        sccredit.setSccredituserid(userObj.getString("userid"));
        sccredit.setSccreditusername(userObj.getString("username"));
        sccredit.setSccreditdeptid(userObj.getString("deptid"));
        String sccreditDeptName = userInfoService.getDeptName(userObj.getString("deptid"));
        sccredit.setSccreditdeptname(sccreditDeptName);
        sccredit.setSccreditdate(map.get("sccreditdate").toString());
        sccredit.setSccbegindate(map.get("sccbegindate").toString());
        sccredit.setSccenddate(map.get("sccenddate").toString());
        sccredit.setIssccredit(map.get("issccredit").toString());
        String fileTypes = map.get("filetypename").toString();
        JSONArray array = JSONArray.fromObject(fileTypes);
        List<String> fileTypeNames = new ArrayList<>();
        List<String> fileTypeList = new ArrayList<>();
        for(int i =0;i < array.size();i++){
            JSONObject fileType = array.getJSONObject(i);
            String fileTypeName = fileType.getString("gwlxch");
            String fileTypeId = fileType.getString("gwlxid");
            fileTypeNames.add(fileTypeName);
            fileTypeList.add(fileTypeId);
        }
        sccredit.setFiletype(ArrayUtil.join(fileTypeList.toArray(),","));
        sccredit.setFiletypename(ArrayUtil.join(fileTypeNames.toArray(),","));
        sccredit.setSccreditid(map.get("sccreditid").toString());
        return sccredit;
    }

    /**
     *
     * <B>方法名称：删除授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @see FlowSccreditService#delAuth(String)
     */
    @Override
    public JSONObject delAuth(String id) throws DAOException {
        JSONObject json = new JSONObject();
        json.put("id",id);
        //是否分类，1是；0否
        json.put("isSort","1");
        //取消类型1-主动;2-到期自动
        json.put("canceltype","1");
        String result = accreditService.delAuth(json.toString());
        if(StringUtils.isNotBlank(result)){
            json = JSONObject.fromObject(result);
        }
        return json;
    }

    /**
     *
     * <B>方法名称：获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @see FlowSccreditService#viewAuth(String)
     */
    @Override
    public FlowSccredit viewAuth(String id) {
        FlowSccredit flowSccredit = null;
        try {
            StringBuilder sql = new StringBuilder("select * from flow_sccredit_sort t where ");
            sql.append("t.sccreditid = '").append(id).append("'");
            List<Map<String,Object>> list = workFlowService.getDataBySql(sql.toString());
            if(list.size() > 0){
                flowSccredit = getSccreditSort(list.get(0));
            }
            return flowSccredit;
        }catch (Exception e) {
            log.error("查看代办授权失败！", e);
        }
        return null;
    }

    /**
     *
     * <B>方法名称：修改代办授权信息</B><BR>
     * <B>概要说明：</B><BR>
     */
    @Override
    public String updateAuth(String fileTypeItem, FlowSccredit flowSccredit) {
        String res = "修改授权信息失败！";
        String[] items = fileTypeItem.split(";");
        StringBuilder sql = new StringBuilder("update epcloud.flow_sccredit set ");
        sql.append(" SCCREDITUSERID = ?");
        sql.append(", SCCREDITDEPTID = ?");
        sql.append(", SCCREDIT_USERNM = ?");
        sql.append(", SCCREDIT_DEPTNM = ?");
        sql.append(", SCCREDITTYPE = ?");
        sql.append(", ISSCCREDIT = ?");
        sql.append(", SCCBEGINDATE = ?");
        sql.append(", SCCENDDATE = ?");
        sql.append(", SCCREDITDATE = ?");
        sql.append(", FILETYPE = ?");
        sql.append(", FILETYPENAME = ?");
        sql.append(" where SCCREDITID = ?");
        List<String> param = new ArrayList<>();
        param.add(flowSccredit.getSccredituserid());
        param.add(flowSccredit.getSccreditdeptid());
        String userName = userInfoService.getUserFullName(flowSccredit.getSccredituserid());
        param.add(userName);
        String sccreditdeptName = userInfoService.getDeptFullName(flowSccredit.getSccreditdeptid());
        param.add(sccreditdeptName);
        param.add(flowSccredit.getSccredittype());
        param.add(flowSccredit.getIssccredit());
        param.add(flowSccredit.getSccbegindate());
        param.add(flowSccredit.getSccenddate());
        param.add(DateUtil.now().substring(0,DateUtil.now().length()-3));
        param.add(items[2]);
        param.add(items[3]);
        param.add(flowSccredit.getSccreditid());
        int count = jdbcTemplate.update(sql.toString(),param.toArray());
        if(count > 0){
            res = "更新授权信息成功";
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
    @Override
    public Collection<JSONObject> getAllFlowType() {
        FlowDataService flowDataService = (FlowDataService) SpringBeanUtils.getBean("flowDataService");
        JSONObject param = new JSONObject();
        param.put("sysId",ConfigConsts.SYSTEM_ID);
        param.put("orgId",ConfigConsts.ORG_ID);
        param.put("flag","0");
        String json = flowDataService.querySortAndFlow(param.toString());

        //过滤掉父级分类
        Map<String,JSONObject> map1 = new LinkedHashMap<>();
        if(StringUtils.isNotBlank(json)){
            JSONArray array = JSONArray.fromObject(json);
            for(int i = 0;i < array.size();i++){
                JSONObject flow = array.getJSONObject(i);
                String flowId = flow.getString("id");
                map1.put(flowId,flow);
                String superFlowId = flow.getString("superId");
                if(map1.containsKey(superFlowId)){
                    //如果该流程的父流程ID，在map中已经存在，则移除父流程
                    map1.remove(superFlowId);
                }
            }
        }
        return map1.values();
    }

}
