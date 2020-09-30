package com.sinosoft.sinoep.modules.system.online.services;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.online.dao.SysOnlineTimeDao;
import com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("onlineTimeService")
public class SysOnlineTimeServiceImpl implements SysOnlineTimeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysOnlineTimeDao timeDao;

    /**
     * TODO 保存用户登录登出时间
     * @author 李利广
     * @Date 2019年06月19日 13:52:41
     * @param onlineTime (必传:userId、ticket、time)
     * @param logType 类型（登入、登出）
     * @return com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime
     */
    @Override
    public SysOnlineTime saveTime(SysOnlineTime onlineTime,String logType){
        log.info("用户 " + logType);
        try{
            String userId = onlineTime.getUserId();
            String ticket = onlineTime.getTicket();
            if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(ticket)){
                if("LOGIN".equals(logType)){
                    //如果是登入，先校验该用户上一次登录是否已退出，如果没有退出，则自动保存一个登出时间
                    List<SysOnlineTime> timeList = timeDao.getOnlineByUserId(userId);
                    if(timeList.size() == 0){
                        onlineTime = timeDao.save(onlineTime);
                    }else{
                        SysOnlineTime lastTime = timeList.get(0);
                        String lastOutTime = lastTime.getLogoutTime();
                        if(StringUtils.isNotBlank(lastOutTime)){
                            //不为空，则表示上一次正常退出
                            onlineTime = timeDao.save(onlineTime);
                        }else{
                            //如果为空，表示上一次登录还没有登出,则将本次的登入时间，设置为上次的登出时间
                            lastTime.setLogoutTime(onlineTime.getLoginTime());
                            long outTime = Long.valueOf(lastTime.getLogoutTime());
                            long inTime = Long.valueOf(lastTime.getLoginTime());
                            lastTime.setOnlineTime(Convert.toStr(outTime-inTime));
                            timeDao.save(lastTime);
                            //然后再保存本次登入记录
                            onlineTime = timeDao.save(onlineTime);
                        }
                    }
                }else if("LOGOUT".equals(logType)){
                    //如果是登出，先查询，后保存
                    SysOnlineTime time = timeDao.getTimeByUserIdAndTic(userId,ticket);
                    if(time != null){
                        String loginTime = time.getLoginTime();
                        String logoutTime = onlineTime.getLogoutTime();
                        //赋值登出时间
                        time.setLogoutTime(logoutTime);
                        //计算在线时长
                        long outTime = Long.valueOf(logoutTime);
                        long inTime = Long.valueOf(loginTime);
                        time.setOnlineTime(Convert.toStr(outTime-inTime));
                        onlineTime = timeDao.save(time);
                    }
                }
            }
            log.info("记录登出、登入时间成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("记录登出、登入时间异常");
            log.error(e.getMessage(),e);
        }
        return onlineTime;
    }

    /**
     * TODO 根据登录票据，查询某个用户某次登录的在线时长
     * @author 李利广
     * @Date 2019年06月19日 13:54:21
     * @param userId
     * @param ticket
     * @return com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime
     */
    @Override
    public SysOnlineTime getOnlineTime(String userId,String ticket){
        return timeDao.getTimeByUserIdAndTic(userId,ticket);
    }

    /**
     * TODO 查询登录登出情况（分页）
     * @author 李利广
     * @Date 2019年06月19日 13:56:52
     * @param page
     * @param onlineTime
     * @param startDate
     * @param endDate
     * @return com.sinosoft.sinoep.common.util.PageImpl
     */
    @Override
    public PageImpl getOnlineTimeByDate(PageImpl page, SysOnlineTime onlineTime, String startDate, String endDate){
        List<String> paraList = new ArrayList<>();
        StringBuffer hql = new StringBuffer(" from SysOnlineTime t where 1=1 ");
        //只查询登录、登出时间均不为空的数据
        hql.append(" and t.loginTime is not null and t.logoutTime is not null ");
        if(StringUtils.isNotBlank(onlineTime.getUserId())){
            hql.append(" and t.userId = ?");
            paraList.add(onlineTime.getUserId());
        }
        if(StringUtils.isNotBlank(onlineTime.getTicket())){
            hql.append(" and t.ticket = ?");
            paraList.add(onlineTime.getTicket());
        }
        if(StringUtils.isNotBlank(startDate)){
            DateTime startTime = new DateTime(startDate+" 00:00:00", DatePattern.NORM_DATETIME_PATTERN);
            DateTime endTime = new DateTime(endDate+" 23:59:59", DatePattern.NORM_DATETIME_PATTERN);
            hql.append(" and t.loginTime >= ?");
            hql.append(" and t.loginTime <= ?");
            paraList.add(Convert.toStr(startTime.getTime()));
            paraList.add(Convert.toStr(endTime.getTime()));
        }
        //拼接排序语句
        if (StringUtils.isBlank(page.getSortName())) {
            hql.append("  order by t.loginTime desc ");
        }else{
            hql.append("  order by t."+page.getSortName()+" "+page.getSortOrder());
        }
        Pageable pageable = new PageRequest(page.getPageNumber()-1, page.getPageSize());
        Page<SysOnlineTime> timeList = timeDao.query(hql.toString(),pageable,paraList.toArray());
        List<SysOnlineTime> content = timeList.getContent();
        //对查询结果中的日期进行格式处理
        content = handlerDate(content);
        page.getData().setRows(content);
        page.getData().setTotal((int)timeList.getTotalElements());
        return page;
    }

    /**
     * TODO 对查询结果中的日期进行格式处理,并将在线时长转为分钟数(精确到秒)
     * @author 李利广
     * @Date 2019年06月20日 16:56:38
     * @param content
     * @return java.util.List<com.sinosoft.sinoep.modules.system.online.entity.SysOnlineTime>
     */
    private List<SysOnlineTime> handlerDate(List<SysOnlineTime> content){
        if(content.size() > 0){
            for(SysOnlineTime time:content){
                String loginTime = time.getLoginTime();
                String logoutTime = time.getLogoutTime();
                String onlineTime = time.getOnlineTime();
                String loginTimeStr = DateUtil.formatDateTime(new DateTime(Convert.toLong(loginTime,0L)));
                time.setLoginTime(loginTimeStr);
                if(StringUtils.isNotBlank(logoutTime)){
                    String logoutTimeStr = DateUtil.formatDateTime(new DateTime(Convert.toLong(logoutTime,0L)));
                    time.setLogoutTime(logoutTimeStr);
                }
                //精确到秒，例如：10分24秒
                if(StringUtils.isNotBlank(onlineTime)){
                    String onlineTimeStr = DateUtil.formatBetween(Convert.toLong(onlineTime,0L), BetweenFormater.Level.SECOND);
                    time.setOnlineTime(onlineTimeStr);
                }
            }
        }
        return content;
    }

    /**
     * TODO 查询登录登出情况（不分页）
     * @author 李利广
     * @Date 2019年06月19日 13:56:52
     * @param onlineTime
     * @param startDate
     * @param endDate
     * @return com.sinosoft.sinoep.common.util.PageImpl
     */
    @Override
    public List<SysOnlineTime> getOnlineTimeByDate(SysOnlineTime onlineTime, String startDate, String endDate){
        return null;
    }


}
