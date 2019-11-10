package cn.ltx.jtajpa.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

@Configuration
public class FlowableConfig {
    @Autowired
    @Qualifier("sidDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("atomikosTransactionManager")
    private TransactionManager transactionManager;

    @Bean
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        JtaProcessEngineConfiguration configuration = new JtaProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(transactionManager);
        configuration.setDatabaseSchemaUpdate("true");
        return configuration;
    }

    @Bean
    public ProcessEngine getProcessEnging() {
        ProcessEngine processEngine = getProcessEngineConfiguration().buildProcessEngine();
        return processEngine;
    }

}
