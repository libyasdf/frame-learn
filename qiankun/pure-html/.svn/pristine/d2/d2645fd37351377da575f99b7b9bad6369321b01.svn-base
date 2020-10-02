package com.sinosoft.sinoep.modules.system.login.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.jedis.services.RedisClusterService;
import com.sinosoft.sinoep.common.util.CookieUtil;
import com.sinosoft.sinoep.user.constant.UserConfigConsts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.api.constant.GlobalConstants;
import com.sinosoft.sinoep.api.user.model.UserInfoBind;
import com.sinosoft.sinoep.api.user.service.UserInfoBindService;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisPool;

/**
 * TODO 登录控制类
 * @author 李利广
 * @Date 2018年7月3日 下午1:47:44
 */
@Controller
@RequestMapping("/system/login")
public class LoginController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

    private Logger msgLog = LoggerFactory.getLogger("WEBSCOKET");

    @Autowired
    private RedisClusterService redisService;

	/**
	 * TODO 消息客户端证书登录方法
	 * @author 李利广
	 * @Date 2018年7月12日 下午8:42:35
	 * @param request
	 * @return
	 */
    @RequestMapping("login_ca")
    @ResponseBody
	public JSONObject loginCa(HttpServletRequest request){
        msgLog.info("============消息客户端登录============");
		JSONObject json = new JSONObject();
		String gcode = "";
		String ca_status = "2";
		SysFlowUserVo userInfo = new SysFlowUserVo();
	    try{
	    	//获取证书号
			gcode = CookieUtil.getKeyCode(request);
            msgLog.info("========CA_G_CODE:" + gcode + "============");
			gcode = request.getParameter("gcode");
			if(StringUtils.isNotBlank(gcode)){
			    //通过ca码获取用户id
			    UserInfoBindService userInfoBindService = (UserInfoBindService) SpringBeanUtils.getBean("userInfoBindService");
			    UserInfoBind userInfoBind = userInfoBindService.getUserInfoBindByContent(GlobalConstants.BD_TYPE_CA, gcode);
			    if(null!=userInfoBind){
			        //ca已绑定用户
                    //证书校验，判断cookie中的key是否和session中的key是否一致
                    if(checkKey(request)){
                        ca_status="1";
                        //设置session
                        Map<String, SysFlowUserVo> user = UserUtil.getUserVo(userInfoBind.getUser_id());
                        userInfo = user.get(userInfoBind.getUser_id());
                        request.setAttribute("userid", userInfo.getUserId());
                        String deptId = userInfo.getUserDeptId();
                        String[] dept = deptId.split(",");
                        clearSession(request);
                        request.setAttribute("loginType", CommonConstants.LOGIN_TYPE[1]);
                        UserUtil.setSession(request, dept[0]);
                    }else{
                        //证书校验未通过
                        ca_status="3";
                    }
			    }else{
			        //ca未绑定用户
			    	ca_status="2";
			    }
			}else{
				//未插入CA证书
				ca_status="0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			ca_status="-1";
            msgLog.info(e.getMessage(),e);
		}
        json.put("data", userInfo);
        json.put("flag", ca_status);
        msgLog.info("消息客户端登录结束！result:" + json.toString());
	    return json;
	}

	/**
	 * TODO 登录完成之后，先将session失效
	 * @author 李利广
	 * @Date 2019年03月27日 13:36:40
	 * @param request
	 * @return void
	 */
	private void clearSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if (session != null){
			session.invalidate();
		}
	}

	/**
	 * TODO 注销方法
	 * @author 李利广
	 * @Date 2019年05月13日 21:06:26
	 * @param request
	 * @return void
	 */
    @RequestMapping("logout")
    @ResponseBody
	public void doLogOut(HttpServletRequest request,HttpServletResponse response){
	    //清理redis中的ticket
        log.info("退出操作");
        try{
            String ticket = CookieUtil.getCookie(request,ConfigConsts.COOKIENAME);
            log.info("证书ticket===" + ticket);
            Map<String, JedisPool> map = redisService.getJedisCluster().getClusterNodes();
            boolean isExist = redisService.getJedisCluster().exists(ticket);
            log.info("redis中是否存在该ticket：" + isExist);
            if(StringUtils.isNotBlank(ticket) && isExist){
                log.info("清除redis中的ticket");
                redisService.del(ticket);
            }
            log.info("redis中是否存在该ticket：" + redisService.getJedisCluster().exists(ticket));
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }finally {
            //session失效
            log.info("使session失效");
            clearSession(request);
            //清除cookie
            log.info("清除cookie");
            CookieUtil.clearCookie(request,response);
            //重定向到登录页面
            String scheme = request.getScheme();
            int serverPort = request.getServerPort();
            String serverName = request.getServerName();
            String gotoURL = scheme + "://" + serverName + ":" + serverPort;
            String url = ConfigConsts.SSO_SERVICE_ROOT_URL + "/ticket/logout?gotoURL=" + gotoURL;
            log.info("退出，重定向地址：" + url);
            response.setHeader("gotoURL",url);
            response.setHeader("REDIRECT","REDIRECT");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /**
     * TODO 检测cookie中的证书是否与session中的一致
     * @author 李利广
     * @Date 2019年04月28日 16:13:38
     * @param request
     * @return java.lang.Boolean
     */
    private Boolean checkKey(HttpServletRequest request){
        msgLog.info("客户端登录！验证cookie与session中key是否一致====");
        boolean isVaild = false;
        msgLog.info("客户端登录！证书登录开关状态，VALIDATE_CA_FLAG===" + ConfigConsts.VALIDATE_CA_FLAG);
        if(ConfigConsts.VALIDATE_CA_FLAG){
            //cookie中的证书
            String cookieKey = CookieUtil.getKeyCode(request);
            msgLog.info("cookie中的证书：" + cookieKey);
            //session中的证书
            String sessionKey = (String) request.getSession().getAttribute(UserConfigConsts.COOKIE_KEY_CODE);
            msgLog.info("session中的证书：" + sessionKey);
            //如果cookie中的证书为空,直接退出
            if(StringUtils.isBlank(cookieKey)){
                isVaild = false;
            }else{
                if(StringUtils.isBlank(sessionKey)){
                    //如果sessionKey为空，表示是第一次登录，验证通过
                    isVaild = true;
                }else{
                    //如果cookie中证书不为空，校验是否与session一致
                    if(cookieKey.equals(sessionKey)){
                        isVaild = true;
                    }else{
                        isVaild = false;
                    }
                }
            }
        }else{
            isVaild = true;
        }
        msgLog.info("客户端登录！证书验证：" + (isVaild?"通过":"未通过"));
        return isVaild;
    }

}
