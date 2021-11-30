package com.epam.turik.restchat.infrastructure;

import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

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
            "spring.datasource.password=" + postgres.getPassword()
        ).applyTo(context.getEnvironment());
    }
}
