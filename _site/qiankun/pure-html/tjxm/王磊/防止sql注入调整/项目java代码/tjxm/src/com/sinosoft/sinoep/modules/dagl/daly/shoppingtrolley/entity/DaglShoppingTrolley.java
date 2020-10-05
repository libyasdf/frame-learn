package com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * @Author 王富康
 * @Description //TODO 购物车实体类
 * @Date 2019/2/18 14:48
 * @Param
 * @return
 **/
@Entity
@Table(name = "dagl_shopping_trolley")
public class DaglShoppingTrolley {

  /** 主键id */
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "uuid")
  @GeneratedValue(generator = "idGenerator")
  @Column(name = "id", length = 36)
  private String id;

  /** 创建人id */
  @Column(name = "cre_user_id", length = 50)
  private String creUserId;

  /** 创建人姓名 */
  @Column(name = "cre_user_name", length = 50)
  private String creUserName;

  /** 创建人部门id */
  @Column(name = "cre_dept_id", length = 50)
  private String creDeptId;

  /** 创建人部门名 */
  @Column(name = "cre_dept_name", length = 50)
  private String creDeptName;

  /** 创建人处室id */
  @Column(name = "cre_chushi_id", length = 50)
  private String creChushiId;

  /** 创建人处室名 */
  @Column(name = "cre_chushi_name", length = 50)
  private String creChushiName;

  /** 创建人局id */
  @Column(name = "cre_ju_id", length = 50)
  private String creJuId;

  /** 创建人局名 */
  @Column(name = "cre_ju_name", length = 50)
  private String creJuName;

  /** 创建时间 */
  @Column(name = "cre_time", length = 30)
  private String creTime;

  /** 档案唯一标识 */
  @Column(name = "recid", length = 50)
  private String recid;

  /** 题名 */
  @Column(name = "main_title", length = 200)
  private String mainTitle;

  /** 立卷单位 */
  @Column(name = "basefolder_unit", length = 50)
  private String basefolderUnit;

  /** 立卷单位mark */
  @Column(name = "ljdw_mark", length = 50)
  private String ljdwMark;

  /** 档号 */
  @Column(name = "archive_no", length = 100)
  private String archiveNo;

  /** 门类代号，例如文书档案编号w，Q2档案Q2等 */
  @Column(name = "category_no", length = 30)
  private String categoryNo;

  /** 门类名称 */
  @Column(name = "category_name", length = 30)
  private String categoryName;

  /** 档案所在业务表 */
  @Column(name = "table_name", length = 30)
  private String tableName;

  /** 借阅人id */
  @Column(name = "borrow_user_id", length = 50)
  private String borrowUserId;

  /** 借阅人name */
  @Column(name = "borrow_user_name", length = 30)
  private String borrowUserName;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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


  public String getCreChushiId() {
    return creChushiId;
  }

  public void setCreChushiId(String creChushiId) {
    this.creChushiId = creChushiId;
  }


  public String getCreChushiName() {
    return creChushiName;
  }

  public void setCreChushiName(String creChushiName) {
    this.creChushiName = creChushiName;
  }


  public String getCreJuId() {
    return creJuId;
  }

  public void setCreJuId(String creJuId) {
    this.creJuId = creJuId;
  }


  public String getCreJuName() {
    return creJuName;
  }

  public void setCreJuName(String creJuName) {
    this.creJuName = creJuName;
  }


  public String getCreTime() {
    return creTime;
  }

  public void setCreTime(String creTime) {
    this.creTime = creTime;
  }


  public String getRecid() {
    return recid;
  }

  public void setRecid(String recid) {
    this.recid = recid;
  }


  public String getMainTitle() {
    return mainTitle;
  }

  public void setMainTitle(String mainTitle) {
    this.mainTitle = mainTitle;
  }


  public String getBasefolderUnit() {
    return basefolderUnit;
  }

  public void setBasefolderUnit(String basefolderUnit) {
    this.basefolderUnit = basefolderUnit;
  }


  public String getLjdwMark() {
    return ljdwMark;
  }

  public void setLjdwMark(String ljdwMark) {
    this.ljdwMark = ljdwMark;
  }


  public String getArchiveNo() {
    return archiveNo;
  }

  public void setArchiveNo(String archiveNo) {
    this.archiveNo = archiveNo;
  }


  public String getCategoryNo() {
    return categoryNo;
  }

  public void setCategoryNo(String categoryNo) {
    this.categoryNo = categoryNo;
  }


  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getBorrowUserId() {
    return borrowUserId;
  }

  public void setBorrowUserId(String borrowUserId) {
    this.borrowUserId = borrowUserId;
  }

  public String getBorrowUserName() {
    return borrowUserName;
  }

  public void setBorrowUserName(String borrowUserName) {
    this.borrowUserName = borrowUserName;
  }
}
