package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.service;/**
 * Created by s on 2018/9/15.
 */

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DyxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.TeamSituationTotal;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.dao.FzdxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.entity.DdjsSqrglPartyconsiderationEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description :TODO 预备党员审议 Service
 * @Author: 李帅
 * @Date: 2018/9/15 14:10
 */
@Service
public class FzdxServiceImpl  implements  FzdxServiceI{

    @Autowired
    private FzdxDao ybdysyDao;
    @Autowired
    private DyxxDao dyxxDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     *
     *预备党员审议 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/15
     * */
    public DdjsSqrglPartyconsiderationEntity saveYbdysy(DdjsSqrglPartyconsiderationEntity ybdysy,String typeOfPersonnel) {
        String id = ybdysy.getId();
        if (StringUtils.isBlank(id)) {
            ybdysy.setCreUserId(UserUtil.getCruUserId());
            ybdysy.setCreUserName(UserUtil.getCruUserName());
            ybdysy.setCreDeptId(UserUtil.getCruDeptId());
            ybdysy.setCreDeptName(UserUtil.getCruDeptName());
            ybdysy.setCreChushiId(UserUtil.getCruChushiId());
            ybdysy.setCreChushiName(UserUtil.getCruChushiName());
            ybdysy.setCreJuId(UserUtil.getCruJuId());
            ybdysy.setCreJuName(UserUtil.getCruJuName());
            ybdysy.setVisible(CommonConstants.VISIBLE[1]);
            ybdysy.setCreTime(JDateToolkit.getNowDate4());
            String typeArry [] = typeOfPersonnel.split(",");
            List typeVal = Arrays.asList(typeArry);
            if(!typeVal.contains("04")){
                typeOfPersonnel =(typeOfPersonnel+",04");
            }
            String deldrhd = "update DdjsDyglUserbasicinfoEntity q set q.typeOfPersonnel='"+typeOfPersonnel+"' where q.id='"+ybdysy.getSuperId()+"'";
            dyxxDao.update(deldrhd);
            ybdysy = ybdysyDao.save(ybdysy);
            return ybdysy;
        } else {
            DdjsSqrglPartyconsiderationEntity oldYbdysy = ybdysyDao.findOne(ybdysy.getId());
            oldYbdysy.setSuperId(ybdysy.getSuperId());
            oldYbdysy.setExpectedDevelopmentTime(ybdysy.getExpectedDevelopmentTime());
            oldYbdysy.setActualDevelopmentTime(ybdysy.getActualDevelopmentTime());
            oldYbdysy.setIntroducer(ybdysy.getIntroducer());
            oldYbdysy.setPartyconsiderationTime(ybdysy.getPartyconsiderationTime());
            oldYbdysy.setOrganApprovalTime(ybdysy.getOrganApprovalTime());
            String typeArry [] = typeOfPersonnel.split(",");
            List typeVal = Arrays.asList(typeArry);
            if(!typeVal.contains("04")){
                typeOfPersonnel =(typeOfPersonnel+",04");
            }
            String deldrhd = "update DdjsDyglUserbasicinfoEntity q set q.typeOfPersonnel='"+typeOfPersonnel+"' where q.id='"+ybdysy.getSuperId()+"'";
            dyxxDao.update(deldrhd);
            oldYbdysy = ybdysyDao.save(oldYbdysy);
            return oldYbdysy;
        }

    }

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月15日
     * @param id
     * @return
     */
    @Override
    public DdjsSqrglPartyconsiderationEntity getById(String id) throws Exception {
        return ybdysyDao.findBySuperId(id);
    }

    /**
     * 申请人简要信息查询
     * TODO
     * @author 李帅
     * @Date 2018年9月16日
     * @param
     * @return
     */
public  Object applicantStatistics(String annual,String ids){
    ids = DzzUtil.spiltString(ids);
        String sql ="SELECT\n" +
                "distinct	(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo	WHERE	PARTY_ORGANIZATION_ID IN("+ids+") and	VISIBLE = '1'	AND (	type_of_personnel LIKE '%01%'	OR type_of_personnel LIKE '%02%'	OR type_of_personnel LIKE '%03%                                                                                                                                                                                                                 ')AND (INSTR (type_of_personnel, '04') <= 0)) AS xx ,	\n" +
                "(	SELECT COUNT (ID)	FROM		ddjs_dygl_userbasicinfo	WHERE	PARTY_ORGANIZATION_ID IN("+ids+") and	VISIBLE = '1'	AND (	type_of_personnel LIKE '%01%'	OR type_of_personnel LIKE '%02%'	OR type_of_personnel LIKE '%03%')AND (INSTR (type_of_personnel, '04') <= 0)) AS sqrzs ， \n" +
                "	\n" +
                "	(	SELECT COUNT (ID)FROM	ddjs_dygl_userbasicinfo	WHERE PARTY_ORGANIZATION_ID IN("+ids+") and	VISIBLE = '1'AND type_of_personnel LIKE '%02%'AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0) AS jjfz,\n" +
                "  \n" +
                "  ROUND(decode( (SELECT COUNT (ID)FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND (type_of_personnel LIKE '%01%'OR type_of_personnel LIKE '%02%'OR type_of_personnel LIKE '%03%')AND (INSTR (type_of_personnel, '04') <= 0)),0,0,\n" +
                "  ((SELECT COUNT (ID)FROM ddjs_dygl_userbasicinfo	WHERE PARTY_ORGANIZATION_ID IN("+ids+") and	VISIBLE = '1'AND type_of_personnel LIKE '%02%'AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0)\n" +
                " )/ (SELECT COUNT (ID)FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1'AND (type_of_personnel LIKE '%01%'OR type_of_personnel LIKE '%02%'OR type_of_personnel LIKE '%03%')AND (INSTR (type_of_personnel, '04') <= 0) )) ,2)as jjfzbl,\n" +
                "  	\n" +
                "	(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 AND SEX='女')as njjfz ,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ),0,0,\n" +
                "	((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 AND SEX='女')\n" +
                "  /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ))) ,2)as njjfzbfb,\n" +
                "  	\n" +
                "	(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 AND NATION!='01')as ssmzjjfz ,\n" +
                "	\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ),0,0,\n" +
                "	((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 AND NATION!='01')\n" +
                "  /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ))) ,2)as ssmzjjfzbfb,\n" +
                "  	\n" +
                "(select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and is_historyparty = '0'and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ) a where  a.age <= '35') as sswsyx,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ),0,0,\n" +
                "((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and  is_historyparty = '0'and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ) a where a.age <= '35')\n" +
                " /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ))),2) as sswsyxbfb,\n" +
                " (SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%')) as dzxlys,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0  AND INSTR (type_of_personnel, '03') <= 0 ),0,0,\n" +
                " ((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%'))\n" +
                " /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%02%' AND INSTR (type_of_personnel, '04') <= 0 AND INSTR (type_of_personnel, '03') <= 0 ))),2)as dzxlysbfb,\n" +
                " \n" +
                " \n" +
                " (	SELECT COUNT (ID)FROM	ddjs_dygl_userbasicinfo	WHERE PARTY_ORGANIZATION_ID IN("+ids+") and 	VISIBLE = '1'AND type_of_personnel LIKE '%03%'AND INSTR (type_of_personnel, '04') <= 0) AS fzdx,\n" +
                "  \n" +
                "  ROUND(decode((SELECT COUNT (ID)FROM ddjs_dygl_userbasicinfo	WHERE PARTY_ORGANIZATION_ID IN("+ids+") and 	VISIBLE = '1'AND type_of_personnel LIKE '%03%'AND INSTR (type_of_personnel, '04') <= 0),0,0,\n" +
                "  ((SELECT COUNT (ID)FROM ddjs_dygl_userbasicinfo	WHERE PARTY_ORGANIZATION_ID IN("+ids+") and	VISIBLE = '1'AND type_of_personnel LIKE '%03%'AND INSTR (type_of_personnel, '04') <= 0)\n" +
                "	)/(SELECT COUNT (ID)FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1'AND (type_of_personnel LIKE '%01%'OR type_of_personnel LIKE '%02%'OR type_of_personnel LIKE '%03%')AND (INSTR (type_of_personnel, '04') <= 0) )),2) as fzdxbl,\n" +
                "  	\n" +
                "	(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 AND SEX='女')as nfzdx ,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ),0,0,\n" +
                "	((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 AND SEX='女')\n" +
                "  /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ))),2) as nfzdxbfb,\n" +
                "  	\n" +
                "	(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 AND NATION!='01')as ssmzfzdx ,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ),0,0,\n" +
                "	((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 AND  NATION!='01')\n" +
                "  /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ))),2) as ssmzfzdxbfb,\n" +
                "	\n" +
                "(select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and is_historyparty = '0'and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ) a where a.age <= '35') as sswsyxfzdx,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ),0,0,\n" +
                "((select count(*) from (SELECT floor(months_between(SYSDATE,to_date(u.date_of_birth, 'yyyy-mm-dd')) / 12) age FROM ddjs_dygl_userbasicinfo u where PARTY_ORGANIZATION_ID IN("+ids+") and  is_historyparty = '0'and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ) a where a.age <= '35')\n" +
                " /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and  VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ))),2) as sswsyxfzdxbfb,\n" +
                " (SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%')) as dzxlysfzdx,\n" +
                "ROUND(decode((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ),0,0,\n" +
                " ((SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 AND (EDUCATION LIKE '%05%' OR EDUCATION LIKE '%06%' OR EDUCATION LIKE '%07%'))\n" +
                " /(SELECT COUNT (ID) FROM ddjs_dygl_userbasicinfo WHERE PARTY_ORGANIZATION_ID IN("+ids+") and VISIBLE = '1' AND type_of_personnel LIKE '%03%' AND INSTR (type_of_personnel, '04') <= 0 ))),2)as dzxlysfzdxbfb\n" +
                "	\n" +
                " FROM\n" +
                "	ddjs_dygl_userbasicinfo";
             List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
            BigDecimal jjfzzsVal = BigDecimal.valueOf(0);
            BigDecimal sqrzsVal = BigDecimal.valueOf(0);
            BigDecimal njjfzVal = BigDecimal.valueOf(0);
            BigDecimal njjfzBl = BigDecimal.valueOf(0);
            BigDecimal ssmzjjfzVal = BigDecimal.valueOf(0);
            BigDecimal ssmzjjfzBl = BigDecimal.valueOf(0);
            BigDecimal sswjjfzVal = BigDecimal.valueOf(0);
            BigDecimal sswjjfzBl = BigDecimal.valueOf(0);
            BigDecimal dzjjfzVal = BigDecimal.valueOf(0);
            BigDecimal dzjjfzBl = BigDecimal.valueOf(0);

            BigDecimal fzdxzsVal = BigDecimal.valueOf(0);
            BigDecimal nfzdxVal = BigDecimal.valueOf(0);
            BigDecimal nfzdxBl = BigDecimal.valueOf(0);
            BigDecimal ssmzfzdxVal = BigDecimal.valueOf(0);
            BigDecimal ssmzfzdxBl = BigDecimal.valueOf(0);
            BigDecimal sswfzdxVal = BigDecimal.valueOf(0);
            BigDecimal sswfzdxBl = BigDecimal.valueOf(0);
            BigDecimal dzfzdxVal = BigDecimal.valueOf(0);
            BigDecimal dzfzdxBl = BigDecimal.valueOf(0);
            if(list.size()>0){
                sqrzsVal = list.get(0).get("sqrzs")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sqrzs");
                jjfzzsVal = list.get(0).get("jjfz")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("jjfz");
                njjfzVal = list.get(0).get("njjfz")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("njjfz");
                njjfzBl = list.get(0).get("njjfzbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("njjfzbfb");
                ssmzjjfzVal = list.get(0).get("ssmzjjfz")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzjjfz");
                ssmzjjfzBl = list.get(0).get("ssmzjjfzbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzjjfzbfb");
                sswjjfzVal = list.get(0).get("sswsyx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswsyx");
                sswjjfzBl = list.get(0).get("sswsyxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswsyxbfb");
                dzjjfzVal = list.get(0).get("dzxlys")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxlys");
                dzjjfzBl = list.get(0).get("dzxlysbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxlysbfb");

                fzdxzsVal = list.get(0).get("fzdx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("fzdx");
                nfzdxVal = list.get(0).get("nfzdx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("nfzdx");
                nfzdxBl = list.get(0).get("nfzdxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("nfzdxbfb");
                ssmzfzdxVal = list.get(0).get("ssmzfzdx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzfzdx");
                ssmzfzdxBl = list.get(0).get("ssmzfzdxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("ssmzfzdxbfb");
                sswfzdxVal = list.get(0).get("sswsyxfzdx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswsyxfzdx");
                sswfzdxBl = list.get(0).get("sswsyxfzdxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("sswsyxfzdxbfb");
                dzfzdxVal = list.get(0).get("dzxlysfzdx")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxlysfzdx");
                dzfzdxBl = list.get(0).get("dzxlysfzdxbfb")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(0).get("dzxlysfzdxbfb");

            }
        String teamSituationTotalHtml = "共有入党申请人<u>" + sqrzsVal+"</u>名。其中</br></br>一、入党积极分子：</br>";
        teamSituationTotalHtml +="入党积极分子共有<u>"+jjfzzsVal+"</u>名。</br>";
        teamSituationTotalHtml += "入党积极分子中，女性<u>" + njjfzVal + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml +=njjfzBl.multiply(BigDecimal.valueOf(100)) + "%</u>；少数民族<u>" + ssmzjjfzVal+ "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += ssmzjjfzBl.multiply(BigDecimal.valueOf(100)) + "%</u>；35岁及以下<u>" + sswjjfzVal + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += sswjjfzBl.multiply(BigDecimal.valueOf(100)) + "%</u>；大专及以上学历的<u>" + dzjjfzVal + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += dzjjfzBl.multiply(BigDecimal.valueOf(100)) + "%</u></br>入党积极分子中，工人<u>" + "0 "+ "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += "0 %" + "</u>；企事业单位、民办非企业单位管理人员<u>" + "0 "+ "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += "0 %" + "</u>；党政机关工作人员<u>" + "0 " + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += "0 %" + "</u>；离退休人员<u>";
        teamSituationTotalHtml += "0 " + "</u>名。</br>";

        teamSituationTotalHtml += "</br>二、发展对象：</br><u>" ;
        teamSituationTotalHtml += "</u>发展对象共有<u>" +fzdxzsVal+ "</u>名。</br><u>" ;
        teamSituationTotalHtml += "</u>发展对象中，女性<u>" + nfzdxVal+ "</u>名，占发展对象总数的<u>" + nfzdxBl.multiply(BigDecimal.valueOf(100));
        teamSituationTotalHtml += "%</u>；少数民族<u>" + ssmzfzdxVal + "</u>名，占发展对象总数的<u>" + ssmzfzdxBl.multiply(BigDecimal.valueOf(100));
        teamSituationTotalHtml += "%</u>；35岁及以下<u>" + sswfzdxVal + "</u>名，占发展对象总数的<u>" + sswfzdxBl.multiply(BigDecimal.valueOf(100));
        teamSituationTotalHtml += "%</u>；大专及以上学历的<u>" + dzfzdxVal + "</u>名，占发展对象总数的<u>" + dzfzdxBl.multiply(BigDecimal.valueOf(100));
        teamSituationTotalHtml += "%</u>发展对象中，工人<u>" +"0 "+ "</u>名，占发展对象总数的<u>" + "0%";
        teamSituationTotalHtml += "</u>企事业单位、民办 非企业单位管理人员<u>" + "0 " + "</u>名，占发展对象总数的<u>" + "0% ";
        teamSituationTotalHtml += "</u>党政机关工作人员<u>" + "0 " + "</u>名，占发展对象总数的<u>" + "0% ";
        teamSituationTotalHtml += "</u>离退休人员<u>" + "0 " + "</u>名。<u>";
    return teamSituationTotalHtml;


    }

    /**
     * 根据统计实体获取页面所需html代码
     * @param teamSituationTotal
     * @return
     */
    private Object getJsonByTeamSituationTotal(TeamSituationTotal teamSituationTotal) {
        String teamSituationTotalHtml = "共有入党申请人<u>" + teamSituationTotal.getPartyMember() +"</u>名。其中</br>入党积极分子：</br>";
        teamSituationTotalHtml +="入党积极分子共有<u>"+teamSituationTotal.getPartyMember()+"</u>名。</br>";
        teamSituationTotalHtml += "入党积极分子中，女性<u>" + teamSituationTotal.getFemalePartyMember() + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getFemalePartyMemberBl() + "</u>；少数民族<u>" + teamSituationTotal.getMinNationPartyMember()+ "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getMinNationPartyMemberBl() + "</u>；35岁及以下<u>" + teamSituationTotal.getCollegeAndAboveDmPartyMember() + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getCollegeAndAbovePartyMemberBl() + "</u>；大专及以上学历的<u>" + teamSituationTotal.getThirtyFiveUnderPartyMember() + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getThirtyFiveUnderPartyMemberBl() + "</u></br>入党积极分子中，工人<u>" + teamSituationTotal.getThirtySixAbovePartyMember() + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getFortySixAbovePartyMemberBl() + "</u>；企事业单位、民办非企业单位管理人员" + teamSituationTotal.getSixtyOneAbovePartyMember() + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getFortySixAbovePartyMemberBl() + "</u>；党政机关工作人员" + teamSituationTotal.getSixtyOneAbovePartyMember() + "</u>名，占入党积极分子总数的<u>";
        teamSituationTotalHtml += teamSituationTotal.getFortySixAbovePartyMemberBl() + "</u>；离退休人员<u>";
        teamSituationTotalHtml += teamSituationTotal.getSixtyOneAbovePartyMemberBl() + "</u>名。";

        teamSituationTotalHtml += "</br>发展对象：,</br><u>" ;
        teamSituationTotalHtml += "</u>发展对象共有<u>" + teamSituationTotal.getGreatRevolutionPartyMember() + "</u>名。</br><u>" ;
        teamSituationTotalHtml += "</u>发展对象中，女性<u>" + teamSituationTotal.getEighteenthMajorPartyMember() + "</u>名，占发展对象总数的<u>" + teamSituationTotal.getSixteenMajorPartyMember();
        teamSituationTotalHtml += "</u>；少数民族<u>" + teamSituationTotal.getEighteenthMajorPartyMember() + "</u>名，占发展对象总数的<u>" + teamSituationTotal.getSixteenMajorPartyMember();
        teamSituationTotalHtml += "</br>；35岁及以下<u>" + teamSituationTotal.getWorkerPartyMember() + "</u>名，占发展对象总数的<u>" + teamSituationTotal.getAdminPartyMember();
        teamSituationTotalHtml += "</u>；大专及以上学历的<u>" + teamSituationTotal.getOfficePartyMember() + "</u>名，占发展对象总数的<u>" ;
        teamSituationTotalHtml += "发展对象中，工人<u>" + teamSituationTotal.getDmPartyMember() + "</u>名，占发展对象总数的<u>" + teamSituationTotal.getFemaleDmPartyMember();
        teamSituationTotalHtml += "</u>企事业单位、民办 非企业单位管理人员<u>" + teamSituationTotal.getMinNationDmPartyMemberBl() + "</u>名，占发展对象总数的<u>" + teamSituationTotal.getThirtyFiveUnderDmPartyMember();
        teamSituationTotalHtml += "</u>党政机关工作人员<u>" + teamSituationTotal.getThirtyFiveUnderDmPartyMemberBl() + "</u>名，占发展对象总数的<u>" + teamSituationTotal.getCollegeAndAboveDmPartyMember();
        teamSituationTotalHtml += "</u>离退休人员<u>" + teamSituationTotal.getCollegeAndAboveDmPartyMemberBl() + "</u>名。<u>";
        return teamSituationTotalHtml;
    }
}
