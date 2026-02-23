package com.mh.inventory.service.serviceImpl;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.PurchaseOrderItemDto;
import com.mh.inventory.dtos.PurchaseOrderRequestDto;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.PurchaseOrder;
import com.mh.inventory.entity.PurchaseOrderItems;
import com.mh.inventory.enums.PurchaseOrderStatus;
import com.mh.inventory.repository.ItemRepo;
import com.mh.inventory.repository.PurchaseOrderRepo;
import com.mh.inventory.service.PurchaseOrderService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final EntityManager entityManager;
    private final PurchaseOrderRepo purchaseOrderRepo;
    private final ItemRepo itemRepo;
//    private final Supplier


    @Override
    public Response createPurchaseOrder(PurchaseOrderRequestDto request) {

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

        double totalAmount = 0.0;

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

            po.getItems().add(poItem);
        }

//        po.setTotalAmount(totalAmount);

        PurchaseOrder savedData = purchaseOrderRepo.save(po);
        if(savedData.getId() != null) {
            return ResponseUtils.createSuccessResponse("Saved Successful");
        }
        return ResponseUtils.createFailedResponse("Failed To Save");
    }


    private String generatePurchaseOrderCode() {

        String poCode = (String) entityManager
                .createNativeQuery("SELECT * FROM mh_inventory.fn_generate_po_code()").getSingleResult();

        return poCode;

    }
}
