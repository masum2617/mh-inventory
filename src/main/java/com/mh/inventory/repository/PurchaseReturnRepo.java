package com.mh.inventory.repository;

import com.mh.inventory.entity.PurchaseReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturn, Long> {
}

