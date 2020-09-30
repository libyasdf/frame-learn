package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.service;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.entity.DdjsDyglPunishEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dycf.dao.DycfDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DyxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.HistoryDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.IncreseDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.PartyBasicInfoDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglHistoryEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.util.tool.JDateToolkit;


@Service
public class DycfServiceImpl implements DycfService {
  private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DycfDao ddjsDyglPunishDao;
    @Autowired
    private DyxxDao dyxxDao;
    @Autowired
    private HistoryDao historyDao;
    @Autowired
    private PartyBasicInfoDao partyBasicInfoDao;
    @Autowired
    private IncreseDao increseDao;
 	
 	/**
	 * 保存党员处分 页面
	 * @author 
	 * @Date 2019-02-27 16:48:38
	 * @param entity
	 * @return
	 */
    public DdjsDyglPunishEntity save(DdjsDyglPunishEntity entity) throws IOException {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            entity.setCreUserId(UserUtil.getCruUserId());
            entity.setCreUserName(UserUtil.getCruUserName());
            entity.setCreDeptId(UserUtil.getCruDeptId());
            entity.setCreDeptName(UserUtil.getCruDeptName());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiName(UserUtil.getCruChushiName());
            entity.setCreJuId(UserUtil.getCruJuId());
            entity.setCreJuName(UserUtil.getCruJuName());
            entity.setVisible(CommonConstants.VISIBLE[1]);
            entity.setCreTime(JDateToolkit.getNowDate4());
            entity = ddjsDyglPunishDao.save(entity);

            if(entity.getDispositionResult().equals("01")||entity.getDispositionResult().equals("02")||entity.getDispositionResult().equals("03")){
                DdjsDyglUserbasicinfoEntity userbasicinfoEntity = dyxxDao.findOne(entity.getPartyMemberId());
                DdjsDyglHistoryEntity historyEntity = new DdjsDyglHistoryEntity();
                BeanUtils.copyProperties(userbasicinfoEntity,historyEntity);
                historyEntity.setUserbasicinfoId(userbasicinfoEntity.getId());
                historyEntity = historyDao.save(historyEntity);
                userbasicinfoEntity.setIsHistoryparty("06");
                userbasicinfoEntity.setVisible(CommonConstants.VISIBLE[0]);
                //更新党员基本情况
                String updatPartyBasic = "update DdjsDyglPartybasicinfoEntity q set q.locationpartygroup='' where q.partybasicinfoSuperId='"+userbasicinfoEntity.getId()+"'";
                int m = partyBasicInfoDao.update(updatPartyBasic);
                //删除党员增加情况
                String delIncrease = "update DdjsDyglIncreaseEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.increaseSuperId='"+userbasicinfoEntity.getId()+"'";
                int b = increseDao.update(delIncrease);
                dyxxDao.save(userbasicinfoEntity);
            }
            return entity;
        } else {
            DdjsDyglPunishEntity oldEntity = ddjsDyglPunishDao.findOne(entity.getId());
            oldEntity.setPartyOrganization(entity.getPartyOrganization());
            oldEntity.setPartyOrganizationId(entity.getPartyOrganizationId());
            oldEntity.setPartyMember(entity.getPartyMember());
            oldEntity.setPartyMemberId(entity.getPartyMemberId());
 			oldEntity.setPunishDate(entity.getPunishDate());
 			oldEntity.setPunishReason(entity.getPunishReason());
 			oldEntity.setPunishMechanism(entity.getPunishMechanism());
 			oldEntity.setApprovalAuthority(entity.getApprovalAuthority());
 			oldEntity.setHandlingSituation(entity.getHandlingSituation());
 			oldEntity.setBasicSituation(entity.getBasicSituation());
 			oldEntity.setDispositionResult(entity.getDispositionResult());
 			oldEntity.setWrongPerformance(entity.getWrongPerformance());
 			oldEntity.setRevokeDate(entity.getRevokeDate());
 			oldEntity.setMemo(entity.getMemo());
            oldEntity = ddjsDyglPunishDao.save(oldEntity);

            if(oldEntity.getDispositionResult().equals("01")||oldEntity.getDispositionResult().equals("02")||oldEntity.getDispositionResult().equals("03")){
                DdjsDyglUserbasicinfoEntity userbasicinfoEntity = dyxxDao.findOne(oldEntity.getPartyMemberId());
                DdjsDyglHistoryEntity historyEntity = new DdjsDyglHistoryEntity();
                BeanUtils.copyProperties(userbasicinfoEntity,historyEntity);
                historyEntity.setUserbasicinfoId(userbasicinfoEntity.getId());
                historyEntity = historyDao.save(historyEntity);
                userbasicinfoEntity.setIsHistoryparty("06");
                userbasicinfoEntity.setVisible(CommonConstants.VISIBLE[0]);
                //更新党员基本情况
                String updatPartyBasic = "update DdjsDyglPartybasicinfoEntity q set q.locationpartygroup='' where q.partybasicinfoSuperId='"+userbasicinfoEntity.getId()+"'";
                int m = partyBasicInfoDao.update(updatPartyBasic);
                //删除党员增加情况
                String delIncrease = "update DdjsDyglIncreaseEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.increaseSuperId='"+userbasicinfoEntity.getId()+"'";
                int b = increseDao.update(delIncrease);
                dyxxDao.save(userbasicinfoEntity);
            }
            return oldEntity;
        }

	}
	
	 /**
     * 根据主键ID查询一条数据 
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param id
     * @return
     */
 	@Override
    public DdjsDyglPunishEntity getById(String id) throws Exception {
        return ddjsDyglPunishDao.findOne(id);
    }
    
     /**
     * 根据id逻辑删除党员处分 对应列表
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param entity
     * @return
     */
 	@Override
    public int delete(DdjsDyglPunishEntity entity) {
        int n = 0;
        if(StringUtils.isNotBlank(entity.getId())){
            try {
                //逻辑删除党员处分
                String deleteSql = "update DdjsDyglPunishEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+entity.getId()+"'";
                n = ddjsDyglPunishDao.update(deleteSql);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除党员处分出现异常！");
                n=0;
            }
        }
        return n;
    }
    
     /**
     * 党员处分列表查询（带分页）
     * @author 
     * @Date 2019-02-27 16:48:38
     * @param pageable
     * @param pageImpl
     * @param entity
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsDyglPunishEntity entity, String startTime, String endTime,String id, String type) {
        StringBuilder sql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sql.append(" from DdjsDyglPunishEntity t ");
        sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(type.equals("org")){
            id = DzzUtil.spiltString(id);
            sql.append(" and partyOrganizationId in ("+id+")");
        }else{
            sql.append(" and partyMemberId = '"+id+"'");
        }
        // 处分日期时间
        if (StringUtils.isNotBlank(entity.getPunishDate())) {
            sql.append(" and substr(t.punishDate,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 党员名称
        if (StringUtils.isNotBlank(entity.getPartyMember()) ) {
            sql.append(" and t.partyMember = ?");
            para.add(entity.getPartyMember());
        }

        // 处分原因
        if (StringUtils.isNotBlank(entity.getPunishReason())) {
            sql.append(" and t.punishReason = ?");
            para.add(entity.getPunishReason());
        }

        // 处分结果
        if (StringUtils.isNotBlank(entity.getDispositionResult())) {
            sql.append(" and t.dispositionResult = ?");
            para.add(entity.getDispositionResult());
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            sql.append(" order by t.creTime desc ");
        } else {
            sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DdjsDyglPunishEntity> page = ddjsDyglPunishDao.query(sql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DdjsDyglPunishEntity> content = page.getContent();
       		 for (DdjsDyglPunishEntity ddjsDyglPunishEntity : content) {
                ddjsDyglPunishEntity.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }
    

}