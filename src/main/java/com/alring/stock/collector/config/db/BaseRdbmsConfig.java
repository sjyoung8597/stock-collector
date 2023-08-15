package com.alring.stock.collector.config.db;

import com.alring.stock.collector.config.db.model.PropertySource;
import com.alring.stock.collector.config.jooq.BaseJooqExecuteListener;
import org.hibernate.SessionFactory;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
public class BaseRdbmsConfig extends DataSourceConfig {

    public BaseRdbmsConfig(Environment env, PropertySource propertySource) {
        super(env, propertySource);
    }

    public static final String dbStockName = "stock";

    public static final String dataSource = "stockDataSource";

    public static final String rdbmsJooqContext = "stockRdbms";

    public static final String stockSessionFactory = "stockSessionFactory";

    public static final String stockTransactionManager = "stockTransactionManager";

    @Bean(name = rdbmsJooqContext)
    public DefaultDSLContext rdbmsJooqContext() {
        DefaultConfiguration dslConfig = new DefaultConfiguration();
        dslConfig.set(new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource())));
        dslConfig.set(new DefaultExecuteListenerProvider(new BaseJooqExecuteListener()));
        dslConfig.set(SQLDialect.MARIADB);
        return new DefaultDSLContext(dslConfig);
    }

    @Bean(name = dataSource)
    public DataSource dataSource() {
        return dataSourceReadWrite(dbStockName);
    }

    @Bean(name = stockSessionFactory)
    public SessionFactory stockSessionFactory() {
        return sessionFactory(dataSource());
    }

    @Bean(name = stockTransactionManager)
    public PlatformTransactionManager stockTransactionManager() {
        return transactionManager(stockSessionFactory());
    }

//    @Bean(name = blockDataSource)
//    public DataSource blockDataSource() {
//        return blockDataSourceReadWrite(dbBlockName);
//    }

//    @Bean(name = shardDataSource)
//    @Primary
//    public DataSource shardDataSource() {
//        return dataSourceReadWrite(shardDbName);
//    }
}
