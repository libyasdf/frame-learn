package com.sinosoft.sinoep.modules.zhbg.ldtxb.jldcjbb.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldcjbb.dao.JldcjbbDao;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldcjbb.entity.Jldcjbb;
import com.sinosoft.sinoep.user.util.UserUtil;
@Service
public class JldcjbbServiceImpl implements JldcjbbService {
	@Autowired
	private JldcjbbDao jldcjbbDao;
	

	/**
	 * 保存方法
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:05
	 * @param plan
	 * @return
	 */
	@Override
	public Jldcjbb saveForm(Jldcjbb jldcjbb) {
		jldcjbb.setVisible("1");
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if (StringUtils.isBlank(jldcjbb.getId())) {
			jldcjbb.setCreTime(nowTime);
			jldcjbb.setCreUserId(UserUtil.getCruUserId());
			jldcjbb.setCreUserName(UserUtil.getCruUserName());
			jldcjbb.setUpdateTime(nowTime);
			jldcjbb.setUpdateUserId(UserUtil.getCruUserId());
			jldcjbb.setUpdateUserName(UserUtil.getCruUserName());
			jldcjbb.setCreChushiId(UserUtil.getCruChushiId());
			jldcjbb.setCreChushiName(UserUtil.getCruChushiName());
			jldcjbb.setCreDeptId(UserUtil.getCruDeptId());
			jldcjbb.setCreDeptName(UserUtil.getCruDeptName());
			jldcjbb.setCreJuId(UserUtil.getCruJuId());
			jldcjbb.setCreJuName(UserUtil.getCruJuName());
			jldcjbb.setEndTime(getEndDate(jldcjbb.getStartTime()));
			jldcjbb.setStartTime(getStartDate(jldcjbb.getStartTime()));
			jldcjbb = jldcjbbDao.save(jldcjbb);
		}else{
			Jldcjbb oldJldcjbb = jldcjbbDao.findOne(jldcjbb.getId());
			oldJldcjbb.setEndTime(jldcjbb.getEndTime());
			oldJldcjbb.setStartTime(jldcjbb.getStartTime());
			oldJldcjbb.setName(jldcjbb.getName());
			oldJldcjbb.setPlace(jldcjbb.getPlace());
			oldJldcjbb.setRemark(jldcjbb.getRemark());
			oldJldcjbb.setTimeLong(jldcjbb.getTimeLong());
			oldJldcjbb.setTitle(jldcjbb.getTitle());
			oldJldcjbb.setTraffic(jldcjbb.getTraffic());
			oldJldcjbb.setUpdateTime(nowTime);
			oldJldcjbb.setUpdateUserId(UserUtil.getCruUserId());
			oldJldcjbb.setUpdateUserName(UserUtil.getCruUserName());
			jldcjbb = jldcjbbDao.save(oldJldcjbb);
		}
		
		/*// 本地服务调用	保存临时意见
		if (!StringUtils.isBlank(plan.getIdea())) {
			WorkFlowUtil.saveTempIdea(plan.getWorkitemid(), plan.getIdea());
		}*/
		return jldcjbb;
	}
	
	private String getStartDate(String date) {
		if(!StringUtils.isBlank(date)) {
			return date.substring(0, date.indexOf(" - "));
		}else {
			return "";
		}
		
	}
	private String getEndDate(String date) {
		if(!StringUtils.isBlank(date)) {
			String aa = date.substring(date.indexOf(" - ") + 3,date.length());
			return date.substring(date.indexOf(" - ") + 3,date.length());
		}else {
			return "";
		}
	}
	
	/**
	 * 根据主键ID查询一条数据
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:36
	 * @param id
	 * @return
	 */
	@Override
	public Jldcjbb getById(String id) {
		return jldcjbbDao.findOne(id);
	}
	
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String name, String startDate,
			String endDate) {
		
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("	from Jldcjbb t ");
		//querySql.append("	where t.creUserId = ? and t.isVisible = '"+CommonConstants.VISIBLE[1]+"'");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		
		//拼接条件
		//para.add(UserUtil.getCruUserId());
		if (StringUtils.isNotBlank(name)) {
			querySql.append("   and t.name like ?");
			para.add("%" + name + "%");
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.creTime <= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.creTime >= ?");
			para.add(endDate);
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
        Page<Jldcjbb> page = jldcjbbDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<Jldcjbb> content = page.getContent();
        for (Jldcjbb jldcjbb : content) {
        	//if (ConfigConsts.START_FLAG.equals(jldcjbb.getSubflag())) {
        		jldcjbb.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
			//}
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 删除一条记录
	 * TODO 
	 * @author 李利广
	 * @Date 2018年3月15日 下午8:48:21
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		int result = 0;
		String jpql = "update Jldcjbb t set t.visible = ? where t.id = ?";
		try {
			result = jldcjbbDao.update(jpql, CommonConstants.VISIBLE[0], id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
