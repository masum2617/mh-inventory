package com.mh.inventory.dtos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PurOrderRequestDto {

    private String poCode;
    private Long id; //po id
    private Long supplierId;
    private Date receiveDate;
    private Long receiveFlag;

//    @NotNull(message = "Expected delivery date is required")
    private Date expectedDeliveryDate;

    private String remarks;

//    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal discAmt;

    private String orderStatus;
    private Double totalAmount;
    private Long warehouseId;

//    @NotEmpty(message = "At least one item is required")
    private List<PurchaseOrderItemDto> items;
}