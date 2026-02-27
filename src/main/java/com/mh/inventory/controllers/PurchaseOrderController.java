package com.mh.inventory.controllers;


import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurOrderRequestDto;
import com.mh.inventory.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/save")
    public Response saveItem(@RequestBody PurOrderRequestDto requestDto) {

        return purchaseOrderService.createPurchaseOrder(requestDto);
    }

    @GetMapping("/get-purchase-order")
    public Response getPurchaseOrder(@RequestParam Long id) {

        return purchaseOrderService.getPurchaseOrderWithItems(id);
    }

    @PostMapping("/update")
    public Response updatePurchaseOrder(@RequestBody PurOrderRequestDto requestDto) {

        return purchaseOrderService.updatePurchaseOrder(requestDto);
    }
}
