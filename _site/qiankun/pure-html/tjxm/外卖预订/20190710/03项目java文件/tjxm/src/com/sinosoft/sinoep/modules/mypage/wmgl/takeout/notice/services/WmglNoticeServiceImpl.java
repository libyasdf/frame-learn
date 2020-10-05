package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.dao.WmglNoticeDao;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.notice.entity.WmglNotice;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO 外卖管理-基础配置service
 * @author 李颖洁  
 * @date 2019年5月7日  下午3:47:45
 */
@Service
public class WmglNoticeServiceImpl implements WmglNoticeService{

    @Autowired
    private WmglNoticeDao dao;
    
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
    public PageImpl list(Pageable pageable, PageImpl pageImpl, String isValid,String startDate,String endDate) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append("	from WmglNotice t where t.visible = ? ");
        para.add(CommonConstants.VISIBLE[1]);
        //拼接条件
        if (StringUtils.isNotBlank(isValid)) {
            querySql.append("   and t.isValid =?");
            para.add(isValid);
        }
        if (StringUtils.isNotBlank(startDate)) {
            querySql.append("   and t.creTime >= ?");
            para.add(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
        	querySql.append("   and t.creTime <= ?");
        	para.add(endDate);
        }
        //拼接排序语句
        querySql.append("  order by t.creTime desc ) ");

        Page<WmglNotice> page = dao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

    /**
     * TODO 保存信息
     * @author 李颖洁  
     * @date 2019年5月14日下午1:57:55
     * @param notice
     * @return
     */
	@Override
	public WmglNotice saveForm(WmglNotice notice){
		WmglNotice newNotice;
		if (StringUtils.isBlank(notice.getId())) {
			newNotice= new WmglNotice(notice.getId(),notice.getContent(),notice.getIsValid(),notice.getStatus());
			notice = dao.save(newNotice);
		} else{
			newNotice = dao.findOne(notice.getId());
			newNotice.setContent(notice.getContent());
			newNotice.setIsValid(notice.getIsValid());
			newNotice.setStatus(notice.getStatus());
			newNotice.setUpdateTime(DateUtil.COMMON.getDateText(new Date()));
			newNotice.setUpdateUserId(UserUtil.getCruUserId());
			newNotice.setUpdateUserName(UserUtil.getCruUserName());
			notice = dao.save(newNotice);
		}
		return notice;
	}

	/** 
	 * TODO 根据id获取数据 
	 * @author 李颖洁  
	 * @date 2019年5月7日下午2:53:44
	 * @param id
	 * @return WmglNotice
	 */
	@Override
	public WmglNotice getById(String id) {
		return dao.findOne(id);
	}
    
    /**
     * 获取最新的一条通知公告
     * TODO 
     * @author 马秋霞
     * @Date 2019年5月14日 上午9:11:24
     * @return
     */
	@Override
	public String getLast() {
		StringBuilder sql = new StringBuilder(" select CONTENT content from (select CONTENT from wmgl_notice where visible = ? and is_valid = ? and status='1' order by cre_time desc) where rownum = 1 ");
		String notice = jdbcTemplate.queryForObject(sql.toString(), String.class,CommonConstants.VISIBLE[1],WmglConstants.VALID[1]);
		return notice;
	}

	/** 
	 * TODO 根据id删除一条通知
	 * @author 李颖洁  
	 * @date 2019年5月14日下午4:09:59
	 * @param id
	 * @return int
	 */
	@Override
	public int deleteById(String id) {
		return dao.update("update WmglNotice t set t.visible = ? where t.id = ?",CommonConstants.VISIBLE[0], id);
	}

	
}
