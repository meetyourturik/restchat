package com.epam.turik.restchat.integration_tests.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class TestInitializer {

    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12")
        .withDatabaseName("database")
        .withUsername("developer")
        .withPassword("password");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            init(context);
        }
    }

    private static void init(ConfigurableApplicationContext context) {
        postgres.start();

        TestPropertyValues.of(
            "spring.datasource.url=" + postgres.getJdbcUrl(),
            "spring.datasource.username=" + postgres.getUsername(),
            "spring.datasource.password=" + postgres.getPassword(),
            "dbunit.connectionUrl=" + postgres.getJdbcUrl(),
            "dbunit.username=" + postgres.getUsername(),
            "dbunit.password=" + postgres.getPassword()
        ).applyTo(context.getEnvironment());
    }
}
