package com.sinosoft.sinoep.user.controller;

import com.sinosoft.sinoep.user.service.TreeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 树的扩展controller
 *
 * @author 张卜亢
 * @date 2018 -03-20 14:43:00
 */
@RestController
@RequestMapping("tree")
public class TreeController {

    @Resource
    private TreeService treeService;

    /**
     * 只显示当前用户的父部门和父部门人员，
     * 和本部门和子部门，以及本部门人员和子部门人员
     *
     * @return
     * @author 张卜亢
     * @date 2018 -03-20 14:43:00
     */
    @RequestMapping("getTreeOnlyParentAndChild")
    public List<Map<Object, Object>> getTreeOnlyParentAndChild() {
        List<Map<Object, Object>> treeNodes = treeService.getTreeByDeptid(null);
        return treeNodes;
    }
}
