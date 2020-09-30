package com.sinosoft.sinoep.modules.mypage.mail.controller;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestToolsNew;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import java.net.URLEncoder;
import java.util.Map;

/**
 * TODO 个人门户集成邮件系统控制类
 * @author 李利广
 * @Date 2018年6月19日 上午11:29:42
 */
@Controller
@RequestMapping("/mypage/mail")
public class NewMailController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * TODO 获取邮件个数（已读、未读）
	 * @author 李利广
	 * @Date 2018年6月19日 下午1:53:02
	 * @return
	 */
	@RequestMapping("/getCount")
	@ResponseBody
	public JSONObject getMailMsgCount(){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		int allNum = 0,unreadNum = 0,readNum = 0;
		try {
			//获取当前用户的邮箱
			Map<String, SysFlowUserVo> userVo = UserUtil.getUserVo(UserUtil.getCruUserId());
			String mailBox = userVo.get(UserUtil.getCruUserId()).getEmail();
			BASE64Encoder encoder = new BASE64Encoder();
			String mail = encoder.encode(mailBox.getBytes("GBK"));
			mail = URLEncoder.encode(mail,"GBK");
			if (!StringUtils.isBlank(mailBox)) {
				/**
				 * mailbox	要统计的邮箱
				 * allmail	统计所有邮件数	无此参数：统计所有未读邮件数
				 * allfolder	统计所有邮件夹邮件数	无此参数：统计收件箱未读邮件数
				 */
				//获取所有邮件数
				String allRes = HttpRequestToolsNew.doGet(ConfigConsts.MAIL_SERVICE_PATH,
						"t=d_mailnumber&mailbox=" + mailBox + "&allmail&JSON","POST");
				//allRes = "{\"mailNumber\":5}";
				if (!StringUtils.isBlank(allRes)) {
					JSONObject allCount = JSONObject.fromObject(allRes);
					if (allCount.containsKey("mailNumber")) {
						allNum = allCount.getInt("mailNumber");
					}
				}
				//获取所有未读消息数
				String unreadRes = HttpRequestToolsNew.doGet(ConfigConsts.MAIL_SERVICE_PATH,
						"t=d_mailnumber&mailbox=" + mailBox + "&JSON","POST");
				//unreadRes = "{\"mailNumber\":0}";
				if (!StringUtils.isBlank(unreadRes)) {
					JSONObject unreadCount = JSONObject.fromObject(unreadRes);
					if (unreadCount.containsKey("mailNumber")) {
						unreadNum = unreadCount.getInt("mailNumber");
					}
				}
				//已读消息个数
				readNum = allNum - unreadNum;
				json.put("flag", "1");
				json.put("read", readNum);//已读消息数
				json.put("unread", unreadNum);//未读消息数
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
