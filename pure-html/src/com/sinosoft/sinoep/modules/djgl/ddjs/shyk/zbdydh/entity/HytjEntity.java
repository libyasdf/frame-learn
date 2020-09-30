package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.entity;

import java.math.BigDecimal;

/**TODO 三会一课的会议统计实体类
 * @Author: 李帅
 * @Date: 2018/9/7 9:34
 */
public class HytjEntity {
    //序号
    private int id;
    //年度
    private String annual;
    //实际上报数
    private BigDecimal actualReport;
    //组织名称
    private String partyOrganizationName;
    /** 操作 */
    private String cz;
    /** 上级党组织名称 */
    private String superName = "";

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public BigDecimal getActualReport() {
        return actualReport;
    }

    public void setActualReport(BigDecimal actualReport) {
        this.actualReport = actualReport;
    }

    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public HytjEntity(String cz, int id, BigDecimal actualReport, String partyOrganizationName,String annual) {
        this.cz = cz;
        this.id = id;
        this.annual =annual;
        this.actualReport = actualReport;
        this.partyOrganizationName = partyOrganizationName;
    }
    public HytjEntity(){
        super();
    }
}
