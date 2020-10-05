package com.sinosoft.sinoep.modules.system.online.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.online.entity.SysUserCount;

import java.util.List;

/**
 * TODO 在线人数统计service
 * @author 李利广
 * @Date 2019年06月19日 13:44:40
 */
public interface SysUserCountService {

    /**
     * TODO 保存在线人数
     * @author 李利广
     * @Date 2019年06月19日 13:51:58
     * @param count
     * @return com.sinosoft.sinoep.modules.system.online.entity.SysUserCount
     */
    SysUserCount saveCount(SysUserCount count);

    /**
     * 查询统计在线人数（分页）
     * @param page
     * @param count
     * @param startTime
     * @param endTime
     * @param type
     * @return PageImpl
     */
    PageImpl getUserCount(PageImpl page, SysUserCount count,String startTime,String endTime,String type);

    /**
     * 查询统计在线人数（不分页）
     * @param count
     * @param startTime
     * @param endTime
     * @param type
     * @return PageImpl
     */
    List<SysUserCount> getUserCount(SysUserCount count, String startTime, String endTime, String type);
}
