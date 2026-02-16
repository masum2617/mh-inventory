package com.mh.inventory.controllers;


import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.dtos.NotificationDto;
import com.mh.inventory.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @PostMapping("/change-status")
    public Response changeReadStatus(@RequestBody NotificationDto notificationDto) {
        return notificationService.updateReadStatus(notificationDto);
    }

}
