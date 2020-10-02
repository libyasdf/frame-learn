package com.sinosoft.sinoep.common.auditlog.util;

/**
 * TODO sql操作类别
 * @author 李利广
 * @Date 2019年04月12日 10:58:44
 */
public enum OperType {

    /**
     * 新增操作
     */
    CREATE("save"),

    /**
     * 修改操作
     */
    UPDATE("update"),

    /**
     * 删除操作
     */
    DELETE("delete");

    private String operType;

    OperType(String operType){
        this.operType = operType;
    }

    public String getOperType(){
        return this.operType;
    }

}
