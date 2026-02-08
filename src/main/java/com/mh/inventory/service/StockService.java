package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.StockRequestDto;

public interface StockService {
    Response reduceStock(StockRequestDto stockRequestDto);
}
