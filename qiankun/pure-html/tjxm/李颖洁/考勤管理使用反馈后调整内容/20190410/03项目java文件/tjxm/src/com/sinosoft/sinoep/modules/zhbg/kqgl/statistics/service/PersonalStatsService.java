package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import java.util.List;

import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;

public interface PersonalStatsService {
	
	/**
	 * 统计出某个时间段内当前登陆用户的外出、出差、补签次数及迟到早退次数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月19日 下午5:21:57
	 * @param timeRange
	 * @return
	 */
	List<PersonalStats> getList(String timeRange) throws Exception;

}
