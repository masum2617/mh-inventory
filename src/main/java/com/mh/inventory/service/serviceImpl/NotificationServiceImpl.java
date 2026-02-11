package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.NotificationDto;
import com.mh.inventory.entity.Notification;
import com.mh.inventory.repository.NotificationRepo;
import com.mh.inventory.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;

    @Override
    public Notification saveNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        if(notificationDto != null) {

            notification.setTitle(notificationDto.getTitle());
            notification.setMessage(notificationDto.getMessage());

            //todo: later add more fields

            Notification saveObj = notificationRepo.save(notification);
            if(saveObj != null) {
                return saveObj;
            }

        }
        return null;
    }

    @Transactional
    @Override
    public Response updateReadStatus(NotificationDto notificationDto) {
        if(notificationDto.getIsRead() == null) {
            return ResponseUtils.createFailedResponse("Failed To update Read Status");
        }

        Notification notification = notificationRepo.findById(notificationDto.getId()).orElse(null);

        if(!notification.isRead()) {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
        }
        return ResponseUtils.createSuccessResponse("Successfully Updated Read Status");

    }
}
