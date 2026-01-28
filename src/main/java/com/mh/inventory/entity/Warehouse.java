package com.mh.inventory.entity;


import com.mh.inventory.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouses", schema = "mh_inventory")
@Getter
@Setter
@NoArgsConstructor
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String location;

    @OneToMany(mappedBy = "warehouse")
    private List<Stock> stockList = new ArrayList<>();


}

