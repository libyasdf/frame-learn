package com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * @Author 王富康
 * @Description //TODO 立卷单位和录入人及立卷单位管理员关系实体类
 * @Date 2019/2/1 11:48
 * @Param
 * @return
 **/
@Entity
@Table(name = "dagl_cate_dept_person_relation")
public class DaglCateDeptPersonRelation {

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

  /** 	最近更新人名*/
  @Column(name = "update_user_name", length = 50)
  private String updateUserName;

  /** 	最近更新id*/
  @Column(name = "update_user_id", length = 50)
  private String updateUserId;

  /** 最近更新时间*/
  @Column(name = "update_time", length = 50)
  private String updateTime;

  /** 门类id*/
  @Column(name = "cate_id", length = 50)
  private String cateId;

  /** 数据字典里立卷单位名称*/
  @Column(name = "ljdw_name", length = 50)
  private String ljdwName;

  /** 数据字典对应的mark值*/
  @Column(name = "ljdw_mark", length = 50)
  private String ljdwMark;

  /** 录入人name*/
  @Column(name = "lrr_name", length = 50)
  private String lrrName;

  /** 录入人id*/
  @Column(name = "lrr_id", length = 50)
  private String lrrId;

  /** 立卷单位管理员name*/
  @Column(name = "ljdw_admin_name", length = 50)
  private String ljdwAdminName;

  /** 立卷单位管理员id*/
  @Column(name = "ljdw_admin_id", length = 50)
  private String ljdwAdminId;


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


  public String getUpdateUserName() {
    return updateUserName;
  }

  public void setUpdateUserName(String updateUserName) {
    this.updateUserName = updateUserName;
  }


  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }


  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }


  public String getCateId() {
    return cateId;
  }

  public void setCateId(String cateId) {
    this.cateId = cateId;
  }


  public String getLjdwName() {
    return ljdwName;
  }

  public void setLjdwName(String ljdwName) {
    this.ljdwName = ljdwName;
  }


  public String getLjdwMark() {
    return ljdwMark;
  }

  public void setLjdwMark(String ljdwMark) {
    this.ljdwMark = ljdwMark;
  }


  public String getLrrName() {
    return lrrName;
  }

  public void setLrrName(String lrrName) {
    this.lrrName = lrrName;
  }


  public String getLrrId() {
    return lrrId;
  }

  public void setLrrId(String lrrId) {
    this.lrrId = lrrId;
  }


  public String getLjdwAdminName() {
    return ljdwAdminName;
  }

  public void setLjdwAdminName(String ljdwAdminName) {
    this.ljdwAdminName = ljdwAdminName;
  }


  public String getLjdwAdminId() {
    return ljdwAdminId;
  }

  public void setLjdwAdminId(String ljdwAdminId) {
    this.ljdwAdminId = ljdwAdminId;
  }

  public DaglCateDeptPersonRelation() {
    super();
  }

  public DaglCateDeptPersonRelation(String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String creTime, String updateUserName, String updateUserId, String updateTime, String cateId, String ljdwName, String ljdwMark, String lrrName, String lrrId, String ljdwAdminName, String ljdwAdminId) {
    super();
    this.creUserId = creUserId;
    this.creUserName = creUserName;
    this.creDeptId = creDeptId;
    this.creDeptName = creDeptName;
    this.creChushiId = creChushiId;
    this.creChushiName = creChushiName;
    this.creJuId = creJuId;
    this.creJuName = creJuName;
    this.creTime = creTime;
    this.updateUserName = updateUserName;
    this.updateUserId = updateUserId;
    this.updateTime = updateTime;
    this.cateId = cateId;
    this.ljdwName = ljdwName;
    this.ljdwMark = ljdwMark;
    this.lrrName = lrrName;
    this.lrrId = lrrId;
    this.ljdwAdminName = ljdwAdminName;
    this.ljdwAdminId = ljdwAdminId;
  }
}
