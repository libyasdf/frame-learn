package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.service;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.entity.DdjsDyglPunishEntity;
import com.sinosoft.sinoep.common.util.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;


public interface DycfService {
	
 	
 	/**
	 * 保存党员处分页面
	 * @author 
	 * @Date 2019-02-27 16:48:38
	 * @param entity
	 * @return
	 */
 	public DdjsDyglPunishEntity save(DdjsDyglPunishEntity entity) throws IOException; 
 	
 	 /**
     * 根据主键ID查询一条数据 
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param id
     * @return
     */
 	public DdjsDyglPunishEntity getById(String id) throws Exception;
 	
 	 /**
     * 根据id逻辑删除党员处分 对应列表
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param entity
     * @return
     */
 	public int delete(DdjsDyglPunishEntity entity);
 	
 	 /**
     * 党员处分列表查询（带分页）
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param pageable
     * @param pageImpl
     * @param entity
     * @return
     */
 	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsDyglPunishEntity entity, String startTime, String endTime,String id, String type);
 	
}
