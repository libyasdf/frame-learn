package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.service;

import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity.DzbDfgEntity;
import net.sf.json.JSONObject;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity.DfglEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * TODO 党组织管理 党费管理Service
 *
 * @Author: 李帅
 * @Date: 2018/9/11 11:08
 */
public interface DfglServiceI {

    /**
     *
     *党费管理 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/11
     * */
    public DfglEntity saveDfgl(DfglEntity dfgl) throws IOException;
    /**
     * 党费管理列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月11日
     * @param pageable
     * @param pageImpl
     * @param dfgl
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DfglEntity dfgl, String startTime, String endTime,String ids,String type);
    /**
     * 根据id逻辑党费管理一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param dfgl
     * @return
     */
    public int delete(DfglEntity dfgl);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月11日
     * @param
     * @return
     */
    public DfglEntity getById(String partyId,String annual,String month) throws Exception;
    /**
     * 新增党费时判断该人员该月是否已交党费
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    public DfglEntity queryFee(DfglEntity dfgl);

   /*
     * TODO 计算某一个党支部某个月所有党员的党费信息
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    public JSONObject getByIdVal(int pageNumber ,String ids , String month , String year,String opertation) ;
    /**
     * 按党支部保存党费数据
     * TODO
     * @author 李帅
     * @Date 2018年11月27日
     * @param
     * @return
     */
    public DzbDfgEntity saveDzbForm(DzbDfgEntity dzbDfgl)throws IOException;
    /**
     * TODO 在党支部视角查看的list列表
     * @author 李帅
     * @Date 2018年11月28日
     * @param
     * @param
     * @return
     */
    public JSONObject getDzbByIdVal(String PartyOrganizationId ,String annual,String ids);

    /**
     * TODO 得到某一个党支部有多少个人
     * @author 李帅
     * @Date 2018年11月29日
     * @param
     * @param
     * @return
     */
    public BigDecimal getTotal(String ids , String month , String year);

}
