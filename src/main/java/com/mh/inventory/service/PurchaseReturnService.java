package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurOrderDto;
import com.mh.inventory.dtos.PurOrderReturnRequestDto;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface PurchaseReturnService {

    Response processPurchaseOrderReturn(PurOrderReturnRequestDto request);
    Response updatePurchaseReturn(PurOrderReturnRequestDto request);
    Page<PurOrderDto> getPurchaseReturnData(
            String keyword,
            Date startDate,
            Date endDate,
            int page,
            int size
    );
}

