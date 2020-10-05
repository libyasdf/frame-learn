/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.sso.constant;

import com.sinosoft.sinoep.common.util.ConfigProperties;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：单点登录相关常量</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 yuhao
 * @since 2018年1月8日
 */
public class SSOConfigConsts {
    /**
     * 页面调用没有权限请求
     */
    public static final String NO_ACCESS_URL = ConfigProperties.getPropertyValue("NO_ACCESS_URL");
    /**
     * Ajax调用没有权限请求
     */
    public static final String NO_ACCESS_AJAX = ConfigProperties.getPropertyValue("NO_ACCESS_AJAX");
    /**
     * 业务系统票据名称
     */
    public static final String APP_TICKET_TEMP_NAME = ConfigProperties.getPropertyValue("APP_TICKET_TEMP_NAME");
    /**
     * 票据验证过期时间
     */
    public static final Integer APP_TICKET_CHECK_TIME = ConfigProperties.getIntPropertyValue("APP_TICKET_CHECK_TIME",
            1800000);
    /**
     * 用户名
     */
    public static final String USER_NAME = "USER_NAME";

    /**
     * 资源id参数名
     */
    public static final String RES_ID = "resId";

}
