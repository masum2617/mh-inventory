package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.NotificationDto;
import com.mh.inventory.dtos.UserRequestDto;
import com.mh.inventory.dtos.UserResponseDto;
import com.mh.inventory.entity.Notification;

public interface NotificationService {
    Notification saveNotification(NotificationDto notificationDto);

    Response updateReadStatus(NotificationDto notificationDto);
}
