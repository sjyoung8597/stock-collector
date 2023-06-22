package com.alring.stock.collector.config.redis;

import com.crossangle.uniswap.model.exception.PropertySourceNotFoundException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by jysong@crossangle.io 2023-03-02
 */
public class RedisTemplate {
    private final StringRedisTemplate redis;
    private static final String URL_KEY_PREFIX = "xangle:config.";

    public RedisTemplate(String redisHost, int redisPort, boolean cluster) {
        JedisConnectionFactory factory;

        factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);

        factory.afterPropertiesSet();
        this.redis = new StringRedisTemplate(factory);
    }

    public RedisTemplate(StringRedisTemplate redis) {
        if (redis == null) {
            throw new IllegalArgumentException("JedisCluster object is null");
        } else {
            this.redis = redis;
        }
    }

    public String get(String key) {
        String value = (String)this.redis.opsForValue().get(URL_KEY_PREFIX + key);
        if (isEmpty(value)) {
            throw new PropertySourceNotFoundException();
        } else {
            return value;
        }
    }

    public Map<String, String> hGetAllRequired(String key) {
        Map<String, String> map = new HashMap(this.redis.opsForHash().entries(URL_KEY_PREFIX + key));
        if (map != null && map.size() != 0) {
            return map;
        } else {
            throw new PropertySourceNotFoundException();
        }
    }

    public Map<String, String> hGetAll(String key) {
        Map<Object, Object> entries = this.redis.opsForHash().entries(URL_KEY_PREFIX + key);
        return (Map)(entries != null && !entries.isEmpty() ? new HashMap(entries) : Collections.emptyMap());
    }

    public boolean isEmpty(String... values) {
        return Stream.of(values).anyMatch((str) -> {
            return str == null || "".equals(str);
        });
    }
}
