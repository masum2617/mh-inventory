package com.mh.inventory.repository;

import com.mh.inventory.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
}
