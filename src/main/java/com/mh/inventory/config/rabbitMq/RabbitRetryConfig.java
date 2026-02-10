package com.mh.inventory.config.rabbitMq;

import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

//@Configuration
public class RabbitRetryConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);


        factory.setDefaultRequeueRejected(false);

        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .maxAttempts(3)                 // try 3 times
                        .backOffOptions(
                                1000,   // initial delay: 1s
                                2.0,    // multiplier
                                5000    // max delay
                        )
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .build()
        );

        return factory;
    }
}
