package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;

public interface DataDictionarysDao extends BaseRepository<DataDictionarys, String> ,  DataDictionarysRepository {

//	@Modifying
//	@Query("update DataDictionarys t set t.mark = ?2 where t.visible = '1' and t.type = '1' and t.mark = ?1")
//	public void updateMark(String oldMark, String newMark);

	@Query("select count(*) from DataTable t where t.visible = '1' and  t.nodeId = ?1")
	public int countForItem(String id);

	@Modifying
	@Query("update DataDictionarys t set t.visible = '0' where t.id = ?1")
	public int delVisible(String id);

	@Query(" from DataDictionarys t where t.treeType=?1 and t.type = ?2 and  t.pId is null  and t.creJuId = ?3 and t.visible = '1' order by t.sort")
	public List<DataDictionarys> getDicByMark(String treeType,String type,String creJuId);
	
	/**
	 * 根据treeType查询资料树，资料学习用
	 * @author 王磊
	 * @Date 2018年9月11日 下午2:08:03
	 * @param treeType
	 * @return
	 */
	@Query(" from DataDictionarys t where t.treeType=?1 and t.pId is null and t.visible = '1' order by t.sort")
	public List<DataDictionarys> getTreeType(String treeType);
	
	@Query(" from DataDictionarys t where t.pId = ?1 and t.type = ?2 and t.visible = '1' order by t.sort")
	public List<DataDictionarys> getDicByPid(String pId,String type);

	@Query(" from DataDictionarys t where t.type = '0' and t.visible = '1' order by t.sort")
	public List<DataDictionarys> getAllDicType();

//	@Query(" from DataDictionarys t where t.treetype = ?1 and t.type = '1' and t.visible = '1' and t.flag = '1' order by t.sort")
//	public List<DataDictionarys> getListByMark(String treetype);
//	
	@Query(" from DataDictionarys t where t.pId = ?1 and t.type = '0' and t.visible = '1' and t.flag = '1' order by t.sort")
	public List<DataDictionarys> getTypeListByPid(String pid);

//	@Query(" from DataDictionarys t where t.pId is null and t.cruJuId like '%?1%' and t.treeType like '%?2%' and t.visible = '1' and t.type ='0'")
//	public List<DataDictionarys> getBuMenTree(String cruJuId, String treeType);



}
