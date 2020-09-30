package com.sinosoft.sinoep.modules.system.online.task;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.modules.system.online.common.OnlineUtils;
import com.sinosoft.sinoep.modules.system.online.entity.OnlineUserInfo;
import com.sinosoft.sinoep.modules.system.online.entity.SysUserCount;
import com.sinosoft.sinoep.modules.system.online.services.SysUserCountService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;

/**
 * TODO 定时任务类
 * @author 李利广
 * @Date 2019年06月20日 15:04:45
 */
@Component
public class OnlineTask {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServletContext servletContext;

    /**
     * TODO 定时记录当前在线人数
     * @author 李利广
     * @Date 2019年06月20日 15:05:06
     * @param
     * @return void
     */
    @Scheduled(fixedDelay = 5*60*1000)
    public void saveUserCount(){
        try{
            //System.out.println("定时器5分钟执行一次！！！！！");
            SysUserCount count = new SysUserCount();
            //获取当前时间：格式 yyyy-MM-dd HH:mm:ss
            count.setCreTime(DateUtil.now());
            //获取在线人数
            List<OnlineUserInfo> userList = OnlineUtils.getUserSize(servletContext);
            count.setCount(Convert.toStr(userList.size(),"0"));
            if(userList.size() > 0){
                count.setUserInfo(JSONArray.fromObject(userList).toString());
            }
            SysUserCountService countService = (SysUserCountService) SpringBeanUtils.getBean("userCountService");
            countService.saveCount(count);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    }

}
