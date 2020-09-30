package com.sinosoft.sinoep.sso.filter;

import com.sinosoft.sinoep.common.util.HttpUtil;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.tool.Helper;
import com.sinosoft.sinoep.sso.cache.MemoryCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet Filter implementation class NewXssFilter
 */

public class NewXssFilter extends HttpServlet implements Filter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    FilterConfig filterConfig = null;
    String trustedHost = null;
    String fileType = null;
    String charset= "UTF-8";
    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        request.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        try{
            if (noFilter(req)) {
                chain.doFilter(request, response);
            }
            // HTTP 头设置 Referer过滤
            String referer = ((HttpServletRequest) request).getHeader("Referer");
            String host = ((HttpServletRequest) request).getHeader("Host");
            // 如果referer非本站点，且非可信域名，则返回非法提醒
//            if (referer!=null && referer.indexOf(host)==-1) {
            if (referer!=null && !isTrustedDomain(req,referer)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("text/javascript");
                resp.getWriter().print("拦截器拦截,Referer:" + referer);
                return;
            }
            String method=req.getMethod();
            if (!"GET".equals(method)&&!"POST".equals(method)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("text/javascript");
                resp.getWriter().print("拦截器拦截,非POST或GET方式请求");
                return;
            }
            String requestDispatcherPath= Helper.nvlString(req.getRequestURI()).toLowerCase();
            String[] strFileType=fileType.split(",");
            if(requestDispatcherPath.indexOf(".")!=-1){
                boolean bol=true;
                for(String str:strFileType){
                    if(requestDispatcherPath.indexOf(str)!=-1){//fileType为"",则都不进行拦截
                        bol=false;
                        break;
                    }

                }
                if(bol){
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.setContentType("text/javascript");
                    resp.getWriter().print("拦截器拦截,请求带'.'且是非白名单会被拦截");
                    return;
                }
            }
            String servletPath=req.getServletPath();
            if("/ReportServer".equals(servletPath)){//帆软报表需特殊对待
                String op=Helper.nvlString(req.getParameter("op"));
                if("write".equals(op)){
                    Pattern pattFR=Pattern.compile("[0-9a-zA-Z_\\-\\u4e00-\\u9fa5.%/]+");
                    Pattern pattFRK=Pattern.compile("[0-9a-zA-Z]+");
                    Map<String,String[]> mapFR=req.getParameterMap();
                    for(String key:mapFR.keySet()){
                        String val=mapFR.get(key)[0];
                        Matcher matcherfr=pattFR.matcher(val);
                        Matcher matcherfrK=pattFRK.matcher(key);
                        Boolean bolFR=(StringUtils.isNotBlank(val)&&(!matcherfr.matches()));
                        Boolean bolFRK=(StringUtils.isNotBlank(key)&&(!matcherfrK.matches()));
                        if(bolFR||bolFRK){
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            resp.setContentType("text/javascript");
                            resp.getWriter().print("拦截器,拦截帆软报表--");
                            return;
                        }
                    }

                }
                String p=Helper.nvlString(req.getParameter("p"));
                String dataTime=Helper.nvlString(req.getParameter("dataTime"));
                String dataTime2=Helper.nvlString(req.getParameter("dataTime2"));
                String chushiId=Helper.nvlString(req.getParameter("chushiId"));
                String iddR=Helper.nvlString(req.getParameter("id"));
                Pattern pattstr=Pattern.compile("select[\\w*%)(.><',;= \\-\\u4e00-\\u9fa5]+(\\)|first)");
                Pattern pattnum=Pattern.compile("[0-9\\-]+");
                Pattern pattNZ=Pattern.compile("[0-9A-Za-z_]+");
                Pattern pattID=Pattern.compile("[0-9A-Za-z,'\\-.]+");
                Matcher matcher=pattstr.matcher(p);
                Matcher matcher2=pattnum.matcher(dataTime);
                Matcher matcher3=pattnum.matcher(dataTime2);
                Matcher matcher4=pattNZ.matcher(chushiId);
                Matcher matcher5=pattID.matcher(iddR);
                Matcher matcher6=pattNZ.matcher(op);
                Boolean a=(StringUtils.isNotBlank(p)&&(!matcher.matches()));
                Boolean b=(StringUtils.isNotBlank(dataTime)&&(!matcher2.matches()));
                Boolean c=(StringUtils.isNotBlank(dataTime2)&&(!matcher3.matches()));
                Boolean d=(StringUtils.isNotBlank(chushiId)&&(!matcher4.matches()));
                Boolean e=(StringUtils.isNotBlank(iddR)&&(!matcher5.matches()));
                Boolean f=(StringUtils.isNotBlank(op)&&(!matcher6.matches()));
                if(a||b||c||d||e||f){
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.setContentType("text/javascript");
                    resp.getWriter().print("拦截器,拦截帆软报表");
                    return;
                }
            }
            if(!resp.isCommitted()){
                NewXssHttpServletRequestWrapper xssRequest = new NewXssHttpServletRequestWrapper(req);
                chain.doFilter(xssRequest, resp);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            if(!resp.isCommitted()){
                NewXssHttpServletRequestWrapper xssRequest = new NewXssHttpServletRequestWrapper(req);
                chain.doFilter(xssRequest, resp);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        trustedHost = filterConfig.getInitParameter("trustedHost");
        fileType = Helper.nvlString(filterConfig.getInitParameter("fileType"));
    }

    /**
     *
     * <B>方法名称：</B><BR>
     * <B>概要说明：判断是否为可信域名</B><BR>
     *
     * @param req 请求域名，如果非默认端口则凭借端口信息
     * @param url 请求URL
     * @param url 请求路径
     * @return
     */
    private Boolean isTrustedDomain(HttpServletRequest req, String url) {
        String httpDomain = req.getServerName();
        // 路径为空，默认可信
        if (StringUtils.isBlank(url)) {
            return true;
        }
        String urlHost = getHost(url);
        // 与当前应用HOST一致可信
        if (httpDomain.equals(urlHost)) {
            return true;
        }
        // 未配置可信域默认不限制
        if (StringUtils.isBlank(trustedHost)) {
            return true;
        }
        // 地址为可信域则可跳转
        String[] trustedDomains = trustedHost.split(",");
        for (int i = 0; i < trustedDomains.length; i++) {
            String trusted = trustedDomains[i];
            if (urlHost.equals(trusted)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * <B>方法名称：</B><BR>
     * <B>概要说明：截取HOST</B><BR>
     *
     * @param url
     * @return
     */
    private static String getHost(String url) {
        String host = "";
        host = url.substring(url.indexOf("://") + 3);
        if (host.indexOf("/") < 0) {
            return host;
        }
        host = host.substring(0, host.indexOf("/"));
        if (host.indexOf(":") < 0) {
            return host;
        }
        return host.substring(0, host.indexOf(":"));
    }

    /**
     * 不需要拦截
     * @return boolean
     */
    private boolean noFilter(HttpServletRequest request) {
        if (HttpUtil.isStaticReq(request)) {
            return true;
        }
        String path = request.getServletPath();
        List<String> noXssFilter = MemoryCache.getNoXssFilter();
        return noXssFilter.contains(path);
    }

}
