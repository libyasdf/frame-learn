package com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.services;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.services.ConfigService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.constant.ZBGLCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.vo.ShangBaoVo;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.dao.ShangBaoDao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author 王富康
 * @Description //TODO 上报表Service层
 * @Date 2018/7/17 9:34
 * @Param
 * @return
 **/
@Service
public class ShangBaoServiceImpl implements ShangBaoService{

    @Autowired
    private ShangBaoDao shangBaoDao;

    @Autowired
    private ConfigService configService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Author 王富康
     * @Description //TODO 值班表列表查询(值班表管理使用)
     * @Date 2018/7/17 9:30
     * @Param [pageImpl, time, unitId]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @Override
    public PageImpl list(Pageable pageable, PageImpl pageImpl, String month, String isReport, String reportStatus){
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append(" from ShangBao t where 1=1 ");

        querySql.append(" and unitId='"+UserUtil.getCruChushiId()+"'");//本部门的
        //拼接条件
        if (StringUtils.isNotBlank(month)) {
            querySql.append("   and t.month = ?");
            para.add(""+month+"");
        }
        if (StringUtils.isNotBlank(isReport)) {
            querySql.append("   and t.isReport = ?");
            para.add(""+isReport+"");
        }
        if (StringUtils.isNotBlank(reportStatus)) {
            querySql.append("   and t.isOnTime = ?");
            para.add(""+reportStatus+"");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.month desc ) ");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
        }

        Page<ShangBao> page = shangBaoDao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

    /**
     * @Author 王富康
     * @Description //TODO 总值班室查看值班室上报情况 x/x
     * @Date 2018/7/27 10:49
     * @Param [pageable, pageImpl, month]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @Override
    public PageImpl getList(Pageable pageable, PageImpl pageImpl,String month){

        PageImpl lists = configService.list(pageable, pageImpl, month,"");

        List<Config> content1 = (List<Config>) lists.getData().getRows();

        List<ShangBaoVo> list = new ArrayList<>();
        for (int i = 0;i< content1.size();i++){
            //查询当月基础配置的单位中已上报的单位数量
            String sql = "select count(n.id) as bl from  zbgl_duty_info n where n.is_report='1' and n.month ='"+content1.get(i).getDutyMonth()+"' and n.unit_id in ("+content1.get(i).getUnitId()+")";
            List<ShangBaoVo> page = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ShangBaoVo.class));
            String sbs = page.get(0).getBl();
            int zs = content1.get(i).getUnitId().split(",").length;

            content1.get(i).setBl(sbs+"/"+zs);
        }
        return lists;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询处室上报情况详细信息
     * @Date 2018/7/27 17:17
     * @Param [month]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao>
     **/
    @Override
    public List<ShangBao> getChushiReportStatus(String year_month){

        //获取基础配置的数据
        List<Config> lists = configService.queryOne(year_month);
        //基础配置中的ids和names
        String unitIds = lists.get(0).getUnitId();
        String unitNames = lists.get(0).getUnitName();

        String sql="select t.* from zbgl_duty_info t where t.month = ? and t.unit_id in ("+unitIds+")";

        List<ShangBao> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ShangBao.class),year_month);

        //把上报表没数据的单位置为未上报
        //遍历上报表的处室id从基础配置ids中去除
        for(int i = 0;i<list.size();i++){
            //去除数组中上报表的id
            if(unitIds.contains(list.get(i).getUnitId()+",")){
                //排除最后一个
                unitIds = unitIds.replaceAll( ""+list.get(i).getUnitId()+",","");
            }else if(unitIds.contains(","+list.get(i).getUnitId())){
                //如果是最后一个
                unitIds = unitIds.replaceAll( ","+list.get(i).getUnitId()+"","");
            }else if(unitIds.contains(list.get(i).getUnitId())){
                //只有一个单位的时候
                unitIds = unitIds.replaceAll( ""+list.get(i).getUnitId()+"","");
            }
            //去除数组中上报表的单位名称
            if(unitNames.contains(list.get(i).getUnitName()+",")){
                //排除最后一个
                unitNames = unitNames.replaceAll( ""+list.get(i).getUnitName()+",","");
            }else if(unitNames.contains(","+list.get(i).getUnitName())){
                //如果是最后一个
                unitNames = unitNames.replaceAll( ","+list.get(i).getUnitName()+"","");
            }else if(unitNames.contains(list.get(i).getUnitName())){
                //只有一个单位的时候
                unitNames = unitNames.replaceAll( ""+list.get(i).getUnitName()+"","");
            }
        }
        String[] unitList = unitIds.split(",");
        String[] unitName = unitNames.split(",");
        for(int j = 0;j<unitList.length;j++){
            if("".equals(unitList[j])){
                //如果单位为空，则不做任何操作
            }else{
                //有数据，则在之间的list中添加伪数据。
                ShangBao sbb = new ShangBao();
                sbb.setId(UUID.randomUUID().toString());
                sbb.setMonth(year_month);
                sbb.setUnitId(unitList[j]);
                sbb.setUnitName(unitName[j]);
                sbb.setIsReport(ZBGLCommonConstants.IS_REPORT[0]);
                list.add(sbb);
            }
        }
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 上报(修改上报表的上报状态)
     * @Date 2018/8/1 10:02
     * @Param [id, commonPhone, privatePhone]
     * @return int
     **/
    @Override
    public int reportDuty(String id, String month) {

        String reporterId = UserUtil.getCruUserId();
        String reporterName = UserUtil.getCruUserName();
        String reporterTime = JDateToolkit.getNowDate4();
        String isOnTime = ZBGLCommonConstants.IS_ON_TIME[0];
        //拿现在时间跟基础配置的时间做对比
        if(configService.isOnTime(month)){
            isOnTime = ZBGLCommonConstants.IS_ON_TIME[1];
        }
        String jpql = "update ShangBao t set t.isReport="+ZBGLCommonConstants.IS_REPORT[1]+", t.reporterId = ?, t.reporterName = ?, t.reporterTime = ?,t.isOnTime = ? where t.id = ?";
        int updateDuty = shangBaoDao.update(jpql, reporterId, reporterName, reporterTime, isOnTime, id);
        return updateDuty;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据月份查询本部门是否已经添加该月份的上报数据
     * @Date 2018/8/1 15:46
     * @Param [month]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao>
     **/
    @Override
    public List<ShangBao> queryOne(String month) {
        String sql="    select t.* from zbgl_duty_info t where t.month = ? ";
        String chushiId = UserUtil.getCruChushiId();
        if(StringUtils.isNotBlank(chushiId)){
            sql += "    and t.unit_id = '"+chushiId+"'" ;
        }
        List<ShangBao> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ShangBao.class),month);
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据月份和单位id查询出一条上报数据
     * @Date 2018/8/24 16:33
     * @Param [month, unitId]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao>
     **/
    @Override
    public List<ShangBao> queryOneByMonthAndUnitId(String month,String unitId){

    	List<Object> paraList = new ArrayList<>();
    	paraList.add(month);
        String sql="    select t.* from zbgl_duty_info t where t.month = ? and unit_id in ("+CommonUtils.solveSqlInjectionOfIn(unitId.replaceAll("'", ""), paraList)+") and t.is_report = "+ZBGLCommonConstants.IS_REPORT[1];

        List<ShangBao> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ShangBao.class),paraList.toArray());
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增上报表数据
     * @Date 2018/8/1 15:46
     * @Param [sb]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao
     **/
    @Override
    public ShangBao save(ShangBao sb) {
        //如果id为空，则为新增
        String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        sb.setUnitId(UserUtil.getCruChushiId());//创建人id
        sb.setUnitName(UserUtil.getCruChushiName());
        sb.setMonth(sb.getMonth());
        sb.setIsReport(ZBGLCommonConstants.IS_REPORT[0]);
        sb = shangBaoDao.save(sb);

        return sb;
    }
}
