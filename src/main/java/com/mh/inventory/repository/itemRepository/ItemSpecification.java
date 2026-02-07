package com.mh.inventory.repository.itemRepository;

import com.mh.inventory.entity.Item;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> filter(
            String keyword,
            List<Long> categoryIds,
            Integer activeStatus
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("itemName")),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
            }

            if (categoryIds != null && !categoryIds.isEmpty()) {
                predicates.add(root.get("category").get("id").in(categoryIds));
            }

            if (activeStatus != null) {
                predicates.add(cb.equal(root.get("activeStatus"), activeStatus));
            }
//
//            if (startDate != null && endDate != null) {
//                predicates.add(
//                        cb.between(root.get("createdDate"), startDate, endDate)
//                );
//            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
