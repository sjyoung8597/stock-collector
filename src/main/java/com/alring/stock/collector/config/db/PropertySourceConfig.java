package com.alring.stock.collector.config.db;

import com.alring.stock.collector.config.db.model.PropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
public class PropertySourceConfig {

    private final Environment env;

    private final StringRedisTemplate redis;

    public PropertySourceConfig(Environment env, StringRedisTemplate redis) {
        this.env = env;
        this.redis = redis;
    }

    @Bean
    public PropertySource propertySource() {
        return new PropertySource(redis) { };
    }
}
