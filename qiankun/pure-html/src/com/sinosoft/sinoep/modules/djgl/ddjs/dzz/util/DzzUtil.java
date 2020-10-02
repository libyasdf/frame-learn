package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util;


import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * TODO 
 * @author 胡石阳
 * @Date 2018年8月20日 下午8:43:28
 */
public class DzzUtil {

//	private static Logger log = LoggerFactory.getLogger(AffixUtil.class);
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");

	/**
	 * 生成排序号
	 * @param str
	 * @return
	 */
	public static Integer getMaxOrder(String str){
		Integer orgOrder = jdbcTemplate.queryForObject(str,Integer.class);
		return (orgOrder == null) ? 0 : (orgOrder+1);
	}

	/**
	 * 党小组最大编号
	 * @param superId
	 * @return
	 */
	public static String getOrgId(String superId){
		StringBuilder sb = new StringBuilder();
		sb.append("select max(substr(t.org_id,length(t.org_id)-3+1,3)) from ddjs_dzz_org t where t.super_id = '"+ superId +"' and t.org_type = '666'");
		Integer orgOrder = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
		orgOrder = (orgOrder == null) ? 0 : (orgOrder+1);
		return String.format("%03d",orgOrder);
	}

	/**
	 * 向下更新组织结构ID
	 * @param
	 * @return
	 */
	public static Integer edit(String oldId,String orgId){
		StringBuilder sb = new StringBuilder();
		sb.append("update ddjs_dzz_org set org_id = regexp_replace(org_id,'^"+oldId+"','"+orgId+"',1,1,'c') where visible = '"+ CommonConstants.VISIBLE[1]+"' or revoke_id is not null");
		Integer count = jdbcTemplate.update(sb.toString());
		return count;
	}

	public static String spiltString(String str) {
		StringBuffer sb = new StringBuffer();
		String[] temp = str.split(",");
		for (int i = 0; i < temp.length; i++) {
			if (!"".equals(temp[i]) && temp[i] != null)
				sb.append("'" + temp[i] + "',");
		}
		String result = sb.toString();
		String tp = result.substring(result.length() - 1, result.length());
		if (",".equals(tp))
			return result.substring(0, result.length() - 1);
		else
			return result;
	}


	/**
	 * 获取初始记录
	 * @param
	 * @return
	 */
//	public static String getGlyOrgId(){
//		StringBuilder sb = new StringBuilder();
//		sb.append("select org_id from ddjs_dzz_org t where length(t.org_id) = 6 and visible = '1'");
//		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
//		return list.get(0).get("org_id").toString();
//	}

	public static String getDzbsjOrgId(String userId){
		StringBuilder sb = new StringBuilder();
		sb.append(" select  o.id from ddjs_dzz_ldcy l, ddjs_dzz_hjxj h,ddjs_dzz_org o where h.hjxj_order=(select max(hjxj_order) from ddjs_dzz_hjxj ) and l.hjxj_id=h.id and " +
				" l.visible='1' and l.is_tenure='1' and h.visible='1' and (duties='01' or duties='02' or duties='09'or duties='10'or duties='12'or duties='13') and o.id=h.dwxt_org_id and o.visible='1' and leader_id = '"+userId+"'");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
		if(list.size() > 0 ){
			return list.get(0).get("id").toString();
		}
		return null;
	}


}
