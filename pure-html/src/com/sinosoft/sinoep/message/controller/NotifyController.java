package com.sinosoft.sinoep.message.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.GlobalNames;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.message.dateUtil.DataUtil;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.message.services.MessageService;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;
import workflow.common.JDateToolkit;

/**
 * <B>系统名称：统一用户系统</B><BR>
 * <B>模块名称：用户中心</B><BR>
 * <B>中文类名：</B>消息通知<BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 戴国鹏
 * @since 2016年4月8日
 */
@RequestMapping("notity")
@Controller
public class NotifyController {
	
    private static Log log = LogFactory.getLog(NotifyController.class);

    private NotityService notityService = (NotityService) SpringBeanUtils.getBean("notityService");
    
	@Autowired
	private MessageService messageService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
    /**
     * 
     * <B>方法名称：消息初始化</B><BR>
     * <B>概要说明：将消息以分页展示出来</B><BR>
     * 
     * @param message
     * @param timeRange
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public PageImpl list(PageImpl pageImpl, NotityMessage message,String timeRange)
            throws Exception {
    	pageImpl.setFlag("0");
        String userId = message.getAccepterId();
        if(StringUtils.isBlank(message.getAccepterId())){
        	userId = UserUtil.getCruUserId();
        }
        //message.getAccepterId();
        Page page = new Page();
        if (StringUtils.isNotBlank(userId)) {
        	try {
            	//notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);
                page = notityService.getPage(pageImpl.getPageNumber(),pageImpl.getPageSize(), message, userId);
                pageImpl.setFlag("1");
            }catch (Exception e) {
            	log.error(e.getMessage(),e);
            }
        }
        @SuppressWarnings("unchecked")
		List<NotityMessage> recordList = page.getRecordList();
        if (!recordList.isEmpty()) {
			for (NotityMessage msg : recordList) {
				String content = msg.getContent();
				if (StringUtils.isNotBlank(content) && content.contains("\n")) {
					content = content.replace("\n", "<br>");
					msg.setContent(content);
				}
			}
		}
		pageImpl.getData().setRows(recordList);
        pageImpl.getData().setTotal(page.getTotalResult());
        return pageImpl;
    }

    /**
     * 
     * <B>方法名称：查询消息未读状态的个数</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "msgcount")
    public JSONObject getMsgCount(HttpServletRequest request,String accepterId) {
        JSONObject msgObj = new JSONObject();
        msgObj.put("flag", "0");
        String userId = accepterId;
        if(StringUtils.isBlank(userId)){
        	userId = UserUtil.getCruUserId();
        }
        if (StringUtils.isNotBlank(userId)) {
            try {
				/*Long msgCount = notityService.getMyMsgCount(userid, ConfigConsts.SYSTEM_ID, GlobalNames.SYS_NOTIFY_STATUS_W);*/
				Long msgCount = notityService.getMyMsgCount(userId, "", GlobalNames.SYS_NOTIFY_STATUS_W);
				msgObj.put("user", userId);
				msgObj.put("msgCount", msgCount);
				msgObj.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
        }
        return msgObj;
    }

    /**
     * 
     * <B>方法名称：消息状态修改</B><BR>
     * <B>概要说明：消息状态在点击标题后展开内容，同时消息由未读变成已读</B><BR>
     * 
     * @param id
     */
    @ResponseBody
    @RequestMapping(value = "edit")
    public JSONObject edit(String id) {
    	JSONObject json = new JSONObject();
    	json.put("flag", "0");
        try {
			NotityMessage notity = notityService.getById(id);
			notity.setStatus(GlobalNames.SYS_NOTIFY_STATUS_Y);
			notityService.updateStatus(notity);
			json.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
        return json;
    }

    /**
     * 
     * <B>方法名称：消息的新增</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年5月8日 下午1:32:22
     * @param notityMessage
     * @return JSONObject
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public JSONObject save(NotityMessage notityMessage) {
        JSONObject result = new JSONObject();
        result.put("flag", "0");
        try {
        	notityMessage.setSendTime(new Date());
			notityService.add(notityMessage);
			result.put("flag", "1");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.put("msg", "发送消息出现异常！");
		}
        return result;
    }

    /**
     * 
     * <B>方法名称：根据消息id查询消息详情</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年5月10日 上午9:35:32
     * @param id
     * @return NotityMessage
     */
    @ResponseBody
    @RequestMapping(value = "view")
    public JSONObject view(String id) {
    	JSONObject json = new JSONObject();
    	json.put("flag", "0");
        NotityMessage notity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				notity = notityService.getById(id);
				json.put("flag", "1");
				json.put("data", notity);
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);
		}
        return json;
    }
    
    
    @RequestMapping("nine")
    @ResponseBody
    public List<NotityMessage> nineday(String userId) {
    	JSONObject json = new JSONObject();
    	json.put("flag", "1");
        List<NotityMessage> msgByDate = new ArrayList<>();
        try {
            msgByDate = messageService.getMsgByDate(DataUtil.getDateBefore(new Date(), 9), DataUtil.getDateAfter(new Date(), 1), userId);
            JSONObject data = new JSONObject();
            if (!msgByDate.isEmpty()) {
                for (NotityMessage msg : msgByDate) {
                    String content = msg.getContent();
                    if (StringUtils.isNotBlank(content) && content.contains("\n")) {
                        content = content.replace("\n", "<br>");
                        msg.setContent(content);
                    }
                }
            }
            data.put("rows", msgByDate);
            json.put("data", data);
            json.put("yesterday", DataUtil.getDateBefore(new Date(), 1));
            json.put("hebdomad2", DataUtil.getDateBefore(new Date(), 1));
            json.put("hebdomad9", DataUtil.getDateBefore(new Date(), 9));
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return msgByDate;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping("scope")
    @ResponseBody
    public PageImpl scope(PageImpl page,String userId, String startDate, String endDate, String content) {
        try{
            content = content.trim();
            if (StringUtils.isBlank(content)) {
                content="";
            }
            if(startDate.equals("oneYue")) {
                startDate = DataUtil.getDateBefore(new Date(), 30);
            }
            if(startDate.equals("threeYue")) {
                startDate = DataUtil.getDateBefore(new Date(), 90);
            }
            if(startDate.equals("oneYers")) {
                startDate = DataUtil.getDateBefore(new Date(), 365);
            }
            if(startDate.equals("all")) {
                startDate = "";
            }

            page = messageService.getMsgByDateAndContent(page, startDate, DataUtil.getDateAfter(new Date(), 1), content, userId);
            List<NotityMessage> msgByDate = (List<NotityMessage>) page.getData().getRows();
            if (!msgByDate.isEmpty()) {
                for (NotityMessage msg : msgByDate) {
                    String cont = msg.getContent();
                    if (StringUtils.isNotBlank(cont) && cont.contains("\n")) {
                        cont = cont.replace("\n", "<br>");
                        msg.setContent(cont);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return page;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping("history")
    @ResponseBody
    public PageImpl history(PageImpl page,String userId) {
        try {
            page = messageService.getMsgByDateAndContent(page, "", DataUtil.getDateBefore(new Date(), 8), "", userId);
            List<NotityMessage> msgByDate = (List<NotityMessage>) page.getData().getRows();
            if (!msgByDate.isEmpty()) {
                for (NotityMessage msg : msgByDate) {
                    String cont = msg.getContent();
                    if (StringUtils.isNotBlank(cont) && cont.contains("\n")) {
                        cont = cont.replace("\n", "<br>");
                        msg.setContent(cont);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return page;
    }
    
    @RequestMapping("todaymsg")
    @ResponseBody
    public List<NotityMessage> todaymsg(String userId) {
        List<NotityMessage> todayMsg = new ArrayList<>();
        try {
            todayMsg = messageService.getTodayMsg(userId);
            if (!todayMsg.isEmpty()) {
                for (NotityMessage msg : todayMsg) {
                    String cont = msg.getContent();
                    if (StringUtils.isNotBlank(cont) && cont.contains("\n")) {
                        cont = cont.replace("\n", "<br>");
                        msg.setContent(cont);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return todayMsg;
    }
    
    @RequestMapping("creTime")
    @ResponseBody
    public JSONObject creTime(String userId) {
    	JSONObject json = new JSONObject();
    	try {
            json.put("todayTime", DataUtil.getToday());
            json.put("yesterdayTime", DataUtil.getDateBefore(new Date(), 1));
            json.put("hebdomad2Time", DataUtil.getDateBefore(new Date(), 1));
            json.put("hebdomad9Time", DataUtil.getDateBefore(new Date(), 9));
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return json;
    }
    
    @RequestMapping("updateHistory")
    @ResponseBody
    public JSONObject updateHistory(String userId) {
    	JSONObject json = new JSONObject();
    	json.put("flag", "0");
    	try{
            Boolean updateStatusByDate = messageService.updateStatusByDate("", DataUtil.getDateBefore(new Date(), 8), userId);
            if (updateStatusByDate) {
                json.put("flag", "1");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return json;
    }
    
    @RequestMapping("updateStatusByDate")
    @ResponseBody
    public JSONObject updateStatusByDate(String date,String userId) {
    	JSONObject json = new JSONObject();
    	json.put("flag", "0");
    	String startTime = "";
    	String andTime = "";
        try{
            if (date.equals("today")) {
                startTime = DataUtil.getToday();
                andTime=DataUtil.getDateAfter(new Date(), 1);
            }else if(date.equals("yesterday")) {
                startTime = DataUtil.getDateBefore(new Date(), 1);
                andTime=DataUtil.getToday();
            }else if(date.equals("hebdomad")) {
                startTime = DataUtil.getDateBefore(new Date(), 7);
                andTime=DataUtil.getDateBefore(new Date(), 1);
            }else if(date.equals("msgCn")) {
                startTime =  DataUtil.getDateBefore(new Date(), 30000);
                andTime=DataUtil.getDateAfter(new Date(), 1);
            }
            Boolean updateStatusByDate = messageService.updateStatusByDate(startTime, andTime, userId);
            if (updateStatusByDate) {
                json.put("flag", "1");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return json;
    }
    
    @RequestMapping("historyCount")
    @ResponseBody
    public JSONObject historyCount(String userId) {
        JSONObject json = new JSONObject();
        try{
            json = messageService.historyCount("", DataUtil.getDateBefore(new Date(), 8), userId);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return json;
    }
    
    @RequestMapping("getIsRead")
    @ResponseBody
    public PageImpl getIsRead(PageImpl impl,String status,String userId) {
        try{
            impl = messageService.getIsRead(impl, status, userId);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    	return impl;
    }

	/**
	 * 结束流程后发送消息提示
	 * @param workItemId //待办id
	 * @param msgConfirmstr//办理类型
	 * @return
	 * http://localhost:8080/notity/finishFlowSendMessage?subject=办结提示&content=测试发送成功后发送消息提示&accepterId=97588
	 */
    @LogAnnotation(value = "save",opName = "结束流程后发送消息提示")
    @RequestMapping("finishFlowSendMessage")
	@ResponseBody
    public JSONObject finishFlowSendMessage(String workItemId,String msgConfirmstr){
    	JSONObject json = new JSONObject();
    	boolean flag = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date data = new Date();
		try {
			String content ="";
			String accepterId = "";
			String sql = "select w.op_name,w.createTime,w.draftUser from sys_waitdo w where w.id ='"+workItemId+"'";
			List<Map<String,Object>> list =jdbcTemplate.queryForList(sql);
			Map<String,Object> map = new HashMap<String, Object>();
			if(list.size()>0){
				map = list.get(0);
				content = "时间为:"+(String) map.get("createTime")+"的"+(String)map.get("op_name")+msgConfirmstr;
				accepterId = (String) map.get("draftUser");
				log.info("内容="+content+"  接收人="+accepterId);
				data = format.parse(JDateToolkit.getNowDate4());
				NotityMessage message = new NotityMessage();
				message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
				message.setSubject("办结提醒");//标题
				message.setContent(content);//内容
				message.setSendTime(data);//发送时间
				message.setAcceptTime(data);//接收时间
				message.setAccepterId(accepterId);//接收人id
				message.setStatus("0");//状态
				message.setType("3");//类型  1手机    2邮箱   3栈内
				//message.setMsgUrl(messageURL);//消息链接
				NotityService notityService =  (NotityService) SpringBeanUtils.getBean("notityService");
				notityService.add(message);
				flag = true;
			}else {
				log.info("没有找到id为:"+workItemId+"的待办!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("flag",flag);
    	return json;
	}

	/**
	 * 查询已读、未读消息数量
	 * @param accepterId 接收人id
	 * @param status 0：未读 1：已读
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "readcount")
	public JSONObject getReadCount(String accepterId,String status) {
		JSONObject msgObj = new JSONObject();
		Long msgCount = 0L;
		msgObj.put("flag", "0");
		String userId = accepterId;
		if(StringUtils.isBlank(userId)){
			userId = UserUtil.getCruUserId();
		}
		if (StringUtils.isNotBlank(userId)) {
			try {
				msgCount = notityService.getMyMsgCount(userId, "", status);
				msgObj.put("user", userId);
				msgObj.put("msgCount", msgCount);
				msgObj.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return msgObj;
	}
}
