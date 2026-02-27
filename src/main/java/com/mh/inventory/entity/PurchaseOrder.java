package com.mh.inventory.entity;

import com.mh.inventory.common.BaseEntity;
import com.mh.inventory.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchase_orders", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_code", unique = true)
    private String poCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private PurchaseOrderStatus orderStatus;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "expected_delivery_date")
    private Date expectedDeliveryDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "receive_flag")
    private Long receiveFlag;

    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "draft_flag")
    private Integer draftFlag;

    @Column(name = "disc_amt")
    private BigDecimal discAmt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PurchaseOrderItems> purchaseOrderItems = new ArrayList<>();
}