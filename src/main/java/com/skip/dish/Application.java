package com.skip.dish;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
/*
    @EnableJpaRepositories(basePackages = "com.skip.dish.dao" )
*/
@EntityScan("com.skip.dish.dao.dto")
    public class Application {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }


