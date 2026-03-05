package com.mh.inventory.dtos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PurOrderReturnRequestDto {

    private Long purchaseOrderId; //po id
    private Long id; //return id
    private String remarks;
    private Integer returnFlag;
    private String reason;

//    @NotEmpty(message = "At least one item is required")
    private List<PurOrderReturnRequestItemDto> items;

}