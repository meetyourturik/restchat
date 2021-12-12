package com.epam.turik.restchat.integration_tests.infrastructure;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(basePackages = "com.epam.turik.restchat")
@EnableAutoConfiguration
public class TestConfig {
}