package com.mh.inventory.events.listener;

import com.mh.inventory.dtos.PurchaseOrderItemDto;
import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.enums.TransactionType;
import com.mh.inventory.events.PurchaseOrderReceivedEvent;
import com.mh.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PurOrderInventoryListener {
    private final InventoryService inventoryService;


    /*BEFORE COMMIT ->  Runs inside the same transaction;
    If inventory update fails → entire purchase order rolls back;
    */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePurchaseOrderReceived(PurchaseOrderReceivedEvent event) {

        for (PurchaseOrderItemDto item : event.getItems()) {

            StockRequestDto stockDto = new StockRequestDto();
            stockDto.setItemId(item.getItemId());
            stockDto.setQtyChange(
                    item.getQtyReceive() + item.getBonusQty()
            );
            stockDto.setWarehouseId(event.getWarehouseId());
            stockDto.setSupplierId(item.getSupplierId());
            stockDto.setBatchNumber(item.getBatchNumber());
            stockDto.setPurchasePrice(item.getUnitPrice());
            stockDto.setTransactionType(TransactionType.PURCHASE.getId());

            inventoryService.addInventory(stockDto);
        }

    }
}
