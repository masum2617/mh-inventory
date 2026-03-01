package com.mh.inventory.dtos;


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
