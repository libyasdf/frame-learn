package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.vo;

import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 接收List参数
 * @Date 2018/8/7 11:21
 * @Param
 * @return
 **/
public class dutyTableListVo {

    private List<DutyTable> dutyTables;
    private String isSecurity;
    private String zbNoticeId;
    private String privatePhone;
    private String commonPhone;

    public List<DutyTable> getDutyTables() {
        return dutyTables;
    }

    public void setDutyTables(List<DutyTable> dutyTables) {
        this.dutyTables = dutyTables;
    }

    public String getIsSecurity() {
        return isSecurity;
    }

    public void setIsSecurity(String isSecurity) {
        this.isSecurity = isSecurity;
    }

    public String getZbNoticeId() {
        return zbNoticeId;
    }

    public void setZbNoticeId(String zbNoticeId) {
        this.zbNoticeId = zbNoticeId;
    }

    public String getPrivatePhone() {
        return privatePhone;
    }

    public void setPrivatePhone(String privatePhone) {
        this.privatePhone = privatePhone;
    }

    public String getCommonPhone() {
        return commonPhone;
    }

    public void setCommonPhone(String commonPhone) {
        this.commonPhone = commonPhone;
    }
}
