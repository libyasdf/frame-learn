package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.dao.DymzpyDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.dao.MzpyDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.DymzpyEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.mzpy.entity.MzpyEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** TODO 党组织管理 民主评议Service
 * @Author: 李帅
 * @Date: 2018/9/9 16:39
 */
@Service
public class MzpyServiceImpl implements MzpyServiceI {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MzpyDao mzpyDao;
    @Autowired
    private DymzpyDao dymzpyDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     *
     *民主评议 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/9
     * */
    public MzpyEntity saveMzpy(MzpyEntity mzpy) throws IOException {
        String id = mzpy.getId();
        if (StringUtils.isBlank(id)) {
            mzpy.setCreUserId(UserUtil.getCruUserId());
            mzpy.setCreUserName(UserUtil.getCruUserName());
            mzpy.setCreDeptId(UserUtil.getCruDeptId());
            mzpy.setCreDeptName(UserUtil.getCruDeptName());
            mzpy.setCreChushiId(UserUtil.getCruChushiId());
            mzpy.setCreChushiName(UserUtil.getCruChushiName());
            mzpy.setCreJuId(UserUtil.getCruJuId());
            mzpy.setCreJuName(UserUtil.getCruJuName());
            mzpy.setVisible(CommonConstants.VISIBLE[1]);
            mzpy.setCreTime(JDateToolkit.getNowDate4());
            mzpy.setPartyOrganizationName(java.net.URLDecoder.decode(mzpy.getPartyOrganizationName(),"UTF-8"));
            mzpy = mzpyDao.save(mzpy);
            return mzpy;
        } else {
            MzpyEntity oldMzpy = mzpyDao.findOne(mzpy.getId());
            oldMzpy.setPartyOrganizationId(mzpy.getPartyOrganizationId());
            oldMzpy.setPartyOrganizationName(java.net.URLDecoder.decode(mzpy.getPartyOrganizationName(),"UTF-8"));
            oldMzpy.setStartTime(mzpy.getStartTime());
            oldMzpy.setEndTime(mzpy.getEndTime());
            oldMzpy.setPlaceval(mzpy.getPlaceval());
            oldMzpy.setThemeval(mzpy.getThemeval());
            oldMzpy.setPeopleNumber(mzpy.getPeopleNumber());
            oldMzpy.setActualNumber(mzpy.getActualNumber());
            oldMzpy.setHost(mzpy.getHost());
            oldMzpy.setPrimaryCoverage(mzpy.getPrimaryCoverage());
            oldMzpy.setSituation(mzpy.getSituation());
            oldMzpy.setAttendance(mzpy.getAttendance());
            oldMzpy = mzpyDao.save(oldMzpy);
            List<DymzpyEntity> list =  dymzpyDao.findByReviewidAndVisible(oldMzpy.getId(),CommonConstants.VISIBLE[1]);
            for(DymzpyEntity dymzpyEntity : list){
                dymzpyEntity.setStartTime(oldMzpy.getStartTime());
                dymzpyEntity.setEndTime(oldMzpy.getEndTime());
            }
            dymzpyDao.save(list);
            return oldMzpy;
        }

    }

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
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, MzpyEntity mzpy, String startDate, String endDate) {

        StringBuilder mzpySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        mzpySql.append(" from MzpyEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        mzpySql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        mzpySql.append(" and t.partyOrganizationId ='" + mzpy.getPartyOrganizationId() + "'" );

        // 开始时间
        if (StringUtils.isNotBlank(mzpy.getStartTime())) {
            mzpySql.append(" and substr(t.startTime,1,10) between ? and ?");
            para.add(startDate);
            para.add(endDate);
        }


        // 地点
        if (StringUtils.isNotBlank(mzpy.getPlaceval()) ) {
            mzpySql.append(" and t.placeval like ?");
            para.add("%"+mzpy.getPlaceval()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(mzpy.getHost()) ) {
            mzpySql.append(" and t.host like ?");
            para.add("%"+mzpy.getHost()+"%");
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            mzpySql.append(" order by t.creTime desc ");
        } else {
            mzpySql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

         Page<MzpyEntity> page = mzpyDao.query(mzpySql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<MzpyEntity> content = page.getContent();
        for (MzpyEntity mzpyVal : content) {
            mzpyVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑民主评议一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param mzpy
     * @return
     */
    @Override
    public int delete(MzpyEntity mzpy) {
        int n = 0;
        if(StringUtils.isNotBlank(mzpy.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update DDJS_DZZ_REVIEW q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+mzpy.getId()+"'";
                String delQuestion1 = "update DDJS_DZZ_PARTYREVIEW d set d.visible='"+CommonConstants.VISIBLE[0]+"' where d.REVIEWID='"+mzpy.getId()+"'";
                jdbcTemplate.update(delQuestion1);
                n = jdbcTemplate.update(delQuestion);
                // n = mzpyDao.update(delQuestion);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除民主评议出现异常！");
                n=0;
            }
        }
        return n;
    }
    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param id
     * @return
     */
    @Override
    public MzpyEntity getById(String id) throws Exception {
        MzpyEntity mzpyEntity = mzpyDao.findOne(id);
        String attendance = "";
        List<DymzpyEntity> list = dymzpyDao.findByReviewidAndVisible(id,CommonConstants.VISIBLE[1]);
        if(list.size() > 0 ) {
            for (DymzpyEntity dymzpyEntity : list) {
                attendance += dymzpyEntity.getPartyName() + ",";
            }
            attendance = attendance.substring(0, attendance.length() - 1);
            mzpyEntity.setAttendance(attendance);
        }
        return mzpyEntity;
    }


    /**
     *
     *党员民主评议 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/10
     * */
    public DymzpyEntity saveDymzpy(DymzpyEntity dymzpy) {
        String id = dymzpy.getId();
        if (StringUtils.isBlank(id)) {
            dymzpy.setCreUserId(UserUtil.getCruUserId());
            dymzpy.setCreUserName(UserUtil.getCruUserName());
            dymzpy.setCreDeptId(UserUtil.getCruDeptId());
            dymzpy.setCreDeptName(UserUtil.getCruDeptName());
            dymzpy.setCreChushiId(UserUtil.getCruChushiId());
            dymzpy.setCreChushiName(UserUtil.getCruChushiName());
            dymzpy.setCreJuId(UserUtil.getCruJuId());
            dymzpy.setCreJuName(UserUtil.getCruJuName());
            dymzpy.setVisible(CommonConstants.VISIBLE[1]);
            dymzpy.setCreTime(JDateToolkit.getNowDate4() + " " + dymzpy.getDyOrder());
            dymzpy = dymzpyDao.save(dymzpy);
            return dymzpy;
        } else {
            DymzpyEntity oldMzpy = dymzpyDao.findOne(dymzpy.getId());
            oldMzpy.setPartyName(dymzpy.getPartyName());
            oldMzpy.setStartTime(dymzpy.getStartTime());
            oldMzpy.setEndTime(dymzpy.getEndTime());
            oldMzpy.setReviewResults(dymzpy.getReviewResults());
            oldMzpy.setReviewReason(dymzpy.getReviewReason());
            oldMzpy.setReviewid(dymzpy.getReviewid());
            oldMzpy.setHandleReason(dymzpy.getHandleReason());
            oldMzpy.setHandleReasonType(dymzpy.getHandleReasonType());
            oldMzpy.setHandleSituation(dymzpy.getHandleSituation());
            oldMzpy.setPartyOrganizationOpinion(dymzpy.getPartyOrganizationOpinion());
            oldMzpy.setMemo(dymzpy.getMemo());
            oldMzpy.setVisible("1");
            oldMzpy = dymzpyDao.save(oldMzpy);
            return oldMzpy;
        }

    }


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
    @Override
    public PageImpl getPageListDymzpy(Pageable pageable, PageImpl pageImpl, DymzpyEntity dymzpy,boolean  isAll) {

        StringBuilder dymzpySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        dymzpySql.append(" from DymzpyEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dymzpySql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(isAll){
            dymzpySql.append(" and t.partyId = '" + dymzpy.getPartyId() + "'");
        }else{
            dymzpySql.append(" and t.reviewid = '" + dymzpy.getReviewid() + "'");
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            dymzpySql.append(" order by t.creTime desc ");
        } else {
            dymzpySql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DymzpyEntity> page = dymzpyDao.query(dymzpySql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DymzpyEntity> content = page.getContent();
        for (DymzpyEntity mzpyVal : content) {
            mzpyVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    /**
     * 根据id逻辑党员民主评议一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param dymzpy
     * @return
     */
    @Override
    public int deleteDymzpy(DymzpyEntity dymzpy) {
        int n = 0;
        if(StringUtils.isNotBlank(dymzpy.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update DymzpyEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+dymzpy.getId()+"'";
                n = mzpyDao.update(delQuestion);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除党员民主评议出现异常！");
                n=0;
            }
        }
        return n;
    }

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月10日
     * @param id
     * @return
     */
    @Override
    public DymzpyEntity getByIdDymzpy(String id) throws Exception {
        return dymzpyDao.findOne(id);
    }

    /**
     *
     *添加多条党员民主评议的方法
     * @Author: 李帅
     * @Date: 2018/9/19
     * */
    public List<DymzpyEntity> saveDymzpys(String partyNames,String partyIds, String reviewid,String startTime,String endTime){
        List<DymzpyEntity> dymzpyList  =new ArrayList<DymzpyEntity>();
        if(StringUtils.isNotBlank(partyIds)) {
            String[] partyNameArray = partyNames.split(",");
            String[] partyIdArray = partyIds.split(",");
            int k = 0;
            if (partyNameArray.length <= partyIdArray.length) {
                k = partyNameArray.length;
            } else {
                k = partyIdArray.length;
            }
            for (int n = 0; n < k; n++) {
                String partyName = partyNameArray[n];
                String partyId = partyIdArray[n];
                DymzpyEntity dymzpy = dymzpyDao.findFirstByReviewidAndPartyIdAndVisible(reviewid, partyId,CommonConstants.VISIBLE[1]);
                if(null == dymzpy){
                    dymzpy = new DymzpyEntity();
                    dymzpy.setReviewResults("03");
                }
                dymzpy.setDyOrder(n);
                dymzpy.setPartyName(partyName);
                dymzpy.setPartyId(partyId);
                dymzpy.setReviewid(reviewid);
                dymzpy.setStartTime(startTime);
                dymzpy.setEndTime(endTime);
                dymzpy.setVisible("1");
                dymzpy = this.saveDymzpy(dymzpy);
                dymzpyList.add(dymzpy);
            }
        }
        return dymzpyList;
    }
}
