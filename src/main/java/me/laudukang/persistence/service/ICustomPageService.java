package me.laudukang.persistence.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/10
 * <p>Time: 17:06
 * <p>Version: 1.0
 */
public interface ICustomPageService<T> {
    Pageable getPageRequest(int page, int pageSize, String sortCol, String sortDir);

    Specification<T> getSpecification(final String account, final String name);
}
