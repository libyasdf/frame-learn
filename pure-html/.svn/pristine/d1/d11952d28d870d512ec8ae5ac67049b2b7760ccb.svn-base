package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.services;

import java.util.List;

import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;




/**
 * TODO 数据字典Service
 * @author 周俊林
 * @Date 2018年4月9日 上午9:37:34
 */
public interface DataDictionarysService {

	List<DataDictionarys> list(DataDictionarys dic) throws Exception;

	DataDictionarys save(DataDictionarys dic);

	DataDictionarys update(DataDictionarys dic);

	int deleteType(String id);

	int deleteDic(String id);

	boolean canDel(String id);

	boolean checkDic(DataDictionarys dic,String checkItem);

	DataDictionarys getDictById(String id);

	List<DataDictionarys> getListByMark(String mark,String type) throws Exception;

	void sort(String[] ids);
	
	int getIsPid(String id);
	
	void updatetreeSort(String[] ids,String dropId,String pId);
	
	/**
	 * TODO 递归查询指定treeType类型下的所有字典类型
	 * @author 李利广
	 * @Date 2018年6月22日 下午1:36:59
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	List<DataDictionarys> getTypeListByMark(String treeType, String creDeptId, String pId) throws Exception;
	
	List<DataDictionarys> getLearningTimeTypeListByMark(String treeType, String creDeptId, String pId) throws Exception;

	/**
	 * 
	 * @param dic
	 * @return
	 * @throws Exception
	 * @author 颜振兴
	 * List<DataDictionarys>
	 * TODO 获取部门的树
	 */
	List<DataDictionarys> bumenlist(DataDictionarys dic) throws Exception;
	/**
	 * 学习时长统计树
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月4日 上午10:42:29
	 * @param dic
	 * @return
	 * @throws Exception
	 */
	List<DataDictionarys> learningTimeTreeList(DataDictionarys dic) throws Exception;

	/**
	 * 
	 * @param id
	 * @return
	 * @author 颜振兴
	 * List<DataDictionarys>
	 * TODO 获取树父级节点的名字
	 */
	List<DataDictionarys> getPname(String id);

	/**
	 * 
	 * @param dic
	 * @return
	 * @throws Exception
	 * @author 颜振兴
	 * List<DataDictionarys>
	 * TODO 获取部门及其他三颗树
	 */
	List<DataDictionarys> bumenSjList(DataDictionarys dic) throws Exception;

	/**
	 * 
	 * @param dic
	 * @return
	 * @throws Exception
	 * @author 颜振兴
	 * List<DataDictionarys>
	 * TODO 获取部门sj的树列表
	 */
	List<DataDictionarys> getBuMenSjList(DataDictionarys dic) throws Exception;
}
