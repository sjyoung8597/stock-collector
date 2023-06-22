package com.alring.stock.collector.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by jysong@crossangle.io 2023/03/13
 */
@EnableTransactionManagement
@Configuration
public class DataBaseConfig {
    @Primary
    @Bean(name = "chainedTransactionManager")
    public PlatformTransactionManager transactionManager(
            PlatformTransactionManager blockTransactionManager,
            PlatformTransactionManager xangleTransactionManager
    ) {
        return new ChainedTransactionManager(
                blockTransactionManager, xangleTransactionManager
        );
    }
}
