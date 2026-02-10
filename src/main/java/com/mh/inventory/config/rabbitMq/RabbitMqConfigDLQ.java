package com.mh.inventory.config.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*this class is when needed DLQ (retry) configured*/

@Configuration
public class RabbitMqConfigDLQ {

    /* ============================
       Exchanges
       ============================ */

    public static final String STOCK_EXCHANGE = "stock.exchange";
    public static final String STOCK_DLX = "stock.dlx";

    /* ============================
       Queues
       ============================ */

    public static final String LOW_STOCK_QUEUE = "stock.low.queue";
    public static final String LOW_STOCK_DLQ = "stock.low.dlq";

    /* ============================
       Routing Keys
       ============================ */

    public static final String LOW_STOCK_ROUTING_KEY = "stock.low";
    public static final String LOW_STOCK_DLQ_ROUTING_KEY = "stock.low.dlq";

    /* ============================
       Exchanges
       ============================ */

    @Bean
    public DirectExchange stockExchange() {
        return new DirectExchange(STOCK_EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(STOCK_DLX);
    }

    /* ============================
       Queues
       ============================ */

    @Bean
    public Queue lowStockQueue() {
        return QueueBuilder.durable(LOW_STOCK_QUEUE)
                // DLQ CONFIG
                .withArgument("x-dead-letter-exchange", STOCK_DLX)
                .withArgument("x-dead-letter-routing-key", LOW_STOCK_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue lowStockDLQ() {
        return QueueBuilder.durable(LOW_STOCK_DLQ).build();
    }

    /* ============================
       Bindings
       ============================ */

    @Bean
    public Binding lowStockBinding() {
        return BindingBuilder
                .bind(lowStockQueue())
                .to(stockExchange())
                .with(LOW_STOCK_ROUTING_KEY);
    }

    @Bean
    public Binding lowStockDLQBinding() {
        return BindingBuilder
                .bind(lowStockDLQ())
                .to(deadLetterExchange())
                .with(LOW_STOCK_DLQ_ROUTING_KEY);
    }
}
