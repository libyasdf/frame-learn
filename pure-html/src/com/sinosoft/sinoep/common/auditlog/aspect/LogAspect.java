package com.sinosoft.sinoep.common.auditlog.aspect;

import com.sinosoft.sinoep.api.auditlog.service.SysAuditLogService;
import com.sinosoft.sinoep.api.auth.vo.MessageAuditLog;
import com.sinosoft.sinoep.common.auditlog.util.LogSqlTool;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.servlet.HttpContext;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.sso.constant.SSOConfigConsts;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * TODO 切面类，用来拦截controller记录日志
 * @author 李利广
 * @Date 2019年04月09日 13:40:25
 */
@Component
@Aspect
public class LogAspect {

    private Logger auditLog = LoggerFactory.getLogger("auditLog");

    @Autowired
    private SysAuditLogService logService;

    /**
     * 拦截注解@LogAnnotation
     */
    @Pointcut("@annotation(com.sinosoft.sinoep.handlerInterceptor.LogAnnotation)")
    private void addLog() {
    }

    /**
     * TODO 异常通知
     * @author 李利广
     * @Date 2019年04月10日 14:17:09
     * @param joinPoint
     * @param e
     * @return void
     */
    @AfterThrowing(value = "addLog() && @annotation(logAnnotation)", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, LogAnnotation logAnnotation, Exception e){
        auditLog.info("=====LogAspect异常通知开始=====");
    }

    /**
     * TODO 环绕通知
     * @author 李利广
     * @Date 2019年04月10日 14:17:25
     * @param pjp
     * @return java.lang.Object
     */
    @Around("addLog() && @annotation(logAnnotation)")
    public Object around(ProceedingJoinPoint pjp, LogAnnotation logAnnotation) throws Throwable{
        auditLog.info("=====LogAspect 环绕通知开始=====");
        String optype = "";
        try{
            /*MethodSignature signature = (MethodSignature)pjp.getSignature();
            LogAnnotation logAnnotation = signature.getMethod().getAnnotation(LogAnnotation.class);*/
            if(logAnnotation != null){
                optype = logAnnotation.value().toLowerCase();
                String opType = logAnnotation.opType().getOpType();
                if (StringUtils.isBlank(optype)){
                    optype = opType;
                }
                if(!OpType.QUERY.getOpType().equalsIgnoreCase(optype)){
                    //获取注解信息、及请求信息，并放到ThreadLocal中
                    handleLog(pjp, logAnnotation,null);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            auditLog.error(e.getMessage(),e);
        }
        //执行目标方法
        Object obj = null;
        try{
            Object[] args = pjp.getArgs();
            obj = pjp.proceed(args);
        }catch (Exception e){
            e.printStackTrace();
            auditLog.error(e.getMessage(),e);
        }
        if(StringUtils.isNotBlank(optype) && !OpType.QUERY.getOpType().equalsIgnoreCase(optype)){
            //调用日志接口，发送日志数据
            sendLog();
        }
        auditLog.info("=====LogAspect 环绕通知结束=====");
        return obj;
    }

    /**
     * TODO 日志处理
     * @author 李利广
     * @Date 2019年04月10日 15:13:35
     * @param joinPoint
     * @param e
     * @return void
     */
    private void handleLog(JoinPoint joinPoint, LogAnnotation logAnnotation, Exception e) {
        JSONObject json = new JSONObject();
        try {
            //拦截到controller请求后，设置请求唯一标识
            String uniqueId = UUID.randomUUID().toString().replaceAll("-","");
            auditLog.info("uniqueId:" + uniqueId);
            LogSqlTool.setUniqueId(uniqueId);
            //设置请求信息
            //获取请求参数
            HttpServletRequest request = HttpContext.getRequest();
            Map<String, String[]> map = request.getParameterMap();
            String resId = "";
            StringBuilder requestPara = new StringBuilder();
            for (String key : map.keySet()) {
                String[] vals = map.get(key);
                for(int i = 0;i < vals.length; i++){
                    requestPara.append(key).append("=").append(vals[i]).append("&");
                }
                if (SSOConfigConsts.RES_ID.equals(key)) {
                    // 获取资源id
                    resId = vals.length > 0 ? vals[0] : "";
                }
            }
            String requestParaStr = "";
            if(requestPara.length() > 0){
                requestParaStr = requestPara.substring(0,requestPara.length() - 1);
            }
            json.put("requestPara",requestParaStr);
            json.put("moduleId",resId);
            String optype = logAnnotation.value().toLowerCase();
            String opType = logAnnotation.opType().getOpType();
            if (StringUtils.isBlank(optype)){
                optype = opType;
            }
            json.put("operationType", optype);
            json.put("moduleState",logAnnotation.opName());
            auditLog.info("RequestInfo:" + json.toString());
            LogSqlTool.setRequestInfo(json);
            //同时设置一个sqlInfo，用来存放sql分析结果
            LogSqlTool.setSqlInfo(new JSONArray());
        }catch (Exception exp) {
            auditLog.error(exp.getMessage(), exp);
            exp.printStackTrace();
        }
    }

    /**
     * TODO 调用日志接口，发送日志数据
     * @author 李利广
     * @Date 2019年04月15日 12:32:06
     * @return java.lang.Boolean
     */
    private Boolean sendLog(){
        try{
            JSONObject json = new JSONObject();
            JSONObject req = LogSqlTool.getRequestInfo();
            HttpServletRequest request = HttpContext.getRequest();
            //组装requestInfo参数
            JSONObject requestInfo = new JSONObject();
            requestInfo.put("sysId", ConfigConsts.SYSTEM_ID);
            requestInfo.put("userId", UserUtil.getCruUserId());
            requestInfo.put("userFullName",UserUtil.getCruUserName());
            requestInfo.put("deptId",UserUtil.getCruDeptId());
            requestInfo.put("deptName",UserUtil.getCruDeptName());
            requestInfo.put("requestNum",LogSqlTool.getUniqueId());
            requestInfo.put("ip",LogSqlTool.getIpAddress(request));
            String url = request.getRequestURL().toString();
            requestInfo.put("url",url);
            requestInfo.put("requestPara",req.getString("requestPara"));
            //组装moduleInfo参数
            JSONObject moduleInfo = new JSONObject();
            moduleInfo.put("moduleId",req.getString("moduleId"));
            moduleInfo.put("operationType",req.getString("operationType"));
            moduleInfo.put("moduleState",req.getString("moduleState"));

            json.put("requestInfo",requestInfo);
            json.put("moduleInfo",moduleInfo);
            json.put("executeInfo",LogSqlTool.getSqlInfo());
            //调用接口
            auditLog.info("审计日志数据：" + json.toString());
            MessageAuditLog auditLogs = logService.logSave(json);
            auditLog.info("审计日志保存结果：" + JSONObject.fromObject(auditLogs).toString());
        }catch (Exception e){
            auditLog.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
