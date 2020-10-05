package com.sinosoft.sinoep.modules.system.online.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.online.dao.SysUserCountDao;
import com.sinosoft.sinoep.modules.system.online.entity.SysUserCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userCountService")
public class SysUserCountServiceImpl implements SysUserCountService {

    @Autowired
    private SysUserCountDao countDao;

    /**
     * TODO 保存在线人数
     * @author 李利广
     * @Date 2019年06月19日 13:51:58
     * @param count
     * @return com.sinosoft.sinoep.modules.system.online.entity.SysUserCount
     */
    @Override
    public SysUserCount saveCount(SysUserCount count){
        return countDao.save(count);
    }

    /**
     * 查询统计在线人数（分页）
     * @param page
     * @param count
     * @param startTime
     * @param endTime
     * @param type
     * @return PageImpl
     */
    @Override
    public PageImpl getUserCount(PageImpl page, SysUserCount count, String startTime, String endTime, String type){
        return null;
    }

    /**
     * 查询统计在线人数（不分页）
     * @param count
     * @param startTime
     * @param endTime
     * @param type
     * @return PageImpl
     */
    @Override
    public List<SysUserCount> getUserCount(SysUserCount count, String startTime, String endTime, String type){
        return null;
    }
}
