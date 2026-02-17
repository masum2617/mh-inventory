package com.mh.inventory.repository;

import com.mh.inventory.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepo extends JpaRepository<InventoryTransaction,Long> {
}
