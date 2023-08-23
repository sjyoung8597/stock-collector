package com.alring.stock.collector.config.redis;


public class RedisPrefix {
    public static final String KEY_PREFIX = "stock:";
    public static final String SHARD_DB_PREFIX = KEY_PREFIX + "config.dbms.shard";

    public static final String SHARD = "shard:";
    public static final String COUNTRY = "county:";
    public static final String MARKET = "market:";
    public static final String EXCHANGE_RATE = "exchangeRate:";
    public static final String INDUSTRY = "industry:";

}
