package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurOrderReturnRequestDto;

public interface PurchaseReturnService {

    Response processPurchaseOrderReturn(PurOrderReturnRequestDto request);

}

