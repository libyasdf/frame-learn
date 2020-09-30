package com.sinosoft.sinoep.modules.system.notice.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerify;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO 通知通告审批DAO接口
 * @author 杜建松
 * @Date 2019年1月8日 上午11:37:11
 */
public interface SysNoticeVerifyDao extends BaseRepository<SysNoticeVerify, String> {

    public SysNoticeVerify findByNoticeDeptIdAndVisible(String noticeDeptId, String visible);

    public SysNoticeVerify findByCreUserIdAndVisible(String creUserId, String visible);
}
