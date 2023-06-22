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

    public static final String dbXangleName = "xangle";

    public static final String dataSource = "xangleDataSource";

    public static final String rdbmsJooqContext = "xangleRdbms";

    public static final String xangleSessionFactory = "xangleSessionFactory";

    public static final String xangleTransactionManager = "xangleTransactionManager";

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
        return dataSourceReadWrite(dbXangleName);
    }

    @Bean(name = xangleSessionFactory)
    public SessionFactory xangleSessionFactory() {
        return sessionFactory(dataSource());
    }

    @Bean(name = xangleTransactionManager)
    public PlatformTransactionManager xangleTransactionManager() {
        return transactionManager(xangleSessionFactory());
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
