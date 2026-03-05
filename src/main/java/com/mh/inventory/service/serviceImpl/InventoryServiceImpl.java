package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseStatus;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.StockLowEvent;
import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.dtos.StockResponseDto;
import com.mh.inventory.entity.*;
import com.mh.inventory.enums.TransactionType;
import com.mh.inventory.mapper.stock.StockMapper;
import com.mh.inventory.repository.InventoryBalanceRepo;
import com.mh.inventory.repository.InventoryTransactionRepo;
import com.mh.inventory.repository.ItemRepo;
import com.mh.inventory.service.InventoryService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final StockMapper inventoryMapper;
    private final EntityManager entityManager;
    private final ItemRepo itemRepo;
    private final InventoryTransactionRepo inventoryTransactionRepo;
    private final InventoryBalanceRepo inventoryBalanceRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public Response addInventory(StockRequestDto stockRequestDto) {

        if(stockRequestDto.getItemId() == null){
            return ResponseUtils.createFailedResponse("Item Id is required");
        }

        InventoryTransaction inventoryTransaction = inventoryMapper.toEntity(stockRequestDto);

        // NO DB HIT here
        /*getReference -> It creates a lazy proxy with only the ID
        * Use EntityManager.getReference() (or repository getReferenceById()).*/
//        Item item = entityManager.getReference(Item.class, stockRequestDto.getItemId()); //works
        Item item = itemRepo.getReferenceById(stockRequestDto.getItemId()); //works

        Warehouse warehouse = entityManager.getReference(Warehouse.class, stockRequestDto.getWarehouseId());
        Supplier supplier = entityManager.getReference(Supplier.class, stockRequestDto.getSupplierId());

        inventoryTransaction.setWarehouse(warehouse);
        inventoryTransaction.setSupplier(supplier);
        inventoryTransaction.setItem(item);

        inventoryTransaction.setTransactionType(stockRequestDto.getTransactionType() != null ? stockRequestDto.getTransactionType() : null);


        InventoryTransaction savedTransaction = inventoryTransactionRepo.save(inventoryTransaction);

        if(savedTransaction.getId() != null){
            //save or update balance table
            //check first if item+batch
            Optional<InventoryBalance> inventoryBalance =
                    inventoryBalanceRepo.findByItemIdAndBatchNumber(stockRequestDto.getItemId(), stockRequestDto.getBatchNumber());
            if(inventoryBalance.isPresent()){
                InventoryBalance balance = inventoryBalance.get();
                balance.setQtyAvailable(
                        balance.getQtyAvailable() + stockRequestDto.getQtyChange()
                );
            } else{
                //new entry
                InventoryBalance balance = new InventoryBalance();
                balance.setItem(savedTransaction.getItem());
                balance.setQtyAvailable(stockRequestDto.getQtyChange());
                balance.setPurchasePrice(BigDecimal.valueOf(stockRequestDto.getPurchasePrice()));
                balance.setWarehouse(savedTransaction.getWarehouse());
                balance.setBatchNumber(stockRequestDto.getBatchNumber());
                balance.setActiveStatus(1);

                InventoryBalance savedBalance = inventoryBalanceRepo.save(balance);
                if(savedBalance.getId() == null){
                    return ResponseUtils.createFailedResponse("Save Failed Transaction Balance");
                }
            }

        }


        return ResponseUtils.createSuccessResponse("Inventory Saved");
    }

    @Override
    @Transactional
    public Response processInventoryAdjustment(StockRequestDto stockRequestDto) {
        Long itemNo = stockRequestDto.getItemId();
        if(stockRequestDto.getItemId() == null){
            return ResponseUtils.createFailedResponse("Item Id is required");
        }

        InventoryTransaction inventoryTransaction = inventoryMapper.toEntity(stockRequestDto);

        Item item = itemRepo.findById(stockRequestDto.getItemId()).orElse(null);

        Warehouse warehouse = entityManager.getReference(Warehouse.class, stockRequestDto.getWarehouseId());
        Supplier supplier = entityManager.getReference(Supplier.class, stockRequestDto.getSupplierId());


        inventoryTransaction.setWarehouse(warehouse.getId() == null ? null : warehouse);
        inventoryTransaction.setSupplier(supplier.getId() == null ? null : supplier);
        inventoryTransaction.setItem(item);
        inventoryTransaction.setQtyChange(-stockRequestDto.getSellQty());
        inventoryTransaction.setTransactionType(stockRequestDto.getTransactionType() == null ? TransactionType.SALE.getId() : stockRequestDto.getTransactionType());

        inventoryTransactionRepo.save(inventoryTransaction);

        //reduce stock balance
        InventoryBalance inventoryItemBalance = inventoryBalanceRepo
                .findByItemIdAndBatchNumber(stockRequestDto.getItemId(), stockRequestDto.getBatchNumber())
                .orElseThrow(() ->
                        new RuntimeException("Stock not available"));

        if(inventoryItemBalance.getQtyAvailable() < stockRequestDto.getQtyChange()){
            return ResponseUtils.createFailedResponse("Insufficient Stock");
        }
        Integer currQty = inventoryItemBalance.getQtyAvailable();
        int newQty = currQty - stockRequestDto.getSellQty();

        inventoryItemBalance.setQtyAvailable(newQty);
        InventoryBalance inventoryBalanceSaved = inventoryBalanceRepo.save(inventoryItemBalance);

        // threshold crossing check
        if (newQty <= item.getReOrderLevel()) {
            eventPublisher.publishEvent(
                    new StockLowEvent(itemNo, newQty, null, item.getItemName())
            );
        }

        if(inventoryBalanceSaved.getId() == null){
            return ResponseUtils.createFailedResponse("Save Failed Transaction Balance");
        }
        return ResponseUtils.createSuccessResponse("Inventory Balance updated successfully");

    }





//    @Transactional
//    @Override
//    public Response reduceStock(StockRequestDto stockRequestDto) {
//        Long itemNo = stockRequestDto.getItemId();
//        Integer sellQty = stockRequestDto.getSellQty() != null ? stockRequestDto.getSellQty() : 1;
//
////        Stock stock = stockRepo.findByItemId(itemNo);
//        StockResponseDto stock = stockRepo.findStockWithItem(itemNo);
//        Stock currStockObj = stockRepo.findById(stock.getStockId()).orElse(null);
//
//
//        Response res = Response.builder()
//                .status(ResponseStatus.success("Stock Updated Successfully"))
//                .data(stock)
//                .build();
//
//
//        Integer currQty = stock.getQtyAvailable();
//
//        int newQty = currQty - sellQty;
//        currStockObj.setQtyAvailable(newQty);
//
//        Stock savedStock = stockRepo.save(currStockObj);
//
//        // threshold crossing check
//        if (newQty <= stock.getReOrderLevel()) {
//            eventPublisher.publishEvent(
//                    new StockLowEvent(itemNo, newQty, null, stock.getItemName())
//            );
//        }
//
//
////
//        if (savedStock != null) {
//            return ResponseUtils.createSuccessResponse("Stock Updated Successfully", stockMapper.toResponseDto(savedStock));
//        }
//
//
//        return ResponseUtils.createFailedResponse("Stock Updated Failed");
//    }

//getReference() like:
//
//            "I know this Supplier exists with ID=5.
//    I don’t need their full profile, I just need their ID for foreign key."
}
