package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.controller;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services.DyxxService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtRevoke;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtUnit;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.vo.DwxtTreeVO;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 党组织控制层
 * @author 胡石阳
 * @Date 2018年4月9日 上午9:30:57
 */
@RestController
@RequestMapping(value = "/djgl/ddjs/dzz/dzzgl")
public class DwxtOrgController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DwxtOrgService dwxtOrgService;

	@Autowired
	private DyxxService dyxxService;

	/**
	 * 党组织结构树
	 * @param dwxtOrg
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "党组织结构树")
	@RequestMapping(value = "getTree", method = RequestMethod.GET)
	public List<DwxtTreeVO> getTree(DwxtOrg dwxtOrg,String type) {
		List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
		List<DwxtTreeVO> treeList = new ArrayList<>();
		for(Map map : list){
			if(type.equals("org") && map.get("type").toString().equals("user")){
				continue;
			}else {
				DwxtTreeVO dwxtTreeVO = new DwxtTreeVO();
				dwxtTreeVO.setId(map.get("id").toString());
				dwxtTreeVO.setName(map.get("name").toString());
				dwxtTreeVO.setpId(map.get("pid").toString());
				dwxtTreeVO.setCode(map.get("code").toString());
				dwxtTreeVO.setCount(map.get("count").toString());
				dwxtTreeVO.setOrgType(map.get("org_Type").toString());
				dwxtTreeVO.setType(map.get("type").toString());
				if(map.get("type").toString().equals("user")){
					dwxtTreeVO.setIcon("/static/images/user.png");
				}  else {
					//dwxtTreeVO.setIsParent(true);
					if (dwxtTreeVO.getpId().equals("000")) {
						dwxtTreeVO.setIcon("/modules/dwgl/common/img/timg.gif");
					} else if (dwxtTreeVO.getOrgType().equals("611") && !dwxtTreeVO.getpId().equals("000")) {
						dwxtTreeVO.setIcon("/modules/dwgl/common/img/folder_Close_red.gif");
					} else if (dwxtTreeVO.getOrgType().equals("621")) {
						dwxtTreeVO.setIcon("/modules/dwgl/common/img/folder_Close_yellow.gif");
					} else if (dwxtTreeVO.getOrgType().equals("631")) {
						dwxtTreeVO.setIcon("/modules/dwgl/common/img/folder_Close_blue.gif");
					} else {
						dwxtTreeVO.setIcon("/modules/dwgl/common/img/folder_Close_green.gif");
					}
				}
				treeList.add(dwxtTreeVO);
			}

		}
		return treeList;
	}

	/**
	 * 党组织列表
	 * @param dwxtOrg
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "党组织列表")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public List<DwxtOrg> list(DwxtOrg dwxtOrg,PageImpl pageImpl){
		return dwxtOrgService.findChildVal(dwxtOrg,pageImpl);
	}

	/**
	 * 查询党组织信息
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "查询党组织信息")
	@RequestMapping(value = "findOne", method = RequestMethod.GET)
	public JSONObject findOne(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtOrg dwxtOrg = null;
		try {
			dwxtOrg = dwxtOrgService.findOne(id);
			json.put("flag", "1");
			json.put("data", dwxtOrg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 查询党小组信息
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "查询党小组信息")
	@RequestMapping(value = "findOneAndUser", method = RequestMethod.GET)
	public JSONObject findOneAndUser(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtOrg dwxtOrg = null;
		try {
			dwxtOrg = dwxtOrgService.findOne(id);
			List<DdjsDyglUserbasicinfoEntity> list = dyxxService.findByPartyOrganizationIdAndVisible(id,"1");
			String selectIds = "";
			String selectNames = "";
			for(DdjsDyglUserbasicinfoEntity dyxx : list){
				selectIds += dyxx.getId()+",";
				selectNames += dyxx.getName()+",";
			}
			if(StringUtils.isNotBlank(selectIds)) {
				selectIds = selectIds.substring(0, selectIds.length() - 1);
				selectNames = selectNames.substring(0, selectNames.length() - 1);
			}
			json.put("flag", "1");
			json.put("data", dwxtOrg);
			json.put("selectIds",selectIds);
			json.put("selectNames",selectNames);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 保存党组织
	 * @param dwxtOrg
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存党组织")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public JSONObject save(DwxtOrg dwxtOrg,String jcOrgId) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dwxtOrg.getId())) {
				dwxtOrg = dwxtOrgService.save(dwxtOrg,jcOrgId);
			} else {
				dwxtOrg = dwxtOrgService.update(dwxtOrg,jcOrgId);
			}
			json.put("flag", 1);
			json.put("data", dwxtOrg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 保存党小组
	 * @param dwxtOrg
	 * @param selectIds
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存党小组")
	@RequestMapping(value = "saveDxz", method = RequestMethod.POST)
	public JSONObject saveDxz(DwxtOrg dwxtOrg,String selectIds) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dwxtOrg.getId())) {
				dwxtOrg = dwxtOrgService.saveDxz(dwxtOrg,selectIds);
			} else {
				dwxtOrg = dwxtOrgService.updateDxz(dwxtOrg,selectIds);
			}
			json.put("flag", 1);
			json.put("data", dwxtOrg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 检查重复党组织编码
	 * @param orgId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "检查重复党组织编码")
	@RequestMapping(value = "check", method = RequestMethod.GET)
	public JSONObject check(String orgId){
		JSONObject json = new JSONObject();
		boolean valid = dwxtOrgService.check(orgId);
		json.put("valid",valid);
		return json;
	}

	/**
	 * 删除党组织
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除党组织")
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public JSONObject delete(String id) {
		JSONObject json = new JSONObject();
		DwxtOrg dwxtOrg = dwxtOrgService.findOne(id);
		if (null != dwxtOrg) {
			try {
				dwxtOrgService.deleteOrg(dwxtOrg,"");
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}

	/**
	 * 查询单位列表
	 * @param dwxtOrg
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询单位列表")
	@RequestMapping(value = "unitList", method = RequestMethod.GET)
	public List<DwxtUnit> unitList(DwxtOrg dwxtOrg){
		return  dwxtOrgService.unitList(dwxtOrg);
	}

	/**
	 * 保存单位
	 * @param dwxtUnit
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "保存单位")
	@RequestMapping(value = "saveUnit", method = RequestMethod.POST)
	public JSONObject saveUnit(DwxtUnit dwxtUnit) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dwxtUnit.getId())) {
				dwxtUnit = dwxtOrgService.saveUnit(dwxtUnit);
			} else {
				dwxtUnit = dwxtOrgService.updateUnit(dwxtUnit);
			}
			json.put("flag", 1);
			json.put("data", dwxtUnit);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	/**
	 * 修改单位
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "edit",opName = "修改单位")
	@RequestMapping(value = "findOneUnit", method = RequestMethod.GET)
	public JSONObject findOneUnit(String id){
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		DwxtUnit dwxtUnit = null;
		try {
			dwxtUnit = dwxtOrgService.findOneUnit(id);
			json.put("flag", "1");
			json.put("data", dwxtUnit);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 删除单位
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "delete",opName = "删除单位")
	@RequestMapping(value = "deleteUnit", method = RequestMethod.GET)
	public JSONObject deleteUnit(String id) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(id)) {
			try {
				dwxtOrgService.deleteUnit(id);
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				json.put("flag", "0");
			}
		}
		return json;
	}

	/**
	 * 撤销信息保存
	 * @param dwxtRevoke
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "撤销信息保存")
	@RequestMapping(value = "saveRevoke", method = RequestMethod.POST)
	public JSONObject saveRevoke(DwxtRevoke dwxtRevoke,String dwxtOrgId) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isBlank(dwxtRevoke.getId())) {
				dwxtRevoke = dwxtOrgService.saveRevoke(dwxtRevoke,dwxtOrgId);
			}
			json.put("flag", 1);
			json.put("data", dwxtRevoke);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("flag", 0);
		}
		return json;
	}

	@LogAnnotation(value = "query",opName = "党组织结构树")
	@RequestMapping(value = "getOrgUserTree", method = RequestMethod.GET)
	public List<DwxtTreeVO> getOrgUserTree(String partyBranchId,String id) {
		List<Map<String,Object>> list = dwxtOrgService.getOrgUserTree(partyBranchId,id);
		List<DwxtTreeVO> treeList = new ArrayList<>();
		for(Map map : list){
			DwxtTreeVO dwxtTreeVO = new DwxtTreeVO();
			dwxtTreeVO.setId(map.get("id").toString());
			dwxtTreeVO.setName(map.get("name").toString());
			dwxtTreeVO.setpId(map.get("pid").toString());
			dwxtTreeVO.setCode(map.get("code").toString());
			dwxtTreeVO.setType(map.get("type").toString());
			if(map.get("type").toString().equals("org")){
				dwxtTreeVO.setIsParent(true);
				if(map.get("org_type").toString().equals("666")){
					dwxtTreeVO.setIcon("/modules/dwgl/common/img/folder_Close_green.gif");
				}else{
					dwxtTreeVO.setIcon("/modules/dwgl/common/img/folder_Close_blue.gif");
				}
			}else{
				dwxtTreeVO.setIcon("/static/images/user.png");
			}
			treeList.add(dwxtTreeVO);
		}
		return treeList;
	}

	@LogAnnotation(value = "query",opName = "党组织顶级节点")
	@RequestMapping(value = "getOrgId", method = RequestMethod.GET)
	public JSONObject getOrgId() {
		JSONObject jb = new JSONObject();
		jb.put("data",dwxtOrgService.getOrgId());
		if(jb.get("data") == null){
			String userId = UserUtil.getCruUserId();
			List<DdjsDyglUserbasicinfoEntity> list = dyxxService.findByUserIdAndVisible(userId, CommonConstants.VISIBLE[1]);
			if(list.size() > 0){
				jb.put("data",DzzUtil.getDzbsjOrgId(list.get(0).getId()));
			}
		}
		return jb;
	}

//	@LogAnnotation(value = "query",opName = "获取部门树顶级节点")
//	@RequestMapping(value = "findAssociativeUnitId", method = RequestMethod.GET)
//	public JSONObject findAssociativeUnitId(String id) {
//		JSONObject jb = new JSONObject();
//		jb.put("data",dwxtOrgService.findByOrgId(id));
//		return jb;
//	}

	@LogAnnotation(value = "query",opName = "查询是否可以撤销")
	@RequestMapping(value = "isRevoke", method = RequestMethod.GET)
	public Boolean isContainUser(DwxtOrg dwxtOrg) {
		List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
		for(Map<String,Object> map : list){
			if(map.get("type").toString().equals("user")){
				return false;
			}
		}
		return true;
	}

	/**
	 * 历史党组织列表
	 * @param dwxtOrg
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "党组织列表")
	@RequestMapping(value = "lslist", method = RequestMethod.GET)
	public List<DwxtOrg> lslist(DwxtOrg dwxtOrg){
		return dwxtOrgService.lsList(dwxtOrg);
	}

}
