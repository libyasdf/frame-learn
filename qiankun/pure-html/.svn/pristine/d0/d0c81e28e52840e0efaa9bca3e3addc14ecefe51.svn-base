package com.sinosoft.sinoep.sso.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sinosoft.sinoep.common.util.PropOperate;
import com.sinosoft.sinoep.sso.cache.MemoryCache;

import java.io.File;

/**
 * kjx 初始化数据
 */
public class InitFilterData implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        // context销毁时，销毁初始化数据
    }

    @SuppressWarnings("unused")
	@Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        Log log = LogFactory.getLog(InitFilterData.class);
        try {
            String filepath = event.getServletContext().getRealPath("/");
            String path = filepath + "WEB-INF"+ File.separator+"classes"+ File.separator+"configs"+ File.separator+"filterUrl.xml";
            System.out.println("filePath:" + path);
            PropOperate pro = new PropOperate(path);
            MemoryCache.setFilterList(pro.getFilterUrlList());
            MemoryCache.setNoFilterList(pro.getNoFilterUrlList());
            MemoryCache.setAuthList(pro.getAuthUrlList());
            MemoryCache.setNoXssFilter(pro.getNoXssFilterList());
        }
        catch (Exception e) {
            log.error("加载过滤的URL失败:" + e.getMessage());
        }
    }

}