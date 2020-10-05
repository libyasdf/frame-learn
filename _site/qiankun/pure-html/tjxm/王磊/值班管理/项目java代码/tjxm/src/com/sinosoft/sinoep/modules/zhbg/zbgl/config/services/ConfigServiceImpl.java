package com.sinosoft.sinoep.modules.zhbg.zbgl.config.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.dao.ConfigDao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO  基础配置Service实现层
 * @Date 2018/7/13 16:44
 * @Param 
 * @return 
 **/
@Service
public class ConfigServiceImpl implements ConfigService{

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Author 王富康
     * @Description //TODO 初始化查询表找出配置信息
     * @Date 2018/7/16 14:12
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config
     **/
    @Override
    public List<Config> queryOne(String dutyMonth) {

        String sql="select t.* from zbgl_duty_config t where t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.duty_month ='"+dutyMonth+"'";

        List<Config> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Config.class));
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO  以往基础配置信息
     * @Date 2018/7/30 18:21
     * @Param [pageable, pageImpl, dutyMonth]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @Override
    public PageImpl list(Pageable pageable, PageImpl pageImpl, String dutyMonthOfSearch, String unitNameOfSearch) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append("	from Config t where t.visible = '"+CommonConstants.VISIBLE[1]+"' ");
        //拼接条件
        if (StringUtils.isNotBlank(dutyMonthOfSearch)) {
            querySql.append("   and t.dutyMonth =?");
            para.add(dutyMonthOfSearch);
        }

        if (StringUtils.isNotBlank(unitNameOfSearch)) {
            querySql.append("   and t.unitName like ? ");
            para.add("%"+unitNameOfSearch+"%");
        }

        //拼接排序语句
        querySql.append("  order by t.dutyMonth desc ) ");

        Page<Config> page = configDao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增基础配置
     * @Date 2018/7/16 14:11
     * @Param [con]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config
     **/
    @Override
    public Config save(Config con) {
            //如果id为空，则为新增
        con.setCreUserId(UserUtil.getCruUserId());//创建人id
        con.setCreUserName(UserUtil.getCruUserName());//创建人姓名
        con.setCreDeptId(UserUtil.getCruDeptId());//部门
        con.setCreDeptName(UserUtil.getCruDeptName());
        con.setCreChushiId(UserUtil.getCruChushiId());//处室
        con.setCreChushiName(UserUtil.getCruChushiName());
        con.setCreJuId(UserUtil.getCruJuId());
        con.setCreJuName(UserUtil.getCruJuName());
        con.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
        con.setVisible(CommonConstants.VISIBLE[1]);//默认为未删除
        con = configDao.save(con);

        return con;
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改基础配置
     * @Date 2018/7/16 17:59
     * @Param [con]
     * @return int
     **/
    public int updateConfig(Config con) {

        String jpql ="update Config t set t.reportOverDate = ?,promptMessageContent=?,unitId=?,unitName=?,updateTime=?,updateUserId=?,updateUserName=? where id = ?";
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return  configDao.update(jpql, con.getReportOverDate(),con.getPromptMessageContent(),con.getUnitId(),con.getUnitName(),updateTime,UserUtil.getCruUserId(),UserUtil.getCruUserName(),con.getId());
    }

    /**
     * @Author 王富康
     * @Description //TODO 是否按时上报
     * @Date 2018/8/1 10:35
     * @Param month 上报的考勤表的月份
     * @return boolean
     **/
    public boolean isOnTime(String month){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<Config> con = queryOne(month);//根据月份查出基础配置
            Date dt1 = df.parse(JDateToolkit.getNowDate4());//当前时间
            Date dt2 = df.parse(con.get(0).getReportOverDate());//基础配置的时间
            if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1 在dt2前");
                return true;
            } else if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据月份和当前登录人的处室id查询当月本部门是否需要上报(暂时用于上报时，查看新增的数据是否可以上报)
     * @Date 2018/8/15 14:07
     * @Param [month]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config>
     **/
    public List<Config> getConfigByMonthAndUnitId(String month){
        String sql="select t.* from zbgl_duty_config t where t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.duty_month ='"+month+"' and t.unit_id like '%"+UserUtil.getCruChushiId()+"%'";

        List<Config> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Config.class));
        return list;
    }
}
