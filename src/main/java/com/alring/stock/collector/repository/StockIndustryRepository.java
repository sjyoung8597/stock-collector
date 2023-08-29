package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.StockIndustryInfo;
import jooq.alring_dsl.tables.AlringStockIndustry;
import lombok.Setter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockIndustryRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public StockIndustryRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustryList'", unless = "#result == null")
    public List<StockIndustryInfo> selectStockIndustryList() {
        final AlringStockIndustry asi = AlringStockIndustry.STOCK_INDUSTRY;

        return rdbmsDslContext.select(
                        asi.STOCK_INDUSTRY_NO.as("stockIndustryNo"),
                        asi.STOCK_NO.as("stockNo"),
                        asi.INDUSTRY_NO.as("industryNo")
                ).from(asi)
                .fetchInto(StockIndustryInfo.class);
    }

    @Caching(evict = {
            //@CacheEvict(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustry' + ':' + #countryType"),
            @CacheEvict(value = RedisPrefix.STOCK_INDUSTRY, key = "'stockIndustryList'")
    })
    public void insertStockIndustry(StockIndustryInfo stockIndustryInfo) {
        final AlringStockIndustry asi = AlringStockIndustry.STOCK_INDUSTRY;

        rdbmsDslContext.insertInto(asi)
                .columns(asi.STOCK_INDUSTRY_NO,
                        asi.STOCK_NO,
                        asi.INDUSTRY_NO)
                .values(stockIndustryInfo.getStockIndustryNo(),
                        stockIndustryInfo.getStockNo(),
                        stockIndustryInfo.getIndustryNo())
                .returning(asi.STOCK_INDUSTRY_NO)
                .fetchOne();
    }
}
