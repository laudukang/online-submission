package me.laudukang.persistence.service.impl;

import com.google.common.base.Strings;
import me.laudukang.persistence.service.ICustomPageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/10
 * <p>Time: 17:07
 * <p>Version: 1.0
 */
public class CustomPageService<T> implements ICustomPageService {
    @Override
    public Pageable getPageRequest(int page, int pageSize, String sortCol, String sortDir) {
        return new PageRequest(page,
                pageSize, new Sort(Sort.Direction.fromString(sortDir), sortCol));
    }

    @Override
    public Specification<T> getSpecification(String account, String name) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (!Strings.isNullOrEmpty(account))
                predicate.getExpressions().add(cb.like(root.get("account").as(String.class), "%" + account.trim() + "%"));
            if (!Strings.isNullOrEmpty(name))
                predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%" + name.trim() + "%"));
            return predicate;
        };
    }
}
