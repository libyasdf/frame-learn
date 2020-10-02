package com.sinosoft.sinoep.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import net.sf.json.JSONObject;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：系统异常统一处理</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2017年11月2日
 */
public class SysExceptionHandler extends SimpleMappingExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (isAjax(request)) {
            result.put("error", true);
            // 有时候我们不希望直接把后台异常直接展示给用户
            result.put("name", ex.getClass().getSimpleName());
            result.put("message", ex.getMessage());
            result.put("details", getErrorDetails(ex));
            if (ex instanceof SysException) {
                result.put("code", ((SysException) ex).getCode());
            }
            else {
                result.put("code", "");
            }
            JSONObject jo = JSONObject.fromObject(result);
            // 此行必加，否则返回的json在浏览器中看到是乱码，不易于识别
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            super.applyStatusCodeIfPossible(request, response, 500);
            try {
                response.getWriter().write(jo.toString());
                response.getWriter().close();
            }
            catch (IOException e) {
            }
            return null;
        }
        // 页面请求，返回异常页面
        result.put("ex", ex);
        // 根据不同错误转向不同页面
        return new ModelAndView("forward:/modules/errorMsg/500.jsp", result);
    }

    /**
     * 判断是否是 Ajax 请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：获取详细的异常详细</B><BR>
     * 
     * @param e
     * @return
     */
    public String getErrorDetails(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

}
