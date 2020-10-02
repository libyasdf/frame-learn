package com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.service;


import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.overTime.entity.OverTime;
import net.sf.json.JSONObject;

/**
 * 加班业务层service接口 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:39:54
 */
public interface OverTimeService {

	/**
	 * 保存加班申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:40:07
	 * @param overTime
	 * @param ideaName
	 * @return
	 */
	OverTime saveForm(OverTime overTime, String ideaName);

	/**
	 * 删除加班申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:40:22
	 * @param id
	 * @return
	 */
	int deleteOverTime(String id);

	/**
	 * 根据主键ID查询一条加班数据 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:40:41
	 * @param id
	 * @return
	 */
	OverTime getById(String id);

	/**
	 * 更新业务表流程状态 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:41:00
	 * @param id
	 * @param subflag
	 * @return
	 */
	String updateSubFlag(String id, String subflag);

	/**
	 * 查询加班申请审批列表（连表查询） TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:41:20
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param subflag
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String title, String startDate, String endDate,
			String subflag);

	/**
	 * 查询加班申请草稿箱列表 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:41:38
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String overTimeType, String startDate, String endDate,
			String subflag);
	
	/**
	 * 根据查询条件查询当前用户的加班列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午5:59:56
	 * @param overTimeType
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	List<OverTime> getList(String userids,String overTimeType, String startDate, String endDate, String subflag);
	
	/**
	 * 根据加班日期获取加班的开始时间和结束数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 上午9:38:39
	 * @param overTimeDate
	 * @return
	 */
	JSONObject getStartAndEndTime(String overTimeDate);
	
	/**
	 * 获取某一天加班的开始结束日期以及是否是节假日
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月20日 下午4:33:57
	 * @param overTimeDate
	 * @return
	 * @throws Exception 
	 */
	JSONObject getStartEndTimeAndIsHoliday(String day) throws Exception;
	/**
	 * 根据季度的开始时间和结束时间获取加班的天数和小时数
	 * @author 郝灵涛
	 * @Date 2018年7月30日 上午11:11:20
	 * @param beginQuarters
	 * @param endQuerters
	 * @return
	 * @throws Exception
	 */
	List<OverTime> getTalOverTimeDaysAndHours(String beginQuarters,String endQuerters) throws Exception;
	
	/**
	 * 根据条件查询数据库中加班查询的记录条数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月29日 下午2:13:13
	 * @param timeRange
	 * @param state
	 * @return
	 */
	long getCnt(String userids, String startDate, String endDate, String subflag);
	
	/**
	 * 查询某些用户某段时间审批通过的加班数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午11:04:20
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	List<Map<String, Object>> findUserData(String startDate, String endDate, String commomSplit);

}
