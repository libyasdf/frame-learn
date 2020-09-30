package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity.DrhdEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/31 10:46
 */
public interface DrhdService {
    /**
     *
     *党日活动 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public DrhdEntity saveDrhd(DrhdEntity drhd) throws IOException;

    /**
     * 党日活动列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月31日
     * @param pageable
     * @param pageImpl
     * @param drhd
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DrhdEntity drhd, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑党日活动一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月31日
     * @param drhd
     * @return
     */
    public int delete(DrhdEntity drhd);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月31日
     * @param id
     * @return
     */
    public DrhdEntity getById(String id) throws Exception;

}
