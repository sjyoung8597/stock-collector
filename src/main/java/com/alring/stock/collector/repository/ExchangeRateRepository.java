package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.ExchangeRateInfo;
import com.alring.stock.collector.model.type.CurrencyType;
import jooq.alring_dsl.tables.AlringExchangeRate;
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
public class ExchangeRateRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public ExchangeRateRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.EXCHANGE_RATE, key = "'exchangeRate' + ':' + #currencyType", unless = "#result == null")
    public ExchangeRateInfo selectExchangeRate(CurrencyType currencyType) {
        final AlringExchangeRate aer = AlringExchangeRate.EXCHANGE_RATE;

        return rdbmsDslContext.select(
                        aer.EXCHANGE_NO.as("exchangeNo"),
                        aer.COUNTRY_TYPE.as("countryType"),
                        aer.CURRENCY_TYPE.as("currencyType"),
                        aer.EX_CURRENCY_TYPE.as("exCurrencyType"),
                        aer.CURRENCY_RATE.as("currencyRate"),
                        aer.EX_CURRENCY_TYPE.as("exCurrencyRate"),
                        aer.DESCRIPTION.as("description"),
                        aer.CREATE_DATE.as("createDate")
                ).from(aer)
                .where(
                        aer.CURRENCY_TYPE.eq(currencyType.getCode())
                ).fetchOneInto(ExchangeRateInfo.class);
    }

    @Cacheable(value = RedisPrefix.EXCHANGE_RATE, key = "'exchangeRateList'", unless = "#result == null")
    public List<ExchangeRateInfo> selectExchangeRateList() {
        final AlringExchangeRate aer = AlringExchangeRate.EXCHANGE_RATE;

        return rdbmsDslContext.select(
                        aer.EXCHANGE_NO.as("exchangeNo"),
                        aer.COUNTRY_TYPE.as("countryType"),
                        aer.CURRENCY_TYPE.as("currencyType"),
                        aer.EX_CURRENCY_TYPE.as("exCurrencyType"),
                        aer.CURRENCY_RATE.as("currencyRate"),
                        aer.EX_CURRENCY_TYPE.as("exCurrencyRate"),
                        aer.DESCRIPTION.as("description"),
                        aer.CREATE_DATE.as("createDate")
                ).from(aer)
                .fetchInto(ExchangeRateInfo.class);
    }

    @Caching(evict = {
            @CacheEvict(value = RedisPrefix.EXCHANGE_RATE, key = "'exchangeRate' + ':' + #exchangeRateInfo.currencyType"),
            @CacheEvict(value = RedisPrefix.EXCHANGE_RATE, key = "'exchangeRateList'")
    })
    public void insertExchangeRate(ExchangeRateInfo exchangeRateInfo) {
        final AlringExchangeRate aer = AlringExchangeRate.EXCHANGE_RATE;

        rdbmsDslContext.insertInto(aer)
                .columns(aer.COUNTRY_TYPE,
                        aer.CURRENCY_TYPE,
                        aer.EX_CURRENCY_TYPE,
                        aer.CURRENCY_RATE,
                        aer.EX_CURRENCY_RATE,
                        aer.DESCRIPTION,
                        aer.CREATE_DATE)
                .values(exchangeRateInfo.getCountryType().getCode(),
                        exchangeRateInfo.getCurrencyType().getCode(),
                        exchangeRateInfo.getExCurrencyType().getCode(),
                        exchangeRateInfo.getCurrencyRate(),
                        exchangeRateInfo.getExCurrencyRate(),
                        exchangeRateInfo.getDescription(),
                        LocalDateTime.now()
                )
                .returning(aer.EXCHANGE_NO)
                .fetchOne()
                .get(aer.EXCHANGE_NO);
    }

}
