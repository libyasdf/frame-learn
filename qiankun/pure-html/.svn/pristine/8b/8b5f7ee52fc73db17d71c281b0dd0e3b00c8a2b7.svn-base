package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.dao;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;

public interface DataDictionarysRepository {

    List<DataDictionarys> getAllChildTypes(String id);
  //获取统计时长左侧树
    List<DataDictionarys> getLearningTimeTreeAllChildTypes(String id);
    
    List<DataDictionarys> getAllpTypes(String id);

    int delTypeVisible(String id);
    
    int findPid(String id);
	
    List<DataDictionarys> getDicByType(String treeType, String type);

    /*
     * 获取部门子集
     */
    List<DataDictionarys> getBuMenTree(String cruJuId, String treeType);
    
    /**
     * 根据id获得当前类别类型
     * @author 王磊
     * @Date 2018年9月5日 上午11:41:12
     * @param id
     * @return treeType
     */
    String getTreeTypeById(String id);
    
    /**
     * 根据id和tableName查询该类型在相关的业务表中是否有数据
     * @author 王磊
     * @Date 2018年9月5日 下午12:09:06
     * @param id
     * @param tableName
     * @return
     */
    int getCountByIdAndTreeType(String id,String tableName);
}
