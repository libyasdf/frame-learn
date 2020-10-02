/**
 * Copyright 2016 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.common.util;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.sinoep.common.constant.ConfigConsts;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 张战国
 * @since 2016年11月23日
 */
public class CookieUtil {

    private static final String DOMAIN = ConfigConsts.DOMAIN;

    /**
     * 
     * <B>方法名称：setCookie</B><BR>
     * <B>概要说明：建立会话cookie</B><BR>
     * 
     * @param response
     * @param cookieName cookie名
     * @param cookieValue cookie值
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
            String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        if (StringUtils.isNotBlank(DOMAIN)) {
            cookie.setDomain(DOMAIN);
        }
        response.addCookie(cookie);

    }

    /**
     * 
     * <B>方法名称：setCookie</B><BR>
     * <B>概要说明：建立固定生命周期的cookie</B><BR>
     * 
     * @param response
     * @param cookieName cookie名
     * @param cookieValue cookie值
     * @param expiry 生存时间
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
            String cookieValue, int expiry) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(expiry);
        cookie.setPath("/");
        if (StringUtils.isNotBlank(DOMAIN)) {
            cookie.setDomain(DOMAIN);
        }
        response.addCookie(cookie);

    }

    /**
     * 
     * <B>方法名称：removeCookie</B><BR>
     * <B>概要说明：删除cookie</B><BR>
     * 
     * @param response
     * @param cookieName cookie名
     */
    public static void removeCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        if (StringUtils.isNotBlank(DOMAIN)) {
            cookie.setDomain(DOMAIN);
        }
        /*cookie.setSecure(false);*/
        response.addCookie(cookie);
    }

    /**
     * 
     * <B>方法名称：getCookie</B><BR>
     * <B>概要说明：获取cookie的值</B><BR>
     * 
     * @param request
     * @param cookieName cookie名
     * @return String cookie值
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }

    /**
     * TODO 清除所有cookie
     * @author 李利广
     * @Date 2018年9月11日 上午9:07:21
     * @param request
     * @param response
     */
    public static void clearCookie(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals(ConfigProperties.getPropertyValue("CA_GCODE_NAME"))) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    if (StringUtils.isNotBlank(DOMAIN)) {
                        cookie.setDomain(DOMAIN);
                    }
                    /*cookie.setSecure(false);*/
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * TODO 获取cookie中的个人证书key号
     * @author 李利广
     * @Date 2019年04月28日 15:38:44
     * @param request
     * @return java.lang.String
     */
    public static String getKeyCode(HttpServletRequest request){
        String gCode = "";
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies == null){
                cookies = new Cookie[0];
            }
            for(int i = 0; i < cookies.length; i ++){
                Cookie cookie = cookies[i];
                if(ConfigProperties.getPropertyValue("CA_GCODE_NAME").equals(cookie.getName())) {
                    gCode = URLDecoder.decode(cookie.getValue(), "GBK");
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return gCode;
    }

}
