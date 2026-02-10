package com.mh.inventory.events;

import com.mh.inventory.config.rabbitMq.RabbitMqConfig;
import com.mh.inventory.config.rabbitMq.RabbitMqConfigDLQ;
import com.mh.inventory.dtos.StockLowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final RabbitTemplate rabbitTemplate;

    @EventListener
    @Async("asyncExecutor")
    public void handleStockLow(StockLowEvent event) {

//        rabbitTemplate.convertAndSend(
//                RabbitMqConfig.STOCK_EXCHANGE,
//                RabbitMqConfig.LOW_STOCK_ROUTING_KEY,
//                event
//        );

        rabbitTemplate.convertAndSend(
                RabbitMqConfigDLQ.STOCK_EXCHANGE,
                RabbitMqConfigDLQ.LOW_STOCK_ROUTING_KEY,
                event
        );
    }
}
