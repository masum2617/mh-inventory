package com.mh.inventory.entity;

import com.mh.inventory.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "inventory_balance",
        schema = "mh_inventory"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBalance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "pur_price", precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "qty_available", nullable = false)
    private Integer qtyAvailable = 0;

    @Column(name = "active_status")
    private Integer activeStatus;

    // ================= RELATIONS =================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

}