package com.mh.inventory.messaging.consumer;


import com.mh.inventory.config.rabbitMq.RabbitMqConfig;
import com.mh.inventory.config.rabbitMq.RabbitMqConfigDLQ;
import com.mh.inventory.dtos.StockLowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Component
@RequiredArgsConstructor
public class LowStockConsumer {

    private final SimpMessagingTemplate messagingTemplate;


    /*
    * “Whenever a message appears in stock.low.queue, call this method.”
    * */
//    @RabbitListener(queues = RabbitMqConfig.LOW_STOCK_QUEUE)
    @RabbitListener(queues = RabbitMqConfigDLQ.LOW_STOCK_QUEUE)
    public void onLowStock(StockLowEvent event) {

        System.out.println(
                "LOW STOCK ALERT → Item: " + event.itemId()
                        + ", Qty: " + event.currentQty()
        );

        // Later:
        // emailService.send(...)
        // websocketService.notify(...)

        //“Broadcast this event to all connected WebSocket clients.”
        try {

//            Notification notification = notificationRepository.save(
//                    Notification.lowStock(event)
//            );
//
            messagingTemplate.convertAndSend(
                    "/topic/stock-alerts",
                    event
            );
        } catch (Exception ex) {
            throw new RuntimeException("Processing failed", ex);
        }
    }

}
