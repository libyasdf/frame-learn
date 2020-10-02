package com.sinosoft.sinoep.modules.system.online.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime;

import java.util.List;

public interface SysOnlineTimeService {

    /**
     * TODO 保存用户登录登出时间
     * @author 李利广
     * @Date 2019年06月19日 13:52:41
     * @param onlineTime
     * @param logType 类型（登入、登出）
     * @return com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime
     */
    SysOnlineTime saveTime(SysOnlineTime onlineTime,String logType);

    /**
     * TODO 根据登录票据，查询某个用户某次登录的在线时长
     * @author 李利广
     * @Date 2019年06月19日 13:54:21
     * @param userId
     * @param ticket
     * @return com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime
     */
    SysOnlineTime getOnlineTime(String userId,String ticket);

    /**
     * TODO 查询登录登出情况（分页）
     * @author 李利广
     * @Date 2019年06月19日 13:56:52
     * @param page
     * @param onlineTime
     * @param startDate
     * @param endDate
     * @return com.sinosoft.sinoep.common.util.PageImpl
     */
    PageImpl getOnlineTimeByDate(PageImpl page,SysOnlineTime onlineTime, String startDate,String endDate);

    /**
     * TODO 查询登录登出情况（不分页）
     * @author 李利广
     * @Date 2019年06月19日 13:56:52
     * @param onlineTime
     * @param startDate
     * @param endDate
     * @return com.sinosoft.sinoep.common.util.PageImpl
     */
    List<SysOnlineTime> getOnlineTimeByDate(SysOnlineTime onlineTime, String startDate, String endDate);

}
