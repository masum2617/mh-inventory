package com.mh.inventory.config.jpaConfig;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class BaseRepo {
    /*query dsl*/
    protected final JPAQueryFactory query;

    protected BaseRepo(JPAQueryFactory query) {
        this.query = query;
    }
}
