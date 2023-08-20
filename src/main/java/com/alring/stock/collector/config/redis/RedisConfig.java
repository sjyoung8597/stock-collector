package com.alring.stock.collector.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${redis.cluster}")
    private Boolean cluster;

    @Value("${redis.password}")
    private String password;

    @Bean("redisClient")
    @Primary
    public StringRedisTemplate redis() {
        LettuceConnectionFactory factory = redisClientFactory();
        return new StringRedisTemplate(factory);
    }

    @Bean("redisClientFactory")
    @Primary
    public LettuceConnectionFactory redisClientFactory() {
        return getFactory(host, port);
    }

    private LettuceConnectionFactory getFactory(String host, int port) {
        LettuceConnectionFactory factory;
        if (cluster) {
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
            clusterConfiguration.clusterNode(host, port);
            factory = new LettuceConnectionFactory(clusterConfiguration);
        } else {
            RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(host, port);
            standaloneConfiguration.setPassword(password);
            factory = new LettuceConnectionFactory(standaloneConfiguration);
        }
        return factory;
    }

    @Primary
    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager(ResourceLoader resourceLoader) {
        RedisCacheManager.RedisCacheManagerBuilder builder =
                RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisClientFactory());

        RedisCacheConfiguration configuration
                = RedisCacheConfiguration.defaultCacheConfig(resourceLoader.getClassLoader())
                .disableCachingNullValues()
                .computePrefixWith(key -> key)
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        initCacheConfig(configuration, cacheConfigurations);
        return builder
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    private void initCacheConfig(RedisCacheConfiguration configuration, Map<String, RedisCacheConfiguration> cacheConfigurations) {
        for (RedisKeyInfo key : RedisKeyInfo.values()) {
            cacheConfigurations.put(key.getKeyPrefix(), configuration.entryTtl(key.getTtl()));
        }
    }
}
