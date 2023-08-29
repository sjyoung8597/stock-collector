package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.StockPriceDayInfo;
import jooq.alring_dsl.tables.AlringStockPriceDay;
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
public class StockPriceDayRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public StockPriceDayRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.STOCK_PRICE_DAY, key = "'stockPriceDayList'", unless = "#result == null")
    public List<StockPriceDayInfo> selectStockPriceDayList() {
        final AlringStockPriceDay aspd = AlringStockPriceDay.STOCK_PRICE_DAY;

        return rdbmsDslContext.select(
                        aspd.PRICE_NO.as("stockIndustryNo"),
                        aspd.STOCK_NO.as("stockNo"),
                        aspd.CURRENCY_TYPE.as("currencyType"),
                        aspd.PRICE.as("price"),
                        aspd.MARKET_CAP.as("marketCap"),
                        aspd.CURRENCY_RATE.as("currencyRate"),
                        aspd.CREATE_DATE.as("createDate")
                ).from(aspd)
                .fetchInto(StockPriceDayInfo.class);
    }

    @Caching(evict = {
            //@CacheEvict(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustry' + ':' + #countryType"),
            @CacheEvict(value = RedisPrefix.STOCK_PRICE_DAY, key = "'stockPriceDayList'")
    })
    public void insertStockPriceDay(StockPriceDayInfo stockPriceDayInfo) {
        final AlringStockPriceDay aspd = AlringStockPriceDay.STOCK_PRICE_DAY;

        rdbmsDslContext.insertInto(aspd)
                .columns(aspd.PRICE_NO,
                        aspd.STOCK_NO,
                        aspd.CURRENCY_TYPE,
                        aspd.PRICE,
                        aspd.MARKET_CAP,
                        aspd.CURRENCY_RATE,
                        aspd.CREATE_DATE)
                .values(stockPriceDayInfo.getPriceNo(),
                        stockPriceDayInfo.getStockNo(),
                        stockPriceDayInfo.getCurrencyType().getCode(),
                        stockPriceDayInfo.getPrice(),
                        stockPriceDayInfo.getMarketCap(),
                        stockPriceDayInfo.getCurrencyRate(),
                        LocalDateTime.now())
                .returning(aspd.PRICE_NO)
                .fetchOne();
    }
}
