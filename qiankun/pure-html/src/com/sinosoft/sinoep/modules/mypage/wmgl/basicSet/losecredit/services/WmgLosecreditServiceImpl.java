package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.services.WmglConfigService;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.dao.WmgLosecreditDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.losecredit.entity.WmgLosecredit;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 外卖管理-基础配置service
 * @author 李颖洁  
 * @date 2019年5月7日  下午3:47:45
 */
@Service
public class WmgLosecreditServiceImpl implements WmgLosecreditService{

    @Autowired
    private WmgLosecreditDao dao;
    
    @Autowired
	JdbcTemplate jdbcTemplate;
    
    @Autowired
    WmglConfigService configService;
    
    private String date = DateUtil.COMMON.getDateText(new Date());

    /**
     * TODO 查询分页列表
     * @author 李颖洁  
     * @date 2019年5月7日下午3:48:14
     * @param pageable
     * @param pageImpl
     * @param isvalid
     * @return PageImpl
     */
    @Override
    public PageImpl list(Pageable pageable, PageImpl pageImpl,WmgLosecredit losecredit,String startDate,String endDate) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append("	from WmgLosecredit t where t.visible = ? ");
        para.add(CommonConstants.VISIBLE[1]);
        //拼接条件
        if (StringUtils.isNotBlank(startDate)) {
            querySql.append("   and substr(t.relieveTime,0,10) >= ?");
            para.add(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
        	querySql.append("   and substr(t.loseTime,0,10) <= ?");
        	para.add(endDate);
        }
        if (StringUtils.isNotBlank(losecredit.getLoseUserName())) {
        	querySql.append("   and t.loseUserName like ?");
        	para.add("%"+losecredit.getLoseUserName()+"%");
        }
        if (StringUtils.isNotBlank(losecredit.getState())&&WmglConstants.VALID[1].equals(losecredit.getState())) {
        	//解除
        	querySql.append("   and t.relieveType = ? or (t.relieveType = ? and t.relieveTime<= ?)");
        	para.add(WmglConstants.RELIEVE_TYPE[1]);
        	para.add(WmglConstants.RELIEVE_TYPE[0]);
        	para.add(date);
        }else if(StringUtils.isNotBlank(losecredit.getState())&&WmglConstants.VALID[0].equals(losecredit.getState())){
        	//锁定
        	querySql.append("   and (t.relieveType = ? and t.relieveTime> ?)");
        	para.add(WmglConstants.RELIEVE_TYPE[0]);
        	para.add(date);
        }
        //拼接排序语句
        querySql.append("  order by t.creTime desc ");

        Page<WmgLosecredit> page = dao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

	/**
	 * TODO 根据id获取数据 
	 * @author 李颖洁  
	 * @date 2019年5月9日下午3:43:08
	 * @param id
	 * @return WmgLosecredit
	 */
	@Override
	public WmgLosecredit getById(String id) {
		return dao.findOne(id);
	}

	/** 
	 * TODO 手动解除锁定状态
	 * @author 李颖洁  
	 * @date 2019年5月9日下午3:48:52
	 * @param id
	 * @return int
	 */
	@Override
	public int updateType(String id) {
		return jdbcTemplate.update("update wmgl_losecredit set relieve_type= ?,relieve_time = ? where id= ?",WmglConstants.RELIEVE_TYPE[1],
				date,id);
	}

	/** 
	 * TODO 根据人员id判断是否锁定状态
	 * @author 李颖洁  
	 * @date 2019年5月9日下午6:40:58
	 * @param userIds (逗号分隔)
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> losecreditOrNot(String userIds) {
		userIds = "506830,111245";
		List<Object> para = new ArrayList<Object>();
		String sql = "select u.userid loseUserId,t.num from uias.sys_flow_user u "
				+ "left join ("
				+ "select t.lose_user_id,count(t.lose_user_id) num from wmgl_losecredit t "
				+ "where t.lose_user_id in ("+CommonUtils.solveSqlInjectionOfIn(userIds,para)+") and t.relieve_type = ? and t.relieve_time > ? "
				+ "group by t.lose_user_id"
				+ ") t on t.lose_user_id = u.userid ";
		para.add(WmglConstants.RELIEVE_TYPE[0]);
		para.add(date);
		sql +=  "where u.userid in ("+CommonUtils.solveSqlInjectionOfIn(userIds,para)+")";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,para.toArray());
		//返回list结果[{LOSTUSERID=506830, NUM=null}, {LOSTUSERID=111245, NUM=null}]
		Map<String,Object> result = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			if(ObjectUtils.isEmpty(map.get("NUM"))){
				result.put(map.get("LOSEUSERID").toString(), false);
			}else{
				result.put(map.get("LOSEUSERID").toString(), true);
			}
		}
		return result;
	}
	
	/**
	 * TODO 插入失信记录数据
	 * @author 李颖洁  
	 * @date 2019年5月10日下午2:04:06
	 * @param userIds 多个以逗号分隔
	 * @param year 年度
	 * @return List<WmgLosecredit>
	 */
	@Override
	public List<WmgLosecredit> saveLosecredit(String userIds,String year) {
		userIds = "506830,111245";
		year = "2018";
		List<WmgLosecredit> list = new ArrayList<>();
		if(StringUtils.isNotBlank(userIds)){
			String[] loseUserIds = userIds.split(",");
			Map<String, SysFlowUserVo> userMap = UserUtil.getUserVo(userIds);
			for (String loseUserId : loseUserIds) {
				SysFlowUserVo userVo = userMap.get(loseUserId);
				WmgLosecredit wlc = new WmgLosecredit(loseUserId,userVo.getUserName(),userVo.getUserDeptId(),userVo.getUserDeptName(),
						userVo.getUserChushiId(),userVo.getUserChushiName(),userVo.getUserJuId(),userVo.getUserJuName(),date,configService.getLoseTimeByYear(year));
				list.add(wlc);
			}
			list = dao.save(list);
		}
		return list;
	}
	
	/**
	 * 获取当前用户的锁定状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月14日 下午1:55:54
	 * @return
	 */
	@Override
	public Map<String,String> getLoseState() {
		String cruYear = new SimpleDateFormat("yyyy").format(new Date());
		Map<String,String> map = new HashMap<String,String>();
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date());
		StringBuilder sql = new StringBuilder(" select count(1) cnt from wmgl_losecredit t where t.visible='1'  and t.LOSE_USER_ID= '" + UserUtil.getCruUserId() + "' and t.RELIEVE_TIME > ? ");
		Integer cnt = jdbcTemplate.queryForObject(sql.toString(), Integer.class,cruDate);
		String state = "0";
		String msg = "";
		if(cnt>0) {//当前用户已被锁定
			state = "1";
			msg = "您已被锁定！";
		}else {
			//查询当前用户还有几次被锁定
			String sql1 = " select count(1) cnt from wmgl_order_info t "
					+ "where t.lose_credit='0' and t.status='4' and t.cre_user_id='" + UserUtil.getCruUserId() + "' and substr(t.CRE_TIME,0,4) = ? ";
			Integer loseCnt = jdbcTemplate.queryForObject(sql1.toString(), Integer.class,cruYear);
			Integer lostCreditLimt = 3;//默认是3次后被锁定
			if(loseCnt>0) {//已有被锁定的次数
				state = "2";
				StringBuilder configSql = new StringBuilder(" select t.lost_credit_limt CreditLimt,t.lock_time lockTime from WMGL_CONFIG t where period  = ? ");
				Map<String,Object> configMap = jdbcTemplate.queryForMap(configSql.toString(), cruYear);
				if(configMap!=null && configMap.get("CREDITLIMT")!=null) {
					lostCreditLimt = Integer.valueOf(configMap.get("CREDITLIMT").toString());
				}
				msg = "您已失信" + loseCnt + "次，再失信"+(lostCreditLimt-loseCnt)+"次，将被锁定！";
			}
		}
		map.put("state", state);
		map.put("msg", msg);
		return map;
	}
	
}
