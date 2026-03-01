package com.mh.inventory.events;

import com.mh.inventory.dtos.PurchaseOrderItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PurchaseOrderReceivedEvent {
    private Long warehouseId;
    private List<PurchaseOrderItemDto> items;

}
