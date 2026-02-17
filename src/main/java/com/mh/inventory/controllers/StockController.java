package com.mh.inventory.controllers;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.service.InventoryService;
import com.mh.inventory.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/sell-items")
    public Response sellInventoryItems(@RequestBody StockRequestDto stockRequestDto) {
        return inventoryService.sellInventoryItems(stockRequestDto);

    }

    @PostMapping("/save-inventory")
    public Response saveInventory(@RequestBody StockRequestDto stockRequestDto) {
        return inventoryService.addInventory(stockRequestDto);

    }
}
