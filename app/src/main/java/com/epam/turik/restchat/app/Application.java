package com.epam.turik.restchat.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.epam.turik.restchat")
@EnableJpaRepositories(basePackages = "com.epam.turik.restchat.data")
@EntityScan("com.epam.turik.restchat.data.objects")
@EnableWebMvc
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.warn("READY");
	}
}