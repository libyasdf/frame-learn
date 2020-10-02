package com.sinosoft.sinoep.message.alert.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MESSAGE_ALERT")
public class MessageAlert {
	/** 主键id */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", length = 50)
	private String id;
	
	/** 用户id */
	@Column(name = "USER_ID", length = 50)
	private String userId;
	
	/** 提示音路径 */
	@Column(name = "SETTING_PATH", length = 50)
	private String settingPath;
	
	/** 主题皮肤 */
	@Column(name = "THEME_CLASS", length = 50)
	private String themeClass;

	/** 类别 */
	@Column(name = "CLASSIFY", length = 50)
	private String classify;
	
	public String getThemeClass() {
		return themeClass;
	}

	public void setThemeClass(String themeClass) {
		this.themeClass = themeClass;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSettingPath() {
		return settingPath;
	}

	public void setSettingPath(String settingPath) {
		this.settingPath = settingPath;
	}



}
