package com.mh.inventory.repository;

import com.mh.inventory.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {

    @Query("""
    select po from PurchaseOrder po
    left join fetch po.items poi
    left join fetch poi.item
    where po.id = :id
""")
    Optional<PurchaseOrder> findByIdWithItems(@Param("id") Long id);
}
