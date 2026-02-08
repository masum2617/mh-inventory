package com.mh.inventory.config.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String STOCK_EXCHANGE = "stock.exchange";
    public static final String LOW_STOCK_QUEUE = "stock.low.queue";
    public static final String LOW_STOCK_ROUTING_KEY = "stock.low";

    @Bean
    public DirectExchange stockExchange() {
        return new DirectExchange(STOCK_EXCHANGE);
    }

    @Bean
    public Queue lowStockQueue() {
        return QueueBuilder.durable(LOW_STOCK_QUEUE).build();
    }

    @Bean
    public Binding lowStockBinding() {
        return BindingBuilder
                .bind(lowStockQueue())
                .to(stockExchange())
                .with(LOW_STOCK_ROUTING_KEY);
    }
}
