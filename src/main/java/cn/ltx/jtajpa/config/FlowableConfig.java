package cn.ltx.jtajpa.config;

import org.flowable.engine.*;
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
        configuration.setJpaHandleTransaction(false);
        return configuration;
    }

    @Bean
    public ProcessEngine getProcessEnging() {
        ProcessEngine processEngine = getProcessEngineConfiguration().buildProcessEngine();
        return processEngine;
    }

    @Bean
    public RepositoryService getRepositoryService() {
        RepositoryService repositoryService = getProcessEnging().getRepositoryService();
        return repositoryService;
    }

    @Bean
    public RuntimeService getRuntimeService() {
        RuntimeService runtimeService = getProcessEnging().getRuntimeService();
        return runtimeService;
    }

    @Bean
    public TaskService getTaskService() {
        TaskService taskService = getProcessEnging().getTaskService();
        return taskService;
    }

    @Bean
    public HistoryService getHistoryService() {
        HistoryService historyService = getProcessEnging().getHistoryService();
        return historyService;
    }

    @Bean
    public IdentityService getIdentityService() {
        IdentityService identityService = getProcessEnging().getIdentityService();
        return identityService;
    }

    @Bean
    public FormService getFormService() {
        FormService formService = getProcessEnging().getFormService();
        return formService;
    }

    @Bean
    public DynamicBpmnService getDynamicBpmnService() {
        DynamicBpmnService dynamicBpmnService = getProcessEnging().getDynamicBpmnService();
        return dynamicBpmnService;
    }

}
