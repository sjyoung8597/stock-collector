package com.alring.stock.collector.config.redis;

import java.time.Duration;

/**
 * Created by jysong@crossangle.io 2023-03-02
 */
public enum RedisKeyInfo {
    SHARD(RedisPrefix.SHARD, Duration.ofMinutes(30L)),
    BLOCK(RedisPrefix.BLOCK, Duration.ofSeconds(60L)),

    ;

    private final String keyPrefix;
    private final Duration ttl;

    RedisKeyInfo(String keyPrefix, Duration ttl) {
        this.keyPrefix = keyPrefix;
        this.ttl  = ttl;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public Duration getTtl() {
        return ttl;
    }

    public static RedisKeyInfo getByKeyPrefix(String keyPrefix) {
        for (RedisKeyInfo value : values()) {
            if(value.keyPrefix.equals(keyPrefix)) {
                return value;
            }
        }
        return null;
    }
}
