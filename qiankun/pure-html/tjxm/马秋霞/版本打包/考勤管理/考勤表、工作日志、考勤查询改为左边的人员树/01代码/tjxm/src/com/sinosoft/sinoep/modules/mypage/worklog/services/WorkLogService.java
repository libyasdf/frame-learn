package com.sinosoft.sinoep.modules.mypage.worklog.services;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.worklog.entity.WorkLog;

import net.sf.json.JSONObject;

public interface WorkLogService {
		
	/**
	 * 
	 * 
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月3日 下午6:39:04
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String type,String flag,String logType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content);
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月4日 上午9:43:52
	 * @param info
	 * @return
	 */
	WorkLog saveForm(WorkLog info,String flag);
	
	/**
	 * 根据主键ID查询一条数据
	 * @param id
	 * @return
	 */
	WorkLog getById(String id);
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 点击首页上的上报按钮
	 * @param id
	 * @return
	 */
	int shangBao(String id);
	
	/**
	 * 根据日期查询是否有该条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月8日 下午3:24:21
	 * @param dateLog
	 * @return
	 */
	List findByDateLog(String dateLog,String id);
	
	/**
	 * 获取首页上工作日志的前6条数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月8日 下午5:00:04
	 * @return
	 */
	List<Map<String,Object>> getPageList();
	
	/**
	 * 领导查询的工作日志分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月15日 下午2:52:10
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	PageImpl getLeaderShowList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String type,String persionId,String logType,String dateLogYear,String dateLogMonth,String dateLogWeek,String timeCondition,String content);
	
	/**
	 * 获取当前周的开始到结束时间
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月2日 下午4:32:31
	 * @param date
	 * @return
	 */
	JSONObject getWeekDate(String date) throws Exception;
	
	

}
