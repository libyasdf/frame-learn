package com.sinosoft.sinoep.common.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * spring注解bean的名字生成器
 * 
 * @author <a href="http://weibo.com/weibowarning">王宁</a><br/>
 * @date 2013-7-5
 */
public class AnnotationBeanNameGenerator extends
		org.springframework.context.annotation.AnnotationBeanNameGenerator {
	/**
	 * 实现类的后缀
	 */
	private static final String IMPLEMENTATION_SUFFIX = "Impl";
	private static final String UNDERLINE="_";
	private static final String DOT="\\.";
	private static final int IMPLEMENTATION_SUFFIX_LENGTH = IMPLEMENTATION_SUFFIX
			.length();
	protected static final Log log = LogFactory
			.getLog(AnnotationBeanNameGenerator.class);

	/**
	 * 更具给定的bean定义返回一个默认的bean名字
	 * <p>
	 * 返回的名字是使用"_"替换"."的类的全路径名但不包含"Impl"后缀，例如:
	 * <li>"com.sinosoft.system.file.service.FileService"=>"com_sinosoft_system_file_service_FileService"
	 * <li>"com.sinosoft.system.file.service.FileServiceImpl"=>"com_sinosoft_system_file_service_FileService"
	 */
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String beanName = definition.getBeanClassName();
		if (beanName.endsWith(IMPLEMENTATION_SUFFIX)) {
			beanName = beanName.substring(0, beanName.length()
					- IMPLEMENTATION_SUFFIX_LENGTH);
		}
		beanName=beanName.replaceAll(DOT, UNDERLINE);
		return beanName;
	}
}
