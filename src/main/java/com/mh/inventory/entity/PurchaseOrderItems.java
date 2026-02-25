package com.mh.inventory.entity;

import com.mh.inventory.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItems extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "pur_price")
    private Double purPrice;

    @Column(name = "total_price")
//    @Column(name = "total_price", precision = 15, scale = 2)
    private Double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pur_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "disc_amt")
    private Double discAmt;

    @Column(name = "qty_ordered")
    private Integer qtyOrdered;

    @Column(name = "qty_received")
    private Integer qtyReceive;

    @Column(name = "bonus_qty")
    private Integer bonusQty;

    @Column(name = "unit_price")
//    private BigDecimal unitPrice;
    private Double unitPrice;

    @Column(name = "weight")
    private String weight;

    @Column(name = "weight_cost")
    private Double weightCost;



}
