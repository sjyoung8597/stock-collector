package com.alring.stock.collector.config.db.model;


import com.alring.stock.collector.config.redis.RedisTemplate;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;



@Slf4j
public abstract class PropertySource {

    private final RedisTemplate redis;

    public PropertySource(String redisHost, int redisPort, boolean cluster) {
        this.redis = new RedisTemplate(redisHost, redisPort, cluster);
    }

    public PropertySource(StringRedisTemplate redis) {
        this.redis = new RedisTemplate(redis);
    }

    public HikariDataSource getDataSource(String databaseName, int maxPoolSize, String initSql) {
        return this.getDataSource(databaseName, maxPoolSize, 5000, (String)initSql);
    }

    public HikariDataSource getDataSource(String databaseName, int maxPoolSize, String initSql, DatabaseModeType modeType) {
        return this.getDataSource(databaseName, maxPoolSize, 5000, initSql, modeType);
    }

    public HikariDataSource getDataSource(String redisKey, String databaseName, int maxPoolSize, String initSql) {
        return this.getDataSource(redisKey, databaseName, maxPoolSize, 5000, initSql, DatabaseModeType.NONE);
    }

    public HikariDataSource getDataSource(String databaseName, int maxPoolSize, int connectTimeout, String initSql) {
        return this.getDataSource("dbms", databaseName, maxPoolSize, connectTimeout, initSql, DatabaseModeType.NONE);
    }

    public HikariDataSource getDataSource(String databaseName, int maxPoolSize, int connectTimeout, String initSql, DatabaseModeType modeType) {
        return this.getDataSource("dbms", databaseName, maxPoolSize, connectTimeout, initSql, modeType);
    }


    public HikariDataSource getDataSource(String redisKey, String databaseName, int maxPoolSize, int connectTimeout, String initSql, DatabaseModeType modeType) {
        DatabaseAccessInfo databaseAccessInfo = null;


        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setJdbcUrl(String.format("jdbc:mariadb://%s/%s?connectTimeout=%d", "221.150.189.117:3306", databaseName, connectTimeout));
        dataSource.setUsername("root");
        dataSource.setPassword("tdksm!@34");
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setMinimumIdle(2);
        dataSource.setIdleTimeout(60000L);
        dataSource.setTransactionIsolation(String.valueOf(2));
        dataSource.setAutoCommit(false);
        dataSource.setConnectionTestQuery(initSql);
        dataSource.setConnectionInitSql(initSql);
        return dataSource;
    }

    public DatabaseModeType getDataBaseReadWrite() {
        return DatabaseModeType.RDBMS;
    }

}
