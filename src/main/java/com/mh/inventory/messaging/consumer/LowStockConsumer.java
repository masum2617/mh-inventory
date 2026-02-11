package com.mh.inventory.messaging.consumer;


import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.config.rabbitMq.RabbitMqConfig;
import com.mh.inventory.config.rabbitMq.RabbitMqConfigDLQ;
import com.mh.inventory.dtos.NotificationDto;
import com.mh.inventory.dtos.StockLowEvent;
import com.mh.inventory.entity.Notification;
import com.mh.inventory.repository.NotificationRepo;
import com.mh.inventory.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LowStockConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;


    /*
    * “Whenever a message appears in stock.low.queue, call this method.”
    * */
    @Transactional
    @RabbitListener(queues = RabbitMqConfig.LOW_STOCK_QUEUE)
//    @RabbitListener(queues = RabbitMqConfigDLQ.LOW_STOCK_QUEUE)
    public void onLowStock(StockLowEvent event) {

        //save notification
        NotificationDto notification = new NotificationDto();
        notification.setTitle("Low Stock");
        notification.setMessage("This is a low stock notification");
        Notification notificationRes = notificationService.saveNotification(notification);


        // Later:
        // emailService.send(...)
        // websocketService.notify(...)

        //“Broadcast this event to all connected WebSocket clients.”
        try {
            StockLowEvent lowEvent = new StockLowEvent(
                    event.itemId(),
                    event.currentQty(),
                    notificationRes.getId(),
                    event.itemName()
            );

            messagingTemplate.convertAndSend(
                    "/topic/stock-alerts",
                    lowEvent
            );
        } catch (Exception ex) {
            throw new RuntimeException("Processing failed", ex);
        }
    }

}
