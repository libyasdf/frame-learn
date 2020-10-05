package com.sinosoft.sinoep.modules.zhbg.xxkh.tree.services;

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
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.dao.DataDictionarysDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.entity.DataDictionarys;
import com.sinosoft.sinoep.modules.zhbg.xxkh.tree.util.DictionaryUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 数据字典
 * 
 * @author 周俊林
 * @Date 2018年4月9日 上午9:49:33
 */
@Service
@Transactional
public class DataDictionarysServiceImpl implements DataDictionarysService {

	@Autowired
	private DataDictionarysDao dao;

	/**
	 * TODO 查询列表
	 * 
	 * @author 周俊林
	 * @Date 2018年4月9日 上午11:05:58
	 * @param dic
	 * @return
	 */
	@Override
	public List<DataDictionarys> list(final DataDictionarys dic) throws Exception {
		Sort sort = new Sort("creTime");
		if ("1".equals(dic.getType())) {
			return getTypeListByMark(dic.getTreeType(), dic.getCreJuId(), dic.getpId());
		}
		return dao.findAll(new Specification<DataDictionarys>() {
			@Override
			public Predicate toPredicate(Root<DataDictionarys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType = cb.equal(root.get("type").as(String.class), dic.getType());
				predicates.add(pVisible);
				predicates.add(pType);
				Predicate pName = null, pCode = null, pTreeType = null;
				if (!StringUtils.isBlank(dic.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + dic.getName() + "%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(dic.getTreeType())) {
					pTreeType = cb.equal(root.get("treeType").as(String.class), dic.getTreeType());
					predicates.add(pTreeType);
				}
				if (!StringUtils.isBlank(dic.getCode())) {
					pCode = cb.equal(root.get("code").as(String.class), dic.getCode());
					predicates.add(pCode);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}

	/**
	 * TODO 新增保存
	 * 
	 * @author 周俊林
	 * @Date 2018年4月9日 下午3:21:39
	 * @param dic
	 * @return
	 */
	@Override
	public DataDictionarys save(DataDictionarys dic) {
		if (dic.getTreeType().equals("juj")) {
			dic.setTreeType(UserUtil.getCruJuId() + dic.getTreeType());
		}
		dic.setCreDeptId(UserUtil.getCruDeptId());
		dic.setCreDeptName(UserUtil.getCruDeptName());
		dic.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dic.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		dic.setCreUserId(UserUtil.getCruUserId());
		dic.setCreUserName(UserUtil.getCruUserName());
		dic.setUpdateUserId(UserUtil.getCruUserId());
		dic.setUpdateUserName(UserUtil.getCruUserName());
		dic.setCreJuId(UserUtil.getCruJuId());
		dic.setCreJuName(UserUtil.getCruJuName());
		dic.setVisible("1");
		dic.setTreeType(dic.getTreeType());
		dic.setSort(DictionaryUtil.getSort(dic));// 初始顺序号
		return dao.saveAndFlush(dic);
	}

	/**
	 * TODO 修改保存
	 * 
	 * @author 周俊林
	 * @Date 2018年4月9日 下午3:21:51
	 * @param dic
	 * @return
	 */
	@Override
	public DataDictionarys update(DataDictionarys dic) {
		DataDictionarys old = dao.findOne(dic.getId());

		old.setCode(dic.getCode());
		old.setFlag(dic.getFlag());
		old.setName(dic.getName());
		old.setRemark(dic.getRemark());
		old.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		old.setUpdateUserId(UserUtil.getCruUserId());
		old.setUpdateUserName(UserUtil.getCruUserName());
		return dao.saveAndFlush(old);
	}

	/**
	 * 逻辑删除字典类型
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteType(String id) {
		return dao.delTypeVisible(id);
	}

	/**
	 * 逻辑删除字典项
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteDic(String id) {
		return dao.delVisible(id);
	}

	/**
	 * 判断字典类型时候可删除
	 * 
	 * @param id
	 * @param mark
	 * @return
	 */
	@Override
	public boolean canDel(String id) {
		boolean can = false;
		// 当前所选id对应的treeType
		String treeType = dao.getTreeTypeById(id);
		// 判断该类型下是否有资料
		if (countForItem(id, treeType) == 0) {// 无资料
			// 判断该类型下是否有子类型
			List<DataDictionarys> childTypes = hasChildType(id);
			if (childTypes.size() != 0) {// 有子类型
				// 判断子类型下是否有资料
				for (DataDictionarys dic : childTypes) {
					if (countForItem(dic.getId(), treeType) != 0) {// 有资料
						can = false;
						break;
					}
					can = true;
				}
			} else {// 无子类型
				can = true;
			}
		}
		return can;
	}

	/**
	 * 判断该类型下是否有相关数据
	 * 
	 * @author 王磊
	 * @Date 2018年9月5日 上午11:50:17
	 * @param id
	 *            类型id
	 * @param treeType
	 * @return
	 */
	private int countForItem(String id, String treeType) {
		//部门试卷树的treeType类型为：二级局id+juj，这里单独处理下
		if(null != treeType && treeType.contains("juj")){
			treeType = "juj";
		}
		// 根据treeType获得业务表名称
		String tableName = XXKHCommonConstants.treeTypeReflectTable.get(treeType);
		return dao.getCountByIdAndTreeType(id, tableName);
	}

	/**
	 * 判断该类型下是否有子类型
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<DataDictionarys> hasChildType(String id) {
		return dao.getAllChildTypes(id);
	}

	@Override
	public boolean checkDic(DataDictionarys dic, String checkItem) {
		boolean valid = false;
		List<DataDictionarys> dicts = new ArrayList<>();
		if ("1".equals(dic.getType())) {// 字典值
			// dicts = dao.getDicByMark(dic.getMark(),dic.getType());
			valid = compare(dicts, dic, checkItem);
		} else if ("0".equals(dic.getType())) {// 字典类型
			dicts = dao.getDicByPid(dic.getpId(), dic.getType());
			valid = compare(dicts, dic, checkItem);
		}
		return valid;
	}

	private boolean compare(List<DataDictionarys> dicts, DataDictionarys dic, String checkItem) {
		if ("mark".equals(checkItem) && "0".equals(dic.getType())) {// 字典类型，校验mark值
			List<DataDictionarys> types = dao.getAllDicType();
			if (types.size() > 0) {
				// for (DataDictionarys type:types) {
				// if (type.getMark().equals(dic.getMark()) &&
				// !type.getId().equals(dic.getId())){
				// return false;
				// }
				// }
			}
		} else if (dicts.size() > 0) {
			for (DataDictionarys dict : dicts) {
				if ("name".equals(checkItem) && dict.getName().equals(dic.getName())
						&& !dict.getId().equals(dic.getId())) {
					return false;
				} else if ("code".equals(checkItem) && dict.getCode().equals(dic.getCode())
						&& !dict.getId().equals(dic.getId())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 根据ID获取一条字典数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public DataDictionarys getDictById(String id) {
		return dao.findOne(id);
	}

	/**
	 * TODO 递归查询指定mark类型下的所有字典类型
	 * 
	 * @author 李利广
	 * @Date 2018年6月22日 下午1:36:59
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataDictionarys> getTypeListByMark(String treeType, String creJuId, String pId) throws Exception {
		List<DataDictionarys> dicList = new ArrayList<>();
		if (StringUtils.isNotBlank(treeType)) {
			List<DataDictionarys> typeList = new ArrayList<DataDictionarys>();
			if(treeType.equals(XXKHCommonConstants.XXKH_TYPE[0]) || treeType.equals(XXKHCommonConstants.XXKH_TYPE[1]) || treeType.equals(XXKHCommonConstants.XXKH_TYPE[2])){
				//如果所查询的树是法制资料、保密资料、政治理论资料则只根据treeType进行查询即可
				typeList = dao.getTreeType(treeType);
			}else{
				typeList = dao.getDicByMark(treeType, "0", creJuId);
			}
			if (typeList.size() > 0) {
				dicList = dao.getAllChildTypes(typeList.get(0).getId());
			}
		}
		return dicList;
	}

	/**
	 * 本部门用来查树的/选试题试卷时 查所有的树
	 */
	public List<DataDictionarys> getTypeListByType(String treeType) throws Exception {
		List<DataDictionarys> dicList = new ArrayList<>();
		if (StringUtils.isNotBlank(treeType)) {
			if (treeType.equals(XXKHCommonConstants.TREE_TYPE_STATUS[2])) {
				List<DataDictionarys> typeList = dao.getDicByType(treeType, "0");
				for (int i = 0; i < typeList.size(); i++) {
					dicList.addAll(dao.getAllChildTypes(typeList.get(i).getId()));
				}
				return dicList;
			}
			if (treeType.equals(XXKHCommonConstants.TREE_TYPE_STATUS[1])) {
				List<DataDictionarys> typeList = dao.getDicByType(treeType, "0");
				for (int i = 0; i < typeList.size(); i++) {
					dicList.addAll(dao.getAllChildTypes(typeList.get(i).getId()));
				}
				return dicList;
			}
			String cruJuId = UserUtil.getCruJuId();
			List<DataDictionarys> typeList = dao.getBuMenTree(cruJuId, treeType);
			for (int i = 0; i < typeList.size(); i++) {
				dicList.addAll(dao.getAllChildTypes(typeList.get(i).getId()));
			}
		}
		return dicList;
	}

	/**
	 * 根据唯一码值查找字典项
	 * 
	 * @param mark
	 * @return
	 */
	@Override
	public List<DataDictionarys> getListByMark(String treetype, String type) throws Exception {
		// if("0".equals(type)){
		// //return dao.getListByMark(treetype);
		// return null;
		// }else if("0".equals(type)){
		//// List<DataDictionarys> typeList = dao.getDicByMark(treetype, type);
		// return dao.getTypeListByPid(typeList.get(0).getId());
		// }else{
		// return null;
		// }
		return null;
	}

	/**
	 * 字典项排序 TODO
	 * 
	 * @author 李利广
	 * @Date 2018年4月27日 上午11:25:04
	 * @param id
	 */
	@Override
	public void sort(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			DataDictionarys dic = dao.findOne(ids[i]);
			dic.setSort(i);
			dao.save(dic);
		}
	}

	/**
	 * 拖拽数据字典排序 TODO
	 * 
	 * @author 冯超
	 * @Date 2018年5月24日 下午3:30:43
	 * @param ids
	 * @param dropId
	 * @param pId
	 */
	@Override
	public void updatetreeSort(String[] ids, String dropId, String pId) {
		// 更新父id
		DataDictionarys dataDictionary = dao.findOne(dropId);
		dataDictionary.setpId(pId);
		dao.save(dataDictionary);
		// 更新sort值
		for (int i = 0; i < ids.length; i++) {
			DataDictionarys dic = dao.findOne(ids[i]);
			dic.setSort(i);
			dao.save(dic);
		}
	}

	@Override
	public int getIsPid(String id) {

		return dao.findPid(id);
	}

	@Override
	public List<DataDictionarys> bumenlist(final DataDictionarys dic) throws Exception {
		Sort sort = new Sort("sort", "creTime");
		if ("1".equals(dic.getType())) {
			return getTypeListByType(dic.getTreeType());
		}
		return dao.findAll(new Specification<DataDictionarys>() {
			@Override
			public Predicate toPredicate(Root<DataDictionarys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType = cb.equal(root.get("type").as(String.class), dic.getType());
				predicates.add(pVisible);
				predicates.add(pType);
				Predicate pName = null, pCode = null, pTreeType = null;
				if (!StringUtils.isBlank(dic.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + dic.getName() + "%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(dic.getTreeType())) {
					pTreeType = cb.equal(root.get("treeType").as(String.class), dic.getTreeType());
					predicates.add(pTreeType);
				}
				if (!StringUtils.isBlank(dic.getCode())) {
					pCode = cb.equal(root.get("code").as(String.class), dic.getCode());
					predicates.add(pCode);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}

	/**
	 * 获取树的父级数据
	 */
	@Override
	public List<DataDictionarys> getPname(String id) {
		// TODO Auto-generated method stub
		return dao.getAllpTypes(id);
	}

	// 获取部门试卷树及其他三棵树的子级
	public List<DataDictionarys> getBuMenTypeListByType(String treeType) {
		List<DataDictionarys> list = new ArrayList<>();
		String cruJuId = UserUtil.getCruJuId();
		List<DataDictionarys> buMenTree = dao.getBuMenTree(cruJuId, "juj");
		if (buMenTree.size()>=1) {
			List<DataDictionarys> allChildTypes = dao.getAllChildTypes(buMenTree.get(0).getId());
			list.addAll(allChildTypes);
		}
		List<DataDictionarys> dicByType = dao.getDicByType("sj", "0");
		for (int i = 0; i < dicByType.size(); i++) {
			if (!dicByType.get(0).getId().equals("")) {
				list.addAll(dao.getAllChildTypes(dicByType.get(i).getId()));
			}
		}

		return list;

	}

	// 获取部门及其他三颗试卷树
	@Override
	public List<DataDictionarys> bumenSjList(final DataDictionarys dic) throws Exception {
		Sort sort = new Sort("sort", "creTime");
		if ("1".equals(dic.getType())) {
			return getBuMenTypeListByType(dic.getTreeType());
		}
		return dao.findAll(new Specification<DataDictionarys>() {
			@Override
			public Predicate toPredicate(Root<DataDictionarys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType = cb.equal(root.get("type").as(String.class), dic.getType());
				predicates.add(pVisible);
				predicates.add(pType);
				Predicate pName = null, pCode = null, pTreeType = null;
				if (!StringUtils.isBlank(dic.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + dic.getName() + "%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(dic.getTreeType())) {
					pTreeType = cb.equal(root.get("treeType").as(String.class), dic.getTreeType());
					predicates.add(pTreeType);
				}
				if (!StringUtils.isBlank(dic.getCode())) {
					pCode = cb.equal(root.get("code").as(String.class), dic.getCode());
					predicates.add(pCode);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}

	// 获取部门试卷树子级
	public List<DataDictionarys> getBuMenListByType(String treeType) {

		String cruJuId = UserUtil.getCruJuId();
		List<DataDictionarys> buMenTree = dao.getBuMenTree(cruJuId,
				treeType.substring(treeType.length() - 3, treeType.length()));
		List<DataDictionarys> allChildTypes = dao.getAllChildTypes(buMenTree.get(0).getId());
		return allChildTypes;

	}

	// add by haolingtao
	@Override
	public List<DataDictionarys> getLearningTimeTypeListByMark(String treeType, String creJuId, String pId)
			throws Exception {
		List<DataDictionarys> dicList = new ArrayList<>();
		if (StringUtils.isNotBlank(treeType)) {
			
			List<DataDictionarys> typeList = new ArrayList<DataDictionarys>();
			if(treeType.equals(XXKHCommonConstants.XXKH_TYPE[0]) || treeType.equals(XXKHCommonConstants.XXKH_TYPE[1]) || treeType.equals(XXKHCommonConstants.XXKH_TYPE[2])){
				//如果所查询的树是法制资料、保密资料、政治理论资料则只根据treeType进行查询即可
				typeList = dao.getTreeType(treeType);
			}else{
				typeList = dao.getDicByMark(treeType, "0", creJuId);
			}
			//List<DataDictionarys> typeList = dao.getDicByMark(treeType, "0", creJuId);
			if (typeList.size() > 0) {
				dicList = dao.getLearningTimeTreeAllChildTypes(typeList.get(0).getId());
			}
		}
		return dicList;
	}

	@Override
	public List<DataDictionarys> learningTimeTreeList(final DataDictionarys dic) throws Exception {
		Sort sort = new Sort("sort", "creTime");
		if ("1".equals(dic.getType())) {
			return getLearningTimeTypeListByMark(dic.getTreeType(), dic.getCreJuId(), dic.getpId());
		}
		return dao.findAll(new Specification<DataDictionarys>() {
			@Override
			public Predicate toPredicate(Root<DataDictionarys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType = cb.equal(root.get("type").as(String.class), dic.getType());
				predicates.add(pVisible);
				predicates.add(pType);
				Predicate pName = null, pCode = null, pTreeType = null;
				if (!StringUtils.isBlank(dic.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + dic.getName() + "%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(dic.getTreeType())) {
					pTreeType = cb.equal(root.get("treeType").as(String.class), dic.getTreeType());
					predicates.add(pTreeType);
				}
				if (!StringUtils.isBlank(dic.getCode())) {
					pCode = cb.equal(root.get("code").as(String.class), dic.getCode());
					predicates.add(pCode);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}

	// 只获取部门的树
	@Override
	public List<DataDictionarys> getBuMenSjList(final DataDictionarys dic) throws Exception {
		Sort sort = new Sort("sort", "creTime");
		if ("1".equals(dic.getType())) {
			return getBuMenListByType(dic.getTreeType());
		}
		return dao.findAll(new Specification<DataDictionarys>() {
			@Override
			public Predicate toPredicate(Root<DataDictionarys> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType = cb.equal(root.get("type").as(String.class), dic.getType());
				predicates.add(pVisible);
				predicates.add(pType);
				Predicate pName = null, pCode = null, pTreeType = null;
				if (!StringUtils.isBlank(dic.getName())) {
					pName = cb.like(root.get("name").as(String.class), "%" + dic.getName() + "%");
					predicates.add(pName);
				}
				if (!StringUtils.isBlank(dic.getTreeType())) {
					pTreeType = cb.equal(root.get("treeType").as(String.class), dic.getTreeType());
					predicates.add(pTreeType);
				}
				if (!StringUtils.isBlank(dic.getCode())) {
					pCode = cb.equal(root.get("code").as(String.class), dic.getCode());
					predicates.add(pCode);
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, sort);
	}
}
