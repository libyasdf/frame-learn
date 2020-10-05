package com.sinosoft.sinoep.modules.system.online.common;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.system.login.constants.LoginConstants;
import com.sinosoft.sinoep.modules.system.online.entity.OnlineUserInfo;
import com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime;
import com.sinosoft.sinoep.modules.system.online.services.SysOnlineTimeService;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * TODO
 * @author 李利广
 * @Date 2019年06月19日 11:05:19
 */
public class OnlineUtils {

    /**
     * TODO 查询全部在线人数
     * @author 李利广
     * @Date 2019年06月19日 11:05:35
     * @return List<OnlineUserInfo>
     */
    public static List<OnlineUserInfo> getUserSize(ServletContext application){
        return getUserSize(application,"");
    }

    /**
     * TODO 查询消息客户端在线人数
     * @author 李利广
     * @Date 2019年06月19日 11:05:35
     * @return List<OnlineUserInfo>
     */
    public static List<OnlineUserInfo> getMsgClientUserSize(ServletContext application){
        return getUserSize(application,CommonConstants.LOGIN_TYPE[1]);
    }

    /**
     * TODO 查询浏览器在线人数
     * @author 李利广
     * @Date 2019年06月19日 11:05:35
     * @return List<OnlineUserInfo>
     */
    public static List<OnlineUserInfo> getBrowUserSize(ServletContext application){
        return getUserSize(application,CommonConstants.LOGIN_TYPE[0]);
    }

    public static List<OnlineUserInfo> getUserSize(ServletContext application,String loginType){
        List<OnlineUserInfo> users = new ArrayList<>();
        HashSet<HttpSession> sessions = new HashSet<>();
        // 在application范围由一个HashSet集保存所有的session
        HashSet<HttpSession> allSessions = (HashSet<HttpSession>) application.getAttribute(LoginConstants.SESSION_LIST_NAME);
        if(CommonConstants.LOGIN_TYPE[1].equals(loginType)){
            //客户端登录
            for (HttpSession session : allSessions) {
                if(loginType.equals(session.getAttribute(UserConfigConsts.LOGIN_TYPE))){
                    sessions.add(session);
                }
            }
        }else if(CommonConstants.LOGIN_TYPE[0].equals(loginType)){
            //非客户端登录
            for (HttpSession session : allSessions) {
                if(loginType.equals(session.getAttribute(UserConfigConsts.LOGIN_TYPE))){
                    sessions.add(session);
                }
            }
        }else{
            //全部在线人数
            sessions = allSessions;
        }
        //去除session中重复的用户session
        sessions = clearSessions(sessions);
        if (!sessions.isEmpty()) {
            for (HttpSession session : sessions) {
                OnlineUserInfo userInfo = new OnlineUserInfo();
                String userId = (String) session.getAttribute(UserConfigConsts.USER_ID);
                String userName = (String) session.getAttribute(UserConfigConsts.USER_NAME);
                String ticket = (String) session.getAttribute(UserConfigConsts.TICKET);
                /** 对于一人多岗用户，如果在刚登陆时，还没有选择岗位，则此人的部门信息为空，因此这里暂时先不记录部门信息 **/
                /*String deptId = (String) allSession.getAttribute(UserConfigConsts.USER_DEPT_ID);
                String deptName = (String) allSession.getAttribute(UserConfigConsts.USER_DEPT_NAME);
                String chuId = (String) allSession.getAttribute(UserConfigConsts.USER_CHUSHI_ID);
                String chuName = (String) allSession.getAttribute(UserConfigConsts.USER_CHUSHI_NAME);
                String juId = (String) allSession.getAttribute(UserConfigConsts.USER_JU_ID);
                String juName = (String) allSession.getAttribute(UserConfigConsts.USER_JU_NAME);*/
                userInfo.setUserId(userId);
                userInfo.setUserName(userName);
                userInfo.setTicket(ticket);
                /*userInfo.setDeptId(deptId);
                userInfo.setDeptName(deptName);
                userInfo.setChuId(chuId);
                userInfo.setChuName(chuName);
                userInfo.setJuId(juId);
                userInfo.setJuName(juName);*/
                users.add(userInfo);
            }
        }
        return users;
    }

    /**
     * 去除session集合中相同用户的session,及空session
     * TODO
     * @author 李利广
     * @Date 2019年06月19日 11:05:35
     * @param sessions
     * @return
     */
    private static HashSet<HttpSession> clearSessions(HashSet<HttpSession> sessions){
        HashSet<HttpSession> sessionList = new HashSet<>();
        //用户ID集合，用来存放session中获取到的不重复的userid
        List<String> userIds = new ArrayList<>();
        if(sessions != null && sessions.size() > 0){
            for (HttpSession session : sessions) {
                String userId = (String)session.getAttribute(UserConfigConsts.USER_ID);
                if (StringUtils.isNotBlank(userId) && !userIds.contains(userId)) {
                    /**
                     * 如果集合中不存在该userid，将userid放入集合中,并且将该session放入session集合中
                     * 如果集合中已经存在该userid，不做任何处理
                     */
                    userIds.add(userId);
                    sessionList.add(session);
                }
            }
        }
        return sessionList;
    }

    /**
     * TODO 保存登录、登出时间
     * @author 李利广
     * @Date 2019年06月19日 20:01:20
     * @param userId
     * @param userName
     * @param ticket
     * @param time 登录、登出时间
     * @param logType 记录类型（LOGIN/LOGOUT）
     * @return void
     */
    public static void saveOnlineTime(String userId,String userName,String ticket,long time,String logType){
        SysOnlineTime onlineTime = new SysOnlineTime();
        onlineTime.setUserId(userId);
        onlineTime.setUserName(userName);
        onlineTime.setTicket(ticket);
        if("LOGIN".equals(logType)){
            onlineTime.setLoginTime(Convert.toStr(time));
        }else if("LOGOUT".equals(logType)){
            onlineTime.setLogoutTime(Convert.toStr(time));
        }
        SysOnlineTimeService timeService = (SysOnlineTimeService) SpringBeanUtils.getBean("onlineTimeService");
        timeService.saveTime(onlineTime,logType);
    }

    public static void main(String[] args){
        try{
            //毫秒转为日期yyyy-MM-dd HH:mm:ss
            DateTime dateTime = DateUtil.date(1561014635983L);
            System.out.println("timeStr11==" + dateTime.toString());

            String dateStr = DateUtil.formatDateTime(new DateTime(1561014635983L));
            System.out.println("dateStr==" + dateStr);

            //日期转为毫秒
            DateTime time = new DateTime("2019-06-20","yyyy-MM-dd");
            System.out.println("timeMil==" + time.getTime());
            Date date = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").parse("2019-06-20 15:10:35");
            System.out.println("timeMil222==" + date.getTime());

            //毫秒转成分钟数
            String bet1 = DateUtil.formatBetween(208633L);
            System.out.println("between1==" + bet1);
            String bet2 = DateUtil.formatBetween(208633L, BetweenFormater.Level.SECOND);
            System.out.println("between2==" + bet2);
            String bet3 = DateUtil.formatBetween(208633L, BetweenFormater.Level.MINUTE);
            System.out.println("between3==" + bet3);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
