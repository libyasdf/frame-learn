package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity;

/**
 * @Author: 李帅
 * @Date: 2018/9/6 21:14
 */
public class NdjhUnreportedEntity {
    //编号
    private int id  ;
    //年度
    private String annual;
    //组织名称
    private String partyOrganizationName;
    //组织Id
    private String partyOrganizationId;
    /** 操作 */
    private String cz ="";

    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
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


    public NdjhUnreportedEntity(int id, String cz, String annual, String partyOrganizationName, String partyOrganizationId) {
        this.id = id;
        this.cz = cz;
        this.annual = annual;
        this.partyOrganizationName = partyOrganizationName;
        this.partyOrganizationId = partyOrganizationId;
    }

    public NdjhUnreportedEntity(){
        super();
    }
}
