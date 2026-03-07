package com.mh.inventory.dtos;

import java.util.Date;

public interface PurOrderDto {

    Long getPurOrdId();
    String getPoCode();

    Long getPurReturnId();
    Date getReturnDate();
    String getReturnStatus();

    Long getPurReturnItemId();
    Integer getQtyReturn();

    Long getItemId();
    String getItemName();
}