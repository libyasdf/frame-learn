package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services;


import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.*;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.vo.DyTreeVO;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * 党员管理-党员信息-业务层
 * author 冯建海
 */
public interface DyxxService {


    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsDyglUserbasicinfoEntity entity ,String type,String ids);

    public int delete(DdjsDyglUserbasicinfoEntity entity);

    public Object getById(String id,String type);

    public DdjsDyglUserbasicinfoEntity saveForm(DdjsDyglUserbasicinfoEntity entity, DdjsDyglPartybasicinfoEntity partybasicinfoEntity,DdjsDyglIncreaseEntity increaseEntity) throws IOException;

    //public PageImpl getHistroyList(Pageable pageable, PageImpl pageImpl,DdjsDyglUserbasicinfoEntity entity,String ids);
    public PageImpl getHistroyList(Pageable pageable, PageImpl pageImpl,DdjsDyglHistoryEntity entity,String ids);

    PageImpl getListForObject(Pageable pageable, PageImpl pageImpl, Object entity, String superId, String type);

    public Object getTeamSituation();

    public List<DyTreeVO> findChild(DwxtOrg dwxtOrg,String ryType);

    public List<DdjsDyglUserbasicinfoEntity> findByPartyOrganizationIdAndVisible (String PartyOrganizationId, String visible);

    public DdjsDyglUserbasicinfoEntity findOne(String id);
    /**
     * 党员简要信息查询
     * TODO
     * @author 李帅
     * @Date 2018年10月30日
     * @param
     * @return
     */
    public  Object applicantStatistics(String annual,String ids);

    List<DdjsDyglUserbasicinfoEntity> findByUserIdAndVisible (String userId, String visible);
    /**
     * 根据id逻辑删除历史党员信息 对应列表
     * @author 李帅
     * @Date 2018年11月8日
     * @return
     */
    public int deleteHistory(DdjsDyglHistoryEntity entity) ;
    /**
     * 保存人员基本信息
     * @author 李帅
     * @Date 2018年11月8日
     * @return
     */
    public DdjsDyglUserbasicinfoEntity saveUserForm(DdjsDyglUserbasicinfoEntity entity) throws IOException;

    /**
     * 保存党员基本情况
     * @author 李帅
     * @Date 2018年11月8日
     * @return
     */
    public DdjsDyglPartybasicinfoEntity savePartyForm(DdjsDyglPartybasicinfoEntity entity) throws IOException;
    /**
     * 保存党员增加情况
     * @author 李帅
     * @Date 2018年11月8日
     * @return
     */
    public DdjsDyglIncreaseEntity saveIncreaseForm(DdjsDyglIncreaseEntity entity) throws IOException;
}
