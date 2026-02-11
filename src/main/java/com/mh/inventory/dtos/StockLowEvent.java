package com.mh.inventory.dtos;

public record StockLowEvent(Long itemId, int currentQty, Long id, String itemName) {
}
