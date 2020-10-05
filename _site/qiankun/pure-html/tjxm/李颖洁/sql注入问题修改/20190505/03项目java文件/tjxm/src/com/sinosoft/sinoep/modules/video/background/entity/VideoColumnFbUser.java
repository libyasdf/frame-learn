package com.sinosoft.sinoep.modules.video.background.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "video_COLUMN_FB_USER", schema = "TJXM")
public class VideoColumnFbUser {
    private String id;
    private String creUserId;
    private String creUserName;
    private String visible;
    private String creTiem;
    private String fbUserId;
    private String fbUserName;
    private String shUserId;
    private String shUserName;
    private String columnId;
    private String fbDeptId;
    private String shDeptId;

    public VideoColumnFbUser() {
    }

	public VideoColumnFbUser(String id, String creUserId, String creUserName, String visible, String creTiem,
			String fbUserId, String fbUserName, String shUserId, String shUserName, String columnId, String fbDeptId,
			String shDeptId) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.visible = visible;
		this.creTiem = creTiem;
		this.fbUserId = fbUserId;
		this.fbUserName = fbUserName;
		this.shUserId = shUserId;
		this.shUserName = shUserName;
		this.columnId = columnId;
		this.fbDeptId = fbDeptId;
		this.shDeptId = shDeptId;
	}



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
    @Column(name = "VISIBLE")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Basic
    @Column(name = "CRE_TIEM")
    public String getCreTiem() {
        return creTiem;
    }

    public void setCreTiem(String creTiem) {
        this.creTiem = creTiem;
    }

    @Basic
    @Column(name = "FB_USER_ID")
    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    @Basic
    @Column(name = "FB_USER_NAME")
    public String getFbUserName() {
        return fbUserName;
    }

    public void setFbUserName(String fbUserName) {
        this.fbUserName = fbUserName;
    }

    @Basic
    @Column(name = "SH_USER_ID")
    public String getShUserId() {
        return shUserId;
    }

    public void setShUserId(String shUserId) {
        this.shUserId = shUserId;
    }

    @Basic
    @Column(name = "SH_USER_NAME")
    public String getShUserName() {
        return shUserName;
    }

    public void setShUserName(String shUserName) {
        this.shUserName = shUserName;
    }

    @Basic
    @Column(name = "COLUMN_ID")
    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Basic
    @Column(name = "fb_dept_id")
	public String getFbDeptId() {
		return fbDeptId;
	}

	public void setFbDeptId(String fbDeptId) {
		this.fbDeptId = fbDeptId;
	}

	@Basic
    @Column(name = "sh_dept_id")
	public String getShDeptId() {
		return shDeptId;
	}

	public void setShDeptId(String shDeptId) {
		this.shDeptId = shDeptId;
	}

    

    
    
}
