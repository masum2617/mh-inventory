package com.mh.inventory.procedures;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.repository.ItemRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemProcedureImpl {

    @Autowired
    EntityManager entityManager;
    @Autowired
    private ItemRepo itemRepo;

    public Response createItemWithProcedure(
            String itemCode,
            String itemName,
            Long categoryId,
            Integer qty,
            Integer reorderLevel
    ) {

        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery(
                        "mh_inventory.pd_item_save"
                );

        int index = 0;

//        Item itemEntity = itemMapper.toEntity(item);

        query.registerStoredProcedureParameter(++index, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, Long.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter(++index, String.class, ParameterMode.OUT);

        query.setParameter(1, itemCode);
        query.setParameter(2, itemName);
        query.setParameter(3, categoryId);
        query.setParameter(4, qty);
        query.setParameter(5, reorderLevel);

        query.execute();

        String error = (String) query.getOutputParameterValue(7);

        if (error != null) {
            return ResponseUtils.createFailedResponse(error);
        }

        Long id = (Long) query.getOutputParameterValue(6);

        return ResponseUtils.createSuccessResponse("Item created with id " + id);
    }

    public Response searchItemWithProcedure(String searchText) {
        /*postgres supports refcursor but NOT RECOMMENDED
         * this example is with ref cursor*/
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery(
                        "mh_inventory.pd_search_item"
                );

        int index = 0;
        ResultSet rs = null;

        query.registerStoredProcedureParameter(++index, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(++index, Void.class, ParameterMode.REF_CURSOR);

        query.setParameter(1, searchText);
        query.setParameter(2, null);

        List<ItemDto> itemDtoList = new ArrayList<>();

        try {
            rs = (ResultSet) query.getOutputParameterValue(3);

            while (rs != null && rs.next()) {
                ItemDto item = new ItemDto();
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
//                item.setCategoryId(rs.getLong("category_id"));
                item.setQty(rs.getInt("qty"));
                item.setReOrderLevel(rs.getInt("re_order_lvl"));

                itemDtoList.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ResponseUtils.createSuccessResponse("Successful", itemDtoList);
    }

    public Response createItemWithStockWithFunction(
            String itemCode,
            String itemName,
            Long categoryId,
            Integer qty,
            Integer reorderLevel
    ) {
        Long itemId = (Long) entityManager
                .createNativeQuery("""
            SELECT mh_inventory.fn_create_item_with_stock(
                :itemCode,
                :itemName,
                :categoryId,
                :qty
            )
        """)
                .setParameter("itemCode", itemCode)
                .setParameter("itemName", itemName)
                .setParameter("categoryId", categoryId)
                .setParameter("qty", qty)
                .getSingleResult();

        if (itemId == null) {
            return ResponseUtils.createFailedResponse("Item created with id " + itemId);
        }
        return ResponseUtils.createSuccessResponse("Item created with id and stock added " + itemId);

    }

    public Response searchItemWithFunc(String searchText) {
        /*Way - 01 ( works)*/
        List<Object[]> resObject = itemRepo.searchItemWithFunc(searchText, null);

//        List<ItemDto> resArr = itemRepo.searchItemWithFunc2(searchText, null);
//        List<ItemDto> resArr = itemRepo.searchItemWithFunc3(searchText, null);

        /*way 02 (works) */
        List<ItemDto> itemDtos = entityManager.createNativeQuery(
                        "SELECT * FROM mh_inventory.fn_search_item(:searchText, :categoryId)",
                        "ItemDtoMapping"
                )
                .setParameter("searchText", searchText)
                .setParameter("categoryId", null)
                .getResultList();



        List<ItemDto> itemDtoList = new ArrayList<>();

        /*Way - 01 ( works)*/
        if (resObject != null && !resObject.isEmpty()) {
            for (Object[] row : resObject) {
                ItemDto itemDto = new ItemDto();
                itemDto.setId(row[0] != null ? ((Number) row[0]).longValue() : null);

                itemDto.setItemName(row[1] != null ? row[1].toString() : null);
//                itemDto.setQty(row[3] != null ? ((Number) row[3]).intValue() : null);
//                itemDto.setReOrderLevel(row[4] != null ? ((Number) row[4]).intValue() : null);
//                if (row.length > 5) {
//                    itemDto.setCategoryName(row[5] != null ? row[5].toString() : null);
//                }

                itemDtoList.add(itemDto);
            }
        }

        if (itemDtos.isEmpty()) {
            return ResponseUtils.createFailedResponse("No items found");
        }
        return ResponseUtils.createSuccessResponse("Successful", itemDtos);
    }
}
