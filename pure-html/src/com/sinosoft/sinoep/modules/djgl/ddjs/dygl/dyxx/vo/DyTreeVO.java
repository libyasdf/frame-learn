package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.vo;


public class DyTreeVO {

	private String id; //组织ID
	private String name;
	private String pId;
	private String isParent;
	private String open;
	private String icon;
    public String type = "";//党员、党组织
	private String zid; //组织主键

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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}

    public String getType() {return type; }

    public void setType(String type) {this.type = type; }
}
