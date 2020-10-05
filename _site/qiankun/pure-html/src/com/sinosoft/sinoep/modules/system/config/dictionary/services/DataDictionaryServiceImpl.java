package com.sinosoft.sinoep.modules.system.config.dictionary.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.system.config.dictionary.dao.DataDictionaryDao;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.util.DictionaryUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 数据字典
 * @author 周俊林
 * @Date 2018年4月9日 上午9:49:33
 */
@Service
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Autowired
	private DataDictionaryDao dao;

	/**
	 * TODO 查询列表
	 * @author 周俊林
	 * @Date 2018年4月9日 上午11:05:58
	 * @param dic
	 * @return
	 */
	@Override
	public List<DataDictionary> list(final DataDictionary dic) throws Exception {
		Sort sort = new Sort("sort","creTime");
		if ("0".equals(dic.getType()) && StringUtils.isNotBlank(dic.getMark())) {
			return getTypeListByMark(dic.getMark());
		}
		return dao.findAll(new Specification<DataDictionary>() {
			@Override
			public Predicate toPredicate(Root<DataDictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType =  cb.equal(root.get("type").as(String.class), dic.getType());
				predicates.add(pVisible);
				predicates.add(pType);
				Predicate pName = null,pCode = null;
				if (!StringUtils.isBlank(dic.getName())){
					pName = cb.like(root.get("name").as(String.class), "%"+dic.getName()+"%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(dic.getCode())){
					pCode = cb.equal(root.get("code").as(String.class), dic.getCode());
					predicates.add(pCode);
				}
				if (!StringUtils.isBlank(dic.getMark())) {
					Predicate pMark = cb.equal(root.get("mark").as(String.class), dic.getMark());
					predicates.add(pMark);
					return cb.and(predicates.toArray(new Predicate[0]));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}

	/**
	 * TODO 新增保存
	 * @author 周俊林
	 * @Date 2018年4月9日 下午3:21:39
	 * @param dic
	 * @return
	 */
	@Override
	public DataDictionary save(DataDictionary dic) {
		dic.setCreDeptId(UserUtil.getCruDeptId());
		dic.setCreDeptName(UserUtil.getCruDeptName());
		dic.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dic.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dic.setCreUserId(UserUtil.getCruUserId());
		dic.setCreUserName(UserUtil.getCruUserName());
		dic.setUpdateUserId(UserUtil.getCruUserId());
		dic.setUpdateUserName(UserUtil.getCruUserName());
		dic.setVisible("1");
		dic.setSort(DictionaryUtil.getSort(dic));// 初始顺序号
		return dao.saveAndFlush(dic);
	}

	/**
	 * TODO 修改保存
	 * @author 周俊林
	 * @Date 2018年4月9日 下午3:21:51
	 * @param dic
	 * @return
	 */
	@Override
	public DataDictionary update(DataDictionary dic) {
		DataDictionary old = dao.findOne(dic.getId());
		if ("0".equals(dic.getType())) {
			// 更新该字典类型下的所有字典项的mark字段
			dao.updateMark(old.getMark(), dic.getMark());
		}
		old.setCode(dic.getCode());
		old.setFlag(dic.getFlag());
		old.setMark(dic.getMark());
		old.setName(dic.getName());
		old.setRemark(dic.getRemark());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dao.saveAndFlush(old);
	}

	/**
	 * 逻辑删除字典类型
	 * @param id
	 * @return
	 */
	@Override
	public int deleteType(String id) {
		return dao.delTypeVisible(id);
	}

	/**
	 * 逻辑删除字典项
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDic(String id) {
		return dao.delVisible(id);
	}

	/**
	 * 判断字典类型是否可删除
	 * @param id
	 * @param mark
	 * @return
	 */
	@Override
	public boolean canDel(String id,String mark){
		boolean can = false;
		//判断该类型下是否有字典项
		if(countForItem(mark) == 0){//无字典项
			//判断该类型下是否有子类型
			List<DataDictionary> childTypes = hasChildType(id);
			if(childTypes.size() != 0){//有子类型
				//判断子类型下是否有字典项
				for (DataDictionary dic:childTypes ) {
					if(countForItem(dic.getMark()) != 0){//有字典项
						can = false;
						break;
					}
					can = true;
				}
			}else{//无子类型
				can = true;
			}
		}
		return can;
	}

	/**
	 * 判断该类型下是否有字典项
	 * @param mark
	 * @return
	 */
	private int countForItem(String mark) {
		return dao.countForItem(mark);
	}

	/**
	 * 判断该类型下是否有子类型
	 * @param id
	 * @return
	 */
	private List<DataDictionary> hasChildType(String id) {
		return dao.getAllChildTypes(id);
	}

	/**
	 * TODO 校验字典名、字典值不可重复
	 * @author 李利广
	 * @Date 2018年7月10日 下午8:13:25
	 * @param dic
	 * @param checkItem
	 * @return
	 */
	@Override
	public boolean checkDic(DataDictionary dic,String checkItem){
		boolean valid = false;
 		List<DataDictionary> dicts = new ArrayList<>();
		if("1".equals(dic.getType())){//字典值
			dicts = dao.getAllDicByMark(dic.getMark(),dic.getType());
			valid = compare(dicts,dic,checkItem);
		}else if("0".equals(dic.getType())){//字典类型
			dicts = dao.getDicByPid(dic.getpId(),dic.getType());
			valid = compare(dicts,dic,checkItem);
		}
		return valid;
	}

	/**
	 * TODO 比较字典名、字典值是否一样
	 * @author 李利广
	 * @Date 2018年7月10日 下午8:15:13
	 * @param dicts
	 * @param dic
	 * @param checkItem
	 * @return
	 */
	private boolean compare(List<DataDictionary> dicts,DataDictionary dic,String checkItem){
		if ("mark".equals(checkItem) && "0".equals(dic.getType())){//字典类型，校验mark值
			List<DataDictionary> types = dao.getAllDicType();
			if(types.size() > 0){
				for (DataDictionary type:types) {
					if (type.getMark().equals(dic.getMark()) && !type.getId().equals(dic.getId())){
						return false;
					}
				}
			}
		}else if(dicts.size() > 0){
			for (DataDictionary dict:dicts) {
				if("name".equals(checkItem) && dict.getName().equals(dic.getName()) && !dict.getId().equals(dic.getId())){
					return false;
				}else if ("code".equals(checkItem) && dict.getCode().equals(dic.getCode()) && !dict.getId().equals(dic.getId())){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 根据ID获取一条字典数据
	 * @param id
	 * @return
	 */
	@Override
	public DataDictionary getDictById(String id){
		return dao.findOne(id);
	}
	
	/**
	 * TODO 递归查询指定mark类型下的所有字典类型
	 * @author 李利广
	 * @Date 2018年6月22日 下午1:36:59
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataDictionary> getTypeListByMark(String mark) throws Exception{
		List<DataDictionary> dicList = new ArrayList<>();
		if (StringUtils.isNotBlank(mark)) {
			List<DataDictionary> typeList = dao.getAllDicByMark(mark, "0");
			if (typeList.size() > 0) {
				dicList = dao.getAllChildTypes(typeList.get(0).getId());
			}
		}
		return dicList;
	}

	/**
	 * 根据唯一码值查找字典项
	 * @param mark
	 * @return
	 */
	@Override
	public List<DataDictionary> getListByMark(String mark,String type) throws Exception{
		if("1".equals(type)){
			return dao.getListByMark(mark);
		}else if("0".equals(type)){
			List<DataDictionary> typeList = dao.getDicByMark(mark, type);
			return dao.getTypeListByPid(typeList.get(0).getId());
		}else{
			return null;
		}
	}

	/**
	 * 字典项排序
	 * TODO 
	 * @author 李利广
	 * @Date 2018年4月27日 上午11:25:04
	 * @param id
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			DataDictionary dic = dao.findOne(ids[i]);
			dic.setSort(i);
			dao.save(dic);
		}
	}
	
	/**
	 * 拖拽数据字典排序
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月24日 下午3:30:43
	 * @param ids
	 * @param dropId
	 * @param pId
	 */
	@Override
	public void updatetreeSort(String[] ids,String dropId,String pId) {
		//更新父id
		DataDictionary dataDictionary = dao.findOne(dropId);
		dataDictionary.setpId(pId);
		dao.save(dataDictionary);
		//更新sort值
		for (int i = 0; i < ids.length; i++) {
			DataDictionary dic = dao.findOne(ids[i]);
			dic.setSort(i);
			dao.save(dic);
		}
	}
	
}
