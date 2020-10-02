package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.entity.ZzshhEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/28 19:46
 */
public interface ZzshhService {
    /**
     *
     *组织生活会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public ZzshhEntity saveZzshh(ZzshhEntity zzshh) throws IOException;

    /**
     * 组织生活会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param zzshh
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, ZzshhEntity zzshh, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑组织生活会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param zzshh
     * @return
     */
    public int delete(ZzshhEntity zzshh);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param id
     * @return
     */
    public ZzshhEntity getById(String id) throws Exception;

}
