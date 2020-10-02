/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.notion.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.modules.notion.service.CommonNotionService;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 goulijun
 * @since 2017年5月23日
 */
@Controller
@RequestMapping("/commonNotion")
public class CommonNotionController {

    @Autowired
    CommonNotionService commonNotionService;

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：goulijun
     * @cretetime:2017年5月23日 下午3:53:45
     * @param request
     * @return Map<String,Object>
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        returnmap.put("list", commonNotionService.getList(request));
        return returnmap;
    }

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：goulijun
     * @cretetime:2017年5月23日 下午4:11:17
     * @param request
     * @param content
     * @return Map<String,Object>
     */
    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> add(HttpServletRequest request, String notion) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        returnmap = commonNotionService.add(request, notion);
        return returnmap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(String id, HttpServletRequest request) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        returnmap = commonNotionService.delete(id);

        return returnmap;

    }

}
