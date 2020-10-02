package com.key.authority.common.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.key.authority.common.AuthConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

public class AuthorityUtils {
	
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	
	/**
	 * 获取当前用户权限内的数据ID
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月18日 下午2:57:02
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public static List<String> getAuthList() throws Exception{
		Map<String, SysFlowUserVo> userVo = UserUtil.getUserVo(UserUtil.getCruUserId());
		StringBuilder sBuilder = new StringBuilder("");
		sBuilder.append("select t.dwsurvey_id from t_auth_dept t where t.dept_id = '"+UserUtil.getCruDeptId()+"' and t.visible = '"+AuthConstants.VISIBLE[1]+"'");
		sBuilder.append(" union ");
		sBuilder.append("select t.dwsurvey_id from t_auth_zwqx t where t.position_id = '"+userVo.get(UserUtil.getCruUserId()).getPositionName()+"' and t.visible = '"+AuthConstants.VISIBLE[1]+"'");
		sBuilder.append(" union ");
		sBuilder.append("select t.dwsurvey_id from t_auth_dept_zwqx t where (t.dept_id = '"+UserUtil.getCruDeptId()+"' or t.dept_id = '"+UserUtil.getCruChushiId()+"' or t.dept_id = '"+UserUtil.getCruJuId()+"' or t.dept_id = '"+UserUtil.getCruOrgId()+"') and t.position_id = '"+userVo.get(UserUtil.getCruUserId()).getPositionName()+"' and t.visible = '"+AuthConstants.VISIBLE[1]+"'");
		List<String> authList = jdbcTemplate.queryForList(sBuilder.toString(), String.class);
		return authList;
	}

}
