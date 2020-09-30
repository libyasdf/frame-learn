package com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.businessTrip.entity.BusinessTrip;

/**
 * 出差业务层service接口 TODO
 * 
 * @author 张建明
 * @Date 2018年4月12日 上午10:39:54
 */
public interface BusinessTripService {

	/**
	 * 保存出差申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:40:07
	 * @param chuChai
	 * @param ideaName
	 * @return
	 */
	BusinessTrip saveForm(BusinessTrip businessTrip, String ideaName);

	/**
	 * 删除出差申请 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:40:22
	 * @param id
	 * @return
	 */
	int deleteBusinessTrip(String id);

	/**
	 * 根据主键ID查询一条出差数据 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:40:41
	 * @param id
	 * @return
	 */
	BusinessTrip getById(String id);

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
	 * 查询出差申请审批列表（连表查询） TODO
	 * 
	 * @author 张建明
	 * @Date 2018年4月12日 上午10:41:20
	 * @param pageable
	 * @param pageImpl
	 * @param title
	 * @param subflag
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String busiTripType, String startDate, String endDate,
			String subflag);

	/**
	 * 查询出差申请草稿箱列表 TODO
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
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String title, String startDate, String endDate,
			String subflag);
	/**
	 * 
	 * 根据条件查询出差列表 TODO 
	 * @author 张建明
	 * @Date 2018年7月9日 下午6:52:53
	 * @param overTimeType
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	List<BusinessTrip> getList(String overTimeType, String startDate, String endDate, String subflag);
	
	/**
	 * 查询某用户该天的出差记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年7月24日 下午1:00:04
	 * @param userId
	 * @param day
	 * @return
	 */
	List<Map<String,Object>> getDataByUseridAndDay(String userId,String day);
    
	/**
     * 根据条件查询出差记录 TODO 
     * TODO 
     * @author 郝灵涛
     * @Date 2018年8月29日 下午2:57:54
     * @param overTimeType
     * @param startDate
     * @param endDate
     * @param subflag
     * @return
     */
	PageImpl getBusinessTripRecordList(Pageable pageable, PageImpl pageImpl,String busiType, String startDate, String endDate);
	
	/**
	 * //查询某段时间某些人做为同行人的出差记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月31日 上午11:47:23
	 * @param startDate
	 * @param endDate
	 * @param users
	 * @return
	 */
	List<Map<String,Object>> findTripColleagueByUserId(String startDate, String endDate, String[] userids);
	
	/**
	 * /查询某写用户某段时间的出差数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月4日 上午10:47:25
	 * @param startDate
	 * @param endDate
	 * @param commomSplit
	 * @return
	 */
	List<Map<String, Object>> findUserData(String startDate, String endDate, String userids);
	
	
}
