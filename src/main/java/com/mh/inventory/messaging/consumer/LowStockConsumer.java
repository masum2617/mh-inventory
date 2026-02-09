package com.mh.inventory.messaging.consumer;


import com.mh.inventory.config.rabbitMq.RabbitMqConfig;
import com.mh.inventory.dtos.StockLowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Component
@RequiredArgsConstructor
public class LowStockConsumer {

    private final SimpMessagingTemplate messagingTemplate;


    @RabbitListener(queues = RabbitMqConfig.LOW_STOCK_QUEUE)
    public void onLowStock(StockLowEvent event) {

        System.out.println(
                "LOW STOCK ALERT → Item: " + event.itemId()
                        + ", Qty: " + event.currentQty()
        );

        // Later:
        // emailService.send(...)
        // websocketService.notify(...)

        //websocket, Backend is now pushing live data.
        messagingTemplate.convertAndSend(
                "/topic/stock-alerts",
                event
        );
    }
}
