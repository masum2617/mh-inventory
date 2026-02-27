package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurOrderRequestDto;

public interface PurchaseOrderService {

    Response createPurchaseOrder(PurOrderRequestDto request);
    Response updatePurchaseOrder(PurOrderRequestDto request);
    Response getPurchaseOrderWithItems(Long purchaseOrderId);
//    PurchaseOrder submitForApproval(Long id);

//    PurchaseOrder approvePurchaseOrder(Long id, Long approverId);

//    PurchaseOrder receivePurchaseOrder(Long id, Long warehouseId);

}
