package cn.ltx.jtajpa.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = {"cn.ltx.jtajpa.model.sid","cn.ltx.jtajpa.dao.sid"}, entityManagerFactoryRef = "sidEntityManager", transactionManagerRef = "transactionManager")
public class SidConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    //sid库
    @Primary
    @Bean(name = "sidDataSourceProperties")
    @Qualifier("sidDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.druid.sid")
    public DataSourceProperties sidDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Primary
    @Bean(name = "sidDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid.sid")
    public DataSource sidDataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(sidDataSourceProperties().getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(sidDataSourceProperties().getPassword());
        mysqlXaDataSource.setUser(sidDataSourceProperties().getUsername());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("sid");
        xaDataSource.setBorrowConnectionTimeout(60);
        xaDataSource.setMaxPoolSize(20);
        return xaDataSource;

    }

    @Primary
    @Bean(name = "sidEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean sidEntityManager() throws Throwable {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(sidDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("cn.ltx.jtajpa.model.sid");
        entityManager.setPersistenceUnitName("sidPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }
}
