package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.meetingRoom.entity.MeetingRoomInfo;

public interface MeetingRoomInfoService {
	
		
	/**
	 * 
	 * TODO 获取会议室信息
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:58:41
	 * @param id
	 * @return
	 */
	MeetingRoomInfo getById(String id);
	/**
	 * 根据门牌号获取 判断唯一性
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月29日 上午10:11:20
	 * @param doorNumber
	 * @return
	 */
	List<MeetingRoomInfo> getByDoorNum(String doorNumber);
	
	/**
	 * 
	 * TODO 保存会议室信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:02:27
	 * @param meetingRoomInfo
	 * @return
	 */
	MeetingRoomInfo saveForm(MeetingRoomInfo meetingRoomInfo);
	
	/**
	 * 
	 * TODO 查询会议室列表
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
	 * TODO 查询会议室列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:11:36
	 * @param meetingRoomInfo
	 * @return
	 */
	List<MeetingRoomInfo> getList(MeetingRoomInfo meetingRoomInfo);
	
	/**
	 * 
	 * TODO 删除会议室
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:55:27
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 
	 * TODO 会议室排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:17:28
	 * @param ids
	 */
	void sort(String[] ids);
	
	/**
	 * 判断会议室编号是否重复
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月21日 下午4:01:52
	 * @param serviceDeptInfo
	 * @return
	 */
	boolean checkSdi(MeetingRoomInfo meetingRoomInfo);
}
