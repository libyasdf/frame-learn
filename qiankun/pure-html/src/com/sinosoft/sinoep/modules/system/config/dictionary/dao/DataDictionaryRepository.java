package com.sinosoft.sinoep.modules.system.config.dictionary.dao;

import java.util.List;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;

public interface DataDictionaryRepository {

	/**
     * 查询该类型下的所有子孙类型
     * @param id
     * @return
     */
    List<DataDictionary> getAllChildTypes(String id);

    /**
     * 逻辑删除该类型下的所有子孙类型
     * @param id
     * @return
     */
    int delTypeVisible(String id);
	
}
