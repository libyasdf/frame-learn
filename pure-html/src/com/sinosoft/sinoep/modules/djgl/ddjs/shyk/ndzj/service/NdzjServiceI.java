package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.entity.NdzjEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * @Author: 李帅
 * @Date: 2018/9/8 8:10
 */
public interface NdzjServiceI {
    /**
     * 年度总结列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月8日
     * @param pageable
     * @param pageImpl
     * @param ndzj
     * @param startTime
     * @param endTime
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, NdzjEntity ndzj, String startTime, String endTime,String typeVal,String ids) ;


    /**
     *
     *年底总结 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/8
     * */
    public NdzjEntity saveNdzj(NdzjEntity ndzj) throws IOException;

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月8日
     * @param id
     * @return
     */
    public NdzjEntity getById(String id) throws Exception;

    /**
     * 根据id逻辑年底总结一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param ndzj
     * @return
     */
    public int delete(NdzjEntity ndzj);

    /**
     * 新增时判断今年是否有年底总结
     * TODO
     * @author 李帅
     * @Date 2018年10月10日
     * @param annual
     * @parampartyOrganizationName
     * @return
     */
    public NdzjEntity addFrom(String annual, String partyOrganizationId);
}
