package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;

import java.util.List;

public interface ItemServiceQ {
    Response getItemById(Long id);
    Response itemListWithStock(Long itemId);

    //for data table
    Response itemListPaginated(
            String keyword,
            List<Long> categoryIds,
            Integer page,
            Integer perPage,
            String sortBy,
            String direction,
            String startDate,
            String endDate
    );
}
