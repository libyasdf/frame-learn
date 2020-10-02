package com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity;

/**
 * @Author 王富康
 * @Description //TODO 表与表的关系表的vo
 * @Date 2018/11/17 11:06
 * @Param 
 * @return 
 **/
public class DATreeVo {
    
    
    private String pId;
    private String id;
    private String name;
    private boolean open = true;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
