package com.sinosoft.sinoep.modules.system.config.toggle.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Auther: 周俊林
 * @Date: 2018/4/27 10:23
 * @Description:
 */
@Entity
@Table(name = "sys_toggle")
public class SysToggle {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 50)
    private String id;

    /** 开关的名称 */
    @Column(name = "name", length = 200)
    private String name;

    /** 开关标识，作为开关在业务逻辑中的唯一标识，业务逻辑根据此标识可以获取到该开关。 */
    @Column(name = "key", length = 200)
    private String key;

    /** 开关是否处于打开状态，0为关闭，1为打开。添加开关时未指定值，该字段默认为0. */
    @Column(name = "is_open", length = 1)
    private int isOpen;

    /** 对与该开关的描述信息 */
    @Column(name = "describe", length = 200)
    private String describe;

    /** 开关的默认打开状态，0为关闭，1为打开。如果添加数据时没有提供该字段的值，开关的默认状态将为0。用于恢复默认设置 */
    @Column(name = "toggle_default", length = 1)
    private int toggleDefault;

    /** 该开关数据的状态，比如删除，暂停使用，锁定等，默认值为0。 各取值的定义为： 0：正常使用 1：开关被锁定(不可编辑) 2：暂停使用 3：开关被删除 */
    @Column(name = "state", length = 2)
    private String state;

    /** 创建人ID */
    @Column(name = "cre_user_id", length = 50)
    private String creUserId;

    /** 创建时间 */
    @Column(name = "cre_time", length = 30)
    private String creTime;

    /** 更新人ID */
    @Column(name = "update_user_id", length = 50)
    private String updateUserId;

    /** 更新时间 */
    @Column(name = "update_time", length = 30)
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getToggleDefault() {
        return toggleDefault;
    }

    public void setToggleDefault(int toggleDefault) {
        this.toggleDefault = toggleDefault;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    
	public SysToggle() {
		super();
	}

	
	public SysToggle(String id, String name, String key, int isOpen, String describe, int toggleDefault, String state,
			String creUserId, String creTime, String updateUserId, String updateTime) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.isOpen = isOpen;
		this.describe = describe;
		this.toggleDefault = toggleDefault;
		this.state = state;
		this.creUserId = creUserId;
		this.creTime = creTime;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
	}

	public SysToggle(String id, String name, String key, int isOpen, String describe, int toggleDefault, String state) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.isOpen = isOpen;
		this.describe = describe;
		this.toggleDefault = toggleDefault;
		this.state = state;
	}
    
}
