package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.entity.WmglConfig;
import org.springframework.data.domain.Pageable;

/**
 * TODO 外卖管理-基础配置
 * @author 李颖洁  
 * @date 2019年5月7日  下午2:23:06
 */
public interface WmglConfigService {

    /**
     * TODO 获取列表分页数据
     * @author 李颖洁  
     * @date 2019年5月7日下午3:05:17
     * @param pageable
     * @param pageImpl
     * @param isvalid
     * @return PageImpl
     */
    PageImpl list(Pageable pageable, PageImpl pageImpl, String isvalid);

    /**
     * TODO 修改数据
     * @author 李颖洁  
     * @date 2019年5月7日下午3:06:19
     * @param con
     * @return Config
     */
    WmglConfig updateConfig(WmglConfig con);

	/** 
	 * TODO 获取修改、只读数据
	 * @author 李颖洁  
	 * @date 2019年5月7日下午2:53:37
	 * @param id
	 * @return Config
	 */
    WmglConfig getById(String id);

	/**
	 * TODO 根据年度查询解锁时间
	 * @author 李颖洁  
	 * @date 2019年5月10日下午2:40:30
	 * @param year 年度
	 * @return String
	 */
	String getLoseTimeByYear(String year);
	
	/**
	 * 获取本年度的注意事项
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月16日 下午4:38:16
	 * @return
	 */
	String getAttendItem();

	
}
