package com.alring.stock.collector.config.db;

import com.alring.stock.collector.config.db.model.PropertySource;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


public class DataSourceConfig {

    @Value("${datasource.maxPoolSize}")
    int maxPoolSize;
    protected final Environment env;

    private final PropertySource propertySource;
    private static final String poolNamePrefix = "hikari-";

    public DataSourceConfig(Environment env, PropertySource propertySource) {
        this.env = env;
        this.propertySource = propertySource;
    }


    protected DataSource dataSourceReadWrite(String databaseName) {
        String sql = "SELECT 1";
        HikariDataSource dataSource = propertySource.getDataSource(databaseName, maxPoolSize, sql, propertySource.getDataBaseReadWrite());
        dataSource.setPoolName(poolNamePrefix + "rw-" + databaseName);
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    protected SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
        localSessionFactoryBuilder.scanPackages("com.alring.stock");
        localSessionFactoryBuilder.addProperties(getHibernateProperties());
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    protected PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        //txManager.setDataSource(dataSource);
        return txManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        return properties;
    }
}
