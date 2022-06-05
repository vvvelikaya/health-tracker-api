package com.diploma.service.specification;

import com.diploma.domain.Account;
import com.diploma.service.SearchParameters;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification implements Specification<Account> {

    private final SearchParameters searchParameters;

    public AccountSpecification(final SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public Predicate toPredicate(final Root<Account> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String nameKeyword = searchParameters.getNameKeyword();
        String cityKeyword = searchParameters.getSurnameKeyword();

        if (nameKeyword == null && cityKeyword == null) {
            Predicate predicate = criteriaBuilder.conjunction();
            predicates.add(predicate);
        }

        if (nameKeyword != null) {
            Predicate predicateForName = criteriaBuilder.like(root.get("name"), "%" + nameKeyword + "%");
            Predicate predicateForSurname = criteriaBuilder.like(root.get("surname"), "%" + nameKeyword + "%");
            predicates.add(criteriaBuilder.or(predicateForName, predicateForSurname));
        }

        if (cityKeyword != null) {
            Predicate predicateForCity = criteriaBuilder.like(root.join("address").get("city"), "%" + cityKeyword + "%");
            Predicate predicateForCityAddress = criteriaBuilder.like(root.join("address").get("cityAddress"), "%" + cityKeyword + "%");
            predicates.add(criteriaBuilder.or(predicateForCity, predicateForCityAddress));
        }

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }
}
