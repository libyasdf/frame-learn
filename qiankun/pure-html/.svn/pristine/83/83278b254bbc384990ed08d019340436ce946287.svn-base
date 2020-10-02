package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.shyktx.service;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.shyktx.dao.ShykRemindDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.shyktx.entity.ShykRemindEntity;
import com.sinosoft.sinoep.urge.entity.SysUrge;
import com.sinosoft.sinoep.urge.services.SysUrgeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**TODO 三会一课 党课Service
 * @Author: 李帅
 * @Date: 2018/8/28 18:47
 */
@Service
public class ShykRemindServiceImpl implements ShykRemindService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShykRemindDao shykRemindDao;

    @Autowired
    private DwxtOrgService dwxtOrgService;

    @Autowired
    private NotityService notityService;

    @Autowired
    private SysUrgeService sysUrgeService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void sendRemind(){
        StringBuffer sql = new StringBuffer(" from ShykRemindEntity t where t.visible = '1'");
        Query query = shykRemindDao.getEntityManager().createQuery(sql.toString());
        List<ShykRemindEntity> list = query.getResultList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for(int i = 0;i<list.size();i++){
                int num = 1;
                ShykRemindEntity shykRemindEntity = list.get(i);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE,Integer.parseInt(shykRemindEntity.getAdvanceDays()));
                String sjDate = format.format(calendar.getTime());
                String planDate = shykRemindEntity.getStartTime();
                while(sjDate.compareTo(planDate)>0){
                    planDate = getDate(format.parse(shykRemindEntity.getStartTime()),shykRemindEntity.getBackCycle(),num);
                    num++;
                }
                if(sjDate.compareTo(planDate) == 0){
                    DwxtOrg dwxtOrg = dwxtOrgService.findOne(shykRemindEntity.getOrganId());
                    String[] userIds = dwxtOrg.getAssociativeUserId().split(",");
                    for(int j = 0;j<userIds.length;j++){
                        saveRemindMessageAndUrge(userIds[j],shykRemindEntity.getBackContent(),"",shykRemindEntity.getTitle());
                    }
                }
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    public String getDate(Date date,String cycle,int num){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (cycle){
            case "1":calendar.add(Calendar.MONTH,1*num); break;
            case "2":calendar.add(Calendar.MONTH,3*num); break;
            case "3":calendar.add(Calendar.YEAR,1*num); break;
            case "4":calendar.add(Calendar.YEAR,3*num); break;
            case "5":calendar.add(Calendar.YEAR,5*num); break;
        }
        return format.format(calendar.getTime());
    }

    public void saveRemindMessageAndUrge(String userId,String content,String url,String title) {
        NotityMessage message = new NotityMessage();
        message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
        message.setSubject(title);//标题
        message.setContent(content);//内容
        message.setSendTime(new Date());//发送时间
        message.setAcceptTime(new Date());//接收时间
        message.setAccepterId(userId);//接收人id
        message.setStatus("0");//状态
        message.setType("3");//类型  1手机  2邮箱   3栈内
        message.setMsgUrl(url);//消息链接
        notityService.add(message);
    }
}
