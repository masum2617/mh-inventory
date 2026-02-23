package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurchaseOrderRequestDto;
import com.mh.inventory.entity.PurchaseOrder;

public interface PurchaseOrderService {

    Response createPurchaseOrder(PurchaseOrderRequestDto request);
//
//    PurchaseOrder submitForApproval(Long id);
//
//    PurchaseOrder approvePurchaseOrder(Long id, Long approverId);
//
//    PurchaseOrder receivePurchaseOrder(Long id, Long warehouseId);

}
