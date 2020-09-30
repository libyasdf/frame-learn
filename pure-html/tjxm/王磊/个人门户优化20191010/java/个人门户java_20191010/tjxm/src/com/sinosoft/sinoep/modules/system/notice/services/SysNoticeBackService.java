package com.sinosoft.sinoep.modules.system.notice.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeBack;

/**
 * TODO 通知公告反馈接口
 * @author 李利广
 * @Date 2018年9月5日 下午2:18:47
 */
public interface SysNoticeBackService {
	
	/**
	 * TODO 保存通知公告反馈结果
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:22:26
	 * @param noticeId 通知公告ID
	 * @return
	 */
	SysNoticeBack saveBack(SysNoticeBack back) throws Exception;
	
	/**
	 * TODO 获取当前用户指定通知公告的反馈信息
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	SysNoticeBack getBack(String noticeId) throws Exception;
	
	/**
	 * TODO 获取通知公告的所有反馈信息
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	PageImpl getAllBack(PageImpl page,String noticeId) throws Exception;

	/**
	 * TODO 统计未反馈处室
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	PageImpl getAllNoBackChu(PageImpl page,String noticeId) throws Exception;

	/**
	 * TODO 统计未反馈人员
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	PageImpl getAllNoBackUser(PageImpl page,String noticeId) throws Exception;

}
