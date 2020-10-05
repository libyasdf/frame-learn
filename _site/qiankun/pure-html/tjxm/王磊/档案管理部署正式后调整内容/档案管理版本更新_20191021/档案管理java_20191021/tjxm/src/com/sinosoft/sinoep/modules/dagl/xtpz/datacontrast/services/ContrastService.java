package com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.services;

import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.DataContrast;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 数据对照Service层
 * @Date 2018/11/13 20:14
 * @Param
 * @return
 **/
public interface ContrastService {

    /**
     * @Author 王富康
     * @Description //TODO 查询数据对照表信息
     * @Date 2018/11/13 20:58
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    List<ContrastingRelations> getContrastingRelations(ContrastingRelations contrastingRelations);

    /**
     * @Author 王富康
     * @Description //TODO 根据id获取一条关系信息
     * @Date 2018/11/20 17:06
     * @Param [id]
     * @return com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations
     **/
    ContrastingRelations findById(String id);

    /**
     * @Author 王富康
     * @Description //TODO 删除关系信息
     * @Date 2018/11/20 20:49
     * @Param [ruleList]
     * @return net.sf.json.JSONObject
     **/
    void delete(List<ContrastingRelations> RelationsList);

    /**
     * @Author 王富康
     * @Description //TODO 查询指定id的对照详细信息
     * @Date 2018/11/13 21:00
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    List<DataContrast> getDataContrast(DataContrast dataContrast);

    /**
     * @Author 王富康
     * @Description //TODO 根据表名获取表的字段相关信息
     * @Date 2018/11/14 9:53
     * @Param [tableName]
     * @return java.util.List
     **/
    List<TableStructDescription> getColumns(String tableName, String column_name, String contrastingId);

    /**
     * @Author 王富康
     * @Description //TODO 新增关系对照关系
     * @Date 2018/11/14 11:58
     * @Param [contratingRelations]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations>
     **/
    List<ContrastingRelations> saveContratingRelations(ContrastingRelations contratingRelations);

    /**
     * @Author 王富康
     * @Description //TODO 根据id修改对应关系
     * @Date 2018/11/14 16:09
     * @Param [dataContrast]
     * @return int
     **/
    int updateDataContrast(DataContrast dataContrast);

    /**
     * @Author 王富康
     * @Description //TODO 根据关联id获取目标表未做关联的，并且该字段不能为空的字段。
     * @Date 2018/11/19 11:52
     * @Param [tableName, column_name]
     * @return net.sf.json.JSONObject
     **/
    List<TableStructDescription> getNotNullData(String id);

    /**
     * @Author 王富康
     * @Description //TODO 根据表关系id，目标字段名称获取一条字段对应关系
     * @Date 2018/11/28 15:35
     * @Param [contrastingId, targetColumn]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.DataContrast>
     **/
    List<DataContrast> getcontrastData(String contrastingId,String targetColumn);

    /**
     * @Author 王富康
     * @Description //TODO 添加档号规则组成项时更新表描述中字段值继承为否
     * @Date 2019/10/17 11:36
     * @Param [tableName, column_name]
     * @return void
     **/
    void updateColumnInheritByTableAndColumnName(String tableName, String column_name);
}
