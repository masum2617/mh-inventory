package com.mh.inventory.entity;


import com.mh.inventory.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "inventory_transaction", schema = "mh_inventory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransaction  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "transaction_type")
    private Integer transactionType;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "pur_price", precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "sales_price", precision = 19, scale = 2)
    private BigDecimal salesPrice;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "qty_change", nullable = false)
    private Integer qtyChange;

    @Column(name = "condition")
    private String condition;

    @Column(name = "remarks")
    private String remarks;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}