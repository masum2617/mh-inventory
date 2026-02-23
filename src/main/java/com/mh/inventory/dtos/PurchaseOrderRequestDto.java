package com.mh.inventory.dtos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderRequestDto {

    private String poCode;

    private Long supplierId;

//    @NotNull(message = "Expected delivery date is required")
    private Date expectedDeliveryDate;

    private String remarks;

//    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal discAmt;

    private String orderStatus;
    private Double totalAmount;

//    @NotEmpty(message = "At least one item is required")
    private List<PurchaseOrderItemDto> items;
}