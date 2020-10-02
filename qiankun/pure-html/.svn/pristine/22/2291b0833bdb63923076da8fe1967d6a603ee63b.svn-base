package com.sinosoft.sinoep.user.service;

import com.sinosoft.sinoep.user.dao.TreeDao;
import com.sinosoft.sinoep.user.entity.SysFlowDept;
import com.sinosoft.sinoep.user.entity.SysFlowUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TreeServiceImpl实现类
 *
 * @author 张卜亢
 * @date 2018 -03-20 10:30:55
 */
@Service
public class TreeServiceImpl implements TreeService {

    @Resource
    private TreeDao treeDao;

    private List<Map<Object, Object>> sysFlowDeptToTreeNode(List<SysFlowDept> sysFlowDepts) {
        List<Map<Object, Object>> treeNodes = new ArrayList<>();
        for (int i = 0; i < sysFlowDepts.size(); i++) {
            Map<Object, Object> treeNode = new HashMap<>();
            SysFlowDept sysFlowDept = sysFlowDepts.get(i);
            treeNode.put("id", sysFlowDept.getDeptid());
            treeNode.put("name", sysFlowDept.getDeptname());
            treeNode.put("open", true);
            List<Map<Object, Object>> sysUsers = new ArrayList<>();
            for (SysFlowUser sysFlowUser : sysFlowDept.getSysFlowUsers()) {
                Map<Object, Object> sysUser = new HashMap<>();
                sysUser.put("id", sysFlowUser.getUserid());
                sysUser.put("name", sysFlowUser.getUsername());
                sysUsers.add(sysUser);
            }
            if (sysFlowDept.getChildSysFlowDepts().size() > 0) {
                sysUsers.addAll(sysFlowDeptToTreeNode(
                        sysFlowDept.getChildSysFlowDepts()));
                treeNode.put("children", sysUsers);
            } else {
                treeNode.put("children", sysUsers);
            }
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    /**
     * 将dao传过来的树数据转换位前端用的数据
     *
     * @param deptid
     * @return
     * @author 张卜亢
     * @date 2018 -03-20 10:29:38
     */
    @Override
    public List<Map<Object, Object>> getTreeByDeptid(Long deptid) {
        List<SysFlowDept> sysFlowDepts = treeDao.getTreeByDeptid(deptid);
        return sysFlowDeptToTreeNode(sysFlowDepts);
    }
}
