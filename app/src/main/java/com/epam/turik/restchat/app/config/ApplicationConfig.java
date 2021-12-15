package com.epam.turik.restchat.app.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.support.DatabaseStartupValidator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.turik.restchat")
@EnableJpaRepositories(basePackages = "com.epam.turik.restchat.data")
@EntityScan("com.epam.turik.restchat.data.objects")
public class ApplicationConfig {
    private static final String DB_STARTUP_VALIDATOR = "dbStartupValidator";
    private static final int DB_CHECK_INTERVAL_S = 5;
    private static final int DB_CHECK_TIMEOUT_S = 60;

    @Bean(name = DB_STARTUP_VALIDATOR)
    public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
        DatabaseStartupValidator validator = new DatabaseStartupValidator();
        validator.setDataSource(dataSource);
        validator.setInterval(DB_CHECK_INTERVAL_S);
        validator.setTimeout(DB_CHECK_TIMEOUT_S);
        return validator;
    }

    @Bean
    public static BeanFactoryPostProcessor dependsOnPostProcessor() {
        return beanFactory -> {
            BeanDefinition definition = beanFactory.getBeanDefinition("liquibase");
            definition.setDependsOn(DB_STARTUP_VALIDATOR);
        };
    }
}
