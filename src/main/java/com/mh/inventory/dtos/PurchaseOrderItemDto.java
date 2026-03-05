package com.mh.inventory.dtos;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseOrderItemDto {

    @NotNull(message = "Item is required")
    private Long itemId;

//    @NotNull
//    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer qtyOrdered;

//    @NotNull
//    @DecimalMin(value = "0.0", inclusive = false)
    private Double unitPrice;

//    @DecimalMin(value = "0.0", inclusive = true)
//    private BigDecimal discAmt;
    private Double discAmt;
    private Double totalAmt;
    private Integer bonusQty;
    private String weight;
    private Integer qtyReceive;
    private String batchNumber;
    private Long supplierId;

    private BigDecimal weightCost;

    /*return*/
    private Integer returnFlag;
}