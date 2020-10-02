package com.sinosoft.sinoep.modules.mypage.wmgl.index.order.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.constant.WmglConstants;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.order.entity.OrderInfo;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.order.services.OrderInfoService;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.services.TakeOutInfoService;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import net.sf.json.JSONObject;

/**
 * 外卖订单controller
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月14日 上午9:44:11
 */
@Controller
@RequestMapping("/mypage/wmgl/index/order")
public class OrderInfoController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OrderInfoService service;
	@Autowired
	TakeOutInfoService outInfoService;
	
	/**
	 * 下单操作
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 上午9:56:31
	 * @param takeOutId
	 * @return
	 */
	 @LogAnnotation(opType = OpType.SAVE,opName = "预定页面的下单操作")
	 @RequestMapping("placeOrder")
	 @ResponseBody
	public JSONObject  placeOrder(String takeOutId,String title,String cartgoodsInfo,String phone,String orderId) {
		 JSONObject jonObj = new JSONObject();
			jonObj.put("flag", "1");
			String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String msg = "";
			try {
				 //查询该订单是否已超过最晚下单时间，是否已下单
				List<Map<String,Object>>  info =  outInfoService.findById(takeOutId);
				if(info.size()>0 && cruDate.compareTo(info.get(0).get("DEADLINETIME").toString())>0) {
					jonObj.put("flag", "2");
					msg = "已超过了最晚下单时间";
					jonObj.put("msg", msg);
				}else if(info.size()>0 && StringUtils.isNotBlank(info.get(0).get("ORDERID").toString()) && StringUtils.isBlank(orderId)){
					jonObj.put("flag", "3");
					msg = "已下单，不能再次下单";
					jonObj.put("msg", msg);
				}else {
					//查询这些商品是否有超过了库存
					synchronized(this){
						JSONObject jsonObj = outInfoService.surpassRepertory(takeOutId,cartgoodsInfo,orderId);
						String flag = jsonObj.getString("flag");
						if("1".equals(flag)) {//不存在超过库存，或商品已被删除的情况
							//下单操作
							 orderId = service.placeOrder(takeOutId,title,phone,cartgoodsInfo,orderId);
						}else {
							//存在超过库存，商品已被删除或售罄的情况
							jonObj.put("flag", flag);
							jonObj.put("msg", jsonObj.getString("msg"));
						}
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
				jonObj.put("flag", "0");
			}
			jonObj.put("orderId", orderId);
			return jonObj;
	}
	 
	   
		
	 /**
	  * TODO 查询订单列表数据，分页
	  * @author 李颖洁  
	  * @date 2019年5月15日下午1:35:31
	  * @param pageImpl
	  * @param status 订单状态
	  * @param cardNo 门禁卡号
	  * @param orderNum 订单号
	  * @param takeOutId 订单id
	  * @return PageImpl
	  */
    @LogAnnotation(opType = OpType.QUERY,opName = "查询订单列表数据，分页")
    @ResponseBody
    @RequestMapping("getOrderList")
    public PageImpl list(PageImpl pageImpl, String status,String cardNo,String orderNum,String takeOutId){
        try {
            if(StringUtils.isNotBlank(takeOutId)){
            	pageImpl = service.getOrderList(pageImpl,status,cardNo,orderNum,takeOutId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }
 
    /**
	  * TODO 查询订单列表数据，分页
	  * @author 李颖洁  
	  * @date 2019年5月15日下午1:35:31
	  * @param pageImpl
	  * @param status 订单状态
	  * @param cardNo 门禁卡号
	  * @param orderNum 订单号
	  * @param takeOutId 订单id
	  * @return PageImpl
	  */
   @LogAnnotation(opType = OpType.QUERY,opName = "查询订单列表数据，分页")
   @ResponseBody
   @RequestMapping("getGoodsList")
   public PageImpl getGoodsList(PageImpl pageImpl, String status,String cardNo,String orderNum,String takeOutId){
       try {
           if(StringUtils.isNotBlank(takeOutId)){
           	pageImpl = service.getOrderList(pageImpl,status,cardNo,orderNum,takeOutId);
           }
       } catch (Exception e) {
           e.printStackTrace();
           log.error(e.getMessage(),e);
       }
       return pageImpl;
   }
   
   /**
    * TODO 导出订单汇总表
    * @author 李颖洁  
    * @date 2019年5月16日下午5:20:40
    * @param status
    * @param cardNo
    * @param orderNum
    * @param takeOutId
    * @param response
    * @return void
    */
	@LogAnnotation(opType = OpType.QUERY,opName = "导出订单汇总列表")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void exportOrderList(String status1,String cardNo,String orderNum,String takeOutId, HttpServletResponse response) {
		try {
			ExportDataToExcel exportData = new ExportDataToExcel();
			List<ExportBean> list = new ArrayList<ExportBean>();
			ExportBean exportBean = new ExportBean();
			List<OrderInfo> orderList = service.getOrderDate(status1,cardNo,orderNum,takeOutId);
			exportBean.setExportList(orderList);
			exportBean.setGetMethod(WmglConstants.GET_METHOD);
			exportBean.setHeader(WmglConstants.HEADER);
			exportBean.setSheetTitle(WmglConstants.SHEET_TITLE[0]);
			exportBean.setSheetColWidth(WmglConstants.SHEET_COL_WIDTH);
			exportBean.setFontName(WmglConstants.FONT_NAME);
			exportBean.setTextFontSize(WmglConstants.TEXT_FONT_SIZE);
			exportBean.setTitleFontSize(WmglConstants.TITLE_FONT_SIZE);
			exportBean.setTitleRowHight(WmglConstants.TITLE_ROW_HEIGHT);
			list.add(exportBean);
			exportData.excelProject(response, list, WmglConstants.FILE_NAME[0]);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}		
	}
   
   /**
    * 修改订单状态
    * TODO 
    * @author 马秋霞
    * @Date 2019年5月16日 上午10:16:58
    * @param id
    * @param status
    * @return
    */
   @LogAnnotation(opType = OpType.UPDATE,opName = "修改订单状态")
   @RequestMapping("updateState")
   @ResponseBody
   public JSONObject  updateState(String ids,String orderUserId,String status) {
	   JSONObject jonObj = new JSONObject();
		jonObj.put("flag", "1"); 
		try {
			service.updateState(ids,orderUserId, status);
		} catch (Exception e) {
			jonObj.put("flag", "0"); 
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return jonObj;
   }
   
   /**
    * 根据订单id查询订单信息
    * TODO 
    * @author 马秋霞
    * @Date 2019年5月20日 上午9:21:53
    * @param id
    * @return
    */
	@LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
	@ResponseBody
	@RequestMapping("edit")
	public JSONObject edit(String id) {
		JSONObject json = new JSONObject();
		OrderInfo info = new OrderInfo();
		try {
			info = service.getById(id);
			json.put("flag", "1");
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", "0");
		}
		json.put("data", info);
		return json;
	}
	
	/**
	 * TODO 获取当前用户订单列表
	 * @author 李颖洁  
	 * @date 2019年5月22日下午5:13:09
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取当前用户订单列表")
	@ResponseBody
	@RequestMapping("getMyOrderList")
	public PageImpl getMyOrderList(PageImpl pageImpl,String timeRange) {
		String startDate = "";
		String endDate = "";
		try {
			if (StringUtils.isNotBlank(timeRange)) {
				startDate = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				endDate = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
			pageImpl = service.getMyOrderList(pageable,pageImpl,startDate,endDate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageImpl;
	}
	
	/**
	 * 获取商品总个数
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年6月5日 下午3:22:44
	 * @param takeOutId
	 * @param title
	 * @param cartgoodsInfo
	 * @param phone
	 * @return
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "获取商品总个数")
	@ResponseBody
	@RequestMapping("getGoodsNum")
	public JSONObject  getGoodsNum(String takeOutId,String categoryId) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flg", "1");
		Integer goodsNum=0;
		try {
			goodsNum = service.getGoodsNum(takeOutId,categoryId);
		} catch (Exception e) {
			jsonObj.put("flg", "0");
		}
		jsonObj.put("goodsNum", goodsNum);
		return jsonObj;
	}
	
	/**
	 * TODO 删除订单
	 * @author 李颖洁  
	 * @date 2019年6月12日上午11:02:21
	 * @param id
	 * @return JSONObject
	 */
   @LogAnnotation(opType = OpType.DELETE,opName = "删除订单")
   @RequestMapping("delete")
   @ResponseBody
   public JSONObject  deleteOrder(String id) {
	   JSONObject jonObj = new JSONObject();
		jonObj.put("flag", "0"); 
		try {
			int num = service.delete(id);
			if(num>0){
				jonObj.put("flag", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return jonObj;
   }
   
   /**
    * 获取当前用户某次外卖的订单id和手机号
    * TODO 
    * @author 马秋霞
    * @Date 2019年6月13日 下午5:00:24
    * @param takeOutId
    * @return
    */
   @LogAnnotation(opType = OpType.QUERY,opName = "获取当前用户的订单id和手机号")
   @RequestMapping("getOrderIdAndPhone")
   @ResponseBody
   public JSONObject  getOrderIdAndPhone(String takeOutId) {
	   JSONObject jonObj = new JSONObject();
	   JSONObject data = new JSONObject();
		jonObj.put("flag", "0"); 
		try {
			data = service.getOrderIdAndPhone(takeOutId);
			jonObj.put("flag", "1"); 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		jonObj.put("data", data);
		return jonObj;
   }
   
}
