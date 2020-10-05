package com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.dao.ContrastDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.DataContrast;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import workflow.common.JDateToolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 王富康
 * @Description //TODO 数据对照Service实现层
 * @Date 2018/11/13 20:14
 * @Param
 * @return
 **/
@Service
public class ContrastServiceImpl implements ContrastService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ContrastDao contrastDao;

    /**
     * @Author 王富康
     * @Description //TODO 查询数据对照表信息
     * @Date 2018/11/13 20:58
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    @Override
    public List<ContrastingRelations> getContrastingRelations(ContrastingRelations contrastingRelations){

        String sql="select t.* from dagl_contrasting_relations t where t.visible = '"+CommonConstants.VISIBLE[1]+ "' ";

        ///防止sql注入 王磊 2019年4月26日
        List<Object> paraList = new ArrayList<>();
        if(StringUtils.isNotBlank(contrastingRelations.getId())){
            //根据id获取一条数据
            sql +=" and t.id = ? ";
            paraList.add(contrastingRelations.getId());
        }

        if(StringUtils.isNotBlank(contrastingRelations.getSourceName())){
            //如果源表name不为空name就是查询源表name为contrastingRelations.getSourceName()的关系
            sql +=" and t.source_name = ? ";
            paraList.add(contrastingRelations.getSourceName());
        }

        if(StringUtils.isNotBlank(contrastingRelations.getTargetName())){
            //如果目标表name不为空name就是查询目标表name为contrastingRelations.getContrastName()的关系
            sql +=" and t.target_name = ? ";
            paraList.add(contrastingRelations.getTargetName());
        }
        //如果contrastingRelations.getSourceName()和contrastingRelations.getContrastName()都不为空，
        // 那么就可以根据源表跟目标表的name锁定一条对应关系

        if(StringUtils.isNotBlank(contrastingRelations.getContrastName())){
            //根据关系名称查询
            sql +=" and t.contrast_name like ? ";
            paraList.add("%"+contrastingRelations.getContrastName()+"%");
        }
        sql +=" order by t.cre_time desc";
        List<ContrastingRelations> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ContrastingRelations.class),paraList.toArray());
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据id获取一条关系信息
     * @Date 2018/11/20 17:07
     * @Param [id]
     * @return com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations
     **/
    @Override
    public ContrastingRelations findById(String id){
        return contrastDao.findOne(id);
    }

    /**
     * @Author 王富康
     * @Description //TODO 删除关系信息
     * @Date 2018/11/20 20:49
     * @Param [ruleList]
     * @return net.sf.json.JSONObject
     **/
    @Override
    public void delete(List<ContrastingRelations> RelationsList){

        for(int i = 0 ;i < RelationsList.size(); i++){
            //删除对照关系
            String jpql = "delete from dagl_data_contrast t  where t.contrasting_id = ?";
            int deleteNum = jdbcTemplate.update(jpql, RelationsList.get(i).getId());
            //删除关联关系
            contrastDao.delete(RelationsList.get(i).getId());
        }
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询指定id的对照详细信息
     * @Date 2018/11/13 21:13
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    @Override
    public List<DataContrast> getDataContrast(DataContrast dataContrast){

        String sql="select t.* from dagl_data_contrast t where t.contrasting_id = ? order by t.target_column asc";

        List<DataContrast> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataContrast.class),dataContrast.getContrastingId());
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据表名获取表的字段相关信息
     * @Date 2018/11/14 9:53
     * @Param [tableName]
     * @return java.util.List
     **/
    @Override
    public List<TableStructDescription> getColumns(String tableName, String column_name, String contrastingId){

        String sql="select t.*\n" +
                        "  from table_struct_description t\n" +
                        " where t.table_name = ? \n" +
                        "   and t.column_name not in ('basefolder_unit','recid','id')\n";
        List<Object> paraList = new ArrayList<>();
        paraList.add(tableName);
        if(StringUtils.isNotBlank(column_name)){
            sql += "and t.column_name = ? ";
            paraList.add(column_name);
        }
        List<TableStructDescription> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TableStructDescription.class),paraList.toArray());
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增关系对照关系
     * @Date 2018/11/14 14:37
     * @Param [contratingRelations]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations>
     **/
    @Override
    public List<ContrastingRelations> saveContratingRelations(ContrastingRelations contratingRelations){

        List<ContrastingRelations> ContrastingRelationsR = new ArrayList<>();

        if(StringUtils.isNotBlank(contratingRelations.getId())){
            //这里因为获取不到源门类和目标门类的信息，所以重新查询一遍
            ContrastingRelations oldcontratingRelations = findById(contratingRelations.getId());
            //修改，只修改关系名称
            if(StringUtils.isNotBlank(contratingRelations.getSourceName())){
                //如果源门类的英文名不为空，证明用户做了修改，把数据放进去
                oldcontratingRelations.setSourceName(contratingRelations.getSourceName());
                oldcontratingRelations.setSourceChnName(contratingRelations.getSourceChnName());

                //删除所有对应关系，并重新插入新的源门类字段
                //删除
                //防止sql注入 王磊 2019年4月26日
                String deleteSql = "delete from dagl_data_contrast t where t.contrasting_id = ? ";
                jdbcTemplate.update(deleteSql,contratingRelations.getId());

                //查询并插入
                List<TableStructDescription> sourcecolumns = getColumns(contratingRelations.getSourceName(),"","");
                //往字段对应表中添加源表的数据
                for(int i = 0; i < sourcecolumns.size();i++){
                	//防止sql注入 王磊 2019年4月26日
                    String uuid = UUID.randomUUID().toString();
                    String sql = "insert into dagl_data_contrast (id,contrasting_id, source_column,source_type,source_length,source_column_chn_name) " +
                            "       values (?,?,?,?,?,?)";
                    jdbcTemplate.update(sql,uuid,contratingRelations.getId(),sourcecolumns.get(i).getColumnName(),sourcecolumns.get(i).getColumnType(),sourcecolumns.get(i).getColumnWidth(),sourcecolumns.get(i).getColumnChnName());
                }
            }

            if(StringUtils.isNotBlank(contratingRelations.getTargetName())){
                //如果目标门类的英文名不为空，证明用户做了修改，把数据放进去
                oldcontratingRelations.setSourceName(contratingRelations.getTargetName());
                oldcontratingRelations.setSourceChnName(contratingRelations.getTargetChnName());

                //清除目标表的数据
                String deleteTargetDataSql ="UPDATE dagl_data_contrast t\n" +
                                "   SET t.target_column          = '',\n" +
                                "       t.target_type            = '',\n" +
                                "       t.target_length          = '',\n" +
                                "       t.target_column_chn_name = ''\n" +
                                " WHERE t.contrasting_id = ? ";


                jdbcTemplate.update(deleteTargetDataSql,contratingRelations.getId());
            }

            oldcontratingRelations.setContrastName(contratingRelations.getContrastName());
            oldcontratingRelations.setUpdateTime(JDateToolkit.getNowDate4());
            oldcontratingRelations.setUpdateUserId(UserUtil.getCruUserId());
            oldcontratingRelations.setUpdateUserName(UserUtil.getCruUserName());

            ContrastingRelations contratingRelation = contrastDao.save(oldcontratingRelations);

            //放到新的list中返回去
            ContrastingRelationsR.add(contratingRelation);

        }else{

            //新增
            contratingRelations.setCreUserId(UserUtil.getCruUserId());
            contratingRelations.setCreUserName(UserUtil.getCruUserName());
            contratingRelations.setCreDeptId(UserUtil.getCruDeptId());
            contratingRelations.setCreDeptName(UserUtil.getCruDeptName());
            contratingRelations.setCreChushiId(UserUtil.getCruChushiId());
            contratingRelations.setCreChushiName(UserUtil.getCruChushiName());
            contratingRelations.setCreJuId(UserUtil.getCruJuId());
            contratingRelations.setCreJuName(UserUtil.getCruJuName());
            contratingRelations.setVisible(CommonConstants.VISIBLE[1]);
            contratingRelations.setCreTime(JDateToolkit.getNowDate4());

            //保存
            ContrastingRelations contratingRelation = contrastDao.save(contratingRelations);

            //放到新的list中返回去
            ContrastingRelationsR.add(contratingRelation);
            //新增对照关系后对应的把源表的字段新增到字段对应表中，之后提供目标表的字段，用户自己去对应字段
            //得到源表字段
            List<TableStructDescription> sourcecolumns = getColumns(contratingRelations.getSourceName(),"","");
            //往字段对应表中添加源表的数据
            for(int i = 0; i < sourcecolumns.size();i++){
            	//防止sql注入 王磊 2019年4月26日
                String uuid = UUID.randomUUID().toString();
                String sql = "insert into dagl_data_contrast (id,contrasting_id, source_column,source_type,source_length,source_column_chn_name) " +
                        "       values (?,?,?,?,?,?)";
                jdbcTemplate.update(sql,uuid,contratingRelation.getId(),sourcecolumns.get(i).getColumnName(),sourcecolumns.get(i).getColumnType(),sourcecolumns.get(i).getColumnWidth(),sourcecolumns.get(i).getColumnChnName());
            }
        }

        return ContrastingRelationsR;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据id修改对应关系
     * @Date 2018/11/14 16:09
     * @Param [dataContrast]
     * @return int
     **/
    @Override
    public int updateDataContrast(DataContrast dataContrast) {

        String targetType = "";
        String targetLength = "";
        String targetColumnChnName = "";
        int updateDuty = 0;
        //获取目标的表名
        ContrastingRelations cr = new ContrastingRelations();
        cr.setId(dataContrast.getContrastingId());
        List<ContrastingRelations> crs = getContrastingRelations(cr);

        if(crs.size()>0&&StringUtils.isNotBlank(dataContrast.getTargetColumn())){
            String targetName = crs.get(0).getTargetName();//目标表的表名
            //得到源表字段
            List<TableStructDescription> sourcecolumns = getColumns(targetName,dataContrast.getTargetColumn(),"");
            targetType = sourcecolumns.get(0).getColumnType();
            targetLength = sourcecolumns.get(0).getColumnWidth();
            targetColumnChnName = sourcecolumns.get(0).getColumnChnName();

            //删除之前目标字段为选中的字段的数据
            String deleteSql = "update dagl_data_contrast t set t.target_column=?,t.target_type=?,t.target_length=?,t.target_column_chn_name=? where t.contrasting_id = ? and t.target_column = ?";
            int deleteData = jdbcTemplate.update(deleteSql, "","","","",dataContrast.getContrastingId(),dataContrast.getTargetColumn());
            //更新
            String jpql = "update dagl_data_contrast t set t.target_column=?,t.target_type=?,t.target_length=?,t.target_column_chn_name=? where t.id = ?";
            updateDuty = jdbcTemplate.update(jpql, dataContrast.getTargetColumn(),targetType,targetLength,targetColumnChnName,dataContrast.getId());
        }

        if(crs.size()>0 && !(StringUtils.isNotBlank(dataContrast.getTargetColumn()))){
            //这里是选择则的空时
            String jpql = "update dagl_data_contrast t set t.target_column=?,t.target_type=?,t.target_length=?,t.target_column_chn_name=? where t.id = ?";
            updateDuty = jdbcTemplate.update(jpql, dataContrast.getTargetColumn(),targetType,targetLength,targetColumnChnName,dataContrast.getId());
        }

        return updateDuty;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据关联id获取目标表未做关联的，并且该字段不能为空的字段。
     * @Date 2018/11/19 11:52
     * @Param [tableName, column_name]
     * @return net.sf.json.JSONObject
     **/
    @Override
    public List<TableStructDescription> getNotNullData(String id){
        String sql =
                "select t.*\n" +
                        "  from table_struct_description t\n" +
                        " where t.table_name =\n" +
                        "       (SELECT t.target_name\n" +
                        "          FROM dagl_contrasting_relations t\n" +
                        "         WHERE t.id = ? )\n" +
                        "   and t.column_name not in\n" +
                        "       (SELECT s.target_column\n" +
                        "          FROM dagl_data_contrast s\n" +
                        "         WHERE s.contrasting_id = ? \n" +
                        "           and s.target_column is not null) \n" +//--所有关联的字段
                        "   and t.column_can_null = 'F'";

        List<TableStructDescription> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TableStructDescription.class),id,id);
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据表关系id，目标字段名称获取一条字段对应关系
     * @Date 2018/11/28 15:36
     * @Param [contrastingId, targetColumn]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.DataContrast>
     **/
    @Override
    public List<DataContrast> getcontrastData(String contrastingId,String targetColumn){

    	//防止sql注入 王磊 2019年4月26日
        String sql = "select * from dagl_data_contrast t where t.contrasting_id=? and t.target_column=?";
        List<DataContrast> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DataContrast.class),contrastingId,targetColumn);
        return list;
    }

}
