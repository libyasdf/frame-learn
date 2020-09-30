package com.sinosoft.sinoep.common.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * spring mvc 分配器
 * 
 * @author <a href="http://weibo.com/weibowarning">王宁</a><br>
 *         email: <a
 *         href="mailto:childe.wangning@gmail.com">childe.wangning@gmail.com</a>
 * <br>
 * <b>date</b>: 2013-5-20
 */
@SuppressWarnings("serial")
public class SpringDispatcherServlet extends DispatcherServlet {
	private ServletContext servletContext;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletContext = config.getServletContext();
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		createHttpContext(request, response);
		super.service(request, response);
	}

	/**
	 * 为当前线程创建HttpContext对象
	 * 
	 * @see HttpContext
	 * @param request
	 * @param response
	 */
	protected void createHttpContext(HttpServletRequest request,
			HttpServletResponse response) {
		HttpContext httpContext = new HttpContext(request, response);
		HttpContext.setContext(httpContext);
		HttpContext.setServletContext(servletContext);
	}
}
