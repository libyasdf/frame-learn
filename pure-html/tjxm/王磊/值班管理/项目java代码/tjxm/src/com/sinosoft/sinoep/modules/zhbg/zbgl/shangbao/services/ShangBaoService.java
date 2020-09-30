package com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 上报情况Service层
 * @Date 2018/7/17 10:25
 * @Param
 * @return
 **/
public interface ShangBaoService {

    /**
     * @Author 王富康
     * @Description //TODO 值班表列表查询(值班表管理使用)
     * @Date 2018/7/17 9:30
     * @Param [pageImpl, time, unitId]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl list(Pageable pageable, PageImpl pageImpl, String month, String isReport, String reportStatus);

    /**
     * @Author 王富康
     * @Description //TODO 总值班室查看值班室上报情况 x/x
     * @Date 2018/7/27 10:48
     * @Param [pageable, pageImpl, month]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl getList(Pageable pageable, PageImpl pageImpl,String month);

    /**
     * @Author 王富康
     * @Description //TODO 查询处室上报情况详细信息
     * @Date 2018/7/27 17:52
     * @Param [month]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao>
     **/
    List<ShangBao> getChushiReportStatus(String year_month);

    /**
     * @Author 王富康
     * @Description //TODO 上报(修改上报表的上报状态)
     * @Date 2018/8/1 10:02
     * @Param [id]
     * @return int
     **/
    int reportDuty(String id, String month);

    /**
     * @Author 王富康
     * @Description //TODO 根据月份查询本部门是否已经添加该月份的上报数据
     * @Date 2018/8/1 14:49
     * @Param [dutyMonth]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao>
     **/
    List<ShangBao> queryOne(String month);

    /**
     * @Author 王富康
     * @Description //TODO 根据月份和单位id查询出一条上报数据
     * @Date 2018/8/24 16:31
     * @Param [month]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao>
     **/
    List<ShangBao> queryOneByMonthAndUnitId(String month,String unitId);

    /**
     * @Author 王富康
     * @Description //TODO 新增上报表数据
     * @Date 2018/8/1 15:46
     * @Param [sb]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao
     **/
    ShangBao save(ShangBao shangBao);
}
