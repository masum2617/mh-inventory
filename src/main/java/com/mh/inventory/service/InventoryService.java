package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.StockRequestDto;

public interface InventoryService {
//    Response reduceStock(StockRequestDto stockRequestDto);

    Response addInventory(StockRequestDto stockRequestDto);

    Response processInventoryAdjustment(StockRequestDto stockRequestDto);
}
