package com.sparta.springprepare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableJpaAuditing
@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class SpringPrepareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPrepareApplication.class, args);
    }

}
