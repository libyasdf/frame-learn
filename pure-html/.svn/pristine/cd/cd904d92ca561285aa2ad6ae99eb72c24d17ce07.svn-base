package com.sinosoft.sinoep.modules.system.config.toggle.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.modules.system.config.toggle.entity.SysToggle;
import com.sinosoft.sinoep.modules.system.config.toggle.services.SysToggleService;
import net.sf.json.JSONObject;

/**
 * 
 * TODO 开关Action层
 * @author 王富康
 * @Date 2018年5月11日 上午11:24:05
 */
@Controller
@RequestMapping("/system/config/toggle")
public class SysToggleAction {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysToggleService service;
    

    /**
     * 查询开关列表
     * @param pageImpl 分页
     * @param name 开关的名称
     * @param key 开关标识，作为开关在业务逻辑中的唯一标识，业务逻辑根据此标识可以获取到该开关
     * @param describe 开关描述.
     * @return
     */
	@ResponseBody
	@RequestMapping("list")
	public PageImpl list(PageImpl pageImpl,String name, String key, String describe){
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.list(pageable,pageImpl,name,key,describe);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	
    /**
     * 添加，修改开关(修改时key不能被修改)
     * @param ST  锁对象
     * @return
     */
	@SameUrlData
    @RequestMapping("saveorupdate")
    @ResponseBody
    public JSONObject saveorupdate(SysToggle ST,String key) {
        JSONObject json = new JSONObject();
        try {
    		ST = service.save(ST,key);
			json.put("flag", 1);
			json.put("data", ST);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        
        return json;
    }
    
    /**
	 * 开关时，key值不能重复
	 * @param st
	 * @param checkItem
	 * @return
	 */
	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject check(SysToggle st,String checkItem){
		JSONObject json = new JSONObject();
		boolean valid = service.check(st,checkItem);
		json.put("valid",valid);
		return json;
	}
    
    /**
	 * 根据ID获取一条开关数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view")
	@ResponseBody
	public JSONObject view(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			SysToggle st = service.findById(id);
			json.put("flag","1");
			json.put("data",st);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

    /**
     * 删除开关
     * @param id  开关id
     * @param key 开关一标识
     * @return 
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET) 
    @ResponseBody
    public JSONObject delete(String id ,String key) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
		if (StringUtils.isNotBlank(id)) {
			try {
					service.delete(id);
					json.put("flag", "1");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
		}
        return json;
    }
    
    /**
     * 修改开关是否打开
     * @param id  锁id
     * @param isOpen  开关是否打开
     * @return
     */
    @RequestMapping("updateToggle")
    @ResponseBody
    public JSONObject updateToggle(String id,String isOpen) {
    	JSONObject json = new JSONObject();
        json.put("flag", "0");
		if (StringUtils.isNotBlank(id)) {
			try {
					service.updateToggle(id, isOpen);
					json.put("flag", "1");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
		}
        return json;
    }
    
    /**
     * 修改开关是否可编辑
     * @param id  锁id
     * @param state  默认值为0。 各取值的定义为： 0：正常使用 1：开关被锁定(不可编辑) 2：暂停使用 3：开关被删除
     * @return
     */
    @RequestMapping("updateToggleState")
    @ResponseBody
    public JSONObject updateToggleState(String id,String state) {
    	JSONObject json = new JSONObject();
        json.put("flag", "0");
		if (StringUtils.isNotBlank(id)) {
			try {
					service.updateToggleState(id, state);
					json.put("flag", "1");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
		}
        return json;
    }
    
    /**
   	 * 根据key获取一条开关打开状态
   	 * @param key
   	 * @return
   	 */
   	@RequestMapping(value = "getToggleIsOpenByKey")
   	@ResponseBody
   	public JSONObject getToggleIsOpenByKey(String key) {
   		JSONObject json = new JSONObject();
   		json.put("flag", "0");
   		try {
   			List<SysToggle> list = service.getToggleIsOpenByKey(key);
   			for(int i=0;i<list.size();i++) {
   				json.put("flag","1");
   				json.put("isOpen",list.get(i).getIsOpen());
   			}
   		} catch (Exception e) {
   			log.error(e.getMessage(),e);
   		}
   		return json;
   	}
}
