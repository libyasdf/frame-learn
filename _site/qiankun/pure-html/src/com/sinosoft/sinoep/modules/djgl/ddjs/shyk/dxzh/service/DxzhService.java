package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.entity.DxzhEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/28 18:46
 */
public interface DxzhService {
    /**
     *
     *党小组会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public DxzhEntity saveDxzh(DxzhEntity dxzh) throws IOException;

    /**
     * 党小组会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param dxzh
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DxzhEntity dxzh, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑党小组会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dxzh
     * @return
     */
    public int delete(DxzhEntity dxzh);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param id
     * @return
     */
    public DxzhEntity getById(String id) throws Exception;

}
