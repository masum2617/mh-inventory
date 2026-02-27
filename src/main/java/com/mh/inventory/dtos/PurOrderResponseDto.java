package com.mh.inventory.dtos;

import lombok.Getter;
import lombok.Setter;

public interface PurOrderResponseDto {
    Long getPurOrderId();
    String getPoCode();
    Long getPurOrderItemId();
    Long getItemId();
    String getItemName();
    Integer getQtyOrdered();
    Integer getQtyReceived();
    Double getUnitPrice();
}
