package com.sinosoft.sinoep.common.configurer;

import com.sinosoft.sinoep.common.util.CryptoUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * TODO 数据库配置文件解密类
 * @author 李利广
 * @Date 2019年03月26日 23:20:40
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 数据库密码
     */
    private static final String JDBC_PWD = "jdbc.password";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        try {
            String password = props.getProperty(JDBC_PWD);
            if (StringUtils.isNotBlank(password)) {
                if(password.startsWith("encrypt:")){
                    String pwd = password.substring(password.indexOf(":") + 1);
                    props.setProperty(JDBC_PWD, CryptoUtil.decryptStr(pwd));
//                    props.setProperty(JDBC_PWD, AesUtil.cbcDecrypt(pwd));
                }else{
                    props.setProperty(JDBC_PWD, password);
                }
            }
            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            throw new BeanInitializationException(e.getMessage());
        }
    }
}
