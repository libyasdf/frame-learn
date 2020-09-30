package com.sinosoft.sinoep.modules.system.notice.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerify;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO 通知通告发布人、审批人DAO接口
 * @author 杜建松
 * @Date 2019年1月8日 上午11:37:11
 */
public interface SysNoticeVerifyUserDao extends BaseRepository<SysNoticeVerifyUser, String> {

    @Transactional
    @Modifying
    @Query("update SysNoticeVerifyUser t set t.visible = '0' where t.verifyId = ?1")
    public int delVisible(String verifyId);

}
