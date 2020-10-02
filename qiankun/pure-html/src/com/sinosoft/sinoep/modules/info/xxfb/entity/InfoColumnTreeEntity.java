package com.sinosoft.sinoep.modules.info.xxfb.entity;

public class InfoColumnTreeEntity {
    private String id;
    private String pId;
    private String name;
    private String orderNo;
    private String nodeLevel;

    public InfoColumnTreeEntity() {
    }

    public InfoColumnTreeEntity(String id, String pId, String name, String orderNo, String nodeLevel) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.orderNo = orderNo;
        this.nodeLevel = nodeLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(String nodeLevel) {
        this.nodeLevel = nodeLevel;
    }
}
