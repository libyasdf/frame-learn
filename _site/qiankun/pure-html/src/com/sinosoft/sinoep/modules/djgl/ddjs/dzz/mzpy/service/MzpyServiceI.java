package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.DymzpyEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.MzpyEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * TODO 党组织管理 民主评议Service
 *
 * @Author: 李帅
 * @Date: 2018/9/9 11:08
 */
public interface MzpyServiceI {

    /**
     *
     *民主评议 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/9
     * */
    public MzpyEntity saveMzpy(MzpyEntity mzpy) throws IOException;
    /**
     * 民主评议列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param pageable
     * @param pageImpl
     * @param mzpy
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, MzpyEntity mzpy, String startDate, String endDate);
    /**
     * 根据id逻辑民主评议一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param mzpy
     * @return
     */
    public int delete(MzpyEntity mzpy);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param id
     * @return
     */
    public MzpyEntity getById(String id) throws Exception;

    /**
     *
     *党员民主评议 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/10
     * */
    public DymzpyEntity saveDymzpy(DymzpyEntity dymzpy);

    /**
     *  党员民主评议列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param pageable
     * @param pageImpl
     * @param dymzpy
     * @return
     */
    public PageImpl getPageListDymzpy(Pageable pageable, PageImpl pageImpl, DymzpyEntity dymzpy,boolean  isAll);
    /**
     * 根据id逻辑党员民主评议一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param dymzpy
     * @return
     */
    public int deleteDymzpy(DymzpyEntity dymzpy);
    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param id
     * @return
     */
    public DymzpyEntity getByIdDymzpy(String id) throws Exception;

    /**
     *
     *添加多条党员民主评议的方法
     * @Author: 李帅
     * @Date: 2018/9/19
     * */
    public List<DymzpyEntity> saveDymzpys(String partyNames, String partyIds, String reviewid,String startTime,String endTime);
}
