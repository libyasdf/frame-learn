package com.sinosoft.sinoep.common.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * The HttpContext is the context in which an Spring MVC Controller is executed.
 * Each context is basically a container of objects an controller needs for
 * execution like the session, request, response, etc.
 * <p>
 * <p/>
 * The HttpContext is thread local which means that values stored in the
 * HttpContext are unique per thread. See the {@link ThreadLocal} class for more
 * information. The benefit of this is you don't need to worry about a user
 * specific http context, you just get it:
 * <p/>
 * <ul>
 * <code>HttpContext context = HttpContext.getContext();</code>
 * </ul>
 * <p/>
 * Finally, because of the thread local usage you don't need to worry about
 * making your controllers thread safe.
 * 
 * @author <a href="http://weibo.com/weibowarning">王宁</a><br>
 * <b>date</b>: 2013-6-21
 */
public class HttpContext {
	protected static final Log log = LogFactory.getLog(HttpContext.class);
	private static final ThreadLocal<HttpContext> httpContext = new ThreadLocal<HttpContext>();

	/**
	 * Constant for the HTTP request object.
	 */
	public static final String HTTP_REQUEST = "com.sinosoft.web.servlet.ServletContext.HttpServletRequest";

	/**
	 * Constant for the HTTP response object.
	 */
	public static final String HTTP_RESPONSE = "com.sinosoft.web.servlet.ServletContext.HttpServletResponse";

	/**
	 * Constant for the HTTP session object.
	 */
	public static final String HTTP_SESSION = "com.sinosoft.web.servlet.ServletContext.HttpSession";
	/**
	 * Constant for the {@link javax.servlet.ServletContext servlet context}
	 * object.
	 */
	public static final String SERVLET_CONTEXT = "com.sinosoft.web.servlet.ServletContext.ServletContext";
	private Map<String, Object> context;

	/**
	 * Instantiates a new http context.
	 *
	 * @param context the context
	 */
	public HttpContext(Map<String, Object> context) {
		Assert.notNull(context);
		this.context = context;
	}

	/**
	 * Instantiates a new http context.
	 *
	 * @param request the request
	 * @param response the response
	 */
	public HttpContext(HttpServletRequest request, HttpServletResponse response) {
		this.context = new HashMap<String, Object>();
		this.context.put(HTTP_REQUEST, request);
		this.context.put(HTTP_RESPONSE, response);
	}

	/**
	 * Stores a value in the current ServletContext. The value can be looked up
	 * using the key.
	 * 
	 * @param key
	 *            the key of the value.
	 * @param value
	 *            the value to be stored.
	 */
	public void put(String key, Object value) {
		context.put(key, value);
	}

	/**
	 * Returns a value that is stored in the current ServletContext by doing a
	 * lookup using the value's key.
	 * 
	 * @param key
	 *            the key used to find the value.
	 * @return the value that was found using the key or <tt>null</tt> if the
	 *         key was not found.
	 */
	public Object get(String key) {
		return context.get(key);
	}

	@Override
	public String toString() {
		return "HttpContext [context=" + context + "]";
	}

	/**
	 * Returns the ServletContext specific to the current thread.
	 * 
	 * @return the ServletContext for the current thread, is never <tt>null</tt>
	 *         .
	 */
	public static HttpContext getContext() {
		return (HttpContext) httpContext.get();
	}

	/**
	 * Sets the action context for the current thread.
	 * 
	 * @param context
	 *            the action context.
	 */
	public static void setContext(HttpContext context) {
		if (log.isDebugEnabled()) {
			log.debug("为线程[" + Thread.currentThread() + "]绑定" + context);
		}
		httpContext.set(context);
	}

	/**
	 * Sets the HTTP servlet request object.
	 * 
	 * @param request
	 *            the HTTP servlet request object.
	 */
	public static void setRequest(HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("为线程[" + Thread.currentThread() + "]绑定" + request);
		}
		HttpContext.getContext().put(HTTP_REQUEST, request);
	}

	/**
	 * Gets the HTTP servlet request object.
	 * 
	 * @return the HTTP servlet request object.
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) HttpContext.getContext().get(HTTP_REQUEST);
	}

	/**
	 * Sets the HTTP servlet response object.
	 * 
	 * @param response
	 *            the HTTP servlet response object.
	 */
	public static void setResponse(HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("为线程[" + Thread.currentThread() + "]绑定" + response);
		}
		HttpContext.getContext().put(HTTP_RESPONSE, response);
	}

	/**
	 * Gets the HTTP servlet response object.
	 * 
	 * @return the HTTP servlet response object.
	 */
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) HttpContext.getContext()
				.get(HTTP_RESPONSE);
	}

	/**
	 * Gets the servlet context.
	 * 
	 * @return the servlet context.
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) HttpContext.getContext().get(SERVLET_CONTEXT);
	}

	/**
	 * Sets the current servlet context object
	 * 
	 * @param servletContext
	 *            The servlet context to use
	 */
	public static void setServletContext(ServletContext servletContext) {
		if (log.isDebugEnabled()) {
			log.debug("为线程[" + Thread.currentThread() + "]绑定" + servletContext);
		}
		HttpContext.getContext().put(SERVLET_CONTEXT, servletContext);
	}

	/**
	 * Gets the servlet session.
	 * 
	 * @return the servlet session.return <b>null</b> if there is no current
	 *         session
	 * @see javax.servlet.http.HttpServletRequest#getSession(boolean create)
	 */
	public static HttpSession getSession() {
		HttpSession session = (HttpSession) HttpContext.getContext().get(
				HTTP_SESSION);
		if (session == null) {
			session = getRequest().getSession(false);
			if (session != null)
				setSession(session);
		}
		return session;
	}

	/**
	 * Sets the current servlet context object
	 * 
	 * @param servletContext
	 *            The servlet context to use
	 */
	public static void setSession(HttpSession session) {
		if (log.isDebugEnabled()) {
			log.debug("为线程[" + Thread.currentThread() + "]绑定" + session);
		}
		HttpContext.getContext().put(HTTP_SESSION, session);
	}
}
