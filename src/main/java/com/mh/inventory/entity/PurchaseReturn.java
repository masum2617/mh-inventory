package com.mh.inventory.entity;

import com.mh.inventory.enums.PurchaseReturnStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_returns", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK → purchase_orders(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(columnDefinition = "text")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseReturnStatus status;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(columnDefinition = "text")
    private String remarks;

    @Column(name = "active_status")
    private Integer activeStatus;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToMany(mappedBy = "purchaseReturn",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PurchaseReturnItem> items = new ArrayList<>();
}