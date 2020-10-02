package com.sinosoft.sinoep.common.util;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * http工具类
 */
public class HttpUtil extends org.springframework.web.util.WebUtils {
    private static Pattern excludeUrls = Pattern.compile("^.*?\\.(css|js|jpg|jpeg|png|gif|css|eot|svg|ttf|woff|woff2|map)$",
            Pattern.CASE_INSENSITIVE);
	private static Pattern excludehtmlUrl = Pattern.compile("^.*?\\.(html)$", Pattern.CASE_INSENSITIVE);

	public static String getDomain(HttpServletRequest request) {
		return request.getServerName();
	}

	public static String getHttpDomain(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName();
	}

	public static String getContextHttpUri(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	public static String getRealPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	public static String getRequestFullUri(HttpServletRequest request) {
		String port = "";
		if (request.getServerPort() != 80) {
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port + request.getContextPath()
				+ request.getServletPath();
	}

	public static String getRequestFullUriNoContextPath(HttpServletRequest request) {
		String port = "";
		if (request.getServerPort() != 80) {
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port + request.getServletPath();
	}

	public static Boolean isStaticReq(HttpServletRequest request) {
		String path = request.getServletPath();
		Matcher m = excludeUrls.matcher(path);
		return m.matches();
	}

	public static Boolean isHtmlReq(HttpServletRequest request) {
		String path = request.getServletPath();
		Matcher m = excludehtmlUrl.matcher(path);
		return m.matches();
	}

	public static String getRequestURL(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return url;
	}

	// 获取ip地址；
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			if (ip.indexOf("::ffff:") != -1)
				ip = ip.replace("::ffff:", "");
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	// 判断当前请求是否为Ajax
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return !StringUtils.isEmpty(header) && "XMLHttpRequest".equals(header);
	}

	/**
	 * 重定向
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param url
	 */
	public static void redirectUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String url) {
		try {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重定向到http://的url
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param url
	 */
	public static void redirectHttpUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String url) {
		try {
			httpServletResponse.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 准备参数信息，参数间使用 &隔开
	public static String prepareParameter(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		StringBuilder sb = new StringBuilder("");
		for (String key : map.keySet()) {
			if (map.get(key) != null && map.get(key).length > 0) {
				if (map.get(key)[0] != null) {
					sb.append(key + "=" + map.get(key)[0] + "&");// 用&间隔
				}
			}
		}
		String str = sb.toString();
		if (StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0, str.length() - 1);
		}
	}

}
