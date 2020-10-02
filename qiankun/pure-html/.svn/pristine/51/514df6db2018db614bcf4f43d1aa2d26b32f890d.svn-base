package com.sinosoft.sinoep.modules.system.config.dictionary.dao;

import java.util.List;

import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;

public interface DataDictionaryDao extends BaseRepository<DataDictionary, String>, DataDictionaryRepository {

	@Modifying
	@Query("update DataDictionary t set t.mark = ?2 where t.visible = '1' and t.type = '1' and t.mark = ?1")
	public void updateMark(String oldMark, String newMark);

	//不用
	@Query("select count(*) from DataDictionary t where t.visible = '1' and t.type = '1' and t.mark = ?1")
	public int countForItem(String mark);

	//不用
	@Modifying
	@Query("update DataDictionary t set t.visible = '0' where t.id = ?1")
	public int delVisible(String id);

	//不用
	@Query(" from DataDictionary t where t.mark = ?1 and t.type = ?2 and t.visible = '1' order by t.sort")
	public List<DataDictionary> getAllDicByMark(String mark,String type);
	
	//用
	@Query(" from DataDictionary t where t.mark = ?1 and t.type = ?2 and t.visible = '1' and t.flag = '1' order by t.sort")
	public List<DataDictionary> getDicByMark(String mark,String type);

	//不用
	@Query(" from DataDictionary t where t.pId = ?1 and t.type = ?2 and t.visible = '1' order by t.sort")
	public List<DataDictionary> getDicByPid(String pId,String type);
	
	//不用
	@Query(" from DataDictionary t where t.type = '0' and t.visible = '1' order by t.sort")
	public List<DataDictionary> getAllDicType();

	//需要
	@Query(" from DataDictionary t where t.mark = ?1 and t.type = '1' and t.visible = '1' and t.flag = '1' order by t.sort")
	public List<DataDictionary> getListByMark(String mark);
	
	//需要
	@Query(" from DataDictionary t where t.pId = ?1 and t.type = '0' and t.visible = '1' and t.flag = '1' order by t.sort")
	public List<DataDictionary> getTypeListByPid(String pid);

}
