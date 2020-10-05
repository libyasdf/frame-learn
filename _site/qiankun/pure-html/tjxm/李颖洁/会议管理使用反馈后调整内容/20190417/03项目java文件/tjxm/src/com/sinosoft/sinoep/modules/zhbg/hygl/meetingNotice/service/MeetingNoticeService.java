package com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.service;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.meetingNotice.entity.MeetingNoticeInfo;
import net.sf.json.JSONObject;

/**
 * 会议通知Service
 * TODO 
 * @author 郝灵涛
 * @Date 2018年7月9日 下午6:40:36
 */
public interface MeetingNoticeService {
	
	/**
	 * 保存
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年7月9日 下午6:41:34
	 * @param hyglNoticeInfo
	 * @return
	 * @throws Exception 
	 */
	MeetingNoticeInfo saveForm(MeetingNoticeInfo hyglNoticeInfo) throws Exception;

	/**
	 * 查询会议通知列表
	 * @author 郝灵涛
	 * @Date 2018年7月9日 下午7:38:16
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param meetingName
	 * @param status
	 * @return
	 */
	PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String meetingName,
			String status);

	/**
	 * 根据Id获取会议通知信息
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年7月9日 下午6:42:40
	 * @param id
	 * @return
	 */
	MeetingNoticeInfo getById(String id);

	/**
	 * 根据id删除会议通知数据 
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年7月9日 下午6:43:25
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	int delete(String id) throws Exception;

	/**
	 * 更新状态 TODO
	 * @author 冯超
	 * @Date 2018年4月18日 上午8:46:31
	 * @param id
	 * @param subflag
	 * @return
	 */
	MeetingNoticeInfo updateSubFlag(String id, String subflag);

	JSONObject hasConfidentiUser(String deptIds, String deptNames) throws Exception;
	
	/**
	 * 更改通知的注意事项
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年11月7日 上午9:39:42
	 * @param meetingNoticeId
	 * @param attentionItem
	 * @return
	 */
	void updateAttentionItem(String meetingNoticeId, String attentionItem);

	/**
	 * TODO 获取已经通过的会议的全部信息（带分页）
	 * @author 李颖洁  
	 * @date 2019年3月21日下午2:00:02
	 * @param pageable
	 * @param pageImpl
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param meeetingTitle 会议标题
	 * @param meetingType 会议类型
	 * @param hostDeptId 主办单位id
	 * @param meetingRoomId 会议室id
	 * @param ifInputByhand 是否手工录入 0 不是 1是
	 * @return JSONObject
	 */
	JSONObject getPageListMeetingAllInfo(Pageable pageable, PageImpl pageImpl, String startDate, String endDate,
			 String meeetingTitle,String meetingType,String hostDeptId,String meetingRoomId,String flg,String ifInputByhand);
	
	/**
	 * 根据会议申请的id查询会议通知
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年4月10日 上午9:48:06
	 * @param applyId
	 * @return
	 */
	MeetingNoticeInfo getByApplyId(String applyId);

}
