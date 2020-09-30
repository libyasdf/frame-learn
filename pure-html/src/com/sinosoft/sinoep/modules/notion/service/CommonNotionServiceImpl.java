/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.notion.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.modules.notion.dao.CommonNotionDao;
import com.sinosoft.sinoep.modules.notion.model.CommonNotion;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 goulijun
 * @since 2017年5月23日
 */
@Service
public class CommonNotionServiceImpl implements CommonNotionService {
    @Autowired
    CommonNotionDao commonNotionDao;

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * 
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<CommonNotion> getList(HttpServletRequest request) {
        HqlHelper hqlHelper = new HqlHelper(CommonNotion.class);
        String userid = UserUtil.getCruUserId();
        String orgid = UserUtil.getCruOrgId();
        // String sysid = CookieUtil.getSysId(request);
        hqlHelper.addCondition("userid = ?", userid);
        hqlHelper.addCondition("orgid = ?", orgid);
        //hqlHelper.addCondition("sysid = ?", sysid);
        List<CommonNotion> list = (List<CommonNotion>) commonNotionDao.queryList(hqlHelper);
        return list;
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * 
     */
    @Override
    public Map<String, Object> add(HttpServletRequest request, String notion) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        String result = "failed";
        String msg = "常用意见添加失败";

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdateStr = sf.format(new Date(System.currentTimeMillis()));

        String userid = UserUtil.getCruUserId();
        String usernm = "";
        CommonNotion model = new CommonNotion();
        try {
            usernm = URLDecoder.decode(UserUtil.getCruUserName(), "UTF-8");
            String orgid = UserUtil.getCruOrgId();
            //String sysid = CookieUtil.getSysId(request);

            model.setNotion(notion);
            model.setUserid(userid);
            model.setUsername(usernm);
            model.setOrgid(orgid);
            //model.setSysid(sysid);
            model.setCreatime(nowdateStr);

            commonNotionDao.add(model);

            result = "success";
            msg = "常用意见添加成功";
        }
        catch (UnsupportedEncodingException e) {
            result = "failed";
            msg = "用户名解码错误";
            e.printStackTrace();
        }

        returnmap.put("result", result);
        returnmap.put("msg", msg);
        returnmap.put("model", model);

        return returnmap;
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * 
     */
    @Override
    public Map<String, Object> delete(String id) {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        String result = "failed";
        String msg = "常用意见删除失败";

        commonNotionDao.deleteById(id);

        result = "success";
        msg = "常用意见删除成功";

        returnmap.put("result", result);
        returnmap.put("msg", msg);
        return returnmap;
    }

}
