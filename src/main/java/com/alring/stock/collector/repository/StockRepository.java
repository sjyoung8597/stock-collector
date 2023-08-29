package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.StockInfo;
import jooq.alring_dsl.tables.AlringStock;
import lombok.Setter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class StockRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public StockRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustryList'", unless = "#result == null")
    public List<StockInfo> selectStockList() {
        final AlringStock as = AlringStock.STOCK_;

        return rdbmsDslContext.select(
                        as.STOCK_NO.as("stockNo"),
                        as.STOCK_NAME.as("stockName"),
                        as.MARKET_NO.as("marketNo"),
                        as.STOCK_LISTED.as("stockListed"),
                        as.STATUS.as("status"),
                        as.UPDATE_DATE.as("updateDate"),
                        as.CREATE_DATE.as("createDate")
                ).from(as)
                .fetchInto(StockInfo.class);
    }

    @Caching(evict = {
            //@CacheEvict(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustry' + ':' + #countryType"),
            @CacheEvict(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustryList'")
    })
    public void insertStockIndustry(StockInfo stockInfo) {
        final AlringStock as = AlringStock.STOCK_;

        rdbmsDslContext.insertInto(as)
                .columns(as.STOCK_NO,
                        as.STOCK_NAME,
                        as.MARKET_NO,
                        as.STOCK_LISTED,
                        as.STATUS,
                        as.UPDATE_DATE,
                        as.CREATE_DATE)
                .values(stockInfo.getStockNo(),
                        stockInfo.getStockName(),
                        stockInfo.getMarketNo(),
                        stockInfo.getStockLited(),
                        stockInfo.getStatus().getCode(),
                        LocalDateTime.now(),
                        LocalDateTime.now())
                .returning(as.STOCK_NO)
                .fetchOne();
    }
}
