package com.sinosoft.sinoep.modules.zhbg.zbgl.config.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 基础配置Service层
 * @Date 2018/7/13 16:47
 * @Param
 * @return
 **/
public interface ConfigService {

    /**
     * @Author 王富康
     * @Description //TODO 初始化查询表找出配置信息
     * @Date 2018/7/16 14:11
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config
     **/
    List<Config> queryOne(String dutyMonth);

    /**
     * @Author 王富康
     * @Description //TODO 以往基础配置信息列表
     * @Date 2018/7/30 18:16
     * @Param [pageable, pageImpl, name, key, describe]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl list(Pageable pageable, PageImpl pageImpl, String dutyMonthOfSearch, String unitNameOfSearch);

    /**
     * @Author 王富康
     * @Description //TODO 新增基础配置
     * @Date 2018/7/13 16:48
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config
     **/
    Config save(Config con);

    /**
     * @Author 王富康
     * @Description //TODO 修改基础配置
     * @Date 2018/7/16 17:58
     * @Param [con]
     * @return int
     **/
    int updateConfig(Config con);

    /**
     * @Author 王富康
     * @Description //TODO 是否按时上报
     * @Date 2018/8/1 10:49
     * @Param month 上报的考勤表的月份
     * @return boolean
     **/
    boolean isOnTime(String month);

    /**
     * @Author 王富康
     * @Description //TODO 根据月份和当前登录人的处室id查询当月本部门是否需要上报(暂时用于上报时，查看新增的数据是否可以上报)
     * @Date 2018/8/15 12:05
     * @Param [month]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config>
     **/
    List<Config> getConfigByMonthAndUnitId(String month);
}
