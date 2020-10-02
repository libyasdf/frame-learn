package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.entity.DnbzEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * TODO 党组织管理 党内表彰Service
 *
 * @Author: 李帅
 * @Date: 2018/9/9 11:08
 */
public interface DnbzServiceI {

    /**
     *
     *党内表彰 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/9
     * */
    public DnbzEntity saveDnbz(DnbzEntity dnbz) throws IOException;
    /**
     * 党内表彰列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param pageable
     * @param pageImpl
     * @param dnbz
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DnbzEntity dnbz, String startTime, String endTime);
    /**
     * 根据id逻辑组织生活会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param dnbz
     * @return
     */
    public int delete(DnbzEntity dnbz);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param id
     * @return
     */
    public DnbzEntity getById(String id) throws Exception;

}
