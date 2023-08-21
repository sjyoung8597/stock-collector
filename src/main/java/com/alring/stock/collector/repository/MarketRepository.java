package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.CountryInfo;
import com.alring.stock.collector.model.entity.MarketInfo;
import com.alring.stock.collector.model.type.CountryType;
import jooq.alring_dsl.tables.AlringCountry;
import jooq.alring_dsl.tables.AlringMarket;
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

/**
 * Created by jysong@crossangle.io 2023/08/21
 */

@Repository
public class MarketRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public MarketRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.COUNTRY, key = "'marketList'", unless = "#result == null")
    public List<MarketInfo> selectMarketList() {
        final AlringMarket am = AlringMarket.MARKET;

        return rdbmsDslContext.select(
                        am.MARKET_NO.as("marketNo"),
                        am.COUNTRY_TYPE.as("countryType"),
                        am.MARKET_TYPE.as("marketType"),
                        am.DESCRIPTION.as("description")
                ).from(am)
                .fetchInto(MarketInfo.class);
    }

    @Caching(evict = {
            //@CacheEvict(value = RedisPrefix.COUNTRY, key = "'market' + ':' + #countryType"),
            @CacheEvict(value = RedisPrefix.COUNTRY, key = "'marketList'")
    })
    public void insertMarket(MarketInfo marketInfo) {
        final AlringMarket am = AlringMarket.MARKET;

        rdbmsDslContext.insertInto(am)
                .columns(am.COUNTRY_TYPE,
                        am.MARKET_TYPE,
                        am.DESCRIPTION)
                .values(marketInfo.getCountryType().getCode(),
                        marketInfo.getMarketType().getCode(),
                        marketInfo.getDescription())
                .returning(am.MARKET_NO)
                .fetchOne();
    }
}
