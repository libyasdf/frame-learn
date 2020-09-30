package com.sinosoft.sinoep.modules.video.background.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;

@Entity
@Table(name = "VIDEO_CONTENT", schema = "TJXM")
public class VideoContent {
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
    private String title;
    private String subtitle;
    private String author;
    private String showStartTime;
    private String showEndTime;
    private String content;
    private String source;
    private String imageId;
    private String videoId;
    private String subflag;
    private String isZd;
    private String columnId;
    private String fbfw;
    private String fbTime;
    private String columnCode;
    private String isFBContent;
    private String imgPath;
    /** 文件在音视频系统中的uuid*/
    private String uuid;
    /** 视频的文件名 */
	private String fileName;
	 /** 播放次数 */
   private String playTimes;
  

    public VideoContent() {
    }
    
    public VideoContent(String id, String creUserId, String creUserName, String creDeptId, String creDeptName,
			String visible, String creTime, String title, String subtitle, String author, String showStartTime,
			String showEndTime, String content, String source, String imageId,String videoId, String subflag, String isZd,
			String columnId, String fbTime,String columnCode) {
		super();
		this.id = id;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.creDeptId = creDeptId;
		this.creDeptName = creDeptName;
		this.visible = visible;
		this.creTime = creTime;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.showStartTime = showStartTime;
		this.showEndTime = showEndTime;
		this.content = content;
		this.source = source;
		this.imageId = imageId;
		this.videoId = videoId;
		this.subflag = subflag;
		this.isZd = isZd;
		this.columnId = columnId;
		this.fbTime = fbTime;
		this.columnCode = columnCode;
	}

    public VideoContent(String id, String creUserId, String creUserName, String creDeptId, String creDeptName, String creChushiId, String creChushiName, String creJuId, String creJuName, String visible, String creTime, String updateUserId, String updateUserName, String updateTime, String title, String subtitle, String author, String showStartTime, String showEndTime, String content, String source, String imageId,String videoId, String subflag, String isZd, String columnId, String fbfw, String fbTime, String columnCode, String isFBContent) {
        this.id = id;
        this.creUserId = creUserId;
        this.creUserName = creUserName;
        this.creDeptId = creDeptId;
        this.creDeptName = creDeptName;
        this.creChushiId = creChushiId;
        this.creChushiName = creChushiName;
        this.creJuId = creJuId;
        this.creJuName = creJuName;
        this.visible = visible;
        this.creTime = creTime;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateTime = updateTime;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.showStartTime = showStartTime;
        this.showEndTime = showEndTime;
        this.content = content;
        this.source = source;
        this.imageId = imageId;
        this.videoId = videoId;
        this.subflag = subflag;
        this.isZd = isZd;
        this.columnId = columnId;
        this.fbfw = fbfw;
        this.fbTime = fbTime;
        this.columnCode = columnCode;
        this.isFBContent = isFBContent;
    }
    
    public VideoContent(String id, String title,String isZd,String fbTime,String imgPath){
    	this.id = id;
    	this.title = title;
    	 this.isZd = isZd;
    	 this.fbTime = fbTime;
    	 this.imgPath = imgPath;
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
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "SUBTITLE")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Basic
    @Column(name = "AUTHOR")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "SHOW_START_TIME")
    public String getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(String showStartTime) {
        this.showStartTime = showStartTime;
    }

    @Basic
    @Column(name = "SHOW_END_TIME")
    public String getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(String showEndTime) {
        this.showEndTime = showEndTime;
    }

    @Basic
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "IMAGE_ID")
    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    @Basic
    @Column(name = "VIDEO_ID")
    public String getVideoId() {
        return videoId;
    }
    
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    

    @Basic
    @Column(name = "SUBFLAG")
    public String getSubflag() {
        return subflag;
    }

    public void setSubflag(String subflag) {
        this.subflag = subflag;
    }

    @Basic
    @Column(name = "IS_ZD")
    public String getIsZd() {
        return isZd;
    }

    public void setIsZd(String isZd) {
        this.isZd = isZd;
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
    @Column(name = "FBFW")
    public String getFbfw() {
        return fbfw;
    }

    public void setFbfw(String fbfw) {
        this.fbfw = fbfw;
    }

    @Basic
    @Column(name = "FB_TIME")
    public String getFbTime() {
        return fbTime;
    }

    public void setFbTime(String fbTime) {
        this.fbTime = fbTime;
    }

    @Basic
    @Column(name = "IS_FB_CONTENT")
    public String getIsFBContent() {
        return isFBContent;
    }

    public void setIsFBContent(String isFBContent) {
        this.isFBContent = isFBContent;
    }

    @Transient
	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	@Transient
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Transient
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Transient
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Transient
	public String getPlayTimes() {
		if(StringUtils.isNotBlank(playTimes)){
			return playTimes;
		}else{
			return "0";
		}
		
	}

	public void setPlayTimes(String playTimes) {
		this.playTimes = playTimes;
	}
	
}
