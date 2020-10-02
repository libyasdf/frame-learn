package com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.services.ContrastService;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.dao.DaglCategoryTableDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.dao.PreferencesDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DaglCategoryTable;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.PartyNumRule;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DATreeVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 王富康
 * @Description //TODO 系统配置-Service实现层
 * @Date 2018/11/8 19:37
 * @Param
 * @return
 **/
@Service
public class PreferencesServceImpl implements PreferencesService{

    @Autowired
    private PreferencesDao preferencesDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DaglCategoryTableDao daglCategoryTableDao;

     @Autowired
    private ContrastService contrastService;

    /**
     * @Author 王富康
     * @Description //TODO 门类列表查询
     * @Date 2018/11/12 9:30
     * @Param []
     * @return java.util.List
     **/
    @Override
    public List<DaglCategoryTable> getCategoryListData(String tName) {

    	//防止sql注入 王磊 2019年4月26日
    	List<Object> paraList = new ArrayList<>();
        String sql="select t.* from dagl_category_table t where t.visible = '"+CommonConstants.VISIBLE[1]+ "'" ;
        if(StringUtils.isNotBlank(tName)){
            sql += " and t.category_code =? ";
            paraList.add(tName);
        }
        sql += " order by t.cre_time asc";
        List<DaglCategoryTable> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DaglCategoryTable.class),paraList.toArray());
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类代号查询最底层表的字段(不包括已配置的字段)
     * @Date 2018/11/12 15:12
     * @Param [category_code]
     * @return java.util.List
     **/
    @Override
    public List<TableStructDescription> getRuleColumn(String category_code) {

        String sql=     "select t.*\n" +
                        "  from table_struct_description t\n" +
                        " where t.table_name =\n" +
                        "       (select x.s_table_name as u\n" +//表名变为大写
                        "          from (\n" +
                        //"                --获取最底层的表名\n" +
                        "                SELECT y.*\n" +
                        "                  FROM ( \n" +//--根据门类代码获取树形列表
                        "                         select s.s_table_name\n" +
                        "                           from tables_relation s\n" +
                        "                          start with s.m_table_name = ? \n" +
                        "                         connect by prior s.s_table_name = s.m_table_name\n" +
                        "                          order by rownum desc) y\n" +
                        "                 where instr(y.s_table_name, 'dak') = 0 \n" +
                        "                   and rownum = 1) x)\n" +
                        "   and t.column_name not in ('recid')\n" +//去掉系统唯一关键字
                        "   and t.column_name not in\n" +
                        "       (SELECT n.rule_field \n" +//字段名变为大写
                        "          FROM dagl_party_num_rule n \n" +
                        "         WHERE n.category_id =\n" +
                        "               (SELECT m.id\n" +
                        "                  FROM dagl_category_table m\n" +
                        "                 WHERE m.category_code = ? ))" +
                        "   and t.column_visible = 'T'";

        List<TableStructDescription> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TableStructDescription.class),category_code,category_code);
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类id获取档号规则
     * @Date 2018/11/12 19:47
     * @Param [category_code]
     * @return java.util.List
     **/
    @Override
    public List<PartyNumRule> getRule(String category_id,String order_num) {

    	//防止sql注入 王磊 2019年4月26日
    	List<Object> paraList = new ArrayList<>();
        String sql= "   SELECT *\n" +
                        "   FROM dagl_party_num_rule t\n" +
                        "   WHERE t.category_id = ? ";
        paraList.add(category_id);
        if(StringUtils.isNotBlank(order_num)){
            sql +=" and t.order_num = ? ";
            paraList.add(order_num);
        }
        sql += "   order by to_number(t.order_num)";
        List<PartyNumRule> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(PartyNumRule.class),paraList.toArray());
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增档号规则数据
     * @Date 2018/11/13 10:18
     * @Param [list]
     * @return void
     **/
    @Override
    public List<PartyNumRule> save(List<PartyNumRule> ruleList){

        List<PartyNumRule> partyNumRules = new ArrayList<>();

        for(int i = 0;i < ruleList.size();i++){
            if(!StringUtils.isNotBlank(ruleList.get(i).getId())){
                List<PartyNumRule> list = getRule(ruleList.get(i).getCategoryId(),"");
                int num = list.size()+1;
                //id为空，则证明是新增
                ruleList.get(i).setCreUserId(UserUtil.getCruUserId());
                ruleList.get(i).setCreUserName(UserUtil.getCruUserName());
                ruleList.get(i).setCreDeptId(UserUtil.getCruDeptId());
                ruleList.get(i).setCreDeptName(UserUtil.getCruDeptName());
                ruleList.get(i).setCreChushiId(UserUtil.getCruChushiId());
                ruleList.get(i).setCreChushiName(UserUtil.getCruChushiName());
                ruleList.get(i).setCreJuId(UserUtil.getCruJuId());
                ruleList.get(i).setCreJuName(UserUtil.getCruJuName());
                ruleList.get(i).setCreTime(JDateToolkit.getNowDate4());
                ruleList.get(i).setOrderNum(num+"");

                PartyNumRule newDut = preferencesDao.save(ruleList.get(i));

                partyNumRules.add(newDut);
                //根据门类id获取下边的表
                String getCategoryIdSql = "select t.* from dagl_category_table t where t.id = ?";
                List<DaglCategoryTable> categoryTableList = jdbcTemplate.query(getCategoryIdSql,new BeanPropertyRowMapper<>(DaglCategoryTable.class),ruleList.get(i).getCategoryId());
                for(int j = 0;j<categoryTableList.size();j++){
                    String categoryCode = categoryTableList.get(j).getCategoryCode();
                    //根据门类名称获取表名
                    String queryTableNameSql ="select s.*\n" +
                                    "  from tables_relation s\n" +
                                    " start with s.m_table_name = ?\n" +
                                    "connect by prior s.s_table_name = s.m_table_name\n" +
                                    " order by rownum asc";
                    List<Map<String,Object>> pTableList = jdbcTemplate.queryForList(queryTableNameSql,categoryCode);
                    for(int n=0;n<pTableList.size();n++){
                        Map<String, Object> relationMap = pTableList.get(n);
                        //子表字段
                        String sColName = String.valueOf(relationMap.get("S_TABLE_NAME"));
                        //设置表描述值继承为否
                        contrastService.updateColumnInheritByTableAndColumnName(sColName,ruleList.get(i).getRuleField());
                    }
                }
            }

        }
        return partyNumRules;
    }

    /**
     * @Author 王富康
     * @Description //TODO 隐藏组成项
     * @Date 2018/11/20 14:24
     * @Param [ruleIds]
     * @return void
     **/
    @Override
    public void delete(List<PartyNumRule> ruleList){

        for(int i = ruleList.size(); i > 0;i--){

            //修改顺序
            String jpql = "update dagl_party_num_rule t set t.order_num = t.order_num-1 where t.category_id = ? and to_number(t.order_num) > ?";
            int updatePartyNumRule = jdbcTemplate.update(jpql, ruleList.get(i-1).getCategoryId(),ruleList.get(i-1).getOrderNum());

            //删除
            preferencesDao.delete(ruleList.get(i-1).getId());
        }

    }

    /**
     * @Author 王富康
     * @Description //TODO 获取档案树
     * @Date 2018/11/17 11:49
     * @Param []
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DATreeVo>
     **/
    @Override
    public List<DATreeVo> findTree() {
        List<DaglCategoryTable> doorList = daglCategoryTableDao.DoorList();
        String sql ="SELECT t.m_table_name   as pid,\n" +
                        "       t.s_table_name   as id,\n" +
                        "       s.table_chn_name as name\n" +
                        "  FROM tables_relation t, table_description s\n" +
                        " WHERE instr(t.s_table_name, 'dak') = 0\n" +
                        "   and t.s_table_name = s.table_name";

        List<DATreeVo> findTree = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DATreeVo.class));

        List<DATreeVo> list = new ArrayList<>();
        for (DaglCategoryTable categoryTable : doorList) {
            DATreeVo class1 = new DATreeVo();
            class1.setName(categoryTable.getCategoryName());
            class1.setpId("");
            class1.setId(categoryTable.getCategoryCode());
            list.add(class1);
        }
        list.addAll(findTree);

        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据id修改修改档号规则的连接符和长度
     * @Date 2018/11/19 20:28
     * @Param [partyNumRule]
     * @return int
     **/
    @Override
    public int updatePartyNumRule(PartyNumRule partyNumRule){
        String jpql = "update dagl_party_num_rule t set t.connector=?,t.numb_length=? where t.id = ?";
        int updatePartyNumRule = jdbcTemplate.update(jpql, partyNumRule.getConnector(),partyNumRule.getNumbLength(),partyNumRule.getId());
        return updatePartyNumRule;
    }

    /**
     * @Author 王富康
     * @Description //TODO 上移档号规则
     * @Date 2018/11/20 9:49
     * @Param [partyNumRule]
     * @return int
     **/
    @Override
    public int moveUp(PartyNumRule partyNumRule){
        String orderNum = Integer.parseInt(partyNumRule.getOrderNum())-1+"";
        List<PartyNumRule> lastList = getRule(partyNumRule.getCategoryId(),orderNum);

        String jpql = "update dagl_party_num_rule t set t.order_num = t.order_num-1 where t.id = ?";
        int updatePartyNumRule = jdbcTemplate.update(jpql,partyNumRule.getId());

        String lastJpql = "update dagl_party_num_rule t set t.order_num = t.order_num+1 where t.id = ?";
        int updatePartyNumRule1 = jdbcTemplate.update(lastJpql,lastList.get(0).getId());

        return updatePartyNumRule;
    }

    /**
     * @Author 王富康
     * @Description //TODO 下移档号规则
     * @Date 2018/11/20 9:49
     * @Param [partyNumRule]
     * @return int
     **/
    @Override
    public int moveDown(PartyNumRule partyNumRule){

        String orderNum = Integer.parseInt(partyNumRule.getOrderNum())+1+"";
        List<PartyNumRule> lastList = getRule(partyNumRule.getCategoryId(),orderNum);

        String jpql = "update dagl_party_num_rule t set t.order_num = t.order_num+1 where t.id = ?";
        int updatePartyNumRule = jdbcTemplate.update(jpql,partyNumRule.getId());

        String nextJpql = "update dagl_party_num_rule t set t.order_num = t.order_num-1 where t.id = ?";
        int updatePartyNumRule1 = jdbcTemplate.update(nextJpql,lastList.get(0).getId());

        return updatePartyNumRule;
    }
}
