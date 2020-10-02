package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhEntity;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * 年度计划子表实体类
 * @Author: 李帅
 * @Date: 2018/8/30 9:34
 */
public interface NdjhService {

    /**
     * 年度计划列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param pageable
     * @param pageImpl
     * @param ndjh
     * @param startTime
     * @param endTime
     * @return
     */

    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, NdjhEntity ndjh, String startTime, String endTime,String typeVal,String ids);


    /**
     * 年度计划保存
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param ndjhEntity
     * @return
     */
    public NdjhEntity saveForm(NdjhEntity ndjhEntity) throws IOException;

    /**
     * 根据id逻辑一条年度计划
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param ndjhEntity
     * @return
     */
    public int delete(NdjhEntity ndjhEntity);
    /**
     * TODO 打开新增页面、只读页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    public JSONObject getById(String id) throws Exception;


    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param id
     * @return
     */
    public NdjhEntity getByIdVal(String id) throws Exception;

    /**
     * 新增时判断今年是否有年度计划
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param annual
     * @parampartyOrganizationName
     * @return
     */
    public NdjhEntity addFrom(String annual, String partyOrganizationId);

    /**
     *年度计划统计
     * TODO
     * @author 李帅
     * @Date 2018年9月6日
     * @return
     * */
    public PageImpl Statistics(Pageable pageable,String sqlName, String startTime, String endTime);

    /**
     *查询未上报的组织列表
     * TODO
     * @author 李帅
     * @Date 2018年9月6日
     * @param annual
     * @return
     * */
    public PageImpl getUnreported(Pageable pageable,String annual,String sqlName);

    /**
     * @Description 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param id
     * @return
     */
    public NdjhEntity getById1(String id );
    /**
     * @Description 更新业务表流程状态
     * @author 李帅
     * @Date 2018年9月28日
     * @param
     * @return
     */
    public NdjhEntity updateFlag(String id, String subflag);
    /**
     * @Description 根据组织编码得到党务工作者id
     * @author 李帅
     * @Date 2018年9月28日
     * @param
     * @return
     */
    public String selectUserId(String id);
}
