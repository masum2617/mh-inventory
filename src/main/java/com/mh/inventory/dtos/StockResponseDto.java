package com.mh.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockResponseDto {
    private Long stockId;
    private Long itemId;
    private String itemName;

    private Integer qtyAvailable;
    private Integer reOrderLevel;
//    private Double purchasePrice;
//    private Double salesPrice;

}
