package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.CountryInfo;
import com.alring.stock.collector.model.type.CountryType;
import jooq.alring_dsl.tables.AlringCountry;
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
public class CountryRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public CountryRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.COUNTRY, key = "'country' + ':' + #countryType", unless = "#result == null")
    public CountryInfo selectCountry(CountryType countryType) {
        final AlringCountry ac = AlringCountry.COUNTRY;

        return rdbmsDslContext.select(
                        ac.COUNTRY_TYPE.as("countryType"),
                        ac.DESCRIPTION.as("description")
                ).from(ac)
                .where(
                        ac.COUNTRY_TYPE.eq(countryType.getCode())
                ).fetchOneInto(CountryInfo.class);
    }

    @Cacheable(value = RedisPrefix.COUNTRY, key = "'countryList'", unless = "#result == null")
    public List<CountryInfo> selectCountryList() {
        final AlringCountry ac = AlringCountry.COUNTRY;

        return rdbmsDslContext.select(
                        ac.COUNTRY_TYPE.as("countryType"),
                        ac.DESCRIPTION.as("description")
                ).from(ac)
                .fetchInto(CountryInfo.class);
    }

    @Caching(evict = {
            @CacheEvict(value = RedisPrefix.COUNTRY, key = "'country' + ':' + #countryType"),
            @CacheEvict(value = RedisPrefix.COUNTRY, key = "'countryList'")
    })
    public void insertCountry(CountryType countryType) {
        final AlringCountry ac = AlringCountry.COUNTRY;

        rdbmsDslContext.insertInto(ac)
                .columns(ac.COUNTRY_TYPE,
                        ac.DESCRIPTION)
                .values(countryType.getCode(),
                        countryType.getDescription())
                .returning(ac.COUNTRY_TYPE)
                .fetchOne();
    }

}
