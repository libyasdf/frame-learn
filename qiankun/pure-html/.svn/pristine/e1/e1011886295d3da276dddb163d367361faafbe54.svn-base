package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.entity.ZbwyhEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *
 *
 * @Author: 李帅
 * @Date: 2018/8/27 18:46
 */
public interface ZbwyhService {
    /**
     *
     *支部委员会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/27
     * */
    public ZbwyhEntity saveZbwyh(ZbwyhEntity zbwyh) throws IOException;

    /**
     * 支部委员会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param pageable
     * @param pageImpl
     * @param zbwyh
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, ZbwyhEntity zbwyh, String startTime, String endTime,String ids);


    /**
     * 根据id逻辑支部委员会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param zbwyh
     * @return
     */
    public int delete(ZbwyhEntity zbwyh);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param id
     * @return
     */
    public ZbwyhEntity getById(String id) throws Exception;

}
