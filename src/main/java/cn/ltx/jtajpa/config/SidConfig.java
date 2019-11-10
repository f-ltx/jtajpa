package cn.ltx.jtajpa.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = {"cn.ltx.jtajpa.model.sid", "cn.ltx.jtajpa.dao.sid"}, entityManagerFactoryRef = "sidEntityManager", transactionManagerRef = "transactionManager")
public class SidConfig {
    @Value("${spring.datasource.druid.sid.url}")
    private String url;
    @Value("${spring.datasource.druid.sid.username}")
    private String username;
    @Value("${spring.datasource.druid.sid.password}")
    private String password;
    @Value("${spring.datasource.druid.sid.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.druid.sid.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.druid.sid.maxActive}")
    private Integer maxActive;
    @Value("${spring.datasource.druid.sid.filters}")
    private String filters;

    @Primary
    @Bean(name = "sidDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid.sid")
    public DataSource sidDataSource() throws SQLException {
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl(url);
        druidXADataSource.setUsername(username);
        druidXADataSource.setPassword(password);
        druidXADataSource.setMinIdle(minIdle);
        druidXADataSource.setMaxActive(maxActive);
        druidXADataSource.setInitialSize(initialSize);
        druidXADataSource.setFilters(filters);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("sid");
        xaDataSource.setXaDataSource(druidXADataSource);
        return xaDataSource;
    }

    @Primary
    @Bean(name = "sidEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean sidEntityManager(EntityManagerFactoryBuilder builder) throws Throwable {
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

        LocalContainerEntityManagerFactoryBean sidPersistenceUnit = builder.dataSource(sidDataSource())
                .properties(properties)
                .packages("cn.ltx.jtajpa.model.sid")
                .persistenceUnit("sidPersistenceUnit")
                .build();
        sidPersistenceUnit.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        return sidPersistenceUnit;
    }

}
