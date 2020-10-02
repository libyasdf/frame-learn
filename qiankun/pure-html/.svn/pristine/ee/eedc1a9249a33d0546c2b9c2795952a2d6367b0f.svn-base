package com.sinosoft.sinoep.modules.system.config.userdefinesetting.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户自定义设置ENTITY
 */
@Entity
@Table(name = "SYS_USER_DEFINE_SETTING")
public class UserSetting {
    private String id;
    private String userId;
    private String personalPortalAddress;
    private String themeSetting;

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
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "PERSONAL_PORTAL_ADDRESS")
    public String getPersonalPortalAddress() {
        return personalPortalAddress;
    }

    public void setPersonalPortalAddress(String personalPortalAddress) {
        this.personalPortalAddress = personalPortalAddress;
    }

    @Basic
    @Column(name = "THEME_SETTING")
    public String getThemeSetting() {
        return themeSetting;
    }

    public void setThemeSetting(String themeSetting) {
        this.themeSetting = themeSetting;
    }

}
