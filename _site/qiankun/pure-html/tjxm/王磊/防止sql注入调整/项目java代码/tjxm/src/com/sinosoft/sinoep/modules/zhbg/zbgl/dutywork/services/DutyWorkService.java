package com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.services;

import java.text.ParseException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork;
import net.sf.json.JSONObject;

/**
 * TODO 值班工作通知业务层
 * @author 李颖洁  
 * @date 2018年7月10日  下午5:39:39
 */
public interface DutyWorkService {

	/**
	 * TODO 保存通知数据基本信息
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:00:12
	 * @param dutyWork
	 * @return DutyWork
	 */
	DutyWork saveNotice(DutyWork dutyWork);

	/**
	 * TODO 删除一条通知记录
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:00:26
	 * @param id
	 * @throws SysException
	 * @return int
	 */
	int delete(String id) throws SysException;

	/**
	 * TODO 获取通知列表
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:00:40
	 * @param pageable
	 * @param pageImpl
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param isSecurity
	 * @return
	 * @return PageImpl
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startTime, String endTime, String state, String isSecurity);

	/**
	 * TODO 根据id查询通知信息
	 * @author 李颖洁  
	 * @date 2018年7月17日下午9:00:48
	 * @param id
	 * @return
	 * @throws SysException
	 * @return DutyWork
	 */
	DutyWork getById(String id) throws SysException;

	/** TODO 根据ID更改发布通知状态
	 * @author 李颖洁  
	 * @date 2018年7月19日下午4:51:37
	 * @param id
	 * @return void
	 * @throws SysException 
	 */
	int updateState(String id) throws SysException;

	/** TODO 根据有效期获取期间的日期集合
	 * @author 李颖洁  
	 * @date 2018年7月31日下午9:06:31
	 * @param startTime
	 * @param endTime
	 * @throws ParseException
	 * @return List<DutyTable>
	 */
	List<JSONObject> getDateList(String startTime, String endTime) throws ParseException;

	/** TODO 判断是否有上报员
	 * @author 李颖洁  
	 * @date 2018年8月1日下午3:46:03
	 * @param deptIds 部门id
	 * @param deptNames 
	 * @return JSONObject
	 * @throws Exception 
	 */
	JSONObject hasReportUserByDeptId(String deptIds, String deptNames) throws Exception;

	
	


	

}
