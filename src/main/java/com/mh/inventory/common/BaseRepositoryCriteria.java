package com.mh.inventory.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public class BaseRepositoryCriteria {

    @Autowired
    private EntityManager entityManager;


    public CriteriaBuilder criteriaBuilder = null;
    public CriteriaQuery<?> criteriaQuery = null;
    public Root<?> root = null;


    @SuppressWarnings({ "rawtypes" })
    public void initRoot(Class query, Class result) {
        initCriteriaRoot(query, result);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Root initCriteriaRoot(Class clazz1, Class clazz2) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(clazz2);
        Root root = criteria.from(clazz1);
        this.criteriaBuilder = builder;
        this.criteriaQuery = criteria;
        this.root = root;
        return root;
    }

}
