package com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.services;

import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DATreeVo;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DaglCategoryTable;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.PartyNumRule;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 系统配置-Service层
 * @Date 2018/11/8 19:38
 * @Param
 * @return
 **/
public interface PreferencesService {

    /**
     * @Author 王富康
     * @Description //TODO 门类列表查询
     * @Date 2018/11/12 9:28
     * @Param []
     * @return java.util.List
     **/
    List<DaglCategoryTable> getCategoryListData(String tName);

    /**
     * @Author 王富康
     * @Description //TODO 根据门类代号查询最底层表的字段(不包括已配置的字段)
     * @Date 2018/11/12 15:12
     * @Param [category_code]
     * @return java.util.List
     **/
    List<TableStructDescription> getRuleColumn(String category_code);

    /**
     * @Author 王富康
     * @Description //TODO 根据门类id查询档号规则
     * @Date 2018/11/12 19:46
     * @Param [category_code]
     * @return java.util.List
     **/
    List<PartyNumRule> getRule(String category_id,String order_num);

    /**
     * @Author 王富康
     * @Description //TODO 添加档号规则字段
     * @Date 2018/11/12 21:13
     * @Param [category_id, rule_field, rule_name, connector]
     * @return void
     **/
    List<PartyNumRule> save(List<PartyNumRule> ruleList);

    /**
     * @Author 王富康
     * @Description //TODO 删除档号规则的某些字段
     * @Date 2018/11/13 10:59
     * @Param [ruleIds]
     * @return int
     **/
    void delete(List<PartyNumRule> ruleList);

    /**
     * @Author 王富康
     * @Description //TODO 获取档案树
     * @Date 2018/11/17 11:48
     * @Param []
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DATreeVo>
     **/
    List<DATreeVo> findTree();

    /**
     * @Author 王富康
     * @Description //TODO 根据id修改修改档号规则的连接符和长度
     * @Date 2018/11/19 20:28
     * @Param [partyNumRule]
     * @return int
     **/
    int updatePartyNumRule(PartyNumRule partyNumRule);

    /**
     * @Author 王富康
     * @Description //TODO 上移档号规则
     * @Date 2018/11/20 9:45
     * @Param [partyNumRule]
     * @return int
     **/
    int moveUp(PartyNumRule partyNumRule);

    /**
     * @Author 王富康
     * @Description //TODO 下移档号规则
     * @Date 2018/11/20 9:45
     * @Param [partyNumRule]
     * @return int
     **/
    int moveDown(PartyNumRule partyNumRule);
}
