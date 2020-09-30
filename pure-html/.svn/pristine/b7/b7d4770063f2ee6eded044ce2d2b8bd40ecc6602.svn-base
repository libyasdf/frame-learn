package com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.entity.DjglPostManagementEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Map;


public interface DjglPostManagementService {
	
 	
 	/**
	 * 保存djgl_post_management页面
	 * @author xx
	 * @Date 2018-12-25 16:40:55
	 * @param entity entity是方法的参名
	 * @return 实体类
	 * @exception IOException IOException是扔出的异常类型
	 */
 	public DjglPostManagementEntity save(DjglPostManagementEntity entity) throws IOException;
 	
 	 /**
     * 根据主键ID查询一条数据 
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param id
     * @return
     */
 	public DjglPostManagementEntity getById(String id) throws Exception;
 	
 	 /**
     * 根据id逻辑删除djgl_post_management 对应列表
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param entity
     * @return
     */
 	public int delete(DjglPostManagementEntity entity);

	public int updateBySubflag(String subflag,String value,String id);

	/*
	 *获取职责书创建人当前起草流程完成的最新数据的id
	 */
	public DjglPostManagementEntity getNewEntity(DjglPostManagementEntity djglPostManagement);

	/*
	 *返回最新需带入数据-变更
	 */
	public DjglPostManagementEntity getNewBGEntity(DjglPostManagementEntity djglPostManagement);
 	 /**
     * djgl_post_management列表查询（带分页）
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param pageable
     * @param pageImpl
     * @param entity
     * @return
     */
 	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DjglPostManagementEntity entity,boolean identifying);
	//个人查询
	public PageImpl individualQuery(Pageable pageable, PageImpl pageImpl, DjglPostManagementEntity entity);
	public  String getDepId();
	//范围查询
	public Map rangeQuery(PageImpl pageImpl, DjglPostManagementEntity entity,String cxDeptid,boolean bol,boolean bolrange);
 	
}
