package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingDeptAndServices;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingService.entity.MeetingServiceInfo;

/**
 * 会务服务项
 * TODO 
 * @author 冯超
 * @Date 2018年8月5日 上午9:43:30
 */
public interface MeetingServiceService {
	
		
	/**
	 * 
	 * TODO 获取服务项信息
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:58:41
	 * @param id
	 * @return
	 */
	MeetingServiceInfo getById(String id);
	
	/**
	 * 
	 * TODO 保存服务项信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:02:27
	 * @param meetingServiceInfo
	 * @return
	 */
	MeetingDeptAndServices saveForm(MeetingDeptAndServices meetingDeptAndServices);
	
	/**
	 * 
	 * TODO 查询服务到位列表
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:28:14
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String name);
	
	/**
	 * 
	 * TODO 查询服务项列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:11:36
	 * @param meetingServiceInfo
	 * @return
	 */
	List<MeetingServiceInfo> getList(MeetingServiceInfo meetingServiceInfo);
	
	/**
	 * 获取所有的会务服务项（会议申请的时候加载）
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月22日 上午10:00:34
	 * @param meetingServiceInfo
	 * @return
	 */
	List<MeetingServiceInfo> getAllList(MeetingServiceInfo meetingServiceInfo);
	
	/**
	 * 
	 * TODO 删除服务项
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:55:27
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 根据外键删除
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月3日 上午11:10:10
	 * @param serviceDeptId
	 * @return
	 */
	int deleteByServiceDeptID(String serviceDeptId);
	
	/**
	 * 批量删除
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月2日 上午10:28:59
	 * @param ids
	 * @return
	 */
	int deleteIds(String ids);
	
	/**
	 * 
	 * TODO 服务项排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:17:28
	 * @param ids
	 */
	void sort(String[] ids);

	List<MeetingServiceInfo> getListByUser(MeetingServiceInfo meetingServiceInfo);
	
	
}
