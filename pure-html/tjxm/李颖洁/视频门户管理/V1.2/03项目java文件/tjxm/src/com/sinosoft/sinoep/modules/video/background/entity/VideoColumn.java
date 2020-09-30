package com.sinosoft.sinoep.modules.video.background.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "video_COLUMN", schema = "TJXM")
public class VideoColumn {
    private String id;
    private String creUserId;
    private String creUserName;
    private String creDeptId;
    private String creDeptName;
    private String creChushiId;
    private String creChushiName;
    private String creJuId;
    private String creJuName;
    private String visible;
    private String creTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private String superId;
    private String nodeLevel;
    private String columnName;
    private String columnCode;
    private String columnIcon;
    private String columnRemark;
    private String isShow;
    private String isSp;
    private String isFbfw;
    private String columnGlUserIds;
    private String columnGlUserNames;
    private String orderNo;
    private String columnFbUserNames;
    private List<VideoColumnFbUser> fbUserList;
    private List<VideoContent> contentList;
    private String isFirst;
    private Integer contentcnt;//当前登录人可以看到内容的个数
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
	
	private String code;
	
	/** 字典项的code码 */
	@Column(name = "code", length = 50)
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic
    @Column(name = "CRE_USER_ID")
    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    @Basic
    @Column(name = "CRE_USER_NAME")
    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    @Basic
    @Column(name = "CRE_DEPT_ID")
    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    @Basic
    @Column(name = "CRE_DEPT_NAME")
    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_ID")
    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    @Basic
    @Column(name = "CRE_CHUSHI_NAME")
    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    @Basic
    @Column(name = "CRE_JU_ID")
    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    @Basic
    @Column(name = "CRE_JU_NAME")
    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    @Basic
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "CRE_TIME")
    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    @Basic
    @Column(name = "UPDATE_USER_ID")
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Basic
    @Column(name = "UPDATE_USER_NAME")
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Basic
    @Column(name = "UPDATE_TIME")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "SUPER_ID")
    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    @Basic
    @Column(name = "NODE_LEVEL")
    public String getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(String nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    @Basic
    @Column(name = "COLUMN_NAME")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "COLUMN_CODE")
    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    @Basic
    @Column(name = "COLUMN_ICON")
    public String getColumnIcon() {
        return columnIcon;
    }

    public void setColumnIcon(String columnIcon) {
        this.columnIcon = columnIcon;
    }

    @Basic
    @Column(name = "COLUMN_REMARK")
    public String getColumnRemark() {
        return columnRemark;
    }

    public void setColumnRemark(String columnRemark) {
        this.columnRemark = columnRemark;
    }

    @Basic
    @Column(name = "IS_SHOW")
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Basic
    @Column(name = "IS_SP")
    public String getIsSp() {
        return isSp;
    }

    public void setIsSp(String isSp) {
        this.isSp = isSp;
    }

    @Basic
    @Column(name = "IS_FBFW")
    public String getIsFbfw() {
        return isFbfw;
    }

    public void setIsFbfw(String isFbfw) {
        this.isFbfw = isFbfw;
    }

    @Basic
    @Column(name = "COLUMN_GL_USER_IDS")
    public String getColumnGlUserIds() {
        return columnGlUserIds;
    }

    public void setColumnGlUserIds(String columnGlUserIds) {
        this.columnGlUserIds = columnGlUserIds;
    }

    @Basic
    @Column(name = "COLUMN_GL_USER_NAMES")
    public String getColumnGlUserNames() {
        return columnGlUserNames;
    }

    public void setColumnGlUserNames(String columnGlUserNames) {
        this.columnGlUserNames = columnGlUserNames;
    }

    @Basic
    @Column(name = "ORDER_NO")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Transient
    public String getColumnFbUserNames() {
        return columnFbUserNames;
    }

    public void setColumnFbUserNames(String columnFbUserNames) {
        this.columnFbUserNames = columnFbUserNames;
    }
    
    @Transient
	public List<VideoColumnFbUser> getFbUserList() {
		return fbUserList;
	}

	public void setFbUserList(List<VideoColumnFbUser> fbUserList) {
		this.fbUserList = fbUserList;
	}
	
	 @Column(name = "IS_FIRST")
	public String getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}
	
	 @Transient
	public List<VideoContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<VideoContent> contentList) {
		this.contentList = contentList;
	}
	
	@Transient
	public Integer getContentcnt() {
		return contentcnt;
	}

	public void setContentcnt(Integer contentcnt) {
		this.contentcnt = contentcnt;
	}

	
   
}
