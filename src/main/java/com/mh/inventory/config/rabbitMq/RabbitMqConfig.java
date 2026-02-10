package com.mh.inventory.config.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 Needed when no dlq configured
* “This class defines RabbitMQ infrastructure (queues, exchanges, bindings).”
* */
@Configuration
public class RabbitMqConfig {
    public static final String STOCK_EXCHANGE = "stock.exchange";
    public static final String LOW_STOCK_QUEUE = "stock.low.queue";
    public static final String LOW_STOCK_ROUTING_KEY = "stock.low";

    /*
    Exchange decides where the message should go
    “Given this message, which queue(s) should receive it?”
    */
    @Bean
    public DirectExchange stockExchange() {
        return new DirectExchange(STOCK_EXCHANGE);
    }

    /*
    * A queue stores messages until a consumer reads them.
    * This method:

        Creates a durable queue

        Named "stock.low.queue"

        Durable means:

        Survives broker restart

        Messages are not lost (if persistent)
    * */
    //this method is when no dead letter queue used
    @Bean
    public Queue lowStockQueue() {
        return QueueBuilder.durable(LOW_STOCK_QUEUE).build();
    }

    /*
    * “Messages sent to stock.exchange with routing key stock.low
        should go to stock.low.queue.”
    *
    * */
    @Bean
    public Binding lowStockBinding() {
        return BindingBuilder
                .bind(lowStockQueue())
                .to(stockExchange())
                .with(LOW_STOCK_ROUTING_KEY);
    }


}
