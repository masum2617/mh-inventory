package com.mh.inventory.repository;

import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> , JpaSpecificationExecutor<Item> {

    //JpaSpecificationExecutor<Item> ( Without JpaSpecificationExecutor, findAll(Specification) does not exist.

    //for db function call to search items
    ///*Way - 01 ( works)*/
    @Query(
            value = """
            SELECT id, item_name
            FROM mh_inventory.fn_search_item(
                :searchText,
                :categoryId
            )
        """,
            nativeQuery = true
    )
    List<Object[]> searchItemWithFunc(@Param("searchText") String searchText, @Param("categoryId") Long categoryId);

    @Query(
            value = """
            SELECT *
            FROM mh_inventory.fn_search_item(
                :searchText,
                :categoryId
            )
        """,
            nativeQuery = true
    )
    List<ItemDto> searchItemWithFunc2(@Param("searchText") String searchText, @Param("categoryId") Long categoryId);


    @Query(
            value = "SELECT * FROM mh_inventory.fn_search_item(:searchText, :categoryId)",
            nativeQuery = true
    )
    List<ItemDto> searchItemWithFunc3(@Param("searchText") String searchText, @Param("categoryId") Long categoryId);

}
