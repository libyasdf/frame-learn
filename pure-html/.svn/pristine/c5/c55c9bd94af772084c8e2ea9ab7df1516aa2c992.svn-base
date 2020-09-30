package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.jbqk.services;

/*import com.fr.web.core.A.T;*/
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.vo.OrgSituationTotalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrgSituationServiceImp implements OrgSituationService{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DwxtOrgService dwxtOrgService;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Override
	public Object getOrgSituation(String id) {
		OrgSituationTotalVo orgSituationTotalVo = new OrgSituationTotalVo();
		Map map = dwxtOrgService.orgBasicSituation(id);
		if (map.get("basic_org") != null) {
			orgSituationTotalVo.setBasicOrg(map.get("basic_org").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp" + map.get("basic_org").toString() + "&nbsp&nbsp");
		} else {
			orgSituationTotalVo.setBasicOrg("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map.get("org_dw") != null) {
			orgSituationTotalVo.setOrgDw(map.get("org_dw").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp" + map.get("org_dw").toString() + "&nbsp&nbsp");
		} else {
			orgSituationTotalVo.setOrgDw("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map.get("org_dzz") != null) {
			orgSituationTotalVo.setOrgDzz(map.get("org_dzz").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp" + map.get("org_dzz").toString() + "&nbsp&nbsp");
		} else {
			orgSituationTotalVo.setOrgDzz("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map.get("org_dzb") != null){
			orgSituationTotalVo.setOrgDzb(map.get("org_dzb").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp" + map.get("org_dzb").toString() + "&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setOrgDzb("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}

		Map map1 = dwxtOrgService.unitBasicSituation(id);
		if (map1.get("jgdwjl_org") != null){
			orgSituationTotalVo.setJgdwjlOrg(map1.get("jgdwjl_org").toString().equals("0")  ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map1.get("jgdwjl_org").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setJgdwjlOrg("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map1.get("jgdwjl_org_b1") != null){
			orgSituationTotalVo.setJgdwjlOrgBl(map1.get("jgdwjl_org_b1").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map1.get("jgdwjl_org_b1").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setJgdwjlOrgBl("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map1.get("sydwjl_org") != null) {
			orgSituationTotalVo.setSydwjlOrg(map1.get("sydwjl_org").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map1.get("sydwjl_org").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setSydwjlOrg("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map1.get("sydwjl_org_b1") != null) {
			orgSituationTotalVo.setSydwjlOrgB1(map1.get("sydwjl_org_b1").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map1.get("sydwjl_org_b1").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setSydwjlOrgB1("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		Map map2 = dwxtOrgService.orgCommendation(id);
		if (map2.get("sbzdxjjc_org") != null) {
			orgSituationTotalVo.setSbzdxjjcOrg(map2.get("sbzdxjjc_org").toString().equals("0")  ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map2.get("sbzdxjjc_org").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setSbzdxjjcOrg("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		Map map3 = dwxtOrgService.orgChangeSituation(id);
		if (map3.get("zj_org") != null) {
			orgSituationTotalVo.setZjOrg(map3.get("zj_org").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map3.get("zj_org").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setZjOrg("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if (map3.get("zj_org_dw") != null) {
			orgSituationTotalVo.setZjOrgDw(map3.get("zj_org_dw").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map3.get("zj_org_dw").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setZjOrgDw("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if(map3.get("js_org") != null){
			orgSituationTotalVo.setJsOrg(map3.get("js_org").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map3.get("js_org").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setJsOrg("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		if(map3.get("js_org_dw") != null){
			orgSituationTotalVo.setJsOrgDw(map3.get("js_org_dw").toString().equals("0") ? "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" : "&nbsp&nbsp"+map3.get("js_org_dw").toString()+"&nbsp&nbsp");
		}else{
			orgSituationTotalVo.setJsOrgDw("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		}
		return this.getJsonByOrgSituationTotal(orgSituationTotalVo);
	}


	/**
	 * 根据统计实体获取页面所需html代码
	 * @param orgSituationTotalVo
	 * @return
	 */
	private Object getJsonByOrgSituationTotal(OrgSituationTotalVo orgSituationTotalVo) {
		StringBuffer sb = new StringBuffer();
		sb.append("已建立党的基层组织<u>"+orgSituationTotalVo.getBasicOrg()+"</u>个，其中，党委<u>"+orgSituationTotalVo.getOrgDw()+"</u>个，党总支<u>"+orgSituationTotalVo.getOrgDzz()+"</u>个,党支部<u>"+orgSituationTotalVo.getOrgDzb()+"</u>个。</br>");
		sb.append("已建立的党组织中：</br>");
		sb.append("机关单位建立党组织<u>"+orgSituationTotalVo.getJgdwjlOrg()+"</u>个，占基层党组织总数的<u>"+orgSituationTotalVo.getJgdwjlOrgBl()+"</u>%；</br>");
		sb.append("事业单位建立党组织<u>"+orgSituationTotalVo.getSydwjlOrg()+"</u>个，占基层党组织总数的<u>"+orgSituationTotalVo.getSydwjlOrgB1()+"</u>%；</br>");
		sb.append("本年受表彰的先进基层党组织<u>"+orgSituationTotalVo.getSbzdxjjcOrg()+"</u>个；</br>");
		sb.append("本年党组织增加<u>"+orgSituationTotalVo.getZjOrg()+"</u>个，其中党委增加<u>"+ orgSituationTotalVo.getZjOrgDw() +"</u>个；");
		sb.append("本年党组织减少<u>"+orgSituationTotalVo.getJsOrg()+"</u>个，其中党委减少<u>"+ orgSituationTotalVo.getJsOrgDw() +"</u>个；");
		//sb.append()

		return sb.toString();
	}

}