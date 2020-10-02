package com.sinosoft.sinoep.modules.video.background.entity;

public class VideoColumnTreeEntity {
    private String id;
    private String pId;
    private String name;
    private String orderNo;
    private String nodeLevel;
    private String code;
    private String isFirst;

    public VideoColumnTreeEntity() {
    }

    public VideoColumnTreeEntity(String id, String pId, String name, String orderNo, String nodeLevel) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.orderNo = orderNo;
        this.nodeLevel = nodeLevel;
    }
    
    
    public String getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
