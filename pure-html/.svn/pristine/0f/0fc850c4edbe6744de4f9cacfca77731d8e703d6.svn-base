package com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.constant.EmConstants;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.entity.OrderDetil;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.orderdetil.services.OrderDetilService;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;
import com.sinosoft.sinoep.modules.system.component.export.excel.poi.util.ExportDataToExcel;
import net.sf.json.JSONObject;

/**
 * 订单详情controller
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月14日 上午9:44:11
 */
@Controller
@RequestMapping("/mypage/wmgl/index/OrderDetil")
public class OrderDetilController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	OrderDetilService service;
	
	/**
	 * 获取某次外卖订单的门类
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月15日 下午4:09:24
	 * @param infoId
	 * @return
	 */
	 @LogAnnotation(opType = OpType.QUERY,opName = " 获取订单的门类")
	 @RequestMapping("getOrderCategory")
	 @ResponseBody
	 public JSONObject getOrderCategory(String takeOutId) {
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("flg", "1");
		 List<Category> list = new ArrayList<Category>();
		 try {
			list = service.getOrderCategory(takeOutId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flg", "1");
		}
		jsonObj.put("data", list);
		return jsonObj;
	 }
	 	
	 	/**
	 	 * 查询此次外卖的商品汇总信息
	 	 * TODO 
	 	 * @author 马秋霞
	 	 * @Date 2019年5月15日 下午5:24:35
	 	 * @param pageImpl
	 	 * @param takeOutId
	 	 * @return
	 	 */
	    @LogAnnotation(opType = OpType.QUERY,opName = "查询此次外卖的商品汇总信息  ")
	    @ResponseBody
	    @RequestMapping("getCollectInfo")
	    public PageImpl getCollectInfo(PageImpl pageImpl, String takeOutId,String categoryId){
	        try {
	            if(StringUtils.isNotBlank(takeOutId)){
	            	pageImpl = service.getCollectInfo(pageImpl,takeOutId,categoryId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            log.error(e.getMessage(),e);
	        }
	        return pageImpl;
	    }
	    
	    /**
	     * 商品汇总的导出
	     * TODO 
	     * @author 马秋霞
	     * @Date 2019年5月17日 上午11:33:35
	     * @param userId
	     * @param overTimeType
	     * @param timeRange
	     * @param subflag
	     * @param request
	     * @param response
	     */
		@LogAnnotation(opType = OpType.QUERY,opName = "导出excel")
		@RequestMapping(value = "/export", method = RequestMethod.GET)
		public void exportDutyLog(String takeOutId,String categoryId,HttpServletRequest request, HttpServletResponse response) {
			try {
				ExportDataToExcel exportData = new ExportDataToExcel();
				List<ExportBean> list = new ArrayList<ExportBean>();
				ExportBean exportBean = new ExportBean();
				List<OrderDetil> collectList = service.getList(takeOutId,categoryId);// 查询数据
				exportBean.setExportList(collectList);
				exportBean.setGetMethod(EmConstants.GET_METHOD);
				exportBean.setHeader(EmConstants.HEADER);
				exportBean.setSheetTitle(EmConstants.SHEET_TITLE[0]);
				exportBean.setSheetColWidth(EmConstants.SHEET_COL_WIDTH);//列宽默认20，可以自己指定
				exportBean.setFontName(EmConstants.FONT_NAME);//默认宋体
				exportBean.setTextFontSize(EmConstants.TEXT_FONT_SIZE);//文本字体大小默认11
				exportBean.setTitleFontSize(EmConstants.TITLE_FONT_SIZE);//表头字体大小默认14
				exportBean.setTitleRowHight(EmConstants.TITLE_ROW_HEIGHT);//表头行高默认600
				list.add(exportBean);
				exportData.excelProject(response, list, EmConstants.FILE_NAME[0]);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}		
		}
		
		/**
		 * 根据订单id查询订单详情
		 * TODO 
		 * @author 马秋霞
		 * @Date 2019年5月21日 下午4:08:47
		 * @param pageImpl
		 * @param overTimeType
		 * @param timeRange
		 * @param subflag
		 * @return
		 */
		@ResponseBody
		@RequestMapping("getlistBootHql")
		@LogAnnotation(opType = OpType.QUERY,opName = "查询订单详情")
		public PageImpl getList(PageImpl pageImpl, String orderId) {
			try {
				Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
					pageImpl = service.getPageList(pageable, pageImpl, orderId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
			return pageImpl;
		}
		
		/**
		 * TODO 修改商品数量
		 * @author 李颖洁  
		 * @date 2019年5月23日下午2:23:34
		 * @param orderDetil
		 * @return JSONObject
		 */
	    @LogAnnotation(opType = OpType.UPDATE,opName = "修改商品数量")
	    @RequestMapping("update")
	    @ResponseBody
	    public JSONObject updateOrderDetil(OrderDetil orderDetil) {
	        JSONObject json = new JSONObject();
	        json.put("flag", "0");
	        if (StringUtils.isNotBlank(orderDetil.getId())) {
	            try {
	            	orderDetil = service.updateOrderDetil(orderDetil);
	                json.put("flag", "1");
	                json.put("data", orderDetil);
	            } catch (Exception e) {
	                e.printStackTrace();
	                log.error(e.getMessage(),e);
	            }
	        }
	        return json;
	    }
	    
	    /**
		 * 删除一条记录
		 * @param id
		 * @return
		 */
		@LogAnnotation(opType = OpType.DELETE,opName = "删除商品数据")
		@ResponseBody
		@RequestMapping("delete")
		public JSONObject deleteOrderDetil(String id){
			JSONObject json = new JSONObject();
			json.put("flag", "0");
			try {
				int result = service.deleteById(id);
				if (result != 0) {
					json.put("flag", "1");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
			return json;
		}
}
