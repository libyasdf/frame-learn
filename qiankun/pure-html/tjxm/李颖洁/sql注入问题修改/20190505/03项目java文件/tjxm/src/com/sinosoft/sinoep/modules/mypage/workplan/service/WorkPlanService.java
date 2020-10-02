package com.sinosoft.sinoep.modules.mypage.workplan.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.workplan.entity.DayStatus;
import com.sinosoft.sinoep.modules.mypage.workplan.entity.WorkPlan;

public interface WorkPlanService {
	
	/**
	 * 获取日期数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:27:45
	 * @param request
	 * @param year
	 * @param month
	 * @return
	 */
	DayStatus[][] getList(HttpServletRequest request, Integer year, Integer month);
	/**
	 * 保存
	 * TODO 
	 * @author 张建明
	 * @Date 2018年10月12日 上午10:11:49
	 * @param info
	 * @param flag
	 * @return
	 */
	public WorkPlan saveForm(WorkPlan info,String flag);
	/**
	 * 把某些日期设置为工作日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 上午10:27:17
	 * @param dates
	 */
	void setWorkDay(String dates);
	
	/**
	 * 设置某些日期为休息日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月10日 下午2:33:55
	 * @param dates
	 */
	void setFreeDay(String dates);
	
	/**
	 * 恢复
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 下午2:37:09
	 * @param dates
	 */
	void setBack(String dates);
	
	/**
	 * 查询某段时间内，节假日设置里是否有数据
	 */
	public Map<Object, Object> getHolidaySets(String startDate,String endDate);
	
	/**
	 * 查询某天日期是否是节假日(1表示是休息日，0表示是工作日）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:44:13
	 * @param day
	 * @return
	 */
	public String isHoliday(String day);

	@SuppressWarnings("rawtypes")
	List findByDatePlan(String dateplan, String id);
	WorkPlan getByDate(String dates, String id);
	PageImpl getLeaderShowList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String type,
			String persionId, String logType, String dateLogYear, String dateLogMonth, String dateLogWeek,
			String timeCondition, String content);
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String type,
			String flag, String logType, String dateLogYear, String dateLogMonth, String dateLogWeek,
			String timeCondition, String content);
	int delete(String id);
	List<WorkPlan> upFinish(String id);
}
