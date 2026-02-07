package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;

import java.util.List;

public interface ItemService {
    Response saveItem(ItemDto item);
    Response saveItemWithProcedure(ItemDto item);
    Response saveItemAndStockWithProcedure(ItemDto item);
    Response getAllItems();

    Response searchItemWithProc(String searchText);
    Response searchItemWithFunc(String searchText);

    Response getFilteredItem(String keyword, List<Long> categoryIds, Integer status);

}
