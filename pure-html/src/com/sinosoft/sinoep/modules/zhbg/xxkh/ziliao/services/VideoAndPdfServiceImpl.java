package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.services;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.dao.VideoAndPdfDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.VideoAndPdf;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 资料对应文件的service实现
 * TODO 
 * @author 王磊
 * @Date 2018年8月21日 下午5:20:34
 */
@Service
@Transactional
public class VideoAndPdfServiceImpl implements VideoAndPdfService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//资料对应文件的dao
	@Autowired
	private VideoAndPdfDao videoAndPdfDao;

	/**
	 * 保存资料对应的文件
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:16:39
	 * @param videoAndPdf
	 * @return
	 */
	@Override
	public VideoAndPdf saveForm(VideoAndPdf videoAndPdf) {
		videoAndPdf.setCreJuId(UserUtil.getCruJuId());
		videoAndPdf.setCreJuName(UserUtil.getCruJuName());
		videoAndPdf.setCreChushiId(UserUtil.getCruChushiId());
		videoAndPdf.setCreChushiName(UserUtil.getCruDeptName());
		videoAndPdf.setCreDeptId(UserUtil.getCruDeptId());
		videoAndPdf.setCreDeptName(UserUtil.getCruDeptName());
		videoAndPdf.setCreUserId(UserUtil.getCruUserId());
		videoAndPdf.setCreUserName(UserUtil.getCruUserName());
		videoAndPdf.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		videoAndPdf.setVisible(CommonConstants.VISIBLE[1]);
		return videoAndPdfDao.save(videoAndPdf);
	}

	/**
	 * 根据资料id查询视频或文档
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:18:01
	 * @param infoId
	 * @return
	 */
	@Override
	public List<VideoAndPdf> getByInfoId(String infoId) {
		return videoAndPdfDao.getFilesByInfoId(infoId);
	}

	/**
	 * 根据文件id逻辑删除文件
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:20:01
	 * @param fileId
	 * @return
	 */
	@Override
	public int delete(String fileId) {
		int n = 0;
		try {
			String delSql = "update VideoAndPdf q set q.visible="+CommonConstants.VISIBLE[0]+" where q.fileId=?";
			n = videoAndPdfDao.update(delSql,fileId);
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("逻辑删除资料的文件异常，文件id："+fileId);
		}
		return n;
	}

}
