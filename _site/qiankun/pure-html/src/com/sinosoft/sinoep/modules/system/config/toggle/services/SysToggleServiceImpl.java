package com.sinosoft.sinoep.modules.system.config.toggle.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lowagie.text.pdf.BarcodePDF417;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.toggle.constant.ConfigConsts;
import com.sinosoft.sinoep.modules.system.config.toggle.dao.SysToggleDao;
import com.sinosoft.sinoep.modules.system.config.toggle.entity.SysToggle;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;


/**
 * 
 * TODO 开关业务层service实现类
 * @author 王富康
 * @Date 2018年5月11日 上午11:43:40
 */
@Service
public class SysToggleServiceImpl implements SysToggleService {

    @Autowired
    private SysToggleDao sysToggleDao;
    
    private JdbcTemplate JdbcTemplate;

   
    /**
     * 查询开关列表
     * @param pageable 分页
     * @param name 开关的名称
     * @param key 开关标识，作为开关在业务逻辑中的唯一标识，业务逻辑根据此标识可以获取到该开关
     * @param describe 开关描述.
     * @return
     */
    @Override
	public PageImpl list(Pageable pageable,PageImpl pageImpl,String name, String key, String describe) {
    	StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		
		querySql.append("select new com.sinosoft.sinoep.modules.system.config.toggle.entity.SysToggle(t.id, t.name, t.key, t.isOpen, t.describe, t.toggleDefault,t.state) ");
		querySql.append("	from SysToggle t where t.state != "+ConfigConsts.TOGGLE_VISIBLE[3]+" ");
		//拼接条件
		if (StringUtils.isNotBlank(name)) {
			querySql.append("   and t.name like ?");
			para.add("%"+name+"%");
		}
		if (StringUtils.isNotBlank(key)) {
			querySql.append("   and t.key like ?");
			para.add("%"+key+"%");
		}
		if (StringUtils.isNotBlank(describe)) {
			querySql.append("   and t.describe like ?");
			para.add("%"+describe+"%");
		}
		
		
		//拼接排序语句
			querySql.append("  order by t.state Asc,t.creTime desc ) ");
		
        Page<SysToggle> page = sysToggleDao.query(querySql.toString(), pageable,para.toArray());
		return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
    
    
    /**
	 * 根据ID获取一条开关数据
	 * @param id
	 * @return
	 */
    @Override
    public SysToggle findById(String id) {
    	return sysToggleDao.findOne(id);
    }
    
    /**
     * 添加，修改开关(修改时key不能被修改)
     * @param ST  锁对象
     * @return
     */
    @Override
    public SysToggle save(SysToggle ST,String ideaName) {
		if (StringUtils.isBlank(ST.getId())) {
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			ST.setCreTime(creTime);//创建时间
			ST.setCreUserId(UserUtil.getCruUserId());//创建id
			ST.setState(ConfigConsts.TOGGLE_VISIBLE[0]);
			ST = sysToggleDao.save(ST);
		}else{
			SysToggle oldST = sysToggleDao.findOne(ST.getId());
			oldST.setKey(ST.getKey());//唯一标识
			oldST.setName(ST.getName());//开关名称
			oldST.setIsOpen(ST.getIsOpen());//开关是否处于打开状态，0为关闭，1为打开。添加开关时未指定值，该字段默认为0.
			oldST.setDescribe(ST.getDescribe());//描述
			oldST.setState(ST.getState());//该开关数据的状态，比如删除，暂停使用，锁定等，默认值为0。 各取值的定义为： 0：正常使用 1：开关被锁定(不可编辑) 2：暂停使用 3：开关被删除 
			String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			oldST.setUpdateTime(updateTime);//更新更新时间
			oldST.setUpdateUserId(UserUtil.getCruUserId());//更新人id
			ST.setState(ST.getState());
			sysToggleDao.save(oldST);
		}
		return ST;
    }
    
    /**
	 * 
	 * TODO 新增开关时，key值不能重复,编辑时id存在，key覆盖。
	 * @author 王富康
	 * @Date 2018年5月14日 上午11:14:44
	 * @param st
	 * @param checkItem
	 * @return
	 */
	public boolean check(SysToggle st,String checkItem) {
		boolean valid = true;
 		List<SysToggle> toggles = sysToggleDao.getToggleByKey(st.getKey().trim());
 		if(toggles.size() > 0){
			for (SysToggle keys:toggles) {
				if (keys.getKey().trim().equals(st.getKey().trim()) && !keys.getId().equals(st.getId())){
					valid = false;
				}
			}
		}
		return valid;
	}
	
    /**
     * 删除开关
     * @param id  开关id
     * @return
     */
    @Override
    public int delete(String id) {
    	String jpql = "update SysToggle t set t.state = ? where t.id = ?";
		return sysToggleDao.update(jpql, ConfigConsts.TOGGLE_VISIBLE[3], id);
    }
    
    /**
     * 修改开关是否打开
     * @param id  锁id
     * @param isOpen  开关是否打开
     * @return
     */
    public int updateToggle(String id,String isOpen) {
    	String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String UpdateUserId = UserUtil.getCruUserId();//创建id
    	String jpql ="update SysToggle t set t.isOpen = ?,updateUserId=?,updateTime=? where id = ?";
    	int is_open = ConfigConsts.TOGGLE_ISOPEN[0] ;
    	if("1".equals(isOpen)) {
    		//打开开关
    		is_open = ConfigConsts.TOGGLE_ISOPEN[1];
    	}
    	return sysToggleDao.update(jpql, is_open,UpdateUserId,updateTime,id);
    }
    
    /**
	 * 根据key获取一条开关打开状态
	 * @param key
	 * @return
	 */
	public List<SysToggle> getToggleIsOpenByKey(String key) {
		/*String sql = "select t.is_open from sys_toggle t where t.key = '"+key+"'";*/
		List<SysToggle> list = sysToggleDao.findByKey(key);
		return list;
	}
    
    /**
     * 修改开关状态，是否可编辑
     * @param id  锁id
     * @param state  默认值为0。 各取值的定义为： 0：正常使用 1：开关被锁定(不可编辑) 2：暂停使用 3：开关被删除
     * @return
     */
    public int updateToggleState(String id,String state) {
    	String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String UpdateUserId = UserUtil.getCruUserId();//创建id
    	String jpql ="update SysToggle t set t.state = ?,updateUserId=?,updateTime=? where id = ?";
    	return sysToggleDao.update(jpql, state,UpdateUserId,updateTime, id);
    }
}
