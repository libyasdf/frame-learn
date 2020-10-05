package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.service;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglDegreeEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglWorkingEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.entity.DdjsSqrglPartybasicinfoEntity;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 *TODO 申请人管理 申请人信息Service
 *
 * @Author: 李帅
 * @Date: 2018/9/13 16:46
 */
public interface SqrxxServiceI {
    /**
     * 申请人信息信息保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年9月13日
     * @return
     */

    public DdjsDyglUserbasicinfoEntity saveForm(DdjsDyglUserbasicinfoEntity entity, DdjsSqrglPartybasicinfoEntity partybasicinfoEntity, DdjsDyglWorkingEntity workingEntity,
                                                DdjsDyglDegreeEntity degreeEntity) throws IOException;
    /**
     * TODO 对编辑数据进行渲染
     * @author 李帅
     * @Date 2018年9月13日
     * @param
     * @param id
     * @return
     */
    public Object getById(String id, String type);

    /**
     * 申请人信息列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月14日
     * @param pageable
     * @param pageImpl
     * @param partyBasicinfo
     * @return
     */
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsSqrglPartybasicinfoEntity partyBasicinfo, String startTime, String endTime);
    /**
     * 新增申请人时判断该人员是否已经为申请人
     * TODO
     * @author 李帅
     * @Date 2018年8月29日
     * @param
     * @param
     * @return
     */
    public DdjsDyglUserbasicinfoEntity  queryUser(String userId);
}
