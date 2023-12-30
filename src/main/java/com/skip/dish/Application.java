package com.skip.dish;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@Configuration
@ComponentScan(basePackages ="com.skip.dish.dao.dto")
@EnableAdminServer
@EnableJpaRepositories(basePackages = "com.skip.dish.dao.dto" )
@EntityScan(basePackages ="com.skip.dish.dao.dto")
    public class Application {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }


