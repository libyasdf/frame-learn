package com.sinosoft.sinoep.sso.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.sinoep.api.constant.GlobalConstants;
import com.sinosoft.sinoep.api.user.model.UserInfoBind;
import com.sinosoft.sinoep.api.user.service.UserInfoBindService;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.*;
import com.sinosoft.sinoep.modules.system.online.common.OnlineUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.sinosoft.sinoep.sso.cache.MemoryCache;
import com.sinosoft.sinoep.sso.constant.SSOConfigConsts;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * Servlet Filter implementation class SSOAuth
 */
@WebFilter("/SSOAuth")
public class SSOAuth extends HttpServlet implements Filter {

	/**  */
	private static final long serialVersionUID = 1L;

	private String ssoService;

	private String ssoPageUrl;

	private String cookieName;

	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	private Log log = LogFactory.getLog(SSOAuth.class);
	private Logger requestLog = Logger.getLogger("REQLOGGER");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SSOAuth() {
		super();
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		ssoService = filterConfig.getInitParameter("SSOService");
		cookieName = filterConfig.getInitParameter("cookieName");
		ssoPageUrl = filterConfig.getInitParameter("SSOPageUrl");
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getContextPath();
		String gotoURL = request.getParameter("gotoURL");
		String params = request.getQueryString();
		if (gotoURL == null){
			gotoURL = request.getRequestURL().toString();
		}
		if (StringUtils.isBlank(ssoPageUrl)) {
			ssoPageUrl = HttpUtil.getHttpDomain(request) + ":8080/sso/ticket";
		}
		
		String URL = "";
		if (StringUtils.isNotBlank(params) && StringUtils.isNotBlank(gotoURL)) {
			URL = ssoPageUrl + "/preLogin?gotoURL=" + gotoURL + "?" + params;
		}else{
			URL = ssoPageUrl + "/preLogin?gotoURL=" + gotoURL;
		}
		//添加日记消息保存到指定文件中 css,js.png,jpg 等静态资源不记录
		if(!HttpUtil.isStaticReq(request)){
			this.logMessage(request,gotoURL,params);
		}
        //判断cookie中的key是否和session中的key是否一致
        if(!checkKey(request)){
            //退出
            logOut(request,response);
        }
		if (HttpUtil.isStaticReq(request) || noFilter(request)) {
			// 不需要拦截
            if(!response.isCommitted()){
                chain.doFilter(request, response);
            }
			return;
		}
		String ticket = CookieUtil.getCookie(request, cookieName);
		if (request.getRequestURI().equals(path + "/logout")) {
			// 触发退出操作
			doLogout(response, chain, ticket, URL);
		} else if (request.getRequestURI().equals(path + "/setCookie")) {
			//校验gotoURL的合法性
			String host = request.getHeader("Host");
			if(StringUtils.isNotBlank(gotoURL) && gotoURL.indexOf(host) == -1){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("text/javascript");
				response.getWriter().print("Illegal gotoURL! gotoURL:" + gotoURL);
				return;
			}
			// SSO回调的设置cookie请求
			setCookie(request, response, chain);
			return;
		} else if (ticket != null) {
			// 票据不为空时，去验证票据
			authCookie(request, response, ticket);
		}
		// 是否需要登录，如果需要但未登录则拦截
		if (isLoginPass(request)) {
            // 是否需要授权验证
            if (isAuthFlag(request)) {
                // 跳转到后台登录
                if (getUserID(request) == null) {
                    logOut(request,response);
                    return;
                }
                if (authPass(request)) {
                    // 身份验证通过
                    if(!response.isCommitted()){
                        chain.doFilter(request, response);
                    }
                } else {
                    if (HttpUtil.isHtmlReq(request)) {
                        response.sendRedirect(request.getContextPath() + SSOConfigConsts.NO_ACCESS_URL);
                    } else {
                        response.sendRedirect(request.getContextPath() + SSOConfigConsts.NO_ACCESS_AJAX);
                    }
                }
            } else {
                if(!response.isCommitted()){
                    chain.doFilter(request, response);
                }
            }
		} else {
            logOut(request,response);
		}

	}

	/**
	 * 
	 * <B>方法名称：setCookie</B><BR>
	 * <B>概要说明：设置cookie </B><BR>
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void setCookie(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(!checkExpiry(request)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/javascript");
			response.getWriter().print("Illegal expiry! expiry:" + request.getAttribute("expiry"));
			return;
		}else{
			if ("0".equals(request.getAttribute("expiry"))) {
				log.info("收到 sso推送的退出消息");
				CookieUtil.clearCookie(request, response);
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				log.info("删除系统会话成功!");
			} else {
			    String ticket = request.getParameter("ticket");
			    request.setAttribute("ticket",ticket);
				Boolean flag = setTicket(request, response);
				// 票据设置成功
				if (flag) {
                    //登入，并票据设置成功后，记录登录时间
                    String userId = (String)request.getAttribute("userId");
                    String userName = (String)request.getAttribute("userName");
                    OnlineUtils.saveOnlineTime(userId,userName,ticket,System.currentTimeMillis(),"LOGIN");

                    String gotoURL = request.getParameter("gotoURL");
					gotoURL = gotoURL != null ? java.net.URLDecoder.decode(gotoURL, "UTF-8") : gotoURL;
					if (gotoURL != null) {
						response.sendRedirect(gotoURL);
					} else {
                        if(!response.isCommitted()){
                            chain.doFilter(request, response);
                        }
					}
				} else {
					if (HttpUtil.isHtmlReq(request)) {
						response.sendRedirect(request.getContextPath() + SSOConfigConsts.NO_ACCESS_URL);
					} else {
						response.sendRedirect(request.getContextPath() + SSOConfigConsts.NO_ACCESS_AJAX);
					}
				}
			}
		}
	}

	/**
	 * 
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：业务系统设置票据信息</B><BR>
	 * 
	 * @param request
	 * @param response
	 * @return 验证通过并设置成功返回true,验证失败放回false
	 * @throws JSONException
	 * @throws IOException
	 * @throws ServletException
	 */
	private Boolean setTicket(HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException, ServletException {
		Long currTime = Calendar.getInstance().getTimeInMillis();
		String ticket = (String)request.getAttribute("ticket");
		JSONObject userInfo = getUserInfoByTK(ticket);
		Boolean flag = false;
		if (userInfo != null) {
			Integer expiry = Integer.parseInt((String)request.getAttribute("expiry"));
			String userId = userInfo.get("userid").toString();
			String username = userInfo.get("username").toString();
			String appTicket = currTime + "-" + userId;
			appTicket = AesUtil.cbcEncrypt(appTicket);
			request.setAttribute("userId",userId);
			request.setAttribute("userName",username);
			CookieUtil.setCookie(response, SSOConfigConsts.APP_TICKET_TEMP_NAME, appTicket, expiry);
			CookieUtil.setCookie(response, SSOConfigConsts.USER_NAME, URLEncoder.encode(username, "utf-8"), expiry);
			CookieUtil.setCookie(response, cookieName, ticket, expiry);
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：根据票据信息获取用户ID</B><BR>
	 *
	 * @author：yuhao
	 * @cretetime:2018年1月5日 下午5:34:49
	 * @param ticket
	 * @return String
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	private JSONObject getUserInfoByTK(String ticket) throws JSONException, IOException, ServletException {
		JSONObject userInfo = null;
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("action", "authTicket");
		params[1] = new NameValuePair("cookieName", ticket);
		JSONObject result = post(params);
		// 验证票据失败
		if (result.getBoolean("error")) {
			return userInfo;
		}
		userInfo = result.getJSONObject("user");
		return userInfo;
	}

	/**
	 * 
	 * <B>方法名称：authCookie</B><BR>
	 * <B>概要说明：自动登录</B><BR>
	 * 
	 * @param request
	 * @param response
	 * @param ticket 令牌
	 * @throws IOException
	 * @throws ServletException
	 */
	private void authCookie(HttpServletRequest request, HttpServletResponse response, String ticket) throws IOException, ServletException {
		String appTicket = CookieUtil.getCookie(request, SSOConfigConsts.APP_TICKET_TEMP_NAME);
		request.setAttribute("ticket",ticket);
		boolean check = false;
		try {
			if (StringUtils.isNotBlank(appTicket)) {
				appTicket = AesUtil.cbcDecrypt(appTicket);
				String[] arr = appTicket.split("-");
				// 判断解析出的临时票据是否有效
				if (arr.length == 2) {
					Long currTime = Calendar.getInstance().getTimeInMillis();
					Long diffTime = currTime - Long.valueOf(arr[0]);
					double time = diffTime;
					check = time < SSOConfigConsts.APP_TICKET_CHECK_TIME;
				}
			}
            if(!checkExpiry(request)){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("text/javascript");
                response.getWriter().print("Illegal expiry! expiry:" + request.getAttribute("expiry"));
                return;
            }
            setTicket(request,response);
		} catch (Exception e) {
			log.error("验证票据失败", e);
			throw new RuntimeException(e);
		}
		if (check) {
			// 未超时,验证票据，如果票据失效，则退出
			NameValuePair[] params = new NameValuePair[2];
			params[0] = new NameValuePair("action", "authTicket");
			params[1] = new NameValuePair("cookieName", ticket);
			JSONObject result = post(params);
			if (result.getBoolean("error")) {
				// 验证票据失败
				log.info("票据验证失败，ticket:"+ticket);
				log.info("errorInfo:"+result.getString("errorInfo"));
				CookieUtil.removeCookie(response, cookieName);
				CookieUtil.removeCookie(response, SSOConfigConsts.APP_TICKET_TEMP_NAME);
				logOut(request, response);
			} else {
				request.setAttribute("userid", result.getJSONObject("user").get("userid"));
				//判断ticket中的userid和根据证书查询的userid是否一致
                if(checkUserId(request)){
                    setSession(request);
                }else{
                    //退出
                    logOut(request,response);
                }
			}
		} else {
			//已超时，直接退出
			logOut(request,response);
		}

	}

	/**
	 * TODO 票据验证成功后向session中保存用户信息
	 * @author 周俊林
	 * @Date 2018年3月12日 下午7:13:27
	 * @param request
	 */
	private void setSession(HttpServletRequest request) {
        UserUtil.setSession(request,"");
	}

	/**
	 * 
	 * <B>方法名称：doLogout</B><BR>
	 * <B>概要说明：退出登录</B><BR>
	 * 
	 * @param response
	 * @param chain
	 * @param ticket 令牌
	 * @param URL 退出登录后重定向的URL
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doLogout( HttpServletResponse response, FilterChain chain, String ticket,
			String URL) throws IOException, ServletException {

		NameValuePair[] params = new NameValuePair[2];
		// 访问统一登录的参数
		params[0] = new NameValuePair("action", "doLogout");
		// 令牌参数
		params[1] = new NameValuePair("cookieName", ticket);
		try {
			post(params);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		} finally {
			response.sendRedirect(URL);
		}
	}

	/**
	 * TODO 退出处理，如果是ajax操作，指定重定向
	 * @author 李利广
	 * @Date 2019年05月13日 20:48:46
	 * @param request
	 * @param response
	 * @return void
	 */
	private void logOut(HttpServletRequest request,HttpServletResponse response){
	    try{
	        //使session失效
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            //清除cookie
            CookieUtil.clearCookie(request, response);
            //重定向
            String scheme = request.getScheme();
            int serverPort = request.getServerPort();
            String serverName = request.getServerName();
            String gotoURL = scheme + "://" + serverName + ":" + serverPort;
            String URL = ssoPageUrl + "/logout?gotoURL=" + gotoURL;
            sendRedirect(request,response,URL);
        }catch (Exception e){
	        e.printStackTrace();
	        log.error(e.getMessage(),e);
        }
    }

    /**
     * TODO ajax重定向
     * @author 李利广
     * @Date 2019年05月20日 10:09:46
     * @param request
     * @param response
     * @param gotoUrl
     * @return void
     */
    private void sendRedirect(HttpServletRequest request,HttpServletResponse response,String gotoUrl){
        try{
            if(HttpUtil.isAjaxRequest(request)){
                response.setHeader("REDIRECT","REDIRECT");
                response.setHeader("gotoURL",gotoUrl);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }else{
                response.sendRedirect(gotoUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    }

	/**
	 * 
	 * <B>方法名称：post</B><BR>
	 * <B>概要说明：发送post请求到统一登录系统</B><BR>
	 * 
	 * @param params 同一登录需要的参数
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws JSONException
	 */
	@SuppressWarnings("static-access")
	private JSONObject post(NameValuePair[] params) throws IOException, ServletException, JSONException {
		if (ssoService.indexOf("https") != -1) {
			String requestUrl = ssoService + "/" + params[0].getValue() + "?" + params[1].getName() + "="
					+ params[1].getValue();
			JSONObject json = HttpRequestUtil.httpsRequest(requestUrl,HttpRequestUtil.POST);
			return json;
		} else {// http请求
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(ssoService + "/" + params[0].getValue());
			postMethod.addParameters(params);
			switch (httpClient.executeMethod(postMethod)) {
			case HttpStatus.SC_OK:
				return JSONObject.fromObject(postMethod.getResponseBodyAsString());
			default:
				// 其它处理
				return null;
			}
		}
	}

	/**
	 * 不需要拦截
	 * 
	 * @return boolean
	 */
	private boolean noFilter(HttpServletRequest request) {
		if (HttpUtil.isStaticReq(request)) {
			return true;
		}
		String path = request.getServletPath();
		List<String> noFilter = MemoryCache.getNoFilterList();
		return noFilter.contains(path);
	}

	/**
	 * 该方法用于判断是否可以不登录访问
	 * 
	 * @return boolean
	 */
	private boolean isLoginPass(HttpServletRequest request) {
		String path = request.getServletPath();
		Boolean loginFlag = false;
		List<String> filter = MemoryCache.getFilterList();
		for (String str : filter) {
			if (path.indexOf(str) > -1) {
				loginFlag = true;
				break;
			}
		}
		// 需要但未登录
		if (loginFlag && getUserID(request) == null) {
			return false;
		}
		return true;

	}

	/**
	 * 判断是否有需要验证身份
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private Boolean isAuthFlag(HttpServletRequest request) throws IOException {
		String path = request.getServletPath();
		// 需要拦截的进行验证
		List<String> auth = MemoryCache.getAuthList();
		// 是否需要验证
		Boolean flag = false;
		for (String str : auth) {
			if (path.indexOf(str) > -1) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判断是否有权限操作
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private boolean authPass(HttpServletRequest request) throws IOException {
		String path = request.getServletPath();
		// 获取请求参数
		Map<String, String[]> map = request.getParameterMap();
		boolean flag = false;
		String resId = "";
		for (String key : map.keySet()) {
			if (SSOConfigConsts.RES_ID.equals(key)) {
				// 存在resId参数，当前请求的是一个资源元素
				flag = true;
				// 获取资源id
				resId = map.get(key).length > 0 ? map.get(key)[0] : "";
				break;
			}
		}
		if (!flag) {
			StringBuffer params = new StringBuffer();
			for (String key : map.keySet()) {
				String[] value = map.get(key);
				String val = StringUtils.join(value,",");
				if(StringUtils.isNotBlank(val)){
					params.append(key).append("=").append(val).append("&");
				}
			}
			if(params.length() > 0){
				path += "?" + params.substring(0,params.length() - 1);
			}
			return RecourseUtils.hasPrivilegeByUrl(request,path);
		} else {
			return RecourseUtils.hasPrivilegeByContent(request,path,resId);
		}
	}

	/**
	 * 获取当前用户的信息
	 * @param request
	 * @return
	 */
	private String getUserID(HttpServletRequest request) {
	    Object userId = null;
		Object useridObj = request.getAttribute("userid");
		HttpSession session = request.getSession(false);
		if(session != null){
		    userId = session.getAttribute(UserConfigConsts.USER_ID);
        }
        if(session == null){
		    return null;
        }else if(userId == null){
		    return null;
        }/*else if(useridObj == null){
		    return null;
        }*/else{
		    return userId.toString();
        }
	}

	/**
	 * TODO
	 * @author 李利广
	 * @Date 2019年04月19日 19:58:32
	 * @param request
	 * @return java.lang.Boolean
	 */
	private Boolean checkExpiry(HttpServletRequest request){
		//对参数expiry进行校验
		Integer expiry = null;
		String expiryStr = request.getParameter("expiry");
		try {
			if(StringUtils.isNotBlank(expiryStr)){
				expiry = Integer.parseInt(expiryStr);
			}
			request.setAttribute("expiry",expiry==null?SSOConfigConsts.APP_TICKET_CHECK_TIME.toString():expiry.toString());
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * TODO 输出日志到sso日志文件
	 * @author 李利广
	 * @Date 2019年03月19日 20:04:25
	 * @param request
	 * @param gotoURL
	 * @param params
	 * @return void
	 */
	private void logMessage(HttpServletRequest request,String gotoURL,String params){
		String resourceId = "";
		if( params != null && params.contains("resId=")){
			String[] paramsArry = params.split("&");
			for (String s :paramsArry){
				if(s.contains("resId")){
					resourceId = s.substring(6,s.length());
				}
			}
		}
		//是否需要拦截
		boolean filter = !this.noFilter(request);
		HttpSession session = request.getSession();
		String userId = "";
		if(session.getAttribute("USER_ID") != null && StringUtils.isNotBlank((String)session.getAttribute("USER_ID"))){
			userId = (String)session.getAttribute("USER_ID");
		}
		String ip = CommonUtils.getClientIp(request);
		requestLog.info("日记记录:url ="+gotoURL+" | resourceId="+resourceId+" | filter result="+filter+" | data="+params+" | userId="+userId+" | ip="+ip);
		log.info("日记记录:url ="+gotoURL+" | resourceId="+resourceId+" | filter result="+filter+" | data="+params+" | userId="+userId+" | ip="+ip);
	}

	/**
	 * TODO 检测cookie中的证书是否与session中的一致
	 * @author 李利广
	 * @Date 2019年04月28日 16:13:38
	 * @param request
	 * @return java.lang.Boolean
	 */
	private Boolean checkKey(HttpServletRequest request){
	    log.info("验证cookie与session中key是否一致====");
	    boolean isVail = false;
        //判断证书登录开关状态，如果为true，则判断key是否一致
        log.info("证书登录开关状态，VALIDATE_CA_FLAG===" + ConfigConsts.VALIDATE_CA_FLAG);
        if(ConfigConsts.VALIDATE_CA_FLAG){
            //cookie中的证书
            String cookieKey = CookieUtil.getKeyCode(request);
            log.info("cookie中的证书：" + cookieKey);
            //session中的证书
            String sessionKey = (String) request.getSession().getAttribute(UserConfigConsts.COOKIE_KEY_CODE);
            log.info("session中的证书：" + sessionKey);
            //如果cookie中的证书为空,直接退出
            if(StringUtils.isBlank(cookieKey)){
                //cookie中的证书为空时，校验通过
                //暂时性修改，为了在正式环境网关后可以使用密码登录
                isVail = true;
            }else{
                //如果cookie中有key证书，
                if(StringUtils.isNotBlank(sessionKey)){
                    //如果cookie中证书不为空，校验是否与session一致
                    if(cookieKey.equals(sessionKey)){
                        isVail = true;
                    }else{
                        isVail = false;
                    }
                }else{
                    //如果sessionKey为空，表示是第一次登录，验证通过
                    isVail = true;
                }
            }
        }else{
            isVail = true;
        }
        log.info("证书验证：" + (isVail?"通过":"未通过"));
		return isVail;
	}

	/**
	 * TODO 校验ticket验证获取的userid和证书查询的userid是否一致
	 * @author 李利广
	 * @Date 2019年05月13日 20:38:20
	 * @param request
	 * @return java.lang.Boolean
	 */
	private Boolean checkUserId(HttpServletRequest request){
	    boolean isSame = false;
	    try{
            //判断证书登录开关状态，如果为true，则判断key是否一致
            log.info("证书登录开关状态，VALIDATE_CA_FLAG===" + ConfigConsts.VALIDATE_CA_FLAG);
            log.info("证书校验开关状态，IS_CA_LOGIN===" + ConfigConsts.IS_CA_LOGIN);
            /**
             * 只有两个开关都打开true，才需要校验userId
             */
            if(ConfigConsts.VALIDATE_CA_FLAG && ConfigConsts.IS_CA_LOGIN){
                //ticket中的userid
                String userId = (String) request.getAttribute("userid");
                log.info("ticket所属用户userid===" + userId);
                //根据CA证书查询用户ID
                String caKeyCode = CookieUtil.getKeyCode(request);
                log.info("证书号：" + caKeyCode);
                UserInfoBindService userInfoBindService = (UserInfoBindService) SpringBeanUtils.getBean("userInfoBindService");
                UserInfoBind userInfoBind = userInfoBindService.getUserInfoBindByContent(GlobalConstants.BD_TYPE_CA, caKeyCode);
                String keyUserId = "";
                if(userInfoBind != null){
                    keyUserId = userInfoBind.getUser_id();
                }
                log.info("证书绑定的用户userid====" + keyUserId);
                if(keyUserId.equals(userId)){
                    isSame = true;
                }
            }else{
                isSame = true;
            }
        }catch (Exception e){
	        e.printStackTrace();
	        log.error(e.getMessage(),e);
        }
        log.info("用户userId验证：" + (isSame?"通过":"未通过"));
	    return isSame;
    }
	
}
