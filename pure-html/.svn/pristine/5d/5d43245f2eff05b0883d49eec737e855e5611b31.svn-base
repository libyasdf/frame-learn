package com.sinosoft.sinoep.modules.mypage.diningmenu.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.diningmenu.entity.DiningMenu;

/**
 * 业务层service接口 TODO
 * 
 * @author 张建明
 * @Date 2018年3月15日 下午8:47:43
 */
public interface DiningMenuService {

	/**
	 * 保存方法 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:48:05
	 * @param diningMenu
	 * @return
	 * @throws Exception
	 */
	DiningMenu saveForm(DiningMenu diningMenu, String ideaName) throws Exception;

	/**
	 * 删除一条记录 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:48:21
	 * @param id
	 * @return
	 */
	int deleteDiningMenu(String id);

	/**
	 * 根据主键ID查询一条数据 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:48:36
	 * @param id
	 * @return
	 */
	DiningMenu getById(String id);

	/**
	 * 查询审批列表（连表查询） TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:49:13
	 * @param pageable
	 * @param userId
	 * @param pageImpl
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param subflag
	 * @return
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String subflag);

	DiningMenu getByDate(String date);

	List<DiningMenu> isExist(String date, String id);
	
	List<DiningMenu> getsByDate(String date,String endDate,String state);
}
