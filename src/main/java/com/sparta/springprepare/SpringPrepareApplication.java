package com.sparta.springprepare;

import com.sparta.springprepare.jwt.JwtVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableJpaAuditing
@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
@EnableConfigurationProperties(JwtVO.class)
public class SpringPrepareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPrepareApplication.class, args);
    }

}
