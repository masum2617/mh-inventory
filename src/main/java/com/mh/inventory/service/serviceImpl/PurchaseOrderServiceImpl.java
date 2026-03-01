package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.PurOrderRequestDto;
import com.mh.inventory.dtos.PurOrderResponseDto;
import com.mh.inventory.dtos.PurchaseOrderItemDto;
import com.mh.inventory.dtos.StockRequestDto;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.PurchaseOrder;
import com.mh.inventory.entity.PurchaseOrderItems;
import com.mh.inventory.enums.PurchaseOrderStatus;
import com.mh.inventory.enums.TransactionType;
import com.mh.inventory.events.PurchaseOrderReceivedEvent;
import com.mh.inventory.mapper.purchaseOrder.PurchaseOrderMapper;
import com.mh.inventory.repository.ItemRepo;
import com.mh.inventory.repository.PurchaseOrderRepo;
import com.mh.inventory.service.InventoryService;
import com.mh.inventory.service.PurchaseOrderService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final EntityManager entityManager;
    private final PurchaseOrderRepo purchaseOrderRepo;
    private final ItemRepo itemRepo;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final InventoryService inventoryService;
    private final ApplicationEventPublisher applicationEventPublisher;
//    private final Supplier


    @Override
    public Response createPurchaseOrder(PurOrderRequestDto request) {

        PurchaseOrder po = new PurchaseOrder();

        if (request.getPoCode() == null) {
            po.setPoCode(generatePurchaseOrderCode());
        }

        po.setOrderDate(new Date());
        po.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
        po.setRemarks(request.getRemarks());
        po.setDiscAmt(request.getDiscAmt());
        po.setTotalAmount(request.getTotalAmount());
        po.setOrderStatus(PurchaseOrderStatus.valueOf(request.getOrderStatus()));

        // Supplier reference (no DB hit)
//            Supplier supplierRef = supplierRepository.getReferenceById(request.getSupplierId());
//            po.setSupplier(supplierRef);

        for (PurchaseOrderItemDto itemDto : request.getItems()) {

            PurchaseOrderItems poItem = new PurchaseOrderItems();

            // Item reference (no DB hit)
            Item itemRef = itemRepo.getReferenceById(itemDto.getItemId());
            poItem.setItem(itemRef);

            poItem.setPurchaseOrder(po);
            poItem.setQtyOrdered(itemDto.getQtyOrdered());
            poItem.setUnitPrice(itemDto.getUnitPrice());
            poItem.setDiscAmt(itemDto.getDiscAmt());
            poItem.setTotalAmount(itemDto.getTotalAmt());


//            double itemTotal =
//                    (itemDto.getQtyOrdered() * itemDto.getUnitPrice())
//                            - Optional.ofNullable(itemDto.getDiscAmt()).orElse(0.0);
//
//            poItem.setTotalAmount(itemTotal);
//
//            totalAmount += itemTotal;

            po.getPurchaseOrderItems().add(poItem);
        }

//        po.setTotalAmount(totalAmount);

        PurchaseOrder savedData = purchaseOrderRepo.save(po);
        if(savedData.getId() != null) {
            return ResponseUtils.createSuccessResponse("Saved Successful");
        }
        return ResponseUtils.createFailedResponse("Failed To Save");
    }


    @Override
    public Response updatePurchaseOrder(PurOrderRequestDto request) {
        if (request.getId() == null) {
            return ResponseUtils.createFailedResponse("id is required");
        }

        PurchaseOrder po = purchaseOrderRepo.findByIdWithItems(request.getId()).get();
        if(po == null) {
            return ResponseUtils.createFailedResponse("Purchase Order Not Found");
        }

        if(po.getOrderStatus().equals(PurchaseOrderStatus.APPROVED)){
            return ResponseUtils.createFailedResponse("Purchase Order Approved. No Edit Allowed");
        }

        purchaseOrderMapper.updateEntityFromDto(request, po);

//        po.setSupplier(supplierRepo.getReferenceById(request.getSupplierId()));

        //Map existing items by itemId
        Map<Long, PurchaseOrderItems> existingMap =
                po.getPurchaseOrderItems().stream()
                                .collect(Collectors.toMap(
                                        item -> item.getItem().getId(),
                                        Function.identity()
                                ));

        Set<Long> incomingItemIds = new HashSet<>();


        for (PurchaseOrderItemDto itemDto : request.getItems()) {

            incomingItemIds.add(itemDto.getItemId());
            PurchaseOrderItems existingItem = existingMap.get(itemDto.getItemId());

            if (existingItem != null) {
                //update existing item
                existingItem.setQtyOrdered(itemDto.getQtyOrdered());
                existingItem.setUnitPrice(itemDto.getUnitPrice());
                existingItem.setDiscAmt(itemDto.getDiscAmt());
                existingItem.setTotalAmount(itemDto.getTotalAmt());
            } else {
                // new item
                PurchaseOrderItems poItem = new PurchaseOrderItems();


                // Item reference (no DB hit)
                Item itemRef = itemRepo.getReferenceById(itemDto.getItemId());
                poItem.setItem(itemRef);

                poItem.setPurchaseOrder(po);
                poItem.setQtyOrdered(itemDto.getQtyOrdered());
                poItem.setUnitPrice(itemDto.getUnitPrice());
                poItem.setDiscAmt(itemDto.getDiscAmt());
                poItem.setTotalAmount(itemDto.getTotalAmt());

                po.getPurchaseOrderItems().add(poItem);
            }

        }

        if(incomingItemIds.isEmpty()) {
           return ResponseUtils.createFailedResponse("No items in purchase order");
        }

        po.getPurchaseOrderItems().removeIf(
                poi -> !incomingItemIds.contains(poi.getItem().getId())
        );

        /*update stock when product is received*/
        if (request.getReceiveFlag() != null && request.getReceiveFlag() == 1) {

            PurchaseOrderReceivedEvent purOrdReqEventDto = new PurchaseOrderReceivedEvent();
            purOrdReqEventDto.setWarehouseId(request.getWarehouseId());
            purOrdReqEventDto.setItems(request.getItems());

            applicationEventPublisher.publishEvent(purOrdReqEventDto);

            /*un comment this if don't want to publish event way*/
            /*for (PurchaseOrderItemDto item : request.getItems()) {

                StockRequestDto stockDto = new StockRequestDto();

                stockDto.setItemId(item.getItemId());
                stockDto.setQtyChange(item.getQtyReceive()+ item.getBonusQty());
                stockDto.setWarehouseId(request.getWarehouseId());

                stockDto.setSupplierId(item.getSupplierId());
                stockDto.setBatchNumber(item.getBatchNumber());
                stockDto.setPurchasePrice(item.getUnitPrice());
                stockDto.setTransactionType(TransactionType.PURCHASE.getId());

                inventoryService.addInventory(stockDto);
            }*/
        }

        return ResponseUtils.createSuccessResponse("Updated Successfully");
    }

    @Override
    public Response getPurchaseOrderWithItems(Long purchaseOrderId) {
        if (purchaseOrderId == null) {
            return ResponseUtils.createFailedResponse("id is required");
        }

        List<PurOrderResponseDto> poList = purchaseOrderRepo.findPurchaseOrderWithItems(purchaseOrderId);
        if(poList.size() == 0) {
            return ResponseUtils.createFailedResponse("Purchase Order Not Found");
        }

        return ResponseUtils.createSuccessResponse("Data Found", poList);
    }



    private String generatePurchaseOrderCode() {

        String poCode = (String) entityManager
                .createNativeQuery("SELECT * FROM mh_inventory.fn_generate_po_code()").getSingleResult();

        return poCode;

    }
}
