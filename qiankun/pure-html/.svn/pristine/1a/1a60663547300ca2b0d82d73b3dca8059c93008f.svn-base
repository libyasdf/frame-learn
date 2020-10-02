package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.entity.DkglEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/28 19:46
 */
public interface DkglService {
    /**
     *
     *党课 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public DkglEntity saveDkgl(DkglEntity dkgl) throws IOException;

    /**
     * 党课列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param dkgl
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DkglEntity dkgl, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑党课一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dkgl
     * @return
     */
    public int delete(DkglEntity dkgl);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param id
     * @return
     */
    public DkglEntity getById(String id) throws Exception;

}
