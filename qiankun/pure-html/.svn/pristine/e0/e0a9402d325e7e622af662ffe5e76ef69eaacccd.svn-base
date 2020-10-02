package com.sinosoft.sinoep.modules.mypage.diningmenu.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.diningmenu.dao.DiningMenuDao;
import com.sinosoft.sinoep.modules.mypage.diningmenu.entity.DiningMenu;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 业务层service实现类 TODO
 * 
 * @author 李利广
 * @Date 2018年3月15日 下午8:51:44
 */
@Service
public class DiningMenuServiceImpl implements DiningMenuService {

	@Autowired
	private DiningMenuDao diningMenuDao;

	/**
	 * 保存方法 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:48:05
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	@Override
	public DiningMenu saveForm(DiningMenu plan, String ideaName) throws Exception {
		plan.setVisible(CommonConstants.VISIBLE[1]);
		//时间
		String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String userId = UserUtil.getCruUserId();
		String userName = UserUtil.getCruUserName();
		String creDeptId =  UserUtil.getCruDeptId();
		String creDeptName = UserUtil.getCruDeptName();
		if (StringUtils.isBlank(plan.getId())) {
			plan.setCreTime(creTime);
			plan.setCreUserId(userId);
			plan.setCreUserName(userName);
			plan.setCreDeptId(creDeptId);
			plan.setCreDeptName(creDeptName);
			plan.setWeek(dayForWeek(plan.getDateMenu()) + "");
			plan = diningMenuDao.save(plan);
		} else {
			DiningMenu oldPlan = diningMenuDao.findOne(plan.getId());
			oldPlan.setWeek(dayForWeek(plan.getDateMenu()) + "");
			oldPlan.setState(plan.getState());
			oldPlan.setDateMenu(plan.getDateMenu());
			/*oldPlan.setFlour(plan.getFlour());
			oldPlan.setSpecialDishes(plan.getSpecialDishes());
			oldPlan.setSpecialDishes2(plan.getSpecialDishes2());
			oldPlan.setTakeOut(plan.getTakeOut());*/
			oldPlan.setWelfare4(plan.getWelfare4());
			oldPlan.setWelfare5(plan.getWelfare5());
			//oldPlan.setWelfare6(plan.getWelfare6());
			//更新人信息
			oldPlan.setUpdateTime(creTime);
			oldPlan.setUpdateUserId(userId);
			oldPlan.setUpdateUserName(userName);
			diningMenuDao.save(oldPlan);
		}
		return plan;
	}

	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 删除一条记录 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:48:21
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDiningMenu(String id) {
		String jpql = "update DiningMenu t set t.visible = ? where t.id = ?";
		return diningMenuDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}

	/**
	 * 根据主键ID查询一条数据 TODO
	 * 
	 * @author 张建明
	 * @Date 2018年3月15日 下午8:48:36
	 * @param id
	 * @return
	 */
	@Override
	public DiningMenu getById(String id) {
		return diningMenuDao.findOne(id);
	}

	/**
	 * 
	 * TODO 查询列表，按食谱日期倒叙
	 * @author 赵海龙
	 * @Date 2018年5月22日 下午4:34:20
	 * @param pageable
	 * @param pageImpl
	 * @param startDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startDate, String endDate, String state) {

		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("select new com.sinosoft.sinoep.modules.mypage.diningmenu.entity.DiningMenu(t.id, "
				+ "t.dateMenu, t.state, t.week)");
		querySql.append("	from DiningMenu t");
		querySql.append("	where ");
		querySql.append("   t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		if (StringUtils.isNotBlank(state)) {
			querySql.append("   and t.state = ?");
			para.add(state);
		}
		if (StringUtils.isNotBlank(startDate)) {
			querySql.append("   and t.dateMenu >= ?");
			para.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			querySql.append("   and t.dateMenu <= ?");
			para.add(endDate);
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.dateMenu desc) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<DiningMenu> page = diningMenuDao.query(querySql.toString(), pageable, para.toArray());
		// 草稿列表，添加操作列
		List<DiningMenu> content = page.getContent();
		for (DiningMenu diningMenu : content) {
			diningMenu.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
		}
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	@Override
	public DiningMenu getByDate(String date) {
		List<DiningMenu> listDm = diningMenuDao.getAllChildTypes(date);
		if (listDm != null && !listDm.isEmpty()) {
			DiningMenu dm = listDm.get(0);
			return dm;
		} else {
			return null;
		}
	}

	@Override
	public List<DiningMenu> isExist(String date, String id) {
			List<DiningMenu> listDm = diningMenuDao.getAllDiningMenus(date,id);
			return listDm;
	}

	@Override
	public List<DiningMenu> getsByDate(String date,String endDate,String state) {
		return diningMenuDao.getDiningMenus(date,endDate, state);
	}

}
