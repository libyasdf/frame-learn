package com.sinosoft.sinoep.modules.system.config.orgdepmapping.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.dao.OtherFlowDepDao;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.entity.OtherFlowDep;
import com.sinosoft.sinoep.modules.system.config.orgdepmapping.util.OtherFlowDepUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

@Service
public class OtherFlowDepServiceImp implements OtherFlowDepService{
	
	@Autowired 
	OtherFlowDepDao dao;
	@Autowired
	JdbcTemplate JdbcTemplate;
	
	/**
	 * 获取该组织体系下的所有部门
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月6日 下午5:50:43
	 * @param orgId
	 * @return
	 */
	@Override
	public List<OtherFlowDep> getList(final String orgId,final String departmentName,String sortName,String sortOrder) {
		List<String> params = new ArrayList<String>();
			StringBuilder sb=new StringBuilder("select id id,dept_name deptName,sort sort,GL_DEPT_NAME gldeptName,peak_deptid peakDeptid,dept_id deptId,GL_DEPT_ID glDeptId from sys_other_flow_dept where visible='" + CommonConstants.VISIBLE[1] + "' " );
		if(!StringUtils.isBlank(orgId)){
				sb.append("  and PEAK_DEPTID = ? " );
				params.add(orgId);
		}else {
			sb.append("  and PEAK_DEPTID =''" );
		}
		if(!StringUtils.isBlank(departmentName)){
			sb.append("  and  dept_name like ? ");
			params.add("%" +departmentName+ "%");
		}
		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortOrder)){
			sb.append(" order by " + sortName + "  " + sortOrder);
		}else{
			sb.append(" order by GL_DEPT_NAME,cre_time ");
		}
		
		
		List<OtherFlowDep> list=JdbcTemplate.query(sb.toString(), new RowMapper<OtherFlowDep>(){
			@Override
			public OtherFlowDep mapRow(ResultSet set, int arg1) throws SQLException {
				OtherFlowDep other=new OtherFlowDep();
				other.setId(set.getString("id"));
				other.setDeptName(set.getString("deptName"));
				other.setSort(set.getInt("sort"));
				other.setGlDeptName(set.getString("gldeptName"));
				other.setGlDeptId(set.getString("glDeptId"));
				other.setDeptId(set.getString("deptId"));
				return other;
			}
			
		},params.toArray());
		for (OtherFlowDep dep : list) {
			dep.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
		return list;
		/*Sort sort = new Sort("glDeptName", "sort");
		return dao.findAll(new Specification<OtherFlowDep>() {
			@Override
			public Predicate toPredicate(Root<OtherFlowDep> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				predicates.add(pVisible);
				Predicate pName = null,peakDeptid = null;
				if (!StringUtils.isBlank(departmentName)){
					pName = cb.like(root.get("deptName").as(String.class), "%"+departmentName+"%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(orgId)){
					peakDeptid = cb.equal(root.get("peakDeptid").as(String.class), orgId);
					predicates.add(peakDeptid);
				}
				
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);*/
	}
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午2:05:26
	 * @param info
	 * @return
	 */
	@Override
	public OtherFlowDep saveForm(OtherFlowDep info) {
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		if(StringUtils.isBlank(info.getId())){
			//根据体系id查询sort最大的值
			Integer  sort=OtherFlowDepUtil.getSort(info.getPeakDeptid());
			info.setSort(sort);
			info.setCreTime(creTime);
			info.setCreUserId(UserUtil.getCruUserId());
			info.setCreUserName(UserUtil.getCruUserName());
			info.setCreDeptId(UserUtil.getCruDeptId());
			info.setCreDeptName(UserUtil.getCruDeptName());
			info.setUpdateTime(creTime);
			info.setUpdateUserId(UserUtil.getCruUserId());
			info.setUpdateUserName(UserUtil.getCruUserName());
			info.setCreChuShiId(UserUtil.getCruChushiId());
			info.setCreChuShiName(UserUtil.getCruChushiName());
			info.setCreJuId(UserUtil.getCruJuId());
			info.setCreJuName(UserUtil.getCruJuId());
			info.setGlDeptName("");
			info.setVisible("1");
			info=dao.save(info);
		}else{
			OtherFlowDep oldInfo=dao.findOne(info.getId());
			oldInfo.setUpdateTime(creTime);
			oldInfo.setUpdateUserId(UserUtil.getCruUserId());
			oldInfo.setUpdateUserName(UserUtil.getCruUserName());
			
			oldInfo.setDeptId(info.getDeptId());
			oldInfo.setDeptName(info.getDeptName());
			info=dao.save(oldInfo);
		}
		return info;
	}
	
	/**
	 * 根据id查询一条记录
	 */
	@Override
	public OtherFlowDep getById(String id) {
		return dao.findOne(id);
	}
	
	/**
	 * 删除一条记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午4:47:48
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update OtherFlowDep t set t.visible = ? where t.id = ?";
		return dao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月7日 下午5:22:17
	 * @param id
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			OtherFlowDep otherDep = dao.findOne(ids[i]);
			otherDep.setSort(i);
			dao.save(otherDep);
		}
		
	}

	@Override
	public int binding(String id, String glDeptId,String glDeptName) {
		String jpql = "update OtherFlowDep t set t.glDeptId = ? , t.glDeptName=? where t.id = ?";
		return dao.update(jpql, glDeptId,glDeptName, id);
		
	}

	@Override
	public int unbinding(String id) {
		String jpql = "update OtherFlowDep t set t.glDeptId = ? , t.glDeptName=? where t.id = ?";
		return dao.update(jpql, "","", id);
	}

}
