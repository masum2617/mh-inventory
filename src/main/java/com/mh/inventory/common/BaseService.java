package com.mh.inventory.common;

import com.mh.inventory.common.commonresponse.Response;
import com.mh.inventory.common.commonresponse.ResponseUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class BaseService extends BaseRepositoryCriteria {
    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Transactional(readOnly = true)
    public Response findList(CriteriaQuery criteria) {
        Response response = new Response();
        List list = null;
        try {
            list = entityManager.createQuery(criteria).setHint(QueryHints.HINT_READONLY, true).getResultList();
            if (list.size() > 0) {

                return ResponseUtils.createSuccessResponse("Data found ", list);
            }
            return ResponseUtils.createFailedResponse("Data Empty ");
        } catch (Exception e) {
            return ResponseUtils.createFailedResponse("Data Not Found");
        }
    }
}
