package com.alring.stock.collector.repository;

import com.alring.stock.collector.config.db.BaseRdbmsConfig;
import com.alring.stock.collector.config.redis.RedisPrefix;
import com.alring.stock.collector.model.entity.IndustryInfo;
import com.alring.stock.collector.model.type.IndustryType;
import jooq.alring_dsl.tables.AlringIndustry;
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
public class IndustryRepository implements ApplicationContextAware {
    private final DSLContext rdbmsDslContext;

    @Setter
    protected ApplicationContext applicationContext;

    public IndustryRepository(@Qualifier(BaseRdbmsConfig.rdbmsJooqContext) DSLContext rdbmsDslContext) {
        this.rdbmsDslContext = rdbmsDslContext;
    }

    @Cacheable(value = RedisPrefix.INDUSTRY, key = "'industry' + ':' + #industryType", unless = "#result == null")
    public IndustryInfo selectIndustry(IndustryType industryType) {
        final AlringIndustry ai = AlringIndustry.INDUSTRY;

        return rdbmsDslContext.select(
                        ai.INDUSTRY_NO.as("industryNo"),
                        ai.INDUSTRY_TYPE.as("industryType"),
                        ai.DESCRIPTION.as("description")
                ).from(ai)
                .where(
                        ai.INDUSTRY_TYPE.eq(industryType.getCode())
                ).fetchOneInto(IndustryInfo.class);
    }

    @Cacheable(value = RedisPrefix.INDUSTRY, key = "'industryList'", unless = "#result == null")
    public List<IndustryInfo> selectIndustryList() {
        final AlringIndustry ai = AlringIndustry.INDUSTRY;

        return rdbmsDslContext.select(
                        ai.INDUSTRY_NO.as("industryNo"),
                        ai.INDUSTRY_TYPE.as("industryType"),
                        ai.DESCRIPTION.as("description")
                ).from(ai)
                .fetchInto(IndustryInfo.class);
    }

    @Caching(evict = {
            @CacheEvict(value = RedisPrefix.INDUSTRY, key = "'industry' + ':' + #industryInfo.industryType"),
            @CacheEvict(value = RedisPrefix.INDUSTRY, key = "'industryList'")
    })
    public void insertIndustry(IndustryInfo industryInfo) {
        final AlringIndustry ai = AlringIndustry.INDUSTRY;

        rdbmsDslContext.insertInto(ai)
                .columns(ai.INDUSTRY_TYPE,
                        ai.DESCRIPTION)
                .values(industryInfo.getIndustryType().getCode(),
                        industryInfo.getDescription()
                )
                .returning(ai.INDUSTRY_NO)
                .fetchOne()
                .get(ai.INDUSTRY_NO);
    }

}
