/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.common.constant;

import com.sinosoft.sinoep.common.util.ConfigProperties;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：常用模块</B><BR>
 * <B>中文类名：系统配置相关常量</B><BR>
 * <B>概要说明：系统配置相关常量放在此类下</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2018年1月5日
 */
public class ConfigConsts {

    /**
     * 系统ID
     */
    public static final String SYSTEM_ID = ConfigProperties.getPropertyValue("SYSTEM_ID");

    /**
     * 组织体系ID
     */
    public static final String ORG_ID = ConfigProperties.getPropertyValue("ORG_ID");
    /**
     * 1-dubbo服务 2-rest服务
     */
    public static final String SERVICE_TYPE = ConfigProperties.getPropertyValue("SERVICE_TYPE");
    /**
     * 1-dubbo服务
     */
    public static final String DUBBO_TYPE = "1";
    /**
     * 2-rest服务
     */
    public static final String REST_TYPE = "2";

    /**
     * cookie 域名
     */
    public static final String DOMAIN = ConfigProperties.getPropertyValue("DOMAIN");

    /**
     * 每页条数
     */
    public static final Integer PAGE_SIZE = ConfigProperties.getIntPropertyValue("PAGE_SIZE", 10);
    /**
     * 每页默认条数
     */
    public static final String PAGE_DEFAULT_SIZE = "10";

    /**
     * 单点登录放回的票据Cookie key值
     */
    public static final String COOKIENAME = ConfigProperties.getPropertyValue("COOKIENAME");
    /**
     * 数据操作成功的状态
     */
    public static final String SUCESS_STARUS = "1";

    /**
     * 数据操作失败的状态
     */
    public static final String ERROR_STATUS = "0";

    /* =====================工作流start=================== */
    /**
     * 流程状态
     */
    //办结
    public static final String END_FLAG = ConfigProperties.getPropertyValue("END_FLAG");
    //流转中
    public static final String SUB_FLAG = ConfigProperties.getPropertyValue("SUB_FLAG");
    //拟稿
    public static final String START_FLAG = ConfigProperties.getPropertyValue("START_FLAG");
    //归档
    public static final String DOSSIER_FLAG = ConfigProperties.getPropertyValue("DOSSIER_FLAG");
    //审批通过
    public static final String APPROVAL_FLAG = ConfigProperties.getPropertyValue("APPROVAL_FLAG");
    //审批不通过
    public static final String NO_APPROVAL_FLAG = ConfigProperties.getPropertyValue("NOAPPROVAL_FLAG");
    //撤办
    public static final String REMOVE_FLAG = ConfigProperties.getPropertyValue("REMOVE_FLAG");

    //发布 2
    public static final String PUBLISH_FLAG = ConfigProperties.getPropertyValue("PUBLISH_FLAG");

    // 国资补录硬盘的状态 9
    public static final String HD_FLAG = ConfigProperties.getPropertyValue("HD_FLAG");
    
    //撤办按钮
    public static final String REMOVE_BTN = ConfigProperties.getPropertyValue("REMOVE_BTN");
    
    //重新启用按钮
    public static final String RECOVERY_BTN = ConfigProperties.getPropertyValue("RECOVERY_BTN");
    
    //收回重办按钮
    public static final String TASK_BACK_BTN = ConfigProperties.getPropertyValue("TASK_BACK_BTN");
    
    //增加会签按钮
    public static final String INCREASE_SIGN_BTN = ConfigProperties.getPropertyValue("INCREASE_SIGN_BTN");
    
    //删除会签按钮
    public static final String DELETE_SIGN_BTN = ConfigProperties.getPropertyValue("DELETE_SIGN_BTN");

    /* =====================工作流end===================== */

    /* =====================统一授权start=================== */

    /**
     * 获取统一授权REST接口根路径
     */
    public static final String UIAS_SERVICE_ROOT_URL = ConfigProperties.getPropertyValue("UIAS_SERVICE_ROOT_URL");

    /* =====================统一授权end===================== */

    /* =====================统一用户start=================== */
    /**
     * 获取统一用户REST接口根路径
     */
    public static final String USER_SERVICE_ROOT_URL = ConfigProperties.getPropertyValue("USER_SERVICE_ROOT_URL");

    /* =====================统一用户end===================== */

    /* =====================统一消息start=================== */
    /**
     * 获取统一消息接口根路径
     */
    public static final String MESSAGE_SERVICE_ROOT_URL = ConfigProperties.getPropertyValue("MESSAGE_SERVICE_ROOT_URL");

    /* =====================统一消息end===================== */
    
    /**
     * 集成邮箱系统REST接口路径
     */
    public static final String MAIL_SERVICE_PATH = ConfigProperties.getPropertyValue("MAIL_SERVICE_PATH");
    
    /**
     * 国资设备数据同步HTTPUTL
     */
    public static final String COUNTRY_DEVICE_URL = ConfigProperties.getPropertyValue("COUNTRY_DEVICE_URL");
    /**
     * weesocket 消息刷新时间
     */
    public static final String WEBSOCKET_TIMER = ConfigProperties.getPropertyValue("WEBSOCKET_TIMER");

    /**
     * CA登录开关
     */
    public static final Boolean VALIDATE_CA_FLAG = ConfigProperties.getBooleanPropertyValue("VALIDATE_CA_FLAG");

    /**
     * 是否是CA证书登录，如果是true,则需要证书校验，如果是false，则不再校验
     */
    public static final Boolean IS_CA_LOGIN = ConfigProperties.getBooleanPropertyValue("IS_CA_LOGIN");

    /**
     * SSO地址
     */
    public static final String SSO_SERVICE_ROOT_URL = ConfigProperties.getPropertyValue("SSO_SERVICE_ROOT_URL");

    /**
     * WORD转换服务地址
     */
    public static final String TRANSFER_DEVICE_URL = ConfigProperties.getPropertyValue("TRANSFER_DEVICE_URL");
   
}
