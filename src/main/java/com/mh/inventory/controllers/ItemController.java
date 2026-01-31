package com.mh.inventory.controllers;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("/save")
    public Response saveItem(@RequestBody ItemDto itemDto) {
        return itemService.saveItem(itemDto);
    }

    @GetMapping("/list")
    public Response listItems() {
        return itemService.getAllItems();
    }
}
