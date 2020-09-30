package com.sinosoft.sinoep.modules.info.xxfb.services;

import java.util.List;

import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.xxfb.entity.BannerImgVo;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent;

import net.sf.json.JSONObject;


public interface InfoContentService {

    public PageImpl getContentPageList(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime);

    public PageImpl getContentPageList1(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime);

    public InfoContent saveFrom(InfoContent entity);

    public InfoContent getInfoContentById(String id);

    public int delete(String ids);

    public void updateSort(String ids);

    public JSONObject queryQx(String columnId,String contentId);

    public PageImpl getSpList(Pageable pageable,PageImpl pageImpl,String columnName,String title);

	public PageImpl getContentByColumn(PageImpl page, String columnCode, InfoContent info) throws Exception;

	public List<BannerImgVo> getImgsByColumn(String columnCode) throws Exception;

    public int updateZdByConetenIds(String contentIds,String isZd);

    public List<InfoContent> getZdList(String columnId);

    public int orderZd(String ids);

    /**
     * TODO 更新状态位
     * @author 李利广
     * @Date 2018年8月17日 下午2:30:44
     * @param id
     * @param flag
     * @return
     */
    InfoContent updateFlag(String id, String flag) throws Exception;
}
