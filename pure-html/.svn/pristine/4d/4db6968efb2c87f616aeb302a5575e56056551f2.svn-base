package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.entity.WmgLosecredit;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

/**
 * TODO 外卖管理-基础配置
 * @author 李颖洁  
 * @date 2019年5月7日  下午2:23:06
 */
public interface WmgLosecreditService {

    /**
     * TODO 获取列表分页数据
     * @author 李颖洁  
     * @date 2019年5月10日上午10:43:17
     * @param pageable
     * @param pageImpl
     * @param losecredit
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return PageImpl
     */
    PageImpl list(Pageable pageable, PageImpl pageImpl,WmgLosecredit losecredit,String startDate,String endDate);

	/** 
	 * TODO 获取修改、只读数据
	 * @author 李颖洁  
	 * @date 2019年5月7日下午2:53:37
	 * @param id
	 * @return WmgLosecredit
	 */
    WmgLosecredit getById(String id);

	/** 
	 * TODO 手动解除锁定
	 * @author 李颖洁  
	 * @date 2019年5月9日下午3:48:31
	 * @param id
	 * @return int
	 */
	int updateType(String id);
	
	/**
	 * TODO 根据人员id判断是否锁定状态
	 * @author 李颖洁  
	 * @date 2019年5月9日下午6:40:10
	 * @param userIds 逗号分隔
	 * @return Map<String,Object>
	 */
	Map<String,Object> losecreditOrNot(String userIds);

	/**
	 * TODO 插入失信记录数据
	 * @author 李颖洁  
	 * @date 2019年5月10日下午2:02:59
	 * @param userIds 多个已逗号分隔
	 * @param year 年度
	 * @return List<WmgLosecredit>
	 */
	List<WmgLosecredit> saveLosecredit(String userIds,String year);
	
	/**
	 * 获取当前用户的锁定状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午1:54:09
	 * @return
	 */
	Map<String,String> getLoseState();
}
