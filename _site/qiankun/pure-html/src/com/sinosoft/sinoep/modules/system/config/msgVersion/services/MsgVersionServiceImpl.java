package com.sinosoft.sinoep.modules.system.config.msgVersion.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.msgVersion.dao.MsgVersionDao;
import com.sinosoft.sinoep.modules.system.config.msgVersion.entity.MsgVersion;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 消息客户端版本管理业务实现类
 * @author 李利广
 * @Date 2019年03月08日 20:11:00
 */
@Service
public class MsgVersionServiceImpl implements MsgVersionService {

    @Autowired
    private MsgVersionDao versionDao;

    /**
     * TODO 根据版本号获取客户端更新信息，如果版本号为空，则获取最新客户端
     * @author 李利广
     * @Date 2019年03月08日 20:39:29
     * @param version 版本号（1.4.0）
     * @return com.sinosoft.sinoep.modules.system.config.msgVersion.entity.MsgVersion
     */
    @Override
    public MsgVersion getMsgByVersion(String version) throws Exception{
        List listzwf=new ArrayList();
        StringBuilder hql = new StringBuilder("from MsgVersion t ");
        hql.append(" where t.status = '1'");
        if (StringUtils.isNotBlank(version)){
            hql.append(" and t.version = ? ");
            listzwf.add(version);
        }
        hql.append(" order by t.first+0 desc,t.second+0 desc,t.three+0 desc ");
        Query query = versionDao.getEntityManager().createQuery(hql.toString());
        for(int i=0;i<listzwf.size();i++){
            query.setParameter((i+1),listzwf.get(i));//为问号赋占位符
        }
        List<MsgVersion> list = query.getResultList();
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * TODO 保存版本信息
     * @author 李利广
     * @Date 2019年03月08日 22:40:56
     * @param msgVersion
     * @return net.sf.json.JSONObject
     */
    @Override
    public MsgVersion save(MsgVersion msgVersion){
        if (StringUtils.isNotBlank(msgVersion.getId())){
            //修改
            MsgVersion oldVersion = versionDao.findOne(msgVersion.getId());
            oldVersion.setContent(msgVersion.getContent());
            oldVersion.setVersion(msgVersion.getVersion());
            if(StringUtils.isNotBlank(msgVersion.getVersion())) {
                String[] vers = msgVersion.getVersion().split("\\.");
                if (vers.length == 3){
                    oldVersion.setFirst(vers[0]);
                    oldVersion.setSecond(vers[1]);
                    oldVersion.setThree(vers[2]);
                    oldVersion = versionDao.save(oldVersion);
                    return oldVersion;
                }else {
                    return null;
                }
            }
        }else{
            //新增
            msgVersion.setCreUserId(UserUtil.getCruUserId());
            msgVersion.setCreUserName(UserUtil.getCruUserName());
            msgVersion.setCreDeptId(UserUtil.getCruDeptId());
            msgVersion.setCreDeptName(UserUtil.getCruDeptName());
            msgVersion.setCreJuId(UserUtil.getCruJuId());
            msgVersion.setCreJuName(UserUtil.getCruJuName());
            msgVersion.setStatus("1");
            if(StringUtils.isNotBlank(msgVersion.getVersion())){
                String[] vers = msgVersion.getVersion().split("\\.");
                if (vers.length == 3){
                    msgVersion.setThree(vers[2]);
                    msgVersion.setSecond(vers[1]);
                    msgVersion.setFirst(vers[0]);
                    msgVersion = versionDao.save(msgVersion);
                    return msgVersion;
                }else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * TODO 删除版本信息
     * @author 李利广
     * @Date 2019年03月08日 22:40:56
     * @param id
     * @return net.sf.json.JSONObject
     */
    @Override
    public Boolean deleteVersion(String id){
        try{
            MsgVersion version = versionDao.findOne(id);
            version.setStatus("0");
            versionDao.save(version);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * TODO 查询版本信息列表
     * @author 李利广
     * @Date 2019年03月09日 10:00:14
     * @param pageable
     * @param pageImpl
     * @param msgVersion
     * @param startDate
     * @param endDate
     * @return net.sf.json.JSONObject
     */
    @Override
    public PageImpl getVersionList(Pageable pageable, PageImpl pageImpl, MsgVersion msgVersion, String startDate, String endDate) throws Exception{
        pageImpl.setFlag("0");
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        List listzwf=new ArrayList();
        querySql.append("from MsgVersion t where t.status = '1'");
        if (StringUtils.isNotBlank(msgVersion.getVersion())){
            querySql.append(" and t.version like ? ");
            listzwf.add("%"+msgVersion.getVersion()+"%");
        }
        if (StringUtils.isNotBlank(msgVersion.getContent())){
            querySql.append(" and t.content like ? ");
            listzwf.add("%"+msgVersion.getContent()+"%");
        }
        if (StringUtils.isNotBlank(startDate)){
            querySql.append(" and t.creTime >= ? ");
            listzwf.add(startDate);
        }
        if (StringUtils.isNotBlank(endDate)){
            querySql.append(" and t.creTime <= ? ");
            listzwf.add(endDate);
        }

        //拼接排序语句,默认以版本号倒序排列
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.first+0 desc,t.second+0 desc,t.three+0 desc ");
        }else if("version".equals(pageImpl.getSortName())){
            querySql.append("  order by t.first+0 "+pageImpl.getSortOrder()+",t.second+0 "+pageImpl.getSortOrder()+",t.three+0 "+pageImpl.getSortOrder());

        }else{
            querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
        }
        Page<MsgVersion> page = versionDao.query(querySql.toString(),pageable,listzwf.toArray());
        pageImpl.setFlag("1");
        pageImpl.getData().setRows(page.getContent());
        pageImpl.getData().setTotal((int)page.getTotalElements());
        return pageImpl;
    }

    /**
     * TODO 根据ID查询一条数据
     * @author 李利广
     * @Date 2019年03月09日 10:00:14
     * @param id
     * @return net.sf.json.JSONObject
     */
    @Override
    public MsgVersion getById(String id) throws Exception {
        return versionDao.findOne(id);
    }

}
