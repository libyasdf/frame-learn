package com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.entity.Jldtxb;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.services.JldtxbService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("jldtxb")
public class JldtxbController {

    @Autowired
    private JldtxbService jldtxbService;

    @ResponseBody
    @RequestMapping("getlistBootHql")
    public PageImpl getList(PageImpl pageImpl, String name, String unitName){
        Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
        return jldtxbService.getPageListDraft(pageable,pageImpl,name,unitName);
    }

    @ResponseBody
    @RequestMapping("saveForm")
    public Jldtxb saveForm(Jldtxb jldtxb){
        try {
            jldtxb = jldtxbService.saveForm(jldtxb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jldtxb;
    }

    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit(String id){
        JSONObject json = new JSONObject();
        Jldtxb jldtxb = null;
        try {
            jldtxb = jldtxbService.getById(id);
            json.put("flag", "1");
            json.put("data", jldtxb);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("deleteOne")
    public JSONObject deleteOne(String id){
        JSONObject json = new JSONObject();
        int result = jldtxbService.deleteOne(id);
        if (result != 0) {
            json.put("flag", "1");
        }else{
            json.put("flag", "0");
        }
        return json;
    }
}
