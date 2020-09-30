package com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.entity.DutyWorkIncept;
import net.sf.json.JSONObject;

/**
 * TODO 
 * @author 李颖洁  
 * @date 2018年7月10日  下午5:39:39
 */
public interface DutyWorkInceptService {

	/**
	 * TODO 发布通知(根据部门保存通知接收表)
	 * @author 李颖洁  
	 * @date 2018年7月17日下午8:57:43
	 * @param dutyWork
	 * @param deptIds
	 * @param deptNames
	 * @return void
	 */
	List<DutyWorkIncept> sendNotice(DutyWork dutyWork,String deptIds,String deptNames);

	/** 
	 * TODO 获取处室接收的通知列表
	 * @author 李颖洁  
	 * @date 2018年7月19日上午9:55:52
	 * @param pageable
	 * @param pageImpl
	 * @param startTime
	 * @param endTime
	 * @param isSecurity
	 * @param isReport
	 * @param isSecurity
	 * @return PageImpl
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startTime, String endTime,
			String isRead,String isReport, String isSecurity);

	/** 
	 * TODO 获取本处室的通知信息和修改处室通知的读取状态
	 * @author 李颖洁  
	 * @date 2018年7月19日上午10:56:03
	 * @param id
	 * @param isRead
	 * @throws SysException
	 * @return DutyWork
	 */
	DutyWork getById(String id, String isRead) throws SysException;

	/** 
	 * TODO 根据通知ID查询所有处室接收的通知
	 * @author 李颖洁  
	 * @date 2018年7月26日下午9:39:59
	 * @param zbNoticeId
	 * @return PageImpl
	 */
	List<DutyWorkIncept> getList(PageImpl pageImpl,String zbNoticeId, String state);

	/** 
	 * TODO 修改部门值班表上报状态
	 * @author 李颖洁  
	 * @date 2018年7月30日下午4:45:49
	 * @param id
	 * @return void
	 * @throws SysException 
	 */
	int updateState(String id) throws SysException;

	/** TODO 查询消息id
	 * @author 李颖洁  
	 * @date 2018年8月2日下午5:11:28
	 * @param accepterid
	 * @param senderid
	 * @param subject
	 * @param content
	 * @return NotityMessage
	 */
	JSONObject queryMessageId(NotityMessage notityMessage);

	/** TODO 根据接收表ID查询接收表
	 * @author 李颖洁  
	 * @date 2018年8月3日上午9:50:40
	 * @param id
	 * @return DutyWorkIncept
	 */
	DutyWorkIncept getInceptById(String id);

	/** TODO 根据ID删除接收表数据
	 * @author 李颖洁  
	 * @date 2018年8月9日下午1:40:03
	 * @param id
	 * @throws SysException
	 * @return int
	 */
	int delete(String id,String noticeId) throws SysException;

	/** TODO 根据通知信息id批量删除
	 * @author 李颖洁  
	 * @date 2018年8月9日下午1:46:08
	 * @param id
	 * @throws SysException
	 * @return int
	 */
	int deleteBatch(String id) throws SysException;

	/** TODO 发布通知后修改为可见
	 * @author 李颖洁  
	 * @date 2018年8月9日下午3:38:11
	 * @param id
	 * @throws SysException
	 * @return int
	 */
	int updateVisible(String id,String deptId) throws SysException;

	/** TODO 获取未发布保存的部门
	 * @author 李颖洁  
	 * @date 2018年8月9日下午4:48:36
	 * @param zbNoticeId
	 * @return List<DutyWorkIncept>
	 */
	List<DutyWorkIncept> getDeptList(String zbNoticeId);


}
