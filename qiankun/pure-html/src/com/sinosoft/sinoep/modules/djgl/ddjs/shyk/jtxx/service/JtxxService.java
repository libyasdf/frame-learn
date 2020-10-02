package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.entity.JtxxEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/28 19:46
 */
public interface JtxxService {
    /**
     *
     *集体学习讨论 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public JtxxEntity saveJtxx(JtxxEntity jtxx) throws IOException;

    /**
     * 集体学习讨论列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param jtxx
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, JtxxEntity jtxx, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑集体学习讨论一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param jtxx
     * @return
     */
    public int delete(JtxxEntity jtxx);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param id
     * @return
     */
    public JtxxEntity getById(String id) throws Exception;

}
