package com.sinosoft.sinoep.common.util.repeatformvalidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 一个用户 相同url 同时提交 相同数据 验证  
 * TODO 
 * @author 王富康
 * @Date 2018年5月11日 下午5:59:11
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME) 
public @interface SameUrlData {

}
