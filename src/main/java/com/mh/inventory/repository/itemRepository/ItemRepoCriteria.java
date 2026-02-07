package com.mh.inventory.repository.itemRepository;

import com.mh.inventory.common.BaseService;
import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.entity.Category;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.Stock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepoCriteria extends BaseService {

    @Autowired
    private EntityManager em;

/*
    public Response getItemListWithStock(Long itemId) {

        *//*Way Globally with base Repo*//*
        initRoot(Item.class, ItemDto.class);
        Join<Item, Category>  categoryJoin = root.join("category", JoinType.LEFT);
        Join<Item, Stock>  stockJoin = root.join("stockList", JoinType.LEFT);


        *//*Way 01 - works*//*
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<ItemDto> criteria = criteriaBuilder.createQuery(ItemDto.class);
//
//        Root<Item> root = criteriaQuery.from(Item.class);
//
//        Join<Item, Category>  categoryJoin = itemRoot.join("category", JoinType.LEFT);
//        Join<Item, Stock>  stockJoin = itemRoot.join("stockList", JoinType.LEFT);
        *//*way 01 - works*//*

        criteriaQuery.multiselect(
                root.get("itemName"),
                categoryJoin.get("categoryName"),
                stockJoin.get("qtyAvailable")
        );
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), itemId));

//        List<ItemDto> resultList = em.createQuery(criteriaQuery).getResultList();
//        return ResponseUtils.createSuccessResponse("success", resultList);

        return findList(criteriaQuery);
    }*/



    public Response getItemListWithStock(Long itemId) {

        /*Way Globally with base Repo*/
        initRoot(Item.class, ItemDto.class);
        Join<Item, Category> categoryJoin = root.join("category", JoinType.LEFT);
        Join<Item, Stock>  stockJoin = root.join("stockList", JoinType.LEFT);

        Expression<Integer> stockQty = criteriaBuilder.coalesce(criteriaBuilder.sum(stockJoin.get("qtyAvailable")), 0 );



        criteriaQuery.multiselect(
                root.get("itemName"),
                categoryJoin.get("categoryName"),
                stockJoin.get("qtyAvailable")
        );
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), itemId));
        criteriaQuery.groupBy(root.get("itemName"), categoryJoin.get("categoryName"),  stockJoin.get("qtyAvailable"));

        return findList(criteriaQuery);
    }
}
