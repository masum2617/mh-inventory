package com.mh.inventory.repository;

import com.mh.inventory.dtos.StockResponseDto;
import com.mh.inventory.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepo extends JpaRepository<Stock,Long> {
    /*several ways to find by item id
    * START*/
    //01
    Stock findByItemId(Long itemId);

    //02
    @Query("SELECT s FROM Stock s WHERE s.item.id = :itemId")
    Stock findByItemIdCustom(@Param("itemId") Long itemId);

    // Use this version to fetch the Item details in one go (avoids extra queries)
//    @Query("SELECT s FROM Stock s JOIN FETCH s.item WHERE s.item.id = :itemId")
//    Stock findByItemIdWithDetails(@Param("itemId") Long itemId);

    //JPQL
    @Query("""
    SELECT new com.mh.inventory.dtos.StockResponseDto(
        s.id,
        i.id,
        i.itemName,
        s.qtyAvailable,
        i.reOrderLevel
    )
    FROM Stock s
    JOIN s.item i
    WHERE i.id = :itemId
""")
    StockResponseDto findStockWithItem(@Param("itemId") Long itemId);


    //native query
    @Query(
            value = """
            SELECT * FROM mh_inventory.stock(:itemId)
        """, nativeQuery = true
    )
    Stock findLowStockPurchases(@Param("itemId") Long itemId);

    /*=== END ====*/
}
