package com.mh.inventory.entity;

import com.mh.inventory.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_code", nullable = false, length = 100, unique = true)
    private String itemCode;

    @Column(name = "item_name", nullable = false, length = 150)
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_items_category")
    )
    private Category category;

    @Column(name = "qty")
    private Integer quantity = 0;

    @Column(name = "re_order_lvl")
    private Integer reOrderLevel;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stockList = new ArrayList<>();

    @Column(name = "active_status")
    private Integer activeStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


}