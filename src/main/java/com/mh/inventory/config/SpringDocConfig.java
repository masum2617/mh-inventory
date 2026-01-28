package com.mh.inventory.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    @ConditionalOnMissingBean(name = "queryDslQuerydslPredicateOperationCustomizer")
    public Object queryDslQuerydslPredicateOperationCustomizer() {
        return new Object(); // dummy to skip auto-config
    }
}

