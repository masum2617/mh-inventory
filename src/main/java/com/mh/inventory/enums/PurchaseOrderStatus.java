package com.mh.inventory.enums;

public enum PurchaseOrderStatus {
    DRAFT,
    PENDING,
    APPROVED,
    RECEIVED,
    CANCELLED;

    public static PurchaseOrderStatus toPoStatusEnum(String value) {
        for (PurchaseOrderStatus status : PurchaseOrderStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status: " + value);
    }
}
