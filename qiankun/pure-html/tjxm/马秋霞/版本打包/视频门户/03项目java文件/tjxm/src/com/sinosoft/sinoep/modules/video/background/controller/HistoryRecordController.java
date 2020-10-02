package com.sinosoft.sinoep.modules.video.background.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.video.background.entity.HistoryRecord;
import com.sinosoft.sinoep.modules.video.background.services.HistoryRecordService;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/video/background/historyRecord")
public class HistoryRecordController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistoryRecordService service;

  
    @LogAnnotation(value = "save",opName = "保存历史查看记录")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject saveFlow(String contentId,String videoId){
        JSONObject json = new JSONObject();
        HistoryRecord record = new HistoryRecord();
        json.put("flag",0);
        try{
        	record = service.save(contentId,videoId);
            json.put("flag","1");
            json.put("msg","保存成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","保存异常！");
            log.error("保存异常！");
        }
        json.put("data", record);
        return json;
    }
    
    /**
     *显示最近观看的前5条历史记录
     */
    @RequestMapping("getLastestHistoryRecord")
    @ResponseBody
    public JSONObject getLastestHistoryRecord(){
        JSONObject json = new JSONObject();
        List<HistoryRecord> list = new ArrayList<HistoryRecord>();
        json.put("flag",0);
        try{
        	list = service.getLastestHistoryRecord();
            json.put("flag","1");
            json.put("msg","获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","保存异常！");
            log.error("获取数据异常！");
        }
        json.put("data", list);
        return json;
    }
    
    /**
     * 视频播放完后更新某个个视频的状态
     * TODO 
     * @author 马秋霞
     * @Date 2018年12月3日 上午10:45:43
     * @return
     */
    @RequestMapping("updateState")
    @ResponseBody
    public JSONObject updateState(String historyId){
        JSONObject json = new JSONObject();
        json.put("flag",0);
        try{
            service.updateState(historyId);
            json.put("flag","1");
            json.put("msg","获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","保存异常！");
            log.error("获取数据异常！");
        }
        
        return json;
    }
    
}