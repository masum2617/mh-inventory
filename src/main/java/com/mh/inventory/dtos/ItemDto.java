package com.mh.inventory.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

//    private Long id;

    @NotBlank
    private String itemCode;

    @NotBlank
    private String itemName;

    private Long categoryId;

    private Integer qty;

    private Integer reOrderLevel;

}

