/**
 * Copyright 2016 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.uias.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：资源实体</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：资源实体对象</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2016年6月20日
 */
public class SysRecourse {
    private String id;
    private String pid;
    private String name;
    private String url;
    private String style;
    private Integer sort;
    /**
     * 描述
     */
    private String discription;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否在资源树中显示（1显示；0）
     */
    private String isNavigation;
    /**
     * 是否是标签页（1是标签页；0不是）
     */
    private String isTab;
    private String treeId;
    private List<SysRecourse> childrenList = new ArrayList<SysRecourse>();

 
	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SysRecourse(String id, String pid, String name, String url, String style) {
        super();
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.url = url;
        this.style = style;
    }

    public SysRecourse() {
        super();
    }

    public List<SysRecourse> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<SysRecourse> childrenList) {
        this.childrenList = childrenList;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

	public String getIsNavigation() {
		return isNavigation;
	}

	public void setIsNavigation(String isNavigation) {
		this.isNavigation = isNavigation;
	}

	public String getIsTab() {
		return isTab;
	}

	public void setIsTab(String isTab) {
		this.isTab = isTab;
	}

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }
}
