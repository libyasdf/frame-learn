package com.sinosoft.sinoep.common.jedis.factory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {
    private Resource addressConfig;
    private String addressKeyPrefix;
    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;
    private GenericObjectPoolConfig genericObjectPoolConfig;
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

    public JedisClusterFactory() {
    }

    @Override
    public JedisCluster getObject() throws Exception {
        return this.jedisCluster;
    }

    @Override
    public Class<? extends JedisCluster> getObjectType() {
        return this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Set<HostAndPort> parseHostAndPort() throws Exception {
        try {
            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());
            Set<HostAndPort> haps = new HashSet();
            Iterator var4 = prop.keySet().iterator();

            while(var4.hasNext()) {
                Object key = var4.next();
                if (((String)key).startsWith(this.addressKeyPrefix)) {
                    String val = (String)prop.get(key);
                    boolean isIpPort = this.p.matcher(val).matches();
                    if (!isIpPort) {
                        throw new IllegalArgumentException("ip 或 port 不合法");
                    }

                    String[] ipAndPort = val.split(":");
                    HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                    haps.add(hap);
                }
            }

            return haps;
        } catch (IllegalArgumentException var9) {
            throw var9;
        } catch (Exception var10) {
            throw new Exception("解析 jedis 配置文件失败", var10);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> haps = this.parseHostAndPort();
        this.jedisCluster = new JedisCluster(haps, this.timeout, this.maxRedirections, this.genericObjectPoolConfig);
    }

    public void setAddressConfig(Resource addressConfig) {
        this.addressConfig = addressConfig;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public void setAddressKeyPrefix(String addressKeyPrefix) {
        this.addressKeyPrefix = addressKeyPrefix;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }
}
