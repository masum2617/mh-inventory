package com.mh.inventory.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
    private String title;
    private String message;
    private String type;

    private Boolean isRead;
    private Long id;
}
