package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.entity.WmglNotice;

import org.springframework.data.domain.Pageable;

/**
 * TODO 外卖管理-基础配置
 * @author 李颖洁  
 * @date 2019年5月7日  下午2:23:06
 */
public interface WmglNoticeService {

    /**
     * TODO 获取列表分页数据
     * @author 李颖洁  
     * @date 2019年5月14日下午4:42:39
     * @param pageable
     * @param pageImpl
     * @param isValid
     * @param startDate
     * @param endDate
     * @return PageImpl
     */
    PageImpl list(Pageable pageable, PageImpl pageImpl, String isValid,String startDate,String endDate);

    /**
     * TODO 修改数据
     * @author 李颖洁  
     * @date 2019年5月7日下午3:06:19
     * @param notice
     * @return WmglNotice
     */
    WmglNotice saveForm(WmglNotice notice);

	/**
	 * TODO 获取修改、只读数据
	 * @author 李颖洁  
	 * @date 2019年5月14日下午4:42:57
	 * @param id
	 * @return WmglNotice
	 */
    WmglNotice getById(String id);

	/**
	 * TODO 获取最新的一条通知公告
	 * @author 马秋霞  
	 * @date 2019年5月14日下午4:43:13
	 * @return String
	 */
	String getLast();

	/** 
	 * TODO 根据id删除一条通知
	 * @author 李颖洁  
	 * @date 2019年5月14日下午4:09:34
	 * @param id
	 * @return int
	 */
	int deleteById(String id);

	
}
