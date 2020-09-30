/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.uias.constant;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：统一授权相关常量</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 yuhao
 * @since 2018年1月7日
 */
public class UiasConfigConsts {
    /**
     * 状态字段标识
     */
    public static final String STATUS = "status";

    /**
     * 资源根目录字段标识
     */
    public static final String RECOURSES_ROOT = "resourceInfo";
    /**
     * 资源根目录字段标识
     */
    public static final String ROLES_ROOT = "rolesInfo";

    /**
     * 部门人员树字段标识
     */
    public static final String DEPT_USER_TREE = "deptUserTree";

    /**
     * 返回结果标识位（0无数据；1正常；-1异常）
     */
    public static final String[] FLAG_STATUS = {"0","1","-1"};

    /**
     * 是否有权限（0：无权限；1有权限；-1无资源）
     */
    public static final String[] HAS_AUTH = {"0","1","-1"};

}
