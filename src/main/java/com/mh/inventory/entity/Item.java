package com.mh.inventory.entity;

import com.mh.inventory.common.BaseEntity;
import com.mh.inventory.dtos.ItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(
        name = "ItemDtoMapping",
        classes = @ConstructorResult(
                targetClass = ItemDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "item_code", type = String.class),
                        @ColumnResult(name = "item_name", type = String.class),
                        @ColumnResult(name = "category_id", type = Long.class),
                        @ColumnResult(name = "qty", type = Integer.class),
                        @ColumnResult(name = "re_order_lvl", type = Integer.class),
                        @ColumnResult(name = "category_name", type = String.class)
                }
        )
)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_code", nullable = false, length = 100, unique = true)
    private String itemCode;

    @Column(name = "item_name", nullable = false, length = 150)
    private String itemName;

    //owning side of category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_items_category")
    )
    private Category category;

    @Column(name = "qty")
    private Integer qty = 0;

    @Column(name = "re_order_lvl")
    private Integer reOrderLevel;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stockList = new ArrayList<>();

    //mappedBy tells Hibernate: “I’m not the owner”
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<InventoryBalance> inventoryBalances;
}