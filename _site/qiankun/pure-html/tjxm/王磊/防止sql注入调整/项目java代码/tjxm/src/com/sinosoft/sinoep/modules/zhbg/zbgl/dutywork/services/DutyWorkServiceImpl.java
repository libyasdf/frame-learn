package com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sinosoft.sinoep.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.constant.ZBGLCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.dao.DutyWorkDao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DeptVo;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 值班工作通知业务实现
 * @author 李颖洁  
 * @date 2018年7月10日  下午5:41:08
 */
@Service
public class DutyWorkServiceImpl implements DutyWorkService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private DutyWorkDao dutyWorkDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * TODO 保存以及修改通知、安保通知数据
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:02:49
	 * @param dutyWork
	 * @return DutyWork
	 */
	@Override
	public DutyWork saveNotice(DutyWork dutyWork) {
		dutyWork.setVisible(CommonConstants.VISIBLE[1]);
		if(dutyWork.getYxq()==null){
			dutyWork.setYxq("");
		}
		String yxq = dutyWork.getYxq();
		String startTime = "";
		String endTime = "";
		if(yxq!=""){
			startTime = yxq.substring(0, (yxq.length() + 1) / 2 - 1).trim();
			endTime = yxq.substring((yxq.length() + 1) / 2, yxq.length()).trim();
		}
		//id为空,创建id保存数据
		if(StringUtils.isBlank(dutyWork.getId())){
			//验证参数有效性
			if(!StringUtils.isBlank(dutyWork.getTitle())&& !StringUtils.isBlank(dutyWork.getNoticeText())){
				//创建人、部门、处室、二级局（id、name）
				dutyWork.setCreUserId(UserUtil.getCruUserId());
				dutyWork.setCreUserName(UserUtil.getCruUserName());
				dutyWork.setCreDeptId(UserUtil.getCruDeptId());
				dutyWork.setCreDeptName(UserUtil.getCruDeptName());
				dutyWork.setCreChuShiId(UserUtil.getCruChushiId());
				dutyWork.setCreChuShiName(UserUtil.getCruChushiName());
				dutyWork.setCreJuId(UserUtil.getCruJuId());
				dutyWork.setCreJuName(UserUtil.getCruJuName());
				//随机生成id
				dutyWork.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
				dutyWork.setStartTime(startTime);
				dutyWork.setEndTime(endTime);
				dutyWork.setState(ZBGLCommonConstants.STATE[0]);
				dutyWork = dutyWorkDao.save(dutyWork);
			}
		}else{
			//修改原来的内容
			DutyWork oriDutyWork = dutyWorkDao.findOne(dutyWork.getId());
			oriDutyWork.setStartTime(startTime);
			oriDutyWork.setEndTime(endTime);
			oriDutyWork.setTitle(dutyWork.getTitle());
			oriDutyWork.setNoticeText(dutyWork.getNoticeText());
			oriDutyWork.setRemark(dutyWork.getRemark());
			oriDutyWork.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			oriDutyWork.setUpdateUserId(UserUtil.getCruUserId());
			oriDutyWork.setUpdateUserName(UserUtil.getCruUserName());
			oriDutyWork.setState(ZBGLCommonConstants.STATE[0]);
			dutyWork = dutyWorkDao.save(oriDutyWork);
		}
		return dutyWork;
	}

	/**
	 * TODO 根据id获取通知信息
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:03:59
	 * @param id
	 * @return DutyWork
	 * @throws SysException
	 */
	@Override
	public DutyWork getById(String id) throws SysException {
		//验证参数有效性
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，加载数据失败！");
		return dutyWorkDao.findOne(id);
	}
	
	/**
	 * TODO 根据ID删除通知信息
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:04:26
	 * @param id
	 * @return int
	 * @throws SysException
	 */
	@Override
	public int delete(String id) throws SysException {
		//验证参数有效性
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，删除数据失败,请重试！");
		String jpql = "update DutyWork t set t.visible = ? where t.id = ?";
		return dutyWorkDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}

	/**
	 * TODO 获取分页数据列表
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:05:07
	 * @param pageable
	 * @param pageImpl
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param isSecurity
	 * @return PageImpl
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startTime, String endTime, String state,String isSecurity) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		//全部列表查询
		querySql.append("select new com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork(t.id, t.title, t.startTime, t.endTime, t.state"+")");
		querySql.append("	from DutyWork t ");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(isSecurity)){
			querySql.append("   and t.isSecurity = ?");
			para.add(isSecurity);
		}
		if (!StringUtils.isBlank(state)){
			querySql.append("   and t.state = ?");
			para.add(state);
		}
		if (!StringUtils.isBlank(endTime)) {
			querySql.append("   and t.startTime <= ?");
			para.add(endTime);
		}
		if (!StringUtils.isBlank(startTime)) {
			querySql.append("   and t.endTime >= ?");
			para.add(startTime);
		}

		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<DutyWork> page = dutyWorkDao.query(querySql.toString(), pageable,para.toArray());
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;
	}

	/** TODO 发布通知更改状态
	 * @author 李颖洁  
	 * @date 2018年7月19日下午4:52:06
	 * @param id
	 * @return 
	 * @throws SysException 
	 */
	@Override
	public int updateState(String id) throws SysException{
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，删除数据失败,请重试！");
		String jpql = "update DutyWork t set t.state = ? where t.id = ?";
		return dutyWorkDao.update(jpql, ZBGLCommonConstants.STATE[1], id);
	}
	
	/** TODO 获取有效期间的日期列表
	 * @author 李颖洁  
	 * @date 2018年7月28日下午12:14:30
	 * @param startTime
	 * @param endTime
	 * @throws ParseException 
	 */
	@Override
	public List<JSONObject> getDateList(String startTime, String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> lDate = new ArrayList<Date>();
		List<JSONObject> list = new ArrayList<>();
		try {
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
			lDate.add(startDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			boolean bContinue = true;
			while (bContinue) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (endDate.after(cal.getTime())) {
					lDate.add(cal.getTime());
				} else {
					break;
				}
			}
			lDate.add(endDate);
			for (Date Date : lDate) {
				JSONObject d = new JSONObject();
				d.put("watchDate", sdf.format(Date));
				list.add(d);
			}
			log.info(list.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return list;
	}

	/**
	 * TODO 判断是否有上报员
	 * @author 李颖洁  
	 * @date 2018年8月1日下午3:47:18
	 * @param deptIds
	 * @throws Exception
	 */
	@Override
	public JSONObject hasReportUserByDeptId(String deptIds,String deptNames) throws Exception {
		JSONObject json = new JSONObject();
		boolean f = false;
		json.put("flag", "0");
		StringBuilder  deptNoUser = new StringBuilder();
		try {
			String[] deptId = mapper.readValue(deptIds, String[].class);
			String[] deptName = mapper.readValue(deptNames, String[].class);
			String ids = StringUtils.join(deptId,",");
			ids = CommonUtils.commomSplit(ids);
			String sql = "select sfd.deptname,sfd.deptid,duser.userid" 
					+"	from" 
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,12) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 12) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+ids+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in('C122','D122'))and d.status = '"+ZBGLCommonConstants.STATE[1]+"') t"
					+"	on d.deptid = t.deptid) duser" 
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";
			JSONObject result = userInfoService.getDateBySql(sql);
			List<DeptVo> list = new ArrayList<>();
			log.info(JSON.toJSONString(result));
			JSONArray arr = result.getJSONArray("data");
			list = JSONArray.toList(arr, DeptVo.class);
			for(int i=0;i<deptId.length;i++){
				String n = deptId[i];
				String m = deptName[i];
				n = n.replace("'", "").trim();
				for (DeptVo deptVo : list) {
					log.info(deptVo.getDeptid());
					if(n.equals(deptVo.getDeptid())){
						f = true;
						break;
					}
				}
				if(!f){
					String pSql ="SELECT t.deptname\n" +
									"  FROM sys_flow_dept t\n" +
									" WHERE t.deptid =\n" +
									"       (SELECT t.super_id FROM sys_flow_dept t WHERE t.deptid = '"+n+"')";
					JSONObject pResult = userInfoService.getDateBySql(pSql);
					List<Map<String,Object>> lists = pResult.getJSONArray("data");
					String pName = "";
					if(lists.size()>0){
						pName = lists.get(0).get("deptname").toString();
					}
					deptNoUser.append(pName+"-"+m+"；");
					log.info(deptNoUser.toString());
				}
				f = false;
			}
			json.put("flag", "1");
			json.put("data", JSONArray.fromObject(list));
			if(deptNoUser.length()!=0){
				json.put("deptNames",deptNoUser.toString());
				json.put("flag", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", "解析异常");
		}
		return json;
	}

	

	
}
