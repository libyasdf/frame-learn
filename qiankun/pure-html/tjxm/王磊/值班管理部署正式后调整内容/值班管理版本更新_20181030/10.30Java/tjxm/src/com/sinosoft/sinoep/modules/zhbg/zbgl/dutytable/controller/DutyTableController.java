package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.common.util.repeatformvalidator.SameUrlData;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcelOfMergeCells;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.MergeCells;
import com.sinosoft.sinoep.modules.system.config.holidayset.service.HolidaySetService;
import com.sinosoft.sinoep.modules.zhbg.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.constant.EmConstants;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.services.DutyTableService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.vo.dutyTableListVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *   
 * TODO 值班表管理控制层,值班表管理传本单位的id查询本单位的值班表信息，
 * @author 王富康
 * @date 2018/7/11 14:42
 * @param
 * @return
 */
@Controller
@RequestMapping("zhbg/zbgl/dutytable")
public class DutyTableController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DutyTableService dutyTableService;

    @Autowired
    private HolidaySetService holidaySetService;

    /**
     * @Author 王富康
     * @Description //TODO 值班表列表查询
     * @Date 2018/7/17 16:52
     * @Param [dut]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "查询值班表列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getList(DutyTable dut){
        JSONObject json = new JSONObject();
        try {
            List<DutyTable> list = dutyTableService.getList(dut);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total", list.size());
            JSONArray array = new JSONArray();
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
            jsonConfig.setExcludes(new String[] { "visible", "creTime", "updateTime", "creUserId", "creUserName",
                    "updateUserId", "updateUserName", "creDeptId", "creDeptName" });// 将所需忽略字段加到数组中
            array = JSONArray.fromObject(list, jsonConfig);
            data.put("rows", array);
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }
        log.info(json.toString());
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 总值班室值班查询，值班查询接口
     * @Date 2018/7/31 14:49
     * @Param [dut]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "总值班室值班查询，值班查询")
    @RequestMapping(value = "getListOfDutySearch", method = RequestMethod.GET)
    @ResponseBody
    public PageImpl list(PageImpl pageImpl, DutyTable dut){
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = dutyTableService.getListOfDutySearch(pageable,pageImpl,dut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增保存接口
     * @Date 2018/7/30 11:53
     * @Param [con]
     * @return net.sf.json.JSONObject
     **/
    @SameUrlData
    @LogAnnotation(value = "save",opName = "新增值班表信息信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(DutyTable dut) {

        JSONObject json = new JSONObject();

        try {
            dut = dutyTableService.save(dut);
            json.put("flag", 1);
            json.put("data", dut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 保存list数据
     * @Date 2018/8/7 10:43
     * @Param [dut, privatePone, commonPhone, isSecurity, zbNoticeId]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "保存list数据")
    @RequestMapping("saveList")
    @ResponseBody
    public JSONObject saveList( @RequestBody dutyTableListVo dlv) {

        JSONObject json = new JSONObject();

        try {
            List<DutyTable> dut = dutyTableService.saveList(dlv.getDutyTables(),dlv.getPrivatePhone(),dlv.getCommonPhone(),dlv.getIsSecurity(),dlv.getZbNoticeId());
            json.put("flag", 1);
            json.put("data", dut);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据id获取一条值班信息
     * @Date 2018/7/30 11:54
     * @Param [id]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据id获取一条值班信息")
    @RequestMapping(value = "getDutyTableById")
    @ResponseBody
    public JSONObject getDutyTableById(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            DutyTable st = dutyTableService.getDutyTableById(id);
            json.put("flag","1");
            json.put("data",st);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改考勤表数据
     * @Date 2018/7/19 20:07
     * @Param [dut]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "修改考勤表列表")
    @RequestMapping("updateDutyTable")
    @ResponseBody
    public JSONObject updateDutyTable(DutyTable dut) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(dut.getId())) {
            try {
                dutyTableService.updateDutyTable(dut);
                json.put("flag", "1");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改考勤表数据(普网电话，专网电话)
     * @Date 2018/7/19 20:07
     * @Param [dut]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "修改普网，专网电话")
    @RequestMapping("updateDutyTablePhone")
    @ResponseBody
    public JSONObject updateDutyTablePhone(String id, String commonPhone, String privatePhone) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(id)) {
            try {
                int result = dutyTableService.updateDutyTablePhone(id,commonPhone,privatePhone);
                json.put("flag", "1");
                json.put("result", result);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 导出值班表模版
     * @Date 2018/7/19 20:05
     * @Param [dutyType, request, response]
     * @return void
     **/
    @RequestMapping(value = "exportBqData", method = RequestMethod.POST)
    public void exportDutyLog(String dutyType, String year_month, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            ExportDataToExcelOfMergeCells exportData = new ExportDataToExcelOfMergeCells();
            List<ExportBean> list = new ArrayList<ExportBean>();
            ExportBean exportBean = new ExportBean();
            List<DutyTable> dutyLoglist = new ArrayList<DutyTable>();//supplementSignService.getsByDate(startDate, endDate, supplementSignType);// 查询数据
            //获取当月的天数-(前提是每月一号之后才会填写模板)
            String temp[]=year_month.split("-");//根据url传过来的年月分割，获得年月（年月之间必须为“-”）
            int year = Integer.parseInt(temp[0]);
            int month = Integer.parseInt(temp[1]);
            int dayCount = DateUtil.getDaysByYearMonth(year,month);//获取当月的天数
            String firstDay = DateUtil.getFirstDayOfMonth(year,month);//该月份的第一天
            String lastDay = DateUtil.getLastDayOfMonth(year,month);//该月份的最后一天
            Map<Object, Object> holiday = holidaySetService.getHolidaySets(firstDay,lastDay);//查詢当月的节假日
            for (int i = 1 ; i <= dayCount ; i++) {
                String day = "";
                String monthA = "";
                if(i<10){
                    day = "0" + i;
                }else{
                    day = "" + i;
                }
                if(month<10){
                    monthA = "0" + month;
                }else{
                    monthA = "" + month;
                }
            //生成当月的列表
                String date = ""+year+"-"+monthA+"-"+day+"";
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(date));
                int dow1 =calendar.get(Calendar.DAY_OF_WEEK);
                String weekday = "";
                if(dow1 == 1){
                    weekday = "日";
                }else if(dow1 == 2){
                    weekday = "一";
                }else if(dow1 == 3){
                    weekday = "二";
                }else if(dow1 == 4){
                    weekday = "三";
                }else if(dow1 == 5){
                    weekday = "四";
                }else if(dow1 == 6){
                    weekday = "五";
                }else if(dow1 == 7){
                    weekday = "六";
                }
                if(holiday.containsKey(date)){
                    //节假日中包含当天
                    if("1".equals(holiday.get(date))&& "two".equals(dutyType)){
                        //如果节假日设置设置当天为节假日,并且为两班倒
                        DutyTable dut1 = new DutyTable("1",""+date+"",""+weekday+"","","昼","","","","","");
                        dutyLoglist.add(dut1);
                        DutyTable dut = new DutyTable("1",""+date+"",""+weekday+"","","夜","","","","","");
                        dutyLoglist.add(dut);
                    }else if("1".equals(holiday.get(date))&& "three".equals(dutyType)){
                        //如果节假日设置设置当天为节假日,并且为三班倒
                        DutyTable dut2 = new DutyTable("1",""+date+"",""+weekday+"","","早","","","","","");
                        dutyLoglist.add(dut2);
                        DutyTable dut1 = new DutyTable("1",""+date+"",""+weekday+"","","中","","","","","");
                        dutyLoglist.add(dut1);
                        DutyTable dut = new DutyTable("1",""+date+"",""+weekday+"","","晚","","","","","");
                        dutyLoglist.add(dut);
                    }else{
                        //包含周六日设为工作日的信息都为夜班值班
                        DutyTable dut = new DutyTable("1",""+date+"",""+weekday+"","","夜","","","","","");
                        dutyLoglist.add(dut);
                    }
                }else if(dow1==1 || dow1==7){
                    //正常周六日
                    if("two".equals(dutyType)){
                        //两班
                        DutyTable dut1 = new DutyTable("1",""+date+"",""+weekday+"","","昼","","","","","");
                        dutyLoglist.add(dut1);
                        DutyTable dut = new DutyTable("1",""+date+"",""+weekday+"","","夜","","","","","");
                        dutyLoglist.add(dut);
                    }else if("three".equals(dutyType)){
                        //三班
                        DutyTable dut2 = new DutyTable("1",""+date+"",""+weekday+"","","早","","","","","");
                        dutyLoglist.add(dut2);
                        DutyTable dut1 = new DutyTable("1",""+date+"",""+weekday+"","","中","","","","","");
                        dutyLoglist.add(dut1);
                        DutyTable dut = new DutyTable("1",""+date+"",""+weekday+"","","晚","","","","","");
                        dutyLoglist.add(dut);
                    }
                }else{
                    //正常工作日夜班
                    DutyTable dut = new DutyTable("1",""+date+"",""+weekday+"","","夜","","","","","");
                    dutyLoglist.add(dut);
                }
            }
            exportBean.setExportList(dutyLoglist);
            exportBean.setGetMethod(EmConstants.GET_METHOD);
            exportBean.setHeader(EmConstants.HEADER);
            exportBean.setSheetTitle(EmConstants.SHEET_TITLE[0]);
            exportBean.setSheetColWidth(EmConstants.SHEET_COL_WIDTH);// 列宽默认20，可以自己指定
            exportBean.setFontName(EmConstants.FONT_NAME);// 默认宋体
            exportBean.setTextFontSize(EmConstants.TEXT_FONT_SIZE);// 文本字体大小默认11
            exportBean.setTitleFontSize(EmConstants.TITLE_FONT_SIZE);// 表头字体大小默认14
            exportBean.setTitleRowHight(EmConstants.TITLE_ROW_HEIGHT);// 表头行高默认600
            list.add(exportBean);
            String ChushiName = "";
            if(UserUtil.getCruChushiName() != null){
                ChushiName = UserUtil.getCruChushiName();
            }
            List<MergeCells> mergeCells = new ArrayList<>();

            MergeCells mergeCell = new MergeCells();
            mergeCell.setCellLine(1);//需要合并的列
            mergeCell.setStarRow(1);//从第几行开始合并
            mergeCells.add(mergeCell);

            MergeCells mergeCell1 = new MergeCells();
            mergeCell1.setCellLine(2);//需要合并的列
            mergeCell1.setStarRow(1);//从第几行开始合并
            mergeCells.add(mergeCell1);

            MergeCells mergeCell2 = new MergeCells();
            mergeCell2.setCellLine(8);//需要合并的列
            mergeCell2.setStarRow(1);//从第几行开始合并
            mergeCells.add(mergeCell2);

            MergeCells mergeCell3 = new MergeCells();
            mergeCell3.setCellLine(9);//需要合并的列
            mergeCell3.setStarRow(1);//从第几行开始合并
            mergeCells.add(mergeCell3);

            exportData.excelProject(response, list, ChushiName+""+year_month+"值班表.xls", mergeCells);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @Author 王富康
     * @Description //TODO 导入值班表
     * @Date 2018/7/19 20:06
     * @Param [request, file]
     * @return net.sf.json.JSONObject
     **/
    @RequestMapping(value = "/importInfo",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importInfo(HttpServletRequest request, @RequestParam("file") MultipartFile file,String id){
        String filePath = request.getSession().getServletContext().getRealPath("/")+"upload"+file.getOriginalFilename();
        JSONObject json = new JSONObject();
        String msg = "导入成功！";
        try {
            msg = dutyTableService.importInfo(filePath,file,id);
//            msg = dutyTableService.importInfo(file);
        } catch (Exception e) {
            msg = "导入失败！";
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }

        json.put("msg",msg);
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 多次导入时删除考勤表数据
     * @Date 2018/7/20 11:46
     * @Param [id, key]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "delete",opName = "多次导入时删除考勤表数据")
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject delete(String reportId ) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(reportId)) {
            try {
                dutyTableService.delete(reportId);
                json.put("flag", "1");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询值班管理员这个业务角色的人
     * @Date 2018/8/9 14:43
     * @Param [reportId]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "多次导入时删除考勤表数据")
    @RequestMapping(value = "queryReportAdmin", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryReportAdmin() {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
            try {
                json = dutyTableService.queryReportAdmin();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        return json;
    }
}
