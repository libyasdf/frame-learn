package com.sinosoft.sinoep.modules.system.online.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime;
import com.sinosoft.sinoep.modules.system.online.services.SysOnlineTimeService;
import com.sinosoft.sinoep.modules.system.online.services.SysUserCountService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO 统计在线人数及在线时长控制器
 * @author 李利广
 * @Date 2019年06月21日 14:20:03
 */
@Controller
@RequestMapping("/system/online")
public class OnlineController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysOnlineTimeService timeService;

    @Autowired
    private SysUserCountService countService;

    /**
     * TODO 统计在线人数
     * @author 李利广
     * @Date 2019年06月21日 14:21:33
     */
    @ResponseBody
    @RequestMapping("getUserCount")
    @LogAnnotation(opType = OpType.QUERY,opName = "统计在线人数")
    public JSONObject getUserCount(){
        JSONObject json = new JSONObject();
        return json;
    }

    /**
     * TODO 查询在线时长
     * @author 李利广
     * @Date 2019年06月20日 17:55:07
     * @param page
     * @param onlineTime
     * @param timeRange 日期范围，精确到日（例如：2019-06-20 - 2019-06-21）
     * @return com.sinosoft.sinoep.common.util.PageImpl
     */
    @ResponseBody
    @RequestMapping("getOnlineTime")
    @LogAnnotation(opType = OpType.QUERY,opName = "查询在线时长")
    public PageImpl getOnlineTime(PageImpl page, SysOnlineTime onlineTime, String timeRange){
        page.setFlag("0");
        String startDate = "";
        String endDate = "";
        try {
            if (StringUtils.isNotBlank(timeRange)) {
                startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
                endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
            }
            page = timeService.getOnlineTimeByDate(page,onlineTime,startDate,endDate);
            page.setFlag("1");
        }catch (Exception e){
            e.printStackTrace();
            page.setMsg("查询异常，请稍后重试");
            log.error(e.getMessage(),e);
        }
        return page;
    }
}
