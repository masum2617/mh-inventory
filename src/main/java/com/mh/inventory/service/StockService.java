package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.dtos.StockResponseDto;

public interface StockService {
    StockResponseDto saveStock(StockRequestDto stockRequestDto);
    Response reduceStock(StockRequestDto stockRequestDto);
}
