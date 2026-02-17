package com.mh.inventory.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequestDto {
    private Long itemId;
    private Integer sellQty;
    private Long warehouseId;
    private Long supplierId;
    private Integer qtyChange;
    private Integer transactionType;
    private Double purchasePrice;
    private String batchNumber;
    private Double salesPrice;

}
