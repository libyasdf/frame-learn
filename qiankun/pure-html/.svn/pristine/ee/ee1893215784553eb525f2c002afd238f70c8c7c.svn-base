package com.sinosoft.sinoep.modules.mypage.diningmenu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * TODO 食堂菜谱实体类
 * 
 * @author 张建明
 * @Date 2018年5月7日 上午10:23:40
 */
@Entity
@Table(name = "DINING_MENU")
public class DiningMenu {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "CRE_TIME")
	private String creTime;

	@Column(name = "CRE_USER_ID")
	private String creUserId;

	@Column(name = "CRE_USER_NAME")
	private String creUserName;

	@Column(name = "CRE_DEPT_ID")
	private String creDeptId;

	@Column(name = "CRE_DEPT_NAME")
	private String creDeptName;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "UPDATE_USER_NAME")
	private String updateUserName;

	@Column(name = "UPDATE_TIME")
	private String updateTime;

	@Column(name = "DATE_MENU")
	private String dateMenu;

	@Column(name = "VISIBLE")
	private String visible;

	@Column(name = "STATE")
	private String state;

	@Column(name = "WEEK")
	private String week;

	@Column(name = "WELFARE4")
	private String welfare4;

	@Column(name = "WELFARE5")
	private String welfare5;

	@Column(name = "WELFARE6")
	private String welfare6;

	@Column(name = "SPECIAL_DISHES")
	private String specialDishes;

	@Column(name = "SPECIAL_DISHES2")
	private String specialDishes2;

	@Column(name = "FLOUR")
	private String flour;

	@Column(name = "TAKE_OUT")
	private String takeOut;

	@Column(name = "SPARE2")
	private String spare2;

	@Column(name = "SPARE3")
	private String spare3;

	@Transient
	private String workitemid;

	@Transient
	private String cz = "";

	@Transient
	private String yibanid;

	public DiningMenu() {
		super();
	}

	public DiningMenu(String id, String creTime, String creUserId, String creUserName, String dateMenu, String visible,
			String state, String week, String welfare4, String welfare5, String welfare6, String specialDishes,
			String specialDishes2, String flour, String takeOut) {
		super();
		this.id = id;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.dateMenu = dateMenu;
		this.visible = visible;
		this.state = state;
		this.week = week;
		this.welfare4 = welfare4;
		this.welfare5 = welfare5;
		this.welfare6 = welfare6;
		this.specialDishes = specialDishes;
		this.specialDishes2 = specialDishes2;
		this.flour = flour;
		this.takeOut = takeOut;
	}

	public DiningMenu(String id, String creTime, String creUserId, String creUserName, String creDeptId,
			String creDeptName, String updateUserId, String updateUserName, String updateTime, String dateMenu,
			String visible, String state, String week, String welfare4, String welfare5, String welfare6,
			String specialDishes, String specialDishes2, String flour, String takeOut, String spare2, String spare3,
			String workitemid, String cz, String yibanid) {
		super();
		this.id = id;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
		this.dateMenu = dateMenu;
		this.visible = visible;
		this.state = state;
		this.week = week;
		this.welfare4 = welfare4;
		this.welfare5 = welfare5;
		this.welfare6 = welfare6;
		this.specialDishes = specialDishes;
		this.specialDishes2 = specialDishes2;
		this.flour = flour;
		this.takeOut = takeOut;
		this.spare2 = spare2;
		this.spare3 = spare3;
		this.workitemid = workitemid;
		this.cz = cz;
		this.yibanid = yibanid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDateMenu() {
		return dateMenu;
	}

	public void setDateMenu(String dateMenu) {
		this.dateMenu = dateMenu;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWelfare4() {
		return welfare4;
	}

	public void setWelfare4(String welfare4) {
		this.welfare4 = welfare4;
	}

	public String getWelfare5() {
		return welfare5;
	}

	public void setWelfare5(String welfare5) {
		this.welfare5 = welfare5;
	}

	public String getWelfare6() {
		return welfare6;
	}

	public void setWelfare6(String welfare6) {
		this.welfare6 = welfare6;
	}

	public String getSpecialDishes() {
		return specialDishes;
	}

	public void setSpecialDishes(String specialDishes) {
		this.specialDishes = specialDishes;
	}

	public String getSpecialDishes2() {
		return specialDishes2;
	}

	public void setSpecialDishes2(String specialDishes2) {
		this.specialDishes2 = specialDishes2;
	}

	public String getFlour() {
		return flour;
	}

	public void setFlour(String flour) {
		this.flour = flour;
	}

	public String getTakeOut() {
		return takeOut;
	}

	public void setTakeOut(String takeOut) {
		this.takeOut = takeOut;
	}

	public String getSpare2() {
		return spare2;
	}

	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}

	public String getSpare3() {
		return spare3;
	}

	public void setSpare3(String spare3) {
		this.spare3 = spare3;
	}

	public String getWorkitemid() {
		return workitemid;
	}

	public void setWorkitemid(String workitemid) {
		this.workitemid = workitemid;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getYibanid() {
		return yibanid;
	}

	public void setYibanid(String yibanid) {
		this.yibanid = yibanid;
	}

	/**
	 * 为hql连表查询，创建有参构造
	 * 
	 * @param yibanid
	 * @param id
	 * @param title
	 * @param workFlowId
	 * @param subflag
	 * @param note
	 * @param zhubDept
	 * @param zhubPerson
	 * @param zhubDeptNm
	 * @param zhubPersonNm
	 * @param xiebDept
	 * @param xiebPerson
	 * @param creTime
	 * @param creUserId
	 * @param creUserName
	 * @param idea
	 */
	public DiningMenu(String id, String dateMenu, String state, String week) {
		super();
		this.id = id;
		this.dateMenu = dateMenu;
		this.state = state;
		this.week = week;

	}
}
