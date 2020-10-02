/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.workflow.constant;

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
public class WorkFlowConfigConsts {

    /**
     * 工作流服务根URL
     */
    public static final String WORKFLOW_SERVICE_ROOT_URL = ConfigProperties.getPropertyValue(
            "WORKFLOW_SERVICE_ROOT_URL");
    /**
     * 退回意见类型名称
     */
    public static final String BACK_IDEA_FIELDNAME = ConfigProperties.getPropertyValue("BACK_IDEA_FIELDNAME");

}
