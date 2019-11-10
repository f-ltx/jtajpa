package cn.ltx.jtajpa.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "cn.ltx.jtajpa.*.lee", entityManagerFactoryRef = "leeEntityManager", transactionManagerRef = "transactionManager")
public class LeeConfig {
    @Value("${spring.datasource.druid.lee.url}")
    private String url;
    @Value("${spring.datasource.druid.lee.username}")
    private String username;
    @Value("${spring.datasource.druid.lee.password}")
    private String password;
    @Value("${spring.datasource.druid.lee.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.druid.lee.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.druid.lee.maxActive}")
    private Integer maxActive;
    @Value("${spring.datasource.druid.lee.filters}")
    private String filters;

    @Bean(name = "leeDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid.lee")
    public DataSource leeDataSource() throws SQLException {
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl(url);
        druidXADataSource.setUsername(username);
        druidXADataSource.setPassword(password);
        druidXADataSource.setMinIdle(minIdle);
        druidXADataSource.setMaxActive(maxActive);
        druidXADataSource.setInitialSize(initialSize);
        druidXADataSource.setFilters(filters);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("lee");
        xaDataSource.setXaDataSource(druidXADataSource);
        return xaDataSource;
    }

    @Bean(name = "leeEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean leeEntityManager(EntityManagerFactoryBuilder builder) throws Throwable {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        //显示sql
        hibernateJpaVendorAdapter.setShowSql(true);
        //自动生成/更新表
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        //设置数据库类型
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

        LocalContainerEntityManagerFactoryBean leePersistenceUnit = builder.dataSource(leeDataSource())
                .properties(properties)
                .packages("cn.ltx.jtajpa.model.lee")
                .persistenceUnit("leePersistenceUnit")
                .build();
        leePersistenceUnit.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        return leePersistenceUnit;
    }
}
