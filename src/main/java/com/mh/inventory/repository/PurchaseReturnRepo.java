package com.mh.inventory.repository;

import com.mh.inventory.dtos.PurOrderDto;
import com.mh.inventory.dtos.StockResponseDto;
import com.mh.inventory.entity.PurchaseReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturn, Long> {

    @Query("""
            select distinct pr from PurchaseReturn pr
            left join fetch pr.items pri
            left join fetch pri.item
            where pr.id = :id
            """)
    Optional<PurchaseReturn> findByIdWithItems(@Param("id") Long id);


    @Query(value = """
            select 
            po.id as purOrdId,
            po.po_code as poCode,
            pr.id as purReturnId,
            pr.return_date as returnDate,
            pr.status as returnStatus,
            pri.id as purReturnItemId,
            pri.qty_return as qtyReturn,
            i.id as itemId,
            i.item_name as itemName
            from purchase_orders po
            left join purchase_returns pr on pr.purchase_order_id = po.id
            left join purchase_return_items pri on pri.purchase_return_id = pr.id
            join items i on i.id = pri.item_id
            """, nativeQuery = true)
    List<PurOrderDto> purOrderDataViewList();


    @Query(value = """
            select 
            po.id as purOrdId,
            po.po_code as poCode,
            pr.id as purReturnId,
            pr.return_date as returnDate,
            pr.status as returnStatus,
            pri.id as purReturnItemId,
            pri.qty_return as qtyReturn,
            i.id as itemId,
            i.item_name as itemName
            
            from mh_inventory.purchase_orders po
            left join mh_inventory.purchase_returns pr on pr.purchase_order_id = po.id
            left join mh_inventory.purchase_return_items pri on pri.purchase_return_id = pr.id
            join mh_inventory.items i on i.id = pri.item_id
            
            where
            (:searchKeyword is null or :searchKeyword = ''
             or lower(i.item_name) like lower(concat('%',:searchKeyword,'%')))
            """,
            countQuery = """
                    select count(pri.id)
                    from mh_inventory.purchase_orders po
                    left join mh_inventory.purchase_returns pr on pr.purchase_order_id = po.id
                    left join mh_inventory.purchase_return_items pri on pri.purchase_return_id = pr.id
                    join mh_inventory.items i on i.id = pri.item_id
                    
                    where
                    (:searchKeyword is null or :searchKeyword = ''
                     or lower(i.item_name) like lower(concat('%',:searchKeyword,'%')))
                    """,
            nativeQuery = true)
    Page<PurOrderDto> purOrderDataViewPaginatedList(String searchKeyword, Pageable pageable,
                                                    @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate);
}

