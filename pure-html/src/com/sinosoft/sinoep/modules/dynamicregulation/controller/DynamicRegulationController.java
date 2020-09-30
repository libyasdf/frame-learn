package com.sinosoft.sinoep.modules.dynamicregulation.controller;

import com.sinosoft.sinoep.modules.dynamicregulation.service.DynamicRegulationService;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.Help;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.tool.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dynamicRegulationController")
public class DynamicRegulationController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DynamicRegulationService dynamicRegulationService;

	/**
	 *根据表名返回列集合
	 * @author 子火
	 * @Date 2019-2-14 09:24:27
	 */
	@RequestMapping("/tableLine")
	@ResponseBody
	public String tableLine(String tablename) {
		String json=null;
		try {
			String tableLine=dynamicRegulationService.tableLine(tablename);
			json=Help.returnClass(200, "查询成功",tableLine);
		} catch (Exception e) {
			json=Help.returnClass(300, "tableLine方法-异常报错", Helper.exceptionToString(e));//异常信息太多会占内存,所以此
		}
		return json;
	}
    /**
     *根据表名返回动态调整表数据
     * @author 子火
     * @Date 2019-2-14 15:06:50
     */
    @RequestMapping("/tableLineD")
    @ResponseBody
    public String tableLineD(String tablename) {
        String json=null;
        try {
			List<Map<String, Object>> tableLineK=dynamicRegulationService.tableLineD("DYNAMIC_REGULATION");
            List<Map<String, Object>> tableLineY=dynamicRegulationService.tableLineD(tablename);
            Map resultMAP=new HashMap();
			resultMAP.put("k",tableLineK);
			resultMAP.put("y",tableLineY);
            if(tableLineY.size()==0){
                json=Help.returnClass(300, "无查询数据","查询结果为0");
            }else{
                json=Help.returnClass(200, "查询成功",resultMAP);
            }

        } catch (Exception e) {
            json=Help.returnClass(500, "tableLineD方法-异常报错", Helper.exceptionToString(e));
        }
        return json;
    }
	
}
