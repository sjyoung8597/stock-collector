package com.alring.stock.collector.config.redis;

/**
 * Created by jysong@crossangle.io 2023-03-03
 */
public class RedisPrefix {
    public static final String KEY_PREFIX = "stock:";
    public static final String SHARD_DB_PREFIX = KEY_PREFIX + "config.dbms.shard";

    public static final String SHARD = "shard:";
    public static final String BLOCK = "block:";

}
