package com.mh.inventory.controllers;


import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.PurOrderDto;
import com.mh.inventory.dtos.PurOrderRequestDto;
import com.mh.inventory.dtos.PurOrderReturnRequestDto;
import com.mh.inventory.service.PurchaseOrderService;
import com.mh.inventory.service.PurchaseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PutMapping("/return/update")
    public Response updatePurchaseOrderReturn(@RequestBody PurOrderReturnRequestDto requestDto) {

        return purchaseReturnService.updatePurchaseReturn(requestDto);
    }

    @GetMapping("/purchase-return")
    public Response getPurchaseReturnData(

            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<PurOrderDto> purchaseReturnData = purchaseReturnService.getPurchaseReturnData(searchKeyword, startDate, endDate, page, size);
        Response res = new Response();
        res.setData(purchaseReturnData);

        return res;
    }
}
