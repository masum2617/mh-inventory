package com.mh.inventory.controllers;


import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurOrderRequestDto;
import com.mh.inventory.dtos.PurOrderReturnRequestDto;
import com.mh.inventory.service.PurchaseOrderService;
import com.mh.inventory.service.PurchaseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseReturnService purchaseReturnService;

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

    /*return*/
    @PostMapping("/return/save")
    public Response savePurchaseOrderReturn(@RequestBody PurOrderReturnRequestDto requestDto) {

        return purchaseReturnService.processPurchaseOrderReturn(requestDto);
    }

}
