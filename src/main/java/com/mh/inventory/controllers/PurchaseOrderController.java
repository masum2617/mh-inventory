package com.mh.inventory.controllers;


import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.dtos.PurchaseOrderRequestDto;
import com.mh.inventory.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/save")
    public Response saveItem(@RequestBody PurchaseOrderRequestDto requestDto) {

        return purchaseOrderService.createPurchaseOrder(requestDto);
    }

    @PostMapping("/update")
    public Response updatePurchaseOrder(@RequestBody PurchaseOrderRequestDto requestDto) {

        return purchaseOrderService.updatePurchaseOrder(requestDto);
    }
}
