package com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.services;

import com.fr.web.core.A.O;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.bmgl.services.BmglService;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.dao.ShoppingTrolleyDao;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 王富康
 * @Description //TODO 收集列表Serivce实现层
 * @Date 2019/2/18 14:56
 * @Param
 * @return
 **/
@Service
public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ShoppingTrolleyDao shoppingTrolleyDao;

    @Autowired
    private BmglService bmglService;

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 查询收集列表内容（管理员）
     * @Date 2019/2/18 15:17
     * @Param [pageable, pageImpl, daglShoppingTrolley]
     **/
    @Override
    public PageImpl getShoppingTrolleyData(Pageable pageable, PageImpl pageImpl, DaglShoppingTrolley daglShoppingTrolley) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append(" from DaglShoppingTrolley t where 1=1 ");

        querySql.append("   and t.creUserId = ?");//本人
        para.add("" + UserUtil.getCruUserId() + "");

        querySql.append("   and t.borrowUserId is null");//未发送的

        if (StringUtils.isNotBlank(daglShoppingTrolley.getMainTitle())) {
            querySql.append("   and t.mainTitle like ?");//题名
            para.add("%" + daglShoppingTrolley.getMainTitle() + "%");
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getLjdwMark())) {
            querySql.append("   and t.ljdwMark = ?");//立卷单位Mark
            para.add(daglShoppingTrolley.getLjdwMark());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getArchiveNo())) {
            querySql.append("   and t.archiveNo like ? ");//档号
            para.add("%" + daglShoppingTrolley.getArchiveNo() + "%");
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getCategoryName())) {
            querySql.append("   and t.categoryName like ?");//门类名称
            para.add("%" + daglShoppingTrolley.getCategoryName() + "%");
        }

        //拼接条件
        /*if () {//立卷单位id
            querySql.append("   and t.ljdwMark = ?");
            para.add(""+daglCateDeptPersonRelation.getLjdwMark()+"");
        }
*/
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.ljdwMark desc ) ");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
        }

        Page<DaglShoppingTrolley> page = shoppingTrolleyDao.query(querySql.toString(), pageable, para.toArray());
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    /**
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley>
     * @Author 王富康
     * @Description //TODO 查询收集列表数据（不分页）（管理员）
     * @Date 2019/2/19 9:54
     * @Param [daglCateDeptPersonRelation]
     **/
    @Override
    public List<DaglShoppingTrolley> getShoppingTrolleyListOfAdmin(DaglShoppingTrolley daglShoppingTrolley) {

        List<Object> para = new ArrayList<>();
        String sql = "select * from dagl_shopping_trolley t where 1=1 " +
                "   and t.cre_user_id = ? " +//本人
                "   and t.borrow_user_id is null  ";//未推送的
        para.add(UserUtil.getCruUserId());
        //判重条件
        if (StringUtils.isNotBlank(daglShoppingTrolley.getRecid())) {
            sql += "   and t.recid = ? ";//该id
            para.add(daglShoppingTrolley.getRecid());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getCategoryNo())) {
            sql += "   and t.category_no = ?";//该门类下的
            para.add(daglShoppingTrolley.getCategoryNo());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getTableName())) {
            sql += "   and t.table_name = ?";//该业务表下的
            para.add(daglShoppingTrolley.getTableName());
        }

        //查询项
        if (StringUtils.isNotBlank(daglShoppingTrolley.getMainTitle())) {
            sql += "   and t.main_title like ? ";//题名
            para.add("%" + daglShoppingTrolley.getMainTitle() + "%");
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getLjdwMark())) {
            sql += "   and t.ljdw_mark = ?";//立卷单位Mark
            para.add(daglShoppingTrolley.getLjdwMark());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getArchiveNo())) {
            sql += "   and t.archive_no like ? ";//档号
            para.add("%" + daglShoppingTrolley.getArchiveNo() + "%");
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getCategoryName())) {
            sql += "   and t.category_name like ? ";//门类名称
            para.add("%" + daglShoppingTrolley.getCategoryName() + "%");
        }

        List<DaglShoppingTrolley> daglShoppingTrolleys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglShoppingTrolley.class),para.toArray());

        return daglShoppingTrolleys;
    }

    /**
     * @return com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley
     * @Author 王富康
     * @Description //TODO 收集数据
     * @Date 2019/2/18 15:27
     * @Param [daglShoppingTrolley]
     **/
    @Override
    public List<Map<String, Object>> saveShoppingTrolley(String ids, String tName, String all, String ranking, String categoryCode, String categoryName) {

        List<DaglShoppingTrolley> successList = new ArrayList<>();
        List<DaglShoppingTrolley> existErrorList = new ArrayList<>();
        List<DaglShoppingTrolley> statusErrorList = new ArrayList<>();
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {

            //根据id和表名查询该数据
            List<Map<String, Object>> findById = bmglService.findById(tName, id[i]);
            DaglShoppingTrolley daglShoppingTrolley1 = new DaglShoppingTrolley();
            daglShoppingTrolley1.setRecid(id[i]);
            daglShoppingTrolley1.setCategoryNo(categoryCode);
            //daglShoppingTrolley1.setTableName();表code
            //查询是否已经存在
            List<DaglShoppingTrolley> existDaglShoppingTrolleys = getShoppingTrolleyListOfAdmin(daglShoppingTrolley1);

            DaglShoppingTrolley daglShoppingTrolley = new DaglShoppingTrolley();
            //null的判断
            if (findById.size() > 0) {
                daglShoppingTrolley.setRecid(id[i]);//档案唯一标识
                daglShoppingTrolley.setMainTitle(findById.get(0).get("maintitle") == null ? "" : findById.get(0).get("maintitle").toString());//题名
                daglShoppingTrolley.setBasefolderUnit(findById.get(0).get("basefolder_unit") == null ? "" : findById.get(0).get("basefolder_unit").toString());//立卷单位
                daglShoppingTrolley.setLjdwMark(findById.get(0).get("cre_chushi_id") == null ? "" : findById.get(0).get("cre_chushi_id").toString());//立卷单位id

                //案卷级档号不确定，根据关联关系取
                String dh = "archive_no";
                Map<String, Object> relation = bmglService.findDAGLByTableNameF(tName);
                if (null != relation) {
                    dh = relation.get("m_col_name") == null ? "" : relation.get("m_col_name").toString();
                }
                daglShoppingTrolley.setArchiveNo(findById.get(0).get(dh) == null ? "" : findById.get(0).get(dh).toString());//档号
                daglShoppingTrolley.setCategoryName(findById.get(0).get("leibiehao") == null ? "" : findById.get(0).get("leibiehao").toString());//门类名称
                daglShoppingTrolley.setCategoryNo(categoryCode);//门类code
                daglShoppingTrolley.setTableName(tName);//档案所在业务表
                daglShoppingTrolley.setSecurityClass(findById.get(0).get("security_class") == null ? "" : findById.get(0).get("security_class").toString());

                if ("0".equals(findById.get(0).get("borrow_status")) || null == findById.get(0).get("borrow_status")) {
                    //未借出
                    if (existDaglShoppingTrolleys.size() == 0) {
                        //不存在，新增

                        //基本数据
                        daglShoppingTrolley.setCreUserId(UserUtil.getCruUserId());
                        daglShoppingTrolley.setCreUserName(UserUtil.getCruUserName());
                        daglShoppingTrolley.setCreDeptId(UserUtil.getCruDeptId());
                        daglShoppingTrolley.setCreDeptName(UserUtil.getCruDeptName());
                        daglShoppingTrolley.setCreChushiId(UserUtil.getCruChushiId());
                        daglShoppingTrolley.setCreChushiName(UserUtil.getCruChushiName());
                        daglShoppingTrolley.setCreJuId(UserUtil.getCruJuId());
                        daglShoppingTrolley.setCreJuName(UserUtil.getCruJuName());
                        daglShoppingTrolley.setCreTime(JDateToolkit.getNowDate4());
                        String pageNum = "";
                        //获取页数
                        if("1".equals(ranking)){
                            if("1".equals(all) || "2".equals(all)){
                                //一层档案直接取，二层档案也直接取第一层的总页数
                                pageNum = findById.get(0).get("PAGE_NUM")==null?"":findById.get(0).get("PAGE_NUM").toString();
                            }else{
                                //三层的第一层获取底层页数，需要获取第二层的子表数据，页数相加
                                //获取子表表名
                                //查找
                                String pTableSql =
                                        "SELECT t.*\n" +
                                                "  FROM tables_relation t, table_description s\n" +
                                                " WHERE t.s_table_name = s.table_name\n" +
                                                "   and t.m_table_name = ?";//排除父节点为门类的数据

                                List<Map<String,Object>> pTableList = jdbcTemplate.queryForList(pTableSql,tName);
                                if(pTableList.size()>0) {
                                    //有父表，查询出父表的状态
                                    Map<String, Object> relationMap = pTableList.get(0);
                                    //父表表名
                                    String sTableName = String.valueOf(relationMap.get("S_TABLE_NAME"));
                                    //父表字段
                                    String mColName = String.valueOf(relationMap.get("M_COL_NAME"));
                                    //子表字段
                                    String sColName = String.valueOf(relationMap.get("S_COL_NAME"));
                                    String sColData = findById.get(0).get(mColName.toUpperCase()).toString();
                                    //查询子表数据
                                    String sTableSql =	"select *\n" +
                                            "  from "+sTableName+"\n" +
                                            " where 1 = 1\n" +
                                            "   and archive_flag = '"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"'\n" +
                                            "   and "+sColName+" = '"+sColData+"'\n" ;
                                    if(tName.indexOf("_dak")==-1){
                                        //单位预立库根据立卷单位和创建人查询,并且是未归档的数据
                                        sTableSql += "   and (ARCHIVE_ENTITY_STATUS != '"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[2] +"' or ARCHIVE_ENTITY_STATUS is null)\n"+
                                                "   and cre_user_id = '"+findById.get(0).get("CRE_USER_ID")+"'\n" +
                                                "   and cre_chushi_id = '"+findById.get(0).get("CRE_CHUSHI_ID")+"'\n" ;
                                    }
                                    List<Map<String,Object>> sTableList = jdbcTemplate.queryForList(sTableSql);
                                    int page_num = 0;
                                    for(int j=0;j<sTableList.size();j++){
                                        page_num += Integer.parseInt(sTableList.get(j).get("PAGE_NUM")==null?"0":sTableList.get(j).get("PAGE_NUM").toString());
                                    }
                                    pageNum = ""+page_num;
                                }
                            }
                        }else{
                            pageNum = findById.get(0).get("PAGE_NUM")==null?"":findById.get(0).get("PAGE_NUM").toString();
                        }
                        daglShoppingTrolley.setPageNum(pageNum);
                        DaglShoppingTrolley daglShoppingTrolleyOfNew = shoppingTrolleyDao.save(daglShoppingTrolley);

                        successList.add(daglShoppingTrolleyOfNew);

                    } else {
                        //存在，放入错误list
                        existErrorList.add(existDaglShoppingTrolleys.get(0));
                    }
                } else {
                    //部分借出，借出，空
                    statusErrorList.add(daglShoppingTrolley);
                }

            } else {
                //未查询到数据
            }

        }

        Map<String, Object> zongMap = new HashMap<String, Object>();
        zongMap.put("successList", successList);
        zongMap.put("existErrorList", existErrorList);
        zongMap.put("statusErrorList", statusErrorList);//NotAssociatedList
        List<Map<String, Object>> zongList = new ArrayList();
        zongList.add(zongMap);
        return zongList;

    }

    /**
     * @return int
     * @Author 王富康
     * @Description //TODO 删除收集列表数据（管理员）
     * @Date 2019/2/18 15:28
     * @Param []
     **/
    @Override
    public int deleteShoppingTrolley(String ShoppingTrolleyIds) {
        List<Object> para = new ArrayList<>();
        //删除分三种情况；1.单个，或者批量删除收集列表；2.清空收集列表；3.导入到档案利用时清空收集列表
        //1.单个，或者批量删除收集列表；(根据id删除收集列表)
        String jpql = "delete from  DaglShoppingTrolley t where 1 = 1 "
                + "	and t.creUserId = '" + UserUtil.getCruUserId() + "'"//当前登录人的
                + "	and t.creDeptId = '" + UserUtil.getCruDeptId() + "'" //该部门下的
                + "  and t.borrowUserId is null ";//未推送的


        if (StringUtils.isNotBlank(ShoppingTrolleyIds)) {

            /*StringBuilder pIds = new StringBuilder();
            String[] pId = ShoppingTrolleyIds.split(",");
            for (String i : pId) {
                pIds.append("'" + i + "',");
            }
            String ids = pIds.substring(0, pIds.length() - 1);*/
            String inString = CommonUtils.solveSqlInjectionOfIn(ShoppingTrolleyIds,para);
            jpql += "	and t.id in (" + inString + ")";
        }

        return shoppingTrolleyDao.update(jpql,para.toArray());
    }

    /**
     * @return int
     * @Author 王富康
     * @Description //TODO 推送收集数据（管理员）
     * @Date 2019/2/25 17:50
     * @Param [borrowUserId, borrowUserName]
     **/
    @Override
    public List<Map<String, Object>> sendShoppingTrolley(String borrowUserId, String borrowUserName) {

        List<DaglShoppingTrolley> successList = new ArrayList<>();
        List<DaglShoppingTrolley> existErrorList = new ArrayList<>();

        List<DaglShoppingTrolley> daglShoppingTrolleys = getShoppingTrolleyListOfAdmin(new DaglShoppingTrolley());

        for (int i = 0; i < daglShoppingTrolleys.size(); i++) {

            DaglShoppingTrolley daglShoppingTrolley = daglShoppingTrolleys.get(i);

            DaglShoppingTrolley PcDaglShoppingTrolley = new DaglShoppingTrolley();//判重查询的实体
            PcDaglShoppingTrolley.setRecid(daglShoppingTrolley.getRecid());
            PcDaglShoppingTrolley.setTableName(daglShoppingTrolley.getTableName());
            PcDaglShoppingTrolley.setCategoryNo(daglShoppingTrolley.getCategoryNo());
            PcDaglShoppingTrolley.setBorrowUserId(borrowUserId);

            List<DaglShoppingTrolley> existDaglShoppingTrolley = getShoppingTrolleyListOfUser(PcDaglShoppingTrolley);
            if (existDaglShoppingTrolley.size() > 0) {
                existErrorList.add(existDaglShoppingTrolley.get(0));
            } else {
                daglShoppingTrolley.setBorrowUserId(borrowUserId);
                daglShoppingTrolley.setBorrowUserName(borrowUserName);
                DaglShoppingTrolley daglShoppingTrolley1 = shoppingTrolleyDao.save(daglShoppingTrolley);
                successList.add(daglShoppingTrolley1);
            }
        }

        Map<String, Object> zongMap = new HashMap<String, Object>();
        zongMap.put("successList", successList);
        zongMap.put("existErrorList", existErrorList);
        List<Map<String, Object>> zongList = new ArrayList();
        zongList.add(zongMap);
        return zongList;

    }

    /**
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley>
     * @Author 王富康
     * @Description //TODO 查询收集列表数据（不分页）（借阅人）
     * @Date 2019/2/26 9:29
     * @Param [recid, categoryno]
     **/
    @Override
    public List<DaglShoppingTrolley> getShoppingTrolleyListOfUser(DaglShoppingTrolley daglShoppingTrolley) {

        List<Object> para = new ArrayList<>();
        String sql = "select * from dagl_shopping_trolley t where 1=1 ";

        if (StringUtils.isNotBlank(daglShoppingTrolley.getBorrowUserId())) {
            sql += "   and t.borrow_user_id = ? ";//根据推送人查询，判重
            para.add(daglShoppingTrolley.getBorrowUserId());
        } else {
            sql += "   and t.borrow_user_id = ? ";//本人
            para.add(UserUtil.getCruUserId());
        }


        if (StringUtils.isNotBlank(daglShoppingTrolley.getRecid())) {
            sql += "   and t.recid = ? ";//该id
            para.add(daglShoppingTrolley.getRecid());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getCategoryNo())) {
            sql += "   and t.category_no = ? ";//该门类
            para.add(daglShoppingTrolley.getCategoryNo());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getTableName())) {
            sql += "   and t.table_name = ? ";//该业务表下的
            para.add(daglShoppingTrolley.getTableName());
        }

        //查询项
        if (StringUtils.isNotBlank(daglShoppingTrolley.getMainTitle())) {
            sql += "   and t.main_title like ? ";//题名
            para.add("%" + daglShoppingTrolley.getMainTitle() + "%");
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getLjdwMark())) {
            sql += "   and t.ljdw_mark = ?";//立卷单位Mark
            para.add(daglShoppingTrolley.getLjdwMark());
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getArchiveNo())) {
            sql += "   and t.archive_no like ? ";//档号
            para.add("%" + daglShoppingTrolley.getArchiveNo() + "%");
        }

        if (StringUtils.isNotBlank(daglShoppingTrolley.getCategoryName())) {
            sql += "   and t.category_name like ? ";//门类名称
            para.add("%" + daglShoppingTrolley.getCategoryName() + "%");
        }

        List<DaglShoppingTrolley> daglShoppingTrolleys = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglShoppingTrolley.class),para.toArray());

        return daglShoppingTrolleys;
    }

    /**
     * @return int
     * @Author 王富康
     * @Description //TODO 删除收集列表数据（借阅人）
     * @Date 2019/2/26 17:18
     * @Param [ShoppingTrolleyIds]
     **/
    @Override
    public int deleteShoppingTrolleyOfUser(String ShoppingTrolleyIds) {
        List<Object> para = new ArrayList<>();
        //删除分三种情况；1.单个，或者批量删除收集列表；2.清空收集列表；3.导入到档案利用时清空收集列表
        //1.单个，或者批量删除收集列表；(根据id删除收集列表)
        String jpql = "delete from  DaglShoppingTrolley t where 1 = 1 "
                + "	and t.borrowUserId = ? ";//当前登录人的
        para.add(UserUtil.getCruUserId());
        if (StringUtils.isNotBlank(ShoppingTrolleyIds)) {

            /*StringBuilder pIds = new StringBuilder();
            String[] pId = ShoppingTrolleyIds.split(",");
            for (String i : pId) {
                pIds.append("'" + i + "',");
            }
            String ids = pIds.substring(0, pIds.length() - 1);*/
            String inString = CommonUtils.solveSqlInjectionOfIn(ShoppingTrolleyIds,para);
            jpql += "	and t.id in (" + inString + ")";
        }

        return shoppingTrolleyDao.update(jpql,para.toArray());
    }


    /**
     * @Auther:邴秀慧
     * @Description:借阅人删除收集数据，根据档案的唯一标识和表名（用于借阅发送时，删除收集列表的数据）
     * @Date:2019/3/5 21:01
     */
    @Override
    public int deleteShoppingTrolleyOfUserByRecidAndTableName(String recid, String tableName) {
        List<Object> para = new ArrayList<>();
        String jpql = "delete from  DaglShoppingTrolley t where 1 = 1 "
                + "	and t.borrowUserId = ? ";//当前登录人的
        para.add(UserUtil.getCruUserId());
        if (StringUtils.isNotBlank(recid)) {
            jpql += "	and t.recid = ? ";
            para.add(recid);
        }
        if (StringUtils.isNotBlank(tableName)){
            jpql += "	and t.tableName = ? ";
            para.add(tableName);
        }
        return shoppingTrolleyDao.update(jpql,para.toArray());
    }

    /**
     * @Author 王富康
     * @Description //TODO 删除档案时，同时删除收集列表的数据
     * @Date 2019/4/11 9:31
     * @Param [recid, tableName]
     * @return int
     **/
    @Override
    public int deleteShoppingTrolleyByRecidAndTableName(String recid, String tableName) {
        List<Object> para = new ArrayList<>();
        String inString  = CommonUtils.solveSqlInjectionOfIn(recid,para);
        para.add(tableName);
        String jpql = "delete from  DaglShoppingTrolley t where 1 = 1 and t.recid in (" + inString + ") and t.tableName = ? ";
        return shoppingTrolleyDao.update(jpql,para.toArray());
    }

}
