package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.util.Map;

import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.LeaveRegular;


/**
 * 业务层service接口
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月20日 下午4:01:50
 */
public interface LeaveRegularService {
	
	/**
	 * 查询年休假规则的所有数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月23日 上午10:25:25
	 * @return
	 */
	Map<String,Object> findAll();
	
	/**
	 * 根据id查询年休假规则
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午4:31:59
	 * @param string
	 */
	LeaveRegular findById(String string);
	
	/**
	 * 保存年休假规则列表数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午4:40:56
	 * @param data
	 */
	void saveAll(String data,String dayNumbers);

}
