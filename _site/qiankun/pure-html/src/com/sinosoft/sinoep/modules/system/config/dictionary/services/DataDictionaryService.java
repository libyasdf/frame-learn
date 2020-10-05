package com.sinosoft.sinoep.modules.system.config.dictionary.services;

import java.util.List;

import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;


/**
 * TODO 数据字典Service
 * @author 周俊林
 * @Date 2018年4月9日 上午9:37:34
 */
public interface DataDictionaryService {

	List<DataDictionary> list(DataDictionary dic) throws Exception;

	DataDictionary save(DataDictionary dic);

	DataDictionary update(DataDictionary dic);

	int deleteType(String id);

	/**
	 * 逻辑删除字典项
	 * @param id
	 * @return
	 */
	int deleteDic(String id);

	/**
	 * 判断字典类型是否可删除
	 * @param id
	 * @param mark
	 * @return
	 */
	boolean canDel(String id,String mark);

	/**
	 * TODO 校验字典名、字典值不可重复
	 * @author 李利广
	 * @Date 2018年7月10日 下午8:13:25
	 * @param dic
	 * @param checkItem
	 * @return
	 */
	boolean checkDic(DataDictionary dic,String checkItem);

	/**
	 * 根据ID获取一条字典数据
	 * @param id
	 * @return
	 */
	DataDictionary getDictById(String id);

	/**
	 * 根据唯一码值查找字典项
	 * @param mark
	 * @return
	 */
	List<DataDictionary> getListByMark(String mark,String type) throws Exception;

	/**
	 * 字典项排序
	 * TODO 
	 * @author 李利广
	 * @Date 2018年4月27日 上午11:25:04
	 * @param id
	 */
	void sort(String[] ids);
	
	/**
	 * 拖拽数据字典排序
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月24日 下午3:30:43
	 * @param ids
	 * @param dropId
	 * @param pId
	 */
	void updatetreeSort(String[] ids,String dropId,String pId);
	
	/**
	 * TODO 递归查询指定mark类型下的所有字典类型
	 * @author 李利广
	 * @Date 2018年6月22日 下午1:36:59
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	List<DataDictionary> getTypeListByMark(String mark) throws Exception;

}
