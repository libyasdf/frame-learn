package com.sinosoft.sinoep.modules.system.config.toggle.services;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.toggle.entity.SysToggle;


/**
 *  
 * TODO 开关Service
 * @author 王富康
 * @Date 2018年5月11日 上午11:42:39
 */
public interface SysToggleService {
	
	/**
     * 查询开关列表
     * @param page 分页
     * @param name 开关的名称
     * @param key 开关标识，作为开关在业务逻辑中的唯一标识，业务逻辑根据此标识可以获取到该开关
     * @param describe 开关描述.
     * @return
     */
	PageImpl list(Pageable pageable,PageImpl pageImpl,String name, String key, String describe);
	
	/**
	 * 
	 * TODO 开关时，key值不能重复
	 * @author 王富康
	 * @Date 2018年5月14日 上午11:14:44
	 * @param st
	 * @return
	 */
	boolean check(SysToggle st,String checkItem);
    
	/**
	 * 根据ID获取一条开关数据
	 * @param id
	 * @return
	 */
	SysToggle findById(String id);
    
	/**
     * 添加，修改开关(修改时key不能被修改)
     * @param ST  锁对象
     * @return
     */
    SysToggle save(SysToggle ST,String ideaName);
    
    /**
     * 删除开关
     * @param id  开关id
     * @param key 开关一标识
     * @return 
     */
	int delete(String id);
	
	/**
     * 修改开关是否打开
     * @param id  锁id
     * @param isOpen  开关是否打开
     * @return
     */
    int updateToggle(String id,String isOpen);
    
    /**
     * 修改开关状态，是否可编辑
     * @param id  锁id
     * @param state  默认值为0。 各取值的定义为： 0：正常使用 1：开关被锁定(不可编辑) 2：暂停使用 3：开关被删除
     * @return
     */
    int updateToggleState(String id,String state);
    
    /**
	 * 根据key获取一条开关打开状态
	 * @param key
	 * @return
	 */
    List<SysToggle> getToggleIsOpenByKey(String key);
}
