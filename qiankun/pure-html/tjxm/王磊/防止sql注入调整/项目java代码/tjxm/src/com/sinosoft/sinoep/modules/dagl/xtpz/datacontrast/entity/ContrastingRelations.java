package com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

/**
 * @Author 王富康
 * @Description //TODO 对照表关系的实体类
 * @Date 2018/11/13 20:24
 * @Param
 * @return
 **/
@Entity
@Table(name = "dagl_contrasting_relations")
public class ContrastingRelations {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 36)
    private String id;

    /** 对照关系名称 */
    @Column(name = "contrast_name", length = 50)
    private String contrastName;

    /** 原表name(相当于id，唯一标识)  */
    @Column(name = "source_name", length = 50)
    private String sourceName;

    /** 目标表name(相当于id，唯一标识) */
    @Column(name = "target_name", length = 50)
    private String targetName;

    /** 源表中文名 */
    @Column(name = "source_chn_name", length = 50)
    private String sourceChnName;

    /** 目标表中文名 */
    @Column(name = "target_chn_name", length = 50)
    private String targetChnName;

    /** 创建人id */
    @Column(name = "cre_user_id", length = 50)
    private String creUserId;//创建人id

    /** 创建人姓名 */
    @Column(name = "cre_user_name", length = 50)
    private String creUserName;//创建人姓名

    /** 创建人部门id */
    @Column(name = "cre_dept_id", length = 50)
    private String creDeptId;//创建人部门id

    /** 创建人部门名 */
    @Column(name = "cre_dept_name", length = 50)
    private String creDeptName;//创建人部门名

    /** 创建人处室id */
    @Column(name = "cre_chushi_id", length = 50)
    private String creChushiId;//创建人处室id

    /** 创建人处室名 */
    @Column(name = "cre_chushi_name", length = 50)
    private String creChushiName;//创建人处室名

    /** 创建人局id */
    @Column(name = "cre_ju_id", length = 50)
    private String creJuId;//创建人局id

    /** 创建人局名 */
    @Column(name = "cre_ju_name", length = 50)
    private String creJuName;//创建人局名

    /** 创建时间 */
    @Column(name = "cre_time", length = 30)
    private String creTime;//创建时间

    /** 	最近更新id*/
    @Column(name = "update_user_id", length = 50)
    private String updateUserId;

    /** 	最近更新人名*/
    @Column(name = "update_user_name", length = 50)
    private String updateUserName;

    /** 最近更新时间*/
    @Column(name = "update_time", length = 50)
    private String updateTime;

    /** 逻辑删除标识 */
    @Column(name = "visible", length = 1)
    private String visible;//逻辑删除标识
    
    //文电-》档案 时立卷单位和录入人从配置中获取 王磊添加 20190211
    //立卷单位名称
    @Transient
    private String ljdwName;
    
    //立卷单位mark值（数据字典中配置）
    @Transient
    private String ljdwMark;
    
    //录入人id
    @Transient
    private String lrrId;
    
    //录入人姓名
    @Transient
    private String lrrName;
    //文电-》档案 时立卷单位和录入人从配置中获取 王磊添加 20190211
    
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

	public String getLrrId() {
		return lrrId;
	}

	public void setLrrId(String lrrId) {
		this.lrrId = lrrId;
	}

	public String getLrrName() {
		return lrrName;
	}

	public void setLrrName(String lrrName) {
		this.lrrName = lrrName;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContrastName() {
        return contrastName;
    }

    public void setContrastName(String contrastName) {
        this.contrastName = contrastName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getSourceChnName() {
        return sourceChnName;
    }

    public void setSourceChnName(String sourceChnName) {
        this.sourceChnName = sourceChnName;
    }

    public String getTargetChnName() {
        return targetChnName;
    }

    public void setTargetChnName(String targetChnName) {
        this.targetChnName = targetChnName;
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

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public ContrastingRelations() {
        super();
    }

    public ContrastingRelations(String contrastName, String sourceName, String targetName, String sourceChnName, String targetChnName, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String creTime, String updateUserId, String updateUserName, String updateTime, String visible) {
        super();
        this.contrastName = contrastName;
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.sourceChnName = sourceChnName;
        this.targetChnName = targetChnName;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.creTime = creTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.visible = visible;
    }
}
