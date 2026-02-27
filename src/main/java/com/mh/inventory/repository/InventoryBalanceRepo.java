package com.mh.inventory.repository;

import com.mh.inventory.entity.InventoryBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryBalanceRepo extends JpaRepository<InventoryBalance, Long> {

    //    Optional<InventoryBalance> findByItemIdAndBatchNumber(Long itemId, String batchId);
    //no join will happen with the follwing
    @Query("SELECT ib FROM InventoryBalance ib WHERE ib.item.id = :itemId AND ib.batchNumber = :batchNumber")
    Optional<InventoryBalance> findByItemIdAndBatchNumber(@Param("itemId") Long itemId, @Param("batchNumber") String batchNumber);

}
