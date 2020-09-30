package com.sinosoft.sinoep.common.jedis.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
public class RedisClusterService {

    @Autowired
    private JedisCluster jedisCluster;

    public RedisClusterService() {
    }

    public Object get(String key) {
        return this.jedisCluster.get(key);
    }

    public List<String> getList(String key) {
        return this.jedisCluster.lrange(key, 0L, -1L);
    }

    public void set(String key, String value) {
        this.jedisCluster.set(key, value);
    }

    public void set(String key, List<String> list) {
        this.jedisCluster.rpush(key, (String[])list.toArray(new String[0]));
    }

    public void set(String key, Map<String, String> map) {
        this.jedisCluster.hmset(key, map);
    }

    public Map<String, String> getMap(String key) {
        return this.jedisCluster.hgetAll(key);
    }

    public void del(String key) {
        this.jedisCluster.del(key);
    }

    public JedisCluster getJedisCluster() {
        return this.jedisCluster;
    }

    public void expire(String key, Integer seconds) {
        this.jedisCluster.expire(key, seconds);
    }
}
