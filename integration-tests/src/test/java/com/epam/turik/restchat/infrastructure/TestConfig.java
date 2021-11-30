package com.epam.turik.restchat.infrastructure;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"com.epam.turik"})
@EnableAutoConfiguration
public class TestConfig {

}