package com.mh.inventory.messaging.consumer;

import com.mh.inventory.config.rabbitMq.RabbitMqConfig;
import com.mh.inventory.dtos.StockLowEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LowStockConsumer {

    @RabbitListener(queues = RabbitMqConfig.LOW_STOCK_QUEUE)
    public void onLowStock(StockLowEvent event) {

        System.out.println(
                "LOW STOCK ALERT → Item: " + event.itemId()
                        + ", Qty: " + event.currentQty()
        );

        // Later:
        // emailService.send(...)
        // websocketService.notify(...)
    }
}
