package com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.hygl.basicSet.serviceDept.entity.ServiceDeptInfo;

/**
 * 会务服务单位信息
 * TODO 
 * @author 冯超
 * @Date 2018年8月5日 上午9:40:50
 */
public interface ServiceDeptService {
	
		
	/**
	 * 
	 * TODO 获取服务单位信息
	 * @author 冯超
	 * @Date 2018年5月7日 上午11:58:41
	 * @param id
	 * @return
	 */
	ServiceDeptInfo getById(String id);
	
	/**
	 * 
	 * TODO 保存服务单位信息
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:02:27
	 * @param meetingRoomInfo
	 * @return
	 */
	ServiceDeptInfo saveForm(ServiceDeptInfo serviceDeptInfo);
	
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
	 * TODO 查询服务单位列表不分页
	 * @author 冯超
	 * @Date 2018年5月7日 下午6:11:36
	 * @param meetingRoomInfo
	 * @return
	 */
	List<ServiceDeptInfo> getList(ServiceDeptInfo serviceDeptInfo);
	
	/**
	 * 
	 * TODO 删除服务单位
	 * @author 冯超
	 * @Date 2018年5月7日 下午2:55:27
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 
	 * TODO 服务单位排序
	 * @author 冯超
	 * @Date 2018年5月7日 下午5:17:28
	 * @param ids
	 */
	void sort(String[] ids);
	
	/**
	 * 
	 * TODO 
	 * @author 冯超
	 * @Date 2018年8月3日 下午2:05:31
	 * @param serviceDeptInfo
	 * @return
	 */
	boolean checkSdi(ServiceDeptInfo serviceDeptInfo);
	
	
}
