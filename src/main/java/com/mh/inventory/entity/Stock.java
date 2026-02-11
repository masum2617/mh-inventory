package com.mh.inventory.entity;

import com.mh.inventory.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_stock_product"))
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "fk_stock_supplier"))
    private Supplier supplier;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "pur_price", precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "sales_price", precision = 19, scale = 2)
    private BigDecimal salesPrice;

    @Column(name = "qty_rcv")
    private Integer qtyRcv;

    @Column(name = "qty_available")
    private Integer qtyAvailable;

    @Column(name = "reference_id", length = 100)
    private String referenceId;

    @Column(name = "condition", length = 50)
    private String condition;

    @Column(name = "transaction_type", length = 50)
    private String transactionType;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "approve_flag")
    private Integer approveFlag;

    @Column(name = "approve_by")
    private Integer approveBy;

    @Column(name = "draft_flag")
    private Integer draftFlag;

    @Column(name = "approve_date")
    private Date approveDate;
}
