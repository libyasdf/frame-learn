package com.sinosoft.sinoep.modules.system.login.listener;

import java.util.HashSet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.system.online.common.OnlineUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.sinoep.modules.system.login.constants.LoginConstants;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;

/**
 * TODO session监听器，统计当前在线用户数
 * @author 李利广
 * @Date 2018年8月7日 下午2:27:13
 */
public class SessionListener implements HttpSessionListener {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		// 在application范围由一个HashSet集保存所有的session
		HashSet<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute(LoginConstants.SESSION_LIST_NAME);
		if (sessions == null) {
			sessions = new HashSet<>();
			application.setAttribute(LoginConstants.SESSION_LIST_NAME, sessions);
		}
		// 新创建的session均添加到HashSet集中
		sessions.add(session);
		// 可以在别处从application范围中取出sessions集合
		// 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
		HashSet<HttpSession> sessions = new HashSet<>();
		String userId = "";
        String userName = "";
		try{
            ServletContext application = session.getServletContext();
            sessions = (HashSet<HttpSession>) application.getAttribute(LoginConstants.SESSION_LIST_NAME);
            String loginType = (String)session.getAttribute(UserConfigConsts.LOGIN_TYPE);
            userId = (String)session.getAttribute(UserConfigConsts.USER_ID);
            userName = (String)session.getAttribute(UserConfigConsts.USER_NAME);
            log.info("loginType===" + loginType);
            if(CommonConstants.LOGIN_TYPE[0].equals(loginType)){
                //系统登录，非消息客户端登录
                String ticket = (String)session.getAttribute(UserConfigConsts.TICKET);
                //保存登出记录
                OnlineUtils.saveOnlineTime(userId,userName,ticket,System.currentTimeMillis(),"LOGOUT");
            }
        }catch (Exception e){
		    e.printStackTrace();
		    log.error(e.getMessage(),e);
        }finally {
            // 销毁的session均从HashSet集中移除
            sessions.remove(session);
            log.info("移除session：用户：" + userName + " = " + userId);
        }
	}

}
