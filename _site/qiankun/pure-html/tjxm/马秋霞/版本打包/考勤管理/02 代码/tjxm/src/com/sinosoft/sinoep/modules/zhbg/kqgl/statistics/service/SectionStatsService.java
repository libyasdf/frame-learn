package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.PersonalStats;

/**
 * 处长科长统计的service
 * TODO 
 * @author 马秋霞
 * @Date 2018年7月29日 下午3:00:23
 */
public interface SectionStatsService {
	
	/**
	 * 统计某些人某短时时间的一些统计情况
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月29日 下午3:05:26
	 * @param timeRange
	 * @param userids
	 * @return
	 */
	List<PersonalStats> getList(String timeRange, String userids,String deptid,String deptname)throws Exception;

}
