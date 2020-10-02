package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.dao.WmglConfigDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.entity.WmglConfig;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO 外卖管理-基础配置service
 * @author 李颖洁  
 * @date 2019年5月7日  下午3:47:45
 */
@Service
public class WmglConfigServiceImpl implements WmglConfigService{

    @Autowired
    private WmglConfigDao dao;
    
    @Autowired
	JdbcTemplate jdbcTemplate;

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
    public PageImpl list(Pageable pageable, PageImpl pageImpl, String period) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append("	from WmglConfig t where t.visible = ? ");
        para.add(CommonConstants.VISIBLE[1]);
        //拼接条件
        if (StringUtils.isNotBlank(period)) {
            querySql.append("   and t.period =?");
            para.add(period);
        }
        //拼接排序语句
        querySql.append("  order by t.creTime desc  ");

        Page<WmglConfig> page = dao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

   /**
    * TODO 修改配置信息
    * @author 李颖洁  
    * @date 2019年5月7日下午4:17:08
    * @param con
    * @return Config
    */
    @Override
    public WmglConfig updateConfig(WmglConfig con) {
    	WmglConfig oriCon = dao.findOne(con.getId());
    	oriCon.setAttentionItme(con.getAttentionItme());
    	oriCon.setIsvalid(con.getIsvalid());
    	oriCon.setLockTime(con.getLockTime());
    	oriCon.setLostCreditLimt(con.getLostCreditLimt());
    	oriCon.setPeriod(con.getPeriod());
    	oriCon.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
    	oriCon.setUpdateUserId(UserUtil.getCruUserId());
    	oriCon.setUpdateUserName(UserUtil.getCruUserName());
    	con = dao.save(oriCon);
    	return con;
    }

	/** 
	 * TODO 根据id获取数据 
	 * @author 李颖洁  
	 * @date 2019年5月7日下午2:53:44
	 * @param id
	 * @return Config
	 */
	@Override
	public WmglConfig getById(String id) {
		return dao.findOne(id);
	}
	
    /**
     * TODO 根据年度查询解锁时间
     * @author 李颖洁  
     * @date 2019年5月10日下午2:41:31
     * @param year 年度
     * @return String "yyyy-MM-dd"
     */
    @Override
    public String getLoseTimeByYear(String year) {
    	String time = "";
    	if(StringUtils.isNotBlank(year)){
    		String sql = "select t.lock_time lockTime from wmgl_config t where t.visible = ? and t.isvalid = ? and t.period = ?";
    		Map<String,Object> result = jdbcTemplate.queryForMap(sql, CommonConstants.VISIBLE[1],WmglConstants.VALID[1],year);
    		Calendar cale = Calendar.getInstance();
    		cale.setTime(new Date());
    		cale.add(Calendar.MONTH,Integer.valueOf(result.get("LOCKTIME").toString()));
    		time = DateUtil.getDateText(cale.getTime(), "yyyy-MM-dd");
    	}
    	return time;
    }
    
    /**
     * 获取本年度的注意事项
     * TODO 
     * @author 马秋霞
     * @Date 2019年5月16日 下午4:42:08
     * @return
     */
	@Override
	public String getAttendItem() {
		String cruYear = new SimpleDateFormat("yyyy").format(new Date());
		StringBuilder sql = new StringBuilder(" select t.attention_itme item from WMGL_CONFIG t where t.period= ? ");
		String item = jdbcTemplate.queryForObject(sql.toString(), String.class,cruYear);
		return item;
	}
}
