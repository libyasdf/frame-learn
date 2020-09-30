package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.entity.ZbdydhEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/23 18:46
 */
public interface ZbdydhService {
    /**
     *
     *支部党员大会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/23
     * */
    public ZbdydhEntity saveZbdydh(ZbdydhEntity zbdydh) throws IOException;

    /**
     * 支部党员大会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月24日
     * @param pageable
     * @param pageImpl
     * @param zbdydh
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, ZbdydhEntity zbdydh, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑支部党员大会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月24日
     * @param zbdydh
     * @return
     */
    public int delete(ZbdydhEntity zbdydh);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param id
     * @return
     */
    public ZbdydhEntity getById(String id) throws Exception;

    /**
     *三会一课会议次数统计
     * TODO
     * @author 李帅
     * @Date 2018年9月7日
     * @return
     * */
    public PageImpl hytjStatistics(Pageable pageable,PageImpl pageImpl,String tableName, String startTime, String endTime,String ids);

    /**
     *根据组织id得到组织名称
     * TODO
     * @author 李帅
     * @Date 2018年9月28日
     * @return
     * */
    public List<Map<String,Object>> getOrgName(String id);

}
