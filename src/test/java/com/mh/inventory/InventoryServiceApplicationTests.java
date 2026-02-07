package com.mh.inventory;

import com.mh.inventory.dtos.ItemDto;
import com.mh.inventory.entity.Category;
import com.mh.inventory.entity.Item;
import com.mh.inventory.entity.Stock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class InventoryServiceApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {

	}

	@Test
	public void getItemListWithStock() {
		Long itemId = 8L;
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ItemDto> criteriaQuery = criteriaBuilder.createQuery(ItemDto.class);

		Root<Item> itemRoot = criteriaQuery.from(Item.class);

		Join<Item, Category> categoryJoin = itemRoot.join("category", JoinType.LEFT);
		Join<Item, Stock>  stockJoin = itemRoot.join("stockList", JoinType.LEFT);

		criteriaQuery.multiselect(
//				itemRoot.get("id"),
				itemRoot.get("itemName"),
				categoryJoin.get("categoryName"),
				stockJoin.get("qtyAvailable")
		);
		criteriaQuery.where(criteriaBuilder.equal(itemRoot.get("id"), itemId));

		List<ItemDto> resultList = em.createQuery(criteriaQuery).getResultList();

		System.out.println(resultList.stream().toList());

//		return ResponseUtils.createSuccessResponse("success", resultList);
	}


}
