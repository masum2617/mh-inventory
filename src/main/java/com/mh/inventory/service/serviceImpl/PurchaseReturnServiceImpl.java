package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.PurOrderDto;
import com.mh.inventory.dtos.PurOrderReturnRequestDto;
import com.mh.inventory.dtos.PurOrderReturnRequestItemDto;
import com.mh.inventory.dtos.PurchaseOrderItemDto;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.PurchaseOrder;
import com.mh.inventory.entity.PurchaseOrderItems;
import com.mh.inventory.entity.PurchaseReturn;
import com.mh.inventory.entity.PurchaseReturnItem;
import com.mh.inventory.enums.PurchaseReturnStatus;
import com.mh.inventory.repository.ItemRepo;
import com.mh.inventory.repository.PurchaseOrderRepo;
import com.mh.inventory.repository.PurchaseReturnRepo;
import com.mh.inventory.service.PurchaseReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ItemRepo itemRepo;

    public Response purchaseOrderDataList(){
        return null;
    }

    public Page<PurOrderDto> getPurchaseReturnData(
            String keyword,
            Date startDate,
            Date endDate,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PurOrderDto> purOrderDtos = purchaseReturnRepo.purOrderDataViewPaginatedList(
                keyword,
                pageable,
                startDate,
                endDate
        );
        return purOrderDtos;
    }

    @Override
    public Response processPurchaseOrderReturn(PurOrderReturnRequestDto request) {

        if (request == null || request.getPurchaseOrderId() == null) {
            return ResponseUtils.createFailedResponse("purchaseOrderId is required");
        }

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


        // keep only those whose itemId exists in the purchase order AND returnFlag == 1
        List<PurOrderReturnRequestItemDto> validReturnItems = request.getItems()
                .stream()
                .filter(dto -> existingPoItems.containsKey(dto.getItemId()))
                .filter(dto -> Objects.equals(dto.getReturnFlag(), 1))
                .collect(Collectors.toList());

        if (validReturnItems.isEmpty()) {
            return ResponseUtils.createFailedResponse("No valid return items found");
        }

        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setPurchaseOrder(purchaseOrder);
        purchaseReturn.setReturnDate(LocalDate.now());
        purchaseReturn.setReason(request.getReason());
        purchaseReturn.setRemarks(request.getRemarks());
        purchaseReturn.setStatus(PurchaseReturnStatus.PENDING); //


        BigDecimal totalReturnAmount = BigDecimal.ZERO;


        for (PurOrderReturnRequestItemDto dto : validReturnItems) {

            PurchaseOrderItems poItem = existingPoItems.get(dto.getItemId());
            Item item = poItem.getItem();

            PurchaseReturnItem returnItem = new PurchaseReturnItem();
            returnItem.setPurchaseReturn(purchaseReturn);
            returnItem.setItem(item);

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

    @Override
    public Response updatePurchaseReturn(PurOrderReturnRequestDto request) {

        if (request == null || request.getId() == null) {
            return ResponseUtils.createFailedResponse("Purchase Return Id is required");
        }

        PurchaseReturn purchaseReturn = purchaseReturnRepo.findByIdWithItems(request.getId()).orElse(null);
        if (purchaseReturn == null) {
            return ResponseUtils.createFailedResponse("Purchase Return Not Found");
        }

        if (request.getItems() == null) {
            request.setItems(Collections.emptyList());
        }

        // Only consider items explicitly marked as returnFlag == 1
        List<PurOrderReturnRequestItemDto> incomingValid = request.getItems()
                .stream()
                .filter(dto -> dto.getItemId() != null)
                .filter(dto -> Objects.equals(dto.getReturnFlag(), 1))
                .collect(Collectors.toList());

        Set<Long> incomingItemIds = incomingValid.stream()
                .map(PurOrderReturnRequestItemDto::getItemId)
                .collect(Collectors.toSet());

        // Remove DB items missing from request
        purchaseReturn.getItems().removeIf(
                existing -> existing.getItem() == null
                        || existing.getItem().getId() == null
                        || !incomingItemIds.contains(existing.getItem().getId())
        );

        // Map remaining existing items by itemId
        Map<Long, PurchaseReturnItem> existingByItemId = purchaseReturn.getItems()
                .stream()
                .filter(it -> it.getItem() != null && it.getItem().getId() != null)
                .collect(Collectors.toMap(it -> it.getItem().getId(), Function.identity()));

        // Update existing / add new
        for (PurOrderReturnRequestItemDto dto : incomingValid) {
            PurchaseReturnItem existing = existingByItemId.get(dto.getItemId());

            Integer qtyReturn = dto.getQtyReturn() != null ? dto.getQtyReturn() : 0;
            BigDecimal unitPrice = BigDecimal.valueOf(dto.getUnitPrice() != null ? dto.getUnitPrice() : 0.0);
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qtyReturn));

            if (existing != null) {
                existing.setQtyReturn(qtyReturn);
                existing.setUnitPrice(unitPrice);
                existing.setTotalPrice(lineTotal);
            } else {
                PurchaseReturnItem newItem = new PurchaseReturnItem();
                newItem.setPurchaseReturn(purchaseReturn);
                newItem.setItem(itemRepo.getReferenceById(dto.getItemId()));
                newItem.setQtyReturn(qtyReturn);
                newItem.setUnitPrice(unitPrice);
                newItem.setTotalPrice(lineTotal);
                purchaseReturn.getItems().add(newItem);
            }
        }

        // Update header fields + recompute total
        purchaseReturn.setReason(request.getReason());
        purchaseReturn.setRemarks(request.getRemarks());

        BigDecimal total = purchaseReturn.getItems().stream()
                .map(PurchaseReturnItem::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseReturn.setTotalAmount(total);

        purchaseReturnRepo.save(purchaseReturn);
        return ResponseUtils.createSuccessResponse("Purchase Return Updated Successfully");
    }


}

