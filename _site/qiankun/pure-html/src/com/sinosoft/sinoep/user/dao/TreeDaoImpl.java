package com.sinosoft.sinoep.user.dao;

import com.sinosoft.sinoep.user.entity.SysFlowDept;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门人员树扩展查询筛选实现类
 *
 * @author 张卜亢
 * @date 2018 -03-19 21:29:55
 */
@Repository
public class TreeDaoImpl implements TreeDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    /**
     * 根据部门ID查询部门人员
     * 如果ID为null，则将自己父部门的ID作为根，
     * 查询父部门的人员、本部门的人和子部门以及子部门的人和部门
     *
     * @param deptid
     * @return
     * @author 张卜亢
     * @date 2018 -03-19 21:26:23
     */
    @Override
    public List<SysFlowDept> getTreeByDeptid(final Long deptid) {
        if (deptid == null) {
            String defDeptid = UserUtil.getCruDeptId().split(",")[0];
            final Long thisDeptid = Long.parseLong(defDeptid);
            final List<SysFlowDept> thisSysFlowDepts = hibernateTemplate.execute(new HibernateCallback<List<SysFlowDept>>() {
                @Override
                public List<SysFlowDept> doInHibernate(Session session) throws HibernateException {
                    Query query = session.createQuery("from SysFlowDept where deptid=:deptid");
                    query.setLong("deptid", thisDeptid);
                    List<SysFlowDept> sysFlowDepts = query.list();
                    return sysFlowDepts;
                }
            });

            for (int i = 0; i < thisSysFlowDepts.size(); i++) {
                thisSysFlowDepts.get(i).setChildSysFlowDepts(
                        getTreeByDeptid(thisSysFlowDepts.get(i).getDeptid()));
            }

            List<SysFlowDept> parentSysFlowDepts = hibernateTemplate.execute(new HibernateCallback<List<SysFlowDept>>() {
                @Override
                public List<SysFlowDept> doInHibernate(Session session) throws HibernateException {
                    Query query = session.createQuery("from SysFlowDept where deptid=:deptid");
                    query.setLong("deptid", thisSysFlowDepts.get(0).getSuperId());
                    List<SysFlowDept> sysFlowDepts = query.list();
                    return sysFlowDepts;
                }
            });

            if (parentSysFlowDepts.size() > 0) {
                parentSysFlowDepts.get(0).setChildSysFlowDepts(thisSysFlowDepts);
                return parentSysFlowDepts;
            }
            return thisSysFlowDepts;
        } else {
            List<SysFlowDept> childSysFlowDepts = hibernateTemplate.execute(new HibernateCallback<List<SysFlowDept>>() {
                @Override
                public List<SysFlowDept> doInHibernate(Session session) throws HibernateException {
                    Query query = session.createQuery("from SysFlowDept where superId=:superId");
                    query.setLong("superId", deptid);
                    List<SysFlowDept> list = query.list();
                    return list;
                }
            });
            for (int i = 0; i < childSysFlowDepts.size(); i++) {
                childSysFlowDepts.get(i).setChildSysFlowDepts(
                        getTreeByDeptid(childSysFlowDepts.get(i).getDeptid()));
            }
            return childSysFlowDepts;
        }


//        Long thisDeptid; //定义根节点
//        if (deptid == null) { //如果没有传deptid，就将本部门的父部门作为跟节点，不过不加载里面的其他部门
////            deptid = Long.parseLong(UserUtil.getCruDeptId());
//            Long childDeptid = deptid;
//            SysFlowDept parentSysFlowDept = hibernateTemplate.execute(new HibernateCallback<SysFlowDept>() {
//                @Override
//                public SysFlowDept doInHibernate(Session session) throws HibernateException {
//                    Query query = session.createQuery("from SysFlowDept d1 SysFlowDept d2 where d1.deptid=d2.superId and d1.deptid=:deptid");
//                    query.setLong("deptid", childDeptid);
//                    List<SysFlowDept> sysFlowDepts = query.list();
//                    if (sysFlowDepts.size() > 0) {
//                        return sysFlowDepts.get(0);
//                    }
//                    return null;
//                }
//            });
//            thisDeptid = parentSysFlowDept.getDeptid();
//        }else {
//            thisDeptid = deptid;
//        }
//        SysFlowDept thisSysFlowDept = hibernateTemplate.execute(new HibernateCallback<SysFlowDept>() {
//            @Override
//            public SysFlowDept doInHibernate(Session session) throws HibernateException {
//                Query query = session.createQuery("from SysFlowDept where deptid=:deptid");
//                query.setLong("deptid", thisDeptid);
//                List<SysFlowDept> sysFlowDepts = query.list();
//                if (sysFlowDepts.size() > 0) {
//                    return sysFlowDepts.get(0);
//                }
//                return null;
//            }
//        });
//
//        List<SysFlowDept> childSysFlowDepts = hibernateTemplate.execute(new HibernateCallback<List<SysFlowDept>>() {
//            @Override
//            public List<SysFlowDept> doInHibernate(Session session) throws HibernateException {
//                Query query = session.createQuery("from SysFlowDept where superId=:superId");
//                query.setLong("superId", thisSysFlowDept.getDeptid());
//                List<SysFlowDept> list = query.list();
//                return list;
//            }
//        });
//        thisSysFlowDept.getChildSysFlowDepts().addAll(childSysFlowDepts);
//        if (parentSysFlowDept != null) {
//            parentSysFlowDept.getChildSysFlowDepts().add(thisSysFlowDept);
//            return parentSysFlowDept;
//        } else {
//            return thisSysFlowDept;
//        }
    }
}
