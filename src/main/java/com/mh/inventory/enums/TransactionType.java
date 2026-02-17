package com.mh.inventory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    OPENING(1, "OPENING"),
    PURCHASE(2, "PURCHASE"),
    PURCHASE_RETURN(3, "PURCHASE_RETURN"),
    SALE(4, "SALE"),
    SALES_RETURN(5, "SALES_RETURN"),
    ADJUSTMENT_IN(6, "ADJUSTMENT_IN"),
    ADJUSTMENT_OUT(7, "ADJUSTMENT_OUT"),
    TRANSFER_IN(8, "TRANSFER_IN"),
    TRANSFER_OUT(9, "TRANSFER_OUT");

    private final int id;
    private final String code;

    public static TransactionType fromId(int id) {
        for (TransactionType type : values()) {
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException("Invalid ID: " + id);
    }
}