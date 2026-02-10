package com.mh.inventory.dtos;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class ItemDto {

    private Long id;

    @NotBlank
    private String itemCode;

    @NotBlank
    private String itemName;

    private Long categoryId;

    private Integer qty;

    private Integer reOrderLevel;

    private String categoryName;

    private Integer stockQty;

    private Long slNo;


    @QueryProjection
    public ItemDto(Long id, String itemCode, String itemName, Integer qty, Integer reOrderLevel,
                   Long categoryId, String categoryName
    , Integer stockQty) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.qty = qty;
        this.reOrderLevel = reOrderLevel;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.stockQty = stockQty;
    }

    public ItemDto(Long id, String itemCode, String itemName, Integer qty) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.qty = qty;
    }


    public ItemDto(String itemName, String categoryName,
                   Integer stockQty) {
//        this.id = id;
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.stockQty = stockQty;
    }
}

