package com.diploma.service.specification;

import com.diploma.domain.Record;
import com.diploma.service.RecordsParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RecordSpecification implements Specification<Record> {

    private final RecordsParams recordsParams;

    @Override
    public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;
        Long userId = recordsParams.getUserId();

        if (userId != null) {
            predicate = criteriaBuilder.equal(root.get("user_id"), recordsParams.getUserId());

        } else {
            predicate = criteriaBuilder.conjunction();
        }

        return predicate;
    }
}
