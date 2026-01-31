package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;

public interface ItemService {
    Response saveItem(ItemDto item);
    Response getAllItems();
}
