package com.sinosoft.sinoep.modules.system.online.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * TODO 在线人数统计表实体
 * @author 李利广
 * @Date 2019年06月19日 10:46:13
 */
@Entity
@Table(name="SYS_USER_COUNT")
public class SysUserCount {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "CRE_TIME")
    private String creTime;

    @Column(name = "COUNT")
    private String count;

    @Column(name = "ONLINE_USER_INFO")
    private String userInfo;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
