package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;

public class MeetingRoomUtil {
	
	private static JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	
	/**
	 * 
	 * TODO 获取导航最大sort值
	 * @author 冯超
	 * @Date 2018年5月7日 下午7:06:53
	 * @param dic
	 * @return
	 */
	public static Integer getSort(){
		StringBuilder sb = new StringBuilder();
		sb.append("select max(t.sort) from hygl_meetingroom t where ");
		sb.append("t.visible = '"+ CommonConstants.VISIBLE[1]+"'");
		Integer sort = jdbcTemplate.queryForObject(sb.toString(),Integer.class);
		return (sort == null) ? 0 : (sort+1);
	}

}
