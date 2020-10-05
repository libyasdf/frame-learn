package com.sinosoft.sinoep.modules.mypage.wmgl.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.dao.WmglConfigDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.entity.WmglConfig;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;

/**
 * 外卖管理的定时器
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月22日 下午3:17:33
 */
@Service
public class Timer {
	
	@Autowired
	JdbcTemplate jdbcTemlate;
	
	@Autowired
    private WmglConfigDao dao;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 一个小时扫描一下外卖的取餐时间，过取餐时候后，就把此处外卖的订单改为已领取
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月22日 下午3:21:10
	 */
	@Scheduled(cron="0 0 */1 * * ?")
	public void UpdateOrderStatus() {
		String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		StringBuilder sql = new StringBuilder(" select t.id from wmgl_take_out_info t where t.tongji = '0' and  to_date(substr(t.takefood_time, 22, 17),'yyyy-mm-dd hh24:mi')<= to_date('" + cruDate + "','yyyy-mm-dd hh24:mi') ");
		List<String> outIds = jdbcTemlate.queryForList(sql.toString(), String.class);
		for (String outId : outIds) {
			String updateSql1 = " update wmgl_take_out_info set tongji = '1' where id ='" + outId + "'";
			jdbcTemlate.update(updateSql1);
			String updateSql = "update wmgl_order_info set  status='3' where status='1'  and take_out_id =? ";
			jdbcTemlate.update(updateSql, outId);
		}
	}
	
	/**
	 * TODO 每年自动生成一个基础配置
	 * @author 李颖洁  
	 * @date 2019年5月7日下午4:16:15
	 * @return void
	 */
    @Scheduled(cron="0 5 0 1 1 ?")
    public void save(){
        try {
        	Date today = new Date();
            Calendar cale = Calendar.getInstance();
            cale.setTime(today);
        	String year = String.valueOf(cale.get(Calendar.YEAR)-1);
            StringBuilder sql = new StringBuilder("select * from wmgl_config t where (t.period = ? or t.period = ?) and t.visible = ? order by t.period asc");
            List<WmglConfig> list = jdbcTemlate.query(sql.toString(),new Object[]{year,String.valueOf(cale.get(Calendar.YEAR)),CommonConstants.VISIBLE[1]},new BeanPropertyRowMapper<WmglConfig>(WmglConfig.class));
            if(list.size()==1){
            	list.get(0).setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
            	list.get(0).setVisible(CommonConstants.VISIBLE[1]);
            	list.get(0).setIsvalid(WmglConstants.VALID[1]);
            	list.get(0).setId(UUID.randomUUID().toString());
            	list.get(0).setPeriod(String.valueOf(cale.get(Calendar.YEAR)));
            	dao.save(list.get(0));
            }else if(list.size()==0){
            	WmglConfig con = new WmglConfig(String.valueOf(cale.get(Calendar.YEAR)));
            	dao.save(con);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
