/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.notion.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.modules.notion.model.CommonNotion;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 goulijun
 * @since 2017年5月23日
 */
public interface CommonNotionService {
    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：goulijun
     * @cretetime:2017年5月23日 下午3:48:42
     * @param request
     * @return List<CommonNotion>
     */
    public List<CommonNotion> getList(HttpServletRequest request);

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：goulijun
     * @cretetime:2017年5月23日 下午3:58:34
     * @param request
     * @param notion
     * @return Map<String,Object>
     */
    public Map<String, Object> add(HttpServletRequest request, String notion);

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：goulijun
     * @cretetime:2017年5月23日 下午4:16:26
     * @param id
     * @return Map<String,Object>
     */
    public Map<String, Object> delete(String id);
}
