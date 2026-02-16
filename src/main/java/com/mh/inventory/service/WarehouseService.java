package com.mh.inventory.service;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.entity.Warehouse;

public interface WarehouseService {
    Response saveWarehouse(Warehouse warehouse);
    Response updateWarehouse(Warehouse warehouse);
    Warehouse getWareHouse(Long id);
}
