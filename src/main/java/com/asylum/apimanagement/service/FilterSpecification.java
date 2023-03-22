package com.asylum.apimanagement.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.asylum.apimanagement.dao.Request;
import com.asylum.apimanagement.dao.SearchRequest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class FilterSpecification<T> {

    public Specification<T> getSearchSpecification(SearchRequest searchReq) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(searchReq.getColumn()), searchReq.getValue());
            }

        };
    }

    public Specification<T> getSearchSpecification(List<SearchRequest> searchReqs,
            Request.GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> preds = new ArrayList<>();
            for (SearchRequest req : searchReqs) {
                switch (req.getOperation()) {
                    case EQUAL:
                        Predicate eq = criteriaBuilder.equal(root.get(req.getColumn()), req.getValue());
                        preds.add(eq);
                        break;
                    case LIKE:
                        Predicate like = criteriaBuilder.like(root.get(req.getColumn()), "%" + req.getValue() + "%");
                        preds.add(like);
                        break;
                    case IN:
                        String[] split = req.getValue().split(",");
                        Predicate in = root.get(req.getColumn()).in(Arrays.asList(split));
                        preds.add(in);
                        break;
                    case LESS_THAN:
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(req.getColumn()), req.getValue());
                        preds.add(lessThan);
                        break;
                    case GREATER_THAN:
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(req.getColumn()), req.getValue());
                        preds.add(greaterThan);
                        break;
                    case BETWEEN:
                        String[] splitB = req.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(req.getColumn()), Long.parseLong(splitB[0]),Long.parseLong( splitB[1]));
                        preds.add(between);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value");
                }

            }
            if (globalOperator.equals(Request.GlobalOperator.AND))
                return criteriaBuilder.and(preds.toArray(new Predicate[0]));
            else
                return criteriaBuilder.or(preds.toArray(new Predicate[0]));
        };
    }
}
