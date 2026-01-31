package com.mh.inventory.repository;

import com.mh.inventory.config.jpaConfig.BaseRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepoQ extends BaseRepo {

    protected ItemRepoQ(JPAQueryFactory query) {
        super(query);
    }
}
