package com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.dao.JldtxbDao;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.entity.Jldtxb;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JldtxbServiceImpl implements JldtxbService {

    @Autowired
    private JldtxbDao jldtxbDao;

    @Override
    public Jldtxb saveForm(Jldtxb jldtxb) {
        jldtxb.setVisible("1");
        if (StringUtils.isBlank(jldtxb.getId())) {
            String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            jldtxb.setCreTime(creTime);
            jldtxb.setCreUserId(UserUtil.getCruUserId());
            jldtxb.setCreUserName(UserUtil.getCruUserName());
            jldtxb.setCreDeptId(UserUtil.getCruDeptId());
            jldtxb.setCreDeptName(UserUtil.getCruDeptName());
            jldtxb.setUpdateTime(creTime);
            jldtxb.setUpdateUserId(UserUtil.getCruUserId());
            jldtxb.setUpdateUserName(UserUtil.getCruUserName());
            jldtxb = jldtxbDao.save(jldtxb);
        } else {
            Jldtxb oldJldtxb = jldtxbDao.findOne(jldtxb.getId());
            oldJldtxb.setUpdateUserId(UserUtil.getCruUserId());
            oldJldtxb.setUpdateUserName(UserUtil.getCruUserName());
            String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            oldJldtxb.setUpdateTime(creTime);
            oldJldtxb.setName(jldtxb.getName());
            oldJldtxb.setPartyGovernment(jldtxb.getPartyGovernment());
            oldJldtxb.setPrivateNetwork(jldtxb.getPrivateNetwork());
            oldJldtxb.setUniversalNetwork(jldtxb.getUniversalNetwork());
            oldJldtxb.setPhone(jldtxb.getPhone());
            jldtxb = jldtxbDao.save(oldJldtxb);
        }
        return jldtxb;
    }

    @Override
    public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String name, String unitName) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append("	from Jldtxb t ");
        querySql.append("	where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(StringUtils.isNotEmpty(name)){
            querySql.append(" and t.name like ?");
            para.add("%" + name + "%");
        }
        //拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.creTime desc) ");
        }else{
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }
        Page<Jldtxb> page = jldtxbDao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        List<Jldtxb> content = page.getContent();
        for (Jldtxb jldtxb : content) {
            jldtxb.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

    @Override
    public Jldtxb getById(String id) {
        return jldtxbDao.findOne(id);
    }

    @Override
    public int deleteOne(String id) {
        int result = 0;
        String jpql = "update Jldtxb t set t.visible = ? where t.id = ?";
        try {
            result = jldtxbDao.update(jpql, CommonConstants.VISIBLE[0], id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}