package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.PurOrderReturnRequestDto;
import com.mh.inventory.dtos.PurOrderReturnRequestItemDto;
import com.mh.inventory.dtos.PurchaseOrderItemDto;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.PurchaseOrder;
import com.mh.inventory.entity.PurchaseOrderItems;
import com.mh.inventory.entity.PurchaseReturn;
import com.mh.inventory.entity.PurchaseReturnItem;
import com.mh.inventory.enums.PurchaseReturnStatus;
import com.mh.inventory.repository.PurchaseOrderRepo;
import com.mh.inventory.repository.PurchaseReturnRepo;
import com.mh.inventory.service.PurchaseReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    private final PurchaseOrderRepo purchaseOrderRepo;
    private final PurchaseReturnRepo purchaseReturnRepo;

    @Override
    public Response processPurchaseOrderReturn(PurOrderReturnRequestDto request) {

        if (request == null || request.getPurchaseOrderId() == null) {
            return ResponseUtils.createFailedResponse("purchaseOrderId is required");
        }

        // 1. Load purchase order with items
        Optional<PurchaseOrder> poOpt = purchaseOrderRepo.findByIdWithItems(request.getPurchaseOrderId());
        if (poOpt.isEmpty()) {
            return ResponseUtils.createFailedResponse("Purchase Order Not Found");
        }

        PurchaseOrder purchaseOrder = poOpt.get();

        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseUtils.createFailedResponse("No items provided for return");
        }

        // Map existing PO items by itemId for quick lookup
        Map<Long, PurchaseOrderItems> existingPoItems =
                purchaseOrder.getPurchaseOrderItems().stream()
                        .collect(Collectors.toMap(
                                item -> item.getItem().getId(),
                                Function.identity()
                        ));
//
//        Map<Long, PurchaseOrderItems> poItemsByItemId = purchaseOrder.getPurchaseOrderItems()
//                .stream()
//                .filter(poi -> poi.getItem() != null && poi.getItem().getId() != null)
//                .collect(Collectors.toMap(poi -> poi.getItem().getId(), poi -> poi));

        // 2. Filter incoming items:
        // keep only those whose itemId exists in the purchase order AND returnFlag == 1
        List<PurOrderReturnRequestItemDto> validReturnItems = request.getItems()
                .stream()
                .filter(dto -> existingPoItems.containsKey(dto.getItemId()))
                .filter(dto -> Objects.equals(dto.getReturnFlag(), 1))
                .collect(Collectors.toList());

        if (validReturnItems.isEmpty()) {
            return ResponseUtils.createFailedResponse("No valid return items found");
        }

        // 3. Build PurchaseReturn + PurchaseReturnItem entities
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setPurchaseOrder(purchaseOrder);
        purchaseReturn.setReturnDate(LocalDate.now());
        purchaseReturn.setReason(request.getReason());
        purchaseReturn.setRemarks(request.getRemarks());
        purchaseReturn.setStatus(PurchaseReturnStatus.PENDING);


        BigDecimal totalReturnAmount = BigDecimal.ZERO;


        for (PurOrderReturnRequestItemDto dto : validReturnItems) {

            PurchaseOrderItems poItem = existingPoItems.get(dto.getItemId());
            Item item = poItem.getItem();

            PurchaseReturnItem returnItem = new PurchaseReturnItem();
            returnItem.setPurchaseReturn(purchaseReturn);
            returnItem.setItem(item);

            // Interpret qtyReceive from DTO as the return quantity for this operation
            Integer qtyReturn = dto.getQtyReturn();
            if (qtyReturn == null) {
                qtyReturn = 0;
            }
            returnItem.setQtyReturn(qtyReturn);

            BigDecimal unitPrice = BigDecimal.valueOf(
                    dto.getUnitPrice() != null ? dto.getUnitPrice() :
                            (poItem.getUnitPrice() != null ? poItem.getUnitPrice() : 0.0)
            );

            returnItem.setUnitPrice(unitPrice);

            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qtyReturn));
            returnItem.setTotalPrice(lineTotal);


            totalReturnAmount = totalReturnAmount.add(lineTotal);

            purchaseReturn.getItems().add(returnItem);
        }

        purchaseReturn.setTotalAmount(totalReturnAmount);

        PurchaseReturn saved = purchaseReturnRepo.save(purchaseReturn);

        if (saved.getId() != null) {
            return ResponseUtils.createSuccessResponse("Purchase return processed successfully");
        }

        return ResponseUtils.createFailedResponse("Failed to process purchase return");
    }
}

