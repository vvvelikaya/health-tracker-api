package com.diploma.service.specification;

import com.diploma.domain.User;
import com.diploma.service.SearchParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {

    private final SearchParameters searchParameters;

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String name = searchParameters.getNameKeyword();
        String surname = searchParameters.getSurnameKeyword();

        if (name == null && surname == null) {
            Predicate predicate = criteriaBuilder.conjunction();
            predicates.add(predicate);
        }

        if (name != null) {
            Predicate predicateForName = criteriaBuilder.like(root.get("name"), "%" + name + "%");
            predicates.add(predicateForName);
        }

        if (surname != null) {
            Predicate predicateForSurname = criteriaBuilder.like(root.get("surname"), "%" + name + "%");
            predicates.add(predicateForSurname);
        }

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }
}
