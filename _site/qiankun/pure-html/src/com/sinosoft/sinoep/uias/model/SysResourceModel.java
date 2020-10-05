/**
 * Copyright 2016 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.uias.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 daigp
 * @since 2016年12月2日
 */
@Entity
@Table(name = "sys_resource")
public class SysResourceModel {
    @Id
    @GeneratedValue
    private Long id;//主键
    
    @SuppressWarnings("unused")
	private String tree_id;

}
