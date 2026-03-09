package com.basic.myspringboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ProdConfig {
    @Bean
    public CustomerVO customerVO(){
        return CustomerVO.builder() //CustomerBuilder
                .mode("운영 환경")
                .rate(1.5)
                .build();
    }
}
