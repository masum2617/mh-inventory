package com.mh.inventory.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PurOrderReturnRequestItemDto {


    @NotNull(message = "Item is required")
    private Long itemId;

    private Integer qtyReturn;
    private Double unitPrice;
    private String batchNumber;

    /*return*/
    private Integer returnFlag;
    private Long id;

}