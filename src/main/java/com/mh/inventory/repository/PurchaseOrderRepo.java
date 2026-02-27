package com.mh.inventory.repository;

import com.mh.inventory.dtos.PurOrderResponseDto;
import com.mh.inventory.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {

    @Query("""
                select po from PurchaseOrder po
                left join fetch po.purchaseOrderItems poi
                left join fetch poi.item
                where po.id = :id
            """)
    Optional<PurchaseOrder> findByIdWithItems(@Param("id") Long id);

    @Query(value = """
                SELECT 
                    po.id AS purOrderId,
                    po.po_code AS poCode,
                    poi.id AS purOrderItemId,
                    i.id AS itemId,
                    i.item_name AS itemName,
                    poi.qty_ordered AS qtyOrdered,
                    poi.qty_received AS qtyReceived,
                    poi.unit_price AS unitPrice
                FROM mh_inventory.purchase_orders po
                LEFT JOIN mh_inventory.purchase_order_items poi 
                    ON poi.pur_order_id = po.id
                LEFT JOIN mh_inventory.items i 
                    ON i.id = poi.item_id
                WHERE po.id = :id
            """, nativeQuery = true)
    List<PurOrderResponseDto> findPurchaseOrderWithItems(@Param("id") Long id);
}
