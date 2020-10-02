package com.key.authority.services;

import com.key.authority.entity.AuthModel;

/**
 * TODO 信息发布职务权限业务接口
 * @author 李利广
 * @Date 2018年9月15日 下午5:38:02
 */
public interface AuthZwqxService {

	/**
	 * 保存权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 上午9:45:46
	 * @param model
	 * @return
	 */
	AuthModel saveInfoZwqx(AuthModel model);

	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午4:36:10
	 * @param surveyId
	 * @return
	 */
	AuthModel getAuthority(String surveyId);

}
